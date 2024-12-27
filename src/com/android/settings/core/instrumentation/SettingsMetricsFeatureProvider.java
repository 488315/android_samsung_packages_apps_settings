package com.android.settings.core.instrumentation;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.android.settingslib.core.instrumentation.LogWriter;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SettingsMetricsFeatureProvider extends MetricsFeatureProvider {
    public SettingsMetricsFeatureProvider() {
        this.mLoggerWriters = new ArrayList();
        installLogWriters();
    }

    @Override // com.android.settingslib.core.instrumentation.MetricsFeatureProvider
    public final void action(Context context, int i, Pair... pairArr) {
        Log.w(
                "SettingsMetricsFeature",
                "action(Pair<Integer, Object>... taggedData) is deprecated, Use action(int, int,"
                    + " int, String, int) instead.");
        Iterator it = ((ArrayList) this.mLoggerWriters).iterator();
        while (it.hasNext()) {
            ((LogWriter) it.next()).action(i, pairArr);
        }
    }

    public void installLogWriters() {
        this.mLoggerWriters.add(new StatsLogWriter());
        this.mLoggerWriters.add(new SettingsEventLogWriter());
    }
}
