package com.android.settings.notification.modes;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.PrimarySwitchPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeSetTriggerLinkPreferenceController
        extends AbstractZenModePreferenceController {
    protected static final String AUTOMATIC_TRIGGER_PREF_KEY = "zen_automatic_trigger_settings";
    protected Preference.OnPreferenceChangeListener mSwitchChangeListener;

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final boolean isAvailable(ZenMode zenMode) {
        return !zenMode.mIsManualDnd;
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
        Preference findPreference =
                ((PreferenceCategory) preference).findPreference(AUTOMATIC_TRIGGER_PREF_KEY);
        if (findPreference == null) {
            return;
        }
        ((PrimarySwitchPreference) findPreference).setChecked(zenMode.mRule.isEnabled());
        findPreference.setOnPreferenceChangeListener(this.mSwitchChangeListener);
        Bundle bundle = new Bundle();
        bundle.putString("MODE_ID", zenMode.mId);
        int type = zenMode.mRule.getType();
        if (type == 1) {
            findPreference.setTitle(R.string.zen_mode_set_schedule_link);
            findPreference.setSummary(zenMode.mRule.getTriggerDescription());
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
            String name = ZenModeSetScheduleFragment.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            launchRequest.mSourceMetricsCategory = 0;
            launchRequest.mArguments = bundle;
            findPreference.setIntent(subSettingLauncher.toIntent());
            return;
        }
        if (type != 2) {
            findPreference.setTitle("not implemented");
            return;
        }
        findPreference.setTitle(R.string.zen_mode_set_calendar_link);
        findPreference.setSummary(zenMode.mRule.getTriggerDescription());
        SubSettingLauncher subSettingLauncher2 = new SubSettingLauncher(this.mContext);
        String name2 = ZenModeSetCalendarFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest2 = subSettingLauncher2.mLaunchRequest;
        launchRequest2.mDestinationName = name2;
        launchRequest2.mSourceMetricsCategory = 0;
        launchRequest2.mArguments = bundle;
        findPreference.setIntent(subSettingLauncher2.toIntent());
    }
}
