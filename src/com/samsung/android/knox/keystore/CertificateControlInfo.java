package com.samsung.android.knox.keystore;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CertificateControlInfo implements Parcelable {
    public static final Parcelable.Creator<CertificateControlInfo> CREATOR = new AnonymousClass1();
    public String adminPackageName = null;
    public List<X509Certificate> entries = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.keystore.CertificateControlInfo$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<CertificateControlInfo> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CertificateControlInfo createFromParcel(Parcel parcel) {
            return new CertificateControlInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CertificateControlInfo[] newArray(int i) {
            return new CertificateControlInfo[i];
        }
    }

    public CertificateControlInfo() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.adminPackageName = parcel.readString();
        int readInt = parcel.readInt();
        this.entries = new ArrayList();
        for (int i = 0; i < readInt; i++) {
            this.entries.add((X509Certificate) parcel.readSerializable());
        }
    }

    public String toString() {
        return "adminPackageName: " + this.adminPackageName + " ,entries: " + this.entries;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.adminPackageName);
        List<X509Certificate> list = this.entries;
        if (list == null) {
            parcel.writeInt(0);
            return;
        }
        int size = list.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeSerializable(this.entries.get(i2));
        }
    }

    public CertificateControlInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
