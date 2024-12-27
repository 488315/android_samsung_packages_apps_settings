package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzb implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        int i2 = 0;
        String str = null;
        PendingIntent pendingIntent = null;
        ConnectionResult connectionResult = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            char c = (char) readInt;
            if (c == 1) {
                i2 = SafeParcelReader.readInt(readInt, parcel);
            } else if (c == 2) {
                str = SafeParcelReader.createString(readInt, parcel);
            } else if (c == 3) {
                pendingIntent =
                        (PendingIntent)
                                SafeParcelReader.createParcelable(
                                        parcel, readInt, PendingIntent.CREATOR);
            } else if (c == 4) {
                connectionResult =
                        (ConnectionResult)
                                SafeParcelReader.createParcelable(
                                        parcel, readInt, ConnectionResult.CREATOR);
            } else if (c != 1000) {
                SafeParcelReader.skipUnknownField(readInt, parcel);
            } else {
                i = SafeParcelReader.readInt(readInt, parcel);
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        return new Status(i, i2, str, pendingIntent, connectionResult);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new Status[i];
    }
}
