package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.internal.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CachedBluetoothDeviceManager {

    @VisibleForTesting static int sLateBondingTimeoutMillis = 5000;
    public final LocalBluetoothManager mBtManager;

    @VisibleForTesting final List<CachedBluetoothDevice> mCachedDevices;
    public final Context mContext;

    @VisibleForTesting CsipDeviceManager mCsipDeviceManager;
    public final List mFilteredCachedDevices;
    public int mGroupIdOfLateBonding;

    @VisibleForTesting HearingAidDeviceManager mHearingAidDeviceManager;
    public int mOngoingSetMemberGroupId;
    public BluetoothDevice mOngoingSetMemberPair;
    public final Map stubInfoMap;

    public CachedBluetoothDeviceManager(
            Context context, LocalBluetoothManager localBluetoothManager) {
        ArrayList arrayList = new ArrayList();
        this.mFilteredCachedDevices = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.mCachedDevices = arrayList2;
        this.mOngoingSetMemberGroupId = -1;
        this.stubInfoMap = new HashMap();
        this.mContext = context;
        this.mBtManager = localBluetoothManager;
        this.mHearingAidDeviceManager =
                new HearingAidDeviceManager(context, localBluetoothManager, arrayList2, arrayList);
        this.mCsipDeviceManager =
                new CsipDeviceManager(localBluetoothManager, arrayList2, arrayList);
        setStubInfo("com.samsung.android.app.watchmanagerstub");
    }

    public final CachedBluetoothDevice addDevice(
            LocalBluetoothProfileManager localBluetoothProfileManager,
            BluetoothDevice bluetoothDevice) {
        LocalBluetoothAdapter localBluetoothAdapter = this.mBtManager.mLocalAdapter;
        CachedBluetoothDevice cachedBluetoothDevice =
                new CachedBluetoothDevice(
                        this.mContext, localBluetoothProfileManager, bluetoothDevice);
        synchronized (this) {
            try {
                this.mCsipDeviceManager.initCsipDeviceIfNeeded(cachedBluetoothDevice);
                this.mHearingAidDeviceManager.initHearingAidDeviceIfNeeded(cachedBluetoothDevice);
                if (localBluetoothAdapter.mAdapter.isCustomDeviceAddress(
                        cachedBluetoothDevice.mDevice.getAddress())) {
                    return null;
                }
                this.mCsipDeviceManager.setMemberDeviceIfNeeded(cachedBluetoothDevice);
                if (!this.mHearingAidDeviceManager.setSubDeviceIfNeeded(cachedBluetoothDevice)) {
                    if (this.mCachedDevices.contains(cachedBluetoothDevice)) {
                        Log.d(
                                "CachedBluetoothDeviceManager",
                                "addDevice :: newDevice is added already");
                        return findDevice(bluetoothDevice);
                    }
                    boolean addDevice = addDevice(cachedBluetoothDevice);
                    cachedBluetoothDevice.mSequence =
                            this.mCachedDevices.indexOf(cachedBluetoothDevice);
                    if (!addDevice) {
                        this.mBtManager.mEventManager.dispatchDeviceAdded(cachedBluetoothDevice);
                    }
                }
                return cachedBluetoothDevice;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final CachedBluetoothDevice addDevice$1(BluetoothDevice bluetoothDevice) {
        CachedBluetoothDevice findDevice;
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mBtManager.mProfileManager;
        synchronized (this) {
            try {
                findDevice = findDevice(bluetoothDevice);
                if (findDevice == null) {
                    findDevice =
                            new CachedBluetoothDevice(
                                    this.mContext, localBluetoothProfileManager, bluetoothDevice);
                    this.mCsipDeviceManager.initCsipDeviceIfNeeded(findDevice);
                    HearingAidInfo generateHearingAidInfo =
                            this.mHearingAidDeviceManager.generateHearingAidInfo(findDevice);
                    if (generateHearingAidInfo != null) {
                        findDevice.setHearingAidInfo(generateHearingAidInfo);
                    }
                    this.mCsipDeviceManager.setMemberDeviceIfNeeded(findDevice);
                    if (!this.mHearingAidDeviceManager.setSubDeviceIfNeeded(findDevice)) {
                        this.mCachedDevices.add(findDevice);
                        this.mBtManager.mEventManager.dispatchDeviceAdded(findDevice);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return findDevice;
    }

    public final synchronized void clearNonBondedDevices() {
        try {
            for (int size = this.mCachedDevices.size() - 1; size >= 0; size--) {
                CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevices.get(size);
                if (cachedBluetoothDevice.mBondState == 10 && !cachedBluetoothDevice.mIsRestored) {
                    removeDevice(cachedBluetoothDevice);
                }
            }
            updateSequeces();
        } catch (Throwable th) {
            throw th;
        }
    }

    public final synchronized CachedBluetoothDevice findDevice(BluetoothDevice bluetoothDevice) {
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDevices) {
            if (cachedBluetoothDevice.mDevice.equals(bluetoothDevice)) {
                return cachedBluetoothDevice;
            }
            Set<CachedBluetoothDevice> set = cachedBluetoothDevice.mMemberDevices;
            if (!set.isEmpty()) {
                for (CachedBluetoothDevice cachedBluetoothDevice2 : set) {
                    if (cachedBluetoothDevice2.mDevice.equals(bluetoothDevice)) {
                        return cachedBluetoothDevice2;
                    }
                }
            }
            CachedBluetoothDevice cachedBluetoothDevice3 = cachedBluetoothDevice.mSubDevice;
            if (cachedBluetoothDevice3 != null
                    && cachedBluetoothDevice3.mDevice.equals(bluetoothDevice)) {
                return cachedBluetoothDevice3;
            }
        }
        return null;
    }

    public final synchronized Collection getCachedDevicesCopy() {
        return new ArrayList(this.mFilteredCachedDevices);
    }

    public final synchronized Collection getCachedDevicesCopyForSalogging() {
        ArrayList arrayList;
        arrayList = new ArrayList();
        LocalBluetoothAdapter localBluetoothAdapter = this.mBtManager.mLocalAdapter;
        for (int i = 0; i < this.mCachedDevices.size(); i++) {
            CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevices.get(i);
            if (cachedBluetoothDevice != null) {
                if (!localBluetoothAdapter.mAdapter.isCustomDeviceAddress(
                                cachedBluetoothDevice.mDevice.getAddress())
                        && !needListFiltering(cachedBluetoothDevice, true)) {
                    arrayList.add(cachedBluetoothDevice);
                }
            }
        }
        return arrayList;
    }

    public final String getName(BluetoothDevice bluetoothDevice) {
        CachedBluetoothDevice cachedBluetoothDevice;
        if (isOngoingPairByCsip(bluetoothDevice)) {
            CsipDeviceManager csipDeviceManager = this.mCsipDeviceManager;
            int i = this.mGroupIdOfLateBonding;
            List<CachedBluetoothDevice> groupDevicesFromAllOfDevicesList =
                    csipDeviceManager.getGroupDevicesFromAllOfDevicesList(i);
            if (groupDevicesFromAllOfDevicesList.isEmpty()) {
                cachedBluetoothDevice = null;
            } else {
                cachedBluetoothDevice = groupDevicesFromAllOfDevicesList.get(0);
                StringBuilder m =
                        ListPopupWindow$$ExternalSyntheticOutline0.m(
                                i, "getFirstMemberDevice: groupId=", " address=");
                m.append(cachedBluetoothDevice.mDevice.getAnonymizedAddress());
                CsipDeviceManager.log(m.toString());
            }
            if (cachedBluetoothDevice != null && cachedBluetoothDevice.getName() != null) {
                return cachedBluetoothDevice.getName();
            }
        }
        CachedBluetoothDevice findDevice = findDevice(bluetoothDevice);
        if (findDevice != null && findDevice.getName() != null) {
            return findDevice.getName();
        }
        String alias = bluetoothDevice.getAlias();
        return alias != null ? alias : bluetoothDevice.getAddress();
    }

    public final boolean isOngoingPairByCsip(BluetoothDevice bluetoothDevice) {
        BluetoothDevice bluetoothDevice2 = this.mOngoingSetMemberPair;
        return bluetoothDevice2 != null && bluetoothDevice2.equals(bluetoothDevice);
    }

    public final synchronized boolean isSubDevice(BluetoothDevice bluetoothDevice) {
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDevices) {
            if (!cachedBluetoothDevice.mDevice.equals(bluetoothDevice)) {
                Set set = cachedBluetoothDevice.mMemberDevices;
                if (set.isEmpty()) {
                    CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
                    if (cachedBluetoothDevice2 != null
                            && cachedBluetoothDevice2.mDevice.equals(bluetoothDevice)) {
                        return true;
                    }
                } else {
                    Iterator it = set.iterator();
                    while (it.hasNext()) {
                        if (((CachedBluetoothDevice) it.next()).mDevice.equals(bluetoothDevice)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public final boolean isValidStub() {
        Log.d(
                "CachedBluetoothDeviceManager",
                "isValidStub: packageName = com.samsung.android.app.watchmanagerstub");
        return ((HashMap) this.stubInfoMap).get("com.samsung.android.app.watchmanagerstub") != null
                && ((Integer)
                                        ((HashMap) this.stubInfoMap)
                                                .get("com.samsung.android.app.watchmanagerstub"))
                                .intValue()
                        > 100;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x005f, code lost:

       if (r0.getMajorDeviceClass() == 7936) goto L25;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean needListFiltering(
            com.android.settingslib.bluetooth.CachedBluetoothDevice r9, boolean r10) {
        /*
            Method dump skipped, instructions count: 465
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.bluetooth.CachedBluetoothDeviceManager.needListFiltering(com.android.settingslib.bluetooth.CachedBluetoothDevice,"
                    + " boolean):boolean");
    }

    public final synchronized void removeDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        this.mCachedDevices.remove(cachedBluetoothDevice);
        ((ArrayList) this.mFilteredCachedDevices).remove(cachedBluetoothDevice);
    }

    public final synchronized void removeRestoredDevice(
            CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        Intent intent = new Intent();
        intent.setAction("com.samsung.android.intent.action.NOTIFY_REMOVED_SYNC_DEVICE_BLUETOOTH");
        intent.putExtra("android.bluetooth.device.extra.DEVICE", bluetoothDevice);
        intent.setFlags(268435456);
        intent.setPackage("com.android.bluetooth");
        this.mContext.sendBroadcast(intent);
        removeDevice(cachedBluetoothDevice);
    }

    public final synchronized void resetCachedDevices() {
        Iterator<CachedBluetoothDevice> it = this.mCachedDevices.iterator();
        while (it.hasNext()) {
            it.next().resetRetryDetector();
        }
    }

    public final void setOngoingSetMemberPair() {
        this.mOngoingSetMemberPair = null;
        this.mOngoingSetMemberGroupId = -1;
    }

    public final void setStubInfo(String str) {
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            for (ApplicationInfo applicationInfo : packageManager.getInstalledApplications(0)) {
                if (applicationInfo.packageName.equals(str) && applicationInfo.enabled) {
                    int i = packageManager.getPackageInfo(str, 0).versionCode;
                    this.stubInfoMap.put(str, Integer.valueOf(i));
                    Log.d(
                            "CachedBluetoothDeviceManager",
                            "setStubInfo: INSTALLER_STUB is exist. Package : "
                                    + str
                                    + ", Version : "
                                    + i);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public final synchronized void updateSequeces() {
        for (int size = this.mCachedDevices.size() - 1; size >= 0; size--) {
            this.mCachedDevices.get(size).mSequence = size;
        }
    }

    public final synchronized boolean addDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        boolean z;
        this.mCachedDevices.add(cachedBluetoothDevice);
        z = false;
        if (needListFiltering(cachedBluetoothDevice, false)) {
            z = true;
        } else {
            ((ArrayList) this.mFilteredCachedDevices).add(cachedBluetoothDevice);
        }
        return z;
    }

    public final CachedBluetoothDevice addDevice(BluetoothDevice bluetoothDevice) {
        LocalBluetoothManager localBluetoothManager = this.mBtManager;
        LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
        LocalBluetoothProfileManager localBluetoothProfileManager =
                localBluetoothManager.mProfileManager;
        synchronized (this) {
            try {
                CachedBluetoothDevice findDevice = findDevice(bluetoothDevice);
                if (findDevice == null) {
                    findDevice =
                            new CachedBluetoothDevice(
                                    this.mContext, localBluetoothProfileManager, bluetoothDevice);
                    if (localBluetoothAdapter.mAdapter.isCustomDeviceAddress(
                            findDevice.mDevice.getAddress())) {
                        return null;
                    }
                    this.mCsipDeviceManager.initCsipDeviceIfNeeded(findDevice);
                    this.mHearingAidDeviceManager.initHearingAidDeviceIfNeeded(findDevice);
                    this.mCsipDeviceManager.setMemberDeviceIfNeeded(findDevice);
                    if (!this.mHearingAidDeviceManager.setSubDeviceIfNeeded(findDevice)) {
                        if (this.mCachedDevices.contains(findDevice)) {
                            Log.d(
                                    "CachedBluetoothDeviceManager",
                                    "addDevice :: newDevice is added already");
                            return findDevice(bluetoothDevice);
                        }
                        boolean addDevice = addDevice(findDevice);
                        findDevice.mSequence = this.mCachedDevices.indexOf(findDevice);
                        if (!addDevice) {
                            this.mBtManager.mEventManager.dispatchDeviceAdded(findDevice);
                        }
                    }
                }
                return findDevice;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
