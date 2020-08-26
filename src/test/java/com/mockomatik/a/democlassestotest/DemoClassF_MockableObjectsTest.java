package com.mockomatik.a.democlassestotest; 

import java.io.File;
import java.net.Socket;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

public class DemoClassF_MockableObjectsTest {

    private String testString = "test str";

	@Mock private Object obj;
	@Mock private File file;
	@Mock private URL url;

	private DemoClassF_MockableObjects cut;

	@Before
	public void setUp() {
		cut = new DemoClassF_MockableObjects(obj,
				file);
	}

	@After
	public void tearDown() {
		cut = null;
	}

	@Test
	public void testGetObj() {
		//cut.getObj( ... )
	}

}
