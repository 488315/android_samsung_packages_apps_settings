package com.samsung.android.settings.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothStatusReceiver extends BroadcastReceiver {
    public CachedBluetoothDevice mCachedDevice;
    public CachedBluetoothDeviceManager mCachedDeviceManager;
    public final AnonymousClass1 mHandler =
            new Handler() { // from class:
                            // com.samsung.android.settings.bluetooth.BluetoothStatusReceiver.1
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    if (message.what != 1) {
                        return;
                    }
                    Log.d("BluetoothStatusReceiver", "mHandler :: DEVICE_CONNECTION");
                    BluetoothStatusReceiver bluetoothStatusReceiver = BluetoothStatusReceiver.this;
                    if (bluetoothStatusReceiver.mCachedDevice == null) {
                        Log.e(
                                "BluetoothStatusReceiver",
                                "mHandler :: cachedBluetoothDevice is not setted.");
                        return;
                    }
                    String string = message.getData().getString("package_name");
                    boolean z = message.getData().getBoolean("is_start_activity");
                    byte[] byteArray = message.getData().getByteArray("mf_data");
                    CachedBluetoothDevice cachedBluetoothDevice =
                            bluetoothStatusReceiver.mCachedDevice;
                    if (cachedBluetoothDevice.mManufacturerData == null && byteArray != null) {
                        cachedBluetoothDevice.setManufacturerData(byteArray);
                    }
                    CachedBluetoothDevice cachedBluetoothDevice2 =
                            bluetoothStatusReceiver.mCachedDevice;
                    if (cachedBluetoothDevice2.shouldLaunchGM(string, z)) {
                        return;
                    }
                    if (cachedBluetoothDevice2.mBondState == 10) {
                        cachedBluetoothDevice2.startPairing();
                        return;
                    }
                    cachedBluetoothDevice2.checkLEConnectionGuide(true, false);
                    cachedBluetoothDevice2.mConnectAttempted = SystemClock.elapsedRealtime();
                    cachedBluetoothDevice2.connectDevice();
                }
            };
    public LocalBluetoothAdapter mLocalBluetoothAdapter;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) {
            Log.d("BluetoothStatusReceiver", "Received null intent");
            return;
        }
        Log.d("BluetoothStatusReceiver", "Received :: ".concat(action));
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        if (localBluetoothManager == null) {
            Log.d(
                    "BluetoothStatusReceiver",
                    "Error: mLocalBluetoothManager not supported by system");
            return;
        }
        LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
        this.mLocalBluetoothAdapter = localBluetoothAdapter;
        if (localBluetoothAdapter == null) {
            Log.d(
                    "BluetoothStatusReceiver",
                    "Error: mLocalBluetoothAdapter not supported by system");
            return;
        }
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                localBluetoothManager.mCachedDeviceManager;
        this.mCachedDeviceManager = cachedBluetoothDeviceManager;
        if (cachedBluetoothDeviceManager == null) {
            Log.d("BluetoothStatusReceiver", "Error: mCachedDeviceManager not supported by system");
            return;
        }
        if (action.equals("com.samsung.android.action.BLUETOOTH_DEVICE_FROM_APP")) {
            Log.d(
                    "BluetoothStatusReceiver",
                    "For launch gearmanager from non-system app ::"
                        + " com.samsung.android.action.BLUETOOTH_DEVICE_FROM_APP");
            if (intent.getExtras() != null) {
                Log.d(
                        "BluetoothStatusReceiver",
                        "For launch gearmanager from non-system app :: sendBroadcast()");
                this.mCachedDevice = null;
                String stringExtra = intent.getStringExtra("MAC");
                byte[] byteArrayExtra = intent.getByteArrayExtra("DATA");
                boolean booleanExtra = intent.getBooleanExtra("IS_START_ACTIVITY", false);
                String stringExtra2 = intent.getStringExtra("request_app_package_name");
                String stringExtra3 = intent.getStringExtra("MODEL_NAME");
                Log.d("BluetoothStatusReceiver", "data = " + Arrays.toString(byteArrayExtra));
                BluetoothDevice remoteDevice =
                        this.mLocalBluetoothAdapter.mAdapter.getRemoteDevice(stringExtra);
                if (remoteDevice == null) {
                    return;
                }
                CachedBluetoothDevice findDevice =
                        this.mCachedDeviceManager.findDevice(remoteDevice);
                this.mCachedDevice = findDevice;
                if (findDevice == null) {
                    CachedBluetoothDevice addDevice =
                            this.mCachedDeviceManager.addDevice(remoteDevice);
                    this.mCachedDevice = addDevice;
                    if (addDevice == null) {
                        Log.e(
                                "BluetoothStatusReceiver",
                                "Error: Failed to get cachedBluetoothDevice instance.");
                        return;
                    }
                }
                if (!TextUtils.isEmpty(stringExtra3)) {
                    this.mCachedDevice.mModelName = stringExtra3;
                }
                List profiles = this.mCachedDevice.getProfiles();
                boolean z = true;
                for (int i = 0; i < profiles.size(); i++) {
                    LocalBluetoothProfile localBluetoothProfile =
                            (LocalBluetoothProfile) profiles.get(i);
                    if (localBluetoothProfile == null || !localBluetoothProfile.isProfileReady()) {
                        z = false;
                    }
                }
                Message message = new Message();
                message.what = 1;
                message.getData().putString("package_name", stringExtra2);
                message.getData().putBoolean("is_start_activity", booleanExtra);
                message.getData().putByteArray("mf_data", byteArrayExtra);
                if (z) {
                    sendMessage(message);
                } else {
                    sendMessageDelayed(message, 1000L);
                }
            }
        }
    }
}
