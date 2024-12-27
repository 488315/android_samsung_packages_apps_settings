package com.samsung.android.knox.keystore;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Deprecated
/* loaded from: classes4.dex */
public abstract class EnrollmentProfile implements Parcelable {
    public static final Parcelable.Creator<EnrollmentProfile> CREATOR = new AnonymousClass1();
    public String certificateAlias;
    public Bundle credentialStorageBundle = null;
    public Bundle csrExtra = null;
    public String hashAlgorithmType;
    public String keyPairAlgorithm;
    public int keySize;
    public String keystoreType;
    public String profileType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.keystore.EnrollmentProfile$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<EnrollmentProfile> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnrollmentProfile createFromParcel(Parcel parcel) {
            String readString = parcel.readString();
            if (readString.equals(SCEPProfile.class.getName())) {
                return new SCEPProfile(parcel);
            }
            if (readString.equals(CMCProfile.class.getName())) {
                return new CMCProfile(parcel);
            }
            if (readString.equals(CMPProfile.class.getName())) {
                return new CMPProfile(parcel);
            }
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnrollmentProfile[] newArray(int i) {
            return new EnrollmentProfile[i];
        }
    }

    public String getCertificateAlias() {
        return this.certificateAlias;
    }

    public String getKeyPairAlgorithm() {
        return this.keyPairAlgorithm;
    }

    public int getKeySize() {
        return this.keySize;
    }

    public String getKeystoreType() {
        return this.keystoreType;
    }

    public abstract String getProfileType();

    public void setCertificateAlias(String str) {
        this.certificateAlias = str;
    }

    public void setKeyPairAlgorithm(String str) {
        this.keyPairAlgorithm = str;
    }

    public void setKeySize(int i) {
        this.keySize = i;
    }

    public void setKeystoreType(String str) {
        this.keystoreType = str;
    }
}
