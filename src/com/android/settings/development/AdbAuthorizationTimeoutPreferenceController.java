package com.android.settings.development;

import android.content.Context;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AdbAuthorizationTimeoutPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener {
    public final Context mContext;

    public AdbAuthorizationTimeoutPreferenceController(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "adb_authorization_timeout";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        Settings.Global.putLong(
                this.mContext.getContentResolver(), "adb_allowed_connection_time", 604800000L);
        ((TwoStatePreference) this.mPreference).setChecked(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Settings.Global.putLong(
                this.mContext.getContentResolver(),
                "adb_allowed_connection_time",
                !((Boolean) obj).booleanValue() ? 604800000L : 0L);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) this.mPreference)
                .setChecked(
                        Settings.Global.getLong(
                                        this.mContext.getContentResolver(),
                                        "adb_allowed_connection_time",
                                        604800000L)
                                == 0);
    }
}
