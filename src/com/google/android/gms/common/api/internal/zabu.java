package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Preconditions;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zabu implements BaseGmsClient.ConnectionProgressReportCallbacks {
    public final /* synthetic */ GoogleApiManager zaa;
    public final Api.Client zab;
    public final ApiKey zac;
    public IAccountAccessor zad = null;
    public Set zae = null;
    public boolean zaf = false;

    public zabu(GoogleApiManager googleApiManager, Api.Client client, ApiKey apiKey) {
        this.zaa = googleApiManager;
        this.zab = client;
        this.zac = apiKey;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks
    public final void onReportServiceBinding(ConnectionResult connectionResult) {
        this.zaa.zat.post(new zabt(this, connectionResult));
    }

    public final void zae(ConnectionResult connectionResult) {
        zabq zabqVar = (zabq) ((ConcurrentHashMap) this.zaa.zap).get(this.zac);
        if (zabqVar != null) {
            Preconditions.checkHandlerThread(zabqVar.zaa.zat);
            Api.Client client = zabqVar.zac;
            String name = client.getClass().getName();
            String valueOf = String.valueOf(connectionResult);
            StringBuilder sb = new StringBuilder(name.length() + 25 + valueOf.length());
            sb.append("onSignInFailed for ");
            sb.append(name);
            sb.append(" with ");
            sb.append(valueOf);
            client.disconnect(sb.toString());
            zabqVar.zar(connectionResult, null);
        }
    }
}
