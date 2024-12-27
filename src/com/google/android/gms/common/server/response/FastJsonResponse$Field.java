package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.common.server.converter.zaa;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FastJsonResponse$Field<I, O> extends AbstractSafeParcelable {
    public static final zaj CREATOR = new zaj();
    public final int zaa;
    public final boolean zab;
    public final int zac;
    public final boolean zad;
    public final String zae;
    public final int zaf;
    public final Class zag;
    public final String zah;
    public final int zai;
    public zan zaj;
    public final FastJsonResponse$FieldConverter zak;

    public FastJsonResponse$Field(int i, int i2, int i3, String str) {
        this.zai = 1;
        this.zaa = i;
        this.zab = false;
        this.zac = i2;
        this.zad = false;
        this.zae = str;
        this.zaf = i3;
        this.zag = null;
        this.zah = null;
        this.zak = null;
    }

    public static FastJsonResponse$Field<byte[], byte[]> forBase64(String str, int i) {
        return new FastJsonResponse$Field<>(8, 8, i, str);
    }

    public static FastJsonResponse$Field<Integer, Integer> forInteger(String str, int i) {
        return new FastJsonResponse$Field<>(0, 0, i, str);
    }

    public final String toString() {
        Objects.ToStringHelper toStringHelper = new Objects.ToStringHelper(this);
        toStringHelper.add(Integer.valueOf(this.zai), "versionCode");
        toStringHelper.add(Integer.valueOf(this.zaa), "typeIn");
        toStringHelper.add(Boolean.valueOf(this.zab), "typeInArray");
        toStringHelper.add(Integer.valueOf(this.zac), "typeOut");
        toStringHelper.add(Boolean.valueOf(this.zad), "typeOutArray");
        toStringHelper.add(this.zae, "outputFieldName");
        toStringHelper.add(Integer.valueOf(this.zaf), "safeParcelFieldId");
        String str = this.zah;
        if (str == null) {
            str = null;
        }
        toStringHelper.add(str, "concreteTypeName");
        Class cls = this.zag;
        if (cls != null) {
            toStringHelper.add(cls.getCanonicalName(), "concreteType.class");
        }
        FastJsonResponse$FieldConverter fastJsonResponse$FieldConverter = this.zak;
        if (fastJsonResponse$FieldConverter != null) {
            toStringHelper.add(
                    fastJsonResponse$FieldConverter.getClass().getCanonicalName(), "converterName");
        }
        return toStringHelper.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        int i2 = this.zai;
        SafeParcelWriter.zzc(parcel, 1, 4);
        parcel.writeInt(i2);
        int i3 = this.zaa;
        SafeParcelWriter.zzc(parcel, 2, 4);
        parcel.writeInt(i3);
        boolean z = this.zab;
        SafeParcelWriter.zzc(parcel, 3, 4);
        parcel.writeInt(z ? 1 : 0);
        int i4 = this.zac;
        SafeParcelWriter.zzc(parcel, 4, 4);
        parcel.writeInt(i4);
        boolean z2 = this.zad;
        SafeParcelWriter.zzc(parcel, 5, 4);
        parcel.writeInt(z2 ? 1 : 0);
        SafeParcelWriter.writeString(parcel, 6, this.zae);
        int i5 = this.zaf;
        SafeParcelWriter.zzc(parcel, 7, 4);
        parcel.writeInt(i5);
        String str = this.zah;
        zaa zaaVar = null;
        if (str == null) {
            str = null;
        }
        SafeParcelWriter.writeString(parcel, 8, str);
        FastJsonResponse$FieldConverter fastJsonResponse$FieldConverter = this.zak;
        if (fastJsonResponse$FieldConverter != null) {
            Parcelable.Creator<zaa> creator = zaa.CREATOR;
            if (!(fastJsonResponse$FieldConverter instanceof StringToIntConverter)) {
                throw new IllegalArgumentException(
                        "Unsupported safe parcelable field converter class.");
            }
            zaaVar = new zaa((StringToIntConverter) fastJsonResponse$FieldConverter);
        }
        SafeParcelWriter.writeParcelable(parcel, 9, zaaVar, i);
        SafeParcelWriter.zzb(zza, parcel);
    }

    public FastJsonResponse$Field(
            int i,
            int i2,
            boolean z,
            int i3,
            boolean z2,
            String str,
            int i4,
            String str2,
            zaa zaaVar) {
        this.zai = i;
        this.zaa = i2;
        this.zab = z;
        this.zac = i3;
        this.zad = z2;
        this.zae = str;
        this.zaf = i4;
        if (str2 == null) {
            this.zag = null;
            this.zah = null;
        } else {
            this.zag = SafeParcelResponse.class;
            this.zah = str2;
        }
        if (zaaVar == null) {
            this.zak = null;
            return;
        }
        StringToIntConverter stringToIntConverter = zaaVar.zab;
        if (stringToIntConverter != null) {
            this.zak = stringToIntConverter;
            return;
        }
        throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
    }
}
