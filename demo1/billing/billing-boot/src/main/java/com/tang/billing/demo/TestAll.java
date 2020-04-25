package com.tang.billing.demo;

import com.tang.base.util.BaseCommonUtil;
import com.tang.base.util.splitWords.SplitSentences;
import com.tang.base.util.splitWords.Words;

import java.io.IOException;
import java.util.Collections;
import java.util.Random;

public class TestAll {
    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
    public static void main(String[] args) throws IOException {
        Words words = SplitSentences.splitSentences("奥特曼裤子专用的超级大的裤子",null);
        System.out.println(BaseCommonUtil.objectToJsonString(words.wordsWithIgnore()));
        System.out.println(BaseCommonUtil.objectToJsonString(words.wordsCount()));
        System.out.println(BaseCommonUtil.objectToJsonString(words.wordsWithOutIgnore()));
        System.out.println(BaseCommonUtil.objectToJsonString(words.getCountMap()));

    }
}
