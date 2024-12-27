package com.samsung.android.knox.keystore;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TUIProperty implements Parcelable {
    public static final Parcelable.Creator<TUIProperty> CREATOR = new AnonymousClass1();
    public int loginRetry = 2;
    public int loginExpirationPeriod = 120;
    public byte[] pin = null;
    public byte[] secretImage = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.keystore.TUIProperty$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<TUIProperty> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TUIProperty createFromParcel(Parcel parcel) {
            return new TUIProperty(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TUIProperty[] newArray(int i) {
            return new TUIProperty[i];
        }
    }

    public TUIProperty() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.loginRetry = parcel.readInt();
        this.loginExpirationPeriod = parcel.readInt();
        this.pin = parcel.createByteArray();
        this.secretImage = parcel.createByteArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.loginRetry);
        parcel.writeInt(this.loginExpirationPeriod);
        parcel.writeByteArray(this.pin);
        parcel.writeByteArray(this.secretImage);
    }

    public TUIProperty(Parcel parcel) {
        readFromParcel(parcel);
    }
}
