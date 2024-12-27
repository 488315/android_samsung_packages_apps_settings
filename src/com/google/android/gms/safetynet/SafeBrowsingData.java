package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SafeBrowsingData extends AbstractSafeParcelable {
    public static final Parcelable.Creator<SafeBrowsingData> CREATOR = new zzj();
    public String zzb;
    public DataHolder zzc;
    public ParcelFileDescriptor zzd;
    public long zze;
    public byte[] zzf;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzb);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzc, i);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzd, i);
        long j = this.zze;
        SafeParcelWriter.zzc(parcel, 5, 8);
        parcel.writeLong(j);
        byte[] bArr = this.zzf;
        if (bArr != null) {
            int zza2 = SafeParcelWriter.zza(6, parcel);
            parcel.writeByteArray(bArr);
            SafeParcelWriter.zzb(zza2, parcel);
        }
        SafeParcelWriter.zzb(zza, parcel);
        this.zzd = null;
    }
}
