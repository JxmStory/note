package com.sh.test;

public class Dg {

    // 1 1 2 3 5 8 13 n
    public static void main(String[] args) {

        System.out.println(add(8));
        System.out.println(jieCheng(3));


    }

    public static int add(int n) {

        if (n == 1 || n == 2) {
            return 1;
        } else {
            return add(n - 2) + add(n - 1);
        }
    }

    public static int jieCheng(int n) {

        if (n == 1) {
            return 1;
        } else {
            return n * jieCheng(n - 1);
        }
    }


    public static int saleDuck(int n) {

        if (n == 1) {
            return 1;
        } else {
            return n * jieCheng(n - 1);
        }
    }
}
