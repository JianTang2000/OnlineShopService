package com.tang.base.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * < 公共的工具类，可以在各个业务中心被调用， 以类名.方法名的形式调用。 各个业务中心的pom里面引用了base这个项目，打包的时候base会被打到每个业务中心的里面取，
 * final类，有一个私有的构造方法和多个“静态”的public方法。><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/9/30 <br>
 */
public final class BaseCommonUtil {

    /**
     * < 日志 >
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseCommonUtil.class);

    private BaseCommonUtil() {

    }

    /**
     * < clob和String的互转,可用于clob字段的上传和读取中。但是其实大文件比如说长文字和图片影像等， 更合适的选择是上传其在服务器的路径，而不是直接存到数据库里面更合适 > <br>
     *
     * @auther: tang
     * @param s < >
     * @return < >
     */
    public static Clob stringToClob(String s) {
        try {
            Clob clob = new javax.sql.rowset.serial.SerialClob(s.toCharArray());
            return clob;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * < clob和String的互转.> <br>
     *
     * @auther: tang
     * @param clob < >
     * @return < >
     */
    public static String clobToString(Clob clob) {
        if (clob == null) {
            return null;
        }
        Reader is = null;
        try {
            is = clob.getCharacterStream();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        BufferedReader br = new BufferedReader(is);

        String str = null;
        try {
            str = br.readLine(); // 读取第一行
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        StringBuffer sb = new StringBuffer();
        while (str != null) { // 如果没有到达流的末尾，则继续读取下一行
            sb.append(str);
            try {
                str = br.readLine();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        String returnString = sb.toString();
        return returnString;

    }

    /**
     * < object To Bytes > <br>
     *
     * @param obj < obj,比如说反射方法的指定结果是一个object >
     * @return < >
     */
    public static <T> byte[] objectToBytes(T obj) {
        byte[] bytes = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream sOut;
        try {
            sOut = new ObjectOutputStream(out);
            sOut.writeObject(obj);
            sOut.flush();
            bytes = out.toByteArray();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    /**
     * < object转成的String,主要是用来打印日志，这个包在父pom中引用，避免jar冲突 > <br>
     *
     * @param object < >
     * @return < object转成的String >
     * @auther: tang.jian
     */
    public static String objectToJsonString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            logger.debug("objectToJsonString failed!");
        }
        return jsonString;

    }

    /**
     * < 写这个主要是因为修改<T> T转成的的String后要反转成<T> T存进包装类里，所以可以看作是objectToJsonString的反操作 > <br>
     *
     * @auther: tang
     * @param content < 这里希望String是objectToJsonString直接转来的,或者手动造的有合理结构的String也阔以 >
     * @param valueType < 待反转的T的class >
     * @return < >
     */
    public static <T> T StringToObject(String content, Class<T> valueType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return (T) objectMapper.readValue(content, valueType);
        }
        catch (IOException e) {
            logger.info("StringToObject failed!");
            return null;
        }
    }
}
