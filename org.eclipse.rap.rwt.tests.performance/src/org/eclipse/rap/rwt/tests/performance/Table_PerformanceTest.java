package org.eclipse.rap.rwt.tests.performance;

import org.eclipse.rap.rwt.performance.PerformanceTest;
import org.eclipse.rwt.Fixture;
import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.widgets.tablekit.TableLCA;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;


public class Table_PerformanceTest extends PerformanceTest {

	public void testRemoveAll() throws Exception {
		Display display = new Display();
		Shell shell = new Shell(display);
		final Table table = new Table(shell, SWT.SINGLE | SWT.FULL_SELECTION);
		Runnable setup = new Runnable() {
			public void run() {
				for (int i = 0; i < 50; i++) {
					TableItem tableItem = new TableItem(table, SWT.NONE);
					tableItem.setText("foo " + i);
				}
			}
		};
		Runnable testable = new Runnable() {
			public void run() {
				table.removeAll();
			}
		};
		measuredRun(setup, testable, 1000);
		meter.commit();
	}

	public void testComputeSize() throws Exception {
		Display display = new Display();
		Shell shell = new Shell(display);
		final Table table = new Table(shell, SWT.SINGLE | SWT.FULL_SELECTION);
		TableItem tableItem = new TableItem(table, SWT.NONE);
		tableItem.setText("foo ");
		Runnable testable = new Runnable() {
			public void run() {
				table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			}
		};
		measuredRun(testable, 1000);
		meter.commit();
	}

	public void testSelectAll() throws Exception {
		Display display = new Display();
		Shell shell = new Shell(display);
		final Table table = new Table(shell, SWT.SINGLE | SWT.FULL_SELECTION);
		for (int i = 0; i < 1000; i++) {
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText("foo ");
		}
		Runnable setup = new Runnable() {
			public void run() {
				table.deselectAll();
			}
		};
		Runnable testable = new Runnable() {
			public void run() {
				table.selectAll();
			}
		};
		measuredRun(setup, testable, 1000);
		meter.commit();
	}
	
	public void testLCAPerformance() throws Exception {
		Display display = new Display();
		Shell shell = new Shell(display);
		final Table table = new Table(shell, SWT.SINGLE | SWT.FULL_SELECTION);
		for (int i = 0; i < 100; i++) {
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText("foo ");
		}
		final TableLCA lca = new TableLCA();
		Fixture.fakeResponseWriter();
		Runnable testable = new Runnable() {
			public void run() {
				try {
					lca.render(table);
				} catch (Exception e) {
					e.printStackTrace();
					fail(e.getMessage());
				}
			}
		};
		measuredRun(testable, 100);
		meter.commit();
	}
}
