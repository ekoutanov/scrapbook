package scrapbook.katana;

import java.math.*;

public final class FixedPoint {
  private static final MathContext mc = new MathContext(0, RoundingMode.DOWN);
  
  private final long raw;
  
  private final Precision precision;
  
  private FixedPoint(long amount, Precision precision) {
    this.raw = amount;
    this.precision = precision;
  }
  
  public long getRaw() {
    return raw;
  }

  public Precision getPrecision() {
    return precision;
  }
  
  public static FixedPoint of(String decimal) {
    return of(new BigDecimal(decimal));
  }
  
  public static FixedPoint of(BigDecimal decimal) {
    return of(decimal, Precision.getDefault());
  }
  
  public static FixedPoint of(BigDecimal decimal, Precision precision) {
    final var raw = decimal
        .multiply(new BigDecimal(precision.getMultiplier()), mc)
        .setScale(0, RoundingMode.DOWN)
        .longValue();
    return new FixedPoint(raw, precision);
  }
}
