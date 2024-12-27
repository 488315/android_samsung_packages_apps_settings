package com.android.settings.fuelgauge.batteryusage;

import android.app.Activity;
import android.os.BatteryUsageStats;
import android.os.Bundle;
import android.util.Log;

import androidx.loader.app.LoaderManager;
import androidx.loader.app.LoaderManagerImpl;
import androidx.loader.content.Loader;

import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.fuelgauge.BatteryBroadcastReceiver;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PowerUsageBase extends DashboardFragment {
    static final String KEY_INCLUDE_HISTORY = "include_history";
    static final String KEY_REFRESH_TYPE = "refresh_type";
    public BatteryBroadcastReceiver mBatteryBroadcastReceiver;
    BatteryUsageStats mBatteryUsageStats;
    public boolean mIsBatteryPresent = true;
    final BatteryUsageStatsLoaderCallbacks mBatteryUsageStatsLoaderCallbacks =
            new BatteryUsageStatsLoaderCallbacks();

    public final void closeBatteryUsageStatsIfNeeded() {
        BatteryUsageStats batteryUsageStats = this.mBatteryUsageStats;
        if (batteryUsageStats == null) {
            return;
        }
        try {
            try {
                batteryUsageStats.close();
            } catch (Exception e) {
                Log.e("PowerUsageBase", "BatteryUsageStats.close() failed", e);
            }
        } finally {
            this.mBatteryUsageStats = null;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        BatteryBroadcastReceiver batteryBroadcastReceiver =
                new BatteryBroadcastReceiver(getContext());
        this.mBatteryBroadcastReceiver = batteryBroadcastReceiver;
        batteryBroadcastReceiver.mBatteryListener =
                new BatteryBroadcastReceiver
                        .OnBatteryChangedListener() { // from class:
                                                      // com.android.settings.fuelgauge.batteryusage.PowerUsageBase$$ExternalSyntheticLambda0
                    @Override // com.android.settings.fuelgauge.BatteryBroadcastReceiver.OnBatteryChangedListener
                    public final void onBatteryChanged(int i) {
                        PowerUsageBase powerUsageBase = PowerUsageBase.this;
                        if (i == 6) {
                            powerUsageBase.mIsBatteryPresent = false;
                        }
                        powerUsageBase.restartBatteryStatsLoader(i);
                    }
                };
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mBatteryBroadcastReceiver.register();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        BatteryBroadcastReceiver batteryBroadcastReceiver = this.mBatteryBroadcastReceiver;
        batteryBroadcastReceiver.mContext.unregisterReceiver(batteryBroadcastReceiver);
        closeBatteryUsageStatsIfNeeded();
    }

    public abstract void refreshUi(int i);

    public void restartBatteryStatsLoader(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_REFRESH_TYPE, i);
        bundle.putBoolean(KEY_INCLUDE_HISTORY, false);
        restartLoader(0, bundle, this.mBatteryUsageStatsLoaderCallbacks);
    }

    public final void restartLoader(
            int i, Bundle bundle, LoaderManager.LoaderCallbacks loaderCallbacks) {
        LoaderManagerImpl loaderManager = LoaderManager.getInstance(this);
        LoaderManagerImpl.LoaderViewModel loaderViewModel = loaderManager.mLoaderViewModel;
        if (loaderViewModel.mCreatingLoader) {
            throw new IllegalStateException("Called while creating a loader");
        }
        LoaderManagerImpl.LoaderInfo loaderInfo =
                (LoaderManagerImpl.LoaderInfo) loaderViewModel.mLoaders.get(i);
        Loader loader = loaderInfo != null ? loaderInfo.mLoader : null;
        if (loader == null || loader.mReset) {
            loaderManager.initLoader(i, bundle, loaderCallbacks);
        } else {
            loaderManager.restartLoader(i, bundle, loaderCallbacks);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BatteryUsageStatsLoaderCallbacks implements LoaderManager.LoaderCallbacks {
        public int mRefreshType;

        public BatteryUsageStatsLoaderCallbacks() {}

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final Loader onCreateLoader(int i, Bundle bundle) {
            this.mRefreshType = bundle.getInt(PowerUsageBase.KEY_REFRESH_TYPE);
            return new BatteryUsageStatsLoader(
                    PowerUsageBase.this.getContext(),
                    bundle.getBoolean(PowerUsageBase.KEY_INCLUDE_HISTORY));
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoadFinished(Loader loader, Object obj) {
            PowerUsageBase powerUsageBase = PowerUsageBase.this;
            powerUsageBase.closeBatteryUsageStatsIfNeeded();
            powerUsageBase.mBatteryUsageStats = (BatteryUsageStats) obj;
            powerUsageBase.refreshUi(this.mRefreshType);
        }

        @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
        public final void onLoaderReset(Loader loader) {}
    }
}
