package com.samsung.android.knox.net.nap;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class Profile implements Parcelable {
    public static final Parcelable.Creator<Profile> CREATOR = new AnonymousClass1();
    public int activationState;
    public String jsonProfile;
    public int userId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.net.nap.Profile$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<Profile> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Profile createFromParcel(Parcel parcel) {
            return new Profile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Profile[] newArray(int i) {
            return new Profile[i];
        }
    }

    public Profile(int i, String str, int i2) {
        this.jsonProfile = str;
        this.userId = i2;
        this.activationState = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getActivationState() {
        return this.activationState;
    }

    public String getJsonProfile() {
        return this.jsonProfile;
    }

    public int getUserId() {
        return this.userId;
    }

    public final void readFromParcel(Parcel parcel) {
        this.jsonProfile = parcel.readString();
        this.userId = parcel.readInt();
        this.activationState = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.jsonProfile);
        parcel.writeInt(this.userId);
        parcel.writeInt(this.activationState);
    }

    public Profile(Parcel parcel) {
        readFromParcel(parcel);
    }
}
