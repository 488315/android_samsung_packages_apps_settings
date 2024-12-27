package com.samsung.android.knox.ucm.core;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ucmRetParcelable implements Parcelable {
    public static final Parcelable.Creator<ucmRetParcelable> CREATOR = new AnonymousClass1();
    public byte[] mData;
    public int mDataLength;
    public int mResult;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.ucm.core.ucmRetParcelable$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<ucmRetParcelable> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ucmRetParcelable createFromParcel(Parcel parcel) {
            return new ucmRetParcelable(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ucmRetParcelable[] newArray(int i) {
            return new ucmRetParcelable[i];
        }
    }

    public ucmRetParcelable(int i, byte[] bArr) {
        this.mResult = i;
        this.mData = bArr;
        if (bArr != null) {
            this.mDataLength = bArr.length;
        } else {
            this.mDataLength = 0;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.mResult = parcel.readInt();
        this.mDataLength = parcel.readInt();
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder(
                        "UCMERRORTESTING: @ucmRetParcelable readFromParcel: dateLength = "),
                this.mDataLength,
                "ucmRetParcelable");
        int i = this.mDataLength;
        if (i > 0) {
            byte[] bArr = new byte[i];
            this.mData = bArr;
            parcel.readByteArray(bArr);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mResult);
        parcel.writeInt(this.mDataLength);
        if (this.mDataLength > 0) {
            parcel.writeByteArray(this.mData);
        }
    }

    public ucmRetParcelable(Parcel parcel) {
        readFromParcel(parcel);
    }
}
