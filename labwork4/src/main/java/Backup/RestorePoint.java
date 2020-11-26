package Backup;

import Create.*;
import Delete.*;
import Delete.SeparatedDeleteAlgorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class RestorePoint {
    private ArrayList<String> filePaths;
    private String resultPath;
    private long restorePointSize;
    private Date date;
    private String algo;
    private String type;
    private Integer id;

    RestorePoint(String backupPath, ArrayList<String> filePaths, int id, String type, String algo) {
        this.id = id;
        this.resultPath = backupPath + File.separator + id;
        File file = new File(resultPath);
        file.mkdirs();
        this.filePaths = new ArrayList<>(filePaths);
        date = new Date();
        this.algo = algo;
        this.type = type;
    }

    public String getAlgo() {
        return algo;
    }

    public String getType() {
        return type;
    }

    public void setSize(long size) {
        restorePointSize = size;
    }

    public long getSize() {
        return restorePointSize;
    }

    public Date getDate() {
        return date;
    }

    public String getResultPath() {
        return resultPath;
    }

    public Integer getId() {
        return id;
    }
    public ArrayList<String> getFilePaths() {
        return filePaths;
    }
}
