package com.google.android.gms.common.internal.safeparcel;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SafeParcelWriter {
    public static void writeBundle(Parcel parcel, int i, Bundle bundle) {
        if (bundle == null) {
            return;
        }
        int zza = zza(i, parcel);
        parcel.writeBundle(bundle);
        zzb(zza, parcel);
    }

    public static void writeParcelable(Parcel parcel, int i, Parcelable parcelable, int i2) {
        if (parcelable == null) {
            return;
        }
        int zza = zza(i, parcel);
        parcelable.writeToParcel(parcel, i2);
        zzb(zza, parcel);
    }

    public static void writeString(Parcel parcel, int i, String str) {
        if (str == null) {
            return;
        }
        int zza = zza(i, parcel);
        parcel.writeString(str);
        zzb(zza, parcel);
    }

    public static void writeTypedArray(Parcel parcel, int i, Parcelable[] parcelableArr, int i2) {
        if (parcelableArr == null) {
            return;
        }
        int zza = zza(i, parcel);
        parcel.writeInt(parcelableArr.length);
        for (Parcelable parcelable : parcelableArr) {
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                int dataPosition = parcel.dataPosition();
                parcel.writeInt(1);
                int dataPosition2 = parcel.dataPosition();
                parcelable.writeToParcel(parcel, i2);
                int dataPosition3 = parcel.dataPosition();
                parcel.setDataPosition(dataPosition);
                parcel.writeInt(dataPosition3 - dataPosition2);
                parcel.setDataPosition(dataPosition3);
            }
        }
        zzb(zza, parcel);
    }

    public static void writeTypedList(Parcel parcel, List list, int i) {
        if (list == null) {
            return;
        }
        int zza = zza(i, parcel);
        int size = list.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            Parcelable parcelable = (Parcelable) list.get(i2);
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                int dataPosition = parcel.dataPosition();
                parcel.writeInt(1);
                int dataPosition2 = parcel.dataPosition();
                parcelable.writeToParcel(parcel, 0);
                int dataPosition3 = parcel.dataPosition();
                parcel.setDataPosition(dataPosition);
                parcel.writeInt(dataPosition3 - dataPosition2);
                parcel.setDataPosition(dataPosition3);
            }
        }
        zzb(zza, parcel);
    }

    public static int zza(int i, Parcel parcel) {
        parcel.writeInt(i | (-65536));
        parcel.writeInt(0);
        return parcel.dataPosition();
    }

    public static void zzb(int i, Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        parcel.setDataPosition(i - 4);
        parcel.writeInt(dataPosition - i);
        parcel.setDataPosition(dataPosition);
    }

    public static void zzc(Parcel parcel, int i, int i2) {
        parcel.writeInt(i | (i2 << 16));
    }
}
