package com.mockomatik.a.democlassestotest;

public class DemoClassG_IrregularMethods {

    // No Constructor


    private void thisMethodShouldNotWork() {

    }

    /*
     * Same name, Private, different signature
     */

    private int thisMethodShouldNotWork(int fail) {
        return 1;
    }

    private String thisMethodShouldNotWork(String fail) {
        return "1";
    }

    /*
     * Same name, Public, different signature
     */

    // public int Random comment
    public int thisMethodShouldWork(int fail) {
        return 1;
    }

    // public int Random Comment
    public String thisMethodShouldWork(String fail) {
        return "1";
    }

}
