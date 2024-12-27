package com.samsung.android.knox.location;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LatLongPoint implements Serializable, Parcelable {
    public static final Parcelable.Creator<LatLongPoint> CREATOR = new AnonymousClass1();
    private static final long serialVersionUID = 1;
    public double latitude;
    public double longitude;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.location.LatLongPoint$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<LatLongPoint> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LatLongPoint createFromParcel(Parcel parcel) {
            return new LatLongPoint(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LatLongPoint[] newArray(int i) {
            return new LatLongPoint[i];
        }
    }

    public LatLongPoint(double d, double d2) {
        this.latitude = d;
        this.longitude = d2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.latitude = parcel.readDouble();
        this.longitude = parcel.readDouble();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.latitude);
        parcel.writeDouble(this.longitude);
    }

    public LatLongPoint(Parcel parcel) {
        readFromParcel(parcel);
    }
}
