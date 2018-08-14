package scrapbook.katana;

import java.util.*;

import com.obsidiandynamics.func.*;

public final class Tuple<A, B> {
  private static final Tuple<?, ?> NULL_TUPLE = new Tuple<>(null, null);
  
  private final A first;
  
  private final B second;
  
  private Tuple(A first, B second) {
    this.first = first;
    this.second = second;
  }
  
  public A getFirst() {
    return first;
  }
  
  public B getSecond() {
    return second;
  }

  @Override
  public int hashCode() {
    var result = 1;
    result = 31 * result + Objects.hashCode(first);
    result = 31 * result + Objects.hashCode(second);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (obj instanceof Tuple) {
      final var that = (Tuple<?, ?>) obj;
      return Objects.equals(first, that.first) && Objects.equals(second, that.second);
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return Tuple.class.getSimpleName() + " [first=" + first + ", second=" + second + "]";
  }
  
  public static <A, B> Tuple<A, B> empty() {
    return Classes.cast(NULL_TUPLE);
  }
  
  public static <A, B> Tuple<A, B> of(A first, B second) {
    if (first == null && second == null) {
      return empty();
    } else {
      return new Tuple<>(first, second);
    }
  }
}