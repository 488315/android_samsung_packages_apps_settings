package com.samsung.android.settings.accessibility;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SIPUtils {
    public static final ComponentName HONEYBOARD_HIGH_CONTRAST_KEYBOARD_SETTING_ACTIVITY =
            ComponentName.createRelative(
                    "com.samsung.android.honeyboard",
                    ".settings.styleandlayout.highcontrast.HighContrastThemeSettings");
    public static final Uri HONEYBOARD_KEYBOARD_SETTINGS_PROVIDER =
            Uri.parse("content://com.samsung.android.honeyboard.provider.KeyboardSettingsProvider");

    public static int getCurrentInputMethodType(Context context) {
        ComponentName unflattenFromString;
        String string =
                Settings.Secure.getString(context.getContentResolver(), "default_input_method");
        DialogFragment$$ExternalSyntheticOutline0.m("defaultInputMethod: ", string, "SIPUtils");
        if (string == null
                || (unflattenFromString = ComponentName.unflattenFromString(string)) == null) {
            return -1;
        }
        return unflattenFromString.getPackageName().equals("com.samsung.android.honeyboard")
                ? 1
                : 0;
    }

    public static int getIntFromSIPProvider(Context context, String str, String str2, int i) {
        String valueFromSIPProvider = getValueFromSIPProvider(context, str, str2);
        if (valueFromSIPProvider == null) {
            return i;
        }
        try {
            return Integer.parseInt(valueFromSIPProvider);
        } catch (NumberFormatException e) {
            Log.w("SIPUtils", "getIntFromSIPProvider - value is not int type", e);
            return i;
        }
    }

    public static String getValueFromSIPProvider(Context context, String str, String str2) {
        int columnIndex;
        try {
            Cursor query =
                    context.getContentResolver()
                            .query(
                                    HONEYBOARD_KEYBOARD_SETTINGS_PROVIDER,
                                    null,
                                    null,
                                    new String[] {str},
                                    null);
            if (query != null) {
                while (query.moveToNext()) {
                    try {
                        int columnIndex2 = query.getColumnIndex("NAME");
                        if (columnIndex2 != -1
                                && str2.equals(query.getString(columnIndex2))
                                && (columnIndex = query.getColumnIndex("VALUE")) != -1) {
                            String string = query.getString(columnIndex);
                            query.close();
                            return string;
                        }
                    } finally {
                    }
                }
            }
            if (query == null) {
                return null;
            }
            query.close();
            return null;
        } catch (Exception unused) {
            Log.e("SIPUtils", "getValueFromSIPProvider is failed.");
            return null;
        }
    }

    public static int putValueToSIPProvider(Context context, String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(str, str2);
        try {
            return context.getContentResolver()
                    .update(HONEYBOARD_KEYBOARD_SETTINGS_PROVIDER, contentValues, null, null);
        } catch (Exception e) {
            Log.e(
                    "SIPUtils",
                    "putContentValuesToSIPProvider - updating SIP Provider is failed.",
                    e);
            return 0;
        }
    }
}
