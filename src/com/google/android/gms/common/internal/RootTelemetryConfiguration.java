package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class RootTelemetryConfiguration extends AbstractSafeParcelable {
    public static final Parcelable.Creator<RootTelemetryConfiguration> CREATOR = new zzai();
    public final int zza;
    public final boolean zzb;
    public final boolean zzc;
    public final int zzd;
    public final int zze;

    public RootTelemetryConfiguration(int i, boolean z, boolean z2, int i2, int i3) {
        this.zza = i;
        this.zzb = z;
        this.zzc = z2;
        this.zzd = i2;
        this.zze = i3;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        int i2 = this.zza;
        SafeParcelWriter.zzc(parcel, 1, 4);
        parcel.writeInt(i2);
        boolean z = this.zzb;
        SafeParcelWriter.zzc(parcel, 2, 4);
        parcel.writeInt(z ? 1 : 0);
        boolean z2 = this.zzc;
        SafeParcelWriter.zzc(parcel, 3, 4);
        parcel.writeInt(z2 ? 1 : 0);
        int i3 = this.zzd;
        SafeParcelWriter.zzc(parcel, 4, 4);
        parcel.writeInt(i3);
        int i4 = this.zze;
        SafeParcelWriter.zzc(parcel, 5, 4);
        parcel.writeInt(i4);
        SafeParcelWriter.zzb(zza, parcel);
    }
}
