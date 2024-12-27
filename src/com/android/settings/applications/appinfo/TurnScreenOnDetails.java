package com.android.settings.applications.appinfo;

import android.app.AppOpsManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.applications.AppInfoBase;
import com.android.settings.applications.AppInfoWithHeader;
import com.android.settings.applications.AppStateAppOpsBridge;
import com.android.settings.applications.AppStateTurnScreenOnBridge;

import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TurnScreenOnDetails extends AppInfoWithHeader
        implements Preference.OnPreferenceChangeListener {
    public AppStateTurnScreenOnBridge mAppBridge;
    public AppOpsManager mAppOpsManager;
    public AppStateAppOpsBridge.PermissionState mPermissionState;
    public TwoStatePreference mSwitchPref;

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1922;
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
                new AppStateTurnScreenOnBridge(activity, ((AppInfoBase) this).mState, null);
        this.mAppOpsManager = (AppOpsManager) activity.getSystemService(AppOpsManager.class);
        addPreferencesFromResource(R.xml.turn_screen_on_permissions_details);
        TwoStatePreference twoStatePreference =
                (TwoStatePreference) findPreference("app_ops_settings_switch");
        this.mSwitchPref = twoStatePreference;
        twoStatePreference.setOnPreferenceChangeListener(this);
        SALogging.insertSALog(Integer.toString(39015));
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Boolean bool = (Boolean) obj;
        boolean booleanValue = bool.booleanValue();
        if (preference != this.mSwitchPref) {
            return false;
        }
        AppInfoWithHeader.insertEventLogging(39015, 3945, this.mPackageName, bool.booleanValue());
        AppStateAppOpsBridge.PermissionState permissionState = this.mPermissionState;
        if (permissionState == null || booleanValue == permissionState.isPermissible()) {
            return true;
        }
        if (Settings.TurnScreenOnSettingsActivity.class
                .getName()
                .equals(getIntent().getComponent().getClassName())) {
            setResult(booleanValue ? -1 : 0);
        }
        this.mAppOpsManager.setUidMode(
                "android:turn_screen_on",
                this.mPackageInfo.applicationInfo.uid,
                booleanValue ? 0 : 2);
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
        AppStateAppOpsBridge.PermissionState permissionInfo =
                this.mAppBridge.getPermissionInfo(applicationInfo.uid, this.mPackageName);
        this.mPermissionState = permissionInfo;
        this.mSwitchPref.setEnabled(permissionInfo.permissionDeclared);
        this.mSwitchPref.setChecked(this.mPermissionState.isPermissible());
        return true;
    }
}
