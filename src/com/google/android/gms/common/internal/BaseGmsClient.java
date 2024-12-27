package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.zabo;
import com.google.android.gms.common.api.internal.zabp;
import com.google.android.gms.internal.safetynet.zzaf;
import com.samsung.android.knox.restriction.RestrictionPolicy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class BaseGmsClient {
    public static final Feature[] zze = new Feature[0];
    public volatile String zzA;
    zzu zza;
    public final Handler zzb;
    protected ConnectionProgressReportCallbacks zzc;
    public final Context zzl;
    public final GmsClientSupervisor zzn;
    public zzac zzr;
    public IInterface zzs;
    public zze zzu;
    public final BaseConnectionCallbacks zzw;
    public final BaseOnConnectionFailedListener zzx;
    public final int zzy;
    public final String zzz;
    public volatile String zzk = null;
    public final Object zzp = new Object();
    public final Object zzq = new Object();
    public final ArrayList zzt = new ArrayList();
    public int zzv = 1;
    public ConnectionResult zzB = null;
    public boolean zzC = false;
    public volatile zzj zzD = null;
    protected AtomicInteger zzd = new AtomicInteger(0);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface BaseConnectionCallbacks {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface BaseOnConnectionFailedListener {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ConnectionProgressReportCallbacks {
        void onReportServiceBinding(ConnectionResult connectionResult);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LegacyClientCallbackAdapter implements ConnectionProgressReportCallbacks {
        public LegacyClientCallbackAdapter() {}

        @Override // com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks
        public final void onReportServiceBinding(ConnectionResult connectionResult) {
            boolean z = connectionResult.zzb == 0;
            BaseGmsClient baseGmsClient = BaseGmsClient.this;
            if (z) {
                baseGmsClient.getRemoteService(null, baseGmsClient.getScopes());
                return;
            }
            BaseOnConnectionFailedListener baseOnConnectionFailedListener = baseGmsClient.zzx;
            if (baseOnConnectionFailedListener != null) {
                ((zai) baseOnConnectionFailedListener).zaa.onConnectionFailed(connectionResult);
            }
        }
    }

    public BaseGmsClient(
            Context context,
            Handler handler,
            GmsClientSupervisor gmsClientSupervisor,
            GoogleApiAvailabilityLight googleApiAvailabilityLight,
            int i,
            BaseConnectionCallbacks baseConnectionCallbacks,
            BaseOnConnectionFailedListener baseOnConnectionFailedListener) {
        Preconditions.checkNotNull(context, "Context must not be null");
        this.zzl = context;
        Preconditions.checkNotNull(handler, "Handler must not be null");
        this.zzb = handler;
        handler.getLooper();
        Preconditions.checkNotNull(gmsClientSupervisor, "Supervisor must not be null");
        this.zzn = gmsClientSupervisor;
        Preconditions.checkNotNull(googleApiAvailabilityLight, "API availability must not be null");
        this.zzy = i;
        this.zzw = baseConnectionCallbacks;
        this.zzx = baseOnConnectionFailedListener;
        this.zzz = null;
    }

    public static /* bridge */ /* synthetic */ boolean zzn(
            BaseGmsClient baseGmsClient, int i, int i2, IInterface iInterface) {
        synchronized (baseGmsClient.zzp) {
            try {
                if (baseGmsClient.zzv != i) {
                    return false;
                }
                baseGmsClient.zzp(i2, iInterface);
                return true;
            } finally {
            }
        }
    }

    public final void connect(ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        this.zzc = connectionProgressReportCallbacks;
        zzp(2, null);
    }

    public abstract IInterface createServiceInterface(IBinder iBinder);

    public final void disconnect() {
        this.zzd.incrementAndGet();
        synchronized (this.zzt) {
            try {
                int size = this.zzt.size();
                for (int i = 0; i < size; i++) {
                    zza zzaVar = (zza) this.zzt.get(i);
                    synchronized (zzaVar) {
                        zzaVar.zza$1 = null;
                    }
                }
                this.zzt.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
        synchronized (this.zzq) {
            this.zzr = null;
        }
        zzp(1, null);
    }

    public Account getAccount() {
        return null;
    }

    public Feature[] getApiFeatures() {
        return zze;
    }

    public final Feature[] getAvailableFeatures() {
        zzj zzjVar = this.zzD;
        if (zzjVar == null) {
            return null;
        }
        return zzjVar.zzb;
    }

    public final String getEndpointPackageName() {
        if (!isConnected() || this.zza == null) {
            throw new RuntimeException("Failed to connect when checking package");
        }
        return "com.google.android.gms";
    }

    public Bundle getGetServiceRequestExtraArgs() {
        return new Bundle();
    }

    public final String getLastDisconnectMessage() {
        return this.zzk;
    }

    public int getMinApkVersion() {
        return GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    public final void getRemoteService(IAccountAccessor iAccountAccessor, Set set) {
        Bundle getServiceRequestExtraArgs = getGetServiceRequestExtraArgs();
        GetServiceRequest getServiceRequest = new GetServiceRequest(this.zzy, this.zzA);
        getServiceRequest.zzd = this.zzl.getPackageName();
        getServiceRequest.zzg = getServiceRequestExtraArgs;
        if (set != null) {
            getServiceRequest.zzf = (Scope[]) set.toArray(new Scope[set.size()]);
        }
        if (requiresSignIn()) {
            Account account = getAccount();
            if (account == null) {
                account = new Account("<<default account>>", RestrictionPolicy.GOOGLE_ACCOUNT_TYPE);
            }
            getServiceRequest.zzh = account;
            if (iAccountAccessor != null) {
                getServiceRequest.zze = ((zzv) iAccountAccessor).zza;
            }
        }
        getServiceRequest.zzi = zze;
        getServiceRequest.zzj = getApiFeatures();
        if (this instanceof zzaf) {
            getServiceRequest.zzm = true;
        }
        try {
            try {
                synchronized (this.zzq) {
                    try {
                        zzac zzacVar = this.zzr;
                        if (zzacVar != null) {
                            zzacVar.getService(new zzd(this, this.zzd.get()), getServiceRequest);
                        } else {
                            Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                        }
                    } finally {
                    }
                }
            } catch (DeadObjectException e) {
                Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
                int i = this.zzd.get();
                Handler handler = this.zzb;
                handler.sendMessage(handler.obtainMessage(6, i, 3));
            } catch (SecurityException e2) {
                throw e2;
            }
        } catch (RemoteException | RuntimeException e3) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e3);
            int i2 = this.zzd.get();
            zzf zzfVar = new zzf(this, 8, null, null);
            Handler handler2 = this.zzb;
            handler2.sendMessage(handler2.obtainMessage(1, i2, -1, zzfVar));
        }
    }

    public Set getScopes() {
        return Collections.emptySet();
    }

    public final IInterface getService() {
        IInterface iInterface;
        synchronized (this.zzp) {
            try {
                if (this.zzv == 5) {
                    throw new DeadObjectException();
                }
                if (!isConnected()) {
                    throw new IllegalStateException(
                            "Not connected. Call connect() and wait for onConnected() to be"
                                + " called.");
                }
                iInterface = this.zzs;
                Preconditions.checkNotNull(iInterface, "Client is connected but service is null");
            } catch (Throwable th) {
                throw th;
            }
        }
        return iInterface;
    }

    public abstract String getServiceDescriptor();

    public abstract String getStartServiceAction();

    public boolean getUseDynamicLookup() {
        return getMinApkVersion() >= 211700000;
    }

    public final boolean isConnected() {
        boolean z;
        synchronized (this.zzp) {
            z = this.zzv == 4;
        }
        return z;
    }

    public final boolean isConnecting() {
        boolean z;
        synchronized (this.zzp) {
            int i = this.zzv;
            z = true;
            if (i != 2 && i != 3) {
                z = false;
            }
        }
        return z;
    }

    public final void onUserSignOut(zabp zabpVar) {
        zabpVar.zaa.zaa.zat.post(new zabo(zabpVar));
    }

    public boolean requiresSignIn() {
        return false;
    }

    public void triggerNotAvailable(
            ConnectionProgressReportCallbacks connectionProgressReportCallbacks,
            int i,
            PendingIntent pendingIntent) {
        Preconditions.checkNotNull(
                connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        this.zzc = connectionProgressReportCallbacks;
        int i2 = this.zzd.get();
        Handler handler = this.zzb;
        handler.sendMessage(handler.obtainMessage(3, i2, i, pendingIntent));
    }

    public final void zzp(int i, IInterface iInterface) {
        zzu zzuVar;
        if ((i == 4) != (iInterface != null)) {
            throw new IllegalArgumentException();
        }
        synchronized (this.zzp) {
            try {
                this.zzv = i;
                this.zzs = iInterface;
                if (i == 1) {
                    zze zzeVar = this.zzu;
                    if (zzeVar != null) {
                        GmsClientSupervisor gmsClientSupervisor = this.zzn;
                        String str = this.zza.zza;
                        Preconditions.checkNotNull(str);
                        this.zza.getClass();
                        if (this.zzz == null) {
                            this.zzl.getClass();
                        }
                        gmsClientSupervisor.zzb(str, zzeVar, this.zza.zzd);
                        this.zzu = null;
                    }
                } else if (i == 2 || i == 3) {
                    zze zzeVar2 = this.zzu;
                    if (zzeVar2 != null && (zzuVar = this.zza) != null) {
                        String str2 = zzuVar.zza;
                        StringBuilder sb =
                                new StringBuilder(
                                        String.valueOf(str2).length()
                                                + 70
                                                + "com.google.android.gms".length());
                        sb.append(
                                "Calling connect() while still connected, missing disconnect()"
                                    + " for ");
                        sb.append(str2);
                        sb.append(" on com.google.android.gms");
                        Log.e("GmsClient", sb.toString());
                        GmsClientSupervisor gmsClientSupervisor2 = this.zzn;
                        String str3 = this.zza.zza;
                        Preconditions.checkNotNull(str3);
                        this.zza.getClass();
                        if (this.zzz == null) {
                            this.zzl.getClass();
                        }
                        gmsClientSupervisor2.zzb(str3, zzeVar2, this.zza.zzd);
                        this.zzd.incrementAndGet();
                    }
                    zze zzeVar3 = new zze(this, this.zzd.get());
                    this.zzu = zzeVar3;
                    String startServiceAction = getStartServiceAction();
                    boolean useDynamicLookup = getUseDynamicLookup();
                    this.zza = new zzu(startServiceAction, useDynamicLookup);
                    if (useDynamicLookup && getMinApkVersion() < 17895000) {
                        String valueOf = String.valueOf(this.zza.zza);
                        throw new IllegalStateException(
                                valueOf.length() != 0
                                        ? "Internal Error, the minimum apk version of this BaseGmsClient is too low to support dynamic lookup. Start service action: "
                                                .concat(valueOf)
                                        : new String(
                                                "Internal Error, the minimum apk version of this"
                                                    + " BaseGmsClient is too low to support dynamic"
                                                    + " lookup. Start service action: "));
                    }
                    GmsClientSupervisor gmsClientSupervisor3 = this.zzn;
                    String str4 = this.zza.zza;
                    Preconditions.checkNotNull(str4);
                    this.zza.getClass();
                    String str5 = this.zzz;
                    if (str5 == null) {
                        str5 = this.zzl.getClass().getName();
                    }
                    if (!gmsClientSupervisor3.zzc(
                            new zzn(str4, this.zza.zzd), zzeVar3, str5, null)) {
                        String str6 = this.zza.zza;
                        StringBuilder sb2 =
                                new StringBuilder(
                                        String.valueOf(str6).length()
                                                + 34
                                                + "com.google.android.gms".length());
                        sb2.append("unable to connect to service: ");
                        sb2.append(str6);
                        sb2.append(" on com.google.android.gms");
                        Log.w("GmsClient", sb2.toString());
                        int i2 = this.zzd.get();
                        zzg zzgVar = new zzg(this, 16);
                        Handler handler = this.zzb;
                        handler.sendMessage(handler.obtainMessage(7, i2, -1, zzgVar));
                    }
                } else if (i == 4) {
                    Preconditions.checkNotNull(iInterface);
                    System.currentTimeMillis();
                }
            } finally {
            }
        }
    }

    public BaseGmsClient(
            Context context,
            Looper looper,
            GmsClientSupervisor gmsClientSupervisor,
            GoogleApiAvailabilityLight googleApiAvailabilityLight,
            int i,
            BaseConnectionCallbacks baseConnectionCallbacks,
            BaseOnConnectionFailedListener baseOnConnectionFailedListener,
            String str) {
        Preconditions.checkNotNull(context, "Context must not be null");
        this.zzl = context;
        Preconditions.checkNotNull(looper, "Looper must not be null");
        Preconditions.checkNotNull(gmsClientSupervisor, "Supervisor must not be null");
        this.zzn = gmsClientSupervisor;
        Preconditions.checkNotNull(googleApiAvailabilityLight, "API availability must not be null");
        this.zzb = new zzb(this, looper);
        this.zzy = i;
        this.zzw = baseConnectionCallbacks;
        this.zzx = baseOnConnectionFailedListener;
        this.zzz = str;
    }

    public final void disconnect(String str) {
        this.zzk = str;
        disconnect();
    }
}
