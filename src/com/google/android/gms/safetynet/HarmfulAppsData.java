package com.google.android.gms.safetynet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class HarmfulAppsData extends AbstractSafeParcelable {
    public static final Parcelable.Creator<HarmfulAppsData> CREATOR = new zzc();
    public final int apkCategory;
    public final String apkPackageName;
    public final byte[] apkSha256;

    public HarmfulAppsData(int i, String str, byte[] bArr) {
        this.apkPackageName = str;
        this.apkSha256 = bArr;
        this.apkCategory = i;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        SafeParcelWriter.writeString(parcel, 2, this.apkPackageName);
        byte[] bArr = this.apkSha256;
        if (bArr != null) {
            int zza2 = SafeParcelWriter.zza(3, parcel);
            parcel.writeByteArray(bArr);
            SafeParcelWriter.zzb(zza2, parcel);
        }
        int i2 = this.apkCategory;
        SafeParcelWriter.zzc(parcel, 4, 4);
        parcel.writeInt(i2);
        SafeParcelWriter.zzb(zza, parcel);
    }
}
