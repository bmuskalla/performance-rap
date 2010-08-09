package org.eclipse.rwt.internal.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.rap.rwt.performance.PerformanceTest;


public class ResourceUtil_PerformanceTest extends PerformanceTest {

	public void testReadBinary() throws Exception {
		Runnable testable = new Runnable() {
			public void run() {
				ClassLoader classLoader = getClass().getClassLoader();
				InputStream stream = classLoader.getResourceAsStream( "resources/big_binary.png" );
				try {
					ResourceUtil.readBinary(stream);
				} catch (IOException e) {
					e.printStackTrace();
					fail();
				}
			}
		};
		measuredRun(testable, 1000);
		meter.commit();
	}

}
