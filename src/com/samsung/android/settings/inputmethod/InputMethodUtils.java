package com.samsung.android.settings.inputmethod;

import android.content.Context;
import android.net.Uri;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class InputMethodUtils {
    public static final Uri HOME_CONTENT_URI =
            Uri.parse("content://com.sec.android.app.launcher.settings");

    public static String getMouseButtonFeatureTitle(Context context, int i) {
        switch (i) {
            case 0:
                return context.getString(R.string.none);
            case 1:
                return context.getString(R.string.open_contextual_menu);
            case 2:
                return context.getString(R.string.show_the_apps_screen);
            case 3:
                return context.getString(R.string.app_defined_function);
            case 4:
                return context.getString(R.string.forward);
            case 5:
                return context.getString(R.string.home_button);
            case 6:
                return context.getString(R.string.recents_button);
            case 7:
                return context.getString(R.string.back_button);
            case 8:
                return context.getString(R.string.view_notifications);
            case 9:
                return context.getString(R.string.open_quick_settings);
            case 10:
            case 11:
            case 12:
            case 13:
                return context.getString(R.string.open_app);
            default:
                return ApnSettings.MVNO_NONE;
        }
    }
}
