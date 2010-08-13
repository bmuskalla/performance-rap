package org.eclipse.swt.widgets;

import org.eclipse.rap.rwt.performance.PerformanceTest;
import org.eclipse.swt.SWT;

public class Tree_PerformanceTest extends PerformanceTest {

  public void testFlatIndex() throws Exception {
    Display display = new Display();
    Shell shell = new Shell( display );
    final Tree tree = new Tree( shell, SWT.SINGLE | SWT.FULL_SELECTION );
    for( int i = 0; i < 500; i++ ) {
      TreeItem treeItem = new TreeItem( tree, SWT.NONE );
      treeItem.setText( "foo " + i );
      treeItem.setExpanded( true );
      for( int j = 0; j < 10; j++ ) {
        TreeItem subTreeItem = new TreeItem( tree, SWT.NONE );
        subTreeItem.setText( "sub " + j );
        subTreeItem.setExpanded( true );
      }
    }
    TreeItem treeItem = new TreeItem( tree, SWT.NONE );
    treeItem.setText( "last item" );
    Runnable testable = new Runnable() {

      public void run() {
        tree.updateFlatIndices();
      }
    };
    measuredRun( testable, 1000 );
    meter.commit();
  }
}
