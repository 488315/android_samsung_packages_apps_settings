package com.samsung.android.settings.accessibility.base.logging;

import android.content.Context;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;

import com.samsung.android.settings.logging.SALogging;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class A11ySettingsMetricsFeatureProvider extends SettingsMetricsFeatureProvider {
    public A11ySettingsLogWriter logWriter;

    @Override // com.android.settingslib.core.instrumentation.MetricsFeatureProvider
    public final void action(int i, String str, Map map) {
        this.logWriter.getClass();
        String refineCategory = A11ySettingsLogWriter.refineCategory(i);
        if (refineCategory != null) {
            SALogging.insertSALog(refineCategory, str, map, 0);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.MetricsFeatureProvider
    public final void actionBackground(int i, String str, Map map) {
        this.logWriter.getClass();
        String refineCategory = A11ySettingsLogWriter.refineCategory(i);
        if (refineCategory != null) {
            SALogging.insertSALog(refineCategory, str, map, 1);
        }
    }

    @Override // com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider
    public final void installLogWriters() {
        super.installLogWriters();
        A11ySettingsLogWriter a11ySettingsLogWriter = new A11ySettingsLogWriter();
        this.logWriter = a11ySettingsLogWriter;
        this.mLoggerWriters.add(a11ySettingsLogWriter);
    }

    @Override // com.android.settingslib.core.instrumentation.MetricsFeatureProvider
    public final void visible(Context context, int i, int i2, int i3) {
        this.logWriter.visible(i, i2, i3);
    }
}
