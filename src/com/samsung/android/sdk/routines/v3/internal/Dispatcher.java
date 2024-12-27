package com.samsung.android.sdk.routines.v3.internal;

import android.os.Bundle;

import com.samsung.android.sdk.routines.v3.data.ActionResult$ResultCode;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class Dispatcher {
    public static Bundle c$2() {
        Bundle bundle = new Bundle();
        bundle.putInt(ExtraKey.RESULT_INT.a, ActionResult$ResultCode.FAIL_TIMEOUT.value);
        return bundle;
    }

    public abstract String a();

    /* JADX WARN: Removed duplicated region for block: B:12:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean a(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.String r0 = "wait: "
            monitor-enter(r10)
            r1 = 7000(0x1b58, double:3.4585E-320)
            r3 = 1
            long r4 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> Lf java.lang.InterruptedException -> L13
            r10.wait(r1)     // Catch: java.lang.Throwable -> Lf java.lang.InterruptedException -> L11
            goto L44
        Lf:
            r9 = move-exception
            goto L53
        L11:
            r6 = move-exception
            goto L16
        L13:
            r6 = move-exception
            r4 = 0
        L16:
            java.lang.Throwable r7 = new java.lang.Throwable     // Catch: java.lang.Throwable -> Lf
            r7.<init>()     // Catch: java.lang.Throwable -> Lf
            java.lang.StackTraceElement[] r7 = r7.getStackTrace()     // Catch: java.lang.Throwable -> Lf
            r7 = r7[r3]     // Catch: java.lang.Throwable -> Lf
            java.lang.String r7 = r7.getMethodName()     // Catch: java.lang.Throwable -> Lf
            java.lang.String r9 = r9.a()     // Catch: java.lang.Throwable -> Lf
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lf
            r8.<init>(r0)     // Catch: java.lang.Throwable -> Lf
            r8.append(r7)     // Catch: java.lang.Throwable -> Lf
            java.lang.String r0 = " - "
            r8.append(r0)     // Catch: java.lang.Throwable -> Lf
            java.lang.String r0 = r6.getMessage()     // Catch: java.lang.Throwable -> Lf
            r8.append(r0)     // Catch: java.lang.Throwable -> Lf
            java.lang.String r0 = r8.toString()     // Catch: java.lang.Throwable -> Lf
            com.samsung.android.sdk.routines.v3.internal.Log.a(r9, r0)     // Catch: java.lang.Throwable -> Lf
        L44:
            monitor-exit(r10)     // Catch: java.lang.Throwable -> Lf
            long r9 = java.lang.System.currentTimeMillis()
            long r9 = r9 - r4
            int r9 = (r9 > r1 ? 1 : (r9 == r1 ? 0 : -1))
            if (r9 < 0) goto L50
            r9 = r3
            goto L51
        L50:
            r9 = 0
        L51:
            r9 = r9 ^ r3
            return r9
        L53:
            monitor-exit(r10)     // Catch: java.lang.Throwable -> Lf
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.sdk.routines.v3.internal.Dispatcher.a(java.lang.Object):boolean");
    }
}
