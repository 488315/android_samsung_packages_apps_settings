package com.samsung.android.knox.keystore;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Deprecated
/* loaded from: classes4.dex */
public class CSRProfile implements Parcelable {
    public static final Parcelable.Creator<CSRProfile> CREATOR = new AnonymousClass1();
    public String commonName;
    public String country;
    public CSRFormat csrFormat;
    public String domainComponent;
    public String emailAddress;
    public KeyAlgorithm keyAlgType;
    public int keyLength;
    public String locality;
    public String organization;
    public ProfileType profileType;
    public String state;
    public String templateName;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.keystore.CSRProfile$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<CSRProfile> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CSRProfile createFromParcel(Parcel parcel) {
            return new CSRProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CSRProfile[] newArray(int i) {
            return new CSRProfile[i];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum CSRFormat {
        PKCS10,
        CRMF,
        PROPRIETARY
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum KeyAlgorithm {
        RSA,
        ECC
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum ProfileType {
        SCEP,
        CMP,
        CMC,
        PROPRIETARY
    }

    public CSRProfile() {
        ProfileType profileType = ProfileType.SCEP;
        this.profileType = profileType;
        CSRFormat cSRFormat = CSRFormat.PKCS10;
        this.csrFormat = cSRFormat;
        KeyAlgorithm keyAlgorithm = KeyAlgorithm.RSA;
        this.templateName = null;
        this.keyLength = 1024;
        this.commonName = null;
        this.organization = null;
        this.domainComponent = null;
        this.emailAddress = null;
        this.country = null;
        this.state = null;
        this.locality = null;
        this.profileType = profileType;
        this.csrFormat = cSRFormat;
        this.keyAlgType = keyAlgorithm;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ProfileType profileType = this.profileType;
        if (profileType == null) {
            parcel.writeString(ProfileType.SCEP.name());
        } else {
            parcel.writeString(profileType.name());
        }
        CSRFormat cSRFormat = this.csrFormat;
        if (cSRFormat == null) {
            parcel.writeString(CSRFormat.PKCS10.name());
        } else {
            parcel.writeString(cSRFormat.name());
        }
        KeyAlgorithm keyAlgorithm = this.keyAlgType;
        if (keyAlgorithm == null) {
            parcel.writeString(KeyAlgorithm.RSA.name());
        } else {
            parcel.writeString(keyAlgorithm.name());
        }
        parcel.writeString(this.templateName);
        parcel.writeInt(this.keyLength);
        parcel.writeString(this.commonName);
        parcel.writeString(this.organization);
        parcel.writeString(this.domainComponent);
        parcel.writeString(this.emailAddress);
        parcel.writeString(this.country);
        parcel.writeString(this.state);
        parcel.writeString(this.locality);
    }

    public CSRProfile(Parcel parcel) {
        this.profileType = ProfileType.SCEP;
        this.csrFormat = CSRFormat.PKCS10;
        this.keyAlgType = KeyAlgorithm.RSA;
        this.templateName = null;
        this.keyLength = 1024;
        this.commonName = null;
        this.organization = null;
        this.domainComponent = null;
        this.emailAddress = null;
        this.country = null;
        this.state = null;
        this.locality = null;
        try {
            this.profileType = ProfileType.valueOf(parcel.readString());
        } catch (IllegalArgumentException e) {
            this.profileType = null;
            e.printStackTrace();
        }
        if (this.profileType == null) {
            this.profileType = ProfileType.SCEP;
        }
        try {
            this.csrFormat = CSRFormat.valueOf(parcel.readString());
        } catch (IllegalArgumentException e2) {
            this.csrFormat = null;
            e2.printStackTrace();
        }
        if (this.csrFormat == null) {
            this.csrFormat = CSRFormat.PKCS10;
        }
        try {
            this.keyAlgType = KeyAlgorithm.valueOf(parcel.readString());
        } catch (IllegalArgumentException e3) {
            this.keyAlgType = null;
            e3.printStackTrace();
        }
        if (this.keyAlgType == null) {
            this.keyAlgType = KeyAlgorithm.RSA;
        }
        this.templateName = parcel.readString();
        this.keyLength = parcel.readInt();
        this.commonName = parcel.readString();
        this.organization = parcel.readString();
        this.domainComponent = parcel.readString();
        this.emailAddress = parcel.readString();
        this.country = parcel.readString();
        this.state = parcel.readString();
        this.locality = parcel.readString();
    }
}
