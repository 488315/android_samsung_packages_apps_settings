package com.samsung.android.knox.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothSecureModeWhitelistConfig implements Parcelable {
    public static final Parcelable.Creator<BluetoothSecureModeWhitelistConfig> CREATOR =
            new AnonymousClass1();
    public int cod;
    public String name;
    public String[] uuids;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.bluetooth.BluetoothSecureModeWhitelistConfig$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<BluetoothSecureModeWhitelistConfig> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothSecureModeWhitelistConfig createFromParcel(Parcel parcel) {
            return new BluetoothSecureModeWhitelistConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothSecureModeWhitelistConfig[] newArray(int i) {
            return new BluetoothSecureModeWhitelistConfig[i];
        }
    }

    public BluetoothSecureModeWhitelistConfig() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        if (parcel == null) {
            return;
        }
        this.name = parcel.readString();
        this.cod = parcel.readInt();
        int readInt = parcel.readInt();
        if (readInt <= 0) {
            this.uuids = null;
            return;
        }
        String[] strArr = new String[readInt];
        this.uuids = strArr;
        parcel.readStringArray(strArr);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeString(this.name);
        parcel.writeInt(this.cod);
        String[] strArr = this.uuids;
        if (strArr == null || strArr.length <= 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(strArr.length);
            parcel.writeStringArray(this.uuids);
        }
    }

    public BluetoothSecureModeWhitelistConfig(String str, int i, String[] strArr) {
        this.name = str;
        this.cod = i;
        this.uuids = strArr;
    }

    public BluetoothSecureModeWhitelistConfig(Parcel parcel) {
        readFromParcel(parcel);
    }
}
