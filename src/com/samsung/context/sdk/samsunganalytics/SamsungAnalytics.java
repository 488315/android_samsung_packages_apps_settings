package com.samsung.context.sdk.samsunganalytics;

import android.app.Application;
import android.os.Build;

import com.samsung.context.sdk.samsunganalytics.internal.Tracker;
import com.samsung.context.sdk.samsunganalytics.internal.util.Utils;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SamsungAnalytics {
    public static SamsungAnalytics instance;
    public final Tracker tracker;

    /* JADX WARN: Removed duplicated region for block: B:124:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0188 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01bf  */
    /* JADX WARN: Type inference failed for: r11v6, types: [com.samsung.context.sdk.samsunganalytics.internal.Tracker$4] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SamsungAnalytics(
            final android.app.Application r22,
            final com.samsung.context.sdk.samsunganalytics.Configuration r23) {
        /*
            Method dump skipped, instructions count: 1021
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.context.sdk.samsunganalytics.SamsungAnalytics.<init>(android.app.Application,"
                    + " com.samsung.context.sdk.samsunganalytics.Configuration):void");
    }

    public static SamsungAnalytics getInstance() {
        if (instance == null) {
            Utils.throwException("call after setConfiguration() method");
            if (!Build.TYPE.equals("eng")) {
                return getInstanceAndConfig(null, null);
            }
        }
        return instance;
    }

    public static SamsungAnalytics getInstanceAndConfig(
            Application application, Configuration configuration) {
        SamsungAnalytics samsungAnalytics = instance;
        if (samsungAnalytics == null || samsungAnalytics.tracker == null) {
            synchronized (SamsungAnalytics.class) {
                instance = new SamsungAnalytics(application, configuration);
            }
        }
        return instance;
    }

    public final void sendLog(Map map) {
        try {
            this.tracker.sendLog(map);
        } catch (NullPointerException unused) {
        }
    }
}
