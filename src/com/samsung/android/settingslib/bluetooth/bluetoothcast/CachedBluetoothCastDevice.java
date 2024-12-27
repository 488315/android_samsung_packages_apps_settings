package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.settings.bluetooth.bluetoothcast.BluetoothCastDevicePreference;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CachedBluetoothCastDevice implements Comparable {
    public final Collection mCallbacks;
    public SemBluetoothCastDevice mCastDevice;
    public final HashMap mCastProfileConnectionState;
    public final LinkedHashSet mCastProfiles;
    public final Context mContext;
    public String mErrorMsg;
    public String mName;
    public int mSequence;
    public final Handler toastHandler;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callback {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ToastRunnable implements Runnable {
        public final String mText;

        public ToastRunnable(String str) {
            this.mText = str;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Toast.makeText(CachedBluetoothCastDevice.this.mContext, this.mText, 0).show();
        }
    }

    public CachedBluetoothCastDevice(
            Context context,
            LocalBluetoothCastProfileManager localBluetoothCastProfileManager,
            SemBluetoothCastDevice semBluetoothCastDevice) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        this.mCastProfiles = linkedHashSet;
        this.mCallbacks = new ArrayList();
        this.mContext = context;
        this.mCastDevice = semBluetoothCastDevice;
        this.mCastProfileConnectionState = new HashMap();
        this.toastHandler = new Handler();
        this.mName = this.mCastDevice.getDeviceName();
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(this.mCastDevice.getBluetoothCastType()));
        synchronized (localBluetoothCastProfileManager) {
            try {
                linkedHashSet.clear();
                if (arrayList.contains(1)) {
                    AudioCastProfile audioCastProfile =
                            localBluetoothCastProfileManager.mAudioCastProfile;
                    if (audioCastProfile == null) {
                        if (!linkedHashSet.contains(audioCastProfile)) {}
                    }
                    Log.d("LocalBluetoothCastProfileManager", "Audio Cast Profile added");
                    linkedHashSet.add(localBluetoothCastProfileManager.mAudioCastProfile);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Log.d(
                "CachedBluetoothCastDevice",
                "updateCastProfiles : " + String.valueOf(linkedHashSet.size()));
    }

    public final void dispatchAttributesChanged() {
        try {
            synchronized (this.mCallbacks) {
                try {
                    Iterator it = ((ArrayList) this.mCallbacks).iterator();
                    while (it.hasNext()) {
                        BluetoothCastDevicePreference bluetoothCastDevicePreference =
                                (BluetoothCastDevicePreference) ((Callback) it.next());
                        Log.d(
                                bluetoothCastDevicePreference.TAG,
                                "onCastDeviceAttributesChanged: shouldsort = true");
                        bluetoothCastDevicePreference.mListController.mCastListAdapter
                                .setNeedUpdatePreference();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CachedBluetoothCastDevice)) {
            return false;
        }
        return this.mCastDevice.equals(((CachedBluetoothCastDevice) obj).mCastDevice);
    }

    public final int getCastProfileConnectionState(AudioCastProfile audioCastProfile) {
        if (this.mCastProfileConnectionState.get(audioCastProfile) == null) {
            this.mCastProfileConnectionState.put(audioCastProfile, 0);
        }
        return ((Integer) this.mCastProfileConnectionState.get(audioCastProfile)).intValue();
    }

    public final List getCastProfiles() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mCastProfiles) {
            arrayList.addAll(this.mCastProfiles);
        }
        return Collections.unmodifiableList(arrayList);
    }

    public final long getConnectionTimeStamp() {
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(this.mContext, null);
        if (localBluetoothManager == null) {
            return 0L;
        }
        SemBluetoothCastDevice semBluetoothCastDevice = this.mCastDevice;
        LocalBluetoothCastAdapter localBluetoothCastAdapter =
                localBluetoothManager.mLocalCastAdapter;
        SemBluetoothCastAdapter semBluetoothCastAdapter = localBluetoothCastAdapter.mCastAdapter;
        String str = localBluetoothCastAdapter.TAG;
        if (semBluetoothCastAdapter == null) {
            Log.d(str, "Cannot getLastConnectedTime");
            return 0L;
        }
        Log.d(str, "cancelDiscovery");
        return localBluetoothCastAdapter.mCastAdapter.getLastConnectedTime(semBluetoothCastDevice);
    }

    public final int getMaxConnectionState() {
        int castProfileConnectionState;
        List castProfiles = getCastProfiles();
        Log.d(
                "CachedBluetoothCastDevice",
                "getMaxConnectionState size : " + String.valueOf(castProfiles.size()));
        int i = 0;
        for (int i2 = 0; i2 < castProfiles.size(); i2++) {
            AudioCastProfile audioCastProfile = (AudioCastProfile) castProfiles.get(i2);
            StringBuilder sb = new StringBuilder("getMaxConnectionState profile != null : ");
            sb.append(String.valueOf(audioCastProfile != null));
            Log.d("CachedBluetoothCastDevice", sb.toString());
            if (audioCastProfile != null
                    && (castProfileConnectionState =
                                    getCastProfileConnectionState(audioCastProfile))
                            > i) {
                i = castProfileConnectionState;
            }
        }
        Log.d("CachedBluetoothCastDevice", "getMaxConnectionState : " + String.valueOf(i));
        return i;
    }

    public final String getName() {
        String str = this.mName;
        return str != null
                ? str
                : this.mCastDevice.getDeviceName() != null
                        ? this.mCastDevice.getDeviceName()
                        : this.mCastDevice.getAddress();
    }

    public final int hashCode() {
        return this.mCastDevice.getAddress().hashCode();
    }

    public final boolean isBusy() {
        int castProfileConnectionState;
        List castProfiles = getCastProfiles();
        for (int i = 0; i < castProfiles.size(); i++) {
            AudioCastProfile audioCastProfile = (AudioCastProfile) castProfiles.get(i);
            if (audioCastProfile != null
                    && ((castProfileConnectionState =
                                            getCastProfileConnectionState(audioCastProfile))
                                    == 1
                            || castProfileConnectionState == 3)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isGearIconX() {
        byte[] manufacturerData = this.mCastDevice.getManufacturerData();
        BluetoothClass bluetoothClass = this.mCastDevice.getBluetoothClass();
        ManufacturerData manufacturerData2 = new ManufacturerData(manufacturerData);
        if (manufacturerData == null || bluetoothClass == null || manufacturerData.length < 9) {
            return false;
        }
        byte[] bArr = manufacturerData2.mData.mDeviceId;
        byte b = bArr[0];
        return (b == 0 || b == 1) && bArr[1] == 1 && bluetoothClass.getDeviceClass() == 1028;
    }

    public final void onCastProfileStateChanged(AudioCastProfile audioCastProfile, int i) {
        Log.d("CachedBluetoothCastDevice", "onCastProfileStateChanged : " + String.valueOf(i));
        this.mCastProfileConnectionState.put(audioCastProfile, Integer.valueOf(i));
        this.mCastDevice.setConnectionState(i);
        if (i == 2 && !this.mCastProfiles.contains(audioCastProfile)) {
            this.mCastProfiles.add(audioCastProfile);
        }
        dispatchAttributesChanged();
    }

    @Override // java.lang.Comparable
    public final int compareTo(CachedBluetoothCastDevice cachedBluetoothCastDevice) {
        int maxConnectionState = cachedBluetoothCastDevice.getMaxConnectionState();
        int maxConnectionState2 = getMaxConnectionState();
        int i = (maxConnectionState == 2 ? 1 : 0) - (maxConnectionState2 == 2 ? 1 : 0);
        if (i != 0) {
            return i;
        }
        int i2 =
                ((maxConnectionState == 1 || maxConnectionState == 3) ? 1 : 0)
                        - ((maxConnectionState2 == 1 || maxConnectionState2 == 3) ? 1 : 0);
        if (i2 != 0) {
            return i2;
        }
        long connectionTimeStamp =
                getConnectionTimeStamp() - cachedBluetoothCastDevice.getConnectionTimeStamp();
        if (connectionTimeStamp > 0) {
            return 1;
        }
        if (connectionTimeStamp < 0) {
            return -1;
        }
        int i3 = this.mSequence - cachedBluetoothCastDevice.mSequence;
        return i3 != 0 ? i3 : this.mName.compareTo(cachedBluetoothCastDevice.mName);
    }
}
