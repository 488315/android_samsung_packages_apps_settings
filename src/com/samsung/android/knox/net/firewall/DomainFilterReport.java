package com.samsung.android.knox.net.firewall;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DomainFilterReport implements Parcelable {
    public static final Parcelable.Creator<DomainFilterReport> CREATOR = new AnonymousClass1();
    public String mDomainUrl;
    public String mPackageName;
    public long mTimeStamp;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.net.firewall.DomainFilterReport$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<DomainFilterReport> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DomainFilterReport createFromParcel(Parcel parcel) {
            return new DomainFilterReport(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DomainFilterReport[] newArray(int i) {
            return new DomainFilterReport[i];
        }
    }

    public DomainFilterReport(String str, long j, String str2) {
        this.mPackageName = str;
        this.mTimeStamp = j;
        this.mDomainUrl = str2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getDomainUrl() {
        return this.mDomainUrl;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public long getTimeStamp() {
        return this.mTimeStamp;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mTimeStamp);
        parcel.writeString(this.mDomainUrl);
    }

    public DomainFilterReport() {}

    public DomainFilterReport(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mTimeStamp = parcel.readLong();
        this.mDomainUrl = parcel.readString();
    }
}
