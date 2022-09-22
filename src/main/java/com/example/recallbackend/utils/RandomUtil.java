package com.example.recallbackend.utils;

import java.util.Random;

/**
 * @author tzih
 * @date 2022.09.12
 */
public class RandomUtil {

    /**
     *
     * @author tzih
     * @date 9/12/22
     * @param length 长度
     * @return java.lang.String
     **/
    public static String getRandomUtil(int length) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; ++i) {
            result.append(random.nextInt(5));
        }
        return result.toString();
    }

}
