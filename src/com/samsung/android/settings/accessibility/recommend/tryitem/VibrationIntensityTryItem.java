package com.samsung.android.settings.accessibility.recommend.tryitem;

import com.android.settings.R;

import com.samsung.android.settings.asbase.utils.VibUtils;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class VibrationIntensityTryItem extends AbstractTryItem {
    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.AbstractTryItem
    public final String getFragmentClassName() {
        return "com.samsung.android.settings.asbase.vibration.SecVibrationIntensitySettings";
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final Set getMappedUsingFunctionKeySet() {
        return Set.of(
                "ignore_repeat_key", "tap_duration_key", "select_long_press_timeout_preference");
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final CharSequence getTitle() {
        return this.mContext.getText(R.string.sec_vibration_intensity);
    }

    @Override // com.samsung.android.settings.accessibility.recommend.tryitem.TryItem
    public final boolean isAvailable() {
        return VibUtils.hasVibrator(this.mContext) && VibUtils.isEnableIntensity(this.mContext);
    }
}
