package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zaw implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        IBinder iBinder = null;
        ConnectionResult connectionResult = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            char c = (char) readInt;
            if (c == 1) {
                i = SafeParcelReader.readInt(readInt, parcel);
            } else if (c == 2) {
                int readSize = SafeParcelReader.readSize(readInt, parcel);
                int dataPosition = parcel.dataPosition();
                if (readSize == 0) {
                    iBinder = null;
                } else {
                    iBinder = parcel.readStrongBinder();
                    parcel.setDataPosition(dataPosition + readSize);
                }
            } else if (c == 3) {
                connectionResult =
                        (ConnectionResult)
                                SafeParcelReader.createParcelable(
                                        parcel, readInt, ConnectionResult.CREATOR);
            } else if (c == 4) {
                z = SafeParcelReader.readBoolean(readInt, parcel);
            } else if (c != 5) {
                SafeParcelReader.skipUnknownField(readInt, parcel);
            } else {
                z2 = SafeParcelReader.readBoolean(readInt, parcel);
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        return new zav(i, iBinder, connectionResult, z, z2);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new zav[i];
    }
}
