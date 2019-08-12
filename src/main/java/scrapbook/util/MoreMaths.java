package scrapbook.util;

public final class MoreMaths {
  /**
   *  Subtracts two {@code long} values, returning the difference as an {@code int}, while
   *  accounting for possible integer overflow (capping the difference within the integer boundary).
   *  
   *  @param minuend The value to subtract from.
   *  @param subtrahend The value to subtract.
   *  @return The difference.
   */
  public static int intSubtract(long minuend, long subtrahend) {
    final var difference = minuend - subtrahend;
    return (int) Math.max(Integer.MIN_VALUE, Math.min(difference, Integer.MAX_VALUE));
  }
}
