package com.android.settings.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothPermissionRequest extends BroadcastReceiver {
    public Context mContext;
    public BluetoothDevice mDevice;
    public int mRequestType;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        this.mContext = context;
        String action = intent.getAction();
        if (action == null) {
            Log.d("BluetoothPermissionRequest", "onReceive :: Intent.getAction() is null");
            return;
        }
        Log.d("BluetoothPermissionRequest", "onReceive :: getAction = ".concat(action));
        if (!"android.bluetooth.device.action.CONNECTION_ACCESS_REQUEST".equals(action)
                || ((UserManager) context.getSystemService("user")).isManagedProfile()) {
            return;
        }
        this.mDevice =
                (BluetoothDevice)
                        intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        boolean z = true;
        int intExtra = intent.getIntExtra("android.bluetooth.device.extra.ACCESS_REQUEST_TYPE", 1);
        this.mRequestType = intExtra;
        if (intExtra == 4) {
            if ("1".equals(SystemProperties.get("service.bt.security.policy.mode"))) {
                Log.e(
                        "BluetoothPermissionRequest",
                        "SAP service is disabled; IT Policy is Handsfree Only");
                sendReplyIntentToReceiver(false);
                return;
            }
            int callState = ((TelephonyManager) context.getSystemService("phone")).getCallState();
            if (callState != 0) {
                Log.d(
                        "BluetoothPermissionRequest",
                        "reject SAP Connection Request during ongoing call : " + callState);
                sendReplyIntentToReceiver(false);
                return;
            }
        }
        if (this.mDevice == null) {
            Log.e(
                    "BluetoothPermissionRequest",
                    "Remote device info is null, so dismiss the BT permission activity");
            sendReplyIntentToReceiver(false);
        } else {
            int i = this.mRequestType;
            if (i == 2 || i == 3 || i == 4) {
                LocalBluetoothManager localBluetoothManager =
                        LocalBluetoothManager.getInstance(this.mContext, Utils.mOnInitCallback);
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                        localBluetoothManager.mCachedDeviceManager;
                CachedBluetoothDevice findDevice =
                        cachedBluetoothDeviceManager.findDevice(this.mDevice);
                if (!localBluetoothManager.mLocalAdapter.mAdapter.isEnabled()) {
                    sendReplyIntentToReceiver(false);
                } else if (findDevice == null
                        && cachedBluetoothDeviceManager.addDevice(this.mDevice) == null) {
                    Log.e("BluetoothPermissionRequest", "checkUserChoice() :: No cahced device!");
                } else {
                    int i2 = this.mRequestType;
                    if (i2 == 2) {
                        int phonebookAccessPermission = this.mDevice.getPhonebookAccessPermission();
                        if (phonebookAccessPermission == 0) {
                            if (SystemProperties.get("net.mirrorlink.on").equals("1")) {
                                sendReplyIntentToReceiver(false);
                                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                        "checkUserChoice(): returning ",
                                        "BluetoothPermissionRequest",
                                        z);
                            }
                            z = false;
                            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                    "checkUserChoice(): returning ",
                                    "BluetoothPermissionRequest",
                                    z);
                        } else {
                            if (phonebookAccessPermission == 1) {
                                sendReplyIntentToReceiver(true);
                            } else if (phonebookAccessPermission == 2) {
                                sendReplyIntentToReceiver(false);
                            } else {
                                WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                                        .m(
                                                phonebookAccessPermission,
                                                "Bad phonebookPermission: ",
                                                "BluetoothPermissionRequest");
                                z = false;
                            }
                            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                    "checkUserChoice(): returning ",
                                    "BluetoothPermissionRequest",
                                    z);
                        }
                    } else if (i2 == 3) {
                        int messageAccessPermission = this.mDevice.getMessageAccessPermission();
                        if (messageAccessPermission == 0) {
                            if (SystemProperties.get("net.mirrorlink.on").equals("1")) {
                                sendReplyIntentToReceiver(false);
                                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                        "checkUserChoice(): returning ",
                                        "BluetoothPermissionRequest",
                                        z);
                            }
                            z = false;
                            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                    "checkUserChoice(): returning ",
                                    "BluetoothPermissionRequest",
                                    z);
                        } else {
                            if (messageAccessPermission == 1) {
                                sendReplyIntentToReceiver(true);
                            } else if (messageAccessPermission == 2) {
                                sendReplyIntentToReceiver(false);
                            } else {
                                WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                                        .m(
                                                messageAccessPermission,
                                                "Bad messagePermission: ",
                                                "BluetoothPermissionRequest");
                                z = false;
                            }
                            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                    "checkUserChoice(): returning ",
                                    "BluetoothPermissionRequest",
                                    z);
                        }
                    } else {
                        if (i2 == 4) {
                            int simAccessPermission = this.mDevice.getSimAccessPermission();
                            if (simAccessPermission != 0) {
                                if (simAccessPermission == 1) {
                                    sendReplyIntentToReceiver(true);
                                } else if (simAccessPermission == 2) {
                                    sendReplyIntentToReceiver(false);
                                } else {
                                    WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                                            .m(
                                                    simAccessPermission,
                                                    "Bad simPermission: ",
                                                    "BluetoothPermissionRequest");
                                }
                                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                        "checkUserChoice(): returning ",
                                        "BluetoothPermissionRequest",
                                        z);
                            } else if (SystemProperties.get("net.mirrorlink.on").equals("1")) {
                                sendReplyIntentToReceiver(false);
                                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                        "checkUserChoice(): returning ",
                                        "BluetoothPermissionRequest",
                                        z);
                            }
                        }
                        z = false;
                        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                "checkUserChoice(): returning ", "BluetoothPermissionRequest", z);
                    }
                }
            } else {
                z = false;
            }
        }
        if (z) {
            return;
        }
        Intent intent2 = new Intent(action);
        intent2.setClass(context, BluetoothPermissionActivity.class);
        intent2.setFlags(402653184);
        intent2.setType(Integer.toString(this.mRequestType));
        intent2.putExtra("android.bluetooth.device.extra.ACCESS_REQUEST_TYPE", this.mRequestType);
        intent2.putExtra("android.bluetooth.device.extra.DEVICE", this.mDevice);
        Utils.makeNotiSound(context);
        context.startActivity(intent2);
    }

    public final void sendReplyIntentToReceiver(boolean z) {
        String str;
        String str2;
        int i = this.mRequestType;
        if (i == 2) {
            str = "PBAP";
        } else if (i == 3) {
            str = "MAP";
        } else if (i == 4) {
            str = "SAP";
        } else {
            str = ApnSettings.MVNO_NONE + this.mRequestType;
        }
        BluetoothDevice bluetoothDevice = this.mDevice;
        if (bluetoothDevice == null) {
            str2 = "[unknown]";
        } else {
            str2 = bluetoothDevice.getName() + " (" + this.mDevice.getAddress() + ")";
        }
        Utils.insertMDMLog(
                this.mContext,
                true,
                Process.myPid(),
                "BluetoothPermission",
                String.format(
                        z
                                ? "User authorization of profile %s for device %s was granted"
                                : "User authorization of profile %s for device %s was rejected",
                        str,
                        str2),
                UserHandle.getCallingUserId());
        Intent intent = new Intent("android.bluetooth.device.action.CONNECTION_ACCESS_REPLY");
        intent.putExtra("android.bluetooth.device.extra.CONNECTION_ACCESS_RESULT", z ? 1 : 2);
        intent.putExtra("android.bluetooth.device.extra.DEVICE", this.mDevice);
        intent.putExtra("android.bluetooth.device.extra.ACCESS_REQUEST_TYPE", this.mRequestType);
        this.mContext.sendBroadcast(intent, "android.permission.BLUETOOTH_CONNECT");
    }
}
