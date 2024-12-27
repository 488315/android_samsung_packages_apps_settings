package com.google.android.gms.common.internal;

import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zzn {
    public static final Uri zza =
            new Uri.Builder().scheme("content").authority("com.google.android.gms.chimera").build();
    public final String zzb;
    public final String zzc;
    public final int zze;
    public final boolean zzf;

    public zzn(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Given String is empty or null");
        }
        this.zzb = str;
        if (TextUtils.isEmpty("com.google.android.gms")) {
            throw new IllegalArgumentException("Given String is empty or null");
        }
        this.zzc = "com.google.android.gms";
        this.zze = 4225;
        this.zzf = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzn)) {
            return false;
        }
        zzn zznVar = (zzn) obj;
        return Objects.equal(this.zzb, zznVar.zzb)
                && Objects.equal(this.zzc, zznVar.zzc)
                && Objects.equal(null, null)
                && this.zze == zznVar.zze
                && this.zzf == zznVar.zzf;
    }

    public final int hashCode() {
        return Arrays.hashCode(
                new Object[] {
                    this.zzb, this.zzc, null, Integer.valueOf(this.zze), Boolean.valueOf(this.zzf)
                });
    }

    public final String toString() {
        String str = this.zzb;
        if (str != null) {
            return str;
        }
        Preconditions.checkNotNull(null);
        throw null;
    }
}
