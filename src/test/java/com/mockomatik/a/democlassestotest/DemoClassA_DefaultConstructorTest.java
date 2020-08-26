package com.mockomatik.a.democlassestotest; 

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

public class DemoClassA_DefaultConstructorTest {

// Ignored
	private DemoClassA_DefaultConstructor cut;

	@Before
	public void setUp() {
		cut = new DemoClassA_DefaultConstructor();
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
