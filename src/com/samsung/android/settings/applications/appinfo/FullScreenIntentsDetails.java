package com.samsung.android.settings.applications.appinfo;

import android.app.AppOpsManager;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.applications.AppInfoBase;
import com.android.settings.applications.AppInfoWithHeader;
import com.android.settings.applications.AppStateAppOpsBridge;

import com.samsung.android.settings.applications.AppStateManageFullScreenIntentsBridge;
import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FullScreenIntentsDetails extends AppInfoWithHeader
        implements Preference.OnPreferenceChangeListener {
    public AppOpsManager mAppOpsManager;
    public AppStateAppOpsBridge.PermissionState mFullScreenIntentsState;
    public AppStateManageFullScreenIntentsBridge mManageFullScreenIntentsBridge;
    public TwoStatePreference mSwitchPref;

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mManageFullScreenIntentsBridge =
                new AppStateManageFullScreenIntentsBridge(
                        activity, ((AppInfoBase) this).mState, null);
        this.mAppOpsManager = (AppOpsManager) activity.getSystemService("appops");
        addPreferencesFromResource(R.xml.sec_manage_full_screen_intents_permission_details);
        TwoStatePreference twoStatePreference =
                (TwoStatePreference) findPreference("full_screen_intents_settings_switch");
        this.mSwitchPref = twoStatePreference;
        twoStatePreference.seslSetRoundedBg(15);
        this.mSwitchPref.setOnPreferenceChangeListener(this);
        SALogging.insertSALog(Integer.toString(39016));
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mManageFullScreenIntentsBridge.release();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference != this.mSwitchPref) {
            return false;
        }
        Boolean bool = (Boolean) obj;
        AppInfoWithHeader.insertEventLogging(39016, 3946, this.mPackageName, bool.booleanValue());
        if (this.mFullScreenIntentsState == null
                || bool.booleanValue() == this.mFullScreenIntentsState.isPermissible()) {
            return true;
        }
        PackageInfo packageInfo = this.mPackageInfo;
        if (packageInfo == null) {
            Log.i("FullScreenIntentsDetails", "PackageInfo is null");
        } else {
            this.mAppOpsManager.setUidMode(
                    133, packageInfo.applicationInfo.uid, bool.booleanValue() ? 0 : 2);
        }
        refreshUi();
        return true;
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        PackageInfo packageInfo = this.mPackageInfo;
        if (packageInfo == null) {
            Log.i("FullScreenIntentsDetails", "PackageInfo is null");
            return false;
        }
        AppStateAppOpsBridge.PermissionState permissionInfo =
                this.mManageFullScreenIntentsBridge.getPermissionInfo(
                        packageInfo.applicationInfo.uid, this.mPackageName);
        this.mFullScreenIntentsState = permissionInfo;
        this.mSwitchPref.setChecked(permissionInfo.isPermissible());
        this.mSwitchPref.setEnabled(this.mFullScreenIntentsState.permissionDeclared);
        return true;
    }
}
