package com.samsung.android.knox.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

import com.samsung.android.knox.ControlInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothControlInfo extends ControlInfo {
    public static final Parcelable.Creator<BluetoothControlInfo> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.bluetooth.BluetoothControlInfo$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<BluetoothControlInfo> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothControlInfo createFromParcel(Parcel parcel) {
            return new BluetoothControlInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothControlInfo[] newArray(int i) {
            return new BluetoothControlInfo[i];
        }
    }

    public BluetoothControlInfo() {}

    public BluetoothControlInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
