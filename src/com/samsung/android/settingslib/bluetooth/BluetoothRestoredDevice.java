package com.samsung.android.settingslib.bluetooth;

import android.os.ParcelUuid;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BluetoothRestoredDevice {
    public final String mAddress;
    public int mAppearance;
    public int mBondState;
    public int mCod;
    public int mLinkType = 0;
    public byte[] mManufacturerData;
    public String mName;
    public long mTimeStamp;
    public ParcelUuid[] mUuids;

    public BluetoothRestoredDevice(String str) {
        this.mAddress = str;
    }
}
