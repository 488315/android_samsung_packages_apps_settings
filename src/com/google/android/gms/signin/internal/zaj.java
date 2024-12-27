package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.common.internal.zat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zaj implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        zat zatVar = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            char c = (char) readInt;
            if (c == 1) {
                i = SafeParcelReader.readInt(readInt, parcel);
            } else if (c != 2) {
                SafeParcelReader.skipUnknownField(readInt, parcel);
            } else {
                zatVar = (zat) SafeParcelReader.createParcelable(parcel, readInt, zat.CREATOR);
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        return new zai(i, zatVar);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new zai[i];
    }
}
