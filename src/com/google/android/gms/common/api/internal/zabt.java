package com.google.android.gms.common.api.internal;

import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.IAccountAccessor;

import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zabt implements Runnable {
    public final /* synthetic */ ConnectionResult zaa;
    public final /* synthetic */ zabu zab;

    public zabt(zabu zabuVar, ConnectionResult connectionResult) {
        this.zab = zabuVar;
        this.zaa = connectionResult;
    }

    @Override // java.lang.Runnable
    public final void run() {
        IAccountAccessor iAccountAccessor;
        zabu zabuVar = this.zab;
        zabq zabqVar = (zabq) ((ConcurrentHashMap) zabuVar.zaa.zap).get(zabuVar.zac);
        if (zabqVar == null) {
            return;
        }
        ConnectionResult connectionResult = this.zaa;
        if (!(connectionResult.zzb == 0)) {
            zabqVar.zar(connectionResult, null);
            return;
        }
        zabu zabuVar2 = this.zab;
        zabuVar2.zaf = true;
        if (zabuVar2.zab.requiresSignIn()) {
            zabu zabuVar3 = this.zab;
            if (!zabuVar3.zaf || (iAccountAccessor = zabuVar3.zad) == null) {
                return;
            }
            zabuVar3.zab.getRemoteService(iAccountAccessor, zabuVar3.zae);
            return;
        }
        try {
            Api.Client client = this.zab.zab;
            client.getRemoteService(null, client.getScopesForConnectionlessNonSignIn());
        } catch (SecurityException e) {
            Log.e("GoogleApiManager", "Failed to get service from broker. ", e);
            this.zab.zab.disconnect("Failed to get service from broker.");
            zabqVar.zar(new ConnectionResult(10), null);
        }
    }
}
