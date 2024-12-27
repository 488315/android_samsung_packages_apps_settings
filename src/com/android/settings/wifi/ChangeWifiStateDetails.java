package com.android.settings.wifi;

import android.app.AppOpsManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import com.android.settings.R;
import com.android.settings.applications.AppInfoBase;
import com.android.settings.applications.AppInfoWithHeader;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ChangeWifiStateDetails extends AppInfoWithHeader
        implements Preference.OnPreferenceChangeListener {
    public AppStateChangeWifiStateBridge mAppBridge;
    public AppOpsManager mAppOpsManager;
    public SwitchPreference mSwitchPref;
    public AppStateChangeWifiStateBridge.WifiSettingsState mWifiSettingsState;

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.XLSB;
    }

    public final void logSpecialPermissionChange(boolean z, String str) {
        int i = z ? 774 : 775;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().action(getContext(), i, str);
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mAppBridge =
                new AppStateChangeWifiStateBridge(activity, ((AppInfoBase) this).mState, null);
        this.mAppOpsManager = (AppOpsManager) activity.getSystemService("appops");
        addPreferencesFromResource(R.xml.sec_change_wifi_state_details);
        SwitchPreference switchPreference =
                (SwitchPreference) findPreference("app_ops_settings_switch");
        this.mSwitchPref = switchPreference;
        switchPreference.setTitle(R.string.sec_special_access_detail_switch);
        this.mSwitchPref.setOnPreferenceChangeListener(this);
        SALogging.insertSALog(Integer.toString(39014));
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference != this.mSwitchPref) {
            return false;
        }
        Boolean bool = (Boolean) obj;
        AppInfoWithHeader.insertEventLogging(39014, 3944, this.mPackageName, bool.booleanValue());
        if (this.mWifiSettingsState == null
                || bool.booleanValue() == this.mWifiSettingsState.isPermissible()) {
            return true;
        }
        boolean isPermissible = this.mWifiSettingsState.isPermissible();
        logSpecialPermissionChange(!isPermissible, this.mPackageName);
        this.mAppOpsManager.setMode(
                71,
                this.mPackageInfo.applicationInfo.uid,
                this.mPackageName,
                isPermissible ? 1 : 0);
        refreshUi();
        return true;
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        ApplicationInfo applicationInfo;
        PackageInfo packageInfo = this.mPackageInfo;
        if (packageInfo == null || (applicationInfo = packageInfo.applicationInfo) == null) {
            return false;
        }
        AppStateChangeWifiStateBridge.WifiSettingsState wifiSettingsState =
                new AppStateChangeWifiStateBridge.WifiSettingsState(
                        this.mAppBridge.getPermissionInfo(applicationInfo.uid, this.mPackageName));
        this.mWifiSettingsState = wifiSettingsState;
        this.mSwitchPref.setChecked(wifiSettingsState.isPermissible());
        this.mSwitchPref.setEnabled(this.mWifiSettingsState.permissionDeclared);
        return true;
    }
}
