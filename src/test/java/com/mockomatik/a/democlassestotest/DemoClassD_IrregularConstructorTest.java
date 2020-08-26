package com.mockomatik.a.democlassestotest; 

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

public class DemoClassD_IrregularConstructorTest {

    private String testClassInfoOne = "test value One";
    private String testClassInfoTwo = "test value Two";
	private int unused = 100;

// Ignored
	private DemoClassD_IrregularConstructor cut1,  cut2;

	@Before
	public void setUp() {
		cut1 = new DemoClassD_IrregularConstructor(testClassInfoOne
		);
		cut2 = new DemoClassD_IrregularConstructor(/*NoArgs*/
		);
	}

	@After
	public void tearDown() {
		cut1 = null;
		cut2 = null;
	}

	@Test
	public void testPrintClassInfo() {
		//cut.printClassInfo( ... )
	}

}
