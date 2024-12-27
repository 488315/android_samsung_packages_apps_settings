package com.android.settingslib.core.instrumentation;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Pair;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class MetricsFeatureProvider {
    public List mLoggerWriters;

    public static int getAttribution(Activity activity) {
        Intent intent;
        if (activity == null || (intent = activity.getIntent()) == null) {
            return 0;
        }
        return intent.getIntExtra(":settings:source_metrics", 0);
    }

    public static int getMetricsCategory(Object obj) {
        if (obj instanceof Instrumentable) {
            return ((Instrumentable) obj).getMetricsCategory();
        }
        return 0;
    }

    public void action(int i, String str, Map map) {}

    public abstract void action(Context context, int i, Pair... pairArr);

    public final void changed(int i, int i2, String str) {
        Iterator it = ((ArrayList) this.mLoggerWriters).iterator();
        while (it.hasNext()) {
            ((LogWriter) it.next()).changed(i, i2, str);
        }
    }

    public final void clicked(int i, String str) {
        Iterator it = ((ArrayList) this.mLoggerWriters).iterator();
        while (it.hasNext()) {
            ((LogWriter) it.next()).clicked(i, str);
        }
    }

    public final void logClickedPreference(Preference preference, int i) {
        if (preference == null
                || logSettingsTileClick(i, preference.getKey())
                || logStartedIntent(i, preference.getIntent())) {
            return;
        }
        logSettingsTileClick(i, preference.getFragment());
    }

    public final boolean logSettingsTileClick(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        clicked(i, str);
        return true;
    }

    public final boolean logSettingsTileClickWithProfile(int i, String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        StringBuilder m =
                EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str);
        m.append(z ? "/work" : "/personal");
        clicked(i, m.toString());
        return true;
    }

    public final boolean logStartedIntent(int i, Intent intent) {
        if (intent == null) {
            return false;
        }
        ComponentName component = intent.getComponent();
        return logSettingsTileClick(
                i, component != null ? component.flattenToString() : intent.getAction());
    }

    public void visible(Context context, int i, int i2, int i3) {
        Iterator it = ((ArrayList) this.mLoggerWriters).iterator();
        while (it.hasNext()) {
            ((LogWriter) it.next()).visible(i, i2, i3);
        }
    }

    public final void action(Context context, int i, String str) {
        Iterator it = ((ArrayList) this.mLoggerWriters).iterator();
        while (it.hasNext()) {
            ((LogWriter) it.next()).action(i, str);
        }
    }

    public final void action(int i, int i2, int i3, int i4, String str) {
        Iterator it = ((ArrayList) this.mLoggerWriters).iterator();
        while (it.hasNext()) {
            ((LogWriter) it.next()).action(i, i2, i3, i4, str);
        }
    }

    public final void action(Context context, int i, int i2) {
        Iterator it = ((ArrayList) this.mLoggerWriters).iterator();
        while (it.hasNext()) {
            ((LogWriter) it.next()).action(context, i, i2);
        }
    }

    public final void action(Context context, int i, boolean z) {
        Iterator it = ((ArrayList) this.mLoggerWriters).iterator();
        while (it.hasNext()) {
            ((LogWriter) it.next()).action(context, i, z);
        }
    }

    public void actionBackground(int i, String str, Map map) {}
}
