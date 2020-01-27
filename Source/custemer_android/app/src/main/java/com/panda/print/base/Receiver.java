package com.panda.print.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Log.d("app","Network connectivity change");
        Toast.makeText(context, "Network connectivity change", Toast.LENGTH_SHORT).show();
        if(intent.getExtras()!=null) {
            NetworkInfo ni=(NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
            if(ni!=null && ni.getState()==NetworkInfo.State.CONNECTED) {
                Log.i("app","Network "+ni.getTypeName()+" connected");
            } else if(intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,Boolean.FALSE)) {
                Log.d("app","There's no network connectivity");
            }
        }
    }
}