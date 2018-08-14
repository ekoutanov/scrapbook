package scrapbook.katana;

import static org.junit.Assert.*;

import org.junit.*;

import com.obsidiandynamics.verifier.*;

import nl.jqno.equalsverifier.*;

public final class TupleTest {
  @Test
  public void testPojo() {
    PojoVerifier.forClass(Tuple.class).verify();
  }
  
  @Test
  public void testEqualsHashCode() {
    EqualsVerifier.forClass(Tuple.class).verify();
  }
  
  @Test
  public void testEmptySingleton() {
    assertSame(Tuple.empty(), Tuple.empty());
  }
  
  @Test
  public void testNonEmptyCommuteFields() {
    assertNotEquals(Tuple.of("x", null), Tuple.of(null, "x"));
  }
  
  @Test
  public void testEmpty() {
    assertSame(Tuple.empty(), Tuple.of(null, null));
  }
}
