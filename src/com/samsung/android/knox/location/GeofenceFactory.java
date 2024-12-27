package com.samsung.android.knox.location;

import android.os.Parcel;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GeofenceFactory {
    public static Geofence createGeofence(int i, Parcel parcel) {
        if (i == 1) {
            return new CircularGeofence(parcel);
        }
        if (i == 2) {
            return new PolygonalGeofence(parcel);
        }
        if (i != 3) {
            return null;
        }
        return new LinearGeofence(parcel);
    }
}
