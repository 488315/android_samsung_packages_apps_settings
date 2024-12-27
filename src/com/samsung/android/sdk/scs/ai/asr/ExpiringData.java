package com.samsung.android.sdk.scs.ai.asr;

import android.os.Build;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ExpiringData {
    public static final Duration DEFAULT_DURATION;
    public final String TAG;
    public Function checkValidate;
    public final AtomicReference data;
    public final Supplier defaultSupplier;
    public long lastTimeUpdate;
    public final String name;
    public final Duration timeout;

    static {
        DEFAULT_DURATION =
                "user".equals(Build.TYPE) ^ true
                        ? Duration.ofSeconds(30L)
                        : Duration.ofMinutes(15L);
    }

    public ExpiringData(String str, Supplier supplier) {
        Duration duration = DEFAULT_DURATION;
        this.TAG = "ExpiringData@" + hashCode();
        this.data = new AtomicReference(null);
        this.checkValidate = new ExpiringData$$ExternalSyntheticLambda3(0);
        this.lastTimeUpdate = System.currentTimeMillis();
        this.name = str;
        this.defaultSupplier = supplier;
        this.timeout = duration;
    }
}
