package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BackgroundDetector
        implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {
    public static final BackgroundDetector zza = new BackgroundDetector();
    public final AtomicBoolean zzb = new AtomicBoolean();
    public final AtomicBoolean zzc = new AtomicBoolean();
    public final ArrayList zzd = new ArrayList();
    public boolean zze = false;

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        boolean compareAndSet = this.zzb.compareAndSet(true, false);
        this.zzc.set(true);
        if (compareAndSet) {
            zza(false);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
        boolean compareAndSet = this.zzb.compareAndSet(true, false);
        this.zzc.set(true);
        if (compareAndSet) {
            zza(false);
        }
    }

    @Override // android.content.ComponentCallbacks2
    public final void onTrimMemory(int i) {
        if (i == 20 && this.zzb.compareAndSet(false, true)) {
            this.zzc.set(true);
            zza(true);
        }
    }

    public final void zza(boolean z) {
        synchronized (zza) {
            try {
                Iterator it = this.zzd.iterator();
                while (it.hasNext()) {
                    com.google.android.gms.internal.base.zaq zaqVar = ((zabl) it.next()).zaa.zat;
                    zaqVar.sendMessage(zaqVar.obtainMessage(1, Boolean.valueOf(z)));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.ComponentCallbacks
    public final void onLowMemory() {}

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {}

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {}

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {}

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {}

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {}

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {}
}
