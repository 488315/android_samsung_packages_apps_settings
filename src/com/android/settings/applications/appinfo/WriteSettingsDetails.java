package com.android.settings.applications.appinfo;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.applications.AppInfoWithHeader;
import com.android.settings.applications.AppStateWriteSettingsBridge;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.applications.AppCommonUtils;
import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WriteSettingsDetails extends AppInfoWithHeader
        implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    public AppStateWriteSettingsBridge mAppBridge;
    public AppOpsManager mAppOpsManager;
    public TwoStatePreference mSwitchPref;
    public AppStateWriteSettingsBridge.WriteSettingsState mWriteSettingsState;

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return AppCommonUtils.getWriteSettingsTitle();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1976;
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mAppBridge = new AppStateWriteSettingsBridge(activity, null, null);
        this.mAppOpsManager = (AppOpsManager) activity.getSystemService("appops");
        addPreferencesFromResource(R.xml.sec_write_system_settings_permissions_details);
        TwoStatePreference twoStatePreference =
                (TwoStatePreference) findPreference("app_ops_settings_switch");
        this.mSwitchPref = twoStatePreference;
        twoStatePreference.setOnPreferenceChangeListener(this);
        getActivity().setTitle(AppCommonUtils.getWriteSettingsTitle());
        this.mSwitchPref.seslSetRoundedBg(15);
        SALogging.insertSALog(Integer.toString(39006));
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference != this.mSwitchPref) {
            return false;
        }
        Boolean bool = (Boolean) obj;
        AppInfoWithHeader.insertEventLogging(39006, 3936, this.mPackageName, bool.booleanValue());
        if (this.mWriteSettingsState != null
                && bool.booleanValue() != this.mWriteSettingsState.isPermissible()) {
            boolean z = !this.mWriteSettingsState.isPermissible();
            String str = this.mPackageName;
            int i = z ? 774 : 775;
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl.getMetricsFeatureProvider().action(getContext(), i, str);
            this.mAppOpsManager.setMode(
                    23, this.mPackageInfo.applicationInfo.uid, this.mPackageName, z ? 0 : 2);
            refreshUi();
        }
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        PackageInfo packageInfo = this.mPackageInfo;
        if (packageInfo == null) {
            return false;
        }
        AppStateWriteSettingsBridge.WriteSettingsState writeSettingsInfo =
                this.mAppBridge.getWriteSettingsInfo(
                        packageInfo.applicationInfo.uid, this.mPackageName);
        this.mWriteSettingsState = writeSettingsInfo;
        this.mSwitchPref.setChecked(writeSettingsInfo.isPermissible());
        this.mSwitchPref.setEnabled(this.mWriteSettingsState.permissionDeclared);
        return true;
    }
}
