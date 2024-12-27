package com.samsung.android.settings.analyzestorage.domain.thread;

import android.os.Handler;
import android.os.Looper;

import com.samsung.android.settings.analyzestorage.domain.log.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class ThreadExecutor {
    public static final Handler sMainHandler = new Handler(Looper.getMainLooper());

    public static void runOnMainThread(Runnable runnable) {
        sMainHandler.post(runnable);
    }

    public static void runOnWorkThread(Runnable runnable) {
        try {
            new Thread(runnable).start();
        } catch (OutOfMemoryError e) {
            Log.e("ThreadExecutor", "runOnWorkThread() ] OutOfMemoryError : " + e);
            runnable.run();
        }
    }
}
