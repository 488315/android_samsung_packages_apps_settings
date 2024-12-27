package com.samsung.android.knox.application;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AppInfoLastUsage extends AppInfo implements Parcelable {
    public static final Parcelable.Creator<AppInfoLastUsage> CREATOR = new AnonymousClass1();
    public int launchCountPerMonth = -1;
    public long lastAppUsage = -1;
    public long lastLaunchTime = -1;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.application.AppInfoLastUsage$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<AppInfoLastUsage> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppInfoLastUsage createFromParcel(Parcel parcel) {
            return new AppInfoLastUsage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppInfoLastUsage[] newArray(int i) {
            return new AppInfoLastUsage[i];
        }
    }

    public AppInfoLastUsage() {}

    @Override // com.samsung.android.knox.application.AppInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.knox.application.AppInfo
    public void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.launchCountPerMonth = parcel.readInt();
        this.lastAppUsage = parcel.readLong();
        this.lastLaunchTime = parcel.readLong();
    }

    @Override // com.samsung.android.knox.application.AppInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.launchCountPerMonth);
        parcel.writeLong(this.lastAppUsage);
        parcel.writeLong(this.lastLaunchTime);
    }

    public AppInfoLastUsage(Parcel parcel) {
        readFromParcel(parcel);
    }
}
