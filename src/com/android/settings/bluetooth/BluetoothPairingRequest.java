package com.android.settings.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothDump;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothPairingRequest extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        int intExtra;
        String action = intent.getAction();
        if (action == null) {
            Log.d("BluetoothPairingRequest", "onReceive :: Intent.getAction() is return null");
            return;
        }
        Log.d("BluetoothPairingRequest", "onReceive :: getAction = ".concat(action));
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        if (localBluetoothManager == null) {
            return;
        }
        BluetoothDevice bluetoothDevice =
                (BluetoothDevice)
                        intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        boolean z = true;
        if (!action.equals("android.bluetooth.device.action.PAIRING_REQUEST")) {
            if (!TextUtils.equals(action, "android.bluetooth.action.CSIS_SET_MEMBER_AVAILABLE")
                    || bluetoothDevice == null
                    || (intExtra = intent.getIntExtra("android.bluetooth.extra.CSIS_GROUP_ID", -1))
                            == -1) {
                return;
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                    localBluetoothManager.mCachedDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                if (cachedBluetoothDeviceManager.mOngoingSetMemberPair != null) {
                    Log.d(
                            "CachedBluetoothDeviceManager",
                            "shouldPairByCsip: mOngoingSetMemberPair != null");
                } else if (bluetoothDevice.getBondState() != 10) {
                    Log.d(
                            "CachedBluetoothDeviceManager",
                            "shouldPairByCsip: bondState = " + bluetoothDevice.getBondState());
                } else {
                    Log.d(
                            "CachedBluetoothDeviceManager",
                            "Bond " + bluetoothDevice.getName() + " by CSIP");
                    cachedBluetoothDeviceManager.mOngoingSetMemberPair = bluetoothDevice;
                    cachedBluetoothDeviceManager.mOngoingSetMemberGroupId = intExtra;
                }
                z = false;
            }
            Log.d(
                    "BluetoothPairingRequest",
                    "onReceive: device = " + bluetoothDevice.getAddressForLogging());
            Log.d(
                    "BluetoothPairingRequest",
                    "onReceive: shouldPairByCsip = " + z + ", groupId = " + intExtra);
            BluetoothDump.BtLog(
                    "BluetoothPairingRequest -- onReceive: device = "
                            + bluetoothDevice.getAddressForLogging()
                            + ", shouldPairByCsip = "
                            + z
                            + ", groupId = "
                            + intExtra);
            if (z) {
                BluetoothDump.BtLog("BluetoothPairingRequest -- onReceive: auto createBond");
                BluetoothAdapter adapter =
                        ((BluetoothManager) context.getSystemService(BluetoothManager.class))
                                .getAdapter();
                if (adapter != null && adapter.isBondingState()) {
                    BluetoothDump.BtLog(
                            "BluetoothPairingRequest -- onReceive: isBondingState. Can not"
                                + " createBond");
                    cachedBluetoothDeviceManager.setOngoingSetMemberPair();
                    return;
                } else {
                    if (bluetoothDevice.createBond(2)) {
                        return;
                    }
                    cachedBluetoothDeviceManager.setOngoingSetMemberPair();
                    return;
                }
            }
            return;
        }
        if (bluetoothDevice == null || bluetoothDevice.getBondState() == 12) {
            Log.e(
                    "BluetoothPairingRequest",
                    "onReceive :: mDevice is null or mDevice bonded already.");
            return;
        }
        if (!bluetoothDevice.isBondingInitiatedLocally()
                && !localBluetoothManager.isForegroundActivity()
                && !localBluetoothManager.isForegroundActivityQP()) {
            String string =
                    context.getSharedPreferences("bluetooth_blocking_device", 0)
                            .getString("blocking_device_list", ApnSettings.MVNO_NONE);
            if (!string.equals(ApnSettings.MVNO_NONE)) {
                Object replace = bluetoothDevice.getAddress().replace(":", ApnSettings.MVNO_NONE);
                for (String str : string.split(";")) {
                    String[] split = str.split(",");
                    if (split.length == 5 && split[0].equals(replace)) {
                        try {
                            if (Integer.parseInt(split[4]) == 2) {
                                Log.e(
                                        "BluetoothPairingRequest",
                                        "Drop the pairing request for blocked device");
                                bluetoothDevice.cancelBondProcess();
                                Log.d("BluetoothPairingRequest", "Bonding blocked");
                                BluetoothDump.BtLog(
                                        "BluetoothPairingRequest -- Pairing req is blocked for "
                                                + bluetoothDevice.getAlias());
                                return;
                            }
                        } catch (NumberFormatException unused) {
                            continue;
                        }
                    }
                }
            }
        }
        int intExtra2 =
                intent.getIntExtra(
                        "android.bluetooth.device.extra.PAIRING_VARIANT", Integer.MIN_VALUE);
        StringBuilder m =
                ListPopupWindow$$ExternalSyntheticOutline0.m(
                        intExtra2, "onReceive: type = ", ", canBondWithoutDialog = ");
        m.append(bluetoothDevice.canBondWithoutDialog());
        m.append(", isOngoingPairByCsip = ");
        m.append(localBluetoothManager.mCachedDeviceManager.isOngoingPairByCsip(bluetoothDevice));
        Log.d("BluetoothPairingRequest", m.toString());
        if ((intExtra2 == 2 || intExtra2 == 3)
                && (bluetoothDevice.canBondWithoutDialog()
                        || localBluetoothManager.mCachedDeviceManager.isOngoingPairByCsip(
                                bluetoothDevice))) {
            bluetoothDevice.setPairingConfirmation(true);
            return;
        }
        Intent intent2 = new Intent();
        intent2.setClass(context, BluetoothPairingDialog.class);
        intent2.putExtra("android.bluetooth.device.extra.DEVICE", bluetoothDevice);
        intent2.putExtra("android.bluetooth.device.extra.PAIRING_VARIANT", intExtra2);
        if (intExtra2 == 2 || intExtra2 == 4 || intExtra2 == 5) {
            int intExtra3 =
                    intent.getIntExtra(
                            "android.bluetooth.device.extra.PAIRING_KEY", Integer.MIN_VALUE);
            if (intExtra3 == Integer.MIN_VALUE) {
                Log.e(
                        "BluetoothPairingRequest",
                        "received Invalid Passkey or pin received, will cancel bond process");
                bluetoothDevice.cancelBondProcess();
                return;
            }
            intent2.putExtra("android.bluetooth.device.extra.PAIRING_KEY", intExtra3);
        }
        intent2.setAction("android.bluetooth.device.action.PAIRING_REQUEST");
        intent2.setFlags(268435456);
        context.startActivity(intent2);
    }
}
