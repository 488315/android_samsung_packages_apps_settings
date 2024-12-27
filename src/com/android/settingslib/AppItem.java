package com.android.settingslib;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppItem implements Comparable, Parcelable {
    public static final Parcelable.Creator<AppItem> CREATOR = new AnonymousClass1();
    public int category;
    public final int key;
    public boolean restricted;
    public String restrictedPackageName;
    public long total;
    public final SparseBooleanArray uids;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.AppItem$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new AppItem(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new AppItem[i];
        }
    }

    public AppItem(int i) {
        this.uids = new SparseBooleanArray();
        this.key = i;
    }

    public final void addUid(int i) {
        this.uids.put(i, true);
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        AppItem appItem = (AppItem) obj;
        int compare = Integer.compare(this.category, appItem.category);
        return compare == 0 ? Long.compare(appItem.total, this.total) : compare;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.key);
        parcel.writeSparseBooleanArray(this.uids);
        parcel.writeLong(this.total);
        parcel.writeByte(this.restricted ? (byte) 1 : (byte) 0);
        parcel.writeString(this.restrictedPackageName);
    }

    public AppItem(Parcel parcel) {
        this.uids = new SparseBooleanArray();
        this.key = parcel.readInt();
        this.uids = parcel.readSparseBooleanArray();
        this.total = parcel.readLong();
        this.restricted = parcel.readByte() != 0;
        this.restrictedPackageName = parcel.readString();
    }
}
