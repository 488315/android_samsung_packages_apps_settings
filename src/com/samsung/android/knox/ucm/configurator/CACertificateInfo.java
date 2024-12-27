package com.samsung.android.knox.ucm.configurator;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CACertificateInfo implements Parcelable {
    public static final Parcelable.Creator<CACertificateInfo> CREATOR = new AnonymousClass1();
    public int certLength;
    public byte[] certificate = null;
    public Bundle bundle = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.ucm.configurator.CACertificateInfo$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<CACertificateInfo> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CACertificateInfo createFromParcel(Parcel parcel) {
            return new CACertificateInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CACertificateInfo[] newArray(int i) {
            return new CACertificateInfo[i];
        }
    }

    public CACertificateInfo() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        this.bundle = (Bundle) parcel.readParcelable(Bundle.class.getClassLoader());
        int readInt = parcel.readInt();
        this.certLength = readInt;
        if (readInt > 0) {
            byte[] bArr = new byte[readInt];
            this.certificate = bArr;
            parcel.readByteArray(bArr);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (parcel != null) {
            parcel.writeParcelable(this.bundle, i);
            parcel.writeInt(this.certLength);
            if (this.certLength > 0) {
                parcel.writeByteArray(this.certificate);
            }
        }
    }

    public CACertificateInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
