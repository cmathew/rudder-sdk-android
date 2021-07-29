package com.rudderstack.android.sdk.core;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import static android.content.Context.TELEPHONY_SERVICE;

@RunWith(RobolectricTestRunner.class)
public class RudderNetworkBuilderTest {
    private RudderNetwork rudderNetwork;
    @Mock Application application;
    @Mock TelephonyManager telephonyManager;
    @Mock WifiManager wifi;
    @Mock BluetoothAdapter bluetoothAdapter;
    String networkOperatorName;
    MockedStatic<BluetoothAdapter> bluetoothAdapterMockedStatic;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        networkOperatorName = "mock_networkOperator";
        bluetoothAdapterMockedStatic = Mockito.mockStatic(BluetoothAdapter.class);
    }

    @After public void complete() {
        bluetoothAdapterMockedStatic.close();
    }

    @Test public void init() {
        // Carrier name and Cellular Status
        Mockito.when(application.getSystemService(TELEPHONY_SERVICE)).thenReturn(telephonyManager);
        // Carrier name
        Mockito.when(telephonyManager.getNetworkOperatorName()).thenReturn(networkOperatorName);

        // Wifi enabled
        Mockito.when(application.getSystemService(Context.WIFI_SERVICE)).thenReturn(wifi);
        Mockito.when(wifi.isWifiEnabled()).thenReturn(true);

        // Bluetooth
        bluetoothAdapterMockedStatic.when(BluetoothAdapter::getDefaultAdapter).thenReturn(bluetoothAdapter);
        Mockito.when(bluetoothAdapter.isEnabled()).thenReturn(true);
        Mockito.when(bluetoothAdapter.getState()).thenReturn(BluetoothAdapter.STATE_ON);

        // Cellular status
        Mockito.when(telephonyManager.getSimState()).thenReturn(TelephonyManager.SIM_STATE_READY);

        rudderNetwork = new RudderNetwork(application);
    }

    @Test public void initWhenCarrierAndWifi_isNotEnabled() {
        // Carrier name and Cellular Status
        Mockito.when(application.getSystemService(TELEPHONY_SERVICE)).thenReturn(null);

        // Wifi enabled
        Mockito.when(application.getSystemService(Context.WIFI_SERVICE)).thenReturn(null);

        // Bluetooth
        bluetoothAdapterMockedStatic.when(BluetoothAdapter::getDefaultAdapter).thenReturn(bluetoothAdapter);
        Mockito.when(bluetoothAdapter.isEnabled()).thenReturn(true);
        Mockito.when(bluetoothAdapter.getState()).thenReturn(BluetoothAdapter.STATE_ON);

        // Cellular status
//        Mockito.when(telephonyManager.getSimState()).thenReturn(TelephonyManager.SIM_STATE_READY);

        rudderNetwork = new RudderNetwork(application);
    }

    @Test public void initWhenBluetooth_isNotEnabled() {
        // Carrier name  and Cellular Status
        Mockito.when(application.getSystemService(TELEPHONY_SERVICE)).thenReturn(telephonyManager);
        // Carrier name
        Mockito.when(telephonyManager.getNetworkOperatorName()).thenReturn(networkOperatorName);
        // Wifi enabled
        Mockito.when(application.getSystemService(Context.WIFI_SERVICE)).thenReturn(wifi);
        Mockito.when(wifi.isWifiEnabled()).thenReturn(true);
        // Cellular status
        Mockito.when(telephonyManager.getSimState()).thenReturn(TelephonyManager.SIM_STATE_READY);

        // Bluetooth: 'isBluetoothEnabled = false'
        bluetoothAdapterMockedStatic.when(BluetoothAdapter::getDefaultAdapter).thenReturn(null);
        Mockito.when(bluetoothAdapter.isEnabled()).thenReturn(true);
        Mockito.when(bluetoothAdapter.getState()).thenReturn(BluetoothAdapter.STATE_ON);
        rudderNetwork = new RudderNetwork(application);

        // Bluetooth: 'isBluetoothEnabled = false'
        bluetoothAdapterMockedStatic.when(BluetoothAdapter::getDefaultAdapter).thenReturn(bluetoothAdapter);
        Mockito.when(bluetoothAdapter.isEnabled()).thenReturn(false);
        Mockito.when(bluetoothAdapter.getState()).thenReturn(BluetoothAdapter.STATE_ON);
        rudderNetwork = new RudderNetwork(application);

        // Bluetooth: 'isBluetoothEnabled = false'
        bluetoothAdapterMockedStatic.when(BluetoothAdapter::getDefaultAdapter).thenReturn(bluetoothAdapter);
        Mockito.when(bluetoothAdapter.isEnabled()).thenReturn(true);
        Mockito.when(bluetoothAdapter.getState()).thenReturn(BluetoothAdapter.STATE_OFF);
        rudderNetwork = new RudderNetwork(application);

        // Bluetooth: 'isBluetoothEnabled = true'
        bluetoothAdapterMockedStatic.when(BluetoothAdapter::getDefaultAdapter).thenReturn(bluetoothAdapter);
        Mockito.when(bluetoothAdapter.isEnabled()).thenReturn(true);
        Mockito.when(bluetoothAdapter.getState()).thenReturn(BluetoothAdapter.STATE_ON);
        rudderNetwork = new RudderNetwork(application);
    }

    @Test public void initWhenCellularStatus_isNotEnabled() {
        // Carrier name
        Mockito.when(telephonyManager.getNetworkOperatorName()).thenReturn(networkOperatorName);

        // Wifi enabled
        Mockito.when(application.getSystemService(Context.WIFI_SERVICE)).thenReturn(wifi);
        Mockito.when(wifi.isWifiEnabled()).thenReturn(true);

        // Bluetooth
        bluetoothAdapterMockedStatic.when(BluetoothAdapter::getDefaultAdapter).thenReturn(bluetoothAdapter);
        Mockito.when(bluetoothAdapter.isEnabled()).thenReturn(true);
        Mockito.when(bluetoothAdapter.getState()).thenReturn(BluetoothAdapter.STATE_ON);

        // Carrier name and Cellular Status
        Mockito.when(application.getSystemService(TELEPHONY_SERVICE)).thenReturn(null);
        rudderNetwork = new RudderNetwork(application);

        // Carrier name and Cellular Status
        Mockito.when(application.getSystemService(TELEPHONY_SERVICE)).thenReturn(telephonyManager);
        // Cellular status
        Mockito.when(telephonyManager.getSimState()).thenReturn(TelephonyManager.SIM_STATE_UNKNOWN);
        rudderNetwork = new RudderNetwork(application);

        // Carrier name and Cellular Status
        Mockito.when(application.getSystemService(TELEPHONY_SERVICE)).thenReturn(telephonyManager);
        // Cellular status
        Mockito.when(telephonyManager.getSimState()).thenReturn(TelephonyManager.SIM_STATE_READY);
        rudderNetwork = new RudderNetwork(application);
    }
}
