package com.google.android.gms.common.internal;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class RootTelemetryConfigManager {
    public static RootTelemetryConfigManager zza;
    public static final RootTelemetryConfiguration zzb =
            new RootTelemetryConfiguration(0, false, false, 0, 0);
    public RootTelemetryConfiguration zzc;

    public static synchronized RootTelemetryConfigManager getInstance() {
        RootTelemetryConfigManager rootTelemetryConfigManager;
        synchronized (RootTelemetryConfigManager.class) {
            try {
                if (zza == null) {
                    zza = new RootTelemetryConfigManager();
                }
                rootTelemetryConfigManager = zza;
            } catch (Throwable th) {
                throw th;
            }
        }
        return rootTelemetryConfigManager;
    }

    public final synchronized void zza(RootTelemetryConfiguration rootTelemetryConfiguration) {
        if (rootTelemetryConfiguration == null) {
            this.zzc = zzb;
            return;
        }
        RootTelemetryConfiguration rootTelemetryConfiguration2 = this.zzc;
        if (rootTelemetryConfiguration2 == null
                || rootTelemetryConfiguration2.zza < rootTelemetryConfiguration.zza) {
            this.zzc = rootTelemetryConfiguration;
        }
    }
}
