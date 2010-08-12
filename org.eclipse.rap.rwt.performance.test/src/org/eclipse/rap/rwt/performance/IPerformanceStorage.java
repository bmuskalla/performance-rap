package org.eclipse.rap.rwt.performance;

import java.util.List;

import junit.framework.TestCase;

public interface IPerformanceStorage {

	void putResults(TestCase test, List frames);

	List getAggregatedResults(String testName);
	
}
