package com.android.settings.fuelgauge;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.IconDrawableFactory;
import android.util.SparseLongArray;

import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.fuelgauge.batterytip.AppInfo;
import com.android.settings.fuelgauge.batterytip.BatteryDatabaseManager;
import com.android.settings.fuelgauge.batterytip.BatteryTipDialogFragment;
import com.android.settings.fuelgauge.batterytip.BatteryTipPreferenceController;
import com.android.settings.fuelgauge.batterytip.tips.BatteryTip;
import com.android.settings.fuelgauge.batterytip.tips.RestrictAppTip;
import com.android.settings.fuelgauge.batterytip.tips.UnrestrictAppTip;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.widget.AppCheckBoxPreference;
import com.android.settingslib.utils.StringUtil;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.sec.ims.IMSParameter;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RestrictedAppDetails extends DashboardFragment
        implements BatteryTipPreferenceController.BatteryTipListener {
    static final String EXTRA_APP_INFO_LIST = "app_info_list";
    List<AppInfo> mAppInfos;
    BatteryDatabaseManager mBatteryDatabaseManager;
    BatteryUtils mBatteryUtils;
    IconDrawableFactory mIconDrawableFactory;
    public SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    PackageManager mPackageManager;
    PreferenceGroup mRestrictedAppListGroup;

    public static void startRestrictedAppDetails(
            InstrumentedPreferenceFragment instrumentedPreferenceFragment, List list) {
        Bundle bundle = new Bundle();
        bundle.putParcelableList(EXTRA_APP_INFO_LIST, list);
        SubSettingLauncher subSettingLauncher =
                new SubSettingLauncher(instrumentedPreferenceFragment.getContext());
        String name = RestrictedAppDetails.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.restricted_app_title, null);
        launchRequest.mSourceMetricsCategory = instrumentedPreferenceFragment.getMetricsCategory();
        subSettingLauncher.launch();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BatteryTipDialogFragment createDialogFragment(AppInfo appInfo, boolean z) {
        UnrestrictAppTip unrestrictAppTip;
        if (z) {
            RestrictAppTip restrictAppTip = new RestrictAppTip(1, 0, true);
            ArrayList arrayList = new ArrayList();
            restrictAppTip.mRestrictAppList = arrayList;
            arrayList.add(appInfo);
            restrictAppTip.mNeedUpdate = false;
            unrestrictAppTip = restrictAppTip;
        } else {
            UnrestrictAppTip unrestrictAppTip2 = new UnrestrictAppTip(7, 0, true);
            unrestrictAppTip2.mAppInfo = appInfo;
            unrestrictAppTip = unrestrictAppTip2;
        }
        BatteryTipDialogFragment batteryTipDialogFragment = new BatteryTipDialogFragment();
        Bundle bundle = new Bundle(1);
        bundle.putParcelable(BatteryTipPreferenceController.PREF_NAME, unrestrictAppTip);
        bundle.putInt("metrics_key", 1285);
        batteryTipDialogFragment.setArguments(bundle);
        return batteryTipDialogFragment;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return null;
    }

    public String getKeyFromAppInfo(AppInfo appInfo) {
        return appInfo.uid + "," + appInfo.packageName;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "RestrictedAppDetails";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1285;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.restricted_apps_detail;
    }

    @Override // com.android.settings.fuelgauge.batterytip.BatteryTipPreferenceController.BatteryTipListener
    public final void onBatteryTipHandled(BatteryTip batteryTip) {
        boolean z = batteryTip instanceof RestrictAppTip;
        CheckBoxPreference checkBoxPreference =
                (CheckBoxPreference)
                        this.mRestrictedAppListGroup.findPreference(
                                getKeyFromAppInfo(
                                        z
                                                ? (AppInfo)
                                                        ((RestrictAppTip) batteryTip)
                                                                .mRestrictAppList.get(0)
                                                : ((UnrestrictAppTip) batteryTip).mAppInfo));
        if (checkBoxPreference != null) {
            checkBoxPreference.setChecked(z);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        this.mRestrictedAppListGroup = (PreferenceGroup) findPreference("restrict_app_list");
        this.mAppInfos = getArguments().getParcelableArrayList(EXTRA_APP_INFO_LIST);
        this.mPackageManager = context.getPackageManager();
        this.mIconDrawableFactory = IconDrawableFactory.newInstance(context);
        this.mBatteryUtils = BatteryUtils.getInstance(context);
        this.mBatteryDatabaseManager = BatteryDatabaseManager.getInstance(context);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        refreshUi();
    }

    public void refreshUi() {
        SparseLongArray sparseLongArray;
        Drawable defaultActivityIcon;
        this.mRestrictedAppListGroup.removeAll();
        Context prefContext = getPrefContext();
        BatteryDatabaseManager batteryDatabaseManager = this.mBatteryDatabaseManager;
        synchronized (batteryDatabaseManager) {
            sparseLongArray = new SparseLongArray();
            Cursor query =
                    batteryDatabaseManager
                            .mDatabaseHelper
                            .getReadableDatabase()
                            .query(
                                    IMSParameter.CALL.ACTION,
                                    new String[] {
                                        NetworkAnalyticsConstants.DataPoints.UID, "time_stamp_ms"
                                    },
                                    "action_type = ? ",
                                    new String[] {String.valueOf(0)},
                                    null,
                                    null,
                                    null);
            try {
                int columnIndex = query.getColumnIndex(NetworkAnalyticsConstants.DataPoints.UID);
                int columnIndex2 = query.getColumnIndex("time_stamp_ms");
                while (query.moveToNext()) {
                    sparseLongArray.append(query.getInt(columnIndex), query.getLong(columnIndex2));
                }
                query.close();
            } finally {
            }
        }
        long currentTimeMillis = System.currentTimeMillis();
        int size = this.mAppInfos.size();
        for (int i = 0; i < size; i++) {
            AppCheckBoxPreference appCheckBoxPreference = new AppCheckBoxPreference(prefContext);
            final AppInfo appInfo = this.mAppInfos.get(i);
            try {
                ApplicationInfo applicationInfoAsUser =
                        this.mPackageManager.getApplicationInfoAsUser(
                                appInfo.packageName, 0, UserHandle.getUserId(appInfo.uid));
                appCheckBoxPreference.setChecked(
                        this.mBatteryUtils.mAppOpsManager.checkOpNoThrow(
                                        70, appInfo.uid, appInfo.packageName)
                                == 1);
                appCheckBoxPreference.setTitle(
                        this.mPackageManager.getApplicationLabel(applicationInfoAsUser));
                IconDrawableFactory iconDrawableFactory = this.mIconDrawableFactory;
                PackageManager packageManager = this.mPackageManager;
                String str = appInfo.packageName;
                int userId = UserHandle.getUserId(appInfo.uid);
                StringBuilder sb = Utils.sBuilder;
                try {
                    defaultActivityIcon =
                            iconDrawableFactory.getBadgedIcon(
                                    packageManager.getApplicationInfoAsUser(str, 128, userId),
                                    userId);
                } catch (PackageManager.NameNotFoundException unused) {
                    defaultActivityIcon = packageManager.getDefaultActivityIcon();
                }
                appCheckBoxPreference.setIcon(defaultActivityIcon);
                appCheckBoxPreference.setKey(getKeyFromAppInfo(appInfo));
                appCheckBoxPreference.setOnPreferenceChangeListener(
                        new Preference
                                .OnPreferenceChangeListener() { // from class:
                                                                // com.android.settings.fuelgauge.RestrictedAppDetails$$ExternalSyntheticLambda0
                            @Override // androidx.preference.Preference.OnPreferenceChangeListener
                            public final boolean onPreferenceChange(
                                    Preference preference, Object obj) {
                                RestrictedAppDetails restrictedAppDetails =
                                        RestrictedAppDetails.this;
                                restrictedAppDetails.getClass();
                                boolean booleanValue = ((Boolean) obj).booleanValue();
                                AppInfo appInfo2 = appInfo;
                                BatteryTipDialogFragment createDialogFragment =
                                        restrictedAppDetails.createDialogFragment(
                                                appInfo2, booleanValue);
                                createDialogFragment.setTargetFragment(restrictedAppDetails, 0);
                                createDialogFragment.show(
                                        restrictedAppDetails.getFragmentManager(),
                                        "RestrictedAppDetails");
                                restrictedAppDetails.mMetricsFeatureProvider.action(
                                        restrictedAppDetails.getContext(),
                                        1780,
                                        appInfo2.packageName);
                                return false;
                            }
                        });
                if (sparseLongArray.get(appInfo.uid, -1L) != -1) {
                    appCheckBoxPreference.setSummary(
                            getString(
                                    R.string.restricted_app_time_summary,
                                    StringUtil.formatRelativeTime(
                                            prefContext,
                                            currentTimeMillis - r13,
                                            false,
                                            RelativeDateTimeFormatter.Style.LONG)));
                }
                this.mRestrictedAppListGroup.addPreference(appCheckBoxPreference);
            } catch (PackageManager.NameNotFoundException unused2) {
                MainClear$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Can't find package: "),
                        appInfo.packageName,
                        "RestrictedAppDetails");
            }
        }
    }
}
