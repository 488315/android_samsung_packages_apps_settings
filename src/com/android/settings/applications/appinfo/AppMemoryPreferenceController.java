package com.android.settings.applications.appinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.applications.ProcStatsData;
import com.android.settings.applications.ProcStatsEntry;
import com.android.settings.applications.ProcStatsPackageEntry;
import com.android.settings.applications.ProcessStatsBase;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.development.DevelopmentSettingsEnabler;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settingslib.applications.AppFileSizeFormatter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppMemoryPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnResume {
    private static final String KEY_MEMORY = "memory";
    private final AppInfoDashboardFragment mParent;
    private SecPreference mPreference;
    private ProcStatsPackageEntry mStats;
    private ProcStatsData mStatsManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MemoryUpdater extends AsyncTask {
        public MemoryUpdater() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            PackageInfo packageInfo;
            FragmentActivity activity = AppMemoryPreferenceController.this.mParent.getActivity();
            if (activity == null
                    || (packageInfo = AppMemoryPreferenceController.this.mParent.mPackageInfo)
                            == null
                    || packageInfo.applicationInfo == null) {
                return null;
            }
            if (AppMemoryPreferenceController.this.mStatsManager == null) {
                AppMemoryPreferenceController.this.mStatsManager =
                        new ProcStatsData(activity, false);
                AppMemoryPreferenceController.this.mStatsManager.mDuration =
                        ProcessStatsBase.sDurations[0];
            }
            AppMemoryPreferenceController.this.mStatsManager.refreshStats(true);
            Iterator it = AppMemoryPreferenceController.this.mStatsManager.pkgEntries.iterator();
            while (it.hasNext()) {
                ProcStatsPackageEntry procStatsPackageEntry = (ProcStatsPackageEntry) it.next();
                Iterator it2 = procStatsPackageEntry.mEntries.iterator();
                while (it2.hasNext()) {
                    if (((ProcStatsEntry) it2.next()).mUid == packageInfo.applicationInfo.uid) {
                        procStatsPackageEntry.updateMetrics();
                        return procStatsPackageEntry;
                    }
                }
            }
            return null;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            ProcStatsPackageEntry procStatsPackageEntry = (ProcStatsPackageEntry) obj;
            if (AppMemoryPreferenceController.this.mParent.getActivity() == null) {
                return;
            }
            if (procStatsPackageEntry == null) {
                AppMemoryPreferenceController.this.mPreference.setEnabled(false);
                AppMemoryPreferenceController.this.mPreference.setSummary(
                        ((AbstractPreferenceController) AppMemoryPreferenceController.this)
                                .mContext
                                .getResources()
                                .getQuantityString(R.plurals.no_memory_use_summary, 3, 3));
                return;
            }
            AppMemoryPreferenceController.this.mStats = procStatsPackageEntry;
            AppMemoryPreferenceController.this.mPreference.setEnabled(true);
            AppMemoryPreferenceController.this.mPreference.setSummary(
                    ((AbstractPreferenceController) AppMemoryPreferenceController.this)
                            .mContext.getString(
                                    R.string.memory_use_summary,
                                    AppFileSizeFormatter.formatFileSize(
                                            1,
                                            (long)
                                                    (Math.max(
                                                                    procStatsPackageEntry
                                                                            .mRunWeight,
                                                                    procStatsPackageEntry.mBgWeight)
                                                            * AppMemoryPreferenceController.this
                                                                    .mStatsManager
                                                                    .mMemInfo
                                                                    .weightToRam),
                                            ((AbstractPreferenceController)
                                                            AppMemoryPreferenceController.this)
                                                    .mContext)));
        }
    }

    public AppMemoryPreferenceController(
            Context context,
            AppInfoDashboardFragment appInfoDashboardFragment,
            Lifecycle lifecycle) {
        super(context, KEY_MEMORY);
        this.mParent = appInfoDashboardFragment;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (SecPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (this.mContext.getResources().getBoolean(R.bool.config_show_app_info_settings_memory)) {
            return DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(this.mContext) ? 0 : 2;
        }
        return 3;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!KEY_MEMORY.equals(preference.getKey())) {
            return false;
        }
        ProcessStatsBase.launchMemoryDetail(
                (SettingsActivity) this.mParent.getActivity(),
                this.mStatsManager.mMemInfo,
                this.mStats);
        return true;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (isAvailable()) {
            new MemoryUpdater().execute(new Void[0]);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
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
}
