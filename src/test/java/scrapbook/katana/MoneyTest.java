package scrapbook.katana;

import static org.junit.Assert.*;

import java.math.*;

import org.junit.*;

import com.obsidiandynamics.verifier.*;

import nl.jqno.equalsverifier.*;

public final class MoneyTest {
  @Test
  public void testPojo() {
    PojoVerifier.forClass(Money.class).verify();
  }
  
  @Test
  public void testEqualsHashCode() {
    EqualsVerifier.forClass(Money.class).verify();
  }
  
  @Test
  public void testCanonicalDouble() {
    assertEquals(Money.of("USD", "34.00"), Money.of("USD", 34.00));
    assertEquals(Money.of("USD", "34.30"), Money.of("USD", 34.30));
    assertEquals(Money.of("USD", "34.31"), Money.of("USD", 34.31));
    assertEquals(Money.of("USD", "34.315"), Money.of("USD", 34.315));
  }
  
  @Test
  public void testMultiply() {
    assertEquals(Money.of("USD", "34.50"), Money.of("USD", 17.25).multiply(BigDecimal.valueOf(2)));
  }
  
  @Test
  public void testJson() {
    RoundTripVerifier.forObject(Money.of("USD", "34.50"))
    .withCodec(JsonCodec.getDefault())
    .verify();
  }
}
