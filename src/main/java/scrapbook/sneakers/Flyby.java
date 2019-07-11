package scrapbook.sneakers;

import static java.math.BigInteger.*;

import java.math.*;

public final class Flyby {
  public static void main(String[] args) {
//    final var p = new BigInteger("735632791");
//    final var q = new BigInteger("982451653");
    final var p = new BigInteger("23391000133");
    final var q = new BigInteger("23591002111");
//    final var p = new BigInteger("233910007921");
//    final var q = new BigInteger("293910003161");
//  final var p = new BigInteger("37975227936943673922808872755445627854565536638199");
//  final var q = new BigInteger("40094690950920881030683735292761468389214899724061");
    final var product = p.multiply(q);
    final var sqrt = product.sqrt();
    System.out.println("product=" + product + ", sqrt=" + sqrt);
    
    final var span = BigInteger.valueOf(100);
    
    final var last = sqrt;
    var current = last.subtract(span);
    final var oneHundred = BigInteger.valueOf(100);
    while (current.compareTo(ONE) > 0) {
      final var analysis = SampleAnalyser.analyse(product, current, span);
      final var progress = current.multiply(oneHundred).divide(last);
      System.out.print(progress + "% ");
      System.out.println(analysis);
      if (analysis.getFactor() != null) {
        System.out.println("Factor: " + analysis.getFactor());
        return;
      }
      
      if (analysis.getPeriod().compareTo(span) > 0) {
        current = current.subtract(analysis.getPeriod()).add(analysis.getPhase()).subtract(TWO);
        if (! current.testBit(0)) {
          current = current.add(ONE);
        }
      } else {
        current = current.subtract(span).subtract(TWO);
      }
    }
    

    
//    final var samples = 100;
//    final var first = p.subtract(BigInteger.valueOf(span.longValue() * samples / 2));
//    final var last = p.add(BigInteger.valueOf(span.longValue() * samples / 2));
//    
//    var current = first;
//    while (current.compareTo(last) <= 0) {
//      final var analysis = SampleAnalyser.analyse(product, current, span);
//      System.out.println(analysis);
//      current = current.add(span).add(TWO);
//    }
  }
}
