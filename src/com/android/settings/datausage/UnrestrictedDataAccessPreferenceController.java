package com.android.settings.datausage;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.UserHandle;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.applications.AppStateBaseBridge;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UnrestrictedDataAccessPreferenceController extends BasePreferenceController
        implements LifecycleObserver,
                OnStart,
                OnStop,
                OnDestroy,
                ApplicationsState.Callbacks,
                AppStateBaseBridge.Callback,
                Preference.OnPreferenceChangeListener {
    private static String KEY_DESCRIPTION = "unrestricted_data_access_description";
    private final ApplicationsState mApplicationsState;
    private final DataSaverBackend mDataSaverBackend;
    private final AppStateDataUsageBridge mDataUsageBridge;
    private boolean mExtraLoaded;
    private ApplicationsState.AppFilter mFilter;
    private DashboardFragment mParentFragment;
    private PreferenceScreen mScreen;
    private ApplicationsState.Session mSession;
    private boolean mSort;

    public UnrestrictedDataAccessPreferenceController(Context context, String str) {
        super(context, str);
        ApplicationsState applicationsState =
                ApplicationsState.getInstance((Application) context.getApplicationContext());
        this.mApplicationsState = applicationsState;
        DataSaverBackend dataSaverBackend = new DataSaverBackend(context);
        this.mDataSaverBackend = dataSaverBackend;
        this.mDataUsageBridge =
                new AppStateDataUsageBridge(applicationsState, this, dataSaverBackend);
    }

    private void removeUselessPrefs(Set<String> set) {
        int preferenceCount = this.mScreen.getPreferenceCount();
        if (preferenceCount > 0) {
            for (int i = preferenceCount - 1; i >= 0; i--) {
                Preference preference = this.mScreen.getPreference(i);
                String key = preference.getKey();
                if ((set.isEmpty() || !set.contains(key))
                        && !preference.getKey().equals(KEY_DESCRIPTION)) {
                    this.mScreen.removePreference(preference);
                }
            }
        }
    }

    public static boolean shouldAddPreference(ApplicationsState.AppEntry appEntry) {
        return appEntry != null && UserHandle.isApp(appEntry.info.uid);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mScreen = preferenceScreen;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_data_saver) ? 1 : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    public void logSpecialPermissionChange(boolean z, String str) {
        int i = z ? 781 : 782;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getMetricsFeatureProvider().action(this.mContext, i, str);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        this.mDataUsageBridge.release();
    }

    @Override // com.android.settings.applications.AppStateBaseBridge.Callback
    public void onExtraInfoUpdated() {
        this.mExtraLoaded = true;
        rebuild();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        if (!(preference instanceof UnrestrictedDataAccessPreference)) {
            return false;
        }
        Boolean bool = Boolean.TRUE;
        LoggingHelper.insertEventLogging(7102, 7106, obj == bool ? 1 : 0);
        UnrestrictedDataAccessPreference unrestrictedDataAccessPreference =
                (UnrestrictedDataAccessPreference) preference;
        boolean z = obj == bool;
        logSpecialPermissionChange(z, unrestrictedDataAccessPreference.mEntry.info.packageName);
        DataSaverBackend dataSaverBackend = this.mDataSaverBackend;
        ApplicationInfo applicationInfo = unrestrictedDataAccessPreference.mEntry.info;
        dataSaverBackend.setIsAllowlisted(applicationInfo.uid, applicationInfo.packageName, z);
        AppStateDataUsageBridge.DataUsageState dataUsageState =
                unrestrictedDataAccessPreference.mDataUsageState;
        if (dataUsageState != null) {
            dataUsageState.isDataSaverAllowlisted = z;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x009b  */
    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onRebuildComplete(
            java.util.ArrayList<com.android.settingslib.applications.ApplicationsState.AppEntry>
                    r12) {
        /*
            Method dump skipped, instructions count: 267
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.datausage.UnrestrictedDataAccessPreferenceController.onRebuildComplete(java.util.ArrayList):void");
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mDataUsageBridge.resume(true);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mDataUsageBridge.pause();
    }

    public void rebuild() {
        if (this.mExtraLoaded) {
            this.mSession.rebuild(this.mFilter, ApplicationsState.ALPHA_COMPARATOR, true);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setFilter(ApplicationsState.AppFilter appFilter) {
        this.mFilter = appFilter;
    }

    public void setParentFragment(DashboardFragment dashboardFragment) {
        this.mParentFragment = dashboardFragment;
    }

    public void setSession(Lifecycle lifecycle) {
        this.mSession = this.mApplicationsState.newSession(this, lifecycle);
    }

    public void setSort(boolean z) {
        this.mSort = z;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onAllSizesComputed() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onLauncherInfoChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onLoadEntriesCompleted() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onPackageIconChanged() {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onPackageListChanged() {}

    public /* bridge */ /* synthetic */ void onDisabledAppCheckCompleted(Boolean bool) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onPackageSizeChanged(String str) {}

    @Override // com.android.settingslib.applications.ApplicationsState.Callbacks
    public void onRunningStateChanged(boolean z) {}
}
