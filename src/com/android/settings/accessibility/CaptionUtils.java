package com.android.settings.accessibility;

import android.content.Context;
import android.graphics.Color;
import android.view.accessibility.CaptioningManager;

import com.android.settings.R;

import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.text.NumberFormat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CaptionUtils {
    public static String[] localizedOpacityValues(Context context) {
        String[] stringArray =
                context.getResources().getStringArray(R.array.captioning_opacity_selector_titles);
        String[] strArr = new String[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            strArr[i] =
                    NumberFormat.getPercentInstance()
                            .format(
                                    Integer.parseInt(
                                                    stringArray[i].replaceAll(
                                                            "[^0-9]", ApnSettings.MVNO_NONE))
                                            / 100.0d);
        }
        return strArr;
    }

    public static int mergeColorOpacity(int i, int i2) {
        return !CaptioningManager.CaptionStyle.hasColor(i)
                ? 16776960 | Color.alpha(i2)
                : i == 0 ? Color.alpha(i2) : (i & 16777215) | (i2 & EmergencyPhoneWidget.BG_COLOR);
    }

    public static int parseColor(int i) {
        if (!CaptioningManager.CaptionStyle.hasColor(i)) {
            return 16777215;
        }
        if ((i >>> 24) == 0) {
            return 0;
        }
        return i | EmergencyPhoneWidget.BG_COLOR;
    }

    public static int parseOpacity(int i) {
        return ((CaptioningManager.CaptionStyle.hasColor(i) && (i >>> 24) != 0)
                        ? i & EmergencyPhoneWidget.BG_COLOR
                        : (i & 255) << 24)
                | 16777215;
    }
}
