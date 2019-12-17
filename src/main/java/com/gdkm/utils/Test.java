package com.gdkm.utils;

public class Test {

    public static void main(String[] args) {
        int x=0,y=0,z=0;
        int q= 100;
        x=q-y-z;
        z=q-x-y;

        System.out.println(z);

        q= 3*x + 5*y + z/3;


        System.out.println(x);
        System.out.println(y);
        System.out.println(z);
    }
}
