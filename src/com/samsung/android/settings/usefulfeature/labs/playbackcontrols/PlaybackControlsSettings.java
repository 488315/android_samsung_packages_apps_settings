package com.samsung.android.settings.usefulfeature.labs.playbackcontrols;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.usefulfeature.labs.LabsSettings;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class PlaybackControlsSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();
    public static boolean mEnableSwitch = false;
    public SettingsMainSwitchBar mSwitchBar;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.usefulfeature.labs.playbackcontrols.PlaybackControlsSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            String valueOf = String.valueOf(68518);
            String str =
                    PlaybackControlsSettings.mEnableSwitch ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_playback_controls_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return LabsSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 68500;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_playback_controls_settings;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_advanced_features";
    }

    public final void initPreference$14() {
        setAutoRemoveInsetCategory(false);
        FrameLayout frameLayout =
                (FrameLayout)
                        ((LayoutPreference) findPreference("playback_controls_preview"))
                                .mRootView.findViewById(R.id.playback_image_container);
        frameLayout.semSetRoundedCorners(15);
        frameLayout.semSetRoundedCornerColor(
                15, getContext().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
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
        SALogging.insertSALog(z ? 1L : 0L, String.valueOf(68500), "V012");
        Settings.Global.putInt(
                getContext().getContentResolver(),
                "show_playback_controls_below_videos",
                z ? 1 : 0);
        mEnableSwitch = z;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getPreferenceScreen().removeAll();
        addPreferencesFromResource(R.xml.sec_playback_controls_settings);
        initPreference$14();
        updateSwitchAndPreferenceState$4();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initPreference$14();
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
        updateSwitchAndPreferenceState$4();
    }

    public final void updateSwitchAndPreferenceState$4() {
        boolean z =
                Settings.Global.getInt(
                                getContentResolver(),
                                "Settings.Global.SHOW_PLAYBACK_CONTROLS_BELOW_VIDEOS",
                                0)
                        != 0;
        mEnableSwitch = z;
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(z);
            this.mSwitchBar.show();
        }
    }
}
