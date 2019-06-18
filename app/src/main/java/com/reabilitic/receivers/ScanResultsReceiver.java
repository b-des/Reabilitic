package com.reabilitic.receivers;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class ScanResultsReceiver extends ResultReceiver {
    private Receiver mReceiver;

    public interface Receiver {
        void onReceiveResult(int i, Bundle bundle);
    }

    public ScanResultsReceiver(Handler handler) {
        super(handler);
    }

    protected void onReceiveResult(int i, Bundle bundle) {
        if (this.mReceiver != null) {
            this.mReceiver.onReceiveResult(i, bundle);
        }
    }

    public void setReceiver(Receiver receiver) {
        this.mReceiver = receiver;
    }
}
