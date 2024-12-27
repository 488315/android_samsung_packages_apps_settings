package com.android.settings.fuelgauge.batteryusage;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.fuelgauge.BatteryHeaderPreferenceController;
import com.android.settings.fuelgauge.BatteryInfo;
import com.android.settings.fuelgauge.BatteryInfoLoader;
import com.android.settings.fuelgauge.BatteryUtils;
import com.android.settings.fuelgauge.PowerUsageFeatureProvider;
import com.android.settings.fuelgauge.batterytip.BatteryTipLoader;
import com.android.settings.fuelgauge.batterytip.BatteryTipPreferenceController;
import com.android.settings.fuelgauge.batterytip.tips.BatteryTip;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.feature.SemFloatingFeature;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PowerUsageSummary extends PowerUsageBase
        implements BatteryTipPreferenceController.BatteryTipListener {
    static final String KEY_BATTERY_ERROR = "battery_help_message";
    static final String KEY_BATTERY_USAGE = "battery_usage_summary";
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.power_usage_summary);
    BatteryHeaderPreferenceController mBatteryHeaderPreferenceController;
    BatteryInfo mBatteryInfo;
    LoaderManager.LoaderCallbacks mBatteryInfoLoaderCallbacks;
    BatteryTipPreferenceController mBatteryTipPreferenceController;
    public final AnonymousClass2 mBatteryTipsCallbacks;
    Preference mBatteryUsagePreference;
    BatteryUtils mBatteryUtils;
    Preference mHelpPreference;
    boolean mNeedUpdateBatteryTip;
    PowerUsageFeatureProvider mPowerFeatureProvider;
    final ContentObserver mSettingsObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.android.settings.fuelgauge.batteryusage.PowerUsageSummary.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z, Uri uri) {
                    PowerUsageSummary.this.restartBatteryInfoLoader();
                }
            };

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.fuelgauge.batteryusage.PowerUsageSummary$2] */
    public PowerUsageSummary() {
        final int i = 0;
        this.mBatteryInfoLoaderCallbacks =
                new LoaderManager.LoaderCallbacks(
                        this) { // from class:
                                // com.android.settings.fuelgauge.batteryusage.PowerUsageSummary.2
                    public final /* synthetic */ PowerUsageSummary this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
                    public final Loader onCreateLoader(int i2, Bundle bundle) {
                        switch (i) {
                            case 0:
                                return new BatteryInfoLoader(this.this$0.getContext());
                            default:
                                PowerUsageSummary powerUsageSummary = this.this$0;
                                return new BatteryTipLoader(
                                        powerUsageSummary.getContext(),
                                        powerUsageSummary.mBatteryUsageStats);
                        }
                    }

                    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
                    public final void onLoadFinished(Loader loader, Object obj) {
                        switch (i) {
                            case 0:
                                BatteryInfo batteryInfo = (BatteryInfo) obj;
                                PowerUsageSummary powerUsageSummary = this.this$0;
                                powerUsageSummary.mBatteryHeaderPreferenceController
                                        .updateHeaderPreference(batteryInfo);
                                powerUsageSummary.mBatteryHeaderPreferenceController
                                        .updateHeaderByBatteryTips(
                                                powerUsageSummary.mBatteryTipPreferenceController
                                                        .getCurrentBatteryTip(),
                                                batteryInfo);
                                powerUsageSummary.mBatteryInfo = batteryInfo;
                                break;
                            default:
                                PowerUsageSummary powerUsageSummary2 = this.this$0;
                                powerUsageSummary2.mBatteryTipPreferenceController
                                        .updateBatteryTips((List) obj);
                                powerUsageSummary2.mBatteryHeaderPreferenceController
                                        .updateHeaderByBatteryTips(
                                                powerUsageSummary2.mBatteryTipPreferenceController
                                                        .getCurrentBatteryTip(),
                                                powerUsageSummary2.mBatteryInfo);
                                break;
                        }
                    }

                    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
                    public final void onLoaderReset(Loader loader) {
                        int i2 = i;
                    }

                    private final void
                            onLoaderReset$com$android$settings$fuelgauge$batteryusage$PowerUsageSummary$2(
                                    Loader loader) {}

                    private final void
                            onLoaderReset$com$android$settings$fuelgauge$batteryusage$PowerUsageSummary$3(
                                    Loader loader) {}
                };
        final int i2 = 1;
        this.mBatteryTipsCallbacks =
                new LoaderManager.LoaderCallbacks(
                        this) { // from class:
                                // com.android.settings.fuelgauge.batteryusage.PowerUsageSummary.2
                    public final /* synthetic */ PowerUsageSummary this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
                    public final Loader onCreateLoader(int i22, Bundle bundle) {
                        switch (i2) {
                            case 0:
                                return new BatteryInfoLoader(this.this$0.getContext());
                            default:
                                PowerUsageSummary powerUsageSummary = this.this$0;
                                return new BatteryTipLoader(
                                        powerUsageSummary.getContext(),
                                        powerUsageSummary.mBatteryUsageStats);
                        }
                    }

                    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
                    public final void onLoadFinished(Loader loader, Object obj) {
                        switch (i2) {
                            case 0:
                                BatteryInfo batteryInfo = (BatteryInfo) obj;
                                PowerUsageSummary powerUsageSummary = this.this$0;
                                powerUsageSummary.mBatteryHeaderPreferenceController
                                        .updateHeaderPreference(batteryInfo);
                                powerUsageSummary.mBatteryHeaderPreferenceController
                                        .updateHeaderByBatteryTips(
                                                powerUsageSummary.mBatteryTipPreferenceController
                                                        .getCurrentBatteryTip(),
                                                batteryInfo);
                                powerUsageSummary.mBatteryInfo = batteryInfo;
                                break;
                            default:
                                PowerUsageSummary powerUsageSummary2 = this.this$0;
                                powerUsageSummary2.mBatteryTipPreferenceController
                                        .updateBatteryTips((List) obj);
                                powerUsageSummary2.mBatteryHeaderPreferenceController
                                        .updateHeaderByBatteryTips(
                                                powerUsageSummary2.mBatteryTipPreferenceController
                                                        .getCurrentBatteryTip(),
                                                powerUsageSummary2.mBatteryInfo);
                                break;
                        }
                    }

                    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
                    public final void onLoaderReset(Loader loader) {
                        int i22 = i2;
                    }

                    private final void
                            onLoaderReset$com$android$settings$fuelgauge$batteryusage$PowerUsageSummary$2(
                                    Loader loader) {}

                    private final void
                            onLoaderReset$com$android$settings$fuelgauge$batteryusage$PowerUsageSummary$3(
                                    Loader loader) {}
                };
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "PowerUsageSummary";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1263;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.power_usage_summary;
    }

    public void initFeatureProvider() {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mPowerFeatureProvider = featureFactoryImpl.getPowerUsageFeatureProvider();
    }

    public void initPreference() {
        Preference findPreference = findPreference(KEY_BATTERY_USAGE);
        this.mBatteryUsagePreference = findPreference;
        findPreference.setSummary(getString(R.string.advanced_battery_preference_summary));
        Preference preference = this.mBatteryUsagePreference;
        this.mPowerFeatureProvider.getClass();
        preference.setVisible(true);
        Preference findPreference2 = findPreference(KEY_BATTERY_ERROR);
        this.mHelpPreference = findPreference2;
        findPreference2.setVisible(false);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        this.mBatteryHeaderPreferenceController =
                (BatteryHeaderPreferenceController) use(BatteryHeaderPreferenceController.class);
        BatteryTipPreferenceController batteryTipPreferenceController =
                (BatteryTipPreferenceController) use(BatteryTipPreferenceController.class);
        this.mBatteryTipPreferenceController = batteryTipPreferenceController;
        batteryTipPreferenceController.setActivity(settingsActivity);
        this.mBatteryTipPreferenceController.setFragment(this);
        this.mBatteryTipPreferenceController.setBatteryTipListener(
                new BatteryTipPreferenceController
                        .BatteryTipListener() { // from class:
                                                // com.android.settings.fuelgauge.batteryusage.PowerUsageSummary$$ExternalSyntheticLambda0
                    @Override // com.android.settings.fuelgauge.batterytip.BatteryTipPreferenceController.BatteryTipListener
                    public final void onBatteryTipHandled(BatteryTip batteryTip) {
                        PowerUsageSummary.this.restartBatteryTipLoader();
                    }
                });
    }

    @Override // com.android.settings.fuelgauge.batterytip.BatteryTipPreferenceController.BatteryTipListener
    public final void onBatteryTipHandled(BatteryTip batteryTip) {
        restartBatteryTipLoader();
    }

    @Override // com.android.settings.fuelgauge.batteryusage.PowerUsageBase,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String string =
                SemFloatingFeature.getInstance()
                        .getString(
                                "SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME",
                                "com.samsung.android.lool");
        Intent intent = new Intent("com.samsung.android.sm.ACTION_POWER_USAGE_SUMMARY");
        intent.addFlags(268435456);
        intent.setPackage(string);
        try {
            getContext().startActivity(intent);
            finish();
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        getContentResolver().unregisterContentObserver(this.mSettingsObserver);
        super.onPause();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("battery_estimates_last_update_time"),
                        false,
                        this.mSettingsObserver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.mBatteryTipPreferenceController.saveInstanceState(bundle);
    }

    @Override // com.android.settings.fuelgauge.batteryusage.PowerUsageBase
    public final void refreshUi(int i) {
        if (getContext() != null && this.mIsBatteryPresent) {
            if (!this.mNeedUpdateBatteryTip || i == 1) {
                this.mNeedUpdateBatteryTip = true;
            } else {
                restartBatteryTipLoader();
            }
            restartBatteryInfoLoader();
        }
    }

    public void restartBatteryInfoLoader() {
        if (getContext() != null && this.mIsBatteryPresent) {
            restartLoader(1, Bundle.EMPTY, this.mBatteryInfoLoaderCallbacks);
        }
    }

    @Override // com.android.settings.fuelgauge.batteryusage.PowerUsageBase
    public final void restartBatteryStatsLoader(int i) {
        super.restartBatteryStatsLoader(i);
        if (this.mIsBatteryPresent) {
            this.mBatteryHeaderPreferenceController.quickUpdateHeaderPreference();
        }
    }

    public void restartBatteryTipLoader() {
        restartLoader(2, Bundle.EMPTY, this.mBatteryTipsCallbacks);
    }

    public void updateBatteryTipFlag(Bundle bundle) {
        this.mNeedUpdateBatteryTip =
                bundle == null || this.mBatteryTipPreferenceController.needUpdate();
    }
}
