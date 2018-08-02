package scrapbook.katana;

public final class Precision {
  private static final Precision def = new Precision(10_000_000);
  
  public static Precision getDefault() { return def; }
  
  private final long multiplier;
  
  private Precision(long multiplier) {
    this.multiplier = multiplier;
  }
  
  public long getMultiplier() {
    return multiplier;
  }

  @Override
  public String toString() {
    return Precision.class.getSimpleName() + " [multiplier=" + multiplier + "]";
  }
}
