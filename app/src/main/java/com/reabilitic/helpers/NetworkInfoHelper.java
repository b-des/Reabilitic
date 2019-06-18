package com.reabilitic.helpers;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.net.NetworkInterface;
import java.util.Collections;

import io.evercam.network.discovery.IpTranslator;

public class NetworkInfoHelper {
    public WifiManager mWifiManager;
    public String s_BSSID;
    public String s_SSID;
    public String s_dns1;
    public String s_dns2;
    public String s_gateway;
    public String s_ipAddress;
    public boolean s_isHidden;
    public int s_leaseDuration;
    public String s_netmask;
    public String s_serverAddress;

    public NetworkInfoHelper(Context context) {
        this.mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        this.s_dns1 = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().dns1);
        this.s_dns2 = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().dns2);
        this.s_gateway = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().gateway);
        this.s_ipAddress = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().ipAddress);
        this.s_leaseDuration = this.mWifiManager.getDhcpInfo().leaseDuration * 1000;
        this.s_netmask = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().netmask);
        this.s_serverAddress = IpTranslator.getIpFromIntSigned(this.mWifiManager.getDhcpInfo().serverAddress);
        this.s_SSID = this.mWifiManager.getConnectionInfo().getSSID();
        this.s_SSID = this.s_SSID.replaceAll("^\"|\"$", "");
        this.s_BSSID = this.mWifiManager.getConnectionInfo().getBSSID();
        this.s_isHidden = this.mWifiManager.getConnectionInfo().getHiddenSSID();
    }

    public String getInterfaceMacAddress(Context context) {
        String string = "unknown_mac_address";
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress == null) {
                        return "";
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    for (byte b : hardwareAddress) {
                        stringBuilder.append(Integer.toHexString(b & 255) + ":");
                    }
                    if (stringBuilder.length() > 0) {
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                    }
                    return stringBuilder.toString();
                }
            }
            return string;
        } catch (Exception e) {
            return "unknown_mac_address";
        }
    }

    public int getLinkSpeed() {
        return this.mWifiManager.getConnectionInfo().getLinkSpeed();
    }
}
