package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zav extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zav> CREATOR = new zaw();
    public final int zaa;
    public final IBinder zab;
    public final ConnectionResult zac;
    public final boolean zad;
    public final boolean zae;

    public zav(int i, IBinder iBinder, ConnectionResult connectionResult, boolean z, boolean z2) {
        this.zaa = i;
        this.zab = iBinder;
        this.zac = connectionResult;
        this.zad = z;
        this.zae = z2;
    }

    public final boolean equals(Object obj) {
        Object zzvVar;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zav)) {
            return false;
        }
        zav zavVar = (zav) obj;
        if (this.zac.equals(zavVar.zac)) {
            IBinder iBinder = this.zab;
            Object obj2 = null;
            if (iBinder == null) {
                zzvVar = null;
            } else {
                int i = AccountAccessor.$r8$clinit;
                IInterface queryLocalInterface =
                        iBinder.queryLocalInterface(
                                "com.google.android.gms.common.internal.IAccountAccessor");
                zzvVar =
                        queryLocalInterface instanceof IAccountAccessor
                                ? (IAccountAccessor) queryLocalInterface
                                : new zzv(iBinder);
            }
            IBinder iBinder2 = zavVar.zab;
            if (iBinder2 != null) {
                int i2 = AccountAccessor.$r8$clinit;
                IInterface queryLocalInterface2 =
                        iBinder2.queryLocalInterface(
                                "com.google.android.gms.common.internal.IAccountAccessor");
                obj2 =
                        queryLocalInterface2 instanceof IAccountAccessor
                                ? (IAccountAccessor) queryLocalInterface2
                                : new zzv(iBinder2);
            }
            if (Objects.equal(zzvVar, obj2)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int zza = SafeParcelWriter.zza(20293, parcel);
        int i2 = this.zaa;
        SafeParcelWriter.zzc(parcel, 1, 4);
        parcel.writeInt(i2);
        IBinder iBinder = this.zab;
        if (iBinder != null) {
            int zza2 = SafeParcelWriter.zza(2, parcel);
            parcel.writeStrongBinder(iBinder);
            SafeParcelWriter.zzb(zza2, parcel);
        }
        SafeParcelWriter.writeParcelable(parcel, 3, this.zac, i);
        boolean z = this.zad;
        SafeParcelWriter.zzc(parcel, 4, 4);
        parcel.writeInt(z ? 1 : 0);
        boolean z2 = this.zae;
        SafeParcelWriter.zzc(parcel, 5, 4);
        parcel.writeInt(z2 ? 1 : 0);
        SafeParcelWriter.zzb(zza, parcel);
    }
}
