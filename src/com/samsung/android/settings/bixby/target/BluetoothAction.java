package com.samsung.android.settings.bixby.target;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothDump;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.settings.bixby.utils.BixbyUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BluetoothAction extends Action {
    public final BluetoothAdapter mAdapter;
    public final LocalBluetoothManager mLocalBluetoothManager;

    public BluetoothAction(Context context, Bundle bundle) {
        super(context, bundle);
        this.mAdapter = null;
        this.mLocalBluetoothManager = null;
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mLocalBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doConnectAction() {
        if (!this.mAdapter.isEnabled()) {
            this.mAdapter.enable();
        }
        return gotoBluetoothSettings();
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetAction() {
        return Action.createResult(this.mAdapter.isEnabled() ? "true" : "false");
    }

    /* JADX WARN: Removed duplicated region for block: B:116:0x02b6  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x02c3  */
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle doGetEntryListAction() {
        /*
            Method dump skipped, instructions count: 775
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bixby.target.BluetoothAction.doGetEntryListAction():android.os.Bundle");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        return gotoBluetoothSettings();
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSetAction() {
        String str;
        String value = getValue();
        DialogFragment$$ExternalSyntheticOutline0.m(
                "doSetAction() :: newValue - ", value, "BluetoothAction");
        boolean isEnabled = this.mAdapter.isEnabled();
        int i = 0;
        String str2 = "fail";
        if ("true".equals(value)) {
            if (isEnabled) {
                str = "already_on";
            } else {
                this.mAdapter.enable();
                while (i < 20) {
                    BluetoothAdapter bluetoothAdapter = this.mAdapter;
                    if (bluetoothAdapter == null) {
                        break;
                    }
                    if (12 == bluetoothAdapter.getState()) {
                        str2 = "success";
                        break;
                    }
                    SystemClock.sleep(250L);
                    i++;
                }
                str = str2;
            }
        } else if (!"false".equals(value)) {
            str = null;
        } else if (isEnabled) {
            this.mAdapter.disable();
            while (i < 20) {
                BluetoothAdapter bluetoothAdapter2 = this.mAdapter;
                if (bluetoothAdapter2 == null) {
                    break;
                }
                if (10 == bluetoothAdapter2.getState()) {
                    str2 = "success";
                    break;
                }
                SystemClock.sleep(250L);
                i++;
            }
            str = str2;
        } else {
            str = "already_off";
        }
        return Action.createResult(str);
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSupportAction() {
        return Action.createResult("true");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doTryToConnectAction() {
        String str;
        if (!this.mAdapter.isEnabled()) {
            return Action.createResult("bt_is_off");
        }
        BluetoothDevice remoteDevice = this.mAdapter.getRemoteDevice(getValue());
        CachedBluetoothDevice findDevice =
                this.mLocalBluetoothManager.mCachedDeviceManager.findDevice(remoteDevice);
        if (findDevice == null) {
            Log.w("BluetoothAction", "cachedDevice is null, it can't try to connect");
            str = "not_found";
        } else if (findDevice.isConnected()) {
            str = "already_connected";
        } else {
            BluetoothDump.BtLog(
                    "BluetoothAction -- doTryToConnectAction: "
                            + remoteDevice.getAddressForLogging());
            findDevice.connect$1();
            str = SignalSeverity.NONE;
        }
        String str2 = SignalSeverity.NONE.equals(str) ? "success" : "fail";
        Bundle bundle = new Bundle();
        bundle.putString("result", str2);
        bundle.putString("detail_reason", str);
        return bundle;
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doTryToDisconnectAction() {
        String str;
        if (!this.mAdapter.isEnabled()) {
            return Action.createResult("bt_is_off");
        }
        BluetoothDevice remoteDevice = this.mAdapter.getRemoteDevice(getValue());
        CachedBluetoothDevice findDevice =
                this.mLocalBluetoothManager.mCachedDeviceManager.findDevice(remoteDevice);
        if (findDevice == null) {
            Log.w("BluetoothAction", "cachedDevice is null, it can't try to disconnect");
            str = "not_found";
        } else if (findDevice.isConnected()) {
            BluetoothDump.BtLog(
                    "BluetoothAction -- doTryToDisconnectAction: "
                            + remoteDevice.getAddressForLogging());
            findDevice.disconnect();
            str = SignalSeverity.NONE;
        } else {
            str = "already_disconnected";
        }
        String str2 = SignalSeverity.NONE.equals(str) ? "success" : "fail";
        Bundle bundle = new Bundle();
        bundle.putString("result", str2);
        bundle.putString("detail_reason", str);
        return bundle;
    }

    /* JADX WARN: Code restructure failed: missing block: B:130:0x0133, code lost:

       if (r0 != 0) goto L16;
    */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0139 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x013c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getDeviceType(android.bluetooth.BluetoothDevice r17, byte[] r18) {
        /*
            Method dump skipped, instructions count: 483
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bixby.target.BluetoothAction.getDeviceType(android.bluetooth.BluetoothDevice,"
                    + " byte[]):java.lang.String");
    }

    public final Bundle gotoBluetoothSettings() {
        String str;
        Log.d("BluetoothAction", "gotoBluetoothSettings()");
        Intent intent = new Intent();
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$BluetoothSettingsActivity");
        intent.setAction("android.settings.BLUETOOTH_SETTINGS");
        intent.setFlags(268468224);
        Integer taskId = getTaskId();
        Log.d("BluetoothAction", "gotoBluetoothSettings() :: TargetTask is existed");
        com.android.settings.Utils.setTaskIdToIntent(intent, taskId);
        try {
            launchSettings(
                    intent,
                    com.android.settings.Utils.isDesktopModeEnabled(this.mContext)
                            ? BixbyUtils.getDeXDisplay(this.mContext)
                            : null);
            str = "success";
        } catch (ActivityNotFoundException unused) {
            Log.e(
                    "BluetoothAction",
                    "ActivityNotFoundException :: Can not found BluetoothSettings");
            str = "not_supported_device";
        }
        return Action.createResult(str);
    }
}
