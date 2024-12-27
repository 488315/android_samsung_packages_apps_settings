package com.samsung.android.knox;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AppIdentity implements Parcelable, Serializable {
    public static final Parcelable.Creator<AppIdentity> CREATOR = new AnonymousClass1();
    private static final long serialVersionUID = 1;
    private String packageName;
    private String signature;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.AppIdentity$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<AppIdentity> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppIdentity createFromParcel(Parcel parcel) {
            return new AppIdentity(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppIdentity[] newArray(int i) {
            return new AppIdentity[i];
        }
    }

    public AppIdentity() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public String getSignature() {
        return this.signature;
    }

    public final void readFromParcel(Parcel parcel) {
        this.packageName = parcel.readString();
        this.signature = parcel.readString();
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setSignature(String str) {
        this.signature = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.signature);
    }

    public AppIdentity(String str, String str2) {
        this.packageName = str;
        this.signature = str2;
    }

    public AppIdentity(Parcel parcel) {
        readFromParcel(parcel);
    }
}
