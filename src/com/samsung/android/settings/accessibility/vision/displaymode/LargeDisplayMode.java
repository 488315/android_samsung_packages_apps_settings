package com.samsung.android.settings.accessibility.vision.displaymode;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;

import com.android.settings.R;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.accessibility.AccessibilityConstant;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LargeDisplayMode extends DisplayModeType {
    public Context mContext;

    @Override // com.samsung.android.settings.accessibility.vision.displaymode.DisplayModeType
    public final String changeDisplayModeToast(boolean z) {
        if (z) {
            Context context = this.mContext;
            return context.getString(
                    R.string.display_mode_applied, context.getString(R.string.large_display));
        }
        Context context2 = this.mContext;
        return context2.getString(
                R.string.display_mode_already_applied, context2.getString(R.string.large_display));
    }

    @Override // com.samsung.android.settings.accessibility.vision.displaymode.DisplayModeType
    public final boolean doChangeDisplayMode(boolean z) {
        boolean z2 = true;
        boolean boldFontStatus =
                DisplayModeType.setBoldFontStatus(this.mContext, 1)
                        | DisplayModeType.setHighLightButtonStatus(this.mContext, 1)
                        | DisplayModeType.setScreenZoomSize(this.mContext, true);
        Context context = this.mContext;
        Uri uri = AccessibilityConstant.URI_ACCESSIBILITY_PROVIDER;
        int i =
                Settings.Global.getInt(
                        context.getContentResolver(),
                        "font_size",
                        SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_SETTINGS_CONFIG_DEFAULT_FONT_SIZE"));
        if (i < 7) {
            DisplayModeType.setFontSize(context, i + 1);
        } else {
            z2 = false;
        }
        return boldFontStatus | z2;
    }
}
