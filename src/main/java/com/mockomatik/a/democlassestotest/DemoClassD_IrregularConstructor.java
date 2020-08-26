package com.mockomatik.a.democlassestotest;

// A Basic Class
public class DemoClassD_IrregularConstructor {

    private String testClassInfoOne = "test value One";
    private String testClassInfoTwo = "test value Two";

                            public DemoClassD_IrregularConstructor(


                                    //blah blah blah

                                    String testClassInfoOne

                                    /*



                                                String testClassInfoTwo)

                            */
                                    )

                            {





        this
                .testClassInfoOne


                    = testClassInfoOne;
                    this
                            .testClassInfoTwo
                = testClassInfoTwo;
    }

    public DemoClassD_IrregularConstructor() {
                                // blah blah blah
        String test = "test";
        Integer num = 1111100000;
    }

    /*
    blah
    blah
    String
    double
    Double
    public
    String these lines should be ignored
     */

    public void printClassInfo() {
        System.out.println(testClassInfoOne);
    }

    public String getTestClassInfoOne() {
        return testClassInfoOne;
    }

    // A method with extra space and unused variables
    public String getTestClassInfoTwo() {

        int unused = 100;

        return testClassInfoTwo;


    }
}
