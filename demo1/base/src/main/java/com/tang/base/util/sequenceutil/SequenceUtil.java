package com.tang.base.util.sequenceutil;

import com.tang.base.util.ApplicationContextHolder;

/**
 * < 序列主键获取工具类 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/16 <br>
 */
public class SequenceUtil {
    private static OraSequenceService sequenceService;

    /**
     * < 分布式系统多服务间共用的序列获取接口。这个方法用来取得序列，取到的序列不严格遵循步长，而是每次+1，只有在步长规定的取值处才会向数据库查询拿到下一个序列的值，其他的步长间的值是内存中计算出来。
     * 这个接口保证了数据库的异常断电或服务器进程异常消亡都不会有重复序列的情况发生，并保证了分布式系统请求该接口的线程安全性
     * （即当A/B两个或多个不同服务同时大量请求同一个序列的递增值的时候，序列按上诉逻辑产生，同时可以保证各个线程获取到的序列的不重复性）。
     * 但是服务器本身的进程异常消亡有可能导致序列的不连续性（这里的连续不是指按步长连续，而是按+1连续），这个不连续性一般不会超过一个步长 > <br>
     *
     * @auther: tang
     * @param seqName < 序列名 >
     * @return < 下一个序列的值，这个值不严格遵循步长增加，而是按+1递增 >
     */
    public static long next(String seqName) {
        return getSequenceService().getSequenceId(seqName);
    }

    /**
     * < 分布式系统多服务间共用的序列获取接口。这个方法用来取得序列，每次请求都会向数据库请求查询下一个序列值，取到的序列严格遵循步长。
     * 这个接口保证了数据库的异常断电或服务器进程异常消亡都不会有重复序列的情况发生，并保证了分布式系统请求该接口的线程安全性
     * （序列的不重复性始终能保证，序列是否按照步长连续要看创建序列的时候是否启动缓存区，如果启动，oracle本身不保证按步长连续） > <br>
     *
     * @auther: tang
     * @param seqName < 序列名 >
     * @return < 下一个序列的值，这个值严格遵循步长增加 >
     */
    public static long nextByStep(String seqName) {
        return getSequenceService().nextSequence(seqName);
    }

    /**
     * < 拿到bean oraSequenceService > <br>
     *
     * @auther: tang
     * @return < OraSequenceService >
     */
    private static OraSequenceService getSequenceService() {
        if (null == sequenceService) {
            sequenceService = (OraSequenceService) ApplicationContextHolder.getBeanByName("oraSequenceService");
        }
        return sequenceService;
    }

}
