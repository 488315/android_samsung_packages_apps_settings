package com.samsung.android.knox.ucm.core;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ApduMessage implements Parcelable {
    public static final Parcelable.Creator<ApduMessage> CREATOR = new AnonymousClass1();
    public int errorCode;
    public byte[] message;
    public int messageType;
    public int status;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.ucm.core.ApduMessage$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<ApduMessage> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApduMessage createFromParcel(Parcel parcel) {
            return new ApduMessage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ApduMessage[] newArray(int i) {
            return new ApduMessage[i];
        }
    }

    public ApduMessage(int i, int i2, int i3, byte[] bArr) {
        this.status = i;
        this.errorCode = i2;
        this.messageType = i3;
        this.message = bArr;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [byte[], java.io.Serializable] */
    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(Integer.valueOf(this.status));
        parcel.writeSerializable(Integer.valueOf(this.errorCode));
        parcel.writeSerializable(Integer.valueOf(this.messageType));
        parcel.writeSerializable(this.message);
    }

    public ApduMessage() {
        this.status = 1;
        this.errorCode = -1;
        this.messageType = 0;
        this.message = null;
    }

    public ApduMessage(Parcel parcel) {
        this.status = 1;
        this.errorCode = -1;
        this.messageType = 0;
        this.message = null;
        this.status = ((Integer) parcel.readSerializable()).intValue();
        this.errorCode = ((Integer) parcel.readSerializable()).intValue();
        this.messageType = ((Integer) parcel.readSerializable()).intValue();
        this.message = (byte[]) parcel.readSerializable();
    }
}
