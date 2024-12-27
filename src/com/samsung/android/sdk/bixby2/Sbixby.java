package com.samsung.android.sdk.bixby2;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.sdk.bixby2.provider.CapsuleProvider;
import com.samsung.android.sdk.bixby2.state.StateHandler;
import com.samsung.android.settings.bixby.actionhandler.BaseActionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class Sbixby {
    public static Map appMetaInfoMap;
    public static Sbixby mInstance;

    public static void addActionHandler(String str, BaseActionHandler baseActionHandler) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException(
                    "Action handler is NULL. pass valid app action handler implementation.");
        }
        Object obj = CapsuleProvider.sWaitLock;
        synchronized (obj) {
            try {
                Map map = CapsuleProvider.actionMap;
                if (((BaseActionHandler) ((HashMap) map).get(str)) == null) {
                    ((HashMap) map).put(str, baseActionHandler);
                    String str2 = CapsuleProvider.mActionId;
                    if (str2 != null && str2.equals(str)) {
                        Log.i("CapsuleProvider_1.0.25", "handler added: " + str);
                        obj.notify();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static synchronized Sbixby getInstance() {
        Sbixby sbixby;
        synchronized (Sbixby.class) {
            sbixby = mInstance;
            if (sbixby == null) {
                throw new IllegalStateException(
                        "The Sbixby instance is NULL. do initialize Sbixby before accessing"
                            + " instance.");
            }
        }
        return sbixby;
    }

    public static StateHandler getStateHandler() {
        StateHandler stateHandler;
        synchronized (StateHandler.class) {
            try {
                if (StateHandler.mInstance == null) {
                    StateHandler stateHandler2 = new StateHandler();
                    stateHandler2.mCallback = null;
                    StateHandler.mInstance = stateHandler2;
                }
                stateHandler = StateHandler.mInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return stateHandler;
    }

    public static void initialize(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("App Context is NULL. pass valid context.");
        }
        if (mInstance == null) {
            mInstance = new Sbixby();
        }
        Sbixby sbixby = mInstance;
        String packageName = context.getPackageName();
        sbixby.getClass();
        if (TextUtils.isEmpty(packageName)) {
            throw new IllegalArgumentException("package name is null or empty.");
        }
        Object obj = CapsuleProvider.sWaitLock;
        synchronized (obj) {
            try {
                if (!CapsuleProvider.mIsAppInitialized) {
                    CapsuleProvider.mIsAppInitialized = true;
                    Log.i("CapsuleProvider_1.0.25", "releasing initialize wait lock.");
                    obj.notify();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        new Timer()
                .schedule(
                        new TimerTask() { // from class:
                                          // com.samsung.android.sdk.bixby2.provider.CapsuleProvider.1
                            @Override // java.util.TimerTask, java.lang.Runnable
                            public final void run() {
                                CapsuleProvider.mWaitForHandler = false;
                            }
                        },
                        3000L);
        Log.i("Sbixby_1.0.25", "initialized");
    }
}
