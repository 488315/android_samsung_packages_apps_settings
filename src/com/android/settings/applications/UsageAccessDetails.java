package com.android.settings.applications;

import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UsageAccessDetails extends AppInfoWithHeader
        implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AppOpsManager mAppOpsManager;
    public DevicePolicyManager mDpm;
    public Intent mSettingsIntent;
    public SecRestrictedSwitchPreference mSwitchPref;
    public AppStateUsageBridge mUsageBridge;
    public Preference mUsageDesc;
    public AppStateUsageBridge.UsageState mUsageState;

    public static boolean doesAnyPermissionMatch(String str, String[] strArr) {
        for (String str2 : strArr) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final AlertDialog createDialog(int i) {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 183;
    }

    public void logSpecialPermissionChange(boolean z, String str) {
        int i = z ? 783 : 784;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SettingsMetricsFeatureProvider metricsFeatureProvider =
                featureFactoryImpl.getMetricsFeatureProvider();
        FragmentActivity activity = getActivity();
        metricsFeatureProvider.getClass();
        metricsFeatureProvider.action(
                MetricsFeatureProvider.getAttribution(activity), i, 183, 0, str);
    }

    @Override // com.android.settings.applications.AppInfoBase,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        if (TextUtils.equals(this.mPackageName, activity.getPackageName())) {
            Log.w("UsageAccessDetails", "Unsupported app package.");
            finish();
        }
        this.mUsageBridge = new AppStateUsageBridge(activity, null, null);
        this.mAppOpsManager = (AppOpsManager) activity.getSystemService("appops");
        this.mDpm = (DevicePolicyManager) activity.getSystemService(DevicePolicyManager.class);
        addPreferencesFromResource(R.xml.sec_app_ops_permissions_details);
        this.mSwitchPref =
                (SecRestrictedSwitchPreference) findPreference("app_ops_settings_switch");
        this.mUsageDesc = findPreference("app_ops_settings_description");
        getPreferenceScreen().setTitle(R.string.usage_access);
        this.mSwitchPref.setTitle(R.string.sec_special_access_detail_switch);
        this.mUsageDesc.setTitle(R.string.usage_access_description);
        this.mSwitchPref.setOnPreferenceChangeListener(this);
        this.mSwitchPref.seslSetRoundedBg(15);
        SALogging.insertSALog(Integer.toString(39012));
        this.mSettingsIntent =
                new Intent("android.intent.action.MAIN")
                        .addCategory("android.intent.category.USAGE_ACCESS_CONFIG")
                        .setPackage(this.mPackageName);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference != this.mSwitchPref) {
            return false;
        }
        Boolean bool = (Boolean) obj;
        AppInfoWithHeader.insertEventLogging(39012, 3942, this.mPackageName, bool.booleanValue());
        if (this.mUsageState == null || bool.booleanValue() == this.mUsageState.isPermissible()) {
            return true;
        }
        if (this.mUsageState.isPermissible() && this.mDpm.isProfileOwnerApp(this.mPackageName)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mIconId = android.R.drawable.ic_feedback_indicator;
            builder.setTitle(android.R.string.dialog_alert_title);
            alertParams.mMessage =
                    this.mDpm
                            .getResources()
                            .getString(
                                    "Settings.WORK_PROFILE_DISABLE_USAGE_ACCESS_WARNING",
                                    new Supplier() { // from class:
                                                     // com.android.settings.applications.UsageAccessDetails$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            UsageAccessDetails usageAccessDetails =
                                                    UsageAccessDetails.this;
                                            int i = UsageAccessDetails.$r8$clinit;
                                            return usageAccessDetails.getString(
                                                    R.string.work_profile_usage_access_warning);
                                        }
                                    });
            builder.setPositiveButton(R.string.okay, (DialogInterface.OnClickListener) null);
            builder.show();
        }
        boolean isPermissible = this.mUsageState.isPermissible();
        logSpecialPermissionChange(!isPermissible, this.mPackageName);
        int i = this.mPackageInfo.applicationInfo.uid;
        if (doesAnyPermissionMatch(
                "android.permission.PACKAGE_USAGE_STATS",
                this.mUsageState.packageInfo.requestedPermissions)) {
            this.mAppOpsManager.setMode(43, i, this.mPackageName, isPermissible ? 1 : 0);
        }
        if (doesAnyPermissionMatch(
                "android.permission.LOADER_USAGE_STATS",
                this.mUsageState.packageInfo.requestedPermissions)) {
            this.mAppOpsManager.setMode(95, i, this.mPackageName, isPermissible ? 1 : 0);
        }
        refreshUi();
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        return false;
    }

    @Override // com.android.settings.applications.AppInfoBase
    public final boolean refreshUi() {
        PackageInfo packageInfo;
        retrieveAppEntry$1();
        if (this.mAppEntry == null || (packageInfo = this.mPackageInfo) == null) {
            return false;
        }
        AppStateUsageBridge.UsageState usageState =
                new AppStateUsageBridge.UsageState(
                        this.mUsageBridge.getPermissionInfo(
                                packageInfo.applicationInfo.uid, this.mPackageName));
        this.mUsageState = usageState;
        boolean isPermissible = usageState.isPermissible();
        boolean z = this.mUsageState.permissionDeclared;
        if (z && !isPermissible) {
            this.mSwitchPref.mHelper.checkEcmRestrictionAndSetDisabled(
                    "android:get_usage_stats", this.mPackageName);
            z = !this.mSwitchPref.mHelper.mDisabledByEcm;
        }
        this.mSwitchPref.setChecked(isPermissible);
        this.mSwitchPref.setEnabled(z);
        ResolveInfo resolveActivityAsUser =
                this.mPm.resolveActivityAsUser(this.mSettingsIntent, 128, this.mUserId);
        if (resolveActivityAsUser != null) {
            Bundle bundle = resolveActivityAsUser.activityInfo.metaData;
            Intent intent = this.mSettingsIntent;
            ActivityInfo activityInfo = resolveActivityAsUser.activityInfo;
            intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
            if (bundle != null
                    && bundle.containsKey("android.settings.metadata.USAGE_ACCESS_REASON")) {
                this.mSwitchPref.setSummary(
                        bundle.getString("android.settings.metadata.USAGE_ACCESS_REASON"));
            }
        }
        return true;
    }

    public final void setHasAccess(
            FragmentActivity fragmentActivity,
            String str,
            int i,
            AppStateUsageBridge.UsageState usageState,
            boolean z) {
        this.mAppOpsManager = (AppOpsManager) fragmentActivity.getSystemService("appops");
        this.mDpm =
                (DevicePolicyManager) fragmentActivity.getSystemService(DevicePolicyManager.class);
        if (usageState.isPermissible() && this.mDpm.isProfileOwnerApp(this.mPackageName)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.P.mIconId = android.R.drawable.ic_feedback_indicator;
            builder.setTitle(android.R.string.dialog_alert_title);
            builder.setMessage(R.string.work_profile_usage_access_warning);
            builder.setPositiveButton(R.string.okay, (DialogInterface.OnClickListener) null);
            builder.show();
        }
        logSpecialPermissionChange(z, str);
        int i2 = !z ? 1 : 0;
        if (doesAnyPermissionMatch(
                "android.permission.PACKAGE_USAGE_STATS",
                usageState.packageInfo.requestedPermissions)) {
            this.mAppOpsManager.setMode(43, i, str, i2);
        }
        if (doesAnyPermissionMatch(
                "android.permission.LOADER_USAGE_STATS",
                usageState.packageInfo.requestedPermissions)) {
            this.mAppOpsManager.setMode(95, i, str, i2);
        }
    }
}
