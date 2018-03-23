package cn.edu.nju.tickets.util;

import java.util.Random;

public class RandomUtil {

    public static String generateCode(int length) {
        Random random = new Random();
        String res = "";
        for (int i = 0; i < length; i++) {
            res += random.nextInt(10);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(generateCode(4));
    }

}
