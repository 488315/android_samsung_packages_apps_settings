package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzai implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        int i2 = 0;
        int i3 = 0;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            char c = (char) readInt;
            if (c == 1) {
                i = SafeParcelReader.readInt(readInt, parcel);
            } else if (c == 2) {
                z = SafeParcelReader.readBoolean(readInt, parcel);
            } else if (c == 3) {
                z2 = SafeParcelReader.readBoolean(readInt, parcel);
            } else if (c == 4) {
                i2 = SafeParcelReader.readInt(readInt, parcel);
            } else if (c != 5) {
                SafeParcelReader.skipUnknownField(readInt, parcel);
            } else {
                i3 = SafeParcelReader.readInt(readInt, parcel);
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        return new RootTelemetryConfiguration(i, z, z2, i2, i3);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new RootTelemetryConfiguration[i];
    }
}
