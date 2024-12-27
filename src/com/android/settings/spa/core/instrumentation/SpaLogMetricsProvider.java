package com.android.settings.spa.core.instrumentation;

import android.os.Bundle;
import android.util.Log;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.LogWriter;
import com.android.settingslib.spa.framework.common.LogCategory;
import com.android.settingslib.spa.framework.common.LogEvent;
import com.android.settingslib.spa.framework.common.SpaLogger;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SpaLogMetricsProvider implements SpaLogger {
    public static final SpaLogMetricsProvider INSTANCE = new SpaLogMetricsProvider();

    @Override // com.android.settingslib.spa.framework.common.SpaLogger
    public final void event(String id, LogEvent event, LogCategory logCategory, Bundle extraData) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(event, "event");
        Intrinsics.checkNotNullParameter(extraData, "extraData");
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SettingsMetricsFeatureProvider metricsFeatureProvider =
                featureFactoryImpl.getMetricsFeatureProvider();
        int i = extraData.getInt("metricsCategory");
        Log.d("SpaLogMetricsProvider", event + " page " + i);
        if (i == 0) {
            return;
        }
        int ordinal = event.ordinal();
        if (ordinal == 0) {
            metricsFeatureProvider.visible(null, 0, i, 0);
        } else {
            if (ordinal != 1) {
                return;
            }
            Iterator it = ((ArrayList) metricsFeatureProvider.mLoggerWriters).iterator();
            while (it.hasNext()) {
                ((LogWriter) it.next()).hidden(i, 0);
            }
        }
    }
}
