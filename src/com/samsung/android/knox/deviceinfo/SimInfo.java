package com.samsung.android.knox.deviceinfo;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SimInfo implements Parcelable {
    public static final Parcelable.Creator<SimInfo> CREATOR = new AnonymousClass1();
    public String countryIso;
    public String operator;
    public String operatorName;
    public String phoneNumber;
    public String serialNumber;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.deviceinfo.SimInfo$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<SimInfo> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SimInfo createFromParcel(Parcel parcel) {
            return new SimInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SimInfo[] newArray(int i) {
            return new SimInfo[i];
        }
    }

    public SimInfo() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.serialNumber = parcel.readString();
        this.phoneNumber = parcel.readString();
        this.operator = parcel.readString();
        this.operatorName = parcel.readString();
        this.countryIso = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.serialNumber);
        parcel.writeString(this.phoneNumber);
        parcel.writeString(this.operator);
        parcel.writeString(this.operatorName);
        parcel.writeString(this.countryIso);
    }

    public SimInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
