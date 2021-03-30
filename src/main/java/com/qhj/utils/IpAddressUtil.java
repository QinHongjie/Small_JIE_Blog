package com.qhj.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by QHJ on 2021/3/27
 */
public class IpAddressUtil {

    public static String getIp4() {
        String ip = null;
        try {
            InetAddress ip4 = Inet4Address.getLocalHost();
            ip = ip4.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

}
