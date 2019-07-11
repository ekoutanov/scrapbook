package scrapbook.sneakers;

import static java.math.BigInteger.*;

import java.math.*;

public final class SampleAnalyser {
//  private static final MathContext MC = new MathContext(20,  RoundingMode.HALF_EVEN);
  
  public static SampleAnalysis analyse(BigInteger product, BigInteger first, BigInteger span) {
    final var last = first.add(span);
    var current = first;
    var prevRemainder = current;
//    var deltaSum = ZERO;
//    var deltaSumSquares = ZERO;
    var processed = 0L;
    
    var periodStart = -1L;
    //var period = 0L;
    var sumPeriods = 0d;
    var numPeriods = 0;
    BigInteger firstRemainder = null, lastRemainder = null;
    while (current.compareTo(last) <= 0) {
      final var remainder = product.remainder(current);
      if (firstRemainder == null) firstRemainder = remainder;
      lastRemainder = remainder;
      
      if (remainder.compareTo(ZERO) == 0) {
        return new SampleAnalysis(first, BigInteger.ZERO, BigInteger.ZERO, current);
      }
      
      var remainderDelta = prevRemainder.subtract(remainder);
      if (remainderDelta.compareTo(ZERO) <= 0) {
        remainderDelta = current.add(remainderDelta);
        if (periodStart == -1) {
          periodStart = processed;
          //System.out.println("START MARK");
        } else {
          sumPeriods += processed - periodStart;
          periodStart = processed;
          numPeriods++;
          //System.out.println("END MARK");
        }
      }
//      System.out.println("current=" + current + ", remainder=" + remainder + ", remainderDelta=" + remainderDelta);
//      deltaSum = deltaSum.add(remainderDelta);
//      deltaSumSquares = deltaSumSquares.add(remainderDelta.multiply(remainderDelta));
      prevRemainder = remainder;
      current = current.add(TWO);
      processed++;
    }
    
//    final var samples = BigDecimal.valueOf(processed);
//    final var mean = new BigDecimal(deltaSum).divide(samples, MC);
//    final var variance = new BigDecimal(deltaSumSquares).divide(samples, MC).subtract(mean.multiply(mean));
//    final var stdev = variance.sqrt(MC);
//    final var entropy = stdev.divide(new BigDecimal(first), MC);

    final BigInteger meanPeriod;
    if (numPeriods > 0) {
      meanPeriod = BigInteger.valueOf((int) (sumPeriods * 2 / numPeriods));
    } else {
      var remainderDiff = firstRemainder.subtract(lastRemainder);
      if (remainderDiff.compareTo(BigInteger.ZERO) < 0) {
        remainderDiff = remainderDiff.add(first);
      }
      meanPeriod = first.multiply(span).divide(remainderDiff);
//      System.out.println("firstRem=" + firstRemainder + ", lastRem=" + lastRemainder + ", diff=" + remainderDiff);
    }
    final var phase = firstRemainder.multiply(meanPeriod).divide(first);
    return new SampleAnalysis(first, meanPeriod, phase, null);
  }
}
