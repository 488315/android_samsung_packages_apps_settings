package com.samsung.android.settings.applications.cachedb;

import android.util.Log;

import java.util.concurrent.ThreadFactory;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AppListCacheThreadFactory implements ThreadFactory {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.applications.cachedb.AppListCacheThreadFactory$1, reason: invalid class name */
    public final class AnonymousClass1 implements Thread.UncaughtExceptionHandler {
        @Override // java.lang.Thread.UncaughtExceptionHandler
        public final void uncaughtException(Thread thread, Throwable th) {
            int i = AppListCacheThreadFactory.$r8$clinit;
            Log.d(
                    "AppListCacheThreadFactory",
                    "uncaughtException() threadName = "
                            + thread.getName()
                            + " / "
                            + th.getMessage());
        }
    }

    @Override // java.util.concurrent.ThreadFactory
    public final Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName("AppListCacheThreadFactory");
        thread.setPriority(4);
        thread.setUncaughtExceptionHandler(new AnonymousClass1());
        return thread;
    }
}
