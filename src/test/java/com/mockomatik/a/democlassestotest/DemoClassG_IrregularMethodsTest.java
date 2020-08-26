package com.mockomatik.a.democlassestotest; 

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

public class DemoClassG_IrregularMethodsTest {

// Ignored
	private DemoClassG_IrregularMethods cut;

	@Before
	public void setUp() {
		cut = new DemoClassG_IrregularMethods();
	}

	@After
	public void tearDown() {
		cut = null;
	}

	@Test
	public void testThisMethodShouldWork() {
		//cut.thisMethodShouldWork( ... )
	}

}
