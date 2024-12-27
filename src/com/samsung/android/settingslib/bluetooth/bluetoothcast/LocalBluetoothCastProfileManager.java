package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LocalBluetoothCastProfileManager {
    public final AudioCastProfile mAudioCastProfile;
    public final CachedBluetoothCastDeviceManager mCastDeviceManager;
    public final BluetoothCastEventManager mCastEventManager;
    public final LocalBluetoothCastAdapter mLocalCastAdapter;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class StateChangedHandler implements BluetoothCastEventManager.Handler {
        public final AudioCastProfile mBluetoothCastProfile;

        public StateChangedHandler(AudioCastProfile audioCastProfile) {
            this.mBluetoothCastProfile = audioCastProfile;
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x00c2  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00d4  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x0100  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x0103  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00ec  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00ca  */
        @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(
                android.content.Context r9,
                android.content.Intent r10,
                com.samsung.android.bluetooth.SemBluetoothCastDevice r11) {
            /*
                Method dump skipped, instructions count: 463
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastProfileManager.StateChangedHandler.onReceive(android.content.Context,"
                        + " android.content.Intent,"
                        + " com.samsung.android.bluetooth.SemBluetoothCastDevice):void");
        }
    }

    public LocalBluetoothCastProfileManager(
            Context context,
            LocalBluetoothCastAdapter localBluetoothCastAdapter,
            CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager,
            BluetoothCastEventManager bluetoothCastEventManager) {
        HashMap hashMap = new HashMap();
        Log.d("LocalBluetoothCastProfileManager", "LocalBluetoothCastProfileManager ");
        this.mLocalCastAdapter = localBluetoothCastAdapter;
        this.mCastDeviceManager = cachedBluetoothCastDeviceManager;
        this.mCastEventManager = bluetoothCastEventManager;
        localBluetoothCastAdapter.mCastProfileManager = this;
        if (localBluetoothCastAdapter.mCastAdapter == null) {
            Log.d(localBluetoothCastAdapter.TAG, "Cannot set BluetoothCastStateOn");
        }
        bluetoothCastEventManager.mBluetoothCastProfileManager = this;
        Log.d("LocalBluetoothCastProfileManager", "updateLocalCastProfiles");
        if (this.mAudioCastProfile == null) {
            Log.d("LocalBluetoothCastProfileManager", "updateLocalCastProfiles mAudioCastProfile");
            AudioCastProfile audioCastProfile =
                    new AudioCastProfile(context, cachedBluetoothCastDeviceManager, this);
            this.mAudioCastProfile = audioCastProfile;
            ((HashMap) bluetoothCastEventManager.mHandlerMap)
                    .put(
                            "com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED",
                            new StateChangedHandler(audioCastProfile));
            bluetoothCastEventManager.mCastProfileFilter.addAction(
                    "com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED");
            hashMap.put("AudioCast", audioCastProfile);
            synchronized (bluetoothCastEventManager.mReceivers) {
                try {
                    if (bluetoothCastEventManager.mReceivers.contains(
                            bluetoothCastEventManager.mCastProfileReceiver)) {
                        bluetoothCastEventManager.mContext.unregisterReceiver(
                                bluetoothCastEventManager.mCastProfileReceiver);
                        bluetoothCastEventManager.mReceivers.remove(
                                bluetoothCastEventManager.mCastProfileReceiver);
                        Log.e(
                                "BluetoothCastEventManager",
                                "registerCastProfileIntentReceiver :: mProfileConnectionReceiver"
                                    + " was registered already. Receiver will refresh.");
                    }
                    bluetoothCastEventManager.mContext.registerReceiver(
                            bluetoothCastEventManager.mCastProfileReceiver,
                            bluetoothCastEventManager.mCastProfileFilter);
                    bluetoothCastEventManager.mReceivers.add(
                            bluetoothCastEventManager.mCastProfileReceiver);
                } finally {
                }
            }
        }
    }
}
