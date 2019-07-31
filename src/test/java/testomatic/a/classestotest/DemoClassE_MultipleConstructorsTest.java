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
	private int testByte;
	private String otherString;

	private DemoClassE_MultipleConstructors cut1,  cut2,  cut3,  cut4;

	@Before
	public void setUp() {
		cut1 = new DemoClassE_MultipleConstructors(
		);
		cut2 = new DemoClassE_MultipleConstructors(testString
		);
		cut3 = new DemoClassE_MultipleConstructors(testString,
					testInt
		);
		cut4 = new DemoClassE_MultipleConstructors(// Ignored
		);
	}
	@After
	public void tearDown() {
		cut1 = null;
		cut2 = null;
		cut3 = null;
		cut4 = null;
	}

	@Test
	public void testMethod() {
		//blah
	}
}
