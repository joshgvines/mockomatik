package mockomatik.a.classestotest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DemoClassA_DefaultConstructorTest {

	private DemoClassA_DefaultConstructor cut;

	@Before
	public void setUp() {
		cut = new DemoClassA_DefaultConstructor(// Ignored

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
