package mockomatik.a.classestotest; 

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DemoClassD_IrregularConstructorTest {

    private String testClassInfoOne = "test";
    private String testClassInfoTwo = "test";

// Ignored
	private DemoClassD_IrregularConstructor cut;

	@Before
	public void setUp() {
		cut = new DemoClassD_IrregularConstructor(testClassInfoOne,
				testClassInfoTwo);
	}

	@After
	public void tearDown() {
		cut = null;
	}

	@Test
	public void testOutputClassInfo() {
		//cut.outputClassInfo( ... )
	}

	@Test
	public void testGetTestClassInfo() {
		//cut.getTestClassInfo( ... )
	}

}
