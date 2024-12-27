package com.samsung.android.settings.accessibility.base.logging;

import android.content.Context;
import android.util.Pair;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.android.settingslib.core.instrumentation.LogWriter;

import com.samsung.android.settings.logging.SALogging;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class A11ySettingsLogWriter implements LogWriter {
    public static String refineCategory(int i) {
        if (i < 1000) {
            return null;
        }
        return SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "A11Y");
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void action(Context context, int i, int i2) {}

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void clicked(int i, String str) {
        String refineCategory = refineCategory(i);
        if (refineCategory == null || str == null) {
            return;
        }
        if (str.startsWith("A11Y")) {
            SALogging.insertSALog(refineCategory, str);
            return;
        }
        Map map = (Map) A11yLoggingConstant.PREF_CLICK_EVENT_ID_MAP.get(Integer.valueOf(i));
        String str2 = map == null ? null : (String) map.get(str);
        if (str2 != null) {
            SALogging.insertSALog(refineCategory, str2);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void visible(int i, int i2, int i3) {
        String refineCategory = refineCategory(i2);
        if (refineCategory != null) {
            SALogging.insertSALog(refineCategory);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void action(Context context, int i, boolean z) {}

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void action(int i, Pair... pairArr) {}

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void action(int i, String str) {}

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void action(int i, int i2, int i3, int i4, String str) {}

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void hidden(int i, int i2) {}

    @Override // com.android.settingslib.core.instrumentation.LogWriter
    public final void changed(int i, int i2, String str) {}
}
