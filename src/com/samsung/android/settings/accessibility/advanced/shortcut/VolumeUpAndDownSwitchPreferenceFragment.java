package com.samsung.android.settings.accessibility.advanced.shortcut;

import com.android.settings.R;

import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class VolumeUpAndDownSwitchPreferenceFragment extends ShortcutSwitchPreferenceFragment {
    @Override // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceFragment
    public final String getDbKey() {
        return "accessibility_shortcut_target_service";
    }

    @Override // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceFragment
    public final int getDescriptionResId() {
        return R.string.accessibility_shortcut_volume_up_and_down_summary;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final String getLogTag() {
        return "VolumeUpAndDownSwitchPreferenceFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.CMC_E911_NOT_ALLOWED_ON_SD;
    }

    @Override // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceFragment
    public final int getShortcutType() {
        return 2;
    }

    @Override // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceFragment
    public final int getTitleResId() {
        return R.string.volume_up_down_shortcut_title;
    }
}
