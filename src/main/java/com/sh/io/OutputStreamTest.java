package com.sh.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: micomo
 * @Date: 2019/1/16 15:10
 * @Description:
 */
public class OutputStreamTest {

    public static void main(String[] args) throws IOException {
        OutputStreamTest outputStreamTest = new OutputStreamTest();
        outputStreamTest.writeFile();
    }

    /**
     * 写入文件
     * @throws IOException
     */
    public void writeFile() throws IOException {
        String str = "我是季小沫";
        String path = "C:/Users/admin/Desktop/abc";
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        /**
         * 文件夹存在时 new FileOutputStream(file)会自动创建文件
         * 文件夹不存在时
         * java.io.FileNotFoundException: C:\Users\admin\Desktop\abc\abc.txt (系统找不到指定的路径。)
         */
        FileOutputStream out = new FileOutputStream(new File(path, "abc.txt"));
        byte[] b = str.getBytes();
        out.write(b);
        out.close();
    }
}
