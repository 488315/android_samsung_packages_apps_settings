package com.android.settings.applications.appinfo;

import android.app.AppOpsManager;
import android.content.pm.PackageInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.applications.AppInfoBase;
import com.android.settings.applications.AppInfoWithHeader;
import com.android.settings.applications.AppStateAppOpsBridge;
import com.android.settings.applications.AppStateManageExternalStorageBridge;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ManageExternalStorageDetails extends AppInfoWithHeader
        implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    public AppOpsManager mAppOpsManager;
    public AppStateManageExternalStorageBridge mBridge;
    public SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public AppStateAppOpsBridge.PermissionState mPermissionState;
    public TwoStatePreference mSwitchPref;

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1822;
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mBridge =
                new AppStateManageExternalStorageBridge(
                        activity, ((AppInfoBase) this).mState, null);
        this.mAppOpsManager = (AppOpsManager) activity.getSystemService("appops");
        addPreferencesFromResource(R.xml.sec_manage_external_storage_permission_details);
        TwoStatePreference twoStatePreference =
                (TwoStatePreference) findPreference("app_ops_settings_switch");
        this.mSwitchPref = twoStatePreference;
        twoStatePreference.seslSetRoundedBg(15);
        this.mSwitchPref.setOnPreferenceChangeListener(this);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        SALogging.insertSALog(Integer.toString(39001));
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mBridge.release();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference != this.mSwitchPref) {
            return false;
        }
        AppInfoWithHeader.insertEventLogging(
                39001, 3931, this.mPackageName, ((Boolean) obj).booleanValue());
        AppStateAppOpsBridge.PermissionState permissionState = this.mPermissionState;
        if (permissionState == null
                || obj.equals(Boolean.valueOf(permissionState.isPermissible()))) {
            return true;
        }
        boolean booleanValue = ((Boolean) obj).booleanValue();
        String str = this.mPackageName;
        int i = booleanValue ? 1730 : 1731;
        SettingsMetricsFeatureProvider settingsMetricsFeatureProvider =
                this.mMetricsFeatureProvider;
        FragmentActivity activity = getActivity();
        settingsMetricsFeatureProvider.getClass();
        settingsMetricsFeatureProvider.action(
                MetricsFeatureProvider.getAttribution(activity), i, 1822, 0, str);
        this.mAppOpsManager.setUidMode(
                92, this.mPackageInfo.applicationInfo.uid, booleanValue ? 0 : 2);
        refreshUi();
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
        AppStateAppOpsBridge.PermissionState permissionInfo =
                this.mBridge.getPermissionInfo(packageInfo.applicationInfo.uid, this.mPackageName);
        this.mPermissionState = permissionInfo;
        this.mSwitchPref.setChecked(permissionInfo.isPermissible());
        this.mSwitchPref.setEnabled(this.mPermissionState.permissionDeclared);
        return true;
    }
}
