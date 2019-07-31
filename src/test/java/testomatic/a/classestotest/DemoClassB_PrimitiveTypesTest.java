package testomatic.a.classestotest; 

import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DemoClassB_PrimitiveTypesTest {

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
    private byte testbyte = 1;
    private Byte testByte = 2;

	private DemoClassB_PrimitiveTypes cut;

	@Before
	public void setUp() {
		cut = new DemoClassB_PrimitiveTypes(testfloat,
					testdouble,
					testshort
		);
	}

	@After
	public void tearDown() {
		cut = null;
	}

	@Test
	public void testGetTestClassInfoOne() {
		//Type testResp = cut.getTestClassInfoOne()
	}

	@Test
	public void testSetTestClassInfoOne() {
		//cut.setTestClassInfoOne( variableToSet... )
	}

	@Test
	public void testGetTestClassInfoTwo() {
		//Type testResp = cut.getTestClassInfoTwo()
	}

	@Test
	public void testSetTestClassInfoTwo() {
		//cut.setTestClassInfoTwo( variableToSet... )
	}

	@Test
	public void testGetTestfloat() {
		//Type testResp = cut.getTestfloat()
	}

	@Test
	public void testSetTestfloat() {
		//cut.setTestfloat( variableToSet... )
	}

	@Test
	public void testGetTestFloat() {
		//Type testResp = cut.getTestFloat()
	}

	@Test
	public void testSetTestFloat() {
		//cut.setTestFloat( variableToSet... )
	}

	@Test
	public void testGetTestdouble() {
		//Type testResp = cut.getTestdouble()
	}

	@Test
	public void testSetTestdouble() {
		//cut.setTestdouble( variableToSet... )
	}

	@Test
	public void testGetTestDouble() {
		//Type testResp = cut.getTestDouble()
	}

	@Test
	public void testSetTestDouble() {
		//cut.setTestDouble( variableToSet... )
	}

	@Test
	public void testGetTestChar() {
		//Type testResp = cut.getTestChar()
	}

	@Test
	public void testSetTestChar() {
		//cut.setTestChar( variableToSet... )
	}

	@Test
	public void testGetTestshort() {
		//Type testResp = cut.getTestshort()
	}

	@Test
	public void testSetTestshort() {
		//cut.setTestshort( variableToSet... )
	}

	@Test
	public void testGetTestShort() {
		//Type testResp = cut.getTestShort()
	}

	@Test
	public void testSetTestShort() {
		//cut.setTestShort( variableToSet... )
	}

}
