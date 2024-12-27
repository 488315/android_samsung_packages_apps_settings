package com.samsung.android.settings.usefulfeature.multiwindow.fullscreeninsplitscreenview;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.multiwindow.MultiwindowSettings;
import com.samsung.android.settings.widget.SecHelpAnimationLayoutPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FullScreenInSplitScreenViewSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public Context mContext;
    public SecHelpAnimationLayoutPreference mPreviewPreference;
    public SettingsMainSwitchBar mSwitchBar;

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_full_screen_in_split_screen_view_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return MultiwindowSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return Integer.parseInt("68111");
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_advanced_features";
    }

    public final void initPreference$16() {
        addPreferencesFromResource(R.xml.sec_full_screen_in_split_screen_view_settings);
        setAutoRemoveInsetCategory(false);
        this.mPreviewPreference =
                (SecHelpAnimationLayoutPreference)
                        findPreference("full_screen_in_split_screen_preview");
        if (Rune.supportNavigationBar()) {
            this.mPreviewPreference.setDescText(
                    this.mContext.getString(R.string.sec_full_screen_in_split_screen_view_summary));
        } else {
            this.mPreviewPreference.setDescText(
                    this.mContext.getString(
                            R.string
                                    .sec_full_screen_in_split_screen_view_summary_without_navi_bar));
        }
        if (Utils.isNightMode(this.mContext)) {
            this.mPreviewPreference.setAnimationResource("fullscreeninsplitviewscreen_dark.json");
        } else {
            this.mPreviewPreference.setAnimationResource("fullscreeninsplitviewscreen_light.json");
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.addOnSwitchChangeListener(this);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        MultiWindowManager.getInstance().setSplitImmersiveMode(z);
        LoggingHelper.insertEventLogging(Integer.parseInt("68111"), 68101, z);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getPreferenceScreen().removeAll();
        initPreference$16();
        boolean isSplitImmersiveModeEnabled =
                MultiWindowManager.getInstance().isSplitImmersiveModeEnabled();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(isSplitImmersiveModeEnabled);
            this.mSwitchBar.show();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        initPreference$16();
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

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        boolean isSplitImmersiveModeEnabled =
                MultiWindowManager.getInstance().isSplitImmersiveModeEnabled();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(isSplitImmersiveModeEnabled);
            this.mSwitchBar.show();
        }
    }
}
