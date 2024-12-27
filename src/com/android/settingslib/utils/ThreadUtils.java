package com.android.settingslib.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ThreadUtils {
    public static volatile ListeningExecutorService sListeningService;
    public static volatile Thread sMainThread;
    public static volatile Handler sMainThreadHandler;

    public static void ensureMainThread() {
        if (!isMainThread()) {
            throw new RuntimeException("Must be called on the UI thread");
        }
    }

    public static synchronized ListeningExecutorService getBackgroundExecutor() {
        ListeningExecutorService listeningExecutorService;
        synchronized (ThreadUtils.class) {
            try {
                if (sListeningService == null) {
                    sListeningService =
                            MoreExecutors.listeningDecorator(
                                    Executors.newFixedThreadPool(
                                            Runtime.getRuntime().availableProcessors()));
                }
                listeningExecutorService = sListeningService;
            } catch (Throwable th) {
                throw th;
            }
        }
        return listeningExecutorService;
    }

    public static Handler getUiThreadHandler() {
        if (sMainThreadHandler == null) {
            sMainThreadHandler = new Handler(Looper.getMainLooper());
        }
        return sMainThreadHandler;
    }

    public static boolean isMainThread() {
        if (sMainThread == null) {
            sMainThread = Looper.getMainLooper().getThread();
        }
        return Thread.currentThread() == sMainThread;
    }

    public static ListenableFuture postOnBackgroundThread(Runnable runnable) {
        return ((MoreExecutors.ListeningDecorator) getBackgroundExecutor()).submit(runnable);
    }

    public static void postOnMainThread(Runnable runnable) {
        getUiThreadHandler().post(runnable);
    }
}
