package com.samsung.android.settings.inputmethod;

import android.net.Uri;

import com.android.settings.R;

import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TouchPadGestureSettings extends SecDynamicFragment {
    public TouchPadGestureSettings() {
        Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_touchpad_gesture_settings;
    }
}
