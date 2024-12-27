package com.samsung.android.knox.application;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AppControlInfo implements Parcelable {
    public static final Parcelable.Creator<AppControlInfo> CREATOR = new AnonymousClass1();
    public String adminPackageName = null;
    public List<String> entries = null;
    public final Object mQueueLock = new Object();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.application.AppControlInfo$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<AppControlInfo> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppControlInfo createFromParcel(Parcel parcel) {
            return new AppControlInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppControlInfo[] newArray(int i) {
            return new AppControlInfo[i];
        }
    }

    public AppControlInfo() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        synchronized (this.mQueueLock) {
            this.adminPackageName = parcel.readString();
            this.entries = parcel.createStringArrayList();
        }
    }

    public String toString() {
        return "adminPackageName: " + this.adminPackageName + " ,appControlType: " + this.entries;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        synchronized (this.mQueueLock) {
            parcel.writeString(this.adminPackageName);
            parcel.writeStringList(this.entries);
        }
    }

    public AppControlInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
