package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzl implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        RootTelemetryConfiguration rootTelemetryConfiguration = null;
        int[] iArr = null;
        int[] iArr2 = null;
        boolean z = false;
        boolean z2 = false;
        int i = 0;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            switch ((char) readInt) {
                case 1:
                    rootTelemetryConfiguration =
                            (RootTelemetryConfiguration)
                                    SafeParcelReader.createParcelable(
                                            parcel, readInt, RootTelemetryConfiguration.CREATOR);
                    break;
                case 2:
                    z = SafeParcelReader.readBoolean(readInt, parcel);
                    break;
                case 3:
                    z2 = SafeParcelReader.readBoolean(readInt, parcel);
                    break;
                case 4:
                    iArr = SafeParcelReader.createIntArray(readInt, parcel);
                    break;
                case 5:
                    i = SafeParcelReader.readInt(readInt, parcel);
                    break;
                case 6:
                    iArr2 = SafeParcelReader.createIntArray(readInt, parcel);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(readInt, parcel);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        return new ConnectionTelemetryConfiguration(
                rootTelemetryConfiguration, z, z2, iArr, i, iArr2);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new ConnectionTelemetryConfiguration[i];
    }
}
