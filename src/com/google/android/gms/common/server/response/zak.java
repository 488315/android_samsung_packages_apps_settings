package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zak implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        String str = null;
        FastJsonResponse$Field fastJsonResponse$Field = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            char c = (char) readInt;
            if (c == 1) {
                i = SafeParcelReader.readInt(readInt, parcel);
            } else if (c == 2) {
                str = SafeParcelReader.createString(readInt, parcel);
            } else if (c != 3) {
                SafeParcelReader.skipUnknownField(readInt, parcel);
            } else {
                fastJsonResponse$Field =
                        (FastJsonResponse$Field)
                                SafeParcelReader.createParcelable(
                                        parcel, readInt, FastJsonResponse$Field.CREATOR);
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        return new zam(i, str, fastJsonResponse$Field);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new zam[i];
    }
}
