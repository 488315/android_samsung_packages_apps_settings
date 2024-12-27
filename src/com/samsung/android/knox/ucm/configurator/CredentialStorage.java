package com.samsung.android.knox.ucm.configurator;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CredentialStorage implements Parcelable {
    public static final Parcelable.Creator<CredentialStorage> CREATOR = new AnonymousClass1();
    public Bundle bundle;
    public String manufacturer;
    public String name;
    public String packageName;
    public String signature;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.ucm.configurator.CredentialStorage$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<CredentialStorage> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CredentialStorage createFromParcel(Parcel parcel) {
            return new CredentialStorage(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CredentialStorage[] newArray(int i) {
            return new CredentialStorage[i];
        }
    }

    public CredentialStorage() {
        this.name = null;
        this.manufacturer = null;
        this.packageName = null;
        this.bundle = null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.manufacturer);
        parcel.writeString(this.packageName);
        parcel.writeParcelable(this.bundle, i);
        parcel.writeString(this.signature);
    }

    public CredentialStorage(Parcel parcel) {
        this.name = null;
        this.manufacturer = null;
        this.packageName = null;
        this.bundle = null;
        this.name = parcel.readString();
        this.manufacturer = parcel.readString();
        this.packageName = parcel.readString();
        this.bundle = (Bundle) parcel.readParcelable(Bundle.class.getClassLoader());
        this.signature = parcel.readString();
    }
}
