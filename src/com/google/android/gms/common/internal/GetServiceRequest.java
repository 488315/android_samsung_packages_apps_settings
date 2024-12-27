package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class GetServiceRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<GetServiceRequest> CREATOR = new zzm();
    public final int zza;
    public final int zzb;
    public final int zzc;
    public String zzd;
    public IBinder zze;
    public Scope[] zzf;
    public Bundle zzg;
    public Account zzh;
    public Feature[] zzi;
    public Feature[] zzj;
    public final boolean zzk;
    public final int zzl;
    public boolean zzm;
    public final String zzn;

    public GetServiceRequest(
            int i,
            int i2,
            int i3,
            String str,
            IBinder iBinder,
            Scope[] scopeArr,
            Bundle bundle,
            Account account,
            Feature[] featureArr,
            Feature[] featureArr2,
            boolean z,
            int i4,
            boolean z2,
            String str2) {
        this.zza = i;
        this.zzb = i2;
        this.zzc = i3;
        if ("com.google.android.gms".equals(str)) {
            this.zzd = "com.google.android.gms";
        } else {
            this.zzd = str;
        }
        if (i < 2) {
            Account account2 = null;
            if (iBinder != null) {
                int i5 = AccountAccessor.$r8$clinit;
                IInterface queryLocalInterface =
                        iBinder.queryLocalInterface(
                                "com.google.android.gms.common.internal.IAccountAccessor");
                IInterface zzvVar =
                        queryLocalInterface instanceof IAccountAccessor
                                ? (IAccountAccessor) queryLocalInterface
                                : new zzv(iBinder);
                if (zzvVar != null) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        try {
                            account2 = ((zzv) zzvVar).zzb();
                        } catch (RemoteException unused) {
                            Log.w("AccountAccessor", "Remote account accessor probably died");
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
            this.zzh = account2;
        } else {
            this.zze = iBinder;
            this.zzh = account;
        }
        this.zzf = scopeArr;
        this.zzg = bundle;
        this.zzi = featureArr;
        this.zzj = featureArr2;
        this.zzk = z;
        this.zzl = i4;
        this.zzm = z2;
        this.zzn = str2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        zzm.zza(this, parcel, i);
    }

    public GetServiceRequest(int i, String str) {
        this.zza = 6;
        this.zzc = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.zzb = i;
        this.zzk = true;
        this.zzn = str;
    }
}
