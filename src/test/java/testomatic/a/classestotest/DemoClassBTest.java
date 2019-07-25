package testomatic.a.classestotest; 

import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DemoClassBTest {

    private String testClassInfoOne;
    private String testClassInfoTwo;
    private float testfloat = 1.1F;
    private Float testFloat = 1.2F;
    private double testdouble = 1.1;
    private double testDouble = 1.01;
    private char testChar = 'a';
    private short testshort = 100;
    private Short testShort = 300;
    private long testlong = 1000000000000L;
    private Long testLong = 50000000000000L;
    private int testint = 3000000;
    private Integer testInt = 7000000;

	private DemoClassB cut;

	@Before
	public void setUp() {
		cut = new DemoClassB(testfloat,			testdouble,			testshort
		);
	}

	@After
	public void tearDown() {
		cut = null;
	}

	@Test
	public void testMethod() {
		//blah
	}
}
