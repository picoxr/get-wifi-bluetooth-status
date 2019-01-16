# WifiandBluetoothReceiver Instructions

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
Write these methods in a script, and set the script as a component of a GameObject named setState.
refer to section 3 of the Guideline above.

setBluetoothState(string bluetoothState){};
setBluetoothConnectionState(string bluetoothConnectionState){};
setWifiState(string wifiState){};
setWifiConnectionState(string wifConnectionState){};
setWifiRssiState(string wifiRssiState){};
