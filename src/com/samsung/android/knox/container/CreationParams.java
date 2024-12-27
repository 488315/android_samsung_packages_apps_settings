package com.samsung.android.knox.container;

import android.os.Parcel;
import android.os.Parcelable;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CreationParams implements Parcelable {
    public static final Parcelable.Creator<CreationParams> CREATOR = new AnonymousClass1();
    public static final String TAG = "CreationParams";
    public String mAdminPkgName;
    public String mConfigName;
    public String mPwdResetToken;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.container.CreationParams$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<CreationParams> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreationParams createFromParcel(Parcel parcel) {
            return new CreationParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreationParams[] newArray(int i) {
            return new CreationParams[i];
        }
    }

    public CreationParams() {
        this.mConfigName = null;
        this.mAdminPkgName = null;
        this.mPwdResetToken = null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAdminPackageName() {
        return this.mAdminPkgName;
    }

    public String getConfigurationName() {
        return this.mConfigName;
    }

    public String getPasswordResetToken() {
        return this.mPwdResetToken;
    }

    public void setAdminPackageName(String str) {
        this.mAdminPkgName = str;
    }

    public void setConfigurationName(String str) {
        this.mConfigName = str;
    }

    public void setPasswordResetToken(String str) {
        this.mPwdResetToken = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        String str = this.mConfigName;
        if (str != null) {
            parcel.writeString(str);
        } else {
            parcel.writeString(ApnSettings.MVNO_NONE);
        }
        String str2 = this.mAdminPkgName;
        if (str2 != null) {
            parcel.writeString(str2);
        } else {
            parcel.writeString(ApnSettings.MVNO_NONE);
        }
        String str3 = this.mPwdResetToken;
        if (str3 != null) {
            parcel.writeString(str3);
        } else {
            parcel.writeString(ApnSettings.MVNO_NONE);
        }
    }

    public CreationParams(Parcel parcel) {
        this.mConfigName = null;
        this.mAdminPkgName = null;
        this.mPwdResetToken = null;
        String readString = parcel.readString();
        this.mConfigName = readString;
        if (readString != null && readString.isEmpty()) {
            this.mConfigName = null;
        }
        String readString2 = parcel.readString();
        this.mAdminPkgName = readString2;
        if (readString2 != null && readString2.isEmpty()) {
            this.mAdminPkgName = null;
        }
        String readString3 = parcel.readString();
        this.mPwdResetToken = readString3;
        if (readString3 == null || !readString3.isEmpty()) {
            return;
        }
        this.mPwdResetToken = null;
    }
}
