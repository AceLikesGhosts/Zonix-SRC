package com.thevoxelbox.voxelmap.util;

import java.util.*;
import java.net.*;

public class NetworkUtils
{
    private static ArrayList<InetAddress> localAddresses;
    private static ArrayList<InetAddress> netmasks;
    
    public static void enumerateInterfaces() throws SocketException {
        NetworkUtils.localAddresses = new ArrayList<InetAddress>();
        NetworkUtils.netmasks = new ArrayList<InetAddress>();
        final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            final NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface != null && !networkInterface.isLoopback() && networkInterface.isUp()) {
                for (final InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    if (interfaceAddress != null) {
                        final InetAddress inetAddress = interfaceAddress.getAddress();
                        if (!(inetAddress instanceof Inet4Address)) {
                            continue;
                        }
                        final InetAddress subnetMask = convertNetPrefixToNetmask(interfaceAddress.getNetworkPrefixLength());
                        if (subnetMask == null) {
                            continue;
                        }
                        NetworkUtils.localAddresses.add(inetAddress);
                        NetworkUtils.netmasks.add(subnetMask);
                    }
                }
            }
        }
    }
    
    private static InetAddress convertNetPrefixToNetmask(final int netPrefix) {
        try {
            int shiftby = Integer.MIN_VALUE;
            for (int i = netPrefix - 1; i > 0; --i) {
                shiftby >>= 1;
            }
            final String maskString = Integer.toString(shiftby >> 24 & 0xFF) + "." + Integer.toString(shiftby >> 16 & 0xFF) + "." + Integer.toString(shiftby >> 8 & 0xFF) + "." + Integer.toString(shiftby & 0xFF);
            return InetAddress.getByName(maskString);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static boolean onSameNetwork(final InetAddress ip1, final InetAddress ip2, final InetAddress mask) throws Exception {
        final byte[] a1 = ip1.getAddress();
        final byte[] a2 = ip2.getAddress();
        final byte[] m = mask.getAddress();
        for (int i = 0; i < a1.length; ++i) {
            if ((a1[i] & m[i]) != (a2[i] & m[i])) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isOnLan(final InetAddress serverAddress) {
        try {
            for (int t = 0; t < NetworkUtils.localAddresses.size(); ++t) {
                if (onSameNetwork(NetworkUtils.localAddresses.get(t), serverAddress, NetworkUtils.netmasks.get(t))) {
                    return true;
                }
            }
        }
        catch (Exception ex) {}
        return false;
    }
}
