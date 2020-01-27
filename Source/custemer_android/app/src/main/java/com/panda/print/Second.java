package com.panda.print;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.print.base.Receiver;

import java.util.ArrayList;
import java.util.List;

public class Second extends AppCompatActivity {

    WifiManager wifiManager;
    WifiInfo wifi;
    TextView wifiDetail,wifiMac;
    ListView wifiList;
    int size = 0;
    List<ScanResult> results;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        wifiDetail = (TextView)findViewById(R.id.wifiP);
        wifiMac = (TextView)findViewById(R.id.wifiMac);
        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi =  wifiManager.getConnectionInfo();

        wifiList = (ListView)findViewById(R.id.wifiList);

        Log.d(  "getMacAddress: ",wifi.getMacAddress());
        Log.d(  "getIpAddress: ", String.valueOf(wifi.getIpAddress()));
        if (wifiManager.isWifiEnabled()){
            arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
            wifiList.setAdapter(arrayAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent receiver = new Intent(Second.this, Receiver.class);
        startService(receiver);
        Intent service = new Intent(Second.this, WifiBackgroundService.class);
        startService(service);

        arrayList.clear();
        registerReceiver(wifiReceiver,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        Toast.makeText(this,"Scanning" , Toast.LENGTH_LONG);

    }
    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            results = wifiManager.getScanResults();
            unregisterReceiver(this);

            for (ScanResult scanResult : results){
                arrayList.add(scanResult.SSID + scanResult.BSSID);
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };

}
