package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzk implements Parcelable.Creator {
    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        Bundle bundle = null;
        ConnectionTelemetryConfiguration connectionTelemetryConfiguration = null;
        int i = 0;
        Feature[] featureArr = null;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readInt = parcel.readInt();
            char c = (char) readInt;
            if (c == 1) {
                bundle = SafeParcelReader.createBundle(readInt, parcel);
            } else if (c == 2) {
                featureArr =
                        (Feature[])
                                SafeParcelReader.createTypedArray(parcel, readInt, Feature.CREATOR);
            } else if (c == 3) {
                i = SafeParcelReader.readInt(readInt, parcel);
            } else if (c != 4) {
                SafeParcelReader.skipUnknownField(readInt, parcel);
            } else {
                connectionTelemetryConfiguration =
                        (ConnectionTelemetryConfiguration)
                                SafeParcelReader.createParcelable(
                                        parcel, readInt, ConnectionTelemetryConfiguration.CREATOR);
            }
        }
        SafeParcelReader.ensureAtEnd(validateObjectHeader, parcel);
        zzj zzjVar = new zzj();
        zzjVar.zza = bundle;
        zzjVar.zzb = featureArr;
        zzjVar.zzc = i;
        zzjVar.zzd = connectionTelemetryConfiguration;
        return zzjVar;
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzj[i];
    }
}
