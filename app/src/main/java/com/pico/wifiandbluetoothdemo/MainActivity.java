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
     * 蓝牙广播
     */
    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: blue " + intent.getAction());
            if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                Bundle stateBundle = intent.getExtras();
                int message = stateBundle.getInt(BluetoothAdapter.EXTRA_STATE);
                //蓝牙的开关状态：四种：on oning off offing
                Log.e(TAG, "ACTION_STATE_CHANGED：" + message);
                switch (message) {
                    case BluetoothAdapter.STATE_OFF:
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothState", "bt_state_off");
//                        tvBtSwitchState.setText("关闭");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothState", "bt_state_turing_on");
//                        tvBtSwitchState.setText("正在打开");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothState", "bt_state_on");
//                        tvBtSwitchState.setText("打开");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothState", "bt_state_turing_off");
//                        tvBtSwitchState.setText("正在关闭");
                        break;
                    default:
                        break;
                }
            } else if (intent.getAction().equals(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE,
                        BluetoothProfile.STATE_DISCONNECTED);
                //蓝牙的连接状态
                Log.e(TAG, "ACTION_CONNECTION_STATE_CHANGED：" + state);
                switch (state) {
                    case 2:
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothConnectionState", "bt_connection_state_connected");
//                        tvBtConnectState.setText("已连接");
                        break;
                    case 1:
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothConnectionState", "bt_connection_state_connecting");
//                        tvBtConnectState.setText("正在连接");
                        break;
                    case 0:
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothConnectionState", "bt_connection_state_disconnected");
//                        tvBtConnectState.setText("已断开");
                        break;
                    case 3:
                        UnityPlayer.UnitySendMessage("SetState", "setBluetoothConnectionState", "bt_connection_state_disconnecting");
//                        tvBtConnectState.setText("正在断开");
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
     * wifi广播
     */
    private BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                String networkState = info.isConnected() ? "1" : "0";
                Log.e(TAG, "NETWORK_STATE_CHANGED_ACTION：" + networkState);
                if (networkState.equals("1")) {
//                    tvWfConnectState.setText("已连接");
                    UnityPlayer.UnitySendMessage("SetState", "setWifiState", "wf__state_connected");
                } else if (networkState.equals("0")) {
                    UnityPlayer.UnitySendMessage("SetState", "setWifiState", "wf_state_disconnected");
//                    tvWfConnectState.setText("没有网络");
//                    tvWfSignalState.setText("x");
                }
            }
            if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                        WifiManager.WIFI_STATE_UNKNOWN);
                //wifi 状态：五种，     * @see #WIFI_STATE_DISABLED
                //     * @see #WIFI_STATE_DISABLING
                //     * @see #WIFI_STATE_ENABLED
                //     * @see #WIFI_STATE_ENABLING
                //     * @see #WIFI_STATE_UNKNOWN
                Log.e(TAG, "WIFI_STATE_CHANGED_ACTION：" + state);
                switch (state) {
                    case WifiManager.WIFI_STATE_ENABLING:
                        UnityPlayer.UnitySendMessage("SetState", "setWifiConnectionState", "wf_connection_state_enabling");
//                        tvWfSwitchState.setText("正在打开");
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        UnityPlayer.UnitySendMessage("SetState", "setWifiConnectionState", "wf_connection_state_enabled");
//                        tvWfSwitchState.setText("打开");
                        break;
                    case WifiManager.WIFI_STATE_DISABLED:
                        UnityPlayer.UnitySendMessage("SetState", "setWifiConnectionState", "wf_connection_state_disabled");
//                        tvWfSwitchState.setText("关闭");
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        UnityPlayer.UnitySendMessage("SetState", "setWifiConnectionState", "wf_connection_state_disabling");
//                        tvWfSwitchState.setText("正在关闭");
//                        tvWfSignalState.setText("x");
//                        tvWfConnectState.setText("x");
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        UnityPlayer.UnitySendMessage("SetState", "setWifiConnectionState", "wf_connection_state_unknown");
//                        tvWfSwitchState.setText("未知");
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
