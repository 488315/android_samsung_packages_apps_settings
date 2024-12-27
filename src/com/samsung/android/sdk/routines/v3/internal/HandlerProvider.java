package com.samsung.android.sdk.routines.v3.internal;

import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class HandlerProvider {
    public Object a = null;
    public final HashMap b = new HashMap();
    public final ConcurrentHashMap c = new ConcurrentHashMap();

    public final void b(String str) {
        Object obj = this.c.get(str);
        if (obj != null) {
            android.util.Log.i(
                    "Routine@Sdk[3.1.9]: ".concat("HandlerProvider"),
                    ComposerKt$$ExternalSyntheticOutline0.m("notify: tag=", str, ", notifyAll"));
            synchronized (obj) {
                try {
                    obj.notifyAll();
                } catch (IllegalMonitorStateException e) {
                    android.util.Log.e(
                            "Routine@Sdk[3.1.9]: ".concat("HandlerProvider"),
                            "notify: tag=" + str + ", IllegalMonitorStateException",
                            e);
                }
            }
        }
    }

    public final Object getWithTimeout(String str) {
        Object obj = this.b.get(str);
        if (obj == null && (obj = this.a) == null) {
            obj = null;
        }
        if (obj != null) {
            return obj;
        }
        android.util.Log.i(
                "Routine@Sdk[3.1.9]: ".concat("HandlerProvider"),
                ComposerKt$$ExternalSyntheticOutline0.m(
                        "getWithTimeout: tag=", str, ", wait 3000 ms until initialized..."));
        Object computeIfAbsent =
                this.c.computeIfAbsent(str, new HandlerProvider$$ExternalSyntheticLambda0());
        synchronized (computeIfAbsent) {
            try {
                computeIfAbsent.wait(3000L);
            } catch (IllegalMonitorStateException e) {
                android.util.Log.e(
                        "Routine@Sdk[3.1.9]: ".concat("HandlerProvider"),
                        "waitWithTimeout: tag=" + str + ", IllegalMonitorStateException",
                        e);
            } catch (InterruptedException e2) {
                android.util.Log.e(
                        "Routine@Sdk[3.1.9]: ".concat("HandlerProvider"),
                        "waitWithTimeout: tag=" + str + ", InterruptedException",
                        e2);
            }
        }
        android.util.Log.i(
                "Routine@Sdk[3.1.9]: ".concat("HandlerProvider"),
                ComposerKt$$ExternalSyntheticOutline0.m(
                        "getWithTimeout: tag=", str, ", notified or timeout"));
        Object obj2 = this.b.get(str);
        if (obj2 != null) {
            return obj2;
        }
        Object obj3 = this.a;
        if (obj3 != null) {
            return obj3;
        }
        return null;
    }
}
