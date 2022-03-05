package com.example.uebung0;

public class Ex2Calculator {
    public static Integer calculateQuersumme(int matNum) {
        if (matNum <= 9) {
            return matNum;
        }

        return matNum % 10 + calculateQuersumme(matNum / 10);
    }

    public static String calculateQuersummeAndReturnBinary(int matNum) {
        Integer sum = calculateQuersumme(matNum);
        return Integer.toBinaryString(sum);
    }
}
