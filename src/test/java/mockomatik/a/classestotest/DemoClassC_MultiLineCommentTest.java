package mockomatik.a.classestotest; 

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DemoClassC_MultiLineCommentTest {

    private String testClassInfoOne = "test value One";
    private String testClassInfoTwo = "test value Two";

// Ignored
	private DemoClassC_MultiLineComment cut;

	@Before
	public void setUp() {
		cut = new DemoClassC_MultiLineComment(testClassInfoOne,
					testClassInfoTwo);
	}

	@After
	public void tearDown() {
		cut = null;
	}

	@Test
	public void testPrintClassInfo() {
		//cut.printClassInfo( ... )
	}

	@Test
	public void testGetTestClassInfoOne() {
		//cut.getTestClassInfoOne( ... )
	}

	@Test
	public void testGetTestClassInfoTwo() {
		//cut.getTestClassInfoTwo( ... )
	}

}
