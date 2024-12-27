package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zat extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zat> CREATOR = new zau();
    public final int zaa;
    public final Account zab;
    public final int zac;
    public final GoogleSignInAccount zad;

    public zat(int i, Account account, int i2, GoogleSignInAccount googleSignInAccount) {
        this.zaa = i;
        this.zab = account;
        this.zac = i2;
        this.zad = googleSignInAccount;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        int i2 = this.zaa;
        SafeParcelWriter.zzc(parcel, 1, 4);
        parcel.writeInt(i2);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zab, i);
        int i3 = this.zac;
        SafeParcelWriter.zzc(parcel, 3, 4);
        parcel.writeInt(i3);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zad, i);
        SafeParcelWriter.zzb(zza, parcel);
    }
}
