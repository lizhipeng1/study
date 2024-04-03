package com.study.core.file;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URLEncoder;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-21 22:38
 */
public class FileUtils {

    /**
     * 创建文件
     * 如果目录不存在 依次创建目录
     *
     * @param path 文件目录
     * @throws IOException
     */
    public static void createFile(String path) throws IOException {
        if (StringUtils.isNotEmpty(path)) {
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
    }

    public static File getFile(String full_name) throws Exception {
        File file = new File(full_name);
        if (!file.exists()) {
            throw new Exception("文件 " + full_name + " 未找到!");
        }
        return file;
    }

    public static void writeInputToOutput(InputStream in, OutputStream out) throws Exception {
        writeInputToOutput(in, out, false);
    }

    public static void writeInputToOutput(InputStream in, OutputStream out, boolean ispersist) throws Exception {
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = -1;
        /* if no arrive the end(len is -1) in the data stream then write */
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
            out.flush();
        }
        if (!ispersist) {
            in.close();
            out.close();
        }
    }

    /**
     * delete file
     *
     * @param full_name
     * @return boolean
     * @throws Exception
     */
    public static boolean deleteFile(String full_name) throws Exception {
        File file = new File(full_name);
        if (file.exists()) return file.delete();
        return false;
    }

    /**
     * 下载文件名 encode
     * @param agent
     * @param fileName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodeDownloadFileName(String agent, String fileName) throws UnsupportedEncodingException {
        String encodeFileName = "";
        if (agent.contains("MSIE")) {
            // IE浏览器
            encodeFileName = URLEncoder.encode(fileName, "utf-8");
            encodeFileName = encodeFileName.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            encodeFileName = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            encodeFileName = URLEncoder.encode(fileName, "utf-8");
        } else {
            // 其它浏览器
            encodeFileName = URLEncoder.encode(fileName, "utf-8");
        }
        return encodeFileName;
    }
}
