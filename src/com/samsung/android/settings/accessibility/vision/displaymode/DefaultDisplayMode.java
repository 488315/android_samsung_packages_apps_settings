package com.samsung.android.settings.accessibility.vision.displaymode;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.accessibility.AccessibilityConstant;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DefaultDisplayMode extends DisplayModeType {
    public Context mContext;

    @Override // com.samsung.android.settings.accessibility.vision.displaymode.DisplayModeType
    public final String changeDisplayModeToast(boolean z) {
        if (z) {
            Context context = this.mContext;
            return context.getString(
                    R.string.display_mode_applied,
                    context.getString(R.string.accessibility_timeout_default));
        }
        Context context2 = this.mContext;
        return context2.getString(
                R.string.display_mode_already_applied,
                context2.getString(R.string.accessibility_timeout_default));
    }

    @Override // com.samsung.android.settings.accessibility.vision.displaymode.DisplayModeType
    public final boolean doChangeDisplayMode(boolean z) {
        boolean z2;
        boolean boldFontStatus =
                DisplayModeType.setBoldFontStatus(this.mContext, 0)
                        | DisplayModeType.setHighLightButtonStatus(this.mContext, 0)
                        | DisplayModeType.setHightContrastFontStatus(this.mContext, 0)
                        | DisplayModeType.setHighContrastKeyboard(this.mContext, false)
                        | DisplayModeType.setRemoveAnimationStatus(this.mContext, 0)
                        | DisplayModeType.setReduceTransparency(this.mContext, 0)
                        | DisplayModeType.setScreenZoomSize(this.mContext, false);
        Context context = this.mContext;
        Uri uri = AccessibilityConstant.URI_ACCESSIBILITY_PROVIDER;
        int i =
                SemFloatingFeature.getInstance()
                        .getInt("SEC_FLOATING_FEATURE_SETTINGS_CONFIG_DEFAULT_FONT_SIZE");
        if (Settings.Global.getInt(context.getContentResolver(), "font_size", i) != i) {
            DisplayModeType.setFontSize(context, i);
            z2 = true;
        } else {
            z2 = false;
        }
        return (z
                        ? DisplayModeType.setColorInversionStatus(this.mContext, 0)
                        : DisplayModeType.setDarkModeStatus(this.mContext, 1))
                | boldFontStatus
                | z2;
    }
}
