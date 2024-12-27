package com.android.settings.development;

import android.content.Context;
import android.os.SystemProperties;

import androidx.fragment.app.FragmentManagerImpl;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NfcSnoopLogPreferenceController extends DeveloperOptionsPreferenceController
        implements Preference.OnPreferenceChangeListener {
    static final String NFCSNOOP_MODE_FILTERED = "filtered";
    static final String NFCSNOOP_MODE_FULL = "full";
    static final String NFC_NFCSNOOP_LOG_MODE_PROPERTY = "persist.nfc.snoop_log_mode";
    boolean mChanged;
    public final DevelopmentSettingsDashboardFragment mFragment;

    public NfcSnoopLogPreferenceController(
            Context context,
            DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment) {
        super(context);
        this.mChanged = false;
        this.mFragment = developmentSettingsDashboardFragment;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "nfc_snoop_log";
    }

    @Override // com.android.settingslib.development.DeveloperOptionsPreferenceController
    public final void onDeveloperOptionsSwitchDisabled() {
        super.onDeveloperOptionsSwitchDisabled();
        try {
            SystemProperties.set(NFC_NFCSNOOP_LOG_MODE_PROPERTY, NFCSNOOP_MODE_FILTERED);
            ((TwoStatePreference) this.mPreference).setChecked(false);
        } catch (RuntimeException e) {
            DevelopmentSettingsDashboardFragment$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Fail to set nfc system property: "), "NfcSnoopLog");
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment = this.mFragment;
        FragmentManagerImpl supportFragmentManager =
                developmentSettingsDashboardFragment.getActivity().getSupportFragmentManager();
        if (supportFragmentManager.findFragmentByTag("NfcRebootDialog") == null) {
            NfcRebootDialog nfcRebootDialog = new NfcRebootDialog();
            nfcRebootDialog.setTargetFragment(developmentSettingsDashboardFragment, 0);
            nfcRebootDialog.show(supportFragmentManager, "NfcRebootDialog");
        }
        this.mChanged = true;
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        try {
            ((TwoStatePreference) this.mPreference)
                    .setChecked(
                            SystemProperties.get(NFC_NFCSNOOP_LOG_MODE_PROPERTY)
                                    .equals(NFCSNOOP_MODE_FULL));
        } catch (RuntimeException e) {
            DevelopmentSettingsDashboardFragment$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Fail to get nfc system property: "), "NfcSnoopLog");
        }
    }
}
