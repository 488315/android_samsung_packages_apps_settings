package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zaq implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        Parcel parcel2 = null;
        zan zanVar = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            char c = (char) readInt;
            if (c == 1) {
                i = SafeParcelReader.readInt(readInt, parcel);
            } else if (c == 2) {
                int readSize = SafeParcelReader.readSize(readInt, parcel);
                int dataPosition = parcel.dataPosition();
                if (readSize == 0) {
                    parcel2 = null;
                } else {
                    Parcel obtain = Parcel.obtain();
                    obtain.appendFrom(parcel, dataPosition, readSize);
                    parcel.setDataPosition(dataPosition + readSize);
                    parcel2 = obtain;
                }
            } else if (c != 3) {
                SafeParcelReader.skipUnknownField(readInt, parcel);
            } else {
                zanVar = (zan) SafeParcelReader.createParcelable(parcel, readInt, zan.CREATOR);
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        return new SafeParcelResponse(i, parcel2, zanVar);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new SafeParcelResponse[i];
    }
}
