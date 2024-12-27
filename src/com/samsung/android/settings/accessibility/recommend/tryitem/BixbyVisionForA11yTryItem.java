package com.samsung.android.settings.accessibility.recommend.tryitem;

import android.content.Context;
import android.content.Intent;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BixbyVisionForA11yTryItem extends AbstractTryItem {
    public final BasePreferenceController controller;

    public BixbyVisionForA11yTryItem(Context context) {
        super(context);
        this.controller =
                SecAccessibilityUtils.getPreferenceController(
                        context,
                        "dummy",
                        "com.samsung.android.settings.accessibility.vision.controllers.BixbyVisionAccessibilityPreferenceController");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem,
              // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Intent getLaunchIntent() {
        return new Intent("samsung.intentfilter.visionintelligence.accessibility")
                .addFlags(335544320)
                .putExtra("SHOW_START_BIXBYVISION", true);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Set getMappedUsingFunctionKeySet() {
        return Set.of("magnification_preference_screen");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final CharSequence getTitle() {
        return this.mContext.getText(R.string.bixby_vision_for_accessibility_title);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final boolean isAvailable() {
        BasePreferenceController basePreferenceController = this.controller;
        if (basePreferenceController == null) {
            return false;
        }
        int availabilityStatus = basePreferenceController.getAvailabilityStatus();
        return availabilityStatus == 0 || availabilityStatus == 1;
    }
}
