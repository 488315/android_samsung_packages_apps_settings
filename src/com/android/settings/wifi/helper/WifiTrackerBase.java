package com.android.settings.wifi.helper;

import android.os.HandlerThread;
import android.os.SimpleClock;
import android.os.SystemClock;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import java.time.ZoneOffset;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiTrackerBase implements DefaultLifecycleObserver {
    public static final AnonymousClass1 ELAPSED_REALTIME_CLOCK =
            new AnonymousClass1(ZoneOffset.UTC);
    protected HandlerThread mWorkerThread;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.helper.WifiTrackerBase$1, reason: invalid class name */
    public final class AnonymousClass1 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    public WifiTrackerBase(Lifecycle lifecycle, HandlerThread handlerThread) {
        lifecycle.addObserver(this);
        if (handlerThread == null) {
            handlerThread =
                    new HandlerThread(
                            getTag()
                                    + "{"
                                    + Integer.toHexString(System.identityHashCode(this))
                                    + "}",
                            10);
        }
        this.mWorkerThread = handlerThread;
        handlerThread.start();
    }

    public String getTag() {
        return "WifiTrackerBase";
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public final void onDestroy(LifecycleOwner lifecycleOwner) {
        this.mWorkerThread.quit();
    }
}
