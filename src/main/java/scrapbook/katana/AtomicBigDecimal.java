package scrapbook.katana;

import static com.obsidiandynamics.func.Functions.*;

import java.math.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

import com.obsidiandynamics.func.*;

public final class AtomicBigDecimal extends Number implements Comparable<AtomicBigDecimal> {
  private static final long serialVersionUID = 1L;
  
  private final AtomicReference<BigDecimal> ref;
  
  public AtomicBigDecimal(BigDecimal value) {
    ref = new AtomicReference<>(mustExist(value, withMessage("Value cannot be null", NullArgumentException::new)));
  }
  
  public BigDecimal get() {
    return ref.get();
  }
  
  public BigDecimal getAndSet(BigDecimal newValue) {
    return ref.getAndSet(newValue);
  }
  
  public void set(BigDecimal newValue) {
    ref.set(newValue);
  }
  
  public BigDecimal update(Function<BigDecimal, BigDecimal> operation) {
    for (;;) {
      final var value = ref.get();
      final var newValue = operation.apply(value);
      if (ref.compareAndSet(value, newValue)) return value;
    }
  }
  
  @Override
  public int hashCode() {
    return ref.get().hashCode();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (obj instanceof AtomicBigDecimal) {
      final var that = (AtomicBigDecimal) obj;
      return ref.get().equals(that.ref.get());
    } else {
      return false;
    }
  }
  
  @Override
  public String toString() {
    return ref.get().toString();
  }

  @Override
  public int compareTo(AtomicBigDecimal o) {
    return ref.get().compareTo(o.ref.get());
  }

  @Override
  public int intValue() {
    return ref.get().intValue();
  }

  @Override
  public long longValue() {
    return ref.get().longValue();
  }

  @Override
  public float floatValue() {
    return ref.get().floatValue();
  }

  @Override
  public double doubleValue() {
    return ref.get().doubleValue();
  }
}
