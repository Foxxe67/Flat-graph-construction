package com.company;
import java.util.Arrays;

class Combinations {
    private static int M = 3;
    private static int N = 5;

    public static void getDiapazon(int m, int n){
        M = m;
        N = n;
    }

    public static int[] generateCombinations(int[] arr) {
        if (arr == null) {
            arr = new int[M];
            for (int i = 0; i < M; i++) {
                arr[i] = i + 1;
            }
            return arr;
        }
        for (int i = M - 1; i >= 0; i--) {
            if (arr[i] < N - M + i + 1) {
                arr[i]++;
                for (int j = i; j < M - 1; j++)
                    arr[j + 1] = arr[j] + 1;
                return arr;
            }
        }
        return null;
    }
}