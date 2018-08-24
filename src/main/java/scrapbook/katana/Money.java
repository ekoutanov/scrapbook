package scrapbook.katana;

import java.math.*;
import java.util.*;

import com.fasterxml.jackson.annotation.*;

public final class Money {
  @JsonProperty
  private final String currency;
  
  @JsonProperty
  private final BigDecimal amount;
  
  public Money(@JsonProperty("currency") String currency,
               @JsonProperty("amount") BigDecimal amount) {
    this.currency = currency;
    // strip trailing zeros to reduce the amount to the minimally-required scale; this ensures
    // the correctness of subsequent hashCode() and equals() (which take into account the scale)
    this.amount = amount.stripTrailingZeros();
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public int hashCode() {
    var result = 1;
    result = 31 * result + Objects.hashCode(currency);
    result = 31 * result + Objects.hashCode(amount);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (obj instanceof Money) {
      final var that = (Money) obj;
      return Objects.equals(currency, that.currency) && Objects.equals(amount, that.amount);
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return Money.class.getSimpleName() + " [currency=" + currency + 
        ", amount=" + amount.toPlainString() + "]";
  }
  
  public Money multiply(BigDecimal multiplicand) {
    return new Money(currency, amount.multiply(multiplicand));
  }
  
  public static Money of(String currency, double amount) {
    return of(currency, Double.toString(amount));
  }
  
  public static Money of(String currency, String amount) {
    return new Money(currency, new BigDecimal(amount));
  }
}
