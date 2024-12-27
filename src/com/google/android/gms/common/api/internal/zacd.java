package com.google.android.gms.common.api.internal;

import android.os.SystemClock;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;
import com.google.android.gms.common.internal.MethodInvocation;
import com.google.android.gms.common.internal.RootTelemetryConfigManager;
import com.google.android.gms.common.internal.RootTelemetryConfiguration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.zzw;

import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zacd implements OnCompleteListener {
    public final GoogleApiManager zaa;
    public final int zab;
    public final ApiKey zac;
    public final long zad;
    public final long zae;

    public zacd(
            GoogleApiManager googleApiManager,
            int i,
            ApiKey apiKey,
            long j,
            long j2,
            String str,
            String str2) {
        this.zaa = googleApiManager;
        this.zab = i;
        this.zac = apiKey;
        this.zad = j;
        this.zae = j2;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0031 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0032 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.android.gms.common.internal.ConnectionTelemetryConfiguration zab(
            com.google.android.gms.common.api.internal.zabq r5,
            com.google.android.gms.common.internal.BaseGmsClient r6,
            int r7) {
        /*
            com.google.android.gms.common.internal.zzj r6 = r6.zzD
            r0 = 0
            if (r6 != 0) goto L7
            r6 = r0
            goto L9
        L7:
            com.google.android.gms.common.internal.ConnectionTelemetryConfiguration r6 = r6.zzd
        L9:
            if (r6 == 0) goto L36
            boolean r1 = r6.zzb
            if (r1 == 0) goto L36
            int[] r1 = r6.zzd
            r2 = 0
            if (r1 != 0) goto L24
            int[] r1 = r6.zzf
            if (r1 != 0) goto L19
            goto L2b
        L19:
            int r3 = r1.length
        L1a:
            if (r2 >= r3) goto L2b
            r4 = r1[r2]
            if (r4 != r7) goto L21
            goto L36
        L21:
            int r2 = r2 + 1
            goto L1a
        L24:
            int r3 = r1.length
        L25:
            if (r2 >= r3) goto L36
            r4 = r1[r2]
            if (r4 != r7) goto L33
        L2b:
            int r5 = r5.zam
            int r7 = r6.zze
            if (r5 >= r7) goto L32
            return r6
        L32:
            return r0
        L33:
            int r2 = r2 + 1
            goto L25
        L36:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.gms.common.api.internal.zacd.zab(com.google.android.gms.common.api.internal.zabq,"
                    + " com.google.android.gms.common.internal.BaseGmsClient,"
                    + " int):com.google.android.gms.common.internal.ConnectionTelemetryConfiguration");
    }

    @Override // com.google.android.gms.tasks.OnCompleteListener
    public final void onComplete(Task task) {
        int i;
        int i2;
        int i3;
        Exception exc;
        int i4;
        int i5;
        long j;
        long j2;
        GoogleApiManager googleApiManager = this.zaa;
        if (googleApiManager.zaF()) {
            RootTelemetryConfiguration rootTelemetryConfiguration =
                    RootTelemetryConfigManager.getInstance().zzc;
            if (rootTelemetryConfiguration == null || rootTelemetryConfiguration.zzb) {
                zabq zabqVar = (zabq) ((ConcurrentHashMap) googleApiManager.zap).get(this.zac);
                if (zabqVar != null) {
                    Object obj = zabqVar.zac;
                    if (obj instanceof BaseGmsClient) {
                        BaseGmsClient baseGmsClient = (BaseGmsClient) obj;
                        long j3 = this.zad;
                        int i6 = 0;
                        boolean z = j3 > 0;
                        int i7 = baseGmsClient.zzy;
                        if (rootTelemetryConfiguration != null) {
                            z &= rootTelemetryConfiguration.zzc;
                            i2 = rootTelemetryConfiguration.zzd;
                            i = rootTelemetryConfiguration.zze;
                            i3 = rootTelemetryConfiguration.zza;
                            if (baseGmsClient.zzD != null && !baseGmsClient.isConnecting()) {
                                ConnectionTelemetryConfiguration zab =
                                        zab(zabqVar, baseGmsClient, this.zab);
                                if (zab == null) {
                                    return;
                                }
                                boolean z2 = zab.zzc && j3 > 0;
                                i = zab.zze;
                                z = z2;
                            }
                        } else {
                            i = 100;
                            i2 = 5000;
                            i3 = 0;
                        }
                        int i8 = i2;
                        int i9 = i;
                        if (task.isSuccessful()) {
                            i4 = 0;
                        } else {
                            zzw zzwVar = (zzw) task;
                            synchronized (zzwVar.zza) {
                                exc = zzwVar.zzf;
                            }
                            if (exc instanceof ApiException) {
                                Status status = ((ApiException) exc).getStatus();
                                i6 = status.zzc;
                                ConnectionResult connectionResult = status.zzf;
                                if (connectionResult != null) {
                                    i4 = connectionResult.zzb;
                                }
                            } else {
                                i6 = 101;
                            }
                            i4 = -1;
                        }
                        if (z) {
                            long currentTimeMillis = System.currentTimeMillis();
                            i5 = (int) (SystemClock.elapsedRealtime() - this.zae);
                            j = j3;
                            j2 = currentTimeMillis;
                        } else {
                            i5 = -1;
                            j = 0;
                            j2 = 0;
                        }
                        com.google.android.gms.internal.base.zaq zaqVar = googleApiManager.zat;
                        zaqVar.sendMessage(
                                zaqVar.obtainMessage(
                                        18,
                                        new zace(
                                                new MethodInvocation(
                                                        this.zab, i6, i4, j, j2, null, null, i7,
                                                        i5),
                                                i3,
                                                i8,
                                                i9)));
                    }
                }
            }
        }
    }
}
