package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.Rune$$ExternalSyntheticOutline0;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.HashSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilityButtonSwitchPreferenceFragment extends ShortcutSwitchPreferenceFragment {
    @Override // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceFragment
    public final String getDbKey() {
        return "accessibility_button_targets";
    }

    @Override // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceFragment
    public final int getDescriptionResId() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if (!Rune$$ExternalSyntheticOutline0.m(
                "SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY", "LARGESCREEN")) {
            return R.string.accessibility_shortcut_accessibility_button_or_gesture_summary;
        }
        Context context = getContext();
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        return Utils.isTalkBackEnabled(context)
                ? R.string
                        .accessibility_shortcut_accessibility_button_or_gesture_summary_flip_talkback
                : R.string.accessibility_shortcut_accessibility_button_or_gesture_summary_flip;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AccessibilityButtonSwitchPreferenceFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.MDMN_PULLCALL_BY_SECONDARY;
    }

    @Override // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceFragment
    public final int getShortcutType() {
        return 1;
    }

    @Override // com.samsung.android.settings.accessibility.advanced.shortcut.ShortcutSwitchPreferenceFragment
    public final int getTitleResId() {
        return R.string.accessibility_button_select_actions;
    }
}
