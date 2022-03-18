package com.sh.sort;

import java.util.ArrayList;
import java.util.List;

public class MaxSumArray {

    public static void main(String[] args) {
        int[] a = {1,9,-13,19,-3,-13,12,5,-9,-4,0,-1};
//        int [] a = {-1,-2,-3};
        System.out.println(test(a));
        System.out.println(getMaxArray(a));
    }

    public static List<Integer> getMaxArray(int[] a) {
        List<Integer> maxArr = new ArrayList<>();
        Integer maxSum = 0;
        List<Integer> curArr = new ArrayList<>();
        Integer currSum = 0;

        for (Integer i : a) {
            if (currSum > maxSum) {
                maxSum = currSum;
                maxArr.clear();
                maxArr.addAll(curArr);
            }

            currSum = currSum + i;
            if (currSum > 0) {
                curArr.add(i);
            } else {
                currSum = 0;
                curArr.clear();
            }
        }
        return maxArr;
    }


    public static List<Integer> test(int[] n) {
        int max = 0;//子数组和的最大值
        List<Integer> maxArr = new ArrayList<Integer>();//最大值对应的子数组
        int currMax = 0;//当前计算的最大值
        List<Integer> currMaxArr = new ArrayList<Integer>();//当前最大值对应的子数组

        maxArr.add(n[0]);
        for (int i = 0; i < n.length; i++) {
            if (currMax < 0) {
                currMax = n[i];
                currMaxArr.removeAll(currMaxArr);
                currMaxArr.add(n[i]);
            } else {
                currMax += n[i];
                currMaxArr.add(n[i]);
            }

            if (max < currMax) {
                max = currMax;
                maxArr.removeAll(maxArr);
                maxArr.addAll(currMaxArr);
            }
        }
        return maxArr;
    }

}

