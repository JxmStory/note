package com.sh.io;

import java.io.File;
import java.io.IOException;

/**
 * @Author: micomo
 * @Date: 2019/1/16 16:12
 * @Description:
 */
public class FileTesst {

    public File createFile(String fileName, String filePath){
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
        File f = new File(filePath, fileName);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    public static void main(String args[]){
        FileTesst fileTesst = new FileTesst();
        String fileName = "aaa.txt";
        String filePath = "C:/Users/admin/Desktop/file";
        fileTesst.createFile(fileName, filePath);
    }
}
