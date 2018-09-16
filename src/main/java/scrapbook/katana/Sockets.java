package scrapbook.katana;

import java.net.*;
import java.util.*;

public final class Sockets {
  private Sockets() {}
  
  public List<NetworkInterface> getAllNetworkInterfaces() throws SocketException {
    final var networkInterfaces = new ArrayList<NetworkInterface>();
    NetworkInterface.getNetworkInterfaces().asIterator().forEachRemaining(networkInterfaces::add);
    return networkInterfaces;
  }
  
  public static boolean isExternalInterface(NetworkInterface netIface) throws SocketException {
    return ! netIface.isLoopback() && ! netIface.isVirtual() && ! netIface.isPointToPoint();
  }
  
  public static List<InetAddress> getAddresses(NetworkInterface netIface) {
    final var addresses = new ArrayList<InetAddress>();
    netIface.getInetAddresses().asIterator().forEachRemaining(addresses::add);
    return addresses;
  }
  
  /**
   *  Used to identify a set of (potentially) external addresses, by locating the first network
   *  interface that isn't a loopback, virtual or a P2P, and has at least one IPv4 address assigned 
   *  to it, and then returning all addresses assigned to that interface (including the IPv6 ones).<p>
   *  
   *  As an alternative (and more robust) technique, consider looking through the routing table to
   *  locate the interface that routes to a gateway, then taking the addresses of that interface
   *  (described in https://www.ireasoning.com/articles/find_local_ip_address.htm). However, there
   *  is no portable way of doing that across all operating systems.
   *   
   *  @param interfaces The network interfaces to consider.
   *  @return A collection of (potentially) external addresses.
   *  @throws SocketException If a socket error occurs.
   */
  public static List<InetAddress> getExternalAddresses(Collection<NetworkInterface> interfaces) throws SocketException {
    for (var netIface : interfaces) {
      if (isExternalInterface(netIface)) {
        final var addresses = getAddresses(netIface);
        final var hasIPv4Address = addresses.stream().anyMatch(Inet4Address.class::isInstance);
        if (hasIPv4Address) {
          return addresses;
        }
      }
    }
    return Collections.emptyList();
  }
}
