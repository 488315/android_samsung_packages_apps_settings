package com.github.mikephil.charting.data;

import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class Entry extends BaseEntry implements Parcelable {
    public static final Parcelable.Creator<Entry> CREATOR = new AnonymousClass1();
    public float x;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.github.mikephil.charting.data.Entry$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            Entry entry = new Entry();
            entry.y = 0.0f;
            entry.mData = null;
            entry.x = 0.0f;
            entry.x = parcel.readFloat();
            entry.y = parcel.readFloat();
            if (parcel.readInt() == 1) {
                entry.mData = parcel.readParcelable(Object.class.getClassLoader());
            }
            return entry;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Entry[i];
        }
    }

    public Entry(float f, float f2) {
        this.mData = null;
        this.y = f2;
        this.x = f;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final String toString() {
        return "Entry, x: " + this.x + " y: " + getY();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.x);
        parcel.writeFloat(getY());
        Object obj = this.mData;
        if (obj == null) {
            parcel.writeInt(0);
        } else {
            if (!(obj instanceof Parcelable)) {
                throw new ParcelFormatException("Cannot parcel an Entry with non-parcelable data");
            }
            parcel.writeInt(1);
            parcel.writeParcelable((Parcelable) this.mData, i);
        }
    }
}
