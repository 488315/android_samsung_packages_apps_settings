package com.android.settings.core.instrumentation;

import android.content.Context;
import android.metrics.LogMaker;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.Pair;

import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.core.instrumentation.LogWriter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsEventLogWriter implements LogWriter {
    public static boolean shouldDisableGenericEventLogging() {
        return !DeviceConfig.getBoolean("settings_ui", "event_logging_enabled", true);
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void action(int i, String str) {
        if (shouldDisableGenericEventLogging()) {
            return;
        }
        MetricsLogger.action(new LogMaker(i).setType(4).setPackageName(str));
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void changed(int i, int i2, String str) {
        if (shouldDisableGenericEventLogging()) {
            return;
        }
        LogMaker type = new LogMaker(853).setType(4);
        if (i != 0) {
            type.addTaggedData(833, Integer.valueOf(i));
        }
        if (!TextUtils.isEmpty(str)) {
            type.addTaggedData(854, str);
            type.addTaggedData(1089, Integer.valueOf(i2));
        }
        MetricsLogger.action(type);
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void clicked(int i, String str) {
        if (shouldDisableGenericEventLogging()) {
            return;
        }
        LogMaker type = new LogMaker(830).setType(4);
        if (i != 0) {
            type.addTaggedData(833, Integer.valueOf(i));
        }
        if (!TextUtils.isEmpty(str)) {
            type.addTaggedData(854, str);
        }
        MetricsLogger.action(type);
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void hidden(int i, int i2) {
        if (shouldDisableGenericEventLogging()) {
            return;
        }
        MetricsLogger.action(new LogMaker(i).setType(2).addTaggedData(1089, Integer.valueOf(i2)));
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void visible(int i, int i2, int i3) {
        if (shouldDisableGenericEventLogging()) {
            return;
        }
        MetricsLogger.action(
                new LogMaker(i2)
                        .setType(1)
                        .addTaggedData(833, Integer.valueOf(i))
                        .addTaggedData(1089, Integer.valueOf(i3)));
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void action(Context context, int i, int i2) {
        if (shouldDisableGenericEventLogging()) {
            return;
        }
        MetricsLogger.action(context, i, i2);
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void action(Context context, int i, boolean z) {
        if (shouldDisableGenericEventLogging()) {
            return;
        }
        MetricsLogger.action(context, i, z);
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void action(int i, Pair... pairArr) {
        LogMaker type = new LogMaker(i).setType(4);
        for (Pair pair : pairArr) {
            type.addTaggedData(((Integer) pair.first).intValue(), pair.second);
        }
        MetricsLogger.action(type);
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void action(int i, int i2, int i3, int i4, String str) {
        LogMaker type = new LogMaker(i2).setType(4);
        if (i != 0) {
            type.addTaggedData(833, Integer.valueOf(i3));
        }
        if (!TextUtils.isEmpty(str)) {
            type.addTaggedData(854, str);
            type.addTaggedData(1089, Integer.valueOf(i4));
        }
        MetricsLogger.action(type);
    }
}
