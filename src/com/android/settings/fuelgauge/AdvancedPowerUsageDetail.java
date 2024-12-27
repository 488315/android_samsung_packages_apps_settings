package com.android.settings.fuelgauge;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.applications.appinfo.AppButtonsPreferenceController;
import com.android.settings.applications.appinfo.ButtonActionDialogFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.fuelgauge.batteryusage.AppOptModeSharedPreferencesUtils;
import com.android.settings.fuelgauge.batteryusage.BatteryDiffEntry;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.Utils;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.core.instrumentation.Instrumentable;
import com.android.settingslib.widget.LayoutPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AdvancedPowerUsageDetail extends DashboardFragment
        implements ButtonActionDialogFragment.AppButtonsDialogListener,
                Preference.OnPreferenceClickListener,
                Preference.OnPreferenceChangeListener {
    PrimarySwitchPreference mAllowBackgroundUsagePreference;
    public AppButtonsPreferenceController mAppButtonsPreferenceController;
    ApplicationsState.AppEntry mAppEntry;
    BatteryOptimizeUtils mBatteryOptimizeUtils;
    LayoutPreference mHeaderPreference;
    StringBuilder mLogStringBuilder;
    public PowerUsageTimeController mPowerUsageTimeController;
    ApplicationsState mState;
    public final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    int mOptimizationMode = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LaunchBatteryDetailPageArgs {
        public String mAnomalyHintPrefKey;
        public String mAnomalyHintText;
        public String mAppLabel;
        public long mBackgroundTimeMs;
        public int mConsumedPower;
        public long mForegroundTimeMs;
        public int mIconId;
        public boolean mIsUserEntry;
        public String mPackageName;
        public long mScreenOnTimeMs;
        public boolean mShowTimeInformation;
        public String mSlotInformation;
        public int mUid;
        public String mUsagePercent;
    }

    public static void startBatteryDetailPage(
            Context context,
            int i,
            BatteryDiffEntry batteryDiffEntry,
            String str,
            String str2,
            boolean z,
            String str3,
            String str4) {
        LaunchBatteryDetailPageArgs launchBatteryDetailPageArgs = new LaunchBatteryDetailPageArgs();
        launchBatteryDetailPageArgs.mUsagePercent = str;
        launchBatteryDetailPageArgs.mPackageName = batteryDiffEntry.getPackageName();
        launchBatteryDetailPageArgs.mAppLabel = batteryDiffEntry.getAppLabel();
        launchBatteryDetailPageArgs.mSlotInformation = str2;
        launchBatteryDetailPageArgs.mUid = (int) batteryDiffEntry.mUid;
        launchBatteryDetailPageArgs.mIconId = batteryDiffEntry.getAppIconId();
        launchBatteryDetailPageArgs.mConsumedPower = (int) batteryDiffEntry.mConsumePower;
        launchBatteryDetailPageArgs.mShowTimeInformation = z;
        if (z) {
            launchBatteryDetailPageArgs.mForegroundTimeMs =
                    batteryDiffEntry.mForegroundUsageTimeInMs;
            launchBatteryDetailPageArgs.mBackgroundTimeMs =
                    batteryDiffEntry.mBackgroundUsageTimeInMs
                            + batteryDiffEntry.mForegroundServiceUsageTimeInMs;
            launchBatteryDetailPageArgs.mScreenOnTimeMs = batteryDiffEntry.mScreenOnTimeInMs;
            launchBatteryDetailPageArgs.mAnomalyHintPrefKey = str3;
            launchBatteryDetailPageArgs.mAnomalyHintText = str4;
        }
        launchBatteryDetailPageArgs.mIsUserEntry = batteryDiffEntry.mConsumerType == 2;
        startBatteryDetailPage(context, i, launchBatteryDetailPageArgs);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        Bundle arguments = getArguments();
        int i = arguments.getInt("extra_uid", 0);
        String string = arguments.getString("extra_package_name");
        this.mAppButtonsPreferenceController =
                new AppButtonsPreferenceController(
                        (SettingsActivity) getActivity(),
                        this,
                        getSettingsLifecycle(),
                        string,
                        this.mState,
                        0,
                        1);
        if (arguments.getBoolean("extra_show_time_info", false)) {
            PowerUsageTimeController powerUsageTimeController =
                    new PowerUsageTimeController(getContext());
            this.mPowerUsageTimeController = powerUsageTimeController;
            arrayList.add(powerUsageTimeController);
        }
        arrayList.add(this.mAppButtonsPreferenceController);
        arrayList.add(new AllowBackgroundPreferenceController(context, i, string));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AdvancedPowerDetail";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 53;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.power_usage_detail;
    }

    @Override // com.android.settings.applications.appinfo.ButtonActionDialogFragment.AppButtonsDialogListener
    public final void handleDialogClick(int i) {
        AppButtonsPreferenceController appButtonsPreferenceController =
                this.mAppButtonsPreferenceController;
        if (appButtonsPreferenceController != null) {
            appButtonsPreferenceController.handleDialogClick(i);
        }
    }

    public void initFooter() {
        Context context = getContext();
        this.mAllowBackgroundUsagePreference.setSummary(
                this.mBatteryOptimizeUtils.isDisabledForOptimizeModeOnly()
                        ? context.getString(
                                R.string.manager_battery_usage_footer_limited,
                                context.getString(R.string.manager_battery_usage_optimized_only))
                        : this.mBatteryOptimizeUtils.isSystemOrDefaultApp()
                                ? context.getString(
                                        R.string.manager_battery_usage_footer_limited,
                                        context.getString(
                                                R.string.manager_battery_usage_unrestricted_only))
                                : context.getString(
                                        R.string
                                                .manager_battery_usage_allow_background_usage_summary));
    }

    public void initHeader() {
        View findViewById = this.mHeaderPreference.mRootView.findViewById(R.id.entity_header);
        FragmentActivity activity = getActivity();
        Bundle arguments = getArguments();
        EntityHeaderController entityHeaderController =
                new EntityHeaderController(activity, this, findViewById);
        entityHeaderController.mAction1 = 0;
        entityHeaderController.mAction2 = 0;
        ApplicationsState.AppEntry appEntry = this.mAppEntry;
        if (appEntry == null) {
            entityHeaderController.mLabel = arguments.getString("extra_label");
            if (arguments.getInt("extra_icon_id", 0) == 0) {
                entityHeaderController.setIcon(
                        activity.getPackageManager().getDefaultActivityIcon());
            } else {
                entityHeaderController.setIcon(
                        activity.getDrawable(arguments.getInt("extra_icon_id")));
            }
        } else {
            this.mState.ensureIcon(appEntry);
            entityHeaderController.setLabel(this.mAppEntry);
            entityHeaderController.mIcon =
                    AppUtils.getIconWithoutCache(
                            entityHeaderController.mAppContext, this.mAppEntry);
            entityHeaderController.mIsInstantApp = AppUtils.isInstant(this.mAppEntry.info);
        }
        if (this.mPowerUsageTimeController != null) {
            this.mPowerUsageTimeController.handleScreenTimeUpdated(
                    arguments.getString("extra_slot_time"),
                    arguments.getLong("extra_screen_on_time"),
                    arguments.getLong("extra_background_time"),
                    arguments.getString("extra_anomaly_hint_pref_key"),
                    arguments.getString("extra_anomaly_hint_text"));
        }
        entityHeaderController.done(true);
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        AppButtonsPreferenceController appButtonsPreferenceController =
                this.mAppButtonsPreferenceController;
        if (appButtonsPreferenceController != null) {
            appButtonsPreferenceController.handleActivityResult(i, i2, intent);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mState = ApplicationsState.getInstance(getActivity().getApplication());
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        String string = getArguments().getString("extra_package_name");
        PrimarySwitchPreference primarySwitchPreference =
                (PrimarySwitchPreference) findPreference("allow_background_usage");
        this.mAllowBackgroundUsagePreference = primarySwitchPreference;
        if (primarySwitchPreference != null) {
            primarySwitchPreference.setOnPreferenceClickListener(this);
            this.mAllowBackgroundUsagePreference.setOnPreferenceChangeListener(this);
        }
        this.mBatteryOptimizeUtils =
                new BatteryOptimizeUtils(getContext(), getArguments().getInt("extra_uid"), string);
        this.mHeaderPreference = (LayoutPreference) findPreference("header_view");
        if (string != null) {
            this.mAppEntry = this.mState.getEntry(UserHandle.myUserId(), string);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        final int appOptimizationMode = this.mBatteryOptimizeUtils.getAppOptimizationMode(true);
        StringBuilder sb = this.mLogStringBuilder;
        sb.append(", onPause mode = ");
        sb.append(appOptimizationMode);
        if (appOptimizationMode != this.mOptimizationMode) {
            final int i =
                    appOptimizationMode != 1
                            ? (appOptimizationMode == 2 || appOptimizationMode == 3) ? 1881 : 0
                            : 1882;
            if (i != 0) {
                final int i2 = 1;
                this.mExecutor.execute(
                        new Runnable(
                                this) { // from class:
                                        // com.android.settings.fuelgauge.AdvancedPowerUsageDetail$$ExternalSyntheticLambda0
                            public final /* synthetic */ AdvancedPowerUsageDetail f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i2) {
                                    case 0:
                                        AdvancedPowerUsageDetail advancedPowerUsageDetail =
                                                this.f$0;
                                        if (i != advancedPowerUsageDetail.mOptimizationMode) {
                                            AppOptModeSharedPreferencesUtils
                                                    .deleteAppOptimizationModeEventByUid(
                                                            advancedPowerUsageDetail.getContext(),
                                                            advancedPowerUsageDetail
                                                                    .mBatteryOptimizeUtils
                                                                    .mUid);
                                        }
                                        Context applicationContext =
                                                advancedPowerUsageDetail
                                                        .getContext()
                                                        .getApplicationContext();
                                        BatteryOptimizeHistoricalLogEntry.Action action =
                                                BatteryOptimizeHistoricalLogEntry.Action.LEAVE;
                                        String str =
                                                advancedPowerUsageDetail
                                                        .mBatteryOptimizeUtils
                                                        .mPackageName;
                                        if (str == null) {
                                            str = "unknown";
                                        }
                                        BatteryOptimizeLogUtils.writeLog(
                                                BatteryOptimizeLogUtils.getSharedPreferences(
                                                        applicationContext),
                                                action,
                                                BatteryOptimizeLogUtils.getPackageNameWithUserId(
                                                        UserHandle.myUserId(), str),
                                                advancedPowerUsageDetail.mLogStringBuilder
                                                        .toString());
                                        return;
                                    default:
                                        AdvancedPowerUsageDetail advancedPowerUsageDetail2 =
                                                this.f$0;
                                        int i3 = i;
                                        Context context = advancedPowerUsageDetail2.getContext();
                                        String str2 =
                                                advancedPowerUsageDetail2
                                                        .mBatteryOptimizeUtils
                                                        .mPackageName;
                                        if (str2 == null) {
                                            str2 = "unknown";
                                        }
                                        String loggingPackageName =
                                                BatteryUtils.getLoggingPackageName(context, str2);
                                        FeatureFactoryImpl featureFactoryImpl =
                                                FeatureFactoryImpl._factory;
                                        if (featureFactoryImpl == null) {
                                            throw new UnsupportedOperationException(
                                                    "No feature factory configured");
                                        }
                                        featureFactoryImpl
                                                .getMetricsFeatureProvider()
                                                .action(
                                                        2067,
                                                        i3,
                                                        53,
                                                        advancedPowerUsageDetail2
                                                                .getArguments()
                                                                .getInt("extra_power_usage_amount"),
                                                        loggingPackageName);
                                        return;
                                }
                            }
                        });
            }
        }
        final int i3 = 0;
        this.mExecutor.execute(
                new Runnable(
                        this) { // from class:
                                // com.android.settings.fuelgauge.AdvancedPowerUsageDetail$$ExternalSyntheticLambda0
                    public final /* synthetic */ AdvancedPowerUsageDetail f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i3) {
                            case 0:
                                AdvancedPowerUsageDetail advancedPowerUsageDetail = this.f$0;
                                if (appOptimizationMode
                                        != advancedPowerUsageDetail.mOptimizationMode) {
                                    AppOptModeSharedPreferencesUtils
                                            .deleteAppOptimizationModeEventByUid(
                                                    advancedPowerUsageDetail.getContext(),
                                                    advancedPowerUsageDetail
                                                            .mBatteryOptimizeUtils
                                                            .mUid);
                                }
                                Context applicationContext =
                                        advancedPowerUsageDetail
                                                .getContext()
                                                .getApplicationContext();
                                BatteryOptimizeHistoricalLogEntry.Action action =
                                        BatteryOptimizeHistoricalLogEntry.Action.LEAVE;
                                String str =
                                        advancedPowerUsageDetail.mBatteryOptimizeUtils.mPackageName;
                                if (str == null) {
                                    str = "unknown";
                                }
                                BatteryOptimizeLogUtils.writeLog(
                                        BatteryOptimizeLogUtils.getSharedPreferences(
                                                applicationContext),
                                        action,
                                        BatteryOptimizeLogUtils.getPackageNameWithUserId(
                                                UserHandle.myUserId(), str),
                                        advancedPowerUsageDetail.mLogStringBuilder.toString());
                                return;
                            default:
                                AdvancedPowerUsageDetail advancedPowerUsageDetail2 = this.f$0;
                                int i32 = appOptimizationMode;
                                Context context = advancedPowerUsageDetail2.getContext();
                                String str2 =
                                        advancedPowerUsageDetail2
                                                .mBatteryOptimizeUtils
                                                .mPackageName;
                                if (str2 == null) {
                                    str2 = "unknown";
                                }
                                String loggingPackageName =
                                        BatteryUtils.getLoggingPackageName(context, str2);
                                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                                if (featureFactoryImpl == null) {
                                    throw new UnsupportedOperationException(
                                            "No feature factory configured");
                                }
                                featureFactoryImpl
                                        .getMetricsFeatureProvider()
                                        .action(
                                                2067,
                                                i32,
                                                53,
                                                advancedPowerUsageDetail2
                                                        .getArguments()
                                                        .getInt("extra_power_usage_amount"),
                                                loggingPackageName);
                                return;
                        }
                    }
                });
        Log.d("AdvancedPowerDetail", "Leave with mode: " + appOptimizationMode);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (!(preference instanceof PrimarySwitchPreference)
                || !TextUtils.equals(preference.getKey(), "allow_background_usage")) {
            return false;
        }
        if (obj instanceof Boolean) {
            this.mBatteryOptimizeUtils.setAppUsageState(
                    ((Boolean) obj).booleanValue() ? 3 : 1,
                    BatteryOptimizeHistoricalLogEntry.Action.APPLY);
        }
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        if (!(preference instanceof PrimarySwitchPreference)
                || !TextUtils.equals(preference.getKey(), "allow_background_usage")) {
            return false;
        }
        Context context = getContext();
        Bundle arguments = getArguments();
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        String name = PowerBackgroundUsageDetail.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = arguments;
        launchRequest.mSourceMetricsCategory = 2055;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        initHeader();
        this.mOptimizationMode = this.mBatteryOptimizeUtils.getAppOptimizationMode(true);
        initFooter();
        StringBuilder sb = new StringBuilder("onResume mode = ");
        sb.append(this.mOptimizationMode);
        this.mLogStringBuilder = sb;
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final boolean shouldSkipForInitialSUW() {
        return true;
    }

    public static void startBatteryDetailPage(
            Context context, int i, LaunchBatteryDetailPageArgs launchBatteryDetailPageArgs) {
        int userId;
        Bundle bundle = new Bundle();
        String str = launchBatteryDetailPageArgs.mPackageName;
        if (str == null) {
            bundle.putString("extra_label", launchBatteryDetailPageArgs.mAppLabel);
            bundle.putInt("extra_icon_id", launchBatteryDetailPageArgs.mIconId);
            bundle.putString("extra_package_name", null);
        } else {
            bundle.putString("extra_package_name", str);
        }
        bundle.putInt("extra_uid", launchBatteryDetailPageArgs.mUid);
        bundle.putLong("extra_background_time", launchBatteryDetailPageArgs.mBackgroundTimeMs);
        bundle.putLong("extra_foreground_time", launchBatteryDetailPageArgs.mForegroundTimeMs);
        bundle.putLong("extra_screen_on_time", launchBatteryDetailPageArgs.mScreenOnTimeMs);
        bundle.putString("extra_slot_time", launchBatteryDetailPageArgs.mSlotInformation);
        bundle.putString("extra_power_usage_percent", launchBatteryDetailPageArgs.mUsagePercent);
        bundle.putInt("extra_power_usage_amount", launchBatteryDetailPageArgs.mConsumedPower);
        bundle.putBoolean("extra_show_time_info", launchBatteryDetailPageArgs.mShowTimeInformation);
        bundle.putString(
                "extra_anomaly_hint_pref_key", launchBatteryDetailPageArgs.mAnomalyHintPrefKey);
        bundle.putString("extra_anomaly_hint_text", launchBatteryDetailPageArgs.mAnomalyHintText);
        if (launchBatteryDetailPageArgs.mIsUserEntry) {
            userId = ActivityManager.getCurrentUser();
        } else {
            userId = UserHandle.getUserId(launchBatteryDetailPageArgs.mUid);
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
        String name = PowerBackgroundUsageDetail.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.battery_details_title, null);
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = i;
        launchRequest.mUserHandle = new UserHandle(userId);
        subSettingLauncher.launch();
    }

    public static void startBatteryDetailPage(
            FragmentActivity fragmentActivity,
            Instrumentable instrumentable,
            String str,
            UserHandle userHandle) {
        Bundle bundle = new Bundle(3);
        PackageManager packageManager = fragmentActivity.getPackageManager();
        bundle.putString("extra_package_name", str);
        bundle.putString("extra_power_usage_percent", Utils.formatPercentage(0));
        try {
            bundle.putInt("extra_uid", packageManager.getPackageUid(str, 0));
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("AdvancedPowerDetail", "Cannot find package: " + str, e);
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(fragmentActivity);
        String name = PowerBackgroundUsageDetail.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        subSettingLauncher.setTitleRes(R.string.battery_details_title, null);
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = instrumentable.getMetricsCategory();
        launchRequest.mUserHandle = userHandle;
        subSettingLauncher.launch();
    }
}
