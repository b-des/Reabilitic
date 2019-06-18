package com.reabilitic.ui.views;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.reabilitic.App;
import com.reabilitic.R;
import com.reabilitic.helpers.ConnectivityHelper;
import com.reabilitic.helpers.NetworkInfoHelper;
import com.reabilitic.models.Device;
import com.reabilitic.models.WifiClientModel;
import com.reabilitic.receivers.ScanResultsReceiver;
import com.reabilitic.services.ScanService;
import com.reabilitic.ui.adapters.WifiClientsAdapter;
import com.reabilitic.utils.Const;
import com.reabilitic.utils.SharedPreferenceUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WifiScannerActivity extends AppCompatActivity {

    private static final String TAG = "WIFI_SCAN";
    public static final String AVAILABLE_DEVICES_LIST = "available_devices";
    public static final String PROGRESS = "progress";
    public static final String RECEIVER_STATE = "receiver_state";
    public static final String SCAN_RECEIVER = "scan_service_receiver";
    public static final String TOTAL = "progress_total";

    @BindView(R.id.rvClients)
    RecyclerView rvClients;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.llProgress)
    LinearLayout llProgress;

    @BindView(R.id.tvProgress)
    TextView tvProgress;

    private WifiClientsAdapter wifiClientsAdapter;
    private Thread thread;
    private Context context;
    private ScanResultsReceiver mScanResultsReceiver;
    private ScanEventesListner mListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate", "onCreate");
        setContentView(R.layout.activity_scaner);
        ButterKnife.bind(this);
        context = this;
        init();
    }

    private void init(){
        rvClients.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        wifiClientsAdapter = new WifiClientsAdapter(this);
        rvClients.setAdapter(wifiClientsAdapter);
        wifiClientsAdapter.setOnItemClickListener(item -> {
            SharedPreferenceUtils.getInstance(this).setValue(Const.SP_ALIAS_IP,item.getIp());
            SharedPreferenceUtils.getInstance(this).setValue(Const.SP_ALIAS_MAC,item.getMac());
            Toast.makeText(this,"Устройство сохранено",Toast.LENGTH_LONG).show();
            finish();
        });
        //new NetworkSniffTask(this).execute();

      //  startScan();

        this.mListner = new ScanEventesListner() {
            @Override
            public void onItemClicked(Device device) {

            }

            @Override
            public void scanFinished() {
                Log.d("LOG", "scanFinished");
            }

            @Override
            public void scanStarted() {
                Log.d("LOG", "scanStarted");
            }
        };

        setupReceivers();
        start_scan();
    }





    public interface ScanEventesListner {
        void onItemClicked(Device device);

        void scanFinished();

        void scanStarted();
    }


    public class LocalReceiver implements ScanResultsReceiver.Receiver {
        public void onReceiveResult(int i, Bundle bundle) {
            Log.d("LOG", "onReceiveResult");
            if (i != -1) {
                dispalySnackBar("wifi_problem_system");
            } else if (bundle.getInt(RECEIVER_STATE) == 1) {
                int i2 = bundle.getInt(TOTAL);
                int i3 = bundle.getInt("progress");
                int i4 = (i3 * 100) / i2;
                tvProgress.setText("Поиск устройств: "+i3 + " / " + i2);
                if (i2 - 10 <= i3) {
                    progressBar.setProgress(100);
                } else {
                    progressBar.setProgress(i4);
                }
            } else {
                ArrayList parcelableArrayList = bundle.getParcelableArrayList(AVAILABLE_DEVICES_LIST);
                if (parcelableArrayList != null) {
                    //ScanFragment.this.mAdapter.setDevices(parcelableArrayList);
                    for(Object d : parcelableArrayList){
                        Device device = (Device) d;
                        addClientToList(device.mLocalIP,device.mMacAddress);
                    }
                    //ScanFragment.this.mAdapter.setTotalDevices(parcelableArrayList.size());
                    //ScanFragment.this.update_recycler_view();
                } else {
                    dispalySnackBar("wifi_problem_system");
                }
                mListner.scanFinished();
            }
        }
    }

    private void setupReceivers() {
        this.mScanResultsReceiver = new ScanResultsReceiver(new Handler());
        this.mScanResultsReceiver.setReceiver(new LocalReceiver());
    }

    private void dispalySnackBar(String i) {
        Snackbar.make(findViewById(android.R.id.content),i,Snackbar.LENGTH_LONG).show();
    }


    private void start_scan_service() {
        Intent intent = new Intent(this, ScanService.class);
        intent.putExtra(SCAN_RECEIVER, this.mScanResultsReceiver);
        startService(intent);
        mListner.scanStarted();
    }

    public void bindNetworkInfo() {
        NetworkInfoHelper networkInfoHelper = new NetworkInfoHelper(this);
        // this.mNetworkNameTextView.setText(networkInfoHelper.s_SSID);
        // this.mNetworkIpAddress.setText(networkInfoHelper.s_gateway);
        // this.mNetworkBssid.setText(Device.getMacAddress(networkInfoHelper.s_gateway, getActivity()));
    }

    public void start_scan() {
        if (ConnectivityHelper.isWifiEnabled(this)) {
            start_scan_service();
        }else{
            Log.d("LOG", "isWifiEnabled ? false");
        }
    }



    private void addClientToList(String ip, String mac){
        runOnUiThread(() -> {
            wifiClientsAdapter.addItem(new WifiClientModel(ip,mac));
        });
    }
    public void showSnackBar(String mes) {
        runOnUiThread(() -> Snackbar.make(findViewById(android.R.id.content),mes,Snackbar.LENGTH_LONG).show());

    }

    private void startScan(){
        thread = new Thread(() -> {
            Log.d(TAG, "Let's sniff the network");

            try {

                if (context != null) {

                    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                    WifiInfo connectionInfo = wm.getConnectionInfo();
                    int ipAddress = connectionInfo.getIpAddress();
                    String ipString = Formatter.formatIpAddress(ipAddress);


                    Log.d(TAG, "activeNetwork: " + String.valueOf(activeNetwork));
                    Log.d(TAG, "ipString: " + String.valueOf(ipString));


                    String prefix = ipString.substring(0, ipString.lastIndexOf(".") + 1);

                    Log.d(TAG, "prefix: " + prefix);

                    for (int i = 1; i < 255; i++) {
                        String testIp = prefix + String.valueOf(i);

                        InetAddress address = InetAddress.getByName(testIp);
                        //boolean reachable = address.isReachable(500);
                        String hostName = address.getCanonicalHostName();

                        if (isReachable(i) && !ipString.equals(testIp)) {
                            Log.i(TAG, "Host: " + testIp + "(" + getMacAddressFromIP(String.valueOf(hostName)) + ") is reachable!\r\n");
                            addClientToList(hostName,getMacAddressFromIP(String.valueOf(hostName)));
                        }
                    }
                    llProgress.setVisibility(View.GONE);
                }
            } catch (Throwable t) {
                Log.e(TAG, "Well that's not good.", t);
                llProgress.setVisibility(View.GONE);
            }

        });

        thread.start();
    }

    class NetworkSniffTask extends AsyncTask<Void, Void, Void> {


        private WeakReference<Context> mContextRef;

        public NetworkSniffTask(Context context) {
            mContextRef = new WeakReference<Context>(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("LOG", "Let's sniff the network");

            try {
                Context context = mContextRef.get();

                if (context != null) {

                    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                    WifiInfo connectionInfo = wm.getConnectionInfo();
                    int ipAddress = connectionInfo.getIpAddress();
                    String ipString = Formatter.formatIpAddress(ipAddress);
                    String subnet = getSubnetAddress(wm.getDhcpInfo().gateway);
                    showSnackBar("prefix: " + subnet);
                    Log.d("LOG","prefix: " + subnet);
                    //String strMacAddress = "";

                    for (int i = 1; i < 255; i++) {
                        String host = subnet + "." + i;
                        String strMacAddress = getMacAddressFromIP(host);
                         Log.w("LOG", "Reachable Host: " + String.valueOf(host) + " and Mac : " + strMacAddress);

                        if(!strMacAddress.equals("00:00:00:00") && !strMacAddress.equals("00:00:00:00:00:00")){
                            strMacAddress = getMacAddressFromIP(host);
                            addClientToList(String.valueOf(host),strMacAddress);
                        }
                       /* Log.w("LOG", "Founded IP: " + String.valueOf(host) + " and Mac : " + strMacAddress + " is reachable!");
                        if (InetAddress.getByName(host).isReachable(100)) {
                            strMacAddress = getMacAddressFromIP(host);
                            addClientToList(String.valueOf(host),strMacAddress);
                            Log.d("LOG","Reachable Host: " + String.valueOf(host) + " and Mac : " + strMacAddress + " is reachable!");
                        }*/
                    }


                }
            } catch (Throwable t) {
                Log.e("LOG", "Well that's not good.", t);

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //если не обнаружили нужное устройство
            //повторяем попытку
            llProgress.setVisibility(View.GONE);
        }
    }

    public String doCommand(List<String> command)
            throws IOException
    {
        String s = null;

        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        // read the output from the command
        //Log.d(TAG,"Here is the standard output of the command:\n");
        //log("Here is the standard output of the command:\r\n");
        StringBuilder res = new StringBuilder();
        while ((s = stdInput.readLine()) != null)
        {
            // Log.d(TAG,s);
            res.append(s);
            //log(s);
        }


        // read any errors from the attempted command
        //Log.d(TAG,"Here is the standard error of the command (if any):\n");
        // log("Here is the standard error of the command (if any):\r\n");
        while ((s = stdError.readLine()) != null)
        {
            Log.d("LOG",s);

        }
        return res.toString();
    }

    private boolean isReachable(int i){
        List<String> commands = new ArrayList<String>();
        commands.add("ping");
        commands.add("-c");
        commands.add("1");
        commands.add("192.168.1."+i);
        try {
            String res = doCommand(commands);
            if(!res.contains("Destination Host Unreachable"))
                return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private String getSubnetAddress(int address) {
        String ipString = String.format(
                "%d.%d.%d",
                (address & 0xff),
                (address >> 8 & 0xff),
                (address >> 16 & 0xff));

        return ipString;
    }

    private String getMacAddressFromIP(@NonNull String ipFinding) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/net/arp"));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4) {
                    String ip = splitted[0];
                    String mac = splitted[3];
                    if (mac.matches("..:..:..:..:..:..")) {

                        if (ip.equalsIgnoreCase(ipFinding)) {
                            return mac;
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "00:00:00:00";
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(thread != null)
            thread.interrupt();
    }
}
