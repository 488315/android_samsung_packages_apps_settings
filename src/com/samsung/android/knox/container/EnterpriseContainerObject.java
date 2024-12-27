package com.samsung.android.knox.container;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EnterpriseContainerObject implements EnterpriseContainerConstants, Parcelable {
    public static final Parcelable.Creator<EnterpriseContainerObject> CREATOR =
            new AnonymousClass1();
    public int admin;
    public int containerType;
    public String email;
    public int id;
    public int lockType;
    public String name;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.container.EnterpriseContainerObject$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<EnterpriseContainerObject> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterpriseContainerObject createFromParcel(Parcel parcel) {
            return new EnterpriseContainerObject(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterpriseContainerObject[] newArray(int i) {
            Log.d("EnterpriseContainerObject", "EnterpriseContainerObject[] array to be created");
            return new EnterpriseContainerObject[i];
        }
    }

    public EnterpriseContainerObject() {
        this.id = -1;
        this.admin = -1;
        this.email = null;
        this.name = null;
        this.lockType = 0;
        this.containerType = 0;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getContainerAdmin() {
        return this.admin;
    }

    public String getContainerEmail() {
        return this.email;
    }

    public int getContainerId() {
        return this.id;
    }

    public int getContainerLockType() {
        return this.lockType;
    }

    public String getContainerName() {
        return this.name;
    }

    public int getContainerType() {
        return this.containerType;
    }

    public void setContainerAdmin(int i) {
        this.admin = i;
    }

    public void setContainerEmail(String str) {
        this.email = str;
    }

    public void setContainerId(int i) {
        this.id = i;
    }

    public void setContainerLockType(int i) {
        this.lockType = i;
    }

    public void setContainerName(String str) {
        this.name = str;
    }

    public void setContainerType(int i) {
        this.containerType = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.admin);
        String str = this.email;
        if (str != null) {
            parcel.writeString(str);
        } else {
            parcel.writeString(ApnSettings.MVNO_NONE);
        }
        String str2 = this.name;
        if (str2 != null) {
            parcel.writeString(str2);
        } else {
            parcel.writeString(ApnSettings.MVNO_NONE);
        }
        parcel.writeInt(this.lockType);
        parcel.writeInt(this.containerType);
    }

    public EnterpriseContainerObject(Parcel parcel) {
        this.id = -1;
        this.admin = -1;
        this.email = null;
        this.name = null;
        this.lockType = 0;
        this.containerType = 0;
        this.id = parcel.readInt();
        this.admin = parcel.readInt();
        this.email = parcel.readString();
        this.name = parcel.readString();
        this.lockType = parcel.readInt();
        this.containerType = parcel.readInt();
    }
}
