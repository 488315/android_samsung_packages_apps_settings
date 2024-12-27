package com.samsung.android.settings.accessibility.vision.displaymode;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;

import com.samsung.android.settings.accessibility.SIPUtils;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.sec.ims.configuration.DATA;

import java.util.HashSet;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class DisplayModeType {
    public static final String[] TOGGLE_ANIMATION_TARGETS = {
        "window_animation_scale", "transition_animation_scale", "animator_duration_scale"
    };

    public static boolean setBoldFontStatus(Context context, int i) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "setBoldFontStatus:", "DisplayModeType");
        int i2 = i != 0 ? 300 : 0;
        if (Settings.Global.getInt(context.getContentResolver(), "bold_text", 0) == i) {
            return false;
        }
        Settings.Global.putInt(context.getContentResolver(), "bold_text", i);
        Settings.Secure.putInt(context.getContentResolver(), "font_weight_adjustment", i2);
        return true;
    }

    public static boolean setColorInversionStatus(Context context, int i) {
        Log.i("DisplayModeType", "setColorInversionStatus:" + i);
        if (Settings.Secure.getInt(
                        context.getContentResolver(), "accessibility_display_inversion_enabled", 0)
                == i) {
            return false;
        }
        int i2 = i == 1 ? 1 : 0;
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        Settings.Secure.putInt(
                context.getContentResolver(), "accessibility_display_inversion_enabled", i2);
        return true;
    }

    public static boolean setDarkModeStatus(Context context, int i) {
        Log.i("DisplayModeType", "setDarkModeStatus:" + i);
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService("uimode");
        if (uiModeManager.getNightMode() == i) {
            return false;
        }
        uiModeManager.setNightMode(i);
        return true;
    }

    public static void setFontSize(Context context, int i) {
        Log.i("DisplayModeType", "current setFontSize : " + i);
        Settings.Global.putInt(context.getContentResolver(), "font_size", i);
        Intent intent = new Intent("com.samsung.settings.FONT_SIZE_CHANGED");
        intent.addFlags(16777216);
        intent.putExtra("large_font", 0);
        context.sendBroadcast(intent);
        Log.i("DisplayModeType", "getFontScale : " + i);
        String[] stringArray =
                context.getResources().getStringArray(R.array.sec_entryvalues_8_step_font_size);
        if (i >= stringArray.length) {
            i = stringArray.length - 1;
        }
        float parseFloat = Float.parseFloat(stringArray[i]);
        Log.i("DisplayModeType", "writeFontScaleDBAllUser : " + parseFloat);
        List<UserInfo> users = ((UserManager) context.getSystemService("user")).getUsers();
        if (users != null) {
            for (UserInfo userInfo : users) {
                Settings.System.putFloatForUser(
                        context.getContentResolver(), "font_scale", parseFloat, userInfo.id);
                TooltipPopup$$ExternalSyntheticOutline0.m(
                        new StringBuilder("writeFontScaleDBAllUser() user.id : "),
                        userInfo.id,
                        "DisplayModeType");
            }
        }
    }

    public static boolean setHighContrastKeyboard(Context context, boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m("setHighContrastKeyboard:", "DisplayModeType", z);
        if ((SIPUtils.getIntFromSIPProvider(
                                context, "high_contrast_keyboard", "high_contrast_keyboard", 0)
                        == 1)
                != z) {
            return SIPUtils.putValueToSIPProvider(
                            context,
                            "high_contrast_keyboard",
                            z ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN)
                    > 0;
        }
        return false;
    }

    public static boolean setHighLightButtonStatus(Context context, int i) {
        Log.i("DisplayModeType", "setHighLightButtonStatus:" + i);
        if (Settings.Global.getInt(context.getContentResolver(), "show_button_background", 0)
                == i) {
            return false;
        }
        Settings.Global.putInt(context.getContentResolver(), "show_button_background", i);
        SecAccessibilityUtils.setButtonShape(context, false);
        return true;
    }

    public static boolean setHightContrastFontStatus(Context context, int i) {
        Log.i("DisplayModeType", "setHightContrastFontStatus:" + i);
        if (Settings.Secure.getInt(context.getContentResolver(), "high_text_contrast_enabled", 0)
                == i) {
            return false;
        }
        int i2 = i == 1 ? 1 : 0;
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        Settings.Secure.putInt(context.getContentResolver(), "high_text_contrast_enabled", i2);
        return true;
    }

    public static boolean setReduceTransparency(Context context, int i) {
        Log.i("DisplayModeType", "setReduceTransparency:" + i);
        if (Settings.Global.getInt(
                        context.getContentResolver(), "accessibility_reduce_transparency", 0)
                == i) {
            return false;
        }
        Settings.Global.putInt(
                context.getContentResolver(), "accessibility_reduce_transparency", i);
        return true;
    }

    public static boolean setRemoveAnimationStatus(Context context, int i) {
        Log.i("DisplayModeType", "setRemoveAnimationStatus:" + i);
        if (Settings.System.getInt(context.getContentResolver(), "remove_animations", 0) == i) {
            return false;
        }
        Settings.Global.putInt(context.getContentResolver(), "remove_animations", i);
        String str = i == 1 ? "0.0" : "1";
        String[] strArr = TOGGLE_ANIMATION_TARGETS;
        for (int i2 = 0; i2 < 3; i2++) {
            Settings.Global.putString(context.getContentResolver(), strArr[i2], str);
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00f6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean setScreenZoomSize(android.content.Context r9, boolean r10) {
        /*
            Method dump skipped, instructions count: 344
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.accessibility.vision.displaymode.DisplayModeType.setScreenZoomSize(android.content.Context,"
                    + " boolean):boolean");
    }

    public abstract String changeDisplayModeToast(boolean z);

    public abstract boolean doChangeDisplayMode(boolean z);
}
