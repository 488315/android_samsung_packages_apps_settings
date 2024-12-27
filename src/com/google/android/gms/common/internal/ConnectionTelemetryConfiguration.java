package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ConnectionTelemetryConfiguration extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ConnectionTelemetryConfiguration> CREATOR = new zzl();
    public final RootTelemetryConfiguration zza;
    public final boolean zzb;
    public final boolean zzc;
    public final int[] zzd;
    public final int zze;
    public final int[] zzf;

    public ConnectionTelemetryConfiguration(
            RootTelemetryConfiguration rootTelemetryConfiguration,
            boolean z,
            boolean z2,
            int[] iArr,
            int i,
            int[] iArr2) {
        this.zza = rootTelemetryConfiguration;
        this.zzb = z;
        this.zzc = z2;
        this.zzd = iArr;
        this.zze = i;
        this.zzf = iArr2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zza, i);
        boolean z = this.zzb;
        SafeParcelWriter.zzc(parcel, 2, 4);
        parcel.writeInt(z ? 1 : 0);
        boolean z2 = this.zzc;
        SafeParcelWriter.zzc(parcel, 3, 4);
        parcel.writeInt(z2 ? 1 : 0);
        int[] iArr = this.zzd;
        if (iArr != null) {
            int zza2 = SafeParcelWriter.zza(4, parcel);
            parcel.writeIntArray(iArr);
            SafeParcelWriter.zzb(zza2, parcel);
        }
        int i2 = this.zze;
        SafeParcelWriter.zzc(parcel, 5, 4);
        parcel.writeInt(i2);
        int[] iArr2 = this.zzf;
        if (iArr2 != null) {
            int zza3 = SafeParcelWriter.zza(6, parcel);
            parcel.writeIntArray(iArr2);
            SafeParcelWriter.zzb(zza3, parcel);
        }
        SafeParcelWriter.zzb(zza, parcel);
    }
}
