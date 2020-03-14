package scrapbook.coinchange;

import static java.lang.System.*;

import java.util.*;

public final class MemoizedCoinChange {
  private static class Params {
    final int amount;
    final int limit;
    
    Params(int amount, int limit) {
      this.amount = amount;
      this.limit = limit;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + amount;
      result = 31 * result + limit;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if (obj instanceof Params) {
        final Params that = (Params) obj;
        return amount == that.amount && limit == that.limit;
      } else {
        return false;
      }
    }
  }

  static int computeCached(int amount, int[] coins, int limit, int debugLevel, Map<Params, Integer> cache) {
    if (amount == 0) {
      return 1;
    }
    
    final Params params = new Params(amount, limit);
    final Integer result = cache.get(params);
    if (result != null) {
      out.format("%samount: %d, limit: %d, cached: %d%n", indent(debugLevel), amount, limit, result);
      return result;
    } else {
      final int combs = compute(amount, coins, limit, debugLevel, cache);
      cache.put(params, combs);
      return combs;
    }
  }
  
  static int compute(int amount, int[] coins, int limit, int debugLevel, Map<Params, Integer> cache) {
    int combs = 0;
    for (int i = limit; --i >= 0; ) {
      final int coin = coins[i];
      final int remaining = amount - coin;
      if (remaining >= 0) {
        out.format("%samount: %d, coin: %d, remaining: %d%n", indent(debugLevel), amount, coin, remaining);
        final int branchCombs = computeCached(remaining, coins, i + 1, debugLevel + 1, cache);
        combs += branchCombs;
      }
    }
    
    return combs;
  }
  
  static String indent(int debugLevel) {
    return " ".repeat(debugLevel);
  }
  
  static int runAndPrint(int amount, int[] coins) {
    final int combs = computeCached(amount, coins, coins.length, 0, new HashMap<>());
    out.format("amount: %d, combs: %d%n", amount, combs);
    return combs;
  }
  
  public static void main(String[] args) {
    final int coins[] = {1, 2, 3};
    runAndPrint(100, coins);
  }
}
