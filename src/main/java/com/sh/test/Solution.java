package com.sh.test;

public class Solution {

    public static void main(String[] args) {
        int[] A = {6,5,5,4,8,2,1,0};
        System.out.println(isMonotonic(A));
    }

    public static boolean isMonotonic(int[] A) {
        if(A.length==0||A.length==1){
            return true;
        }
        boolean isup=true,isdown=true;
        int i=0;
        while(i<A.length-1){
            if(isup){
                isup=A[i]<=A[i+1]?true:false;
            }
            if(isdown){
                isdown=A[i]>=A[i+1]?true:false;
            }
            i++;
        }
        return isup||isdown;
    }
}