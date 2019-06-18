package com.reabilitic.helpers;

import android.content.Context;
import android.util.Base64;

import com.reabilitic.R;
import com.reabilitic.models.Device;


public class VendorHelper {
    public static final String CONTENT_KEY_2 = "Y2EtYXBwLXB1Yi00Nzc5MDg0MzkwMDQ3NDQ2LzUwNzQyNzUzOTI=";

    public static String getContent(String str) {
        return new String(Base64.decode(str, 0));
    }

    public static int getIconFromDevice(Context context, Device device) {
        if (device.mIconTypeResources != 0) {
            return device.mIconTypeResources;
        }
        Object obj = -1;

        return R.mipmap.ic_launcher;
    }

    public static String getStripedMacAddress(String str) {
        if (!str.contains(":")) {
            return str;
        }
        String replace = str.replace(":", "");
        return replace.substring(0, Math.min(6, replace.length()));
    }

    public static String getVendorFromDevice(Context context, Device device) {
        return "VENDOR";
    }
}
