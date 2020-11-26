package Backup;

import Create.ICreateAlgorithm;
import Create.SeparatedCreateAlgorithm;
import Create.SharedCreateAlgorithm;
import Delete.ClearingRP;
import Delete.IDeleteAlgorithm;
import Delete.SeparatedDeleteAlgorithm;
import Delete.SharedDeleteAlgorithm;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Backup {
    private UUID id;
    private Integer restorePointCounter = 0;
    private Date creationTime;
    private long backupSize;
    private ArrayList<RestorePoint> restorePoints;
    private ArrayList<String> filePaths;
    private String resultPath;
    private ClearingRP clearingRP = null;

    private void setDataBackup (String path) {
        resultPath = path;
        id = UUID.randomUUID();
        creationTime = new Date();
        filePaths = new ArrayList<>();
        restorePoints = new ArrayList<>();
        File file = new File(path + File.separator + id);
        file.mkdirs();
    }

    public Backup() {
        resultPath = "C:" + File.separator + "Users" + File.separator + System.getProperty("user.name") + File.separator + "Documents";
        setDataBackup(resultPath);
    }

    public Backup(String path) {
        resultPath = path;
        setDataBackup(resultPath);
    }

    public void createRestorePoint(String typeOfAlgo, String typeOfRestorePoint) throws Exception {
        if (filePaths.isEmpty())
            throw new Exception("Нет добавленных файлов");
        typeOfAlgo = typeOfAlgo.toLowerCase();
        typeOfRestorePoint = typeOfRestorePoint.toLowerCase();

        if (!(typeOfAlgo.equals("separated") || typeOfAlgo.equals("shared"))) {
            throw new Exception("Incorrect type of creating algorithm");
        }
        if (!(typeOfRestorePoint.equals("full") || typeOfRestorePoint.equals("incremental"))) {
            throw new Exception("Incorrect type of Restore Point");
        }

        if (typeOfRestorePoint.equals("full")) {
            if (typeOfAlgo.equals("separated")) {
                RestorePoint restorePoint = new RestorePoint(resultPath + File.separator + id, filePaths, restorePointCounter, typeOfRestorePoint, typeOfAlgo);
                ICreateAlgorithm algorithm = new SeparatedCreateAlgorithm();
                creatingFull(restorePoint, algorithm);
            }
            if (typeOfAlgo.equals("shared")) {
                RestorePoint restorePoint = new RestorePoint(resultPath + File.separator + id, filePaths, restorePointCounter, typeOfRestorePoint, typeOfAlgo);
                ICreateAlgorithm algorithm = new SharedCreateAlgorithm();
                creatingFull(restorePoint, algorithm);
            }
        }

        if (typeOfRestorePoint.contains("incremental")) {
            if (typeOfAlgo.equals("separated")) {
                RestorePoint restorePoint = new RestorePoint(resultPath + File.separator + id, filePaths, restorePointCounter, typeOfRestorePoint, typeOfAlgo);
                ICreateAlgorithm algorithm = new SeparatedCreateAlgorithm();
                creatingInc(restorePoint, algorithm);
            }
            if (typeOfAlgo.equals("shared")) {
                RestorePoint restorePoint = new RestorePoint(resultPath + File.separator + id, filePaths, restorePointCounter, typeOfRestorePoint, typeOfAlgo);
                ICreateAlgorithm algorithm = new SharedCreateAlgorithm();
                creatingInc(restorePoint, algorithm);
            }
        }
    }

    private void creatingFull(RestorePoint restorePoint, ICreateAlgorithm algorithm) throws Exception {
        algorithm.setData(filePaths,resultPath + File.separator + id + File.separator + restorePointCounter);
        restorePoint.setSize(algorithm.getSize(resultPath + File.separator + id + File.separator + restorePointCounter));
        backupSize += restorePoint.getSize();
        restorePoints.add(restorePoint);
        restorePointCounter++;
    }

    private void creatingInc(RestorePoint restorePoint, ICreateAlgorithm algorithm) throws Exception {
        algorithm.setData(чекАмплитуда(),resultPath + File.separator + id + File.separator + restorePointCounter);
        restorePoint.setSize(algorithm.getSize(resultPath + File.separator + id + File.separator + restorePointCounter));
        backupSize += restorePoint.getSize();
        restorePoints.add(restorePoint);
        restorePointCounter++;
    }

    public ArrayList<String> чекАмплитуда() {
        ArrayList<String> амплитуда = new ArrayList<>();
        if (restorePoints.size() > 0) {
            ArrayList<String> oldPaths = new ArrayList<>(restorePoints.get(restorePoints.size()-1).getFilePaths());
            for (String s: filePaths) {
                if (!oldPaths.contains(s)) {
                    амплитуда.add(s);
                }
            }
        } else {
            return filePaths;
        }
        return амплитуда;
    }

    public boolean addFile(String path) {
        File f = new File(path);
        if (filePaths.stream().anyMatch(filePath -> filePath.equals(path)) || !f.isFile() || !f.exists()) {
            return false;
        }
        filePaths.add(path);
        return true;
    }

    public void deleteFile(String path) {
        filePaths.remove(path);
    }

    public void deleteRestorePoint(Integer id) throws IOException {
        RestorePoint point = null;
        for (RestorePoint rp: restorePoints) {
            if (id.equals(rp.getId())) {
                point = rp;
            }
        }
        assert point != null;
        if (point.getType().equals("full")) {
            restorePoints.remove(point);
            if (point.getAlgo().equals("shared")) {
                IDeleteAlgorithm shared = new SharedDeleteAlgorithm();
                shared.delete(point);
            } else {
                IDeleteAlgorithm separated = new SeparatedDeleteAlgorithm();
                separated.delete(point);
            }
        }
        if (point.getType().equals("incremental") && point.getAlgo().equals("separated")) {
            restorePoints.remove(point);
            if (restorePoints.stream().noneMatch(rp -> rp.getAlgo().equals("incremental"))) {
                IDeleteAlgorithm deleteAlgorithm = new SeparatedDeleteAlgorithm();
                deleteAlgorithm.delete(point);
            } else {
                RestorePoint newFirst = restorePoints.stream().filter(rp -> rp.getAlgo().equals("incremental")).findFirst().get();
                File newFirstFile = new File(newFirst.getResultPath());
                File oldFistFile = new File(point.getResultPath());
                checkFiles(newFirstFile,oldFistFile);
            }
        }

        if (point.getType().equals("incremental") && point.getAlgo().equals("shared")) {
            restorePoints.remove(point);
            if (restorePoints.stream().noneMatch(rp -> rp.getAlgo().equals("incremental"))) {
                IDeleteAlgorithm deleteAlgorithm = new SharedDeleteAlgorithm();
                deleteAlgorithm.delete(point);
            } else {
                RestorePoint newFirst = restorePoints.stream().filter(rp -> rp.getAlgo().equals("incremental")).findFirst().get();
                checkFilesZip(newFirst.getResultPath() + File.separator + "Backup.zip",point.getResultPath() + File.separator + "Backup.zip");
            }
        }
    }
    public void checkFilesZip (String newFilePath, String oldFilePath) throws IOException {
        ZipFile oldFile = new ZipFile(oldFilePath);
        ZipFile newFile = new ZipFile(newFilePath);
        Enumeration<? extends ZipEntry> Old = oldFile.entries();
        Enumeration<? extends ZipEntry> New;
        while (Old.hasMoreElements()) {
            ZipEntry zeo = Old.nextElement();
            New = newFile.entries();
            boolean isExist = false;
            while (New.hasMoreElements()) {
                ZipEntry zen = New.nextElement();
                if (zen.getName().equals(zeo.getName())) {
                    isExist = true;
                }
            }
            if (!isExist) {
                ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(newFilePath));
                zos.putNextEntry(zeo);
                zos.close();
            }
        }
    }

    public void checkFiles (File newFile, File oldFile) throws IOException {
        File temp;
        for (File file: oldFile.listFiles()) {
            boolean isExist = false;
            for (File _file: newFile.listFiles()) {
                if (file.getName().equals(_file.getName())) {
                    isExist = true;
                }
            }
            if (!isExist) {
                temp = new File(newFile + File.separator + oldFile.getName());
                temp.createNewFile();
                Files.copy(file.toPath(),temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }


    public void setClearingRestorePoint(ClearingRP clearingRP) {
            this.clearingRP = clearingRP;
    }

    public void clearRestorePoint() throws Exception{
        if (clearingRP == null) {
            throw new Exception("You must use method setClearingRestorePoint before");
        }
        ArrayList<RestorePoint> removeRps = clearingRP.Clear(restorePoints);
        for (RestorePoint rp: removeRps) {
            backupSize -= rp.getSize();
        }
        for (RestorePoint rp: removeRps) {
            deleteRestorePoint(rp.getId());
        }
    }


    public String getCreationTime() {
        return creationTime.toString();
    }

    public long getBackupSize() { //размер
        return backupSize;
    }

    public int getBackupLength() { //кол-во точек
        return restorePoints.size();
    }

    public UUID getId() {
        return id;
    }

    public String getResultPath() {
        return resultPath;
    }
}
