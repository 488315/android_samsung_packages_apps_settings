package com.android.settingslib.drawer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DashboardCategory implements Parcelable {
    public static final Parcelable.Creator<DashboardCategory> CREATOR = new AnonymousClass1();
    public final String key;
    public final List mTiles = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.drawer.DashboardCategory$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new DashboardCategory(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new DashboardCategory[i];
        }
    }

    public DashboardCategory(String str) {
        this.key = str;
    }

    public final synchronized void addTile(Tile tile) {
        ((ArrayList) this.mTiles).add(tile);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final synchronized List getTiles() {
        ArrayList arrayList;
        arrayList = new ArrayList(this.mTiles.size());
        Iterator it = this.mTiles.iterator();
        while (it.hasNext()) {
            arrayList.add((Tile) it.next());
        }
        return arrayList;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.key);
        int size = ((ArrayList) this.mTiles).size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            ((Tile) ((ArrayList) this.mTiles).get(i2)).writeToParcel(parcel, i);
        }
    }

    public DashboardCategory(Parcel parcel) {
        this.key = parcel.readString();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mTiles.add(Tile.CREATOR.createFromParcel(parcel));
        }
    }
}
