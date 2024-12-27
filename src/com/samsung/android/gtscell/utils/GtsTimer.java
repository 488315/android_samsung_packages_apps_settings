package com.samsung.android.gtscell.utils;

import android.os.Handler;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        bv = {1, 0, 3},
        d1 = {
            "\u0000&\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0000\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\t\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0006\u0010"
                + "\t\u001a\u00020\n"
                + "J\u0006\u0010\u000b\u001a\u00020\n"
                + "J\u0006\u0010\f\u001a\u00020\n"
                + "R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000¨\u0006\r"
        },
        d2 = {
            "Lcom/samsung/android/gtscell/utils/GtsTimer;",
            ApnSettings.MVNO_NONE,
            "handler",
            "Landroid/os/Handler;",
            "duration",
            ApnSettings.MVNO_NONE,
            "expired",
            "Ljava/lang/Runnable;",
            "(Landroid/os/Handler;JLjava/lang/Runnable;)V",
            "refresh",
            ApnSettings.MVNO_NONE,
            NetworkAnalyticsConstants.DataPoints.OPEN_TIME,
            "stop",
            "gtscell_release"
        },
        k = 1,
        mv = {1, 1, 16})
/* loaded from: classes4.dex */
public final class GtsTimer {
    private final long duration;
    private final Runnable expired;
    private final Handler handler;

    public GtsTimer(Handler handler, long j, Runnable expired) {
        Intrinsics.checkParameterIsNotNull(handler, "handler");
        Intrinsics.checkParameterIsNotNull(expired, "expired");
        this.handler = handler;
        this.duration = j;
        this.expired = expired;
    }

    public final void refresh() {
        stop();
        start();
    }

    public final void start() {
        this.handler.postDelayed(this.expired, this.duration);
    }

    public final void stop() {
        this.handler.removeCallbacks(this.expired);
    }
}
