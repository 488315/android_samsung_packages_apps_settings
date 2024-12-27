package com.android.settings.development;

import android.R;
import android.content.Context;
import android.os.Build;
import android.os.UserManager;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AutomaticSystemServerHeapDumpPreferenceController
        extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener, PreferenceControllerMixin {
    public final boolean mIsConfigEnabled;
    public final UserManager mUserManager;

    public AutomaticSystemServerHeapDumpPreferenceController(Context context) {
        super(context);
        this.mIsConfigEnabled =
                context.getResources()
                        .getBoolean(R.bool.config_defaultBinderHeavyHitterWatcherEnabled);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "automatic_system_server_heap_dumps";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return Build.IS_DEBUGGABLE
                && this.mIsConfigEnabled
                && !this.mUserManager.hasUserRestriction("no_debugging_features");
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        Settings.Secure.putInt(
                this.mContext.getContentResolver(), "enable_automatic_system_server_heap_dumps", 0);
        ((TwoStatePreference) this.mPreference).setChecked(false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Settings.Secure.putInt(
                this.mContext.getContentResolver(),
                "enable_automatic_system_server_heap_dumps",
                ((Boolean) obj).booleanValue() ? 1 : 0);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) this.mPreference)
                .setChecked(
                        Settings.Secure.getInt(
                                        this.mContext.getContentResolver(),
                                        "enable_automatic_system_server_heap_dumps",
                                        1)
                                != 0);
    }
}
