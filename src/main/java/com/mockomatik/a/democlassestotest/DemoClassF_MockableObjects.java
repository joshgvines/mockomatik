package com.mockomatik.a.democlassestotest;

import java.io.File;
import java.net.Socket;
import java.net.URL;

public class DemoClassF_MockableObjects {

    private String testString = "test str";

    // resources/types.txt
    // This will be Mocked because it is on the types.txt list
    private Object obj;
    public File file;
    private URL url;

    /* These examples will not be Mocked because it not on the types.txt list
     * Be careful what object you add to the types list to mock!
     * Mockito does not allow the mocking of private/final classes in java
     * Some versions of the JDK will have different results... */
    private StringBuilder sb;
    public Socket socket;

    // Using common java objects which could be accidently mocked with an @Mock annotation
    public DemoClassF_MockableObjects(Object obj, File file) {
        this.obj = obj;
        this.file = file;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(DemoClassB_PrimitiveTypes obj) {
        this.obj = obj;
    }
}
