package com.android.settings.development;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class StayAwakePreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener,
                LifecycleObserver,
                OnResume,
                OnPause,
                PreferenceControllerMixin {
    static final int SETTING_VALUE_OFF = 0;
    static final int SETTING_VALUE_ON = 15;
    public RestrictedSwitchPreference mPreference;
    SettingsObserver mSettingsObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class SettingsObserver extends ContentObserver {
        public final Uri mStayAwakeUri;

        public SettingsObserver() {
            super(new Handler());
            this.mStayAwakeUri = Settings.Global.getUriFor("stay_on_while_plugged_in");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (this.mStayAwakeUri.equals(uri)) {
                StayAwakePreferenceController stayAwakePreferenceController =
                        StayAwakePreferenceController.this;
                stayAwakePreferenceController.updateState(
                        stayAwakePreferenceController.mPreference);
            }
        }
    }

    public RestrictedLockUtils.EnforcedAdmin checkIfMaximumTimeToLockSetByAdmin() {
        return RestrictedLockUtilsInternal.checkIfMaximumTimeToLockIsSet(this.mContext);
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (RestrictedSwitchPreference) preferenceScreen.findPreference("keep_screen_on");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "keep_screen_on";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        Settings.Global.putInt(this.mContext.getContentResolver(), "stay_on_while_plugged_in", 0);
        this.mPreference.setChecked(false);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        SettingsObserver settingsObserver;
        if (this.mPreference == null || (settingsObserver = this.mSettingsObserver) == null) {
            return;
        }
        StayAwakePreferenceController.this
                .mContext
                .getContentResolver()
                .unregisterContentObserver(settingsObserver);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Settings.Global.putInt(
                this.mContext.getContentResolver(),
                "stay_on_while_plugged_in",
                ((Boolean) obj).booleanValue() ? 15 : 0);
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        if (this.mPreference == null) {
            return;
        }
        if (this.mSettingsObserver == null) {
            this.mSettingsObserver = new SettingsObserver();
        }
        SettingsObserver settingsObserver = this.mSettingsObserver;
        StayAwakePreferenceController.this
                .mContext
                .getContentResolver()
                .registerContentObserver(settingsObserver.mStayAwakeUri, false, settingsObserver);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        RestrictedLockUtils.EnforcedAdmin checkIfMaximumTimeToLockSetByAdmin =
                checkIfMaximumTimeToLockSetByAdmin();
        if (checkIfMaximumTimeToLockSetByAdmin != null) {
            this.mPreference.setDisabledByAdmin(checkIfMaximumTimeToLockSetByAdmin);
        } else {
            this.mPreference.setChecked(
                    Settings.Global.getInt(
                                    this.mContext.getContentResolver(),
                                    "stay_on_while_plugged_in",
                                    0)
                            != 0);
        }
    }
}
