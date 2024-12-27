package com.samsung.android.settings.accessibility.recommend.tryitem;

import android.content.Intent;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.SIPUtils;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class HighContrastKeyboardTryItem extends AbstractTryItem {
    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem,
              // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Intent getLaunchIntent() {
        return new Intent()
                .setComponent(SIPUtils.HONEYBOARD_HIGH_CONTRAST_KEYBOARD_SETTING_ACTIVITY);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Set getMappedUsingFunctionKeySet() {
        return Set.of("speak_keyboard_input_aloud");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final CharSequence getTitle() {
        return this.mContext.getText(
                R.string.accessibility_toggle_high_keyboard_contrast_preference_title);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final boolean isAvailable() {
        return SIPUtils.getCurrentInputMethodType(this.mContext) == 1
                && SIPUtils.getIntFromSIPProvider(
                                this.mContext,
                                "high_contrast_keyboard",
                                "high_contrast_keyboard",
                                0)
                        != 1;
    }
}
