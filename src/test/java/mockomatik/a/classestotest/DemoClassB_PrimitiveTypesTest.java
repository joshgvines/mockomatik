package mockomatik.a.classestotest; 

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
	private Scanner keyboard = new Scanner(System.in);

// Ignored
	private DemoClassB_PrimitiveTypes cut;

	@Before
	public void setUp() {
		cut = new DemoClassB_PrimitiveTypes(testfloat,
				testdouble,
				testshort);
	}

	@After
	public void tearDown() {
		cut = null;
	}

	@Test
	public void testGetTestClassInfoOne() {
		//cut.getTestClassInfoOne( ... )
	}

	@Test
	public void testSetTestClassInfoOne() {
		//cut.setTestClassInfoOne( ... )
	}

	@Test
	public void testGetTestClassInfoTwo() {
		//cut.getTestClassInfoTwo( ... )
	}

	@Test
	public void testSetTestClassInfoTwo() {
		//cut.setTestClassInfoTwo( ... )
	}

	@Test
	public void testGetTestfloat() {
		//cut.getTestfloat( ... )
	}

	@Test
	public void testSetTestfloat() {
		//cut.setTestfloat( ... )
	}

	@Test
	public void testGetTestFloat() {
		//cut.getTestFloat( ... )
	}

	@Test
	public void testSetTestFloat() {
		//cut.setTestFloat( ... )
	}

	@Test
	public void testGetTestdouble() {
		//cut.getTestdouble( ... )
	}

	@Test
	public void testSetTestdouble() {
		//cut.setTestdouble( ... )
	}

	@Test
	public void testGetTestDouble() {
		//cut.getTestDouble( ... )
	}

	@Test
	public void testSetTestDouble() {
		//cut.setTestDouble( ... )
	}

	@Test
	public void testGetTestChar() {
		//cut.getTestChar( ... )
	}

	@Test
	public void testSetTestChar() {
		//cut.setTestChar( ... )
	}

	@Test
	public void testGetTestshort() {
		//cut.getTestshort( ... )
	}

	@Test
	public void testSetTestshort() {
		//cut.setTestshort( ... )
	}

	@Test
	public void testGetTestShort() {
		//cut.getTestShort( ... )
	}

	@Test
	public void testSetTestShort() {
		//cut.setTestShort( ... )
	}

	@Test
	public void testAStringReturnTypeMethod() {
		//cut.aStringReturnTypeMethod( ... )
	}

}
