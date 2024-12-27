package com.samsung.android.settings.accessibility.recommend.tryitem;

import android.content.Context;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BoldFontTryItem extends AbstractTryItem {
    public final BasePreferenceController controller;

    public BoldFontTryItem(Context context) {
        super(context);
        this.controller =
                SecAccessibilityUtils.getPreferenceController(
                        context,
                        "dummy",
                        "com.samsung.android.settings.display.controller.SecFontSizeAndStylePreferenceController");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem
    public final String getFragmentClassName() {
        return "com.samsung.android.settings.display.SecFontSizePreferenceFragment";
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
        return this.mContext.getText(R.string.sec_bold_font_title);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final boolean isAvailable() {
        BasePreferenceController basePreferenceController = this.controller;
        if (basePreferenceController == null) {
            return false;
        }
        int availabilityStatus = basePreferenceController.getAvailabilityStatus();
        return (availabilityStatus == 0 || availabilityStatus == 1)
                && !KnoxUtils.isApplicationRestricted(this.mContext, "sec_font_size")
                && Settings.Global.getInt(this.mContext.getContentResolver(), "bold_text", 0) == 0;
    }
}
