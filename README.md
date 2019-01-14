<p align="right"><a href="https://github.com/PicoSupport/PicoSupport" target="_blank"> <img src="https://github.com/PicoSupport/PicoSupport/blob/master/Assets/home.png" width="20"/> </a></p>

# WifiandBluetoothReceiver Instructions

Note: Regarding java package creation and usege, please refer to [the Guideline](https://github.com/PicoSupport/PicoSupport/blob/master/How_to_use_JAR_file_in_Unity_project_on_Pico_device.docx)

# Introduce

Receive broadcasts of bluetooth and wifi switches and connection status changes and pass the status to Unity

Use Method

1.Create a new GameObject in the unity project and name it SetState

2.Mount the new script on SetState, and the script name is arbitrary

3.Create five new methods in the script and implement them

```
setBluetoothState(string bluetoothState){};
setBluetoothConnectionState(string bluetoothConnectionState){};
setWifiState(string wifiState){};
setWifiConnectionState(string wifConnectionState){};
setWifiRssiState(string wifiRssiState){};
```

4.Add permissions to the Manifest

```
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.BLUETOOTH"/>
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
```

