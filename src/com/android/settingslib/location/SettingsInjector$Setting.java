package com.android.settingslib.location;

import androidx.preference.Preference;

import com.android.settings.location.AppSettingsInjector;
import com.android.settingslib.widget.AppPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SettingsInjector$Setting {
    public final Preference preference;
    public final InjectedSetting setting;
    public long startMillis;
    public final /* synthetic */ AppSettingsInjector this$0;

    public SettingsInjector$Setting(
            AppSettingsInjector appSettingsInjector,
            InjectedSetting injectedSetting,
            AppPreference appPreference) {
        this.this$0 = appSettingsInjector;
        this.setting = injectedSetting;
        this.preference = appPreference;
    }

    public final String toString() {
        return "Setting{setting=" + this.setting + ", preference=" + this.preference + '}';
    }
}
