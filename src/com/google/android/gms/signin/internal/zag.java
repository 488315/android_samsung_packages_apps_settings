package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zag extends AbstractSafeParcelable implements Result {
    public static final Parcelable.Creator<zag> CREATOR = new zah();
    public final List zaa;
    public final String zab;

    public zag(String str, List list) {
        this.zaa = list;
        this.zab = str;
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this.zab != null ? Status.RESULT_SUCCESS : Status.RESULT_CANCELED;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        List<String> list = this.zaa;
        if (list != null) {
            int zza2 = SafeParcelWriter.zza(1, parcel);
            parcel.writeStringList(list);
            SafeParcelWriter.zzb(zza2, parcel);
        }
        SafeParcelWriter.writeString(parcel, 2, this.zab);
        SafeParcelWriter.zzb(zza, parcel);
    }
}
