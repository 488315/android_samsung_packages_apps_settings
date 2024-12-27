package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothPbap;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.os.ParcelUuid;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PbapServerProfile implements LocalBluetoothProfile {

    @VisibleForTesting public static final String NAME = "PBAP Server";
    public boolean mIsProfileReady;
    public BluetoothPbap mService;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PbapServiceListener implements BluetoothProfile.ServiceListener {
        public PbapServiceListener() {}

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            PbapServerProfile pbapServerProfile = PbapServerProfile.this;
            pbapServerProfile.mService = (BluetoothPbap) bluetoothProfile;
            pbapServerProfile.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            PbapServerProfile.this.mIsProfileReady = false;
        }
    }

    static {
        ParcelUuid[] parcelUuidArr = new ParcelUuid[3];
        ParcelUuid parcelUuid = BluetoothUuid.HSP;
        ParcelUuid parcelUuid2 = BluetoothUuid.HFP;
        ParcelUuid parcelUuid3 = BluetoothUuid.PBAP_PCE;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return true;
    }

    public final void finalize() {
        Log.d("PbapServerProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(6, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("PbapServerProfile", "Error cleaning up PBAP proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothPbap bluetoothPbap = this.mService;
        if (bluetoothPbap == null) {
            return 0;
        }
        return bluetoothPbap.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.sec_bluetooth_2d_phone_book;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getNameResource(BluetoothDevice bluetoothDevice) {
        return R.string.bluetooth_profile_pbap;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getOrdinal() {
        return 6;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 6;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        return false;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothPbap bluetoothPbap = this.mService;
        if (bluetoothPbap == null || z) {
            return false;
        }
        return bluetoothPbap.setConnectionPolicy(bluetoothDevice, 0);
    }

    public final String toString() {
        return NAME;
    }
}
