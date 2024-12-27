package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.DeadObjectException;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.collection.ArrayMap;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.UnsupportedApiCallException;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.signin.internal.SignInClientImpl;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zabq
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public final /* synthetic */ GoogleApiManager zaa;
    public final Api.Client zac;
    public final ApiKey zad;
    public final zaad zae;
    public final int zah;
    public final zact zai;
    public boolean zaj;
    public final Queue zab = new LinkedList();
    public final Set zaf = new HashSet();
    public final Map zag = new HashMap();
    public final List zak = new ArrayList();
    public ConnectionResult zal = null;
    public int zam = 0;

    /* JADX WARN: Multi-variable type inference failed */
    public zabq(GoogleApiManager googleApiManager, GoogleApi googleApi) {
        this.zaa = googleApiManager;
        Looper looper = googleApiManager.zat.getLooper();
        ClientSettings.Builder createClientSettingsBuilder =
                googleApi.createClientSettingsBuilder();
        ClientSettings clientSettings =
                new ClientSettings(
                        createClientSettingsBuilder.zaa,
                        createClientSettingsBuilder.zab,
                        createClientSettingsBuilder.zac,
                        createClientSettingsBuilder.zad);
        Api.AbstractClientBuilder abstractClientBuilder = googleApi.zad.zaa;
        Preconditions.checkNotNull(abstractClientBuilder);
        Api.Client buildClient =
                abstractClientBuilder.buildClient(
                        googleApi.zab,
                        looper,
                        clientSettings,
                        (Object) googleApi.zae,
                        (GoogleApiClient.ConnectionCallbacks) this,
                        (GoogleApiClient.OnConnectionFailedListener) this);
        String str = googleApi.zac;
        if (str != null && (buildClient instanceof BaseGmsClient)) {
            ((BaseGmsClient) buildClient).zzA = str;
        }
        if (str != null && (buildClient instanceof NonGmsServiceBrokerClient)) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(buildClient);
            throw null;
        }
        this.zac = buildClient;
        this.zad = googleApi.zaf;
        this.zae = new zaad();
        this.zah = googleApi.zah;
        if (!buildClient.requiresSignIn()) {
            this.zai = null;
            return;
        }
        Context context = googleApiManager.zak;
        com.google.android.gms.internal.base.zaq zaqVar = googleApiManager.zat;
        ClientSettings.Builder createClientSettingsBuilder2 =
                googleApi.createClientSettingsBuilder();
        this.zai =
                new zact(
                        context,
                        zaqVar,
                        new ClientSettings(
                                createClientSettingsBuilder2.zaa,
                                createClientSettingsBuilder2.zab,
                                createClientSettingsBuilder2.zac,
                                createClientSettingsBuilder2.zad));
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnected() {
        Looper myLooper = Looper.myLooper();
        GoogleApiManager googleApiManager = this.zaa;
        if (myLooper == googleApiManager.zat.getLooper()) {
            zaG();
        } else {
            googleApiManager.zat.post(new zabm(this));
        }
    }

    @Override // com.google.android.gms.common.api.internal.OnConnectionFailedListener
    public final void onConnectionFailed(ConnectionResult connectionResult) {
        zar(connectionResult, null);
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnectionSuspended(int i) {
        Looper myLooper = Looper.myLooper();
        GoogleApiManager googleApiManager = this.zaa;
        if (myLooper == googleApiManager.zat.getLooper()) {
            zaH(i);
        } else {
            googleApiManager.zat.post(new zabn(this, i));
        }
    }

    public final void zaC(ConnectionResult connectionResult) {
        Iterator it = ((HashSet) this.zaf).iterator();
        if (!it.hasNext()) {
            ((HashSet) this.zaf).clear();
            return;
        }
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
        if (Objects.equal(connectionResult, ConnectionResult.RESULT_SUCCESS)) {
            this.zac.getEndpointPackageName();
        }
        throw null;
    }

    public final void zaD(Status status) {
        Preconditions.checkHandlerThread(this.zaa.zat);
        zaE(status, null, false);
    }

    public final void zaE(Status status, Exception exc, boolean z) {
        Preconditions.checkHandlerThread(this.zaa.zat);
        if ((status == null) == (exc == null)) {
            throw new IllegalArgumentException("Status XOR exception should be null");
        }
        Iterator it = this.zab.iterator();
        while (it.hasNext()) {
            zai zaiVar = (zai) it.next();
            if (!z || zaiVar.zac == 2) {
                if (status != null) {
                    zaiVar.zad(status);
                } else {
                    zaiVar.zae(exc);
                }
                it.remove();
            }
        }
    }

    public final void zaF() {
        ArrayList arrayList = new ArrayList(this.zab);
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            zai zaiVar = (zai) arrayList.get(i);
            if (!this.zac.isConnected()) {
                return;
            }
            if (zaL(zaiVar)) {
                ((LinkedList) this.zab).remove(zaiVar);
            }
        }
    }

    public final void zaG() {
        GoogleApiManager googleApiManager = this.zaa;
        Preconditions.checkHandlerThread(googleApiManager.zat);
        this.zal = null;
        zaC(ConnectionResult.RESULT_SUCCESS);
        if (this.zaj) {
            com.google.android.gms.internal.base.zaq zaqVar = googleApiManager.zat;
            ApiKey apiKey = this.zad;
            zaqVar.removeMessages(11, apiKey);
            googleApiManager.zat.removeMessages(9, apiKey);
            this.zaj = false;
        }
        Iterator it = ((HashMap) this.zag).values().iterator();
        if (it.hasNext()) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
        zaF();
        zaI();
    }

    public final void zaH(int i) {
        GoogleApiManager googleApiManager = this.zaa;
        Preconditions.checkHandlerThread(googleApiManager.zat);
        this.zal = null;
        this.zaj = true;
        String lastDisconnectMessage = this.zac.getLastDisconnectMessage();
        zaad zaadVar = this.zae;
        zaadVar.getClass();
        StringBuilder sb = new StringBuilder("The connection to Google Play services was lost");
        if (i == 1) {
            sb.append(" due to service disconnection.");
        } else if (i == 3) {
            sb.append(" due to dead object exception.");
        }
        if (lastDisconnectMessage != null) {
            sb.append(" Last reason for disconnect: ");
            sb.append(lastDisconnectMessage);
        }
        zaadVar.zah(new Status(20, sb.toString()), true);
        com.google.android.gms.internal.base.zaq zaqVar = googleApiManager.zat;
        ApiKey apiKey = this.zad;
        Message obtain = Message.obtain(zaqVar, 9, apiKey);
        Status status = GoogleApiManager.zaa;
        zaqVar.sendMessageDelayed(obtain, 5000L);
        com.google.android.gms.internal.base.zaq zaqVar2 = googleApiManager.zat;
        zaqVar2.sendMessageDelayed(Message.obtain(zaqVar2, 11, apiKey), 120000L);
        googleApiManager.zam.zaa.clear();
        Iterator it = ((HashMap) this.zag).values().iterator();
        if (it.hasNext()) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
    }

    public final void zaI() {
        GoogleApiManager googleApiManager = this.zaa;
        com.google.android.gms.internal.base.zaq zaqVar = googleApiManager.zat;
        ApiKey apiKey = this.zad;
        zaqVar.removeMessages(12, apiKey);
        com.google.android.gms.internal.base.zaq zaqVar2 = googleApiManager.zat;
        zaqVar2.sendMessageDelayed(zaqVar2.obtainMessage(12, apiKey), googleApiManager.zag);
    }

    public final boolean zaL(zai zaiVar) {
        Feature feature;
        if (!(zaiVar instanceof zac)) {
            Api.Client client = this.zac;
            zaiVar.zag(this.zae, client.requiresSignIn());
            try {
                zaiVar.zaf(this);
            } catch (DeadObjectException unused) {
                onConnectionSuspended(1);
                client.disconnect("DeadObjectException thrown while running ApiCallRunner.");
            }
            return true;
        }
        zac zacVar = (zac) zaiVar;
        Feature[] zab = zacVar.zab(this);
        if (zab != null && zab.length != 0) {
            Feature[] availableFeatures = this.zac.getAvailableFeatures();
            if (availableFeatures == null) {
                availableFeatures = new Feature[0];
            }
            ArrayMap arrayMap = new ArrayMap(availableFeatures.length);
            for (Feature feature2 : availableFeatures) {
                arrayMap.put(feature2.zza, Long.valueOf(feature2.getVersion()));
            }
            int length = zab.length;
            for (int i = 0; i < length; i++) {
                feature = zab[i];
                Long l = (Long) arrayMap.get(feature.zza);
                if (l == null || l.longValue() < feature.getVersion()) {
                    break;
                }
            }
        }
        feature = null;
        if (feature == null) {
            Api.Client client2 = this.zac;
            zaiVar.zag(this.zae, client2.requiresSignIn());
            try {
                zaiVar.zaf(this);
            } catch (DeadObjectException unused2) {
                onConnectionSuspended(1);
                client2.disconnect("DeadObjectException thrown while running ApiCallRunner.");
            }
            return true;
        }
        String name = this.zac.getClass().getName();
        String str = feature.zza;
        long version = feature.getVersion();
        StringBuilder sb = new StringBuilder(name.length() + 77 + String.valueOf(str).length());
        ConstraintWidget$$ExternalSyntheticOutline0.m(
                sb, name, " could not execute call because it requires feature (", str, ", ");
        sb.append(version);
        sb.append(").");
        Log.w("GoogleApiManager", sb.toString());
        if (!this.zaa.zau || !zacVar.zaa(this)) {
            zacVar.zae(new UnsupportedApiCallException(feature));
            return true;
        }
        zabs zabsVar = new zabs(this.zad, feature);
        int indexOf = ((ArrayList) this.zak).indexOf(zabsVar);
        if (indexOf >= 0) {
            zabs zabsVar2 = (zabs) ((ArrayList) this.zak).get(indexOf);
            this.zaa.zat.removeMessages(15, zabsVar2);
            com.google.android.gms.internal.base.zaq zaqVar = this.zaa.zat;
            Message obtain = Message.obtain(zaqVar, 15, zabsVar2);
            this.zaa.getClass();
            zaqVar.sendMessageDelayed(obtain, 5000L);
        } else {
            ((ArrayList) this.zak).add(zabsVar);
            com.google.android.gms.internal.base.zaq zaqVar2 = this.zaa.zat;
            Message obtain2 = Message.obtain(zaqVar2, 15, zabsVar);
            this.zaa.getClass();
            zaqVar2.sendMessageDelayed(obtain2, 5000L);
            com.google.android.gms.internal.base.zaq zaqVar3 = this.zaa.zat;
            Message obtain3 = Message.obtain(zaqVar3, 16, zabsVar);
            this.zaa.getClass();
            zaqVar3.sendMessageDelayed(obtain3, 120000L);
            ConnectionResult connectionResult = new ConnectionResult(2, null);
            if (!zaM(connectionResult)) {
                this.zaa.zaG(connectionResult, this.zah);
            }
        }
        return false;
    }

    public final boolean zaM(ConnectionResult connectionResult) {
        synchronized (GoogleApiManager.zac) {
            try {
                GoogleApiManager googleApiManager = this.zaa;
                if (googleApiManager.zaq == null || !googleApiManager.zar.contains(this.zad)) {
                    return false;
                }
                zaae zaaeVar = this.zaa.zaq;
                int i = this.zah;
                zaaeVar.getClass();
                zam zamVar = new zam(connectionResult, i);
                if (zaaeVar.zab.compareAndSet(null, zamVar)) {
                    ((zap) zaaeVar).zad.post(new zao(zaaeVar, zamVar));
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void zao() {
        GoogleApiManager googleApiManager = this.zaa;
        Preconditions.checkHandlerThread(googleApiManager.zat);
        Api.Client client = this.zac;
        if (client.isConnected() || client.isConnecting()) {
            return;
        }
        try {
            int zab = googleApiManager.zam.zab(googleApiManager.zak, client);
            if (zab != 0) {
                ConnectionResult connectionResult = new ConnectionResult(zab, null);
                String name = client.getClass().getName();
                String connectionResult2 = connectionResult.toString();
                StringBuilder sb =
                        new StringBuilder(name.length() + 35 + connectionResult2.length());
                sb.append("The service for ");
                sb.append(name);
                sb.append(" is not available: ");
                sb.append(connectionResult2);
                Log.w("GoogleApiManager", sb.toString());
                zar(connectionResult, null);
                return;
            }
            zabu zabuVar = new zabu(googleApiManager, client, this.zad);
            if (client.requiresSignIn()) {
                zact zactVar = this.zai;
                Preconditions.checkNotNull(zactVar);
                SignInClientImpl signInClientImpl = zactVar.zag;
                if (signInClientImpl != null) {
                    signInClientImpl.disconnect();
                }
                zactVar.zaf.zaj = Integer.valueOf(System.identityHashCode(zactVar));
                com.google.android.gms.signin.zaa zaaVar = zactVar.zad;
                Context context = zactVar.zab;
                Looper looper = zactVar.zac.getLooper();
                ClientSettings clientSettings = zactVar.zaf;
                zactVar.zag =
                        (SignInClientImpl)
                                zaaVar.buildClient(
                                        context,
                                        looper,
                                        clientSettings,
                                        (Object) clientSettings.zai,
                                        (GoogleApiClient.ConnectionCallbacks) zactVar,
                                        (GoogleApiClient.OnConnectionFailedListener) zactVar);
                zactVar.zah = zabuVar;
                Set set = zactVar.zae;
                if (set == null || set.isEmpty()) {
                    zactVar.zac.post(new zacq(zactVar));
                } else {
                    SignInClientImpl signInClientImpl2 = zactVar.zag;
                    signInClientImpl2.getClass();
                    signInClientImpl2.connect(new BaseGmsClient.LegacyClientCallbackAdapter());
                }
            }
            try {
                client.connect(zabuVar);
            } catch (SecurityException e) {
                zar(new ConnectionResult(10), e);
            }
        } catch (IllegalStateException e2) {
            zar(new ConnectionResult(10), e2);
        }
    }

    public final void zap(zai zaiVar) {
        Preconditions.checkHandlerThread(this.zaa.zat);
        if (this.zac.isConnected()) {
            if (zaL(zaiVar)) {
                zaI();
                return;
            } else {
                ((LinkedList) this.zab).add(zaiVar);
                return;
            }
        }
        ((LinkedList) this.zab).add(zaiVar);
        ConnectionResult connectionResult = this.zal;
        if (connectionResult == null || connectionResult.zzb == 0 || connectionResult.zzc == null) {
            zao();
        } else {
            zar(connectionResult, null);
        }
    }

    public final void zar(ConnectionResult connectionResult, Exception exc) {
        SignInClientImpl signInClientImpl;
        Preconditions.checkHandlerThread(this.zaa.zat);
        zact zactVar = this.zai;
        if (zactVar != null && (signInClientImpl = zactVar.zag) != null) {
            signInClientImpl.disconnect();
        }
        Preconditions.checkHandlerThread(this.zaa.zat);
        this.zal = null;
        this.zaa.zam.zaa.clear();
        zaC(connectionResult);
        if ((this.zac instanceof com.google.android.gms.common.internal.service.zap)
                && connectionResult.zzb != 24) {
            GoogleApiManager googleApiManager = this.zaa;
            googleApiManager.zah = true;
            com.google.android.gms.internal.base.zaq zaqVar = googleApiManager.zat;
            zaqVar.sendMessageDelayed(zaqVar.obtainMessage(19), 300000L);
        }
        if (connectionResult.zzb == 4) {
            zaD(GoogleApiManager.zab);
            return;
        }
        if (this.zab.isEmpty()) {
            this.zal = connectionResult;
            return;
        }
        if (exc != null) {
            Preconditions.checkHandlerThread(this.zaa.zat);
            zaE(null, exc, false);
            return;
        }
        if (!this.zaa.zau) {
            zaD(GoogleApiManager.zaH(this.zad, connectionResult));
            return;
        }
        zaE(GoogleApiManager.zaH(this.zad, connectionResult), null, true);
        if (this.zab.isEmpty()
                || zaM(connectionResult)
                || this.zaa.zaG(connectionResult, this.zah)) {
            return;
        }
        if (connectionResult.zzb == 18) {
            this.zaj = true;
        }
        if (!this.zaj) {
            zaD(GoogleApiManager.zaH(this.zad, connectionResult));
            return;
        }
        com.google.android.gms.internal.base.zaq zaqVar2 = this.zaa.zat;
        Message obtain = Message.obtain(zaqVar2, 9, this.zad);
        this.zaa.getClass();
        zaqVar2.sendMessageDelayed(obtain, 5000L);
    }

    public final void zav() {
        Preconditions.checkHandlerThread(this.zaa.zat);
        Status status = GoogleApiManager.zaa;
        zaD(status);
        zaad zaadVar = this.zae;
        zaadVar.getClass();
        zaadVar.zah(status, false);
        for (ListenerHolder$ListenerKey listenerHolder$ListenerKey :
                (ListenerHolder$ListenerKey[])
                        ((HashMap) this.zag).keySet().toArray(new ListenerHolder$ListenerKey[0])) {
            zap(new zah(new TaskCompletionSource()));
        }
        zaC(new ConnectionResult(4));
        Api.Client client = this.zac;
        if (client.isConnected()) {
            client.onUserSignOut(new zabp(this));
        }
    }
}
