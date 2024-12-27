package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzc implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String str = null;
        int i = 0;
        byte[] bArr = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            char c = (char) readInt;
            if (c == 2) {
                str = SafeParcelReader.createString(readInt, parcel);
            } else if (c == 3) {
                bArr = SafeParcelReader.createByteArray(readInt, parcel);
            } else if (c != 4) {
                SafeParcelReader.skipUnknownField(readInt, parcel);
            } else {
                i = SafeParcelReader.readInt(readInt, parcel);
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        return new HarmfulAppsData(i, str, bArr);
    }

    @Override // android.os.Parcelable.Creator
    public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
        return new HarmfulAppsData[i];
    }
}
