package com.android.settings.datausage;

import android.app.AlertDialog;
import android.app.AppGlobals;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.NetworkTemplate;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.util.ArraySet;
import android.util.IconDrawableFactory;
import android.util.Log;

import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.DefaultRingtonePreference$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.datausage.lib.AppDataUsageDetailsRepository;
import com.android.settings.datausage.lib.AppDataUsageRepository;
import com.android.settings.datausage.lib.NetworkUsageDetailsData;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager;
import com.android.settings.network.SubscriptionUtil;
import com.android.settingslib.AppItem;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.Utils;
import com.android.settingslib.net.UidDetail;
import com.android.settingslib.net.UidDetailProvider;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.datausage.DataUsageUtilsCHN;
import com.samsung.android.settings.datausage.trafficmanager.ui.DatePickerAlertDialog;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppDataUsage extends DataUsageBaseFragment
        implements Preference.OnPreferenceChangeListener, DataSaverBackend.Listener {
    public static String mPackageName;
    public AppItem mAppItem;
    public Preference mBackgroundUsage;
    public Context mContext;
    public AppDataUsageCycleController mCycleController;
    public ArrayList mCycles;
    public DataSaverBackend mDataSaverBackend;
    public long mEnd;
    public Preference mForegroundUsage;
    public Drawable mIcon;
    public boolean mIsLoading;
    CharSequence mLabel;
    public PackageInfo mPackageInfo;
    public PackageManager mPackageManager;
    public SecRestrictedSwitchPreference mRestrictBackgroundPco;
    public long mSelectedCycle;
    public long mStart;
    NetworkTemplate mTemplate;
    public Preference mTotalUsage;
    public SecRestrictedSwitchPreference mUnrestrictedData;
    public SecRestrictedSwitchPreference mUnrestrictedDataPco;
    public final ArraySet mPackages = new ArraySet();
    public final boolean mIsCHNDataUsage = Rune.SUPPORT_SMARTMANAGER_CN;
    public ConfirmUDSRestrict mDialogUDS = null;
    public SecRestrictedSwitchPreference mRestrictBackground = null;
    public EnterpriseDeviceManager mEDM = null;
    public RestrictionPolicy mRestrictionPolicy = null;
    public final AnonymousClass1 mPcoSettingObserver =
            new ContentObserver(
                    new Handler()) { // from class: com.android.settings.datausage.AppDataUsage.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    Log.i("AppDataUsage", "mPcoSettingObserver onChange(selfChange=" + z + ")");
                    if (AppDataUsage.this.getContext() == null
                            || AppDataUsage.this.getContext().getContentResolver() == null) {
                        Log.e("AppDataUsage", "Invalid context or content-resolver");
                        return;
                    }
                    boolean z2 =
                            Settings.Secure.getInt(
                                            AppDataUsage.this.getContext().getContentResolver(),
                                            "background_data_by_pco",
                                            1)
                                    != 1;
                    AbsAdapter$$ExternalSyntheticOutline0.m(
                            "mPcoSettingObserver restrictBackgroundByPco: ", "AppDataUsage", z2);
                    if (z2) {
                        AppDataUsage appDataUsage = AppDataUsage.this;
                        if (appDataUsage.mRestrictBackground != null) {
                            appDataUsage
                                    .getPreferenceScreen()
                                    .removePreference(AppDataUsage.this.mRestrictBackground);
                        }
                        AppDataUsage appDataUsage2 = AppDataUsage.this;
                        if (appDataUsage2.mUnrestrictedData != null) {
                            appDataUsage2
                                    .getPreferenceScreen()
                                    .removePreference(AppDataUsage.this.mUnrestrictedData);
                        }
                        AppDataUsage appDataUsage3 = AppDataUsage.this;
                        appDataUsage3.mRestrictBackground = null;
                        appDataUsage3.mUnrestrictedData = null;
                        return;
                    }
                    int i = AppDataUsage.this.mAppItem.key;
                    if (i <= 0 || !UserHandle.isApp(i)) {
                        TooltipPopup$$ExternalSyntheticOutline0.m(
                                new StringBuilder("Returning due to invalid app item key: "),
                                AppDataUsage.this.mAppItem.key,
                                "AppDataUsage");
                        return;
                    }
                    AppDataUsage appDataUsage4 = AppDataUsage.this;
                    SecRestrictedSwitchPreference secRestrictedSwitchPreference =
                            appDataUsage4.mRestrictBackgroundPco;
                    appDataUsage4.mRestrictBackground = secRestrictedSwitchPreference;
                    secRestrictedSwitchPreference.setOnPreferenceChangeListener(appDataUsage4);
                    AppDataUsage.this
                            .getPreferenceScreen()
                            .addPreference(AppDataUsage.this.mRestrictBackground);
                    AppDataUsage appDataUsage5 = AppDataUsage.this;
                    SecRestrictedSwitchPreference secRestrictedSwitchPreference2 =
                            appDataUsage5.mUnrestrictedDataPco;
                    appDataUsage5.mUnrestrictedData = secRestrictedSwitchPreference2;
                    secRestrictedSwitchPreference2.setOnPreferenceChangeListener(appDataUsage5);
                    AppDataUsage.this
                            .getPreferenceScreen()
                            .addPreference(AppDataUsage.this.mUnrestrictedData);
                    AppDataUsage.this.updatePrefs();
                }
            };
    public final AnonymousClass2 mDateSetListener = new AnonymousClass2();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.datausage.AppDataUsage$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {}
    }

    static {
        Math.max(2, Math.min(Runtime.getRuntime().availableProcessors() - 1, 4));
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addEntityHeader() {
        /*
            r8 = this;
            java.lang.String r0 = com.android.settings.datausage.AppDataUsage.mPackageName
            r1 = 0
            r2 = 0
            if (r0 == 0) goto L17
            android.util.ArraySet r0 = r8.mPackages
            int r0 = r0.size()
            if (r0 == 0) goto L17
            android.util.ArraySet r0 = r8.mPackages
            java.lang.Object r0 = r0.valueAt(r2)
            java.lang.String r0 = (java.lang.String) r0
            goto L18
        L17:
            r0 = r1
        L18:
            if (r0 == 0) goto L34
            android.content.pm.PackageManager r3 = r8.mPackageManager     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L29
            com.android.settingslib.AppItem r4 = r8.mAppItem     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L29
            int r4 = r4.key     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L29
            int r4 = android.os.UserHandle.getUserId(r4)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L29
            int r3 = r3.getPackageUidAsUser(r0, r4)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L29
            goto L35
        L29:
            java.lang.String r3 = "Skipping UID because cannot find package "
            java.lang.String r3 = r3.concat(r0)
            java.lang.String r4 = "AppDataUsage"
            android.util.Log.w(r4, r3)
        L34:
            r3 = r2
        L35:
            com.android.settingslib.AppItem r4 = r8.mAppItem
            int r4 = r4.key
            r5 = 1
            if (r4 <= 0) goto L3e
            r4 = r5
            goto L3f
        L3e:
            r4 = r2
        L3f:
            androidx.fragment.app.FragmentActivity r6 = r8.getActivity()
            com.android.settings.widget.EntityHeaderController r7 = new com.android.settings.widget.EntityHeaderController
            r7.<init>(r6, r8, r1)
            r7.mUid = r3
            r7.mHasAppInfoLink = r4
            r7.mAction1 = r2
            r7.mAction2 = r2
            android.graphics.drawable.Drawable r1 = r8.mIcon
            r7.setIcon(r1)
            java.lang.CharSequence r1 = r8.mLabel
            r7.mLabel = r1
            r7.mPackageName = r0
            android.content.pm.PackageInfo r0 = r8.mPackageInfo
            if (r0 == 0) goto L63
            java.lang.String r0 = r0.versionName
            r7.mSummary = r0
        L63:
            android.content.Context r0 = r8.getPrefContext()
            com.android.settingslib.widget.LayoutPreference r0 = r7.done(r0)
            androidx.preference.PreferenceScreen r1 = r8.getPreferenceScreen()
            java.lang.String r3 = "pref_app_header"
            androidx.preference.Preference r1 = r1.findPreference(r3)
            if (r1 != 0) goto L7e
            androidx.preference.PreferenceScreen r1 = r8.getPreferenceScreen()
            r1.addPreference(r0)
        L7e:
            com.samsung.android.settings.widget.SecInsetCategoryPreference r1 = new com.samsung.android.settings.widget.SecInsetCategoryPreference
            android.content.Context r3 = r8.getPrefContext()
            r1.<init>(r3)
            r1.setHeight(r2)
            r2 = 3
            r1.seslSetSubheaderRoundedBackground(r2)
            int r0 = r0.getOrder()
            int r0 = r0 + r5
            r1.setOrder(r0)
            androidx.preference.PreferenceScreen r8 = r8.getPreferenceScreen()
            r8.addPreference(r1)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.datausage.AppDataUsage.addEntityHeader():void");
    }

    public final void addUid(int i) {
        PackageManager packageManager = this.mPackageManager;
        if (Process.isSdkSandboxUid(i)) {
            i = Process.getAppUidForSdkSandboxUid(i);
        }
        String[] packagesForUid = packageManager.getPackagesForUid(i);
        if (packagesForUid != null) {
            for (String str : packagesForUid) {
                if (!"com.carrieriq.iqagent".equals(str)) {
                    this.mPackages.add(str);
                }
            }
        }
    }

    public void bindData(NetworkUsageDetailsData networkUsageDetailsData) {
        if (networkUsageDetailsData == null) {
            removePreference("inset_category_1");
            removePreference("cycle");
            Log.d("AppDataUsage", "bindData setVisible false");
        }
        this.mIsLoading = false;
        if (Rune.SUPPORT_SMARTMANAGER_CN) {
            this.mTotalUsage.setSummary(
                    DataUsageUtilsCHN.formatDataUsage(
                            this.mContext, networkUsageDetailsData.totalUsage));
            this.mForegroundUsage.setSummary(
                    DataUsageUtilsCHN.formatDataUsage(
                            this.mContext, networkUsageDetailsData.foregroundUsage));
            this.mBackgroundUsage.setSummary(
                    DataUsageUtilsCHN.formatDataUsage(
                            this.mContext, networkUsageDetailsData.backgroundUsage));
        } else {
            this.mTotalUsage.setSummary(
                    DataUsageUtils.formatDataUsage(
                            this.mContext, networkUsageDetailsData.totalUsage));
            this.mForegroundUsage.setSummary(
                    DataUsageUtils.formatDataUsage(
                            this.mContext, networkUsageDetailsData.foregroundUsage));
            this.mBackgroundUsage.setSummary(
                    DataUsageUtils.formatDataUsage(
                            this.mContext, networkUsageDetailsData.backgroundUsage));
        }
    }

    public final boolean getAppRestrictBackground() {
        int i = this.mAppItem.key;
        return (this.services.mPolicyManager.getUidPolicy(i) & 1) != 0
                && DynamicDenylistManager.getInstance(this.mContext)
                        .getManualDenylistPref()
                        .contains(String.valueOf(i));
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AppDataUsage";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.TSV;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_app_data_usage;
    }

    public UidDetailProvider getUidDetailProvider() {
        return new UidDetailProvider(this.mContext);
    }

    public void initCycle(List<Integer> list) {
        this.mCycleController =
                (AppDataUsageCycleController) use(AppDataUsageCycleController.class);
        this.mCycleController.init(
                new AppDataUsageDetailsRepository(
                        this.mContext, this.mTemplate, this.mCycles, list),
                new Function1() { // from class:
                                  // com.android.settings.datausage.AppDataUsage$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        String str = AppDataUsage.mPackageName;
                        AppDataUsage.this.bindData((NetworkUsageDetailsData) obj);
                        return Unit.INSTANCE;
                    }
                });
        if (Rune.SUPPORT_SMARTMANAGER_CN) {
            this.mCycleController.dateSetByUser(
                    new Function0() { // from class:
                                      // com.android.settings.datausage.AppDataUsage$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            String str = AppDataUsage.mPackageName;
                            AppDataUsage.this.showDialog(101);
                            return null;
                        }
                    });
            SecRestrictedSwitchPreference secRestrictedSwitchPreference =
                    (SecRestrictedSwitchPreference) findPreference("unrestricted_data_saver");
            this.mUnrestrictedData = secRestrictedSwitchPreference;
            if (secRestrictedSwitchPreference != null) {
                secRestrictedSwitchPreference.setSummary(R.string.unrestricted_app_summary_chn);
                this.mUnrestrictedData.setTitle(R.string.unrestricted_app_title_chn);
            }
        }
        if (this.mCycles != null) {
            Log.d("AppDataUsage", "setInitialCycles: " + this.mCycles + " " + this.mSelectedCycle);
            this.mCycleController.setInitialCycles(this.mCycles, this.mSelectedCycle);
        }
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onAllowlistStatusChanged(int i, boolean z) {
        if (this.mAppItem.uids.get(i, false)) {
            updatePrefs(getAppRestrictBackground(), z);
        }
    }

    @Override // com.android.settings.datausage.DataUsageBaseFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        List<Integer> list;
        super.onCreate(bundle);
        getActivity().setTitle(getResources().getString(R.string.data_usage_app_summary_title));
        this.mContext = getContext();
        this.mPackageManager = getPackageManager();
        Bundle arguments = getArguments();
        this.mAppItem = arguments != null ? (AppItem) arguments.getParcelable("app_item") : null;
        this.mTemplate =
                arguments != null
                        ? (NetworkTemplate) arguments.getParcelable("network_template")
                        : null;
        this.mCycles =
                arguments != null ? (ArrayList) arguments.getSerializable("network_cycles") : null;
        this.mSelectedCycle = arguments != null ? arguments.getLong("selected_cycle") : 0L;
        if (this.mIsCHNDataUsage && this.mCycles != null) {
            for (int i = 0; i < this.mCycles.size(); i++) {
                if (((Long) this.mCycles.get(i)).longValue() > this.mEnd) {
                    this.mEnd = ((Long) this.mCycles.get(i)).longValue();
                }
                if (((Long) this.mCycles.get(i)).longValue() < this.mEnd
                        && ((Long) this.mCycles.get(i)).longValue() > this.mStart) {
                    this.mStart = ((Long) this.mCycles.get(i)).longValue();
                }
            }
            if (this.mStart == 0) {
                long j = this.mEnd;
                if (j != 0) {
                    this.mStart = j;
                }
            }
        }
        if (this.mTemplate == null) {
            Context context = this.mContext;
            Intrinsics.checkNotNullParameter(context, "context");
            NetworkTemplate defaultTemplate =
                    DataUsageUtils.getDefaultTemplate(
                            context, SubscriptionManager.getDefaultDataSubscriptionId());
            Intrinsics.checkNotNullExpressionValue(defaultTemplate, "getDefaultTemplate(...)");
            this.mTemplate = defaultTemplate;
        }
        FragmentActivity requireActivity = requireActivity();
        if (this.mAppItem == null) {
            int i2 =
                    arguments != null
                            ? arguments.getInt(NetworkAnalyticsConstants.DataPoints.UID, -1)
                            : getActivity()
                                    .getIntent()
                                    .getIntExtra(NetworkAnalyticsConstants.DataPoints.UID, -1);
            if (i2 == -1) {
                requireActivity.finish();
            } else {
                this.mAppItem = new AppItem(i2);
                ArrayList<Integer> integerArrayList =
                        arguments != null ? arguments.getIntegerArrayList("uids") : null;
                if (!this.mIsCHNDataUsage
                        || integerArrayList == null
                        || integerArrayList.size() <= 0) {
                    addUid(i2);
                    this.mAppItem.addUid(i2);
                } else {
                    for (int i3 = 0; i3 < integerArrayList.size(); i3++) {
                        addUid(integerArrayList.get(i3).intValue());
                        this.mAppItem.addUid(integerArrayList.get(i3).intValue());
                    }
                }
            }
        } else {
            for (int i4 = 0; i4 < this.mAppItem.uids.size(); i4++) {
                addUid(this.mAppItem.uids.keyAt(i4));
            }
        }
        this.mTotalUsage = findPreference("total_usage");
        this.mForegroundUsage = findPreference("foreground_usage");
        this.mBackgroundUsage = findPreference("background_usage");
        List<Integer> appUidList = AppDataUsageRepository.getAppUidList(this.mAppItem.uids);
        initCycle(appUidList);
        UidDetailProvider uidDetailProvider = getUidDetailProvider();
        if (this.mAppItem.key > 0) {
            if (SubscriptionUtil.isSimHardwareVisible(this.mContext)
                    && UserHandle.isApp(this.mAppItem.key)) {
                int userId = UserHandle.getUserId(this.mAppItem.key);
                if (this.mPackages.size() != 0) {
                    try {
                        String string =
                                arguments != null
                                        ? arguments.getString(
                                                "package", (String) this.mPackages.valueAt(0))
                                        : getActivity().getIntent().getStringExtra("package");
                        if (string.equals(ApnSettings.MVNO_NONE)) {
                            string = (String) this.mPackages.valueAt(0);
                        }
                        ApplicationInfo applicationInfoAsUser =
                                this.mPackageManager.getApplicationInfoAsUser(
                                        string, 0, UserHandle.getUserId(this.mAppItem.key));
                        this.mIcon =
                                IconDrawableFactory.newInstance(getActivity())
                                        .getBadgedIcon(applicationInfoAsUser);
                        this.mLabel = applicationInfoAsUser.loadLabel(this.mPackageManager);
                        String str = applicationInfoAsUser.packageName;
                        mPackageName = str;
                        this.mPackageInfo = this.mPackageManager.getPackageInfo(str, 0);
                    } catch (PackageManager.NameNotFoundException unused) {
                    }
                    ((AppDataUsageAppSettingsController)
                                    use(AppDataUsageAppSettingsController.class))
                            .init(this.mPackages, userId);
                    if (!new AppDataUsageAppSettingsController(getContext(), "app_settings")
                            .getVisiable()) {
                        removePreference("divider_1");
                    }
                } else {
                    try {
                        IPackageManager packageManager = AppGlobals.getPackageManager();
                        String nameForUid = this.mPackageManager.getNameForUid(this.mAppItem.key);
                        list = appUidList;
                        try {
                            ApplicationInfo applicationInfo =
                                    packageManager.getApplicationInfo(
                                            nameForUid,
                                            GoodSettingsContract.PreferenceFlag.FLAG_NEED_TYPE,
                                            userId);
                            this.mLabel =
                                    applicationInfo.loadLabel(this.mPackageManager).toString();
                            this.mIcon = this.mPackageManager.getApplicationIcon(applicationInfo);
                            mPackageName = nameForUid;
                            this.mPackageInfo = this.mPackageManager.getPackageInfo(nameForUid, 0);
                        } catch (Exception unused2) {
                        }
                    } catch (Exception unused3) {
                    }
                    SecRestrictedSwitchPreference secRestrictedSwitchPreference =
                            (SecRestrictedSwitchPreference) findPreference("restrict_background");
                    this.mRestrictBackground = secRestrictedSwitchPreference;
                    secRestrictedSwitchPreference.setOnPreferenceChangeListener(this);
                    SecRestrictedSwitchPreference secRestrictedSwitchPreference2 =
                            (SecRestrictedSwitchPreference)
                                    findPreference("unrestricted_data_saver");
                    this.mUnrestrictedData = secRestrictedSwitchPreference2;
                    secRestrictedSwitchPreference2.setVisible(!Utils.isWifiOnly(getContext()));
                    this.mUnrestrictedData.setOnPreferenceChangeListener(this);
                }
                list = appUidList;
                SecRestrictedSwitchPreference secRestrictedSwitchPreference3 =
                        (SecRestrictedSwitchPreference) findPreference("restrict_background");
                this.mRestrictBackground = secRestrictedSwitchPreference3;
                secRestrictedSwitchPreference3.setOnPreferenceChangeListener(this);
                SecRestrictedSwitchPreference secRestrictedSwitchPreference22 =
                        (SecRestrictedSwitchPreference) findPreference("unrestricted_data_saver");
                this.mUnrestrictedData = secRestrictedSwitchPreference22;
                secRestrictedSwitchPreference22.setVisible(!Utils.isWifiOnly(getContext()));
                this.mUnrestrictedData.setOnPreferenceChangeListener(this);
            } else {
                list = appUidList;
                UidDetail uidDetail = uidDetailProvider.getUidDetail(this.mAppItem.key, true);
                this.mIcon = uidDetail.icon;
                this.mLabel = uidDetail.label;
                removePreference("unrestricted_data_saver");
                removePreference("restrict_background");
                Log.i("AppDataUsage", "UserHandle.isApp(mAppItem.key) : false");
                removePreference("data_restrict");
            }
            this.mDataSaverBackend = new DataSaverBackend(this.mContext);
            ((AppDataUsageListController) use(AppDataUsageListController.class)).init(list);
            if (!new AppDataUsageListController(getContext(), "app_list").getVisiable()) {
                Log.i("AppDataUsage", "App List Remove");
                removePreference("app_list");
            }
            if (!new AppDataUsageAppSettingsController(getContext(), "app_settings")
                    .getVisiable()) {
                removePreference("divider_1");
            }
        } else {
            FragmentActivity activity = getActivity();
            UidDetail uidDetail2 = uidDetailProvider.getUidDetail(this.mAppItem.key, true);
            this.mIcon = uidDetail2.icon;
            this.mLabel = uidDetail2.label;
            mPackageName = activity.getPackageName();
            Log.i("AppDataUsage", "mAppItme.key < 0");
            removePreference("app_list");
            removePreference("data_restrict");
            removePreference("divider_1");
            removePreference("unrestricted_data_saver");
            removePreference("restrict_background");
        }
        if (ConnectionsUtils.isSupportPco()) {
            this.mRestrictBackgroundPco =
                    (SecRestrictedSwitchPreference) findPreference("restrict_background");
            this.mUnrestrictedDataPco =
                    (SecRestrictedSwitchPreference) findPreference("unrestricted_data_saver");
            if (Settings.Secure.getInt(
                            getContext().getContentResolver(), "background_data_by_pco", 1)
                    != 1) {
                if (this.mRestrictBackground != null) {
                    getPreferenceScreen().removePreference(this.mRestrictBackground);
                    this.mRestrictBackground = null;
                }
                if (this.mUnrestrictedData != null) {
                    getPreferenceScreen().removePreference(this.mUnrestrictedData);
                }
                removePreference("data_restrict");
            }
        }
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(getContext());
        }
        EnterpriseDeviceManager enterpriseDeviceManager = this.mEDM;
        if (enterpriseDeviceManager != null) {
            this.mRestrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy();
        }
        addEntityHeader();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        if (i != 101) {
            throw new IllegalArgumentException();
        }
        Calendar calendar = Calendar.getInstance();
        DatePickerAlertDialog datePickerAlertDialog =
                new DatePickerAlertDialog(
                        getActivity(),
                        this.mDateSetListener,
                        calendar.get(1),
                        calendar.get(2),
                        calendar.get(5));
        datePickerAlertDialog.getWindow().setSoftInputMode(32);
        return datePickerAlertDialog;
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onDenylistStatusChanged(int i, boolean z) {
        boolean z2 = false;
        if (this.mAppItem.uids.get(i, false)) {
            DataSaverBackend dataSaverBackend = this.mDataSaverBackend;
            if (dataSaverBackend != null) {
                int i2 = this.mAppItem.key;
                for (int i3 : dataSaverBackend.mPolicyManager.getUidsWithPolicy(4)) {
                    dataSaverBackend.mUidPolicies.put(i3, 4);
                }
                if (dataSaverBackend.mUidPolicies.get(i2, 0) == 4) {
                    z2 = true;
                }
            }
            updatePrefs(z, z2);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        DataSaverBackend dataSaverBackend = this.mDataSaverBackend;
        if (dataSaverBackend != null) {
            dataSaverBackend.remListener(this);
        }
        if (this.mPcoSettingObserver == null || !ConnectionsUtils.isSupportPco()) {
            return;
        }
        getContext().getContentResolver().unregisterContentObserver(this.mPcoSettingObserver);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (preference != this.mRestrictBackground) {
            if (preference != this.mUnrestrictedData) {
                return false;
            }
            Boolean bool = (Boolean) obj;
            this.mDataSaverBackend.setIsAllowlisted(
                    this.mAppItem.key, mPackageName, bool.booleanValue());
            if (Rune.SUPPORT_SMARTMANAGER_CN) {
                DataUsageUtilsCHN.sendSmartManagerEventWithValueLog(
                        getContext(),
                        getContext()
                                .getResources()
                                .getInteger(
                                        R.integer.data_usage_application_data_usage_cn_screen_id),
                        getContext()
                                .getResources()
                                .getInteger(
                                        R.integer
                                                .data_usage_application_data_usage_cn_use_data_without_restriction_event_id),
                        bool.booleanValue() ? 1 : 0);
            } else if (this.mTemplate.getMatchRule() == 4) {
                LoggingHelper.insertEventLogging(FileType.TSV, 7134, bool.booleanValue() ? 1L : 0L);
            } else {
                LoggingHelper.insertEventLogging(FileType.TSV, 7115, bool.booleanValue() ? 1L : 0L);
            }
            return true;
        }
        boolean z = Rune.SUPPORT_SMARTMANAGER_CN;
        if (z && Settings.System.getInt(getActivity().getContentResolver(), "udsState", 0) == 1) {
            ConfirmUDSRestrict confirmUDSRestrict = new ConfirmUDSRestrict();
            this.mDialogUDS = confirmUDSRestrict;
            if (isAdded() && !confirmUDSRestrict.isAdded()) {
                confirmUDSRestrict.setTargetFragment(this, 0);
                confirmUDSRestrict.setCancelable(false);
                confirmUDSRestrict.show(getFragmentManager(), "confirmUDSRestrict");
            }
            return false;
        }
        DataSaverBackend dataSaverBackend = this.mDataSaverBackend;
        int i = this.mAppItem.key;
        String str = mPackageName;
        Boolean bool2 = (Boolean) obj;
        int i2 = !bool2.booleanValue() ? 1 : 0;
        dataSaverBackend.mDynamicDenylistManager.setUidPolicyLocked(i, i2);
        dataSaverBackend.mUidPolicies.put(i, i2);
        if (i2 != 0) {
            dataSaverBackend.mMetricsFeatureProvider.action(dataSaverBackend.mContext, 396, str);
        }
        if (z) {
            DataUsageUtilsCHN.sendSmartManagerEventWithValueLog(
                    getContext(),
                    getContext()
                            .getResources()
                            .getInteger(R.integer.data_usage_application_data_usage_cn_screen_id),
                    getContext()
                            .getResources()
                            .getInteger(
                                    R.integer
                                            .data_usage_application_data_usage_cn_restrict_background_event_id),
                    bool2.booleanValue() ? 1 : 0);
        } else if (this.mTemplate.getMatchRule() == 4) {
            LoggingHelper.insertEventLogging(FileType.TSV, 7133, bool2.booleanValue() ? 1L : 0L);
        } else {
            LoggingHelper.insertEventLogging(FileType.TSV, 7114, bool2.booleanValue() ? 1L : 0L);
        }
        updatePrefs();
        return true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (preference != null) {
            return super.onPreferenceTreeClick(preference);
        }
        boolean z = Rune.SUPPORT_SMARTMANAGER_CN;
        if (z) {
            int integer =
                    getContext()
                            .getResources()
                            .getInteger(R.integer.data_usage_application_data_usage_cn_screen_id);
            int integer2 =
                    getContext()
                            .getResources()
                            .getInteger(
                                    R.integer
                                            .data_usage_application_data_usage_cn_view_app_setting_event_id);
            Context context = getContext();
            try {
                Log.d(
                        "DataUsageUtilsCHN",
                        "sendSmartManagerEventLog() START screenId : "
                                + integer
                                + " eventId : "
                                + integer2);
                Uri build =
                        Uri.parse("content://com.samsung.android.sm/SamsungAnalysis")
                                .buildUpon()
                                .appendQueryParameter("screenId", Integer.toString(integer))
                                .appendQueryParameter("eventId", Integer.toString(integer2))
                                .build();
                if (z) {
                    build =
                            Uri.parse("content://com.samsung.android.sa/SamsungAnalysis")
                                    .buildUpon()
                                    .appendQueryParameter("screenId", Integer.toString(integer))
                                    .appendQueryParameter("eventId", Integer.toString(integer2))
                                    .build();
                }
                Cursor query = context.getContentResolver().query(build, null, null, null, null);
                if (query != null) {
                    query.close();
                }
            } catch (IllegalArgumentException unused) {
                Log.e(
                        "DataUsageUtilsCHN",
                        "sendSmartManagerEventLog() has occured IllegalArgumentException with"
                            + " SMLogging");
            }
        }
        getActivity()
                .startActivityAsUser(null, new UserHandle(UserHandle.getUserId(this.mAppItem.key)));
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0097, code lost:

       if (r7 != null) goto L21;
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0099, code lost:

       r7.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00a2, code lost:

       if (r7 == null) goto L28;
    */
    @Override // com.android.settings.datausage.DataUsageBaseFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume() {
        /*
            r14 = this;
            super.onResume()
            com.android.settings.datausage.DataSaverBackend r0 = r14.mDataSaverBackend
            if (r0 == 0) goto La
            r0.addListener(r14)
        La:
            boolean r0 = com.samsung.android.settings.connection.ConnectionsUtils.isSupportPco()
            r1 = 0
            if (r0 == 0) goto L24
            android.content.Context r0 = r14.getContext()
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r2 = "background_data_by_pco"
            android.net.Uri r2 = android.provider.Settings.Secure.getUriFor(r2)
            com.android.settings.datausage.AppDataUsage$1 r3 = r14.mPcoSettingObserver
            r0.registerContentObserver(r2, r1, r3)
        L24:
            r14.updatePrefs()
            boolean r0 = com.samsung.android.settings.Rune.SUPPORT_SMARTMANAGER_CN
            if (r0 == 0) goto Lab
            android.content.Context r2 = r14.getContext()
            android.content.Context r3 = r14.getContext()
            android.content.res.Resources r3 = r3.getResources()
            r4 = 2131427357(0x7f0b001d, float:1.8476328E38)
            int r3 = r3.getInteger(r4)
            java.lang.String r4 = "screenId"
            java.lang.String r5 = "DataUsageUtilsCHN"
            java.lang.String r6 = "sendSmartManagerFlowLog() START screenId : "
            r7 = 0
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            r8.<init>(r6)     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            r8.append(r3)     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            java.lang.String r6 = r8.toString()     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            android.util.Log.d(r5, r6)     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            java.lang.String r6 = "content://com.samsung.android.sm/SamsungAnalysis"
            android.net.Uri r6 = android.net.Uri.parse(r6)     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            android.net.Uri$Builder r6 = r6.buildUpon()     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            java.lang.String r8 = java.lang.Integer.toString(r3)     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            android.net.Uri$Builder r6 = r6.appendQueryParameter(r4, r8)     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            android.net.Uri r6 = r6.build()     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            if (r0 == 0) goto L82
            java.lang.String r0 = "content://com.samsung.android.sa/SamsungAnalysis"
            android.net.Uri r0 = android.net.Uri.parse(r0)     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            android.net.Uri$Builder r0 = r0.buildUpon()     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            java.lang.String r3 = java.lang.Integer.toString(r3)     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            android.net.Uri$Builder r0 = r0.appendQueryParameter(r4, r3)     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            android.net.Uri r6 = r0.build()     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
        L82:
            r9 = r6
            goto L86
        L84:
            r14 = move-exception
            goto La5
        L86:
            android.content.ContentResolver r8 = r2.getContentResolver()     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            r12 = 0
            r13 = 0
            r10 = 0
            r11 = 0
            android.database.Cursor r7 = r8.query(r9, r10, r11, r12, r13)     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
            if (r7 == 0) goto L97
            r7.close()     // Catch: java.lang.Throwable -> L84 java.lang.IllegalArgumentException -> L9d
        L97:
            if (r7 == 0) goto Lab
        L99:
            r7.close()
            goto Lab
        L9d:
            java.lang.String r0 = "sendSmartManagerFlowLog() has occured IllegalArgumentException with SMLogging"
            android.util.Log.e(r5, r0)     // Catch: java.lang.Throwable -> L84
            if (r7 == 0) goto Lab
            goto L99
        La5:
            if (r7 == 0) goto Laa
            r7.close()
        Laa:
            throw r14
        Lab:
            boolean r0 = com.samsung.android.settings.Rune.SUPPORT_SMARTMANAGER_CN
            if (r0 == 0) goto Ld8
            androidx.fragment.app.FragmentActivity r0 = r14.getActivity()
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r2 = "udsState"
            int r0 = android.provider.Settings.System.getInt(r0, r2, r1)
            if (r0 != 0) goto Ld8
            com.android.settings.datausage.AppDataUsage$ConfirmUDSRestrict r0 = r14.mDialogUDS
            if (r0 == 0) goto Ld8
            boolean r0 = r0.isAdded()
            if (r0 == 0) goto Ld8
            com.android.settings.datausage.AppDataUsage$ConfirmUDSRestrict r0 = r14.mDialogUDS
            boolean r2 = r0.isAdded()
            if (r2 != 0) goto Ld2
            goto Ld8
        Ld2:
            r0.setTargetFragment(r14, r1)
            r0.dismissInternal(r1, r1)
        Ld8:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.datausage.AppDataUsage.onResume():void");
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        this.mIsLoading = true;
        getListView().setItemAnimator(null);
    }

    public void updatePrefs() {
        boolean appRestrictBackground = getAppRestrictBackground();
        DataSaverBackend dataSaverBackend = this.mDataSaverBackend;
        boolean z = false;
        if (dataSaverBackend != null) {
            int i = this.mAppItem.key;
            for (int i2 : dataSaverBackend.mPolicyManager.getUidsWithPolicy(4)) {
                dataSaverBackend.mUidPolicies.put(i2, 4);
            }
            if (dataSaverBackend.mUidPolicies.get(i, 0) == 4) {
                z = true;
            }
        }
        updatePrefs(appRestrictBackground, z);
    }

    public final void updatePrefs(boolean z, boolean z2) {
        SecRestrictedSwitchPreference secRestrictedSwitchPreference;
        RestrictionPolicy restrictionPolicy = this.mRestrictionPolicy;
        if (restrictionPolicy != null) {
            boolean z3 =
                    restrictionPolicy.isBackgroundDataEnabled()
                            && this.mRestrictionPolicy.isDataSavingAllowed();
            SecRestrictedSwitchPreference secRestrictedSwitchPreference2 = this.mRestrictBackground;
            if (secRestrictedSwitchPreference2 != null) {
                secRestrictedSwitchPreference2.setEnabled(z3);
            }
            SecRestrictedSwitchPreference secRestrictedSwitchPreference3 = this.mUnrestrictedData;
            if (secRestrictedSwitchPreference3 != null) {
                secRestrictedSwitchPreference3.setEnabled(z3);
            }
            if (!z3) {
                SecRestrictedSwitchPreference secRestrictedSwitchPreference4 =
                        this.mRestrictBackground;
                if (secRestrictedSwitchPreference4 != null) {
                    secRestrictedSwitchPreference4.setChecked(!z);
                }
                SecRestrictedSwitchPreference secRestrictedSwitchPreference5 =
                        this.mUnrestrictedData;
                if (secRestrictedSwitchPreference5 != null) {
                    secRestrictedSwitchPreference5.setChecked(z2);
                    return;
                }
                return;
            }
        }
        if (SubscriptionUtil.isSimHardwareVisible(this.mContext)) {
            if (!this.mIsLoading) {
                RecyclerView listView = getListView();
                if (listView.getItemAnimator() == null) {
                    listView.setItemAnimator(new DefaultItemAnimator());
                }
            }
            RestrictedLockUtils.EnforcedAdmin checkIfMeteredDataUsageUserControlDisabled =
                    RestrictedLockUtilsInternal.checkIfMeteredDataUsageUserControlDisabled(
                            this.mContext, UserHandle.getUserId(this.mAppItem.key), mPackageName);
            SecRestrictedSwitchPreference secRestrictedSwitchPreference6 = this.mRestrictBackground;
            if (secRestrictedSwitchPreference6 != null) {
                secRestrictedSwitchPreference6.setChecked(!z);
                this.mRestrictBackground.setDisabledByAdmin(
                        checkIfMeteredDataUsageUserControlDisabled);
            }
            SecRestrictedSwitchPreference secRestrictedSwitchPreference7 = this.mUnrestrictedData;
            if (secRestrictedSwitchPreference7 != null) {
                if (z) {
                    secRestrictedSwitchPreference7.setEnabled(false);
                } else {
                    secRestrictedSwitchPreference7.setEnabled(true);
                    this.mUnrestrictedData.setDisabledByAdmin(
                            checkIfMeteredDataUsageUserControlDisabled);
                }
                this.mUnrestrictedData.setChecked(z2);
            }
            if (Rune.SUPPORT_SMARTMANAGER_CN && this.mRestrictBackground != null) {
                if (!this.services.mPolicyManager.getFirewallRuleMobileData(this.mAppItem.key)) {
                    this.mRestrictBackground.setEnabled(false);
                    SecRestrictedSwitchPreference secRestrictedSwitchPreference8 =
                            this.mUnrestrictedData;
                    if (secRestrictedSwitchPreference8 != null) {
                        secRestrictedSwitchPreference8.setEnabled(false);
                    }
                } else {
                    this.mRestrictBackground.setEnabled(true);
                    SecRestrictedSwitchPreference secRestrictedSwitchPreference9 =
                            this.mUnrestrictedData;
                    if (secRestrictedSwitchPreference9 != null) {
                        secRestrictedSwitchPreference9.setEnabled(!z);
                    }
                }
            }
            if ("com.google.android.gms".equals(mPackageName)) {
                SecRestrictedSwitchPreference secRestrictedSwitchPreference10 =
                        this.mRestrictBackground;
                if (secRestrictedSwitchPreference10 != null
                        && secRestrictedSwitchPreference10.mChecked) {
                    secRestrictedSwitchPreference10.setEnabled(false);
                }
                SecRestrictedSwitchPreference secRestrictedSwitchPreference11 =
                        this.mUnrestrictedData;
                if (secRestrictedSwitchPreference11 != null
                        && secRestrictedSwitchPreference11.mChecked) {
                    secRestrictedSwitchPreference11.setEnabled(false);
                }
            }
            if (!"com.samsung.android.kgclient".equals(mPackageName)
                    || (secRestrictedSwitchPreference = this.mRestrictBackground) == null) {
                return;
            }
            secRestrictedSwitchPreference.setChecked(true);
            this.mRestrictBackground.setEnabled(false);
        }
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onDataSaverChanged(boolean z) {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ConfirmUDSRestrict extends InstrumentedDialogFragment {
        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 37;
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.uds_dialog_message);
            builder.setPositiveButton(
                    R.string.uds_ok_button,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.datausage.AppDataUsage.ConfirmUDSRestrict.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent();
                            if (Rune.SUPPORT_SMARTMANAGER_CN) {
                                intent.setAction("com.samsung.android.uds.SHOW_UDS_ACTIVITY");
                            } else {
                                DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                                        "com.samsung.android.uds",
                                        "com.samsung.android.uds.ui.datausage.DatausageActivity",
                                        intent);
                            }
                            String str = AppDataUsage.mPackageName;
                            if (str != null) {
                                intent.putExtra("app_pkg", str);
                            }
                            ConfirmUDSRestrict.this.getContext().startActivity(intent);
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new AnonymousClass2());
            return builder.create();
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.datausage.AppDataUsage$ConfirmUDSRestrict$2, reason: invalid class name */
        public final class AnonymousClass2 implements DialogInterface.OnClickListener {
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {}
        }
    }
}
