package mockomatik.a.classestotest; 

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DemoClassF_MockableObjectsTest {

    private String testString = "test str";

	@Mock private Object obj;

	private DemoClassF_MockableObjects cut;

	@Before
	public void setUp() {
		cut = new DemoClassF_MockableObjects(obj
		);
	}

	@After
	public void tearDown() {
		cut = null;
	}

	@Test
	public void testGetObj() {
		//cut.getObj( ... )
	}

	@Test
	public void testSetObj() {
		//cut.setObj( ... )
	}

}
