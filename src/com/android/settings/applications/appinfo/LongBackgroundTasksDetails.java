package com.android.settings.applications.appinfo;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.applications.AppInfoWithHeader;
import com.android.settings.applications.AppStateLongBackgroundTasksBridge;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LongBackgroundTasksDetails extends AppInfoWithHeader
        implements Preference.OnPreferenceChangeListener {
    public AppStateLongBackgroundTasksBridge mAppBridge;
    public AppOpsManager mAppOpsManager;
    public AppStateLongBackgroundTasksBridge.LongBackgroundTasksState mPermissionState;
    public RestrictedSwitchPreference mSwitchPref;
    public volatile Boolean mUncommittedState;

    public static CharSequence getSummary(Context context, ApplicationsState.AppEntry appEntry) {
        AppStateLongBackgroundTasksBridge appStateLongBackgroundTasksBridge =
                new AppStateLongBackgroundTasksBridge(context, null, null);
        ApplicationInfo applicationInfo = appEntry.info;
        return context.getString(
                appStateLongBackgroundTasksBridge.createPermissionState(
                                        applicationInfo.uid, applicationInfo.packageName)
                                .mPermissionGranted
                        ? R.string.app_permission_summary_allowed
                        : R.string.app_permission_summary_not_allowed);
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1975;
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mAppBridge = new AppStateLongBackgroundTasksBridge(activity, null, null);
        this.mAppOpsManager = (AppOpsManager) activity.getSystemService(AppOpsManager.class);
        if (bundle != null) {
            this.mUncommittedState = (Boolean) bundle.get("uncommitted_state");
            if (this.mUncommittedState != null
                    && Settings.LongBackgroundTasksAppActivity.class
                            .getName()
                            .equals(getIntent().getComponent().getClassName())) {
                setResult(this.mUncommittedState.booleanValue() ? -1 : 0);
            }
        }
        addPreferencesFromResource(R.xml.long_background_tasks);
        RestrictedSwitchPreference restrictedSwitchPreference =
                (RestrictedSwitchPreference) findPreference("long_background_tasks_switch");
        this.mSwitchPref = restrictedSwitchPreference;
        restrictedSwitchPreference.setOnPreferenceChangeListener(this);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (getActivity().isChangingConfigurations()
                || this.mPermissionState == null
                || this.mUncommittedState == null
                || this.mUncommittedState.booleanValue()
                        == this.mPermissionState.mPermissionGranted) {
            return;
        }
        boolean booleanValue = this.mUncommittedState.booleanValue();
        this.mAppOpsManager.setUidMode(
                "android:run_user_initiated_jobs",
                this.mPackageInfo.applicationInfo.uid,
                booleanValue ? 0 : 2);
        boolean booleanValue2 = this.mUncommittedState.booleanValue();
        String str = this.mPackageName;
        MetricsFeatureProvider metricsFeatureProvider = this.mMetricsFeatureProvider;
        FragmentActivity activity = getActivity();
        metricsFeatureProvider.getClass();
        metricsFeatureProvider.action(
                MetricsFeatureProvider.getAttribution(activity),
                1804,
                1975,
                booleanValue2 ? 1 : 0,
                str);
        this.mUncommittedState = null;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference != this.mSwitchPref) {
            return false;
        }
        this.mUncommittedState = (Boolean) obj;
        if (Settings.LongBackgroundTasksAppActivity.class
                .getName()
                .equals(getIntent().getComponent().getClassName())) {
            setResult(this.mUncommittedState.booleanValue() ? -1 : 0);
        }
        refreshUi();
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.mUncommittedState != null) {
            bundle.putObject("uncommitted_state", this.mUncommittedState);
        }
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        ApplicationInfo applicationInfo;
        PackageInfo packageInfo = this.mPackageInfo;
        if (packageInfo == null || (applicationInfo = packageInfo.applicationInfo) == null) {
            return false;
        }
        AppStateLongBackgroundTasksBridge.LongBackgroundTasksState createPermissionState =
                this.mAppBridge.createPermissionState(applicationInfo.uid, this.mPackageName);
        this.mPermissionState = createPermissionState;
        this.mSwitchPref.setEnabled(createPermissionState.mPermissionRequested);
        this.mSwitchPref.setChecked(
                this.mUncommittedState != null
                        ? this.mUncommittedState.booleanValue()
                        : this.mPermissionState.mPermissionGranted);
        return true;
    }
}
