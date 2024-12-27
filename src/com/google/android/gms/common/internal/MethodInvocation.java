package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MethodInvocation extends AbstractSafeParcelable {
    public static final Parcelable.Creator<MethodInvocation> CREATOR = new zan();
    public final int zaa;
    public final int zab;
    public final int zac;
    public final long zad;
    public final long zae;
    public final String zaf;
    public final String zag;
    public final int zah;
    public final int zai;

    public MethodInvocation(
            int i, int i2, int i3, long j, long j2, String str, String str2, int i4, int i5) {
        this.zaa = i;
        this.zab = i2;
        this.zac = i3;
        this.zad = j;
        this.zae = j2;
        this.zaf = str;
        this.zag = str2;
        this.zah = i4;
        this.zai = i5;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        int i2 = this.zaa;
        SafeParcelWriter.zzc(parcel, 1, 4);
        parcel.writeInt(i2);
        int i3 = this.zab;
        SafeParcelWriter.zzc(parcel, 2, 4);
        parcel.writeInt(i3);
        int i4 = this.zac;
        SafeParcelWriter.zzc(parcel, 3, 4);
        parcel.writeInt(i4);
        long j = this.zad;
        SafeParcelWriter.zzc(parcel, 4, 8);
        parcel.writeLong(j);
        long j2 = this.zae;
        SafeParcelWriter.zzc(parcel, 5, 8);
        parcel.writeLong(j2);
        SafeParcelWriter.writeString(parcel, 6, this.zaf);
        SafeParcelWriter.writeString(parcel, 7, this.zag);
        int i5 = this.zah;
        SafeParcelWriter.zzc(parcel, 8, 4);
        parcel.writeInt(i5);
        int i6 = this.zai;
        SafeParcelWriter.zzc(parcel, 9, 4);
        parcel.writeInt(i6);
        SafeParcelWriter.zzb(zza, parcel);
    }
}
