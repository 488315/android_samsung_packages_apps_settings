package com.google.android.gms.common.api.internal;

import android.app.ActivityManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.util.Log;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.collection.ArraySet;
import androidx.collection.ArraySet.ElementIterator;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.UnsupportedApiCallException;
import com.google.android.gms.common.internal.GmsClientSupervisor;
import com.google.android.gms.common.internal.MethodInvocation;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.RootTelemetryConfigManager;
import com.google.android.gms.common.internal.RootTelemetryConfiguration;
import com.google.android.gms.common.internal.TelemetryData;
import com.google.android.gms.common.internal.TelemetryLoggingOptions;
import com.google.android.gms.common.internal.zal;
import com.google.android.gms.common.util.DeviceProperties;
import com.google.android.gms.common.wrappers.InstantApps;
import com.google.android.gms.internal.base.zac;
import com.google.android.gms.internal.base.zad;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class GoogleApiManager implements Handler.Callback {
    public static final Status zaa =
            new Status(4, "Sign-out occurred while this API call was in progress.");
    public static final Status zab =
            new Status(4, "The user must be signed in to make this API call.");
    public static final Object zac = new Object();
    public static GoogleApiManager zad;
    public long zag;
    public boolean zah;
    public TelemetryData zai;
    public com.google.android.gms.common.internal.service.zao zaj;
    public final Context zak;
    public final GoogleApiAvailability zal;
    public final zal zam;
    public final AtomicInteger zan;
    public final AtomicInteger zao;
    public final Map zap;
    public final zaae zaq;
    public final ArraySet zar;
    public final ArraySet zas;
    public final com.google.android.gms.internal.base.zaq zat;
    public volatile boolean zau;

    public GoogleApiManager(Context context, Looper looper) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.zab;
        this.zag = 10000L;
        this.zah = false;
        this.zan = new AtomicInteger(1);
        this.zao = new AtomicInteger(0);
        this.zap = new ConcurrentHashMap(5, 0.75f, 1);
        this.zaq = null;
        this.zar = new ArraySet(0);
        this.zas = new ArraySet(0);
        this.zau = true;
        this.zak = context;
        com.google.android.gms.internal.base.zaq zaqVar =
                new com.google.android.gms.internal.base.zaq(looper, this);
        this.zat = zaqVar;
        this.zal = googleApiAvailability;
        this.zam = new zal();
        PackageManager packageManager = context.getPackageManager();
        if (DeviceProperties.zzi == null) {
            DeviceProperties.zzi =
                    Boolean.valueOf(
                            packageManager.hasSystemFeature("android.hardware.type.automotive"));
        }
        if (DeviceProperties.zzi.booleanValue()) {
            this.zau = false;
        }
        zaqVar.sendMessage(zaqVar.obtainMessage(6));
    }

    public static Status zaH(ApiKey apiKey, ConnectionResult connectionResult) {
        String str = apiKey.zab.zac;
        String valueOf = String.valueOf(connectionResult);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 63 + valueOf.length());
        sb.append("API: ");
        sb.append(str);
        sb.append(" is not available on this device. Connection failed with: ");
        sb.append(valueOf);
        return new Status(1, 17, sb.toString(), connectionResult.zzc, connectionResult);
    }

    public static GoogleApiManager zam(Context context) {
        GoogleApiManager googleApiManager;
        synchronized (zac) {
            try {
                if (zad == null) {
                    Looper looper = GmsClientSupervisor.getOrStartHandlerThread().getLooper();
                    Context applicationContext = context.getApplicationContext();
                    Object obj = GoogleApiAvailability.zaa;
                    zad = new GoogleApiManager(applicationContext, looper);
                }
                googleApiManager = zad;
            } catch (Throwable th) {
                throw th;
            }
        }
        return googleApiManager;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        Feature[] zab2;
        int i = message.what;
        zabq zabqVar = null;
        switch (i) {
            case 1:
                this.zag = true == ((Boolean) message.obj).booleanValue() ? 10000L : 300000L;
                this.zat.removeMessages(12);
                for (ApiKey apiKey : ((ConcurrentHashMap) this.zap).keySet()) {
                    com.google.android.gms.internal.base.zaq zaqVar = this.zat;
                    zaqVar.sendMessageDelayed(zaqVar.obtainMessage(12, apiKey), this.zag);
                }
                return true;
            case 2:
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(message.obj);
                throw null;
            case 3:
                for (zabq zabqVar2 : ((ConcurrentHashMap) this.zap).values()) {
                    Preconditions.checkHandlerThread(zabqVar2.zaa.zat);
                    zabqVar2.zal = null;
                    zabqVar2.zao();
                }
                return true;
            case 4:
            case 8:
            case 13:
                zach zachVar = (zach) message.obj;
                zabq zabqVar3 = (zabq) ((ConcurrentHashMap) this.zap).get(zachVar.zac.zaf);
                if (zabqVar3 == null) {
                    zabqVar3 = zaI(zachVar.zac);
                }
                if (!zabqVar3.zac.requiresSignIn() || this.zao.get() == zachVar.zab) {
                    zabqVar3.zap(zachVar.zaa);
                } else {
                    zachVar.zaa.zad(zaa);
                    zabqVar3.zav();
                }
                return true;
            case 5:
                int i2 = message.arg1;
                ConnectionResult connectionResult = (ConnectionResult) message.obj;
                Iterator it = ((ConcurrentHashMap) this.zap).values().iterator();
                while (true) {
                    if (it.hasNext()) {
                        zabq zabqVar4 = (zabq) it.next();
                        if (zabqVar4.zah == i2) {
                            zabqVar = zabqVar4;
                        }
                    }
                }
                if (zabqVar == null) {
                    StringBuilder sb = new StringBuilder(76);
                    sb.append("Could not find API instance ");
                    sb.append(i2);
                    sb.append(" while trying to fail enqueued calls.");
                    Log.wtf("GoogleApiManager", sb.toString(), new Exception());
                } else if (connectionResult.zzb == 13) {
                    GoogleApiAvailability googleApiAvailability = this.zal;
                    int i3 = connectionResult.zzb;
                    googleApiAvailability.getClass();
                    String errorString = GooglePlayServicesUtilLight.getErrorString(i3);
                    String str = connectionResult.zzd;
                    StringBuilder sb2 =
                            new StringBuilder(
                                    String.valueOf(errorString).length()
                                            + 69
                                            + String.valueOf(str).length());
                    sb2.append(
                            "Error resolution was canceled by the user, original error message: ");
                    sb2.append(errorString);
                    sb2.append(": ");
                    sb2.append(str);
                    zabqVar.zaD(new Status(17, sb2.toString()));
                } else {
                    zabqVar.zaD(zaH(zabqVar.zad, connectionResult));
                }
                return true;
            case 6:
                if (this.zak.getApplicationContext() instanceof Application) {
                    Application application = (Application) this.zak.getApplicationContext();
                    BackgroundDetector backgroundDetector = BackgroundDetector.zza;
                    synchronized (backgroundDetector) {
                        try {
                            if (!backgroundDetector.zze) {
                                application.registerActivityLifecycleCallbacks(backgroundDetector);
                                application.registerComponentCallbacks(backgroundDetector);
                                backgroundDetector.zze = true;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    zabl zablVar = new zabl(this);
                    synchronized (backgroundDetector) {
                        backgroundDetector.zzd.add(zablVar);
                    }
                    if (!backgroundDetector.zzc.get()) {
                        ActivityManager.RunningAppProcessInfo runningAppProcessInfo =
                                new ActivityManager.RunningAppProcessInfo();
                        ActivityManager.getMyMemoryState(runningAppProcessInfo);
                        if (!backgroundDetector.zzc.getAndSet(true)
                                && runningAppProcessInfo.importance > 100) {
                            backgroundDetector.zzb.set(true);
                        }
                    }
                    if (!backgroundDetector.zzb.get()) {
                        this.zag = 300000L;
                    }
                }
                return true;
            case 7:
                zaI((GoogleApi) message.obj);
                return true;
            case 9:
                if (((ConcurrentHashMap) this.zap).containsKey(message.obj)) {
                    zabq zabqVar5 = (zabq) ((ConcurrentHashMap) this.zap).get(message.obj);
                    Preconditions.checkHandlerThread(zabqVar5.zaa.zat);
                    if (zabqVar5.zaj) {
                        zabqVar5.zao();
                    }
                }
                return true;
            case 10:
                ArraySet arraySet = this.zas;
                arraySet.getClass();
                ArraySet.ElementIterator elementIterator = arraySet.new ElementIterator();
                while (elementIterator.hasNext()) {
                    zabq zabqVar6 =
                            (zabq)
                                    ((ConcurrentHashMap) this.zap)
                                            .remove((ApiKey) elementIterator.next());
                    if (zabqVar6 != null) {
                        zabqVar6.zav();
                    }
                }
                this.zas.clear();
                return true;
            case 11:
                if (((ConcurrentHashMap) this.zap).containsKey(message.obj)) {
                    zabq zabqVar7 = (zabq) ((ConcurrentHashMap) this.zap).get(message.obj);
                    GoogleApiManager googleApiManager = zabqVar7.zaa;
                    Preconditions.checkHandlerThread(googleApiManager.zat);
                    boolean z = zabqVar7.zaj;
                    if (z) {
                        if (z) {
                            GoogleApiManager googleApiManager2 = zabqVar7.zaa;
                            com.google.android.gms.internal.base.zaq zaqVar2 =
                                    googleApiManager2.zat;
                            ApiKey apiKey2 = zabqVar7.zad;
                            zaqVar2.removeMessages(11, apiKey2);
                            googleApiManager2.zat.removeMessages(9, apiKey2);
                            zabqVar7.zaj = false;
                        }
                        zabqVar7.zaD(
                                googleApiManager.zal.isGooglePlayServicesAvailable(
                                                        googleApiManager.zak,
                                                        GoogleApiAvailabilityLight
                                                                .GOOGLE_PLAY_SERVICES_VERSION_CODE)
                                                == 18
                                        ? new Status(
                                                21,
                                                "Connection timed out waiting for Google Play"
                                                    + " services update to complete.")
                                        : new Status(
                                                22,
                                                "API failed to connect while resuming due to an"
                                                    + " unknown error."));
                        zabqVar7.zac.disconnect("Timing out connection while resuming.");
                    }
                }
                return true;
            case 12:
                if (((ConcurrentHashMap) this.zap).containsKey(message.obj)) {
                    zabq zabqVar8 = (zabq) ((ConcurrentHashMap) this.zap).get(message.obj);
                    Preconditions.checkHandlerThread(zabqVar8.zaa.zat);
                    Api.Client client = zabqVar8.zac;
                    if (client.isConnected() && ((HashMap) zabqVar8.zag).size() == 0) {
                        zaad zaadVar = zabqVar8.zae;
                        if (zaadVar.zaa.isEmpty() && zaadVar.zab.isEmpty()) {
                            client.disconnect("Timing out service connection.");
                        } else {
                            zabqVar8.zaI();
                        }
                    }
                }
                return true;
            case 14:
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(message.obj);
                throw null;
            case 15:
                zabs zabsVar = (zabs) message.obj;
                if (((ConcurrentHashMap) this.zap).containsKey(zabsVar.zaa)) {
                    zabq zabqVar9 = (zabq) ((ConcurrentHashMap) this.zap).get(zabsVar.zaa);
                    if (((ArrayList) zabqVar9.zak).contains(zabsVar) && !zabqVar9.zaj) {
                        if (zabqVar9.zac.isConnected()) {
                            zabqVar9.zaF();
                        } else {
                            zabqVar9.zao();
                        }
                    }
                }
                return true;
            case 16:
                zabs zabsVar2 = (zabs) message.obj;
                if (((ConcurrentHashMap) this.zap).containsKey(zabsVar2.zaa)) {
                    zabq zabqVar10 = (zabq) ((ConcurrentHashMap) this.zap).get(zabsVar2.zaa);
                    if (((ArrayList) zabqVar10.zak).remove(zabsVar2)) {
                        GoogleApiManager googleApiManager3 = zabqVar10.zaa;
                        googleApiManager3.zat.removeMessages(15, zabsVar2);
                        googleApiManager3.zat.removeMessages(16, zabsVar2);
                        Feature feature = zabsVar2.zab;
                        ArrayList arrayList = new ArrayList(((LinkedList) zabqVar10.zab).size());
                        for (zai zaiVar : zabqVar10.zab) {
                            if ((zaiVar instanceof zac)
                                    && (zab2 = ((zac) zaiVar).zab(zabqVar10)) != null) {
                                int length = zab2.length;
                                int i4 = 0;
                                while (true) {
                                    if (i4 >= length) {
                                        break;
                                    }
                                    if (!Objects.equal(zab2[i4], feature)) {
                                        i4++;
                                    } else if (i4 >= 0) {
                                        arrayList.add(zaiVar);
                                    }
                                }
                            }
                        }
                        int size = arrayList.size();
                        for (int i5 = 0; i5 < size; i5++) {
                            zai zaiVar2 = (zai) arrayList.get(i5);
                            ((LinkedList) zabqVar10.zab).remove(zaiVar2);
                            zaiVar2.zae(new UnsupportedApiCallException(feature));
                        }
                    }
                }
                return true;
            case 17:
                final TelemetryData telemetryData = this.zai;
                if (telemetryData != null) {
                    if (telemetryData.zaa > 0 || zaF()) {
                        if (this.zaj == null) {
                            this.zaj =
                                    new com.google.android.gms.common.internal.service.zao(
                                            this.zak,
                                            com.google.android.gms.common.internal.service.zao.zae,
                                            TelemetryLoggingOptions.zaa,
                                            GoogleApi.Settings.DEFAULT_SETTINGS);
                        }
                        com.google.android.gms.common.internal.service.zao zaoVar = this.zaj;
                        zaoVar.getClass();
                        TaskApiCall$Builder taskApiCall$Builder = new TaskApiCall$Builder();
                        Feature[] featureArr = {zad.zaa};
                        taskApiCall$Builder.zac = featureArr;
                        taskApiCall$Builder.zaa =
                                new RemoteCall() { // from class:
                                                   // com.google.android.gms.common.internal.service.zam
                                    @Override // com.google.android.gms.common.api.internal.RemoteCall
                                    public final void accept(Object obj, Object obj2) {
                                        TaskCompletionSource taskCompletionSource =
                                                (TaskCompletionSource) obj2;
                                        zan zanVar = zao.zad;
                                        zai zaiVar3 = (zai) ((zap) obj).getService();
                                        Parcel obtain = Parcel.obtain();
                                        obtain.writeInterfaceToken(zaiVar3.zab);
                                        int i6 = zac.$r8$clinit;
                                        TelemetryData telemetryData2 = TelemetryData.this;
                                        if (telemetryData2 == null) {
                                            obtain.writeInt(0);
                                        } else {
                                            obtain.writeInt(1);
                                            telemetryData2.writeToParcel(obtain, 0);
                                        }
                                        try {
                                            zaiVar3.zaa.transact(1, obtain, null, 1);
                                            obtain.recycle();
                                            taskCompletionSource.setResult(null);
                                        } catch (Throwable th2) {
                                            obtain.recycle();
                                            throw th2;
                                        }
                                    }
                                };
                        zaoVar.zae(2, new zacv(taskApiCall$Builder, featureArr, false, 0));
                    }
                    this.zai = null;
                }
                return true;
            case 18:
                zace zaceVar = (zace) message.obj;
                if (zaceVar.zac == 0) {
                    final TelemetryData telemetryData2 =
                            new TelemetryData(zaceVar.zab, Arrays.asList(zaceVar.zaa));
                    if (this.zaj == null) {
                        this.zaj =
                                new com.google.android.gms.common.internal.service.zao(
                                        this.zak,
                                        com.google.android.gms.common.internal.service.zao.zae,
                                        TelemetryLoggingOptions.zaa,
                                        GoogleApi.Settings.DEFAULT_SETTINGS);
                    }
                    com.google.android.gms.common.internal.service.zao zaoVar2 = this.zaj;
                    zaoVar2.getClass();
                    TaskApiCall$Builder taskApiCall$Builder2 = new TaskApiCall$Builder();
                    Feature[] featureArr2 = {zad.zaa};
                    taskApiCall$Builder2.zac = featureArr2;
                    taskApiCall$Builder2.zaa =
                            new RemoteCall() { // from class:
                                               // com.google.android.gms.common.internal.service.zam
                                @Override // com.google.android.gms.common.api.internal.RemoteCall
                                public final void accept(Object obj, Object obj2) {
                                    TaskCompletionSource taskCompletionSource =
                                            (TaskCompletionSource) obj2;
                                    zan zanVar = zao.zad;
                                    zai zaiVar3 = (zai) ((zap) obj).getService();
                                    Parcel obtain = Parcel.obtain();
                                    obtain.writeInterfaceToken(zaiVar3.zab);
                                    int i6 = zac.$r8$clinit;
                                    TelemetryData telemetryData22 = TelemetryData.this;
                                    if (telemetryData22 == null) {
                                        obtain.writeInt(0);
                                    } else {
                                        obtain.writeInt(1);
                                        telemetryData22.writeToParcel(obtain, 0);
                                    }
                                    try {
                                        zaiVar3.zaa.transact(1, obtain, null, 1);
                                        obtain.recycle();
                                        taskCompletionSource.setResult(null);
                                    } catch (Throwable th2) {
                                        obtain.recycle();
                                        throw th2;
                                    }
                                }
                            };
                    zaoVar2.zae(2, new zacv(taskApiCall$Builder2, featureArr2, false, 0));
                } else {
                    TelemetryData telemetryData3 = this.zai;
                    if (telemetryData3 != null) {
                        List list = telemetryData3.zab;
                        if (telemetryData3.zaa != zaceVar.zab
                                || (list != null && list.size() >= zaceVar.zad)) {
                            this.zat.removeMessages(17);
                            final TelemetryData telemetryData4 = this.zai;
                            if (telemetryData4 != null) {
                                if (telemetryData4.zaa > 0 || zaF()) {
                                    if (this.zaj == null) {
                                        this.zaj =
                                                new com.google
                                                        .android
                                                        .gms
                                                        .common
                                                        .internal
                                                        .service
                                                        .zao(
                                                        this.zak,
                                                        com.google
                                                                .android
                                                                .gms
                                                                .common
                                                                .internal
                                                                .service
                                                                .zao
                                                                .zae,
                                                        TelemetryLoggingOptions.zaa,
                                                        GoogleApi.Settings.DEFAULT_SETTINGS);
                                    }
                                    com.google.android.gms.common.internal.service.zao zaoVar3 =
                                            this.zaj;
                                    zaoVar3.getClass();
                                    TaskApiCall$Builder taskApiCall$Builder3 =
                                            new TaskApiCall$Builder();
                                    Feature[] featureArr3 = {zad.zaa};
                                    taskApiCall$Builder3.zac = featureArr3;
                                    taskApiCall$Builder3.zaa =
                                            new RemoteCall() { // from class:
                                                               // com.google.android.gms.common.internal.service.zam
                                                @Override // com.google.android.gms.common.api.internal.RemoteCall
                                                public final void accept(Object obj, Object obj2) {
                                                    TaskCompletionSource taskCompletionSource =
                                                            (TaskCompletionSource) obj2;
                                                    zan zanVar = zao.zad;
                                                    zai zaiVar3 = (zai) ((zap) obj).getService();
                                                    Parcel obtain = Parcel.obtain();
                                                    obtain.writeInterfaceToken(zaiVar3.zab);
                                                    int i6 = zac.$r8$clinit;
                                                    TelemetryData telemetryData22 =
                                                            TelemetryData.this;
                                                    if (telemetryData22 == null) {
                                                        obtain.writeInt(0);
                                                    } else {
                                                        obtain.writeInt(1);
                                                        telemetryData22.writeToParcel(obtain, 0);
                                                    }
                                                    try {
                                                        zaiVar3.zaa.transact(1, obtain, null, 1);
                                                        obtain.recycle();
                                                        taskCompletionSource.setResult(null);
                                                    } catch (Throwable th2) {
                                                        obtain.recycle();
                                                        throw th2;
                                                    }
                                                }
                                            };
                                    zaoVar3.zae(
                                            2,
                                            new zacv(taskApiCall$Builder3, featureArr3, false, 0));
                                }
                                this.zai = null;
                            }
                        } else {
                            TelemetryData telemetryData5 = this.zai;
                            MethodInvocation methodInvocation = zaceVar.zaa;
                            if (telemetryData5.zab == null) {
                                telemetryData5.zab = new ArrayList();
                            }
                            telemetryData5.zab.add(methodInvocation);
                        }
                    }
                    if (this.zai == null) {
                        ArrayList arrayList2 = new ArrayList();
                        arrayList2.add(zaceVar.zaa);
                        this.zai = new TelemetryData(zaceVar.zab, arrayList2);
                        com.google.android.gms.internal.base.zaq zaqVar3 = this.zat;
                        zaqVar3.sendMessageDelayed(zaqVar3.obtainMessage(17), zaceVar.zac);
                    }
                }
                return true;
            case 19:
                this.zah = false;
                return true;
            default:
                StringBuilder sb3 = new StringBuilder(31);
                sb3.append("Unknown message id: ");
                sb3.append(i);
                Log.w("GoogleApiManager", sb3.toString());
                return false;
        }
    }

    public final boolean zaF() {
        if (this.zah) {
            return false;
        }
        RootTelemetryConfiguration rootTelemetryConfiguration =
                RootTelemetryConfigManager.getInstance().zzc;
        if (rootTelemetryConfiguration != null && !rootTelemetryConfiguration.zzb) {
            return false;
        }
        int i = this.zam.zaa.get(203400000, -1);
        return i == -1 || i == 0;
    }

    public final boolean zaG(ConnectionResult connectionResult, int i) {
        boolean z;
        PendingIntent activity;
        Boolean bool;
        GoogleApiAvailability googleApiAvailability = this.zal;
        Context context = this.zak;
        googleApiAvailability.getClass();
        synchronized (InstantApps.class) {
            Context applicationContext = context.getApplicationContext();
            Context context2 = InstantApps.zza;
            if (context2 != null
                    && (bool = InstantApps.zzb) != null
                    && context2 == applicationContext) {
                z = bool.booleanValue();
            }
            InstantApps.zzb = null;
            boolean isInstantApp = applicationContext.getPackageManager().isInstantApp();
            InstantApps.zzb = Boolean.valueOf(isInstantApp);
            InstantApps.zza = applicationContext;
            z = isInstantApp;
        }
        if (z) {
            return false;
        }
        int i2 = connectionResult.zzb;
        if (i2 == 0 || (activity = connectionResult.zzc) == null) {
            Intent errorResolutionIntent =
                    googleApiAvailability.getErrorResolutionIntent(context, i2, null);
            activity =
                    errorResolutionIntent != null
                            ? PendingIntent.getActivity(
                                    context, 0, errorResolutionIntent, 167772160)
                            : null;
        }
        if (activity == null) {
            return false;
        }
        int i3 = connectionResult.zzb;
        int i4 = GoogleApiActivity.$r8$clinit;
        Intent intent = new Intent(context, (Class<?>) GoogleApiActivity.class);
        intent.putExtra("pending_intent", activity);
        intent.putExtra("failing_client_id", i);
        intent.putExtra("notify_manager", true);
        googleApiAvailability.zae(
                context, i3, PendingIntent.getActivity(context, 0, intent, 167772160));
        return true;
    }

    public final zabq zaI(GoogleApi googleApi) {
        ApiKey apiKey = googleApi.zaf;
        zabq zabqVar = (zabq) ((ConcurrentHashMap) this.zap).get(apiKey);
        if (zabqVar == null) {
            zabqVar = new zabq(this, googleApi);
            ((ConcurrentHashMap) this.zap).put(apiKey, zabqVar);
        }
        if (zabqVar.zac.requiresSignIn()) {
            this.zas.add(apiKey);
        }
        zabqVar.zao();
        return zabqVar;
    }

    public final void zaz(ConnectionResult connectionResult, int i) {
        if (zaG(connectionResult, i)) {
            return;
        }
        com.google.android.gms.internal.base.zaq zaqVar = this.zat;
        zaqVar.sendMessage(zaqVar.obtainMessage(5, i, 0, connectionResult));
    }
}
