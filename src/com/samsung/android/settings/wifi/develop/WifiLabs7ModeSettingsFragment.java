package com.samsung.android.settings.wifi.develop;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.CompoundButton;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settings.widget.SettingsMainSwitchPreference;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiLabs7ModeSettingsFragment extends DashboardFragment
        implements CompoundButton.OnCheckedChangeListener {
    public Context mContext;
    public SettingsMainSwitchPreference mSwitchBar;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiLabs7ModeSettingsFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_7_mode_settings;
    }

    public final void initSwitchBar() {
        int i =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "sec_wifi_7_mode_enabled", 1);
        this.mSwitchBar.setChecked(i == 1);
        this.mSwitchBar.setTitle(
                i == 1
                        ? this.mContext.getString(R.string.sec_wifi_on_title)
                        : this.mContext.getString(R.string.sec_wifi_off_title));
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Settings.Secure.putInt(
                this.mContext.getContentResolver(), "sec_wifi_7_mode_enabled", z ? 1 : 0);
        initSwitchBar();
        ((WifiLabsMloModePreferenceController) use(WifiLabsMloModePreferenceController.class))
                .updateState();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mSwitchBar = (SettingsMainSwitchPreference) findPreference("main_switch_preference");
        this.mContext = getContext();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchPreference settingsMainSwitchPreference = this.mSwitchBar;
        settingsMainSwitchPreference.setVisible(false);
        SettingsMainSwitchBar settingsMainSwitchBar = settingsMainSwitchPreference.mMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.hide();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        SettingsMainSwitchPreference settingsMainSwitchPreference = this.mSwitchBar;
        ((ArrayList) settingsMainSwitchPreference.mSwitchChangeListeners).remove(this);
        SettingsMainSwitchBar settingsMainSwitchBar = settingsMainSwitchPreference.mMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mSwitchBar.addOnSwitchChangeListener(this);
        initSwitchBar();
    }
}
