package com.example.demo.Test;

public class Test {
    public static void main(String[] args) {
        insertionSort(new int[]{4,5,6,3,2,1},6);
    }

    public static void insertionSort(int[] a, int n) {
        if (n <= 1){
            return;
        }
        for (int i = 1; i < n; ++i) {
            int value = a[i];
            int j = i - 1;
            // 查找插入的位置
            for (; j >= 0; --j) {
                if (a[j] > value) {
                    a[j + 1] = a[j]; // 数据移动
                } else {
                    break;
                }
            }
            a[j + 1] = value;
            // 插入数据
         }
    }
}
