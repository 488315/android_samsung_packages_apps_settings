package com.samsung.android.knox.zt.service;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.cert.Certificate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ParcelableCertificate implements Parcelable {
    public static final Parcelable.Creator<ParcelableCertificate> CREATOR = new AnonymousClass1();
    public Certificate mCertificate;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.zt.service.ParcelableCertificate$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<ParcelableCertificate> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableCertificate createFromParcel(Parcel parcel) {
            return new ParcelableCertificate(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableCertificate[] newArray(int i) {
            return new ParcelableCertificate[i];
        }
    }

    public ParcelableCertificate(Certificate certificate) {
        this.mCertificate = certificate;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Certificate getCertificate() {
        return this.mCertificate;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this.mCertificate);
            objectOutputStream.flush();
            objectOutputStream.close();
            parcel.writeByteArray(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ParcelableCertificate(Parcel parcel) {
        try {
            this.mCertificate =
                    (Certificate)
                            new ObjectInputStream(
                                            new ByteArrayInputStream(parcel.createByteArray()))
                                    .readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
