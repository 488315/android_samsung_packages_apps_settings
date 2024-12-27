package com.samsung.android.settings.goodsettings;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GoodSettingsContract {
    public static final long CURRENT_POLICY_VERSION = 5;
    public static long FLAGS_PHASE_1 = 21474873434L;
    public static final String IS_ENABLED = "home_configuration_is_enabled";
    public static final String POLICY_FILE_NAME = "good_settings_policy.json";
    public static final String SAMSUNG_ACCOUNT_POLICY_KEY_HIDE_EMAIL = "hide_samsung_account_email";
    public static final String SAMSUNG_ACCOUNT_POLICY_KEY_SHOW_NICK_NAME =
            "show_samsung_account_nick_name";
    public static final String TOP_LEVEL_PREFERENCES = "home_configuration_top_level_preferences";
    public static final String TOP_LEVEL_PREFERENCES_POLICY_KEY =
            "home_configuration_top_level_preferences";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class EXTRA_NAME {
        public static final String POLICY_KEY = "key";
        public static final String POLICY_TYPE = "type";
        public static final String POLICY_VALUE = "value";
        public static final String PREFERENCE_FLAGS = "flags";
        public static final String PREFERENCE_KEYS = "keys";
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class POLICY_TYPE {
        public static final String BOOLEAN = "Boolean";
        public static final String STRING = "String";
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Retention(RetentionPolicy.SOURCE)
    public @interface PreferenceFlag {
        public static final long FLAG_NEED_PREF_IS_AVAILABLE = 8589934592L;
        public static final long FLAG_NEED_PREF_IS_VISIBLE = 17179869184L;
        public static final long FLAG_NEED_TYPE = 4294967296L;
    }
}
