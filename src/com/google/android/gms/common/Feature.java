package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.samsung.android.gtscell.data.FieldName;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Feature extends AbstractSafeParcelable {
    public static final Parcelable.Creator<Feature> CREATOR = new zzc();
    public final String zza;
    public final int zzb;
    public final long zzc;

    public Feature() {
        this.zza = "CLIENT_TELEMETRY";
        this.zzc = 1L;
        this.zzb = -1;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Feature) {
            Feature feature = (Feature) obj;
            String str = this.zza;
            if (((str != null && str.equals(feature.zza))
                            || (this.zza == null && feature.zza == null))
                    && getVersion() == feature.getVersion()) {
                return true;
            }
        }
        return false;
    }

    public final long getVersion() {
        long j = this.zzc;
        return j == -1 ? this.zzb : j;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[] {this.zza, Long.valueOf(getVersion())});
    }

    public final String toString() {
        Objects.ToStringHelper toStringHelper = new Objects.ToStringHelper(this);
        toStringHelper.add(this.zza, "name");
        toStringHelper.add(Long.valueOf(getVersion()), FieldName.VERSION);
        return toStringHelper.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zza);
        int i2 = this.zzb;
        SafeParcelWriter.zzc(parcel, 2, 4);
        parcel.writeInt(i2);
        long version = getVersion();
        SafeParcelWriter.zzc(parcel, 3, 8);
        parcel.writeLong(version);
        SafeParcelWriter.zzb(zza, parcel);
    }

    public Feature(long j, int i, String str) {
        this.zza = str;
        this.zzb = i;
        this.zzc = j;
    }
}
