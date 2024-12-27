package com.android.settings.nfc;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.applications.AppInfoBase;
import com.android.settings.applications.AppInfoWithHeader;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ChangeNfcTagAppsStateDetails extends AppInfoWithHeader
        implements Preference.OnPreferenceChangeListener {
    public AppStateNfcTagAppsBridge mAppBridge;
    public TwoStatePreference mSwitchPref;

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2016;
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mAppBridge =
                new AppStateNfcTagAppsBridge(getActivity(), ((AppInfoBase) this).mState, null);
        addPreferencesFromResource(R.xml.change_nfc_tag_apps_details);
        TwoStatePreference twoStatePreference =
                (TwoStatePreference) findPreference("app_ops_settings_switch");
        this.mSwitchPref = twoStatePreference;
        twoStatePreference.setTitle(R.string.change_nfc_tag_apps_detail_switch);
        this.mSwitchPref.setOnPreferenceChangeListener(this);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Boolean bool = (Boolean) obj;
        if (preference == this.mSwitchPref) {
            AppStateNfcTagAppsBridge appStateNfcTagAppsBridge = this.mAppBridge;
            if (appStateNfcTagAppsBridge != null) {
                int i = this.mUserId;
                if (appStateNfcTagAppsBridge.mNfcAdapter.setTagIntentAppPreferenceForUser(
                                i, this.mPackageName, bool.booleanValue())
                        == 0) {
                    ((HashMap) AppStateNfcTagAppsBridge.sList)
                            .put(
                                    Integer.valueOf(i),
                                    appStateNfcTagAppsBridge.mNfcAdapter
                                            .getTagIntentAppPreferenceForUser(i));
                    refreshUi();
                    return true;
                }
            }
            Log.e("ChangeNfcTagAppsStateDetails", "Set [" + this.mPackageName + "] failed.");
        }
        return false;
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        PackageInfo packageInfo = this.mPackageInfo;
        if (packageInfo == null || packageInfo.applicationInfo == null) {
            return false;
        }
        retrieveAppEntry$1();
        Object obj = this.mAppEntry.extraInfo;
        AppStateNfcTagAppsBridge.NfcTagAppState nfcTagAppState =
                obj instanceof AppStateNfcTagAppsBridge.NfcTagAppState
                        ? (AppStateNfcTagAppsBridge.NfcTagAppState) obj
                        : new AppStateNfcTagAppsBridge.NfcTagAppState(false, false);
        this.mSwitchPref.setChecked(nfcTagAppState.mIsAllowed);
        this.mSwitchPref.setEnabled(nfcTagAppState.mIsExisted);
        return true;
    }
}
