package org.eclipse.rap.rwt.performance;

import org.eclipse.rap.rwt.performance.db.H2PerformanceStorage;

public class StorageFactory {

	public static IPerformanceStorage createPerformanceStorage() {
		return new H2PerformanceStorage();
	}

}
