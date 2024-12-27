package com.android.settings.applications.appinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.BatteryUsageStats;
import android.os.Bundle;
import android.os.UidBatteryConsumer;
import android.os.UserBatteryConsumer;
import android.os.UserHandle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.fuelgauge.AdvancedPowerUsageDetail;
import com.android.settings.fuelgauge.BatteryUtils;
import com.android.settings.fuelgauge.batteryusage.BatteryChartPreferenceController;
import com.android.settings.fuelgauge.batteryusage.BatteryDiffEntry;
import com.android.settings.fuelgauge.batteryusage.BatteryEntry;
import com.android.settings.fuelgauge.batteryusage.BatteryUsageStatsLoader;
import com.android.settingslib.Utils;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppBatteryPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final String KEY_BATTERY = "battery";
    private static final String TAG = "AppBatteryPreferenceController";
    private boolean mBatteryDiffEntriesLoaded;
    BatteryDiffEntry mBatteryDiffEntry;
    private String mBatteryPercent;
    BatteryUsageStats mBatteryUsageStats;
    private boolean mBatteryUsageStatsLoaded;
    final BatteryUsageStatsLoaderCallbacks mBatteryUsageStatsLoaderCallbacks;
    BatteryUtils mBatteryUtils;
    private final String mPackageName;
    final AppInfoDashboardFragment mParent;
    private Preference mPreference;
    private final int mUid;
    UidBatteryConsumer mUidBatteryConsumer;
    private final int mUserId;

    public AppBatteryPreferenceController(
            Context context,
            AppInfoDashboardFragment appInfoDashboardFragment,
            String str,
            int i,
            Lifecycle lifecycle) {
        super(context, KEY_BATTERY);
        this.mBatteryUsageStatsLoaderCallbacks = new BatteryUsageStatsLoaderCallbacks();
        this.mBatteryUsageStatsLoaded = false;
        this.mBatteryDiffEntriesLoaded = false;
        this.mParent = appInfoDashboardFragment;
        this.mBatteryUtils = BatteryUtils.getInstance(this.mContext);
        this.mPackageName = str;
        this.mUid = i;
        this.mUserId = this.mContext.getUserId();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeBatteryUsageStats() {
        BatteryUsageStats batteryUsageStats = this.mBatteryUsageStats;
        if (batteryUsageStats != null) {
            try {
                try {
                    batteryUsageStats.close();
                } catch (Exception e) {
                    Log.e(TAG, "BatteryUsageStats.close() failed", e);
                }
            } finally {
                this.mBatteryUsageStats = null;
            }
        }
    }

    private void loadBatteryDiffEntries() {
        new AsyncTask() { // from class:
                          // com.android.settings.applications.appinfo.AppBatteryPreferenceController.1
            @Override // android.os.AsyncTask
            public final Object doInBackground(Object[] objArr) {
                if (AppBatteryPreferenceController.this.mPackageName == null) {
                    return null;
                }
                BatteryDiffEntry appBatteryUsageData =
                        BatteryChartPreferenceController.getAppBatteryUsageData(
                                ((AbstractPreferenceController) AppBatteryPreferenceController.this)
                                        .mContext,
                                AppBatteryPreferenceController.this.mUserId,
                                AppBatteryPreferenceController.this.mPackageName);
                Log.d(
                        AppBatteryPreferenceController.TAG,
                        "loadBatteryDiffEntries():\n" + appBatteryUsageData);
                return appBatteryUsageData;
            }

            @Override // android.os.AsyncTask
            public final void onPostExecute(Object obj) {
                AppBatteryPreferenceController.this.mBatteryDiffEntry = (BatteryDiffEntry) obj;
            }
        }.execute(new Void[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLoadFinished() {
        PackageInfo packageInfo;
        BatteryUsageStats batteryUsageStats = this.mBatteryUsageStats;
        if (batteryUsageStats == null || (packageInfo = this.mParent.mPackageInfo) == null) {
            return;
        }
        this.mUidBatteryConsumer =
                findTargetUidBatteryConsumer(batteryUsageStats, packageInfo.applicationInfo.uid);
        if (this.mParent.getActivity() != null) {
            updateBattery();
        }
    }

    private void updateBattery() {
        this.mBatteryUsageStatsLoaded = true;
        this.mPreference.setEnabled(true);
        if (!AppUtils.isAppInstalled(this.mParent.mAppEntry)) {
            this.mPreference.setEnabled(false);
        }
        if (!isBatteryStatsAvailable()) {
            this.mPreference.setSummary(this.mContext.getString(R.string.no_battery_summary));
            return;
        }
        BatteryUtils batteryUtils = this.mBatteryUtils;
        double consumedPower = this.mUidBatteryConsumer.getConsumedPower();
        double consumedPower2 = this.mBatteryUsageStats.getConsumedPower();
        int dischargePercentage = this.mBatteryUsageStats.getDischargePercentage();
        batteryUtils.getClass();
        this.mPreference.setSummary(
                this.mContext.getString(
                        R.string.sec_battery_summary,
                        Integer.valueOf(
                                (int)
                                        (consumedPower2 != 0.0d
                                                ? (consumedPower / consumedPower2)
                                                        * dischargePercentage
                                                : 0.0d))));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = findPreference;
        findPreference.setEnabled(false);
        if (AppUtils.isAppInstalled(this.mParent.mAppEntry)) {
            loadBatteryDiffEntries();
        } else {
            this.mPreference.setSummary(ApnSettings.MVNO_NONE);
        }
    }

    public UidBatteryConsumer findTargetUidBatteryConsumer(
            BatteryUsageStats batteryUsageStats, int i) {
        List uidBatteryConsumers = batteryUsageStats.getUidBatteryConsumers();
        int size = uidBatteryConsumers.size();
        for (int i2 = 0; i2 < size; i2++) {
            UidBatteryConsumer uidBatteryConsumer =
                    (UidBatteryConsumer) uidBatteryConsumers.get(i2);
            if (uidBatteryConsumer.getUid() == i) {
                return uidBatteryConsumer;
            }
        }
        Log.e(TAG, "uid " + i + " is not found in BatteryConsumerEntries");
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_app_info_settings_battery)
                ? 0
                : 2;
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
        if (!KEY_BATTERY.equals(preference.getKey())) {
            return false;
        }
        if (this.mBatteryDiffEntry != null) {
            Log.i(TAG, "handlePreferenceTreeClick():\n" + this.mBatteryDiffEntry);
            FragmentActivity activity = this.mParent.getActivity();
            this.mParent.getClass();
            BatteryDiffEntry batteryDiffEntry = this.mBatteryDiffEntry;
            AdvancedPowerUsageDetail.startBatteryDetailPage(
                    activity,
                    20,
                    batteryDiffEntry,
                    Utils.formatPercentage(batteryDiffEntry.mPercentage, true),
                    null,
                    false,
                    null,
                    null);
            return true;
        }
        if (isBatteryStatsAvailable()) {
            try {
                Context context = this.mContext;
                UidBatteryConsumer uidBatteryConsumer = this.mUidBatteryConsumer;
                BatteryEntry batteryEntry =
                        new BatteryEntry(
                                context,
                                uidBatteryConsumer,
                                false,
                                uidBatteryConsumer.getUid(),
                                null,
                                this.mPackageName);
                int i = batteryEntry.mUid;
                Log.i(
                        TAG,
                        "Battery consumer available, launch : "
                                + batteryEntry.mDefaultPackageName
                                + " | uid : "
                                + i
                                + " with BatteryEntry data");
                FragmentActivity activity2 = this.mParent.getActivity();
                AppInfoDashboardFragment appInfoDashboardFragment = this.mParent;
                String formatPercentage = Utils.formatPercentage(0);
                AdvancedPowerUsageDetail.LaunchBatteryDetailPageArgs launchBatteryDetailPageArgs =
                        new AdvancedPowerUsageDetail.LaunchBatteryDetailPageArgs();
                launchBatteryDetailPageArgs.mUsagePercent = formatPercentage;
                launchBatteryDetailPageArgs.mPackageName = batteryEntry.mDefaultPackageName;
                launchBatteryDetailPageArgs.mAppLabel = batteryEntry.mName;
                launchBatteryDetailPageArgs.mUid = i;
                launchBatteryDetailPageArgs.mIconId = batteryEntry.mIconId;
                launchBatteryDetailPageArgs.mConsumedPower = (int) batteryEntry.mConsumedPower;
                launchBatteryDetailPageArgs.mIsUserEntry =
                        uidBatteryConsumer instanceof UserBatteryConsumer;
                launchBatteryDetailPageArgs.mShowTimeInformation = false;
                appInfoDashboardFragment.getClass();
                AdvancedPowerUsageDetail.startBatteryDetailPage(
                        activity2, 20, launchBatteryDetailPageArgs);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                return false;
            }
        } else {
            Log.i(TAG, "Launch : " + this.mPackageName + " with package name");
            AdvancedPowerUsageDetail.startBatteryDetailPage(
                    this.mParent.getActivity(),
                    this.mParent,
                    this.mPackageName,
                    UserHandle.CURRENT);
        }
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public boolean isBatteryStatsAvailable() {
        return this.mUidBatteryConsumer != null;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mParent.getLoaderManager().destroyLoader(5);
        closeBatteryUsageStats();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        this.mParent
                .getLoaderManager()
                .restartLoader(5, Bundle.EMPTY, this.mBatteryUsageStatsLoaderCallbacks);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updateBatteryWithDiffEntry() {
        BatteryDiffEntry batteryDiffEntry = this.mBatteryDiffEntry;
        if (batteryDiffEntry == null || batteryDiffEntry.mConsumePower <= 0.0d) {
            this.mPreference.setSummary(this.mContext.getString(R.string.no_battery_summary));
        } else {
            String formatPercentage = Utils.formatPercentage(batteryDiffEntry.mPercentage, true);
            this.mBatteryPercent = formatPercentage;
            this.mPreference.setSummary(
                    this.mContext.getString(R.string.battery_summary, formatPercentage));
        }
        this.mBatteryDiffEntriesLoaded = true;
        this.mPreference.setEnabled(this.mBatteryUsageStatsLoaded);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference instanceof SecPreference) {
            SecPreference secPreference = (SecPreference) preference;
            secPreference.getClass();
            SecPreferenceUtils.applySummaryColor(secPreference, false);
        }
        if (AppUtils.isAppInstalled(this.mContext, this.mPackageName)) {
            preference.setEnabled(true);
        } else {
            preference.setEnabled(false);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BatteryUsageStatsLoaderCallbacks implements LoaderManager.LoaderCallbacks {
        public BatteryUsageStatsLoaderCallbacks() {}

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final Loader onCreateLoader(int i, Bundle bundle) {
            return new BatteryUsageStatsLoader(
                    ((AbstractPreferenceController) AppBatteryPreferenceController.this).mContext,
                    false);
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoadFinished(Loader loader, Object obj) {
            AppBatteryPreferenceController appBatteryPreferenceController =
                    AppBatteryPreferenceController.this;
            appBatteryPreferenceController.closeBatteryUsageStats();
            appBatteryPreferenceController.mBatteryUsageStats = (BatteryUsageStats) obj;
            appBatteryPreferenceController.onLoadFinished();
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoaderReset(Loader loader) {}
    }
}
