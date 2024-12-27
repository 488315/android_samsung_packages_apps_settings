package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zaa extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zaa> CREATOR = new zab();
    public final int zaa;
    public final StringToIntConverter zab;

    public zaa(int i, StringToIntConverter stringToIntConverter) {
        this.zaa = i;
        this.zab = stringToIntConverter;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        int i2 = this.zaa;
        SafeParcelWriter.zzc(parcel, 1, 4);
        parcel.writeInt(i2);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zab, i);
        SafeParcelWriter.zzb(zza, parcel);
    }

    public zaa(StringToIntConverter stringToIntConverter) {
        this.zaa = 1;
        this.zab = stringToIntConverter;
    }
}
