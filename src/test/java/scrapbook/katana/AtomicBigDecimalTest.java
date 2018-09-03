package scrapbook.katana;

import static org.junit.Assert.*;

import java.math.*;
import java.util.concurrent.atomic.*;

import org.junit.*;

import com.obsidiandynamics.func.*;

import nl.jqno.equalsverifier.*;

public final class AtomicBigDecimalTest {
  @Test(expected=NullArgumentException.class)
  public void testWithNullValue() {
    new AtomicBigDecimal(null);
  }
  
  @Test
  public void testEqualsHashCode() {
    EqualsVerifier.forClass(AtomicBigDecimal.class).suppress(Warning.NULL_FIELDS).verify();
  }
  
  @Test
  public void testConstructorAndGet() {
    final var decimal = new AtomicBigDecimal(new BigDecimal("10.50"));
    assertEquals(new BigDecimal("10.50"), decimal.get());
  }
  
  @Test
  public void testCompare() {
    final var a = new AtomicBigDecimal(new BigDecimal("5.00"));
    final var b = new AtomicBigDecimal(new BigDecimal("10.00"));
    final var c = new AtomicBigDecimal(new BigDecimal("15.00"));
    assertEquals(-1, a.compareTo(b));
    assertEquals(1, b.compareTo(a));
    assertEquals(0, b.compareTo(b));
    assertEquals(-1, a.compareTo(c));
    assertEquals(-1, b.compareTo(c));
  }
  
  @Test
  public void testToString() {
    final var bd = new BigDecimal("5.00");
    assertEquals(bd.toString(), new AtomicBigDecimal(bd).toString());
  }
  
  @Test
  public void testNumberMethods() {
    final var bd = new BigDecimal("5.00");
    assertEquals(bd.intValue(), new AtomicBigDecimal(bd).intValue());
    assertEquals(bd.longValue(), new AtomicBigDecimal(bd).longValue());
    assertEquals(bd.floatValue(), new AtomicBigDecimal(bd).floatValue(), Float.MAX_VALUE);
    assertEquals(bd.doubleValue(), new AtomicBigDecimal(bd).doubleValue(), Double.MIN_VALUE);
  }
  
  @Test
  public void testGetAndSet() {
    final var initialValue = new BigDecimal("5.00");
    final var decimal = new AtomicBigDecimal(initialValue);
    final var newValue = new BigDecimal("10.00");
    assertEquals(initialValue, decimal.getAndSet(newValue));
    assertEquals(newValue, decimal.get());
    
    decimal.set(initialValue);
    assertEquals(initialValue, decimal.get());
  }
  
  @Test
  public void testUpdateImmediateSuccess() {
    final var initialValue = new BigDecimal("5.00");
    final var decimal = new AtomicBigDecimal(initialValue);
    final var beforeUpdate = decimal.update(d -> d.add(new BigDecimal("3.00")));
    assertEquals(initialValue, beforeUpdate);
    assertEquals(new BigDecimal("8.00"), decimal.get());
  }
  
  @Test
  public void testUpdateWithOneFailure() {
    final var initialValue = new BigDecimal("5.00");
    final var decimal = new AtomicBigDecimal(initialValue);
    final AtomicInteger calls = new AtomicInteger();
    final var beforeUpdate = decimal.update(d -> {
      if (calls.getAndIncrement() == 0) {
        decimal.set(new BigDecimal("10.00"));
      }
      return d.add(new BigDecimal("3.00"));
    });
    assertEquals(new BigDecimal("10.00"), beforeUpdate);
    assertEquals(new BigDecimal("13.00"), decimal.get());
  }
}
