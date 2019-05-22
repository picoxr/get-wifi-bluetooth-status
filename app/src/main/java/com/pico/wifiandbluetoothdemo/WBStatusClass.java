package com.pico.wifiandbluetoothdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import com.unity3d.player.UnityPlayer;

import static android.content.Context.WIFI_SERVICE;

public class WBStatusClass {
    private static final String TAG = "WBStatusClass";

    // The GameObject which contain the script
    private String OBJECT_NAME = "GetStatus";

    // Method name that be called in script
    private String MN_SET_BT_Status = "GetBluetoothStatus";
    private String MN_SET_BT_CONNECTION_STATUS = "GetBluetoothConnectionStatus";
    private String MN_SET_WF_Status = "GetWifiStatus";
    private String MN_SET_WF_CONNECTION_STATUS = "GetWifiConnectionStatus";
    private String MN_SET_RSSI_STATUS = "GetWifiRssiStatus";

    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: blue " + intent.getAction());
            if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                Bundle stateBundle = intent.getExtras();
                int message = stateBundle.getInt(BluetoothAdapter.EXTRA_STATE);
                //bluetooth switch value：on oning off offing
                Log.e(TAG, "ACTION_STATE_CHANGED：" + message);
                switch (message) {
                    case BluetoothAdapter.STATE_OFF:
                        Log.e(TAG, "onReceive: " + MN_SET_BT_Status + ": " + "bt_status_off" );
                        UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_BT_Status, "bt_status_off");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.e(TAG, "onReceive: " + MN_SET_BT_Status + ": " + "bt_status_turning_on" );
                        UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_BT_Status, "bt_status_turning_on");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.e(TAG, "onReceive: " + MN_SET_BT_Status + ": " + "bt_status_on" );
                        UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_BT_Status, "bt_status_on");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.e(TAG, "onReceive: " + MN_SET_BT_Status + ": " + "bt_status_turning_off" );
                        UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_BT_Status, "bt_status_turning_off");
                        break;
                    default:
                        break;
                }
            } else if (intent.getAction().equals(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE,
                        BluetoothProfile.STATE_DISCONNECTED);
                //bluetooth connection value
                Log.e(TAG, "ACTION_CONNECTION_STATE_CHANGED：" + state);
                switch (state) {
                    case 2:
                        Log.e(TAG, "onReceive: " + MN_SET_BT_CONNECTION_STATUS + ": " + "bt_connection_status_connected" );
                        UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_BT_CONNECTION_STATUS, "bt_connection_status_connected");
                        break;
                    case 1:
                        Log.e(TAG, "onReceive: " + MN_SET_BT_CONNECTION_STATUS + ": " + "bt_connection_status_connecting" );
                        UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_BT_CONNECTION_STATUS, "bt_connection_status_connecting");
                        break;
                    case 0:
                        Log.e(TAG, "onReceive: " + MN_SET_BT_CONNECTION_STATUS + ": " + "bt_connection_status_disconnected" );
                        UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_BT_CONNECTION_STATUS, "bt_connection_status_disconnected");
                        break;
                    case 3:
                        Log.e(TAG, "onReceive: " + MN_SET_BT_CONNECTION_STATUS + ": " + "bt_connection_status_disconnecting" );
                        UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_BT_CONNECTION_STATUS, "bt_connection_status_disconnecting");
                        break;
                    default:
                        break;
                }
            }
        }
    };

    private void registerBluetoothReceiver(Context context) {
        Log.e(TAG, "registerBluetoothReceiver: " );
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        context.registerReceiver(mBluetoothReceiver, filter);
    }

    private void unregisterBluetootReceiver(Context context) {
        Log.e(TAG, "unregisterBluetootReceiver: " );
        if (mBluetoothReceiver != null) {
            context.unregisterReceiver(mBluetoothReceiver);
        }
    }


    /**
     * wifi receiver
     */
    private BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                String networkState = info.isConnected() ? "1" : "0";
                Log.e(TAG, "NETWORK_STATE_CHANGED_ACTION：" + networkState);
                if (networkState.equals("1")) {
                    Log.e(TAG, "onReceive: " + MN_SET_WF_Status + ": " + "wf_status_connected" );
                    UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_WF_Status, "wf_status_connected");
                } else if (networkState.equals("0")) {
                    Log.e(TAG, "onReceive: " + MN_SET_WF_Status + ": " + "wf_status_disconnected" );
                    UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_WF_Status, "wf_status_disconnected");
                }
            }
            if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                        WifiManager.WIFI_STATE_UNKNOWN);
                Log.e(TAG, "WIFI_STATE_CHANGED_ACTION：" + state);
                switch (state) {
                    case WifiManager.WIFI_STATE_ENABLING:
                        Log.e(TAG, "onReceive: " + MN_SET_WF_CONNECTION_STATUS + ": " + "wf_connection_status_enabling" );
                        UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_WF_CONNECTION_STATUS, "wf_connection_status_enabling");
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        Log.e(TAG, "onReceive: " + MN_SET_WF_CONNECTION_STATUS + ": " + "wf_connection_status_enabled" );
                        UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_WF_CONNECTION_STATUS, "wf_connection_status_enabled");
                        break;
                    case WifiManager.WIFI_STATE_DISABLED:
                        Log.e(TAG, "onReceive: " + MN_SET_WF_CONNECTION_STATUS + ": " + "wf_connection_status_disabled" );
                        UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_WF_CONNECTION_STATUS, "wf_connection_status_disabled");
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        Log.e(TAG, "onReceive: " + MN_SET_WF_CONNECTION_STATUS + ": " + "wf_connection_status_disabling" );
                        UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_WF_CONNECTION_STATUS, "wf_connection_status_disabling");
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        Log.e(TAG, "onReceive: " + MN_SET_BT_Status + ": " + "bt_status_off" );
                        UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_WF_CONNECTION_STATUS, "wf_connection_status_unknown");
                        break;
                    default:
                        break;
                }
            }
            if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 4);
                Log.e(TAG, MN_SET_RSSI_STATUS + ": " + level);
                UnityPlayer.UnitySendMessage(OBJECT_NAME, MN_SET_RSSI_STATUS, String.valueOf(level));
            }
        }
    };

    private void registerWifiReceiver(Context context) {
        Log.e(TAG, "registerWifiReceiver: " );
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(mWifiReceiver, intentFilter);
    }

    private void unregisterWifiReceiver(Context context) {
        Log.e(TAG, "unregisterWifiReceiver: " );
        if (mWifiReceiver != null) {
            context.unregisterReceiver(mWifiReceiver);
        }
    }
}
