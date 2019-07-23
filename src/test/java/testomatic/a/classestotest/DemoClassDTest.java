package testomatic.a.classestotest; 

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DemoClassDTest {

    private String testClassInfoOne = "test";
    private String testClassInfoTwo = "test";


	private DemoClassD cut;

	@Before
	public void setUp() {
		cut = new DemoClassD(testClassInfoOne,
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
