package com.mockomatik.a.democlassestotest; 

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

public class DemoClassC_MultiLineCommentTest {

    private String testClassInfoOne = "test value One";
    private String testClassInfoTwo = "test value Two";
	private int unused = 100;

// Ignored
	private DemoClassC_MultiLineComment cut;

	@Before
	public void setUp() {
		cut = new DemoClassC_MultiLineComment(testClassInfoOne,
				testClassInfoTwo);
	}

	@After
	public void tearDown() {
		cut = null;
	}

	@Test
	public void testPrintClassInfo() {
		//cut.printClassInfo( ... )
	}

}
