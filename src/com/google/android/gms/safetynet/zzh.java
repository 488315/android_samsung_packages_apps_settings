package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzh extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzh> CREATOR = new zzi();
    public final int zza;
    public final boolean zzb;

    public zzh(int i, boolean z) {
        this.zza = i;
        this.zzb = z;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        int i2 = this.zza;
        SafeParcelWriter.zzc(parcel, 2, 4);
        parcel.writeInt(i2);
        boolean z = this.zzb;
        SafeParcelWriter.zzc(parcel, 3, 4);
        parcel.writeInt(z ? 1 : 0);
        SafeParcelWriter.zzb(zza, parcel);
    }
}
