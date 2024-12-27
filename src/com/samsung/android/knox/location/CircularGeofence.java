package com.samsung.android.knox.location;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CircularGeofence extends Geofence implements Parcelable, Serializable {
    private static final long serialVersionUID = 1;
    public LatLongPoint center;
    public double radius;

    public CircularGeofence(LatLongPoint latLongPoint, double d) {
        this.type = 1;
        this.center = latLongPoint;
        this.radius = d;
    }

    @Override // com.samsung.android.knox.location.Geofence, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.knox.location.Geofence
    public void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.center = LatLongPoint.CREATOR.createFromParcel(parcel);
        this.radius = parcel.readDouble();
    }

    @Override // com.samsung.android.knox.location.Geofence, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        this.center.writeToParcel(parcel, i);
        parcel.writeDouble(this.radius);
    }

    public CircularGeofence(Parcel parcel) {
        readFromParcel(parcel);
    }
}
