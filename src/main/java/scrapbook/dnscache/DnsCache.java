package scrapbook.dnscache;

import java.net.*;
import java.util.*;

public final class DnsCache {
  public static void main(String[] args) throws UnknownHostException, InterruptedException {
    System.out.println("(before) networkaddress.cache.ttl=" + java.security.Security.getProperty("networkaddress.cache.ttl"));
    java.security.Security.setProperty("networkaddress.cache.ttl", String.valueOf(30));
    System.out.println("(after)  networkaddress.cache.ttl=" + java.security.Security.getProperty("networkaddress.cache.ttl"));
    
    while (true) {
      final var addr = InetAddress.getByName("test.host");
      System.out.format("[%s] addr=%s\n", new Date(), addr);
      Thread.sleep(1000);
    }
  }
}
