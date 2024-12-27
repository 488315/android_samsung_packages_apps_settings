package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHearingAid;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import com.android.settings.R;
import com.android.settingslib.Utils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HearingAidProfile implements LocalBluetoothProfile {
    public final BluetoothAdapter mBluetoothAdapter;
    public final Context mContext;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothHearingAid mService;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HearingAidServiceListener implements BluetoothProfile.ServiceListener {
        public HearingAidServiceListener() {}

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            BluetoothHearingAid bluetoothHearingAid = (BluetoothHearingAid) bluetoothProfile;
            HearingAidProfile.this.mService = bluetoothHearingAid;
            List<BluetoothDevice> connectedDevices = bluetoothHearingAid.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice remove = connectedDevices.remove(0);
                CachedBluetoothDevice findDevice =
                        HearingAidProfile.this.mDeviceManager.findDevice(remove);
                if (findDevice == null) {
                    Log.d("HearingAidProfile", "HearingAidProfile found new device: " + remove);
                    HearingAidProfile hearingAidProfile = HearingAidProfile.this;
                    findDevice =
                            hearingAidProfile.mDeviceManager.addDevice(
                                    hearingAidProfile.mProfileManager, remove);
                }
                findDevice.onProfileStateChanged(HearingAidProfile.this, 2);
                findDevice.refresh();
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager =
                    HearingAidProfile.this.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                cachedBluetoothDeviceManager.mHearingAidDeviceManager.updateHearingAidsDevices();
            }
            HearingAidProfile hearingAidProfile2 = HearingAidProfile.this;
            hearingAidProfile2.mIsProfileReady = true;
            hearingAidProfile2.mProfileManager.callServiceConnectedListeners();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            HearingAidProfile hearingAidProfile = HearingAidProfile.this;
            hearingAidProfile.mIsProfileReady = false;
            hearingAidProfile.mProfileManager.callServiceDisconnectedListeners();
        }
    }

    public HearingAidProfile(
            Context context,
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager,
            LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mContext = context;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothAdapter = defaultAdapter;
        defaultAdapter.getProfileProxy(context, new HearingAidServiceListener(), 21);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return false;
    }

    public final void finalize() {
        Log.d("HearingAidProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(21, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("HearingAidProfile", "Error cleaning up Hearing Aid proxy", th);
            }
        }
    }

    public final List getActiveDevices() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        return bluetoothAdapter == null ? new ArrayList() : bluetoothAdapter.getActiveDevices(21);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothHearingAid bluetoothHearingAid = this.mService;
        if (bluetoothHearingAid == null) {
            return 0;
        }
        return bluetoothHearingAid.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return R.drawable.sec_bluetooth_2d_hearing_aids;
    }

    public final long getHiSyncId(BluetoothDevice bluetoothDevice) {
        BluetoothHearingAid bluetoothHearingAid = this.mService;
        if (bluetoothHearingAid == null || bluetoothDevice == null) {
            return 0L;
        }
        return bluetoothHearingAid.getHiSyncId(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getNameResource(BluetoothDevice bluetoothDevice) {
        return R.string.bluetooth_profile_hearing_aid;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getOrdinal() {
        return 21;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 21;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothHearingAid bluetoothHearingAid = this.mService;
        return (bluetoothHearingAid == null
                        || bluetoothDevice == null
                        || bluetoothHearingAid.getConnectionPolicy(bluetoothDevice) <= 0)
                ? false
                : true;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    public final boolean setActiveDevice(BluetoothDevice bluetoothDevice) {
        if (this.mBluetoothAdapter == null) {
            return false;
        }
        boolean isAudioModeOngoingCall = Utils.isAudioModeOngoingCall(this.mContext);
        return bluetoothDevice == null
                ? this.mBluetoothAdapter.removeActiveDevice(isAudioModeOngoingCall ? 1 : 0)
                : this.mBluetoothAdapter.setActiveDevice(
                        bluetoothDevice, isAudioModeOngoingCall ? 1 : 0);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothHearingAid bluetoothHearingAid = this.mService;
        if (bluetoothHearingAid == null || bluetoothDevice == null) {
            return false;
        }
        return z
                ? bluetoothHearingAid.setConnectionPolicy(bluetoothDevice, 100)
                : bluetoothHearingAid.setConnectionPolicy(bluetoothDevice, 0);
    }

    public final String toString() {
        return "HearingAid";
    }
}
