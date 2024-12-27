package com.android.settings.fuelgauge.batterytip;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppInfo implements Comparable, Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public final ArraySet anomalyTypes;
    public final String packageName;
    public final long screenOnTimeMs;
    public final int uid;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.fuelgauge.batterytip.AppInfo$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new AppInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new AppInfo[i];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public final ArraySet mAnomalyTypes = new ArraySet();
        public String mPackageName;
        public long mScreenOnTimeMs;
        public int mUid;
    }

    public AppInfo(Builder builder) {
        this.packageName = builder.mPackageName;
        this.anomalyTypes = builder.mAnomalyTypes;
        this.screenOnTimeMs = builder.mScreenOnTimeMs;
        this.uid = builder.mUid;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return Long.compare(this.screenOnTimeMs, ((AppInfo) obj).screenOnTimeMs);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppInfo)) {
            return false;
        }
        AppInfo appInfo = (AppInfo) obj;
        return Objects.equals(this.anomalyTypes, appInfo.anomalyTypes)
                && this.uid == appInfo.uid
                && this.screenOnTimeMs == appInfo.screenOnTimeMs
                && TextUtils.equals(this.packageName, appInfo.packageName);
    }

    public final String toString() {
        return "packageName="
                + this.packageName
                + ",anomalyTypes="
                + this.anomalyTypes
                + ",screenTime="
                + this.screenOnTimeMs;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeArraySet(this.anomalyTypes);
        parcel.writeLong(this.screenOnTimeMs);
        parcel.writeInt(this.uid);
    }

    public AppInfo(Parcel parcel) {
        this.packageName = parcel.readString();
        this.anomalyTypes = parcel.readArraySet(null);
        this.screenOnTimeMs = parcel.readLong();
        this.uid = parcel.readInt();
    }
}
