package com.samsung.android.knox.location;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BoundingBox implements Serializable, Parcelable {
    public static final Parcelable.Creator<BoundingBox> CREATOR = new AnonymousClass1();
    private static final long serialVersionUID = 1;
    public LatLongPoint bottom;
    public LatLongPoint left;
    public LatLongPoint right;
    public LatLongPoint top;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.location.BoundingBox$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<BoundingBox> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BoundingBox createFromParcel(Parcel parcel) {
            return new BoundingBox(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BoundingBox[] newArray(int i) {
            return new BoundingBox[i];
        }
    }

    public BoundingBox() {
        this.left = new LatLongPoint(0.0d, 0.0d);
        this.right = new LatLongPoint(0.0d, 0.0d);
        this.top = new LatLongPoint(0.0d, 0.0d);
        this.bottom = new LatLongPoint(0.0d, 0.0d);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        Parcelable.Creator<LatLongPoint> creator = LatLongPoint.CREATOR;
        this.left = creator.createFromParcel(parcel);
        this.right = creator.createFromParcel(parcel);
        this.top = creator.createFromParcel(parcel);
        this.bottom = creator.createFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.left.writeToParcel(parcel, i);
        this.right.writeToParcel(parcel, i);
        this.top.writeToParcel(parcel, i);
        this.bottom.writeToParcel(parcel, i);
    }

    public BoundingBox(
            LatLongPoint latLongPoint,
            LatLongPoint latLongPoint2,
            LatLongPoint latLongPoint3,
            LatLongPoint latLongPoint4) {
        this.left = latLongPoint;
        this.right = latLongPoint2;
        this.top = latLongPoint3;
        this.bottom = latLongPoint4;
    }

    public BoundingBox(Parcel parcel) {
        readFromParcel(parcel);
    }
}
