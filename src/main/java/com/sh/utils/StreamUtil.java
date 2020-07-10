package com.sh.utils;

import com.sh.common.MyException;

import javax.servlet.ServletRequest;
import java.io.*;
import java.nio.charset.Charset;

/**
 * 功能描述: 流操作
 * ministrator
 * @Date: 2019/7/22 10:03
 */
public class StreamUtil {

    /**
     * 从输入流读取数据
     */
    public static byte[] read(InputStream in) throws IOException {
        return read(in, Integer.MAX_VALUE);
    }

    /**
     * 从输入流读取数据
     */
    public static byte[] read(InputStream in, int length) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        while (os.size() < length) {
            int n = in.read(buf);
            if (n > 0)  os.write(buf, 0, n);
            else if (n < 0) break;
        }
        in.close();
        return os.toByteArray();
    }

    /**
     * 将输入流的内容全部输出到输出流
     * @param is 输入流
     * @param os 输出流
     * @throws IOException
     */
    public static void copy(InputStream is, OutputStream os) throws IOException {
        synchronized (is) {
            synchronized (os) {
                byte[] buffer = new byte[256];
                while (true) {
                    int bytesRead = is.read(buffer);
                    if (bytesRead == -1) break;
                    os.write(buffer, 0, bytesRead);
                }
            }
        }
    }

    /**
     * 从请求中读取数据
     */
    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}