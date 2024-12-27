package com.android.settings.utils;

import android.content.Context;
import android.hardware.SensorPrivacyManager;

import com.android.settings.privacy.SensorToggleController;

import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SensorPrivacyManagerHelper
        implements SensorPrivacyManager.OnSensorPrivacyChangedListener {
    public static SensorPrivacyManagerHelper sInstance;
    public final Map cache;
    public final Map callbacks;
    public final Object lock;
    public final SensorPrivacyManager sensorPrivacyManager;

    public SensorPrivacyManagerHelper(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.cache = new LinkedHashMap();
        this.callbacks = new LinkedHashMap();
        this.lock = new Object();
        Object systemService = context.getSystemService((Class<Object>) SensorPrivacyManager.class);
        Intrinsics.checkNotNull(systemService);
        SensorPrivacyManager sensorPrivacyManager = (SensorPrivacyManager) systemService;
        this.sensorPrivacyManager = sensorPrivacyManager;
        sensorPrivacyManager.addSensorPrivacyListener(context.getMainExecutor(), this);
    }

    public static final SensorPrivacyManagerHelper getInstance(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (sInstance == null) {
            sInstance = new SensorPrivacyManagerHelper(context);
        }
        return sInstance;
    }

    public final void addSensorBlockedListener(
            int i, int i2, Executor executor, SensorToggleController sensorToggleController) {
        if (i == -1) {
            addSensorBlockedListener(1, i2, executor, sensorToggleController);
            addSensorBlockedListener(2, i2, executor, sensorToggleController);
            return;
        }
        if (i2 == -1) {
            addSensorBlockedListener(i, 1, executor, sensorToggleController);
            addSensorBlockedListener(i, 2, executor, sensorToggleController);
            return;
        }
        synchronized (this.lock) {
            try {
                Map map = this.callbacks;
                Pair pair = new Pair(Integer.valueOf(i), Integer.valueOf(i2));
                LinkedHashMap linkedHashMap = (LinkedHashMap) map;
                Object obj = linkedHashMap.get(pair);
                if (obj == null) {
                    obj = new LinkedHashSet();
                    linkedHashMap.put(pair, obj);
                }
                Intrinsics.checkNotNull(sensorToggleController);
                Intrinsics.checkNotNull(executor);
                ((Set) obj).add(new Pair(sensorToggleController, executor));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isSensorBlocked(int i, int i2) {
        synchronized (this.lock) {
            if (i == -1) {
                boolean z = true;
                if (!isSensorBlocked(1, i2) && !isSensorBlocked(2, i2)) {
                    z = false;
                }
                return z;
            }
            Map map = this.cache;
            Pair pair = new Pair(Integer.valueOf(i), Integer.valueOf(i2));
            LinkedHashMap linkedHashMap = (LinkedHashMap) map;
            Object obj = linkedHashMap.get(pair);
            if (obj == null) {
                obj = Boolean.valueOf(this.sensorPrivacyManager.isSensorPrivacyEnabled(i, i2));
                linkedHashMap.put(pair, obj);
            }
            return ((Boolean) obj).booleanValue();
        }
    }

    public final void onSensorPrivacyChanged(int i, boolean z) {}

    public final void onSensorPrivacyChanged(
            final SensorPrivacyManager.OnSensorPrivacyChangedListener.SensorPrivacyChangedParams
                    params) {
        Intrinsics.checkNotNullParameter(params, "params");
        synchronized (this.lock) {
            if (!Intrinsics.areEqual(
                    this.cache.put(
                            new Pair(
                                    Integer.valueOf(params.getToggleType()),
                                    Integer.valueOf(params.getSensor())),
                            Boolean.valueOf(params.isEnabled())),
                    Boolean.valueOf(params.isEnabled()))) {
                Set<Pair> set =
                        (Set)
                                ((LinkedHashMap) this.callbacks)
                                        .get(
                                                new Pair(
                                                        Integer.valueOf(params.getToggleType()),
                                                        Integer.valueOf(params.getSensor())));
                if (set != null) {
                    for (final Pair pair : set) {
                        ((Executor) pair.getSecond())
                                .execute(
                                        new Runnable() { // from class:
                                                         // com.android.settings.utils.SensorPrivacyManagerHelper$onSensorPrivacyChanged$1$1$1
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                ((SensorToggleController) Pair.this.getFirst())
                                                        .onSensorPrivacyChanged(
                                                                params.getToggleType(),
                                                                params.getSensor(),
                                                                params.isEnabled());
                                            }
                                        });
                    }
                }
            }
        }
    }
}
