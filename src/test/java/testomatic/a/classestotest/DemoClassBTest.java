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


	private DemoClassB cut;

	@Before
	public void setUp() {
		cut = new DemoClassB(testClassInfoOne,
					testClassInfoTwo
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
