package org.eclipse.swt.widgets;

import org.eclipse.rap.rwt.performance.PerformanceTestCase;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;

public class ToolItem_PerformanceTest extends PerformanceTestCase {

  public void testBounds() throws Exception {
    Display display = new Display();
    Shell shell = new Shell( display );
    
    ToolBar toolBar = new ToolBar( shell, SWT.FLAT );
    final ToolItem toolItem = new ToolItem( toolBar, SWT.PUSH );
    toolItem.setText("foo");
    
    final Rectangle[] bounds = new Rectangle[1];
    Runnable testable = new Runnable() {

      public void run() {
        bounds[0] = toolItem.getBounds();
      }
    };
    measuredRun( testable, 10000 );
    assertPerformance();
  }

}
