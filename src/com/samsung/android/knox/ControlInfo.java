package com.samsung.android.knox;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ControlInfo implements Parcelable {
    public static final Parcelable.Creator<ControlInfo> CREATOR = new AnonymousClass1();
    public String adminPackageName = null;
    public List<String> entries = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.ControlInfo$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<ControlInfo> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ControlInfo createFromParcel(Parcel parcel) {
            return new ControlInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ControlInfo[] newArray(int i) {
            return new ControlInfo[i];
        }
    }

    public ControlInfo() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.adminPackageName = parcel.readString();
        this.entries = parcel.createStringArrayList();
    }

    public String toString() {
        return "adminPackageName: " + this.adminPackageName + " ,appControlType: " + this.entries;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.adminPackageName);
        parcel.writeStringList(this.entries);
    }

    public ControlInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
