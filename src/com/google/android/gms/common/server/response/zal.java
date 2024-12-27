package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

import java.util.ArrayList;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zal extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zal> CREATOR = new zap();
    public final int zaa;
    public final String zab;
    public final ArrayList zac;

    public zal(int i, String str, ArrayList arrayList) {
        this.zaa = i;
        this.zab = str;
        this.zac = arrayList;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        int i2 = this.zaa;
        SafeParcelWriter.zzc(parcel, 1, 4);
        parcel.writeInt(i2);
        SafeParcelWriter.writeString(parcel, 2, this.zab);
        SafeParcelWriter.writeTypedList(parcel, this.zac, 3);
        SafeParcelWriter.zzb(zza, parcel);
    }

    public zal(String str, Map map) {
        ArrayList arrayList;
        this.zaa = 1;
        this.zab = str;
        if (map == null) {
            arrayList = null;
        } else {
            arrayList = new ArrayList();
            for (String str2 : map.keySet()) {
                arrayList.add(new zam(str2, (FastJsonResponse$Field) map.get(str2)));
            }
        }
        this.zac = arrayList;
    }
}
