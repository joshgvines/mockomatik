package testomatic.a.classestotest; 

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DemoClassCTest {

    private String testClassInfoOne = "test value One";
    private String testClassInfoTwo = "test value Two";
        int unused = 100;

	private DemoClassC cut;

	@Before
	public void setUp() {
		cut = new DemoClassC(testClassInfoOne,			testClassInfoTwo
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
