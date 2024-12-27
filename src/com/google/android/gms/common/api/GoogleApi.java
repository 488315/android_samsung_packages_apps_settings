package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;

import androidx.collection.ArraySet;

import com.google.android.gms.common.api.internal.ApiExceptionMapper;
import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.zabq;
import com.google.android.gms.common.api.internal.zabv;
import com.google.android.gms.common.api.internal.zacd;
import com.google.android.gms.common.api.internal.zach;
import com.google.android.gms.common.api.internal.zacv;
import com.google.android.gms.common.api.internal.zag;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.RootTelemetryConfigManager;
import com.google.android.gms.common.internal.RootTelemetryConfiguration;
import com.google.android.gms.internal.base.zaq;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.zzj;
import com.google.android.gms.tasks.zzw;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class GoogleApi {
    public final GoogleApiManager zaa;
    public final Context zab;
    public final String zac;
    public final Api zad;
    public final Api.ApiOptions zae;
    public final ApiKey zaf;
    public final Looper zag;
    public final int zah;
    public final zabv zai;
    public final ApiExceptionMapper zaj;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Settings {
        public static final Settings DEFAULT_SETTINGS =
                new Settings(new ApiExceptionMapper(), Looper.getMainLooper());
        public final ApiExceptionMapper zaa;
        public final Looper zab;

        public Settings(ApiExceptionMapper apiExceptionMapper, Looper looper) {
            this.zaa = apiExceptionMapper;
            this.zab = looper;
        }
    }

    public GoogleApi(Context context, Api api, Api.ApiOptions apiOptions, Settings settings) {
        Preconditions.checkNotNull(context, "Null context is not permitted.");
        Preconditions.checkNotNull(api, "Api must not be null.");
        Preconditions.checkNotNull(
                settings, "Settings must not be null; use Settings.DEFAULT_SETTINGS instead.");
        this.zab = context.getApplicationContext();
        String str = null;
        try {
            Class[] clsArr = new Class[0];
            str = (String) Context.class.getMethod("getAttributionTag", null).invoke(context, null);
        } catch (IllegalAccessException
                | NoSuchMethodException
                | InvocationTargetException unused) {
        }
        this.zac = str;
        this.zad = api;
        this.zae = apiOptions;
        this.zag = settings.zab;
        this.zaf = new ApiKey(api, apiOptions, str);
        this.zai = new zabv(this);
        GoogleApiManager zam = GoogleApiManager.zam(this.zab);
        this.zaa = zam;
        this.zah = zam.zan.getAndIncrement();
        this.zaj = settings.zaa;
        zaq zaqVar = zam.zat;
        zaqVar.sendMessage(zaqVar.obtainMessage(7, this));
    }

    public final ClientSettings.Builder createClientSettingsBuilder() {
        ClientSettings.Builder builder = new ClientSettings.Builder();
        builder.zaa = null;
        Set emptySet = Collections.emptySet();
        if (builder.zab == null) {
            builder.zab = new ArraySet(0);
        }
        builder.zab.addAll(emptySet);
        builder.zad = this.zab.getClass().getName();
        builder.zac = this.zab.getPackageName();
        return builder;
    }

    public final zzw zae(int i, zacv zacvVar) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        GoogleApiManager googleApiManager = this.zaa;
        googleApiManager.getClass();
        int i2 = zacvVar.zac;
        zzw zzwVar = taskCompletionSource.zza;
        if (i2 != 0) {
            zacd zacdVar = null;
            if (googleApiManager.zaF()) {
                RootTelemetryConfiguration rootTelemetryConfiguration =
                        RootTelemetryConfigManager.getInstance().zzc;
                ApiKey apiKey = this.zaf;
                boolean z = true;
                if (rootTelemetryConfiguration != null) {
                    if (rootTelemetryConfiguration.zzb) {
                        boolean z2 = rootTelemetryConfiguration.zzc;
                        zabq zabqVar =
                                (zabq) ((ConcurrentHashMap) googleApiManager.zap).get(apiKey);
                        if (zabqVar != null) {
                            Object obj = zabqVar.zac;
                            if (obj instanceof BaseGmsClient) {
                                BaseGmsClient baseGmsClient = (BaseGmsClient) obj;
                                if (baseGmsClient.zzD != null && !baseGmsClient.isConnecting()) {
                                    ConnectionTelemetryConfiguration zab =
                                            zacd.zab(zabqVar, baseGmsClient, i2);
                                    if (zab != null) {
                                        zabqVar.zam++;
                                        z = zab.zzc;
                                    }
                                }
                            }
                        }
                        z = z2;
                    }
                }
                zacdVar =
                        new zacd(
                                googleApiManager,
                                i2,
                                apiKey,
                                z ? System.currentTimeMillis() : 0L,
                                z ? SystemClock.elapsedRealtime() : 0L,
                                null,
                                null);
            }
            if (zacdVar != null) {
                final zaq zaqVar = googleApiManager.zat;
                zaqVar.getClass();
                Executor executor =
                        new Executor() { // from class:
                                         // com.google.android.gms.common.api.internal.zabk
                            @Override // java.util.concurrent.Executor
                            public final void execute(Runnable runnable) {
                                zaqVar.post(runnable);
                            }
                        };
                zzwVar.getClass();
                zzwVar.zzb.zza(new zzj(executor, zacdVar));
                synchronized (zzwVar.zza) {
                    try {
                        if (zzwVar.zzc) {
                            zzwVar.zzb.zzb(zzwVar);
                        }
                    } finally {
                    }
                }
            }
        }
        zag zagVar = new zag(i, zacvVar, taskCompletionSource, this.zaj);
        zaq zaqVar2 = googleApiManager.zat;
        zaqVar2.sendMessage(
                zaqVar2.obtainMessage(4, new zach(zagVar, googleApiManager.zao.get(), this)));
        return zzwVar;
    }
}
