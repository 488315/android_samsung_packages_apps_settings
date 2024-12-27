package com.google.android.material.radiobutton;

import android.R;
import android.content.res.ColorStateList;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.google.android.material.color.MaterialColors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MaterialRadioButton extends AppCompatRadioButton {
    public static final int[][] ENABLED_CHECKED_STATES = {
        new int[] {R.attr.state_enabled, R.attr.state_checked},
        new int[] {R.attr.state_enabled, -16842912},
        new int[] {-16842910, R.attr.state_checked},
        new int[] {-16842910, -16842912}
    };
    public ColorStateList materialThemeColorsTintList;
    public boolean useMaterialThemeColors;

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.useMaterialThemeColors && getButtonTintList() == null) {
            this.useMaterialThemeColors = true;
            if (this.materialThemeColorsTintList == null) {
                int color =
                        MaterialColors.getColor(
                                this, com.android.settings.R.attr.colorControlActivated);
                int color2 =
                        MaterialColors.getColor(this, com.android.settings.R.attr.colorOnSurface);
                int color3 =
                        MaterialColors.getColor(this, com.android.settings.R.attr.colorSurface);
                this.materialThemeColorsTintList =
                        new ColorStateList(
                                ENABLED_CHECKED_STATES,
                                new int[] {
                                    MaterialColors.layer(color3, color, 1.0f),
                                    MaterialColors.layer(color3, color2, 0.54f),
                                    MaterialColors.layer(color3, color2, 0.38f),
                                    MaterialColors.layer(color3, color2, 0.38f)
                                });
            }
            setButtonTintList(this.materialThemeColorsTintList);
        }
    }
}
