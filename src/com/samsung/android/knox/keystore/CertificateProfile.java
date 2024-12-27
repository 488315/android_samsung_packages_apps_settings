package com.samsung.android.knox.keystore;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CertificateProfile implements Parcelable {
    public static final Parcelable.Creator<CertificateProfile> CREATOR = new AnonymousClass1();
    public String alias;
    public boolean allowAllPackages;
    public boolean allowRawSigning;
    public boolean allowWiFi;
    public boolean isCSRResponse;
    public List<String> packageList;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.keystore.CertificateProfile$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<CertificateProfile> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CertificateProfile createFromParcel(Parcel parcel) {
            return new CertificateProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CertificateProfile[] newArray(int i) {
            return new CertificateProfile[i];
        }
    }

    public CertificateProfile() {
        this.isCSRResponse = false;
        this.alias = null;
        this.packageList = new ArrayList();
        this.allowWiFi = false;
        this.allowAllPackages = false;
        this.allowRawSigning = false;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.isCSRResponse ? 1 : 0);
        parcel.writeString(this.alias);
        parcel.writeStringList(this.packageList);
        parcel.writeInt(this.allowWiFi ? 1 : 0);
        parcel.writeInt(this.allowAllPackages ? 1 : 0);
        parcel.writeInt(this.allowRawSigning ? 1 : 0);
    }

    public CertificateProfile(Parcel parcel) {
        this.isCSRResponse = false;
        this.alias = null;
        this.packageList = new ArrayList();
        this.allowWiFi = false;
        this.allowAllPackages = false;
        this.allowRawSigning = false;
        this.isCSRResponse = parcel.readInt() != 0;
        this.alias = parcel.readString();
        parcel.readStringList(this.packageList);
        this.allowWiFi = parcel.readInt() != 0;
        this.allowAllPackages = parcel.readInt() != 0;
        this.allowRawSigning = parcel.readInt() != 0;
    }
}
