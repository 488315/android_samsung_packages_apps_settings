package com.samsung.android.settings.accessibility.dexterity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.CompoundButton;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.settings.accessibility.base.widget.DescriptionPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EasyScreenTurnOnPreferenceFragment extends DashboardFragment
        implements CompoundButton.OnCheckedChangeListener {
    public DescriptionPreference mDescriptionPref;
    public SettingsMainSwitchBar mSwitchBar;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5003;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.easy_screen_turn_on;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        AccessibilityManager accessibilityManager =
                (AccessibilityManager) getContext().getSystemService("accessibility");
        if (z) {
            accessibilityManager.semEnableAirGestureWakeUp();
        } else {
            accessibilityManager.semDisableAirGestureWakeUp();
        }
        compoundButton.announceForAccessibility(
                getString(z ? R.string.switch_on_text : R.string.switch_off_text));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        DescriptionPreference descriptionPreference = this.mDescriptionPref;
        if (descriptionPreference != null) {
            descriptionPreference.notifyChanged();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        super.onCreatePreferences(bundle, str);
        this.mDescriptionPref = (DescriptionPreference) findPreference("guide_description");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        if (settingsActivity != null) {
            SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
            this.mSwitchBar = settingsMainSwitchBar;
            settingsMainSwitchBar.setChecked(
                    Settings.Global.getInt(getContentResolver(), "air_motion_wake_up", 0) != 0);
            this.mSwitchBar.addOnSwitchChangeListener(this);
            this.mSwitchBar.setSessionDescription(
                    settingsActivity.getString(R.string.easy_screen_turn_on_title));
            this.mSwitchBar.show();
        }
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mSwitchBar.hide();
        }
    }
}
