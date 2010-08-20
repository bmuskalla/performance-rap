package org.eclipse.rap.rwt.performance.phase;

import junit.framework.TestCase;

import org.eclipse.rap.rwt.performance.PerformanceStopWatch;
import org.eclipse.rwt.lifecycle.PhaseEvent;
import org.eclipse.rwt.lifecycle.PhaseId;
import org.eclipse.rwt.lifecycle.PhaseListener;

public class PerformancePhaseListener implements PhaseListener {

  private static final long serialVersionUID = 1L;
  private PerformanceStopWatch meter;

  public void beforePhase( final PhaseEvent event ) {
    meter = new PerformanceStopWatch( new TestCase() {
      public String getName() {
        return "foo#" + event.getPhaseId();
      }
    } );
    meter.start();
  }

  public void afterPhase( PhaseEvent event ) {
    meter.stop();
    meter.commitFrame();
    meter.commit();
  }

  public PhaseId getPhaseId() {
    return PhaseId.ANY;
  }
}
