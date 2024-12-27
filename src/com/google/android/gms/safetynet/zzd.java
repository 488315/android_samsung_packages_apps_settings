package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzd extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzd> CREATOR = new zze();
    public final long zza;
    public final HarmfulAppsData[] zzb;
    public final int zzc;
    public final boolean zzd;

    public zzd(long j, HarmfulAppsData[] harmfulAppsDataArr, int i, boolean z) {
        this.zza = j;
        this.zzb = harmfulAppsDataArr;
        this.zzd = z;
        if (z) {
            this.zzc = i;
        } else {
            this.zzc = -1;
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        long j = this.zza;
        SafeParcelWriter.zzc(parcel, 2, 8);
        parcel.writeLong(j);
        SafeParcelWriter.writeTypedArray(parcel, 3, this.zzb, i);
        int i2 = this.zzc;
        SafeParcelWriter.zzc(parcel, 4, 4);
        parcel.writeInt(i2);
        boolean z = this.zzd;
        SafeParcelWriter.zzc(parcel, 5, 4);
        parcel.writeInt(z ? 1 : 0);
        SafeParcelWriter.zzb(zza, parcel);
    }
}
