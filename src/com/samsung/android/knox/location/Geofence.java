package com.samsung.android.knox.location;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class Geofence implements Parcelable {
    public static final Parcelable.Creator<Geofence> CREATOR = new AnonymousClass1();
    public int id;
    public int type;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.location.Geofence$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<Geofence> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Geofence[] newArray(int i) {
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Geofence createFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            parcel.setDataPosition(dataPosition);
            return GeofenceFactory.createGeofence(readInt, parcel);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.type = parcel.readInt();
        this.id = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.type);
        parcel.writeInt(this.id);
    }
}
