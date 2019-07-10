package scrapbook.sneakers;

import static com.obsidiandynamics.func.Functions.*;

import java.math.*;

public final class SampleAnalysis {
  private final BigInteger first;
  
  private final BigDecimal entropy;
  
  private final double period;
  
  private final BigInteger factor;

  public SampleAnalysis(BigInteger first, BigDecimal entropy, double period, BigInteger factor) {
    this.first = first;
    this.entropy = entropy;
    this.period = period;
    this.factor = factor;
  }

  @Override
  public String toString() {
    return SampleAnalysis.class.getSimpleName() + " [first=" + first + 
        ", period=" + String.format("%.5f", period) + 
        ", entropy=" + ifPresent(entropy, BigDecimal::toPlainString) + 
        ", factor=" + factor + "]";
  }
}
