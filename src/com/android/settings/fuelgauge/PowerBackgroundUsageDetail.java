package com.android.settings.fuelgauge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.BidiFormatter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.applications.appinfo.AppButtonsPreferenceController;
import com.android.settings.applications.appinfo.ButtonActionDialogFragment;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.fuelgauge.batteryusage.AppOptModeSharedPreferencesUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.EntityHeaderController;
import com.android.settingslib.HelpUtils;
import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.widget.FooterPreference;
import com.android.settingslib.widget.LayoutPreference;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PowerBackgroundUsageDetail extends DashboardFragment
        implements ButtonActionDialogFragment.AppButtonsDialogListener,
                SelectorWithWidgetPreference.OnClickListener,
                CompoundButton.OnCheckedChangeListener {
    public AppButtonsPreferenceController mAppButtonsPreferenceController;
    ApplicationsState.AppEntry mAppEntry;
    BatteryOptimizeUtils mBatteryOptimizeUtils;
    FooterPreference mFooterPreference;
    LayoutPreference mHeaderPreference;
    StringBuilder mLogStringBuilder;
    SelectorWithWidgetPreference mMainSwitchPreference;
    SelectorWithWidgetPreference mOptimizePreference;
    ApplicationsState mState;
    SelectorWithWidgetPreference mUnrestrictedPreference;
    public final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    int mOptimizationMode = 0;

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        Bundle arguments = getArguments();
        int i = arguments.getInt("extra_uid", 0);
        String string = arguments.getString("extra_package_name");
        AppButtonsPreferenceController appButtonsPreferenceController =
                new AppButtonsPreferenceController(
                        (SettingsActivity) getActivity(),
                        this,
                        getSettingsLifecycle(),
                        string,
                        this.mState,
                        0,
                        1);
        this.mAppButtonsPreferenceController = appButtonsPreferenceController;
        arrayList.add(appButtonsPreferenceController);
        arrayList.add(new AllowBackgroundPreferenceController(context, i, string));
        OptimizedPreferenceController optimizedPreferenceController =
                new OptimizedPreferenceController(context);
        optimizedPreferenceController.mBatteryOptimizeUtils =
                new BatteryOptimizeUtils(context, i, string);
        arrayList.add(optimizedPreferenceController);
        UnrestrictedPreferenceController unrestrictedPreferenceController =
                new UnrestrictedPreferenceController(context);
        unrestrictedPreferenceController.mBatteryOptimizeUtils =
                new BatteryOptimizeUtils(context, i, string);
        arrayList.add(unrestrictedPreferenceController);
        RestrictedPreferenceController restrictedPreferenceController =
                new RestrictedPreferenceController(context);
        restrictedPreferenceController.KEY_RESTRICTED_PREF = "allow_background_usage";
        restrictedPreferenceController.mBatteryOptimizeUtils =
                new BatteryOptimizeUtils(context, i, string);
        arrayList.add(restrictedPreferenceController);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "PowerBackgroundUsageDetail";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2055;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_power_usage_detail;
    }

    public int getSelectedPreference() {
        if (this.mMainSwitchPreference.mChecked) {
            return 1;
        }
        if (this.mUnrestrictedPreference.mChecked) {
            return 2;
        }
        return this.mOptimizePreference.mChecked ? 3 : 0;
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
        this.mFooterPreference.setTitle(
                this.mBatteryOptimizeUtils.isDisabledForOptimizeModeOnly()
                        ? context.getString(
                                R.string.manager_battery_usage_footer_limited,
                                context.getString(R.string.manager_battery_usage_optimized_only))
                        : this.mBatteryOptimizeUtils.isSystemOrDefaultApp()
                                ? context.getString(
                                        R.string.manager_battery_usage_footer_limited,
                                        context.getString(
                                                R.string.manager_battery_usage_unrestricted_only))
                                : context.getString(R.string.manager_battery_usage_footer));
        final Intent helpIntent =
                HelpUtils.getHelpIntent(
                        context,
                        context.getString(R.string.help_url_app_usage_settings),
                        ApnSettings.MVNO_NONE);
        if (helpIntent != null) {
            this.mFooterPreference.setLearnMoreAction(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.fuelgauge.PowerBackgroundUsageDetail$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            PowerBackgroundUsageDetail.this.startActivityForResult(helpIntent, 0);
                        }
                    });
            this.mFooterPreference.setLearnMoreText(
                    context.getString(R.string.manager_battery_usage_link_a11y));
        }
    }

    public void initHeader() {
        ApplicationInfo applicationInfo;
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
        ApplicationsState.AppEntry appEntry2 = this.mAppEntry;
        String string =
                (appEntry2 == null || (applicationInfo = appEntry2.info) == null)
                        ? getArguments() != null
                                ? getArguments().getString("extra_package_name")
                                : null
                        : applicationInfo.packageName;
        if (string != null) {
            try {
                entityHeaderController.mSummary =
                        getPrefContext()
                                .getString(
                                        R.string.version_text,
                                        BidiFormatter.getInstance()
                                                .unicodeWrap(
                                                        getPackageManager()
                                                                .getPackageInfo(string, 0)
                                                                .versionName));
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(
                        "PowerBackgroundUsageDetail",
                        "failed to get the app name : " + e.getMessage());
            }
        }
        entityHeaderController.done(true);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setAutoRemoveInsetCategory(false);
        if (getListView() != null) {
            getListView().mDrawLastRoundedCorner = false;
        }
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
        this.mOptimizePreference =
                (SelectorWithWidgetPreference) findPreference("optimized_preference");
        this.mUnrestrictedPreference =
                (SelectorWithWidgetPreference) findPreference("unrestricted_preference");
        this.mMainSwitchPreference =
                (SelectorWithWidgetPreference) findPreference("allow_background_usage");
        this.mFooterPreference = (FooterPreference) findPreference("app_usage_footer_preference");
        this.mOptimizePreference.mListener = this;
        this.mUnrestrictedPreference.mListener = this;
        this.mMainSwitchPreference.mListener = this;
        this.mBatteryOptimizeUtils =
                new BatteryOptimizeUtils(getContext(), getArguments().getInt("extra_uid"), string);
        this.mHeaderPreference = (LayoutPreference) findPreference("header_view");
        if (string != null) {
            this.mAppEntry = this.mState.getEntry(UserHandle.myUserId(), string);
        }
        setAutoRemoveInsetCategory(false);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        AppButtonsPreferenceController appButtonsPreferenceController =
                this.mAppButtonsPreferenceController;
        if (appButtonsPreferenceController != null) {
            appButtonsPreferenceController.initBottomButtonsLayout();
        }
        return onCreateView;
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
                            ? appOptimizationMode != 2 ? appOptimizationMode != 3 ? 0 : 1777 : 1776
                            : 1778;
            if (i != 0) {
                final int i2 = 1;
                this.mExecutor.execute(
                        new Runnable(
                                this) { // from class:
                                        // com.android.settings.fuelgauge.PowerBackgroundUsageDetail$$ExternalSyntheticLambda0
                            public final /* synthetic */ PowerBackgroundUsageDetail f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // java.lang.Runnable
                            public final void run() {
                                switch (i2) {
                                    case 0:
                                        PowerBackgroundUsageDetail powerBackgroundUsageDetail =
                                                this.f$0;
                                        int i3 = i;
                                        if (powerBackgroundUsageDetail.getContext() == null
                                                || powerBackgroundUsageDetail
                                                                .getContext()
                                                                .getApplicationContext()
                                                        == null) {
                                            return;
                                        }
                                        if (i3 != powerBackgroundUsageDetail.mOptimizationMode) {
                                            AppOptModeSharedPreferencesUtils
                                                    .deleteAppOptimizationModeEventByUid(
                                                            powerBackgroundUsageDetail.getContext(),
                                                            powerBackgroundUsageDetail
                                                                    .mBatteryOptimizeUtils
                                                                    .mUid);
                                        }
                                        Context applicationContext =
                                                powerBackgroundUsageDetail
                                                        .getContext()
                                                        .getApplicationContext();
                                        BatteryOptimizeHistoricalLogEntry.Action action =
                                                BatteryOptimizeHistoricalLogEntry.Action.LEAVE;
                                        String str =
                                                powerBackgroundUsageDetail
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
                                                powerBackgroundUsageDetail.mLogStringBuilder
                                                        .toString());
                                        return;
                                    default:
                                        PowerBackgroundUsageDetail powerBackgroundUsageDetail2 =
                                                this.f$0;
                                        int i4 = i;
                                        Context context = powerBackgroundUsageDetail2.getContext();
                                        String str2 =
                                                powerBackgroundUsageDetail2
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
                                                        2069,
                                                        i4,
                                                        2055,
                                                        powerBackgroundUsageDetail2
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
                                // com.android.settings.fuelgauge.PowerBackgroundUsageDetail$$ExternalSyntheticLambda0
                    public final /* synthetic */ PowerBackgroundUsageDetail f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i3) {
                            case 0:
                                PowerBackgroundUsageDetail powerBackgroundUsageDetail = this.f$0;
                                int i32 = appOptimizationMode;
                                if (powerBackgroundUsageDetail.getContext() == null
                                        || powerBackgroundUsageDetail
                                                        .getContext()
                                                        .getApplicationContext()
                                                == null) {
                                    return;
                                }
                                if (i32 != powerBackgroundUsageDetail.mOptimizationMode) {
                                    AppOptModeSharedPreferencesUtils
                                            .deleteAppOptimizationModeEventByUid(
                                                    powerBackgroundUsageDetail.getContext(),
                                                    powerBackgroundUsageDetail
                                                            .mBatteryOptimizeUtils
                                                            .mUid);
                                }
                                Context applicationContext =
                                        powerBackgroundUsageDetail
                                                .getContext()
                                                .getApplicationContext();
                                BatteryOptimizeHistoricalLogEntry.Action action =
                                        BatteryOptimizeHistoricalLogEntry.Action.LEAVE;
                                String str =
                                        powerBackgroundUsageDetail
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
                                        powerBackgroundUsageDetail.mLogStringBuilder.toString());
                                return;
                            default:
                                PowerBackgroundUsageDetail powerBackgroundUsageDetail2 = this.f$0;
                                int i4 = appOptimizationMode;
                                Context context = powerBackgroundUsageDetail2.getContext();
                                String str2 =
                                        powerBackgroundUsageDetail2
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
                                                2069,
                                                i4,
                                                2055,
                                                powerBackgroundUsageDetail2
                                                        .getArguments()
                                                        .getInt("extra_power_usage_amount"),
                                                loggingPackageName);
                                return;
                        }
                    }
                });
        Log.d("PowerBackgroundUsageDetail", "Leave with mode: " + appOptimizationMode);
    }

    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
    public final void onRadioButtonClicked(
            SelectorWithWidgetPreference selectorWithWidgetPreference) {
        String key =
                selectorWithWidgetPreference == null ? null : selectorWithWidgetPreference.getKey();
        SelectorWithWidgetPreference selectorWithWidgetPreference2 = this.mUnrestrictedPreference;
        selectorWithWidgetPreference2.setChecked(
                TextUtils.equals(key, selectorWithWidgetPreference2.getKey()));
        SelectorWithWidgetPreference selectorWithWidgetPreference3 = this.mOptimizePreference;
        selectorWithWidgetPreference3.setChecked(
                TextUtils.equals(key, selectorWithWidgetPreference3.getKey()));
        SelectorWithWidgetPreference selectorWithWidgetPreference4 = this.mMainSwitchPreference;
        selectorWithWidgetPreference4.setChecked(
                TextUtils.equals(key, selectorWithWidgetPreference4.getKey()));
        this.mBatteryOptimizeUtils.setAppUsageState(
                getSelectedPreference(), BatteryOptimizeHistoricalLogEntry.Action.APPLY);
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
        StringBuilder sb = new StringBuilder("onResume mode = ");
        sb.append(this.mOptimizationMode);
        this.mLogStringBuilder = sb;
    }

    public void updateSelectorPreference(boolean z) {
        this.mOptimizePreference.setEnabled(z);
        this.mUnrestrictedPreference.setEnabled(z);
        onRadioButtonClicked(z ? this.mOptimizePreference : null);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {}
}
