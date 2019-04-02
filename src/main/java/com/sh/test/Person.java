package com.sh.test;

public class Person {

    public String person = "person";

    Person(){
        System.out.println("Person()");
    }

    Person(String name){
        System.out.println("Person(" + name + ")");
    }

    static {
        System.out.println("static person");
    }

    public static String staticPerson = "staticPerson";


    public static void main(String[] args) {
        new Person("name");
    }
}
