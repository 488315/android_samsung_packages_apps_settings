package com.samsung.android.settings.usefulfeature.multiwindow.swipeforsplitview;

import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.CompoundButton;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.multiwindow.MultiwindowSettings;
import com.samsung.android.settings.widget.SecHelpAnimationLayoutPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SwipeForSplitViewSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final Uri[] SETTINGS_URIS = {
        Settings.Global.getUriFor("open_in_split_screen_view")
    };
    public Context mContext;
    public SecHelpAnimationLayoutPreference mPreviewPreference;
    public final AnonymousClass1 mSettingsObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.usefulfeature.multiwindow.swipeforsplitview.SwipeForSplitViewSettings.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    SwipeForSplitViewSettings swipeForSplitViewSettings =
                            SwipeForSplitViewSettings.this;
                    Uri[] uriArr = SwipeForSplitViewSettings.SETTINGS_URIS;
                    swipeForSplitViewSettings.updateSwitchAndPreferenceState$7();
                }
            };
    public SettingsMainSwitchBar mSwitchBar;

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_swpie_for_split_view_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return MultiwindowSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return Integer.parseInt("68130");
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_advanced_features";
    }

    public final void initPreference$18() {
        addPreferencesFromResource(R.xml.sec_swipe_for_split_view_settings);
        setAutoRemoveInsetCategory(false);
        SecHelpAnimationLayoutPreference secHelpAnimationLayoutPreference =
                (SecHelpAnimationLayoutPreference) findPreference("swipe_for_split_preview");
        this.mPreviewPreference = secHelpAnimationLayoutPreference;
        secHelpAnimationLayoutPreference.setDescText(
                this.mContext.getString(R.string.sec_swpie_for_split_view_summary_bartype));
        if (Utils.isNightMode(this.mContext)) {
            this.mPreviewPreference.setAnimationResource("swipeforsplitview_dark.json");
        } else {
            this.mPreviewPreference.setAnimationResource("swipeforsplitview_light.json");
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
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "open_in_split_screen_view", z ? 1 : 0);
        LoggingHelper.insertEventLogging(Integer.parseInt("68130"), 68131, z);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getPreferenceScreen().removeAll();
        initPreference$18();
        updateSwitchAndPreferenceState$7();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        initPreference$18();
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

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updateSwitchAndPreferenceState$7();
        for (Uri uri : SETTINGS_URIS) {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(uri, false, this.mSettingsObserver);
        }
    }

    public final void updateSwitchAndPreferenceState$7() {
        boolean z =
                Settings.Global.getInt(getContentResolver(), "open_in_split_screen_view", 0) != 0;
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(z);
            this.mSwitchBar.show();
        }
    }
}
