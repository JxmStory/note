package com.sh.io;

import java.io.*;

/**
 * @Auther: admin
 * @Date: 2019/1/16 14:23
 * @Description:
 */
public class InputStreamTest {

    public static void main(String[] args) throws IOException {
        InputStreamTest inputStreamTest = new InputStreamTest();
        System.out.println(inputStreamTest.readFile());
    }

    /**
     * 功能描述:读取文件内容
     * @param:
     * @return:
     * @auther: admin
     * @date: 2019/1/16 14:25
     */
    public String readFile() throws IOException {
        String path = "C:/Users/admin/Desktop/excel/jxc.txt";
        File file = new File(path);
        FileInputStream input = new FileInputStream(file);
        byte[] b = new byte[input.available()];
        input.read(b);
        input.close();
        return new String(b);
    }

}
