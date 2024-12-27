package com.samsung.android.knox.cmfa;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public enum AuthActionType implements Parcelable {
    PHONE_LOCK,
    PHONE_UNLOCK,
    CONTAINER_LOCK,
    CONTAINER_UNLOCK,
    LAPTOP_LOCK,
    LAPTOP_UNLOCK;

    public static final Parcelable.Creator<AuthActionType> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.cmfa.AuthActionType$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<AuthActionType> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthActionType createFromParcel(Parcel parcel) {
            return AuthActionType.valueOf(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuthActionType[] newArray(int i) {
            return new AuthActionType[i];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name());
    }
}
