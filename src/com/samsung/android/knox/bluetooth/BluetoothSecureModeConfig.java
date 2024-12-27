package com.samsung.android.knox.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothSecureModeConfig implements Parcelable {
    public static final Parcelable.Creator<BluetoothSecureModeConfig> CREATOR =
            new AnonymousClass1();
    public boolean a2dpEnable;
    public boolean ftpEnable;
    public boolean gattEnable;
    public boolean hdpEnable;
    public boolean hfpEnable;
    public boolean hidEnable;
    public boolean mapEnable;
    public boolean oppEnable;
    public boolean pairingMode;
    public boolean panEnable;
    public boolean pbapEnable;
    public boolean sapEnable;
    public boolean scanMode;
    public boolean whitelistEnable;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.bluetooth.BluetoothSecureModeConfig$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<BluetoothSecureModeConfig> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothSecureModeConfig createFromParcel(Parcel parcel) {
            return new BluetoothSecureModeConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BluetoothSecureModeConfig[] newArray(int i) {
            return new BluetoothSecureModeConfig[i];
        }
    }

    public BluetoothSecureModeConfig(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        if (parcel == null) {
            return;
        }
        this.scanMode = parcel.readInt() != 0;
        this.pairingMode = parcel.readInt() != 0;
        this.hfpEnable = parcel.readInt() != 0;
        this.a2dpEnable = parcel.readInt() != 0;
        this.hidEnable = parcel.readInt() != 0;
        this.hdpEnable = parcel.readInt() != 0;
        this.panEnable = parcel.readInt() != 0;
        this.oppEnable = parcel.readInt() != 0;
        this.pbapEnable = parcel.readInt() != 0;
        this.mapEnable = parcel.readInt() != 0;
        this.ftpEnable = parcel.readInt() != 0;
        this.sapEnable = parcel.readInt() != 0;
        this.whitelistEnable = parcel.readInt() != 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel == null) {
            return;
        }
        parcel.writeInt(this.scanMode ? 1 : 0);
        parcel.writeInt(this.pairingMode ? 1 : 0);
        parcel.writeInt(this.hfpEnable ? 1 : 0);
        parcel.writeInt(this.a2dpEnable ? 1 : 0);
        parcel.writeInt(this.hidEnable ? 1 : 0);
        parcel.writeInt(this.hdpEnable ? 1 : 0);
        parcel.writeInt(this.panEnable ? 1 : 0);
        parcel.writeInt(this.oppEnable ? 1 : 0);
        parcel.writeInt(this.pbapEnable ? 1 : 0);
        parcel.writeInt(this.mapEnable ? 1 : 0);
        parcel.writeInt(this.ftpEnable ? 1 : 0);
        parcel.writeInt(this.sapEnable ? 1 : 0);
        parcel.writeInt(this.whitelistEnable ? 1 : 0);
    }

    public BluetoothSecureModeConfig() {}
}
