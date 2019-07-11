package scrapbook.sneakers;

import java.math.*;

public final class SampleAnalysis {
  private final BigInteger first;
  
  private final BigInteger period;
  
  private final BigInteger phase;
  
  private final BigInteger factor;

  public SampleAnalysis(BigInteger first, BigInteger period, BigInteger phase, BigInteger factor) {
    this.first = first;
    this.period = period;
    this.phase = phase;
    this.factor = factor;
  }
  
  public final BigInteger getFirst() {
    return first;
  }

  public final BigInteger getPeriod() {
    return period;
  }

  public final BigInteger getFactor() {
    return factor;
  }

  public final BigInteger getPhase() {
    return phase;
  }

  @Override
  public String toString() {
    return SampleAnalysis.class.getSimpleName() + " [first=" + first + 
        ", period=" + period + 
        ", phase=" + phase + 
        ", factor=" + factor + "]";
  }
}
