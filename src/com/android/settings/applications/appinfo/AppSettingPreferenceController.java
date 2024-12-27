package com.android.settings.applications.appinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.applications.ApplicationsState;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppSettingPreferenceController extends AppInfoPreferenceControllerBase {
    private String mPackageName;

    public AppSettingPreferenceController(Context context, String str) {
        super(context, str);
    }

    private Intent resolveIntent(Intent intent) {
        ResolveInfo resolveActivity = this.mContext.getPackageManager().resolveActivity(intent, 0);
        if (resolveActivity == null) {
            return null;
        }
        Intent intent2 = new Intent(intent.getAction());
        ActivityInfo activityInfo = resolveActivity.activityInfo;
        return intent2.setClassName(activityInfo.packageName, activityInfo.name);
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (TextUtils.isEmpty(this.mPackageName)
                        || this.mParent == null
                        || resolveIntent(
                                        new Intent("android.intent.action.APPLICATION_PREFERENCES")
                                                .setPackage(this.mPackageName))
                                == null)
                ? 2
                : 0;
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
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        Intent resolveIntent;
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())
                || (resolveIntent =
                                resolveIntent(
                                        new Intent("android.intent.action.APPLICATION_PREFERENCES")
                                                .setPackage(this.mPackageName)))
                        == null) {
            return false;
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        SettingsMetricsFeatureProvider metricsFeatureProvider =
                featureFactoryImpl.getMetricsFeatureProvider();
        this.mParent.getClass();
        metricsFeatureProvider.action(0, 1017, 20, 0, null);
        this.mContext.startActivity(resolveIntent);
        return true;
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
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public AppSettingPreferenceController setPackageName(String str) {
        this.mPackageName = str;
        return this;
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        ApplicationsState.AppEntry appEntry;
        super.updateState(preference);
        AppInfoDashboardFragment appInfoDashboardFragment = this.mParent;
        if (appInfoDashboardFragment == null
                || (appEntry = appInfoDashboardFragment.mAppEntry) == null) {
            return;
        }
        String str = appEntry.label;
        if (preference != null) {
            preference.setTitle(this.mContext.getString(R.string.app_settings_link, str));
        }
    }

    @Override // com.android.settings.applications.appinfo.AppInfoPreferenceControllerBase,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
