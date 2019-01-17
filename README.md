# WifiandBluetoothReceiver

Note: Regarding java package creation and usege, please refer to [the Guideline](https://github.com/PicoSupport/PicoSupport/blob/master/How_to_use_JAR_file_in_Unity_project_on_Pico_device.docx)

## Introduction
Receive broadcasts of bluetooth and wifi switches and connection status changes and pass the status to Unity

## Class Name
android:name="com.pico.wifiandbluetoothdemo.MainActivity"

## Permission
```
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.BLUETOOTH"/>
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
```

## Interface
Usage: this JAR file send message to Unity app, so in Unity project, it's required to implmement these methods in a script and set the script as a component of a GameObject named "setState" to receive the message from JAR files.
Please refer to section 3 (Method 3) of the Guideline above.

Method List

```
setBluetoothState(string bluetoothState){};
setBluetoothConnectionState(string bluetoothConnectionState){};
setWifiState(string wifiState){}; 
setWifiConnectionState(string wifiConnectionState){};
setWifiRssiState(string wifiRssiState){};
```
Message List<br>
\>>bluetoothState:
bt_state_off  
bt_state_turning_on 
bt_state_on 
bt_state_turning_off
\>>bluetoothConnectionState
bt_connection_state_connected 
bt_connection_state_connecting  
bt_connection_state_disconnected  
bt_connection_state_disconnecting

\>>wifiState
wf__state_connected 
wf_state_disconnected 
\>>wifiConnectionState
wf_connection_state_enabling  
wf_connection_state_enabled 
wf_connection_state_disabled  
wf_connection_state_disabling 
wf_connection_state_unknown

\>>wifiRssiState
String.valueOf(level)



