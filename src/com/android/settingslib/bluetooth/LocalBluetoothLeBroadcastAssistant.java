package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import com.android.settings.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LocalBluetoothLeBroadcastAssistant implements LocalBluetoothProfile {
    public final Map mCachedCallbackExecutorMap = new ConcurrentHashMap();
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothLeBroadcastAssistant mService;
    public final AnonymousClass1 mServiceListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant$1, reason: invalid class name */
    public final class AnonymousClass1 implements BluetoothProfile.ServiceListener {
        public AnonymousClass1() {}

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            Log.d("LocalBluetoothLeBroadcastAssistant", "Bluetooth service connected");
            BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant =
                    (BluetoothLeBroadcastAssistant) bluetoothProfile;
            LocalBluetoothLeBroadcastAssistant.this.mService = bluetoothLeBroadcastAssistant;
            List connectedDevices = bluetoothLeBroadcastAssistant.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice =
                        LocalBluetoothLeBroadcastAssistant.this.mDeviceManager.findDevice(
                                bluetoothDevice);
                if (findDevice == null) {
                    Log.d(
                            "LocalBluetoothLeBroadcastAssistant",
                            "LocalBluetoothLeBroadcastAssistant found new device: "
                                    + bluetoothDevice);
                    findDevice =
                            LocalBluetoothLeBroadcastAssistant.this.mDeviceManager.addDevice(
                                    bluetoothDevice);
                }
                findDevice.onProfileStateChanged(LocalBluetoothLeBroadcastAssistant.this, 2);
                findDevice.refresh();
            }
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                    LocalBluetoothLeBroadcastAssistant.this;
            localBluetoothLeBroadcastAssistant.mIsProfileReady = true;
            localBluetoothLeBroadcastAssistant.mProfileManager.callServiceConnectedListeners();
            Log.d(
                    "LocalBluetoothLeBroadcastAssistant",
                    "onServiceConnected, register mCachedCallbackExecutorMap = "
                            + LocalBluetoothLeBroadcastAssistant.this.mCachedCallbackExecutorMap);
            ((ConcurrentHashMap) LocalBluetoothLeBroadcastAssistant.this.mCachedCallbackExecutorMap)
                    .forEach(
                            new BiConsumer() { // from class:
                                               // com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant$1$$ExternalSyntheticLambda0
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    LocalBluetoothLeBroadcastAssistant
                                            localBluetoothLeBroadcastAssistant2 =
                                                    LocalBluetoothLeBroadcastAssistant.this;
                                    localBluetoothLeBroadcastAssistant2.registerServiceCallBack(
                                            (Executor) obj2,
                                            (BluetoothLeBroadcastAssistant.Callback) obj);
                                }
                            });
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public final void onServiceDisconnected(int i) {
            if (i != 29) {
                Log.d(
                        "LocalBluetoothLeBroadcastAssistant",
                        "The profile is not LE_AUDIO_BROADCAST_ASSISTANT");
                return;
            }
            Log.d("LocalBluetoothLeBroadcastAssistant", "Bluetooth service disconnected");
            LocalBluetoothLeBroadcastAssistant.this.mProfileManager
                    .callServiceDisconnectedListeners();
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                    LocalBluetoothLeBroadcastAssistant.this;
            localBluetoothLeBroadcastAssistant.mIsProfileReady = false;
            ((ConcurrentHashMap) localBluetoothLeBroadcastAssistant.mCachedCallbackExecutorMap)
                    .clear();
        }
    }

    public LocalBluetoothLeBroadcastAssistant(
            Context context,
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager,
            LocalBluetoothProfileManager localBluetoothProfileManager) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mProfileManager = localBluetoothProfileManager;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, anonymousClass1, 29);
        new BluetoothLeBroadcastMetadata.Builder();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean accessProfileEnabled() {
        return false;
    }

    public final void addSource(
            BluetoothDevice bluetoothDevice,
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
            boolean z) {
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant == null) {
            Log.d(
                    "LocalBluetoothLeBroadcastAssistant",
                    "The BluetoothLeBroadcastAssistant is null");
        } else {
            bluetoothLeBroadcastAssistant.addSource(
                    bluetoothDevice, bluetoothLeBroadcastMetadata, z);
        }
    }

    public final void finalize() {
        Log.d("LocalBluetoothLeBroadcastAssistant", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(29, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("LocalBluetoothLeBroadcastAssistant", "Error cleaning up LeAudio proxy", th);
            }
        }
    }

    public final List getAllSources(BluetoothDevice bluetoothDevice) {
        Log.d("LocalBluetoothLeBroadcastAssistant", "getAllSources()");
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant != null) {
            return bluetoothLeBroadcastAssistant.getAllSources(bluetoothDevice);
        }
        Log.d("LocalBluetoothLeBroadcastAssistant", "The BluetoothLeBroadcastAssistant is null");
        return new ArrayList();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant == null) {
            return 0;
        }
        return bluetoothLeBroadcastAssistant.getConnectionState(bluetoothDevice);
    }

    public final List getDevicesMatchingConnectionStates(int[] iArr) {
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        return bluetoothLeBroadcastAssistant == null
                ? new ArrayList(0)
                : bluetoothLeBroadcastAssistant.getDevicesMatchingConnectionStates(iArr);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getDrawableResource(BluetoothClass bluetoothClass) {
        return 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getNameResource(BluetoothDevice bluetoothDevice) {
        return R.string.summary_empty;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getOrdinal() {
        return 1;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final int getProfileId() {
        return 29;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isEnabled(BluetoothDevice bluetoothDevice) {
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        return (bluetoothLeBroadcastAssistant == null
                        || bluetoothDevice == null
                        || bluetoothLeBroadcastAssistant.getConnectionPolicy(bluetoothDevice) <= 0)
                ? false
                : true;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean isProfileReady() {
        return this.mIsProfileReady;
    }

    public final boolean isSearchInProgress() {
        Log.d("LocalBluetoothLeBroadcastAssistant", "isSearchInProgress()");
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant != null) {
            return bluetoothLeBroadcastAssistant.isSearchInProgress();
        }
        Log.d("LocalBluetoothLeBroadcastAssistant", "The BluetoothLeBroadcastAssistant is null");
        return false;
    }

    public final void registerServiceCallBack(
            Executor executor, BluetoothLeBroadcastAssistant.Callback callback) {
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant == null) {
            Log.d(
                    "LocalBluetoothLeBroadcastAssistant",
                    "registerServiceCallBack failed, the BluetoothLeBroadcastAssistant is null.");
            ((ConcurrentHashMap) this.mCachedCallbackExecutorMap).putIfAbsent(callback, executor);
            return;
        }
        try {
            bluetoothLeBroadcastAssistant.registerCallback(executor, callback);
        } catch (IllegalArgumentException e) {
            Log.w(
                    "LocalBluetoothLeBroadcastAssistant",
                    "registerServiceCallBack failed. " + e.getMessage());
        }
    }

    public final void removeSource(BluetoothDevice bluetoothDevice, int i) {
        Log.d("LocalBluetoothLeBroadcastAssistant", "removeSource()");
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant == null) {
            Log.d(
                    "LocalBluetoothLeBroadcastAssistant",
                    "The BluetoothLeBroadcastAssistant is null");
        } else {
            bluetoothLeBroadcastAssistant.removeSource(bluetoothDevice, i);
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public final boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant == null || bluetoothDevice == null) {
            return false;
        }
        if (!z) {
            return bluetoothLeBroadcastAssistant.setConnectionPolicy(bluetoothDevice, 0);
        }
        if (bluetoothLeBroadcastAssistant.getConnectionPolicy(bluetoothDevice) < 100) {
            return this.mService.setConnectionPolicy(bluetoothDevice, 100);
        }
        return false;
    }

    public final void startSearchingForSources(List list) {
        Log.d("LocalBluetoothLeBroadcastAssistant", "startSearchingForSources()");
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant == null) {
            Log.d(
                    "LocalBluetoothLeBroadcastAssistant",
                    "The BluetoothLeBroadcastAssistant is null");
        } else {
            bluetoothLeBroadcastAssistant.startSearchingForSources(list);
        }
    }

    public final void stopSearchingForSources$1() {
        Log.d("LocalBluetoothLeBroadcastAssistant", "stopSearchingForSources()");
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant == null) {
            Log.d(
                    "LocalBluetoothLeBroadcastAssistant",
                    "The BluetoothLeBroadcastAssistant is null");
        } else {
            bluetoothLeBroadcastAssistant.stopSearchingForSources();
        }
    }

    public final String toString() {
        return "LE_AUDIO_BROADCAST_ASSISTANT";
    }

    public final void unregisterServiceCallBack(BluetoothLeBroadcastAssistant.Callback callback) {
        ((ConcurrentHashMap) this.mCachedCallbackExecutorMap).remove(callback);
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant == null) {
            Log.d(
                    "LocalBluetoothLeBroadcastAssistant",
                    "unregisterServiceCallBack failed, the BluetoothLeBroadcastAssistant is null.");
            return;
        }
        try {
            bluetoothLeBroadcastAssistant.unregisterCallback(callback);
        } catch (IllegalArgumentException e) {
            Log.w(
                    "LocalBluetoothLeBroadcastAssistant",
                    "unregisterServiceCallBack failed. " + e.getMessage());
        }
    }
}
