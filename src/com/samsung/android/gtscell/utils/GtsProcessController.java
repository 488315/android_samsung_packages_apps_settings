package com.samsung.android.gtscell.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;

import com.samsung.android.gtscell.log.GLogger;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000R\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\t\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0017\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u0018H\u0082\bJ\u0016\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001cR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\n"
                + "\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\r"
                + "X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0018\u0010\u0010\u001a\u00020\u0011*\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001d"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/utils/GtsProcessController;",
            ApnSettings.MVNO_NONE,
            "()V",
            "forcingToImportant",
            "Ljava/util/concurrent/atomic/AtomicBoolean;",
            "permission",
            ApnSettings.MVNO_NONE,
            "permissionState",
            "Ljava/util/concurrent/atomic/AtomicInteger;",
            "shouldCheckPermission",
            "timeout",
            ApnSettings.MVNO_NONE,
            "timer",
            "Lcom/samsung/android/gtscell/utils/GtsTimer;",
            "token",
            "Landroid/os/IBinder;",
            "activityManager",
            "Landroid/app/ActivityManager;",
            "Landroid/content/Context;",
            "getActivityManager",
            "(Landroid/content/Context;)Landroid/app/ActivityManager;",
            "safe",
            ApnSettings.MVNO_NONE,
            "block",
            "Lkotlin/Function0;",
            "setProcessImportant",
            "context",
            "isForeground",
            ApnSettings.MVNO_NONE,
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsProcessController {
    private static final String permission = "android.permission.SET_PROCESS_LIMIT";
    private static final long timeout = 30000;
    private static GtsTimer timer;
    public static final GtsProcessController INSTANCE = new GtsProcessController();
    private static final IBinder token = new Binder();
    private static final AtomicBoolean forcingToImportant = new AtomicBoolean(false);
    private static final AtomicBoolean shouldCheckPermission = new AtomicBoolean(true);
    private static final AtomicInteger permissionState = new AtomicInteger(-1);

    private GtsProcessController() {}

    private final ActivityManager getActivityManager(Context context) {
        Object systemService = context.getSystemService("activity");
        if (systemService != null) {
            return (ActivityManager) systemService;
        }
        throw new TypeCastException(
                "null cannot be cast to non-null type android.app.ActivityManager");
    }

    private final void safe(Function0 block) {
        try {
            block.mo1068invoke();
        } catch (Throwable th) {
            GLogger.INSTANCE.getGlobal().error(th.toString(), new Object[0]);
        }
    }

    public final void setProcessImportant(Context context, boolean isForeground) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (shouldCheckPermission.getAndSet(false)) {
            permissionState.set(context.checkSelfPermission(permission));
        }
        if (permissionState.get() == 0) {
            try {
                AtomicBoolean atomicBoolean = forcingToImportant;
                if (!atomicBoolean.compareAndSet(!isForeground, isForeground)) {
                    if (isForeground && atomicBoolean.get()) {
                        GLogger.INSTANCE.getGlobal().info("timer refresh", new Object[0]);
                        GtsTimer gtsTimer = timer;
                        if (gtsTimer != null) {
                            gtsTimer.refresh();
                            return;
                        }
                        return;
                    }
                    return;
                }
                GLogger.INSTANCE
                        .getGlobal()
                        .info("setProcessImportant isForeground:" + isForeground, new Object[0]);
                GtsProcessController gtsProcessController = INSTANCE;
                Context applicationContext = context.getApplicationContext();
                Intrinsics.checkExpressionValueIsNotNull(
                        applicationContext, "context.applicationContext");
                final ActivityManager activityManager =
                        gtsProcessController.getActivityManager(applicationContext);
                activityManager.semSetProcessImportant(token, Process.myPid(), isForeground);
                if (isForeground) {
                    GtsTimer gtsTimer2 =
                            new GtsTimer(
                                    new Handler(Looper.getMainLooper()),
                                    timeout,
                                    new Runnable() { // from class:
                                                     // com.samsung.android.gtscell.utils.GtsProcessController$setProcessImportant$1$1$1
                                        @Override // java.lang.Runnable
                                        public void run() {
                                            IBinder iBinder;
                                            AtomicBoolean atomicBoolean2;
                                            synchronized (this) {
                                                GLogger.INSTANCE
                                                        .getGlobal()
                                                        .info("timer expired", new Object[0]);
                                                ActivityManager activityManager2 = activityManager;
                                                GtsProcessController gtsProcessController2 =
                                                        GtsProcessController.INSTANCE;
                                                iBinder = GtsProcessController.token;
                                                activityManager2.semSetProcessImportant(
                                                        iBinder, Process.myPid(), false);
                                                atomicBoolean2 =
                                                        GtsProcessController.forcingToImportant;
                                                atomicBoolean2.set(false);
                                            }
                                        }
                                    });
                    gtsTimer2.start();
                    timer = gtsTimer2;
                } else {
                    GtsTimer gtsTimer3 = timer;
                    if (gtsTimer3 != null) {
                        gtsTimer3.stop();
                    }
                    timer = null;
                }
            } catch (Throwable th) {
                GLogger.INSTANCE.getGlobal().error(th.toString(), new Object[0]);
            }
        }
    }
}
