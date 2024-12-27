package com.samsung.android.knox.ddar;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class Secret implements Parcelable {
    public static final Parcelable.Creator<Secret> CREATOR =
            new Parcelable.Creator<Secret>() { // from class: com.samsung.android.knox.ddar.Secret.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Secret createFromParcel(Parcel parcel) {
                    return new Secret(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Secret[] newArray(int i) {
                    return new Secret[i];
                }
            };
    public static final int MAX_SECRET_ALIAS_LEN = 32;
    public static final int MAX_SECRET_LEN = 128;
    public String alias;
    public byte[] data;

    public Secret(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt != -1) {
            byte[] bArr = new byte[readInt];
            this.data = bArr;
            parcel.readByteArray(bArr);
        } else {
            this.data = null;
        }
        this.alias = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte[] bArr = this.data;
        if (bArr != null) {
            parcel.writeInt(bArr.length);
        }
        parcel.writeByteArray(this.data);
        parcel.writeString(this.alias);
    }

    public Secret(String str, byte[] bArr) {
        this.alias = str;
        this.data = bArr;
    }
}
