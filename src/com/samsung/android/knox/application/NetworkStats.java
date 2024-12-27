package com.samsung.android.knox.application;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NetworkStats implements Parcelable {
    public static final Parcelable.Creator<NetworkStats> CREATOR = new AnonymousClass1();
    public int uid = 0;
    public long wifiRxBytes = 0;
    public long wifiTxBytes = 0;
    public long mobileRxBytes = 0;
    public long mobileTxBytes = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.application.NetworkStats$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<NetworkStats> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkStats createFromParcel(Parcel parcel) {
            return new NetworkStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkStats[] newArray(int i) {
            return new NetworkStats[i];
        }
    }

    public NetworkStats() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.uid = parcel.readInt();
        this.wifiRxBytes = parcel.readLong();
        this.wifiTxBytes = parcel.readLong();
        this.mobileRxBytes = parcel.readLong();
        this.mobileTxBytes = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.uid);
        parcel.writeLong(this.wifiRxBytes);
        parcel.writeLong(this.wifiTxBytes);
        parcel.writeLong(this.mobileRxBytes);
        parcel.writeLong(this.mobileTxBytes);
    }

    public NetworkStats(Parcel parcel) {
        readFromParcel(parcel);
    }
}
