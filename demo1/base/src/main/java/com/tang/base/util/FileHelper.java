package com.tang.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/9/29 <br>
 */
public final class FileHelper {

    private FileHelper() {

    }

    public static byte[] readFileToByte(String filePath) throws IOException {
        byte[] pic = null;
        // 读取文件
        File file = new File(filePath);
        InputStream is = new FileInputStream(file);
        pic = new byte[is.available()];
        is.read(pic);
        is.close();
        return pic;
    }

}
