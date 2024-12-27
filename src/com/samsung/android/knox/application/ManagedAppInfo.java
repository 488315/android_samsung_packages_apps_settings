package com.samsung.android.knox.application;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ManagedAppInfo implements Parcelable {
    public static final Parcelable.Creator<ManagedAppInfo> CREATOR = new AnonymousClass1();
    public String mAppPkg = null;
    public int mAppInstallCount = -1;
    public int mAppUninstallCount = -1;
    public int mAppDisabled = -1;
    public int mAppInstallationDisabled = -1;
    public int mAppUninstallationDisabled = -1;
    public int mControlStateDisableCause = 0;
    public String mControlStateDisableCauseMetadata = null;
    public int mIsEnterpriseApp = -1;
    public int mIsInstallSourceMDM = -1;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.application.ManagedAppInfo$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<ManagedAppInfo> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ManagedAppInfo createFromParcel(Parcel parcel) {
            return new ManagedAppInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ManagedAppInfo[] newArray(int i) {
            return new ManagedAppInfo[i];
        }
    }

    public ManagedAppInfo() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.mAppPkg = parcel.readString();
        this.mAppInstallCount = parcel.readInt();
        this.mAppUninstallCount = parcel.readInt();
        this.mAppDisabled = parcel.readInt();
        this.mAppInstallationDisabled = parcel.readInt();
        this.mAppUninstallationDisabled = parcel.readInt();
        this.mControlStateDisableCause = parcel.readInt();
        this.mControlStateDisableCauseMetadata = parcel.readString();
        this.mIsEnterpriseApp = parcel.readInt();
        this.mIsInstallSourceMDM = parcel.readInt();
    }

    public String toString() {
        return "pkg: "
                + this.mAppPkg
                + " ,InstallCount: "
                + this.mAppInstallCount
                + ", UninstallCount: "
                + this.mAppUninstallCount
                + ", mAppDisabled: "
                + this.mAppDisabled
                + ", mAppInstallationDisabled: "
                + this.mAppInstallationDisabled
                + ", mAppUninstallationDisabled: "
                + this.mAppUninstallationDisabled;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mAppPkg);
        parcel.writeInt(this.mAppInstallCount);
        parcel.writeInt(this.mAppUninstallCount);
        parcel.writeInt(this.mAppDisabled);
        parcel.writeInt(this.mAppInstallationDisabled);
        parcel.writeInt(this.mAppUninstallationDisabled);
        parcel.writeInt(this.mControlStateDisableCause);
        parcel.writeString(this.mControlStateDisableCauseMetadata);
        parcel.writeInt(this.mIsEnterpriseApp);
        parcel.writeInt(this.mIsInstallSourceMDM);
    }

    public ManagedAppInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
