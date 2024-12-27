package com.samsung.android.settings.accessibility.base;

import android.text.TextUtils;
import android.util.Log;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class StringBuilderUtils {
    public static String convertDefaultLocaleType(String str) {
        if (TextUtils.isEmpty(str) || !str.contains(".")) {
            return str;
        }
        try {
            return String.format(Locale.getDefault(), "%.1f", Float.valueOf(Float.parseFloat(str)));
        } catch (NumberFormatException unused) {
            return str;
        }
    }

    public static String convertNumberToArabicNumber(String str) {
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        if (!"ar".equals(language) && !"fa".equals(language)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (locale.getLanguage().equals("ar")) {
            while (i < str.length()) {
                if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                    sb.append(str.charAt(i));
                } else {
                    sb.append((char) (str.charAt(i) + 1584));
                }
                i++;
            }
        } else if (locale.getLanguage().equals("fa")) {
            while (i < str.length()) {
                if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                    sb.append(str.charAt(i));
                } else {
                    sb.append((char) (str.charAt(i) + 1728));
                }
                i++;
            }
        }
        return sb.toString();
    }

    public static float convertStringToFloat(String str) {
        char decimalSeparator = DecimalFormatSymbols.getInstance(Locale.US).getDecimalSeparator();
        if (TextUtils.isEmpty(str)) {
            return 0.0f;
        }
        if (str.contains(Character.toString(decimalSeparator))) {
            return parseFloat(str).floatValue();
        }
        try {
            Number parse = NumberFormat.getInstance().parse(str);
            if (parse != null) {
                return parse.floatValue();
            }
            return 0.0f;
        } catch (ParseException e) {
            Log.w("StringBuilderUtils", "convertStringToFloat ParseException occurs.", e);
            return 0.0f;
        }
    }

    public static Float parseFloat(String str) {
        boolean isEmpty = TextUtils.isEmpty(str);
        Float valueOf = Float.valueOf(0.0f);
        if (!isEmpty) {
            try {
                return Float.valueOf(
                        Float.parseFloat(
                                String.format(
                                        Locale.US, "%.1f", Float.valueOf(Float.parseFloat(str)))));
            } catch (NumberFormatException unused) {
            }
        }
        return valueOf;
    }

    public static String convertDefaultLocaleType(float f) {
        return NumberFormat.getInstance(Locale.getDefault()).format(f);
    }
}
