package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzf extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzf> CREATOR = new zzg();
    public final String zza;

    public zzf(String str) {
        this.zza = str;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zza);
        SafeParcelWriter.zzb(zza, parcel);
    }
}
