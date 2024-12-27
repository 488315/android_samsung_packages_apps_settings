package com.android.settings.applications.appinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.applications.ApplicationFeatureProvider;
import com.android.settings.core.LiveDataController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.MinorModeUtils;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.util.SemLog;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TimeSpentInAppPreferenceController extends LiveDataController {
    static final Intent SEE_TIME_IN_APP_TEMPLATE =
            new Intent("android.settings.action.APP_USAGE_SETTINGS");
    private static final String TAG = "TimeSpentInAppPreferenceController";
    protected ApplicationsState.AppEntry mAppEntry;
    private final ApplicationFeatureProvider mAppFeatureProvider;
    private Intent mIntent;
    private final PackageManager mPackageManager;
    private String mPackageName;
    protected AppInfoDashboardFragment mParent;

    public TimeSpentInAppPreferenceController(Context context, String str) {
        super(context, str);
        this.mPackageManager = context.getPackageManager();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mAppFeatureProvider = featureFactoryImpl.getApplicationFeatureProvider();
        this.mSummary = null;
    }

    private boolean isSystemApp(ResolveInfo resolveInfo) {
        String string =
                SemFloatingFeature.getInstance()
                        .getString(
                                "SEC_FLOATING_FEATURE_DWB_CONFIG_UNSUSPENDABLE_PACKAGE_NAME",
                                ApnSettings.MVNO_NONE);
        SemLog.d(TAG, "systemAppsName = " + string);
        if (string != ApnSettings.MVNO_NONE) {
            for (String str : string.split(";")) {
                if (this.mPackageName.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$displayPreference$0(Preference preference) {
        LoggingHelper.insertEventLogging(getMetricsCategory(), 60102);
        return false;
    }

    @Override // com.android.settings.core.LiveDataController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference != null) {
            findPreference.setIntent(this.mIntent);
            findPreference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.applications.appinfo.TimeSpentInAppPreferenceController$$ExternalSyntheticLambda0
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference) {
                            boolean lambda$displayPreference$0;
                            lambda$displayPreference$0 =
                                    TimeSpentInAppPreferenceController.this
                                            .lambda$displayPreference$0(preference);
                            return lambda$displayPreference$0;
                        }
                    });
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        List<ResolveInfo> queryIntentActivities;
        if (!TextUtils.isEmpty(this.mPackageName)
                && !MinorModeUtils.isCHNMinorModeRestrictDateTimeModify(this.mContext)
                && (queryIntentActivities =
                                this.mPackageManager.queryIntentActivities(this.mIntent, 0))
                        != null
                && !queryIntentActivities.isEmpty()) {
            if (TextUtils.equals(
                    this.mPackageName, this.mPackageManager.getWellbeingPackageName())) {
                SemLog.d(TAG, "The executed app info is the wellbeing app.");
                return 3;
            }
            if (this.mPackageManager.semGetUnsuspendablePackages(new String[] {this.mPackageName})
                            .length
                    == 1) {
                SemLog.d(TAG, "The executed app info is unsuspendablePackage");
                return 3;
            }
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            Iterator<ResolveInfo> it =
                    this.mPackageManager.queryIntentActivities(intent, 0).iterator();
            while (it.hasNext()) {
                if (this.mPackageName.equals(
                        it.next().getComponentInfo().applicationInfo.packageName)) {
                    SemLog.d(TAG, "The executed app info have Intent.CATEGORY_HOME");
                    return 3;
                }
            }
            Intent intent2 = new Intent("android.intent.action.MAIN");
            intent2.addCategory("android.intent.category.LAUNCHER");
            List<ResolveInfo> queryIntentActivities2 =
                    this.mPackageManager.queryIntentActivities(intent2, 128);
            if (queryIntentActivities2 != null && !queryIntentActivities2.isEmpty()) {
                for (ResolveInfo resolveInfo : queryIntentActivities2) {
                    if (this.mPackageName.equals(resolveInfo.activityInfo.packageName)) {
                        if (isSystemApp(resolveInfo)) {
                            return 3;
                        }
                        return this.mPackageManager.getPackageUid(this.mPackageName, 0) == 1000
                                ? 3
                                : 0;
                    }
                }
            }
        }
        return 3;
    }

    @Override // com.android.settings.core.LiveDataController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.LiveDataController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.LiveDataController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.LiveDataController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.LiveDataController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.LiveDataController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.LiveDataController
    public CharSequence getSummaryTextInBackground() {
        this.mAppFeatureProvider.getClass();
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settings.core.LiveDataController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.core.LiveDataController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.LiveDataController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.LiveDataController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.LiveDataController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.LiveDataController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.LiveDataController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.LiveDataController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setPackageName(String str) {
        this.mPackageName = str;
        Intent putExtra =
                new Intent(SEE_TIME_IN_APP_TEMPLATE)
                        .setPackage(this.mPackageManager.getWellbeingPackageName())
                        .putExtra("android.intent.extra.PACKAGE_NAME", this.mPackageName);
        this.mIntent = putExtra;
        putExtra.putExtra("from_settings", true);
    }

    public void setParentFragment(AppInfoDashboardFragment appInfoDashboardFragment) {
        this.mParent = appInfoDashboardFragment;
        this.mAppEntry = appInfoDashboardFragment.mAppEntry;
    }

    @Override // com.android.settings.core.LiveDataController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setEnabled(AppUtils.isAppInstalled(this.mContext, this.mPackageName));
    }

    @Override // com.android.settings.core.LiveDataController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
