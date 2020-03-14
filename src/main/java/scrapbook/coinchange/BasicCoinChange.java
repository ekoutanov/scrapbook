package scrapbook.coinchange;

import static java.lang.System.*;

public final class BasicCoinChange {
  static int compute(int amount, int[] coins, int limit, int debugLevel) {
    if (amount == 0) {
      return 1;
    }
    
    int combs = 0;
    for (int i = limit; --i >= 0; ) {
      final int coin = coins[i];
      final int remaining = amount - coin;
      if (remaining >= 0) {
        out.format("%samount: %d, coin: %d, remaining: %d%n", indent(debugLevel), amount, coin, remaining);
        final int branchCombs = compute(remaining, coins, i + 1, debugLevel + 1);
        combs += branchCombs;
      }
    }
    
    return combs;
  }
  
  static String indent(int debugLevel) {
    return " ".repeat(debugLevel);
  }
  
  static int runAndPrint(int amount, int[] coins) {
    final int combs = compute(amount, coins, coins.length, 0);
    out.format("amount: %d, combs: %d%n", amount, combs);
    return combs;
  }
  
  public static void main(String[] args) {
    final int coins[] = {1, 2, 3};
    runAndPrint(100, coins);
  }
}
