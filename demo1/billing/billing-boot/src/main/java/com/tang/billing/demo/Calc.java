package com.tang.billing.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;

public class Calc {
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static void main(String[] args) {

        int hit = 0;
        for (int i = 0; i < 10000000; i++) {
            int a = randInt(1, 6);
            int b = randInt(1, 6);
            int c = randInt(1, 6);
            if ( (a==3 &&b==3 && c==6)||(a==3 &&b==6 && c==3)||(a==6 &&b==3 && c==3)) {
                hit++;
            }
        }
        System.out.println("sum " + hit);


    }
}
