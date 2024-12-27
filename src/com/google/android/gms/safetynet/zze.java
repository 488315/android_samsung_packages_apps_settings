package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zze implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        long j = 0;
        HarmfulAppsData[] harmfulAppsDataArr = null;
        int i = 0;
        boolean z = false;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            char c = (char) readInt;
            if (c == 2) {
                j = SafeParcelReader.readLong(readInt, parcel);
            } else if (c == 3) {
                harmfulAppsDataArr =
                        (HarmfulAppsData[])
                                SafeParcelReader.createTypedArray(
                                        parcel, readInt, HarmfulAppsData.CREATOR);
            } else if (c == 4) {
                i = SafeParcelReader.readInt(readInt, parcel);
            } else if (c != 5) {
                SafeParcelReader.skipUnknownField(readInt, parcel);
            } else {
                z = SafeParcelReader.readBoolean(readInt, parcel);
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        return new zzd(j, harmfulAppsDataArr, i, z);
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
        return new zzd[i];
    }
}
