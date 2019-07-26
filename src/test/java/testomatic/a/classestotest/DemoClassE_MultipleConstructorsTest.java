package testomatic.a.classestotest; 

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DemoClassE_MultipleConstructorsTest {

	private String testString;
	private int testInt;

	private DemoClassE_MultipleConstructors cut1,  cut2,  cut3;

	@Before
	public void setUp() {
		cut1 = new DemoClassE_MultipleConstructors(
		);
		cut2 = new DemoClassE_MultipleConstructors(testString
		);
		cut3 = new DemoClassE_MultipleConstructors(testString,
			testInt
		);
	}

	@After
	public void tearDown() {
		cut1 = null;
		cut2 = null;
		cut3 = null;
	}

	@Test
	public void testMethod() {
		//blah
	}
}
