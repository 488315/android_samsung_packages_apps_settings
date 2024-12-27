package com.samsung.android.settings.applications.appinfo;

import android.app.AppOpsManager;
import android.content.pm.PackageInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.preference.Preference;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.applications.AppInfoWithHeader;
import com.android.settings.applications.AppStateAppOpsBridge;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.settings.applications.AppStateMediaRoutingAppsBridge;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.widget.SecUnclickablePreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MediaRoutingAppsDetails extends AppInfoWithHeader
        implements Preference.OnPreferenceChangeListener {
    public AppOpsManager mAppOpsManager;
    public AppStateAppOpsBridge.PermissionState mPermissionState;
    public SecSwitchPreference mSwitchPref;

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // androidx.fragment.app.Fragment, androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2064;
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mAppOpsManager = (AppOpsManager) getActivity().getSystemService(AppOpsManager.class);
        addPreferencesFromResource(R.xml.sec_apps_media_routing);
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) findPreference("apps_media_routing_settings_switch");
        this.mSwitchPref = secSwitchPreference;
        secSwitchPreference.setOnPreferenceChangeListener(this);
        SecUnclickablePreference secUnclickablePreference =
                (SecUnclickablePreference)
                        findPreference("apps_media_routing_settings_description");
        if (secUnclickablePreference != null) {
            secUnclickablePreference.setTitle(R.string.allow_media_routing_details_description);
        }
        SALogging.insertSALog(Integer.toString(39005));
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Boolean bool = (Boolean) obj;
        boolean booleanValue = bool.booleanValue();
        if (preference != this.mSwitchPref) {
            return false;
        }
        AppInfoWithHeader.insertEventLogging(39005, 3935, this.mPackageName, bool.booleanValue());
        AppStateAppOpsBridge.PermissionState permissionState = this.mPermissionState;
        if (permissionState == null || booleanValue == permissionState.isPermissible()) {
            return true;
        }
        this.mAppOpsManager.setUidMode(
                110, this.mPackageInfo.applicationInfo.uid, booleanValue ? 0 : 2);
        String str = this.mPackageName;
        MetricsFeatureProvider metricsFeatureProvider = this.mMetricsFeatureProvider;
        FragmentActivity activity = getActivity();
        metricsFeatureProvider.getClass();
        metricsFeatureProvider.action(
                MetricsFeatureProvider.getAttribution(activity),
                2064,
                2064,
                booleanValue ? 1 : 0,
                str);
        refreshUi();
        return true;
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        PackageInfo packageInfo = this.mPackageInfo;
        if (packageInfo == null || packageInfo.applicationInfo == null) {
            return false;
        }
        AppStateMediaRoutingAppsBridge appStateMediaRoutingAppsBridge =
                new AppStateMediaRoutingAppsBridge(getActivity(), null, null);
        String str = this.mPackageName;
        int i = this.mPackageInfo.applicationInfo.uid;
        AppStateAppOpsBridge.PermissionState permissionInfo =
                appStateMediaRoutingAppsBridge.getPermissionInfo(i, str);
        permissionInfo.appOpMode =
                appStateMediaRoutingAppsBridge.mAppOpsManager.unsafeCheckOpNoThrow(
                        "android:media_routing_control", i, str);
        this.mPermissionState = permissionInfo;
        this.mSwitchPref.setEnabled(permissionInfo.permissionDeclared);
        this.mSwitchPref.setChecked(this.mPermissionState.isPermissible());
        return true;
    }
}
