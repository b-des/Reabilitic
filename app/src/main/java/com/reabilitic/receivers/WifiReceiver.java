package com.reabilitic.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.reabilitic.helpers.ConnectivityHelper;


public class WifiReceiver extends BroadcastReceiver {
    public static final String NOTIFY_WIFI_CHANGE = "wifi_change_notify";
    public static final String WIFI_RECEIVER_EXTRA = "wifi_receiver_extra";

    public void onReceive(Context context, Intent intent) {
        Intent intent2 = new Intent(NOTIFY_WIFI_CHANGE);
        intent2.putExtra(WIFI_RECEIVER_EXTRA, ConnectivityHelper.isWifiEnabled(context));
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);
    }
}
