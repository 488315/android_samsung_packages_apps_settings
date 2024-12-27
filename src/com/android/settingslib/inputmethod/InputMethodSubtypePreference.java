package com.android.settingslib.inputmethod;

import android.content.Context;
import android.text.TextUtils;

import com.android.internal.annotations.VisibleForTesting;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class InputMethodSubtypePreference extends SwitchWithNoTextPreference {
    public final boolean mIsSystemLanguage;
    public final boolean mIsSystemLocale;

    @VisibleForTesting
    public InputMethodSubtypePreference(
            Context context, String str, CharSequence charSequence, Locale locale, Locale locale2) {
        super(context);
        setPersistent();
        setKey(str);
        setTitle(charSequence);
        if (locale == null) {
            this.mIsSystemLocale = false;
            this.mIsSystemLanguage = false;
        } else {
            boolean equals = locale.equals(locale2);
            this.mIsSystemLocale = equals;
            this.mIsSystemLanguage =
                    equals || TextUtils.equals(locale.getLanguage(), locale2.getLanguage());
        }
    }
}
