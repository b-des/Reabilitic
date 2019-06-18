package com.reabilitic.ui.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.text.format.Formatter;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.reabilitic.App;
import com.reabilitic.R;
import com.reabilitic.ui.base.BasePresenter;
import com.reabilitic.ui.views.ControlContract;
import com.reabilitic.utils.CountDownTimer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetAddress;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ControlPresenter extends BasePresenter<ControlContract.View> implements ControlContract.Presenter {
    ControlContract.View view;
    private String mac = "";
    private String ip = "";
    private static CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int attemptsToScanWifi = 0;
    private static final String TAG = "LOG_WIFI";

    private boolean isMotorBusy = false;

    private CountDownTimer countDownTimer;
    private RequestQueue queue;
    String lastTag = "";

    @Override
    public void viewIsReady() {
        view = getView();
        view.init();
        mac = view.getMacAddress();
        ip = view.getIpAddress();
        queue = Volley.newRequestQueue(getView().getContext());

        if (!mac.equals("") && !mac.equals(getMacAddressFromIP(ip))) {
            ip = "";
            new NetworkSniffTask(App.getContext()).execute();
        }

        countDownTimer = new CountDownTimer() {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                isMotorBusy = false;
            }
        };

    }

    @Override
    public void onButtonDown(String tag) {
        ip = view.getIpAddress();
        lastTag = tag;
        String url = "http://" + ip + "/b?s=" + tag;
       /* if (!isMotorBusy && !ip.equals("")) {
            sendControlCommand(url);
        }*/
        if (ip.equals("")) {
            view.showToast(R.string.control_text_device_not_connected);
        } else if (!isMotorBusy) {
            sendControlCommand(url);
        } else{
            countDownTimer.cancel();
            isMotorBusy = true;
        }

    }

    @Override
    public void onButtonUp() {
        ip = view.getIpAddress();
        String url = "http://" + ip + "/b?s=0";
        if (ip.equals("")) {
            view.showToast(R.string.control_text_device_not_connected);
        } else if (!isMotorBusy) {
            sendControlCommand(url);
            countDownTimer.cancel();
            if (lastTag.equals("11") || lastTag.equals("21")) {
                countDownTimer.start(1000, 1000);
            } else {
                countDownTimer.start(2000, 1000);
            }

            isMotorBusy = true;
        }else{
            isMotorBusy = false;
        }


    }

    @Override
    public void onMacScannedSuccess() {
        mac = view.getMacAddress();
        ip = "";
        //new NetworkSniffTask(App.getContext()).execute();
    }

    private void sendControlCommand(String url) {
        log("------------------------");
        log("Ушла команда: " + url);
        log("Ждем ответа...");
       /* compositeDisposable.add(
                App.getApi().sendControlCommand(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<String>() {

                            @Override
                            public void onSuccess(String result) {
                                Log.d("NETWORK", "onSuccess: " + result);
                                log("Пришел ответ: "+result);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("NETWORK", "onError: " + e.getMessage());
                                log("Ошибка запроса: "+e.getMessage());
                                view.showToast(R.string.control_text_cant_connect);
                                e.printStackTrace();
                            }
                        })
        );*/

       getView().vibrate(50);
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("LOG", response.toString());
                        log("Ответ: " + response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LOG", error.toString() + "");
                        log("Ошибка: " + error.toString());
                    }
                }
        );

        queue.add(getRequest);

    }


    @Override
    public void destroy() {
        super.destroy();
        countDownTimer.cancel();
        countDownTimer = null;
        queue.stop();
    }


    class NetworkSniffTask extends AsyncTask<Void, Void, Void> {


        private WeakReference<Context> mContextRef;

        public NetworkSniffTask(Context context) {
            mContextRef = new WeakReference<Context>(context);
            view.showLoading();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "Let's sniff the network");

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

                    view.showSnackBar("prefix: " + subnet);

                    for (int i = 1; i < 255; i++) {
                        String host = subnet + "." + i;
                        String strMacAddress = getMacAddressFromIP(host);
                        // Log.w(TAG, "Reachable Host: " + String.valueOf(host) + " and Mac : " + strMacAddress);

                        if (mac.equals(strMacAddress)) {
                            Log.w(TAG, "Founded IP: " + String.valueOf(host) + " and Mac : " + strMacAddress + " is reachable!");
                            ip = String.valueOf(host);
                            view.hideLoading();
                            view.saveIp(ip);
                            //view.showToast(R.string.control_text_scanning_success);
                            break;
                        }

                    }


                }
            } catch (Throwable t) {
                Log.e(TAG, "Well that's not good.", t);
                view.hideLoading();
                view.showToast(R.string.control_text_scanning_error);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //если не обнаружили нужное устройство
            //повторяем попытку

            /*if (ip.equals("") && ++attemptsToScanWifi <= 5) {
                new NetworkSniffTask(App.getContext()).execute();
            } else if (ip.equals("") && attemptsToScanWifi > 5) {//если число попыток подключения больше 5
                view.showToast(R.string.control_text_scanning_error);
            }*/

            view.hideLoading();
            if (ip.equals("")) {
                view.showToast(R.string.control_text_scanning_error);
            }

        }
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

    private void log(String log) {
        Log.d(TAG, log);
        view.log(log);
    }
}
