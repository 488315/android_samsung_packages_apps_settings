package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.AccountAccessor;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zav;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.signin.internal.zak;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zacr implements Runnable {
    public final /* synthetic */ zak zaa;
    public final /* synthetic */ zact zab;

    public zacr(zact zactVar, zak zakVar) {
        this.zab = zactVar;
        this.zaa = zakVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        IAccountAccessor zzvVar;
        zact zactVar = this.zab;
        zak zakVar = this.zaa;
        ConnectionResult connectionResult = zakVar.zab;
        if (connectionResult.zzb == 0) {
            zav zavVar = zakVar.zac;
            Preconditions.checkNotNull(zavVar);
            ConnectionResult connectionResult2 = zavVar.zac;
            if (connectionResult2.zzb != 0) {
                Log.wtf(
                        "SignInCoordinator",
                        "Sign-in succeeded with resolve account failure: "
                                .concat(String.valueOf(connectionResult2)),
                        new Exception());
                zactVar.zah.zae(connectionResult2);
                zactVar.zag.disconnect();
                return;
            }
            zabu zabuVar = zactVar.zah;
            IBinder iBinder = zavVar.zab;
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
            Set set = zactVar.zae;
            zabuVar.getClass();
            if (zzvVar == null || set == null) {
                Log.wtf(
                        "GoogleApiManager",
                        "Received null response from onSignInSuccess",
                        new Exception());
                zabuVar.zae(new ConnectionResult(4));
            } else {
                zabuVar.zad = zzvVar;
                zabuVar.zae = set;
                if (zabuVar.zaf) {
                    zabuVar.zab.getRemoteService(zzvVar, set);
                }
            }
        } else {
            zactVar.zah.zae(connectionResult);
        }
        zactVar.zag.disconnect();
    }
}
