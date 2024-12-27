package com.samsung.android.settings.accessibility.recommend.tryitem;

import android.content.Intent;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.settings.accessibility.SIPUtils;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SpeakKeyboardInputAloudTryItem extends AbstractTryItem {
    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem,
              // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Intent getLaunchIntent() {
        return new Intent(
                        "com.samsung.android.honeyboard.intent.action.SPEAK_KEYBOARD_INPUT_ALOUD_SETTING")
                .addFlags(335544320);
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
        return this.mContext.getText(R.string.speak_keyboard_input_aloud);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final boolean isAvailable() {
        return (SIPUtils.getCurrentInputMethodType(this.mContext) != 1
                        || Utils.isTalkBackEnabled(this.mContext)
                        || SIPUtils.getIntFromSIPProvider(
                                        this.mContext,
                                        "speak_keyboard_input_aloud_on",
                                        "speak_keyboard_input_aloud_on",
                                        -1)
                                == 1)
                ? false
                : true;
    }
}
