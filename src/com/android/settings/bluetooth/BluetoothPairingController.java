package com.android.settings.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.util.Log;

import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothPairingController {
    public static final boolean DBG = Debug.semIsProductDev();
    public int mBlockState = 0;
    public String[] mBlockingDevices;
    public final Context mContext;
    public final BluetoothDevice mDevice;
    public final String mDeviceName;
    public boolean mIsAcceptPair;
    public final boolean mIsBondedInitiatedLocally;
    public final boolean mIsLeContactSharingEnabled;
    public final boolean mIsSettingsForeground;
    public final int mPasskey;
    public final String mPasskeyFormatted;
    int mType;
    public String mUserInput;

    public BluetoothPairingController(Context context, Intent intent) {
        this.mIsAcceptPair = false;
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        BluetoothDevice bluetoothDevice =
                (BluetoothDevice)
                        intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
        this.mDevice = bluetoothDevice;
        if (localBluetoothManager == null) {
            throw new IllegalStateException("Could not obtain LocalBluetoothManager");
        }
        if (bluetoothDevice == null) {
            throw new IllegalStateException("Could not find BluetoothDevice");
        }
        this.mType =
                intent.getIntExtra(
                        "android.bluetooth.device.extra.PAIRING_VARIANT", Integer.MIN_VALUE);
        int intExtra =
                intent.getIntExtra("android.bluetooth.device.extra.PAIRING_KEY", Integer.MIN_VALUE);
        this.mPasskey = intExtra;
        intent.getIntExtra("android.bluetooth.device.extra.PAIRING_INITIATOR", Integer.MIN_VALUE);
        this.mDeviceName = localBluetoothManager.mCachedDeviceManager.getName(bluetoothDevice);
        localBluetoothManager.mProfileManager.getClass();
        int i = this.mType;
        this.mPasskeyFormatted =
                i != 2
                        ? (i == 4 || i == 5)
                                ? String.format(Locale.US, "%06d", Integer.valueOf(intExtra))
                                : null
                        : String.format(Locale.US, "%06d", Integer.valueOf(intExtra));
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                localBluetoothManager.mCachedDeviceManager;
        synchronized (cachedBluetoothDeviceManager) {
            if (cachedBluetoothDeviceManager.isOngoingPairByCsip(bluetoothDevice)) {
                Log.d("CachedBluetoothDeviceManager", "isLateBonding: false");
            } else {
                Log.d(
                        "CachedBluetoothDeviceManager",
                        "isLateBonding: pair not ongoing or not matching device");
            }
        }
        localBluetoothManager.mCachedDeviceManager.findDevice(bluetoothDevice);
        this.mIsLeContactSharingEnabled = true;
        this.mIsAcceptPair = false;
        LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
        if (localBluetoothAdapter != null && localBluetoothAdapter.mAdapter.isDiscovering()) {
            localBluetoothAdapter.cancelDiscovery();
        }
        this.mContext = context;
        this.mIsBondedInitiatedLocally = bluetoothDevice.isBondingInitiatedLocally();
        this.mIsSettingsForeground =
                localBluetoothManager.isForegroundActivity()
                        || localBluetoothManager.isForegroundActivityQP();
    }

    public final String getPairingContent() {
        int i = this.mType;
        if (i == 2 || i == 4 || i == 5) {
            return this.mPasskeyFormatted;
        }
        return null;
    }

    public boolean isLeAudio() {
        return false;
    }

    public boolean isLeContactSharingEnabled() {
        return this.mIsLeContactSharingEnabled;
    }

    public final void onCancel() {
        Log.d("BTPairingController", "Pairing dialog canceled");
        this.mDevice.cancelBondProcess();
    }

    public final void onPair(String str) {
        Log.d("BTPairingController", "Pairing dialog accepted");
        switch (this.mType) {
            case 0:
            case 7:
                byte[] convertPinToBytes = BluetoothDevice.convertPinToBytes(str);
                if (convertPinToBytes != null) {
                    this.mDevice.setPin(convertPinToBytes);
                    break;
                }
                break;
            case 1:
            case 4:
            case 5:
            case 6:
                break;
            case 2:
            case 3:
                this.mDevice.setPairingConfirmation(true);
                break;
            default:
                Log.e("BTPairingController", "Incorrect pairing type received");
                break;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00f7, code lost:

       if (r14 == 2) goto L33;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateBlockingDevice(boolean r21) {
        /*
            Method dump skipped, instructions count: 392
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.bluetooth.BluetoothPairingController.updateBlockingDevice(boolean):void");
    }
}
