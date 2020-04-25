package com.tang.base.util.splitWords;

import com.tang.base.util.ValidateUtil;

import java.util.*;

/**
 * @author tang
 * @Description: 结果；结果的词量，结果的map形式（单词，出现次数），結果指定位置写文件；不去掉停用词的结果
 */
public class Words {

    private List<String> stopWords;

    private List<String> words;

    private static String[] stopWordsDictionary = {"的", "我", "有", "人", "个", "用", "和", "在",
            "第", "是", "片", "他", "们", "共", "了", "号", "来", "条", "点", "你", "里", "回", "者", "到",
            "应", "部", "她", "与", "句", "说", "斯", "之", "也", "中", "会", "天", "小", "种", "被", "见",
            "双", "对", "期", "新", "上", "而", "很", "前", "子", "分", "但", "就", "阿", "下", "地", "把",
            "为", "都", "已", "后", "这", "让", "又", "从", "最", "以", "吗", "吧", "着", "太", "好", "要",
            "那", "给", "请", "呢", "只", "比", "当", "将", "于", "它", "等", "不", "真", "还", "像",
            "由", "才", "没", "并", "跟", "向", "更", "如", "因", "可", "其", "么", "无", "得", "再", "别",
            "自", "全", "若", "原", "必", "啦", "使", "找", "带", "边", "该", "刚", "总", "亦", "未", "任",
            "受", "至", "且", "先", "乃", "末", "此"};

    /**
     * @param stopWords   停用词列表，可空，空使用默认停用词列表
     * @param splitResult mmseg4j分词结果
     */
    public Words(List<String> stopWords, List<String> splitResult) {
        if (ValidateUtil.validateNotEmpty(stopWords)) {
            this.stopWords = stopWords;
        }
        this.stopWords = Arrays.asList(this.stopWordsDictionary);
        this.words = splitResult;
    }


    //词量
    public int wordsCount() {
        if (ValidateUtil.validateNotEmpty(this.wordsWithIgnore())) {
            return this.wordsWithIgnore().size();
        }
        return 0;
    }

    //去掉停用詞
    public List<String> wordsWithIgnore() {
        if (ValidateUtil.validateNotEmpty(this.words)) {
            Set set = new HashSet(this.words) {
            };
            for (String stopWord : this.stopWords) {
                if (set.contains(stopWord)) {
                    set.remove(stopWord);
                }
            }
            return new ArrayList<>(set);

        } else {
            return null;
        }
    }

    //不去掉停用詞
    public List<String> wordsWithOutIgnore() {
        if (ValidateUtil.validateNotEmpty(this.words)) {
            Set set = new HashSet(this.words);
            return new ArrayList<>(set);
        } else {
            return null;
        }
    }


    //结果的map形式（单词，出现次数）
    public Map<String, Integer> getCountMap() {
        //先去掉停用词，但是不set
        //统计
        List<String> asc = new ArrayList<>(this.words);
        for (String stopWord : this.stopWords) {
            if (asc.contains(stopWord)) {
                asc.removeAll(Collections.singleton(stopWord));
            }
        }
        Map<String, Integer> countMap = new HashMap<>();
        int size1 = asc.size();
        for (String s : asc) {
            List<String> ascCopy = new ArrayList<>(asc);
            ascCopy.removeAll(Collections.singleton(s));
            int size2 = ascCopy.size();
            int count = size1 - size2;
            countMap.put(s, count);
        }
        return countMap;

    }

    //寫文件,尚未实现
    public void writeCSVWithIgnore(String path) {
    }


}
