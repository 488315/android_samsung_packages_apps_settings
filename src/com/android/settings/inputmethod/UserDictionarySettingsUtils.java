package com.android.settings.inputmethod;

import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class UserDictionarySettingsUtils {
    public static String getLocaleDisplayName(FragmentActivity fragmentActivity, String str) {
        return TextUtils.isEmpty(str)
                ? fragmentActivity
                        .getResources()
                        .getString(R.string.user_dict_settings_all_languages)
                : Utils.createLocaleFromString(str)
                        .getDisplayName(fragmentActivity.getResources().getConfiguration().locale);
    }
}
