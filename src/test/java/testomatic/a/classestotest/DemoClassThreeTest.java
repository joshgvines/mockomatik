package testomatic.a.classestotest; 

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DemoClassThreeTest {

	private DemoClassThree cut;

	@Before
	public void setUp() {
		cut = new DemoClassThree();
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
