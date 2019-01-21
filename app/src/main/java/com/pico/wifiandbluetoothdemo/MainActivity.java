package com.pico.wifiandbluetoothdemo;

import android.app.Activity;
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
import com.unity3d.player.UnityPlayerNativeActivityPico;

public class MainActivity extends UnityPlayerNativeActivityPico {
    private static final String TAG = "---MainActivity---";
    public static Activity unityActivity = null;


//    private TextView tvBtSwitchState;
//    private TextView tvBtConnectState;
//
//    private TextView tvWfSwitchState;
//    private TextView tvWfConnectState;
//    private TextView tvWfSignalState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unityActivity = this;
//        setContentView(R.layout.activity_main);

//        tvBtSwitchState = findViewById(R.id.tv_bt_switch_state);
//        tvBtConnectState = findViewById(R.id.tv_bt_connect_state);
//
//        tvWfSwitchState = findViewById(R.id.tv_wf_switch_state);
//        tvWfConnectState = findViewById(R.id.tv_wf_connect_state);
//        tvWfSignalState = findViewById(R.id.tv_wf_signal_state);

        registerBluetoothReceiver();
        registerWifiReceiver();

    }

    /**
     * bluetooth receiver
     */
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
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothState", "bt_state_off");
//                        tvBtSwitchState.setText("off");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothState", "bt_state_turning_on");
//                        tvBtSwitchState.setText("turning on");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothState", "bt_state_on");
//                        tvBtSwitchState.setText("on");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothState", "bt_state_turning_off");
//                        tvBtSwitchState.setText("turning off");
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
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothConnectionState", "bt_connection_state_connected");
//                        tvBtConnectState.setText("connected");
                        break;
                    case 1:
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothConnectionState", "bt_connection_state_connecting");
//                        tvBtConnectState.setText("connecting");
                        break;
                    case 0:
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothConnectionState", "bt_connection_state_disconnected");
//                        tvBtConnectState.setText("disconnected");
                        break;
                    case 3:
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothConnectionState", "bt_connection_state_disconnecting");
//                        tvBtConnectState.setText("disconnecting");
                        break;
                    default:
                        break;
                }
            }
        }
    };

    private void registerBluetoothReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        registerReceiver(mBluetoothReceiver, filter);
    }

    private void unregisterBluetootReceiver() {
        if (mBluetoothReceiver != null) {
            unregisterReceiver(mBluetoothReceiver);
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
//                    tvWfConnectState.setText("connected");
                    UnityPlayer.UnitySendMessage("SetState", "setWifiState", "wf_state_connected");
                } else if (networkState.equals("0")) {
                    UnityPlayer.UnitySendMessage("SetState", "setWifiState", "wf_state_disconnected");
//                    tvWfConnectState.setText("has not network");
//                    tvWfSignalState.setText("x");
                }
            }
            if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                        WifiManager.WIFI_STATE_UNKNOWN);
                //wifi status：* @see #WIFI_STATE_DISABLED
                //     * @see #WIFI_STATE_DISABLING
                //     * @see #WIFI_STATE_ENABLED
                //     * @see #WIFI_STATE_ENABLING
                //     * @see #WIFI_STATE_UNKNOWN
                Log.e(TAG, "WIFI_STATE_CHANGED_ACTION：" + state);
                switch (state) {
                    case WifiManager.WIFI_STATE_ENABLING:
                        UnityPlayer.UnitySendMessage("SetState", "setWifiConnectionState", "wf_connection_state_enabling");
//                        tvWfSwitchState.setText("enabling");
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        UnityPlayer.UnitySendMessage("SetState", "setWifiConnectionState", "wf_connection_state_enabled");
//                        tvWfSwitchState.setText("enabled");
                        break;
                    case WifiManager.WIFI_STATE_DISABLED:
                        UnityPlayer.UnitySendMessage("SetState", "setWifiConnectionState", "wf_connection_state_disabled");
//                        tvWfSwitchState.setText("disabled");
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        UnityPlayer.UnitySendMessage("SetState", "setWifiConnectionState", "wf_connection_state_disabling");
//                        tvWfSwitchState.setText("disabling");
//                        tvWfSignalState.setText("x");
//                        tvWfConnectState.setText("x");
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        UnityPlayer.UnitySendMessage("SetState", "setWifiConnectionState", "wf_connection_state_unknown");
//                        tvWfSwitchState.setText("unknown");
//                        tvWfSignalState.setText("x");
                        break;
                    default:
                        break;
                }
            }
            if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 4);
                Log.e(TAG, "RSSI_CHANGED_ACTION：" + level);
                UnityPlayer.UnitySendMessage("SetState", "setWifiRssiState", String.valueOf(level));
//                tvWfSignalState.setText(level + "");
            }
        }
    };

    private void registerWifiReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, intentFilter);
    }

    private void unregisterWifiReceiver() {
        if (mWifiReceiver != null) {
            unregisterReceiver(mWifiReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBluetootReceiver();
        unregisterWifiReceiver();
    }
}
