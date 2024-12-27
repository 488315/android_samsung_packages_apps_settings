package com.samsung.android.knox.application;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class UsbDeviceConfig implements Parcelable {
    public static final Parcelable.Creator<UsbDeviceConfig> CREATOR = new AnonymousClass1();
    public int productId;
    public int vendorId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.application.UsbDeviceConfig$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<UsbDeviceConfig> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbDeviceConfig createFromParcel(Parcel parcel) {
            return new UsbDeviceConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UsbDeviceConfig[] newArray(int i) {
            return new UsbDeviceConfig[i];
        }
    }

    public UsbDeviceConfig() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        UsbDeviceConfig usbDeviceConfig;
        int i;
        int i2;
        return obj != null
                && (obj instanceof UsbDeviceConfig)
                && (i = (usbDeviceConfig = (UsbDeviceConfig) obj).vendorId) > 0
                && (i2 = usbDeviceConfig.productId) > 0
                && this.vendorId == i
                && this.productId == i2;
    }

    public void readFromParcel(Parcel parcel) {
        this.vendorId = parcel.readInt();
        this.productId = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.vendorId);
        parcel.writeInt(this.productId);
    }

    public UsbDeviceConfig(int i, int i2) {
        this.vendorId = i;
        this.productId = i2;
    }

    public UsbDeviceConfig(Parcel parcel) {
        readFromParcel(parcel);
    }
}
