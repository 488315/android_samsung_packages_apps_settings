package com.samsung.android.settings.privacy;

import android.os.Bundle;
import android.provider.Settings;
import android.widget.CompoundButton;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.utils.ContentCaptureUtils;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecUnclickablePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PersonalizationServiceSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public SettingsMainSwitchBar mSwitchBar;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (ContentCaptureUtils.isEnabledForUser(getContext()) != z) {
            LoggingHelper.insertEventLogging(10901, 60101, z);
        }
        Settings.Secure.putIntForUser(
                getContext().getContentResolver(),
                "content_capture_enabled",
                z ? 1 : 0,
                ContentCaptureUtils.MY_USER_ID);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.sec_personalization_service_settings);
        ((SecUnclickablePreference) findPreference("personalization_service")).mPositionMode = 1;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.addOnSwitchChangeListener(this);
            this.mSwitchBar.show();
            this.mSwitchBar.setChecked(ContentCaptureUtils.isEnabledForUser(getContext()));
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mSwitchBar.hide();
        }
    }
}
