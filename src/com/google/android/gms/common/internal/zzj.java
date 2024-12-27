package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzj extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzj> CREATOR = new zzk();
    public Bundle zza;
    public Feature[] zzb;
    public int zzc;
    public ConnectionTelemetryConfiguration zzd;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        SafeParcelWriter.writeBundle(parcel, 1, this.zza);
        SafeParcelWriter.writeTypedArray(parcel, 2, this.zzb, i);
        int i2 = this.zzc;
        SafeParcelWriter.zzc(parcel, 3, 4);
        parcel.writeInt(i2);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzd, i);
        SafeParcelWriter.zzb(zza, parcel);
    }
}
