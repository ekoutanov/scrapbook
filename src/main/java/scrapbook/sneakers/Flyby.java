package scrapbook.sneakers;

import static java.math.BigInteger.*;

import java.math.*;

public final class Flyby {
  public static void main(String[] args) {
//    final var p = new BigInteger("735632791");
//    final var q = new BigInteger("982451653");
//    final var p = new BigInteger("23391000133");
//    final var q = new BigInteger("23591002111");
    final var p = new BigInteger("233910007921");
    final var q = new BigInteger("293910003161");
//  final var p = new BigInteger("37975227936943673922808872755445627854565536638199");
//  final var q = new BigInteger("40094690950920881030683735292761468389214899724061");
    final var product = p.multiply(q);
    System.out.println("product=" + product);
    final var sqrt = product.sqrt();
    
    final var span = 50000;
    final var runs = 1000;
    
    final var first = sqrt.subtract(BigInteger.valueOf(span).multiply(BigInteger.valueOf(runs)));
    final var last = sqrt;
    
    var current = first;
    while (current.compareTo(last) < 0) {
      final var analysis = SampleAnalyser.analyse(product, current, span);
      System.out.println(analysis);
      current = current.add(BigInteger.valueOf(span));//.add(TWO);
    }
    
    
//    final var first = p.subtract(BigInteger.valueOf(span * runs / 2));
//    final var last = p.add(BigInteger.valueOf(span * runs / 2));
//    
//    var current = first;
//    while (current.compareTo(last) <= 0) {
//      final var analysis = SampleAnalyser.analyse(product, current, span);
//      System.out.println(analysis);
//      current = current.add(BigInteger.valueOf(span)).add(TWO);
//    }
  }
}
