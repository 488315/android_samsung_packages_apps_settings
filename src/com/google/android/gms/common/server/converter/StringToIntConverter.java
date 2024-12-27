package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.server.response.FastJsonResponse$FieldConverter;

import java.util.ArrayList;
import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class StringToIntConverter extends AbstractSafeParcelable
        implements FastJsonResponse$FieldConverter {
    public static final Parcelable.Creator<StringToIntConverter> CREATOR = new zad();
    public final int zaa;
    public final HashMap zab = new HashMap();
    public final SparseArray zac = new SparseArray();

    public StringToIntConverter(int i, ArrayList arrayList) {
        this.zaa = i;
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            zac zacVar = (zac) arrayList.get(i2);
            String str = zacVar.zab;
            int i3 = zacVar.zac;
            this.zab.put(str, Integer.valueOf(i3));
            this.zac.put(i3, str);
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        int i2 = this.zaa;
        SafeParcelWriter.zzc(parcel, 1, 4);
        parcel.writeInt(i2);
        ArrayList arrayList = new ArrayList();
        for (String str : this.zab.keySet()) {
            arrayList.add(new zac(str, ((Integer) this.zab.get(str)).intValue()));
        }
        SafeParcelWriter.writeTypedList(parcel, arrayList, 2);
        SafeParcelWriter.zzb(zza, parcel);
    }
}
