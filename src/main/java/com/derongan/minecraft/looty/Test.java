package com.derongan.minecraft.looty;

public class Test {
    public static void main(String[] args) {
        Test.a("HEllo");
    }
    static void a(Object a){
        System.out.println("OBJECT CALLED");
    }

    static void a(String a){
        System.out.println("STRING CALLED");
    }
}
