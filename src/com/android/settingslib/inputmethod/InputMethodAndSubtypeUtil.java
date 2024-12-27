package com.android.settingslib.inputmethod;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.icu.text.ListFormatter;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodSubtype;

import androidx.preference.Preference;

import com.android.internal.app.LocaleHelper;
import com.android.internal.app.LocaleStore;
import com.android.settingslib.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class InputMethodAndSubtypeUtil {
    public static final TextUtils.SimpleStringSplitter sStringInputMethodSplitter =
            new TextUtils.SimpleStringSplitter(':');
    public static final TextUtils.SimpleStringSplitter sStringInputMethodSubtypeSplitter =
            new TextUtils.SimpleStringSplitter(';');

    public static Locale getDisplayLocale(Context context) {
        if (context == null) {
            return Locale.getDefault();
        }
        if (context.getResources() == null) {
            return Locale.getDefault();
        }
        Configuration configuration = context.getResources().getConfiguration();
        if (configuration == null) {
            return Locale.getDefault();
        }
        Locale locale = configuration.getLocales().get(0);
        return locale == null ? Locale.getDefault() : locale;
    }

    public static String getSubtypeLocaleNameListAsSentence(
            List list, Context context, InputMethodInfo inputMethodInfo) {
        String str;
        if (list.isEmpty()) {
            return ApnSettings.MVNO_NONE;
        }
        Locale displayLocale = getDisplayLocale(context);
        int size = list.size();
        CharSequence[] charSequenceArr = new CharSequence[size];
        String str2 = Utils.mCountryCode;
        if (str2 == null || str2.isEmpty()) {
            str = SystemProperties.get("ro.csc.countryiso_code");
            Utils.mCountryCode = str;
        } else {
            str = Utils.mCountryCode;
        }
        int i = 0;
        if ("CN".equalsIgnoreCase(str)) {
            while (i < size) {
                String replace = ((InputMethodSubtype) list.get(i)).getLocale().replace("_", "-");
                if (TextUtils.isEmpty(replace)) {
                    charSequenceArr[i] = ApnSettings.MVNO_NONE;
                } else {
                    if ("zh-CN".equalsIgnoreCase(replace)) {
                        replace = "zh-Hans-CN";
                    } else if ("zh-HK".equalsIgnoreCase(replace)) {
                        replace = "zh-Hant-HK";
                    } else if ("zh-TW".equalsIgnoreCase(replace)) {
                        replace = "zh-Hant-TW";
                    }
                    charSequenceArr[i] =
                            LocaleStore.getLocaleInfo(Locale.forLanguageTag(replace))
                                    .getSecFullNameNative();
                }
                i++;
            }
        } else {
            while (i < size) {
                charSequenceArr[i] =
                        ((InputMethodSubtype) list.get(i))
                                .getDisplayName(
                                        context,
                                        inputMethodInfo.getPackageName(),
                                        inputMethodInfo.getServiceInfo().applicationInfo);
                i++;
            }
        }
        return LocaleHelper.toSentenceCase(
                ListFormatter.getInstance(displayLocale).format(charSequenceArr), displayLocale);
    }

    public static boolean isValidNonAuxAsciiCapableIme(InputMethodInfo inputMethodInfo) {
        if (inputMethodInfo.isAuxiliaryIme()) {
            return false;
        }
        int subtypeCount = inputMethodInfo.getSubtypeCount();
        for (int i = 0; i < subtypeCount; i++) {
            InputMethodSubtype subtypeAt = inputMethodInfo.getSubtypeAt(i);
            if ("keyboard".equalsIgnoreCase(subtypeAt.getMode()) && subtypeAt.isAsciiCapable()) {
                return true;
            }
        }
        return false;
    }

    public static void removeUnnecessaryNonPersistentPreference(Preference preference) {
        SharedPreferences sharedPreferences;
        String key = preference.getKey();
        if (preference.isPersistent()
                || key == null
                || (sharedPreferences = preference.getSharedPreferences()) == null
                || !sharedPreferences.contains(key)) {
            return;
        }
        sharedPreferences.edit().remove(key).apply();
    }
}
