package com.android.settings.core.instrumentation;

import android.content.Context;
import android.util.Pair;

import com.android.settingslib.core.instrumentation.LogWriter;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class StatsLogWriter implements LogWriter {
    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void action(int i, Pair... pairArr) {
        action(0, i, 0, 0, null);
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void changed(int i, int i2, String str) {
        SettingsStatsLog.write(
                i, 853, 0, i2, ElapsedTimeUtils.getElapsedTime(System.currentTimeMillis()), str);
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void clicked(int i, String str) {
        SettingsStatsLog.write(
                i, 830, 0, 0L, ElapsedTimeUtils.getElapsedTime(System.currentTimeMillis()), str);
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void hidden(int i, int i2) {
        SettingsStatsLog.write(
                0,
                2,
                i,
                i2,
                ElapsedTimeUtils.getElapsedTime(System.currentTimeMillis()),
                ApnSettings.MVNO_NONE);
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void visible(int i, int i2, int i3) {
        SettingsStatsLog.write(
                i,
                1,
                i2,
                i3,
                ElapsedTimeUtils.getElapsedTime(System.currentTimeMillis()),
                ApnSettings.MVNO_NONE);
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void action(Context context, int i, int i2) {
        action(0, i, 0, i2, null);
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void action(Context context, int i, boolean z) {
        action(0, i, 0, z ? 1 : 0, null);
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void action(int i, String str) {
        action(0, i, 0, 1, str);
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void action(int i, int i2, int i3, int i4, String str) {
        SettingsStatsLog.write(
                i, i2, i3, i4, ElapsedTimeUtils.getElapsedTime(System.currentTimeMillis()), str);
    }
}
