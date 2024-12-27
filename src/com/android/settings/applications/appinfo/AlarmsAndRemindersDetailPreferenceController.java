package com.android.settings.applications.appinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import androidx.preference.Preference;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.applications.AppStateAlarmsAndRemindersBridge;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AlarmsAndRemindersDetailPreferenceController extends AppInfoPreferenceControllerBase {
    private String mPackageName;

    public AlarmsAndRemindersDetailPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return isCandidate() ? 0 : 2;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase
    public Class<? extends SettingsPreferenceFragment> getDetailFragmentClass() {
        return AlarmsAndRemindersDetails.class;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    public CharSequence getPreferenceSummary() {
        Context context = this.mContext;
        ApplicationsState.AppEntry appEntry = this.mParent.mAppEntry;
        AppStateAlarmsAndRemindersBridge appStateAlarmsAndRemindersBridge =
                new AppStateAlarmsAndRemindersBridge(context, null, null);
        ApplicationInfo applicationInfo = appEntry.info;
        return context.getString(
                appStateAlarmsAndRemindersBridge
                                .createPermissionState(
                                        applicationInfo.uid, applicationInfo.packageName)
                                .isAllowed()
                        ? R.string.app_permission_summary_allowed
                        : R.string.app_permission_summary_not_allowed);
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public boolean isCandidate() {
        PackageInfo packageInfo = this.mParent.mPackageInfo;
        if (packageInfo == null) {
            return false;
        }
        return new AppStateAlarmsAndRemindersBridge(this.mContext, null, null)
                .createPermissionState(packageInfo.applicationInfo.uid, this.mPackageName)
                .shouldBeVisible();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.applications.appinfo.AppInfoDashboardFragment.Callback
    public void refreshUi() {
        super.refreshUi();
        updateState(this.mPreference);
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setPackageName(String str) {
        this.mPackageName = str;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        preference.setSummary(getPreferenceSummary());
        if (preference instanceof SecPreference) {
            SecPreferenceUtils.applySummaryColor((SecPreference) preference, true);
        }
        preference.setEnabled(
                AppUtils.isAppInstalled(this.mContext, this.mAppEntry.info.packageName));
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
