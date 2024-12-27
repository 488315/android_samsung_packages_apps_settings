package com.samsung.android.settings.accessibility.recommend.tryitem;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.AccessibilityRune;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class PointerSizeColorTryItem extends AbstractTryItem {
    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem
    public final String getFragmentClassName() {
        return "com.samsung.android.settings.inputmethod.MousePointerSettingsFragment";
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Set getMappedUsingFunctionKeySet() {
        return Set.of("auto_action_preference_screen");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final CharSequence getTitle() {
        return this.mContext.getText(R.string.mouse_pointer_size_and_color_title);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final boolean isAvailable() {
        return !AccessibilityRune.getFloatingFeatureBooleanValue(
                "SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05");
    }
}
