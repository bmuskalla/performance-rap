package org.eclipse.rap.rwt.performance;

import java.util.List;

public interface IPerformanceStorage {

	void putResults(String testName, List frames);

	List getAggregatedResults(String testName);
	
}
