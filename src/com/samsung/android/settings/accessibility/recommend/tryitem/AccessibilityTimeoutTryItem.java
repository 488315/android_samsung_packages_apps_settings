package com.samsung.android.settings.accessibility.recommend.tryitem;

import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AccessibilityTimeoutTryItem extends AbstractTryItem {
    public static final String KEY_UNIVERSAL_SWITCH =
            AccessibilityConstant.COMPONENT_NAME_UNIVERSAL_SWITCH.flattenToString();

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem
    public final String getFragmentClassName() {
        return "com.samsung.android.settings.accessibility.advanced.SecAccessibilityControlTimeoutPreferenceFragment";
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Set getMappedUsingFunctionKeySet() {
        return Set.of(
                "talkback_preference",
                KEY_UNIVERSAL_SWITCH,
                "voice_access_preference",
                "auto_action_preference_screen",
                "magnification_preference_screen");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final CharSequence getTitle() {
        return this.mContext.getText(R.string.accessibility_control_timeout_preference_title);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final boolean isAvailable() {
        return !KnoxUtils.isApplicationRestricted(this.mContext, "accessibility_advanced_settings")
                && Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "accessibility_interactive_ui_timeout_ms",
                                0)
                        == 0;
    }
}
