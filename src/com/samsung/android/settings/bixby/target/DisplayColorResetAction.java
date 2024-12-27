package com.samsung.android.settings.bixby.target;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import com.android.settings.R;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.display.SemMdnieManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.settings.display.SecDisplayUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DisplayColorResetAction extends Action {
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSetAction() {
        int i;
        int i2;
        int i3;
        int i4;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        boolean z = Settings.System.getInt(contentResolver, "ead_enabled", 0) != 0;
        int i5 =
                SemFloatingFeature.getInstance()
                        .getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_DEFAULT_SCREEN_MODE");
        boolean z2 =
                (i5 == Settings.System.getInt(contentResolver, "screen_mode_setting", i5)
                                && Settings.System.getInt(
                                                contentResolver, "sec_display_preset_index", 2)
                                        == 2
                                && Settings.System.getInt(
                                                contentResolver, "sec_display_temperature_red", 0)
                                        == 0
                                && Settings.System.getInt(
                                                contentResolver, "sec_display_temperature_blue", 0)
                                        == 0
                                && Settings.System.getInt(
                                                contentResolver, "sec_display_temperature_green", 0)
                                        == 0
                                && (!Rune.supportVividness()
                                        || Settings.System.getInt(
                                                        contentResolver, "vividness_intensity", 0)
                                                == 0))
                        ? false
                        : true;
        boolean z3 = Settings.System.getInt(contentResolver, "blue_light_filter", 0) != 0;
        if (z) {
            Settings.System.putInt(contentResolver, "ead_enabled", 0);
        }
        if (z2) {
            SemMdnieManager semMdnieManager =
                    (SemMdnieManager) this.mContext.getSystemService("mDNIe");
            boolean supportWcgModeOnAmoled = Rune.supportWcgModeOnAmoled();
            boolean supportVividPlusMode = Rune.supportVividPlusMode();
            boolean supportNaturalModeWithoutWcgMode = Rune.supportNaturalModeWithoutWcgMode();
            int i6 =
                    SemFloatingFeature.getInstance()
                            .getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_DEFAULT_SCREEN_MODE");
            Settings.System.putInt(contentResolver, "screen_mode_setting", i6);
            if (i6 == 4) {
                Settings.System.putInt(contentResolver, "screen_mode_automatic_setting", 1);
                i3 = 0;
            } else {
                i3 = 0;
                Settings.System.putInt(contentResolver, "screen_mode_automatic_setting", 0);
            }
            if (supportWcgModeOnAmoled) {
                if (i6 == 3) {
                    if (!supportVividPlusMode) {
                        SecDisplayUtils.setDisplayColor(i3);
                    }
                    semMdnieManager.setScreenMode(i3);
                } else {
                    if (!supportVividPlusMode) {
                        SecDisplayUtils.setDisplayColor(1);
                    }
                    semMdnieManager.setScreenMode(i6);
                }
                i4 = 2;
            } else if (supportNaturalModeWithoutWcgMode) {
                i4 = 2;
                if (i6 == 2) {
                    semMdnieManager.setScreenMode(2);
                } else {
                    semMdnieManager.setScreenMode(i6);
                }
            } else {
                i4 = 2;
                semMdnieManager.setScreenMode(i6);
            }
            Settings.System.putInt(contentResolver, "sec_display_preset_index", i4);
            i = 0;
            Settings.System.putInt(contentResolver, "sec_display_temperature_red", 0);
            Settings.System.putInt(contentResolver, "sec_display_temperature_blue", 0);
            Settings.System.putInt(contentResolver, "sec_display_temperature_green", 0);
            if (Rune.supportVividness()) {
                Settings.System.putInt(contentResolver, "vividness_intensity", 0);
            }
        } else {
            i = 0;
        }
        if (z3) {
            Settings.System.putInt(contentResolver, "blue_light_filter", i);
            Intent intent = new Intent();
            intent.setComponent(
                    new ComponentName(
                            "com.samsung.android.bluelightfilter",
                            "com.samsung.android.bluelightfilter.BlueLightFilterService"));
            intent.putExtra("BLUE_LIGHT_FILTER_SERVICE_TYPE", 25);
            this.mContext.startService(intent);
            Settings.System.putInt(contentResolver, "blue_light_filter_opacity", 5);
            if (Rune.supportBlueLightFilterAdaptiveMode()) {
                Settings.System.putInt(contentResolver, "blue_light_filter_adaptive_mode", 1);
                i2 = 0;
                Settings.System.putInt(contentResolver, "blue_light_filter_scheduled", 0);
            } else {
                i2 = 0;
                Settings.System.putInt(contentResolver, "blue_light_filter_scheduled", 1);
            }
            Settings.System.putInt(contentResolver, "blue_light_filter_type", i2);
            Settings.System.putLong(contentResolver, "blue_light_filter_on_time", 1140L);
            Settings.System.putLong(contentResolver, "blue_light_filter_off_time", 420L);
            if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_NIGHT_DIM")
                    > 0) {
                Settings.System.putInt(contentResolver, "blue_light_filter_night_dim", 0);
            }
        }
        StringBuilder sb = new StringBuilder();
        if (z || z2 || z3) {
            StringBuilder sb2 = new StringBuilder();
            if (z) {
                sb2.append(this.mContext.getString(R.string.sec_ead));
                sb.append("sec_ead");
            }
            if (z2) {
                if (sb2.length() > 0) {
                    sb2.append(", ");
                    sb.append(",");
                }
                sb2.append(this.mContext.getString(R.string.sec_screen_mode_setting));
                sb.append("screen_mode");
            }
            if (z3) {
                if (sb2.length() > 0) {
                    sb2.append(", ");
                    sb.append(",");
                }
                sb2.append(this.mContext.getString(R.string.sec_eye_comfort_title));
                sb.append("blue_light_filter");
            }
            if ("true".equalsIgnoreCase(getValue())) {
                final String string =
                        this.mContext.getString(
                                R.string.sec_display_color_reset_message, sb2.toString());
                new Handler(Looper.getMainLooper())
                        .post(
                                new Runnable() { // from class:
                                                 // com.samsung.android.settings.bixby.target.DisplayColorResetAction.1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        Toast.makeText(
                                                        DisplayColorResetAction.this.mContext,
                                                        string,
                                                        0)
                                                .show();
                                    }
                                });
            }
        }
        Bundle createResult = Action.createResult("success");
        createResult.putString("target", sb.toString());
        return createResult;
    }
}
