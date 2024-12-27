package com.samsung.android.knox;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EnrollData implements Parcelable {
    public static final Parcelable.Creator<EnrollData> CREATOR = new AnonymousClass1();
    public static final String TAG = "EnrollData";
    public byte[] signature;
    public int policyBitMask = 0;
    public String comment = null;
    public String pkgName = null;
    public int constrainedState = 0;
    public String downloadUrl = null;
    public String targetPkgName = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.EnrollData$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<EnrollData> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnrollData createFromParcel(Parcel parcel) {
            return new EnrollData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnrollData[] newArray(int i) {
            return new EnrollData[i];
        }
    }

    public EnrollData() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getComment() {
        return this.comment;
    }

    public int getConstrainedState() {
        return this.constrainedState;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public String getPackageName() {
        return this.pkgName;
    }

    public int getPolicyBitMask() {
        return this.policyBitMask;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public String getTargetPkgName() {
        return this.targetPkgName;
    }

    public void readFromParcel(Parcel parcel) {
        this.pkgName = parcel.readString();
        this.comment = parcel.readString();
        this.policyBitMask = parcel.readInt();
        this.constrainedState = parcel.readInt();
        this.downloadUrl = parcel.readString();
        int readInt = parcel.readInt();
        if (readInt != 0) {
            byte[] bArr = new byte[readInt];
            this.signature = bArr;
            parcel.readByteArray(bArr);
        }
        this.targetPkgName = parcel.readString();
    }

    public void setData(
            String str, String str2, int i, String str3, int i2, byte[] bArr, String str4) {
        this.pkgName = str;
        this.comment = str2;
        this.policyBitMask = i;
        this.downloadUrl = str3;
        this.constrainedState = i2;
        this.signature = bArr;
        this.targetPkgName = str4;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.pkgName);
        parcel.writeString(this.comment);
        parcel.writeInt(this.policyBitMask);
        parcel.writeInt(this.constrainedState);
        parcel.writeString(this.downloadUrl);
        byte[] bArr = this.signature;
        if (bArr == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(bArr.length);
            parcel.writeByteArray(this.signature);
        }
        parcel.writeString(this.targetPkgName);
    }

    public EnrollData(Parcel parcel) {
        readFromParcel(parcel);
    }
}
