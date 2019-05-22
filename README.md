# WifiandBluetoothReceiver

JAR file and demo APK are in /resource.    
Note: Regarding JAR file creation and usage, please refer to [the Guideline](https://github.com/picoxr/support/blob/master/How%20to%20Use%20JAR%20file%20in%20Unity%20project%20on%20Pico%20device.docx)

## Introduction
Receive broadcasts of bluetooth and wifi switches and connection status changes and pass the status to Unity.

## Class Name
```
android:name="com.pico.wifiandbluetoothdemo.WBStatusClass"
```

## Permission
```
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.BLUETOOTH"/>
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
```

## Interface
**Usage**
This JAR file send message to Unity app, so in Unity project, it's required to implmement these methods in a script and set the script as a component of a GameObject named "GetStatus" to receive the message from JAR files.
Please refer to section 3 (Method 3) of the Guideline above.

**Method List** 
```   
1. GetBluetoothStatus(string bluetoothStatus){};
    bluetoothStatus: bt_status_off
                     bt_status_turning_on
                     bt_status_on
                     bt_status_turning_off

2. GetBluetoothConnectionStatus(string bluetoothConnectionStatus){};
    bluetoothConnectionStatus: bt_connection_status_connected
                               bt_connection_status_connecting
                               bt_connection_status_disconnected
                               bt_connection_status_disconnecting

3. GetWifiStatus(string wifiStatus){};
    wifiStatus: wf_status_connected
                wf_status_disconnected

4. GetWifiConnectionStatus(string wifiConnectionStatus){};
    wifiConnectionStatus: wf_connection_status_enabling
                          wf_connection_status_enabled
                          wf_connection_status_disabled
                          wf_connection_status_disabling
                          wf_connection_status_unknown

5. GetWifiRssiStatus(string wifiRssiStatus){};
    wifiRssiStatus: 1 to 4, bigger num means better signal strength.
	
6. You need to register BluetoothReceiver and WifiReceiver in Start() and unregister BluetootReceiver and WifiReceiver in OnDestroy().
	//register and unregister bluetoothReceiver
	void registerBluetoothReceiver(Context context)
	unregisterBluetootReceiver(Context context)
	
	//register and unregister wifiReceiver
	void registerWifiReceiver(Context context)
	void unregisterWifiReceiver(Context context)
```

## Sample Code
```
public Text wifiText;
AndroidJavaObject ajo;
AndroidJavaObject context;

// Use this for initialization
void Start () {
    ajo = new AndroidJavaObject("com.pico.wifiandbluetoothdemo.WBStatusClass");
    context = new AndroidJavaClass("com.unity3d.player.UnityPlayer").GetStatic<AndroidJavaObject>("currentActivity");

    ajo.Call("registerWifiReceiver", context);
}

//This method will be execute when wifi status changes.
public void GetWifiStatus(string s)
{
    wifiText.text = s;
}

private void OnDestroy()
{
    ajo.Call("unregisterWifiReceiver", context);
}
```

