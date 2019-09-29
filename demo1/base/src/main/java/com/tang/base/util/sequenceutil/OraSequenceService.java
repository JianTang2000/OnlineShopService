package com.tang.base.util.sequenceutil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * < 基于Oracle的sequence获取服务，注册Spring bean,调用侧使用注入方式得到bean(是同一个)，服务内部做了并发锁处理和对缓存区的处理 ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/10/16 <br>
 */
@Component
public class OraSequenceService {

    /**
     * < Spring Boot中使用JdbcTemplate访问数据库，数据源信息是业务中心的默认数据源 >
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * MAX_VALUE_MSG
     */
    private static final String MAX_VALUE_MSG = "The maximum value of sequences %s cannot be null or bigger than Long.MAX_VALUE.";

    /**
     * < sql statement >
     */
    private static final String FORMAT_SQL = "SELECT %s.NEXTVAL SEQ FROM DUAL";

    /**
     * < sql statement >
     */
    private static final String SQL = "SELECT INCREMENT_BY, MAX_VALUE FROM USER_SEQUENCES WHERE SEQUENCE_NAME = ?";

    /**
     * < 日志 >
     */
    private static final Logger logger = LoggerFactory.getLogger(OraSequenceService.class);

    /**
     * The Lock.
     */
    private Lock lock = new ReentrantLock();

    public Long getSequenceId(String sequence) {
        // 1、得到缓存的key值
        String seqKey = getSeqKey(sequence);
        SeqItem seqItem = null;

        lock.lock();
        try {
            seqItem = SequenceCache.seqCache.get(seqKey);
            if (seqItem == null) {
                // 2、缓存步长
                seqItem = getUserSequences(sequence);
                SequenceCache.seqCache.put(seqKey, seqItem);
            }
        }
        finally {
            lock.unlock();
        }

        // 3、如果步长<=1，那么直接查询数据库序列
        if (seqItem == SeqItem.MISSING_DEFINITION || seqItem == null || seqItem.step <= 1) {
            return nextSequence(sequence);
        }

        // 4、如果序列值(next)没有超过数据库的阀值(thresholdByCached)，那么直接返回next，如超出直接从数据库里获取
        long next = seqItem.next();
        if (next < 0) {
            lock.lock();
            try {
                next = seqItem.next();
                if (next < 0) {
                    next = nextSequence(seqItem);
                    seqItem.set(next);
                    seqItem.next();
                }
            }
            finally {
                lock.unlock();
            }
        }
        logger.debug("Get sequence id by seqitem:{}, and current sequence index is {}", seqItem.toString(), next);
        return next;
    }

    private SeqItem getUserSequences(String sequence) {
        Map<String, Object> record = jdbcTemplate.queryForMap(SQL, sequence.toUpperCase());
        BigDecimal incrementBy = (BigDecimal) record.get("INCREMENT_BY");
        // 默认值是9999999999999999999999999999
        BigDecimal maxValue = (BigDecimal) record.get("MAX_VALUE");
        if (maxValue.compareTo(BigDecimal.valueOf(Long.MAX_VALUE)) == 1) {
            throw new IllegalArgumentException(String.format(MAX_VALUE_MSG, sequence));
        }
        return new SeqItem(sequence, incrementBy.longValueExact(), maxValue.longValue());
    }

    public Long nextSequence(String sequence) {
        String sql = String.format(FORMAT_SQL, sequence);
        return queryForLong(sql, "SEQ");
    }

    private Long nextSequence(SeqItem seqItem) {
        return nextSequence(seqItem.name);
    }

    /**
     * < 严格按照步长，去数据库查询下一个序列的值 > <br>
     *
     * @auther: tang
     * @param sql < >
     * @return < >
     */
    private Long queryForLong(String sql, String field, Object... args) {
        List<Map<String, Object>> lists = jdbcTemplate.queryForList(sql, args);
        if (CollectionUtils.isNotEmpty(lists)) {
            Map<String, Object> map = lists.get(0);
            if (MapUtils.isNotEmpty(map)) {
                String value = String.valueOf(map.get(field));
                if (StringUtils.isNotEmpty(value)) {
                    return Long.parseLong(value);
                }
            }
        }
        return null;
    }

    /**
     * < "ORACLE_" + sequence > <br>
     *
     * @auther: tang
     * @param sequence < >
     * @return < >
     */
    private String getSeqKey(String sequence) {
        return "ORACLE_" + sequence;
    }

}
