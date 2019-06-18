package com.reabilitic.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;


import com.reabilitic.helpers.NetworkInfoHelper;
import com.reabilitic.helpers.ScanRangeHelper;
import com.reabilitic.models.Device;
import com.reabilitic.ui.views.WifiScannerActivity;
import com.stealthcopter.networktools.ARPInfo;

import java.util.ArrayList;
import java.util.Iterator;

import io.evercam.network.discovery.IpScan;
import io.evercam.network.discovery.ScanRange;
import io.evercam.network.discovery.ScanResult;

public class ScanService extends IntentService {
    public ScanService() {
        super("ScanNetworkIpsKerbal");
    }

    public ArrayList<Device> getDevicesFromStrings(ArrayList<String> arrayList) {
        ArrayList<Device> arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(new Device((String) it.next(), this));
        }
        return arrayList2;
    }

    protected void onHandleIntent(@Nullable Intent intent) {
        WakeLock newWakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(1, "WifiScanLockLock");
        newWakeLock.acquire();
        NetworkInfoHelper networkInfoHelper = new NetworkInfoHelper(this);
        ArrayList arrayList = new ArrayList();
        final ResultReceiver resultReceiver = (ResultReceiver) intent.getParcelableExtra(WifiScannerActivity.SCAN_RECEIVER);
        try {
            ScanRange scanRangeHelper = new ScanRangeHelper(networkInfoHelper.s_gateway, networkInfoHelper.s_netmask);
            final int size = scanRangeHelper.size();
            new IpScan(new ScanResult() {
                private int count = 0;

                public void onActiveIp(String str) {
                }

                public void onIpScanned(String str) {
                    this.count++;
                    Bundle bundle = new Bundle();
                    bundle.putInt("progress", this.count);
                    bundle.putInt(WifiScannerActivity.RECEIVER_STATE, 1);
                    bundle.putInt(WifiScannerActivity.TOTAL, size);
                    resultReceiver.send(-1, bundle);
                }
            }).scanAll(scanRangeHelper);
            ArrayList allIPAddressesInARPCache = ARPInfo.getAllIPAddressesInARPCache();
            allIPAddressesInARPCache.add(networkInfoHelper.s_ipAddress);
            ArrayList devicesFromStrings = getDevicesFromStrings(allIPAddressesInARPCache);
            Bundle bundle = new Bundle();
            bundle.putInt(WifiScannerActivity.TOTAL, size);
            bundle.putInt(WifiScannerActivity.RECEIVER_STATE, 2);
            bundle.putParcelableArrayList(WifiScannerActivity.AVAILABLE_DEVICES_LIST, devicesFromStrings);
            resultReceiver.send(-1, bundle);
        } catch (Exception e) {
            e.printStackTrace();
            resultReceiver.send(0, null);
        }
        newWakeLock.release();
    }
}
