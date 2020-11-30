package Backup;

import Create.ICreateAlgorithm;
import Create.SeparatedCreateAlgorithm;
import Create.SharedCreateAlgorithm;
import Delete.*;

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

    public Backup() {
        this("C:" + File.separator + "Users" + File.separator + System.getProperty("user.name") + File.separator + "Documents");
    }

    public Backup(String path) {
        resultPath = path;
        id = UUID.randomUUID();
        creationTime = new Date();
        filePaths = new ArrayList<>();
        restorePoints = new ArrayList<>();
        File file = new File(resultPath + File.separator + id);
        file.mkdirs();
    }

    public void createRestorePoint(ICreateAlgorithm algorithm, TypeOfRestorePoint typeOfRestorePoint) throws Exception {
        if (filePaths.isEmpty())
            throw new Exception("Нет добавленных файлов");

        RestorePoint restorePoint = new RestorePoint(resultPath + File.separator + id, filePaths, restorePointCounter, typeOfRestorePoint, algorithm.getType());
        if (typeOfRestorePoint.equals(TypeOfRestorePoint.FULL)) {
            creatingFull(restorePoint, algorithm);
        }
        if (typeOfRestorePoint.equals(TypeOfRestorePoint.INCREMENTAL)) {
            creatingInc(restorePoint, algorithm);
        }
    }

    private void creatingFull(RestorePoint restorePoint, ICreateAlgorithm algorithm) throws Exception {
        algorithm.create(filePaths,resultPath + File.separator + id + File.separator + restorePointCounter);
        restorePoint.setSize(algorithm.getSize(resultPath + File.separator + id + File.separator + restorePointCounter));
        backupSize += restorePoint.getSize();
        restorePoints.add(restorePoint);
        restorePointCounter++;
    }

    private void creatingInc(RestorePoint restorePoint, ICreateAlgorithm algorithm) throws Exception {
        algorithm.create(чекАмплисюда(),resultPath + File.separator + id + File.separator + restorePointCounter);
        restorePoint.setSize(algorithm.getSize(resultPath + File.separator + id + File.separator + restorePointCounter));
        backupSize += restorePoint.getSize();
        restorePoints.add(restorePoint);
        restorePointCounter++;
    }

    public ArrayList<String> чекАмплисюда() {
        ArrayList<String> амплисюда = new ArrayList<>();
        if (restorePoints.size() > 0) {
            ArrayList<String> oldPaths = new ArrayList<>(restorePoints.get(restorePoints.size()-1).getFilePaths());
            for (String s: filePaths) {
                if (!oldPaths.contains(s)) {
                    амплисюда.add(s);
                }
            }
        } else {
            return filePaths;
        }
        return амплисюда;
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

    public void deleteRestorePoint(Integer id) throws Exception {
        RestorePoint point = null;
        for (RestorePoint rp: restorePoints) {
            if (id.equals(rp.getId())) {
                point = rp;
            }
        }
        if (point.getType().equals(TypeOfRestorePoint.FULL)) {
            restorePoints.remove(point);
            if (point.getAlgo().equals(TypeOfAlgo.SHARED)) {
                IDeleteAlgorithm shared = new SharedDeleteAlgorithm();
                shared.delete(point);
            } else {
                IDeleteAlgorithm separated = new SeparatedDeleteAlgorithm();
                separated.delete(point);
            }
        }
        if (point.getType().equals(TypeOfRestorePoint.INCREMENTAL) && point.getAlgo().equals(TypeOfAlgo.SEPARATED)) {
            restorePoints.remove(point);
            if (restorePoints.stream().noneMatch(rp -> rp.getAlgo().equals(TypeOfAlgo.SEPARATED))) {
                IDeleteAlgorithm deleteAlgorithm = new SeparatedDeleteAlgorithm();
                deleteAlgorithm.delete(point);
            } else {
                RestorePoint newFirst = restorePoints.stream().filter(rp -> rp.getAlgo().equals(TypeOfAlgo.SEPARATED)).findFirst().get();
                File newFirstFile = new File(newFirst.getResultPath());
                File oldFistFile = new File(point.getResultPath());
                checkFiles(newFirstFile,oldFistFile);
            }
        }

        if (point.getType().equals(TypeOfRestorePoint.INCREMENTAL) && point.getAlgo().equals(TypeOfAlgo.SHARED)) {
            restorePoints.remove(point);
            if (restorePoints.stream().noneMatch(rp -> rp.getAlgo().equals(TypeOfAlgo.SHARED))) {
                IDeleteAlgorithm deleteAlgorithm = new SharedDeleteAlgorithm();
                deleteAlgorithm.delete(point);
            } else {
                RestorePoint newFirst = restorePoints.stream().filter(rp -> rp.getAlgo().equals(TypeOfAlgo.SEPARATED)).findFirst().get();
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


    public void clearRestorePoint(IClearingAlgo ... algos) throws Exception{
        if (restorePoints.size() == 0) {
            throw new Exception("You must add some files");
        }

        ArrayList<RestorePoint> removeRps = (new ClearingRP()).Clear(restorePoints,algos);
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
