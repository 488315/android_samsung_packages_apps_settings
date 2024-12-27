package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zam extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zam> CREATOR = new zak();
    public final int zaa;
    public final String zab;
    public final FastJsonResponse$Field zac;

    public zam(int i, String str, FastJsonResponse$Field fastJsonResponse$Field) {
        this.zaa = i;
        this.zab = str;
        this.zac = fastJsonResponse$Field;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        int i2 = this.zaa;
        SafeParcelWriter.zzc(parcel, 1, 4);
        parcel.writeInt(i2);
        SafeParcelWriter.writeString(parcel, 2, this.zab);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zac, i);
        SafeParcelWriter.zzb(zza, parcel);
    }

    public zam(String str, FastJsonResponse$Field fastJsonResponse$Field) {
        this.zaa = 1;
        this.zab = str;
        this.zac = fastJsonResponse$Field;
    }
}
