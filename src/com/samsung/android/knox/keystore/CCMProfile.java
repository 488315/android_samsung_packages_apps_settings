package com.samsung.android.knox.keystore;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CCMProfile implements Parcelable {
    public static final Parcelable.Creator<CCMProfile> CREATOR = new AnonymousClass1();
    public AccessControlMethod accessControlMethod;
    public String accessControlPassword;
    public List<String> packageList;
    public int tuiPinLength;
    public TUIProperty tuiProperty;
    public boolean whiteListAllPackages;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.keystore.CCMProfile$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<CCMProfile> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CCMProfile createFromParcel(Parcel parcel) {
            return new CCMProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CCMProfile[] newArray(int i) {
            return new CCMProfile[i];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum AccessControlMethod {
        LOCK_STATE(0),
        PASSWORD(1),
        TRUSTED_UI(2),
        TRUSTED_PINPAD(3),
        AFW(15),
        KNOX_DEFAULT(16),
        USER_AUTH(17);

        private int value;

        AccessControlMethod(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }
    }

    public CCMProfile() {
        AccessControlMethod accessControlMethod = AccessControlMethod.LOCK_STATE;
        this.accessControlMethod = accessControlMethod;
        this.packageList = new ArrayList();
        this.whiteListAllPackages = false;
        this.accessControlPassword = null;
        this.tuiPinLength = 6;
        this.tuiProperty = null;
        this.accessControlMethod = accessControlMethod;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        try {
            AccessControlMethod valueOf = AccessControlMethod.valueOf(parcel.readString());
            this.accessControlMethod = valueOf;
            if (valueOf == null) {
                this.accessControlMethod = AccessControlMethod.LOCK_STATE;
            }
            this.accessControlPassword = parcel.readString();
            this.whiteListAllPackages = parcel.readInt() != 0;
            parcel.readStringList(this.packageList);
            this.tuiProperty = (TUIProperty) parcel.readParcelable(getClass().getClassLoader());
        } catch (IllegalArgumentException e) {
            this.accessControlMethod = null;
            e.printStackTrace();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        AccessControlMethod accessControlMethod = this.accessControlMethod;
        if (accessControlMethod == null) {
            parcel.writeString(AccessControlMethod.LOCK_STATE.name());
        } else {
            parcel.writeString(accessControlMethod.name());
        }
        parcel.writeString(this.accessControlPassword);
        parcel.writeInt(this.whiteListAllPackages ? 1 : 0);
        parcel.writeStringList(this.packageList);
        parcel.writeParcelable(this.tuiProperty, i);
    }

    public CCMProfile(AccessControlMethod accessControlMethod) {
        this.accessControlMethod = AccessControlMethod.LOCK_STATE;
        this.packageList = new ArrayList();
        this.whiteListAllPackages = false;
        this.accessControlPassword = null;
        this.tuiPinLength = 6;
        this.tuiProperty = null;
        this.accessControlMethod = accessControlMethod;
    }

    public CCMProfile(Parcel parcel) {
        this.accessControlMethod = AccessControlMethod.LOCK_STATE;
        this.packageList = new ArrayList();
        this.whiteListAllPackages = false;
        this.accessControlPassword = null;
        this.tuiPinLength = 6;
        this.tuiProperty = null;
        readFromParcel(parcel);
    }
}
