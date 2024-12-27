package com.android.settingslib.avatarpicker;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ThreadUtils {
    public static volatile ListeningExecutorService sListeningService;

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
}
