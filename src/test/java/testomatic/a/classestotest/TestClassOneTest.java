package testomatic.a.classestotest; 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestClassOneTest {

    private String testClassInfoOne;

    private TestClassOne cut;

    @Before
    public void setUp() {
        cut = new TestClassOne();
    }

    @After
    public void tearDown() {
        cut = null;
    }

}
