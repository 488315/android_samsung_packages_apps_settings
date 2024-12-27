package com.samsung.android.settings.accessibility.recommend.tryitem;

import android.app.ActivityManager;
import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.settings.accessibility.AccessibilityRune;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RemoveAnimationsTryItem extends AbstractTryItem {
    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem
    public final String getFragmentClassName() {
        return "com.samsung.android.settings.accessibility.vision.VisibilityEnhancementsFragment";
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Set getMappedUsingFunctionKeySet() {
        return Set.of("magnification_preference_screen");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final CharSequence getTitle() {
        return AccessibilityRune.isAtLeastOneUI_6_1()
                ? this.mContext.getText(R.string.reduce_animations_title)
                : this.mContext.getText(R.string.accessibility_disable_animations);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final boolean isAvailable() {
        return ActivityManager.getCurrentUser() == 0
                && Settings.Global.getInt(
                                this.mContext.getContentResolver(), "remove_animations", 0)
                        == 0;
    }
}
