package com.samsung.android.settings.accessibility.vision.displaymode;

import android.content.Context;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class HighContrastDisplayMode extends DisplayModeType {
    public Context mContext;

    @Override // com.samsung.android.settings.accessibility.vision.displaymode.DisplayModeType
    public final String changeDisplayModeToast(boolean z) {
        if (z) {
            Context context = this.mContext;
            return context.getString(
                    R.string.display_mode_applied, context.getString(R.string.high_contrast));
        }
        Context context2 = this.mContext;
        return context2.getString(
                R.string.display_mode_already_applied, context2.getString(R.string.high_contrast));
    }

    @Override // com.samsung.android.settings.accessibility.vision.displaymode.DisplayModeType
    public final boolean doChangeDisplayMode(boolean z) {
        return (z
                        ? DisplayModeType.setColorInversionStatus(this.mContext, 1)
                        : DisplayModeType.setDarkModeStatus(this.mContext, 2))
                | DisplayModeType.setHightContrastFontStatus(this.mContext, 1)
                | DisplayModeType.setHighContrastKeyboard(this.mContext, true)
                | DisplayModeType.setRemoveAnimationStatus(this.mContext, 1)
                | DisplayModeType.setReduceTransparency(this.mContext, 1);
    }
}
