package com.samsung.android.settings.accessibility.recommend.tryitem;

import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.settings.display.SecDisplayUtils;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DarkModeTryItem extends AbstractTryItem {
    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem
    public final String getFragmentClassName() {
        return "com.android.settings.DisplaySettings";
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Set getMappedUsingFunctionKeySet() {
        return Set.of(
                "toggle_high_text_contrast_preference",
                "high_keyboard_contrast_preference_screen",
                "toggle_inversion_preference");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final CharSequence getTitle() {
        return this.mContext.getText(R.string.dark_mode_title);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final boolean isAvailable() {
        return SecDisplayUtils.canChangeNightMode(this.mContext)
                && Settings.System.getInt(
                                this.mContext.getContentResolver(), "display_night_theme", 0)
                        == 0;
    }
}
