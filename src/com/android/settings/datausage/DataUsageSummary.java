package com.android.settings.datausage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableData;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.BidiFormatter;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.style.RelativeSizeSpan;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceCategory;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.datausage.lib.DataUsageLib;
import com.android.settings.network.ProxySubscriptionManager;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.settings.datausage.SecDataSaverSummaryPreferenceController;
import com.samsung.android.settings.datausage.trafficmanager.ui.DataSaverSummaryCHN;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.sec.ims.configuration.DATA;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DataUsageSummary extends DashboardFragment {
    public DataSaverPreference mDataSaverPreference;
    public SecDataSaverSummaryPreferenceController mDataSaverPreferenceController;
    public DataSaverPreference mDataSaverPreferencePco;
    public final AnonymousClass5 mPcoSettingObserver;
    public ProxySubscriptionManager mProxySubscriptionMgr;
    public DataUsageSummaryPreferenceController mSummaryController;
    public DataUsageSummaryPreference mSummaryPreference;
    public SecPreference mUDSPreference;
    public final AnonymousClass5 mUDSStateObserver;
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass3();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass4();
    public boolean mIsSecureFolderMode = false;
    public final AnonymousClass1 mSimHotSwapReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.datausage.DataUsageSummary.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("com.samsung.intent.action.SIMHOTSWAP".equals(intent.getAction())) {
                        Log.d("DataUsageSummary", "FINISH : HOTSWAP");
                        DataUsageSummary.this.finish();
                    }
                }
            };
    public EnterpriseDeviceManager mEDM = null;
    public RestrictionPolicy mRestrictionPolicy = null;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.datausage.DataUsageSummary$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            boolean dataEnabled = TelephonyManager.from(context).getDataEnabled();
            String valueOf = String.valueOf(3502);
            String str = dataEnabled ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            StatusData statusData = new StatusData();
            statusData.mStatusValue = str;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.datausage.DataUsageSummary$4, reason: invalid class name */
    public final class AnonymousClass4 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getDynamicRawDataToIndex(Context context) {
            boolean z = Rune.SUPPORT_SMARTMANAGER_CN;
            if (z) {
                return null;
            }
            List dynamicRawDataToIndex = super.getDynamicRawDataToIndex(context);
            if (DataUsageUtils.hasMobileData(context)
                    && DataUsageUtils.hasActiveSim(context)
                    && !DataUsageUtils.isSupportATTMobileDataAndRoaming()
                    && DataUsageUtils.getDefaultSubscriptionId(context) != -1
                    && z
                    && Utils.isIntentAvailable(
                            context, "com.samsung.android.uds.SHOW_UDS_ACTIVITY")) {
                SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw).key = "ultra_data_saver";
                searchIndexableRaw.title =
                        context.getResources().getString(R.string.ultra_data_saving_title);
                searchIndexableRaw.keywords =
                        Utils.getKeywordForSearch(context, R.string.ultra_data_saving_title);
                searchIndexableRaw.screenTitle =
                        context.getResources().getString(R.string.data_usage_summary_title);
                ((ArrayList) dynamicRawDataToIndex).add(searchIndexableRaw);
            }
            return dynamicRawDataToIndex;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            if (Rune.SUPPORT_SMARTMANAGER_CN) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.sec_data_usage;
            arrayList.add(searchIndexableResource);
            return arrayList;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.datausage.DataUsageSummary$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.datausage.DataUsageSummary$5] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.datausage.DataUsageSummary$5] */
    public DataUsageSummary() {
        final int i = 0;
        this.mPcoSettingObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.android.settings.datausage.DataUsageSummary.5
                    public final /* synthetic */ DataUsageSummary this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        DataUsageSummary dataUsageSummary;
                        SecPreference secPreference;
                        switch (i) {
                            case 0:
                                Log.i(
                                        "DataUsageSummary",
                                        "mPcoSettingObserver onChange(selfChange=" + z + ")");
                                PreferenceCategory preferenceCategory =
                                        (PreferenceCategory) this.this$0.findPreference("usage");
                                if (preferenceCategory != null) {
                                    Context context = this.this$0.getContext();
                                    if (context != null && context.getContentResolver() != null) {
                                        boolean z2 =
                                                Settings.Secure.getInt(
                                                                context.getContentResolver(),
                                                                "background_data_by_pco",
                                                                1)
                                                        != 1;
                                        AbsAdapter$$ExternalSyntheticOutline0.m(
                                                "mPcoSettingObserver restrictBackgroundByPco: ",
                                                "DataUsageSummary",
                                                z2);
                                        if (!z2) {
                                            if (DataUsageUtils.hasMobileData(
                                                    this.this$0.getContext())) {
                                                DataUsageSummary dataUsageSummary2 = this.this$0;
                                                StatusLogger$StatusLoggingProvider
                                                        statusLogger$StatusLoggingProvider =
                                                                DataUsageSummary
                                                                        .STATUS_LOGGING_PROVIDER;
                                                if (((UserManager)
                                                                dataUsageSummary2
                                                                        .getContext()
                                                                        .getSystemService("user"))
                                                        .isAdminUser()) {
                                                    DataUsageSummary dataUsageSummary3 =
                                                            this.this$0;
                                                    DataSaverPreference dataSaverPreference =
                                                            dataUsageSummary3
                                                                    .mDataSaverPreferencePco;
                                                    dataUsageSummary3.mDataSaverPreference =
                                                            dataSaverPreference;
                                                    preferenceCategory.addPreference(
                                                            dataSaverPreference);
                                                    this.this$0.updateState$3();
                                                    break;
                                                }
                                            }
                                            Log.i(
                                                    "DataUsageSummary",
                                                    "Returning due to no mobile data or not admin");
                                            break;
                                        } else {
                                            DataSaverPreference dataSaverPreference2 =
                                                    this.this$0.mDataSaverPreference;
                                            if (dataSaverPreference2 != null) {
                                                preferenceCategory.removePreference(
                                                        dataSaverPreference2);
                                                this.this$0.mDataSaverPreference = null;
                                                break;
                                            }
                                        }
                                    } else {
                                        Log.e(
                                                "DataUsageSummary",
                                                "Invalid context or content-resolver");
                                        break;
                                    }
                                } else {
                                    Log.e(
                                            "DataUsageSummary",
                                            "'usage' preference category not found");
                                    break;
                                }
                                break;
                            default:
                                if (Settings.System.getInt(
                                                        this.this$0
                                                                .getActivity()
                                                                .getContentResolver(),
                                                        "udsState",
                                                        0)
                                                == 0
                                        && (secPreference =
                                                        (dataUsageSummary = this.this$0)
                                                                .mUDSPreference)
                                                != null) {
                                    secPreference.setSummary(
                                            dataUsageSummary.getString(R.string.switch_off_text));
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mUDSStateObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.android.settings.datausage.DataUsageSummary.5
                    public final /* synthetic */ DataUsageSummary this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        DataUsageSummary dataUsageSummary;
                        SecPreference secPreference;
                        switch (i2) {
                            case 0:
                                Log.i(
                                        "DataUsageSummary",
                                        "mPcoSettingObserver onChange(selfChange=" + z + ")");
                                PreferenceCategory preferenceCategory =
                                        (PreferenceCategory) this.this$0.findPreference("usage");
                                if (preferenceCategory != null) {
                                    Context context = this.this$0.getContext();
                                    if (context != null && context.getContentResolver() != null) {
                                        boolean z2 =
                                                Settings.Secure.getInt(
                                                                context.getContentResolver(),
                                                                "background_data_by_pco",
                                                                1)
                                                        != 1;
                                        AbsAdapter$$ExternalSyntheticOutline0.m(
                                                "mPcoSettingObserver restrictBackgroundByPco: ",
                                                "DataUsageSummary",
                                                z2);
                                        if (!z2) {
                                            if (DataUsageUtils.hasMobileData(
                                                    this.this$0.getContext())) {
                                                DataUsageSummary dataUsageSummary2 = this.this$0;
                                                StatusLogger$StatusLoggingProvider
                                                        statusLogger$StatusLoggingProvider =
                                                                DataUsageSummary
                                                                        .STATUS_LOGGING_PROVIDER;
                                                if (((UserManager)
                                                                dataUsageSummary2
                                                                        .getContext()
                                                                        .getSystemService("user"))
                                                        .isAdminUser()) {
                                                    DataUsageSummary dataUsageSummary3 =
                                                            this.this$0;
                                                    DataSaverPreference dataSaverPreference =
                                                            dataUsageSummary3
                                                                    .mDataSaverPreferencePco;
                                                    dataUsageSummary3.mDataSaverPreference =
                                                            dataSaverPreference;
                                                    preferenceCategory.addPreference(
                                                            dataSaverPreference);
                                                    this.this$0.updateState$3();
                                                    break;
                                                }
                                            }
                                            Log.i(
                                                    "DataUsageSummary",
                                                    "Returning due to no mobile data or not admin");
                                            break;
                                        } else {
                                            DataSaverPreference dataSaverPreference2 =
                                                    this.this$0.mDataSaverPreference;
                                            if (dataSaverPreference2 != null) {
                                                preferenceCategory.removePreference(
                                                        dataSaverPreference2);
                                                this.this$0.mDataSaverPreference = null;
                                                break;
                                            }
                                        }
                                    } else {
                                        Log.e(
                                                "DataUsageSummary",
                                                "Invalid context or content-resolver");
                                        break;
                                    }
                                } else {
                                    Log.e(
                                            "DataUsageSummary",
                                            "'usage' preference category not found");
                                    break;
                                }
                                break;
                            default:
                                if (Settings.System.getInt(
                                                        this.this$0
                                                                .getActivity()
                                                                .getContentResolver(),
                                                        "udsState",
                                                        0)
                                                == 0
                                        && (secPreference =
                                                        (dataUsageSummary = this.this$0)
                                                                .mUDSPreference)
                                                != null) {
                                    secPreference.setSummary(
                                            dataUsageSummary.getString(R.string.switch_off_text));
                                    break;
                                }
                                break;
                        }
                    }
                };
    }

    public static CharSequence formatUsage(Context context, String str, long j) {
        Formatter.BytesResult formatBytes = Formatter.formatBytes(context.getResources(), j, 10);
        SpannableString spannableString = new SpannableString(formatBytes.value);
        spannableString.setSpan(new RelativeSizeSpan(1.5625f), 0, spannableString.length(), 18);
        CharSequence expandTemplate =
                TextUtils.expandTemplate(
                        new SpannableString(
                                context.getString(android.R.string.lockscreen_glogin_invalid_input)
                                        .replace("%1$s", "^1")
                                        .replace("%2$s", "^2")),
                        spannableString,
                        formatBytes.units);
        SpannableString spannableString2 = new SpannableString(str);
        spannableString2.setSpan(new RelativeSizeSpan(0.64f), 0, spannableString2.length(), 18);
        return TextUtils.expandTemplate(
                spannableString2,
                BidiFormatter.getInstance().unicodeWrap(expandTemplate.toString()));
    }

    public void addMobileSection(int i) {
        TemplatePreferenceCategory templatePreferenceCategory =
                (TemplatePreferenceCategory) inflatePreferences(R.xml.data_usage_cellular);
        templatePreferenceCategory.mTemplate = DataUsageLib.getMobileTemplate(getContext(), i);
        templatePreferenceCategory.mSubId = i;
        for (int i2 = 0; i2 < templatePreferenceCategory.getPreferenceCount(); i2++) {
            if (templatePreferenceCategory.getPreference(i2) instanceof TemplatePreference) {
                ((TemplatePreference) templatePreferenceCategory.getPreference(i2))
                        .setTemplate(
                                templatePreferenceCategory.mTemplate,
                                templatePreferenceCategory.mSubId);
            }
        }
        SubscriptionUtil.getUniqueSubscriptionDisplayName(getContext(), null);
    }

    public void addWifiSection() {
        TemplatePreferenceCategory templatePreferenceCategory =
                (TemplatePreferenceCategory) inflatePreferences(R.xml.data_usage_wifi);
        templatePreferenceCategory.mTemplate = new NetworkTemplate.Builder(4).build();
        templatePreferenceCategory.mSubId = 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        FragmentActivity activity = getActivity();
        ArrayList arrayList = new ArrayList();
        DataUsageSummaryPreferenceController dataUsageSummaryPreferenceController =
                new DataUsageSummaryPreferenceController(
                        activity, DataUsageUtils.getDefaultSubscriptionId(activity));
        this.mSummaryController = dataUsageSummaryPreferenceController;
        arrayList.add(dataUsageSummaryPreferenceController);
        return arrayList;
    }

    public void enableProxySubscriptionManager(Context context) {
        ProxySubscriptionManager proxySubscriptionManager =
                ProxySubscriptionManager.getInstance(context);
        this.mProxySubscriptionMgr = proxySubscriptionManager;
        proxySubscriptionManager.setLifecycle(getLifecycle());
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "DataUsageSummary";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 37;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_data_usage;
    }

    public boolean hasActiveSubscription() {
        List activeSubscriptionsInfo =
                this.mProxySubscriptionMgr.mSubscriptionMonitor.getActiveSubscriptionsInfo();
        return activeSubscriptionsInfo != null && activeSubscriptionsInfo.size() > 0;
    }

    public final Preference inflatePreferences(int i) {
        PreferenceScreen inflateFromResource =
                getPreferenceManager().inflateFromResource(getPrefContext(), i, null);
        Preference preference = inflateFromResource.getPreference(0);
        inflateFromResource.removeAll();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preference.setOrder(preferenceScreen.getPreferenceCount());
        preferenceScreen.addPreference(preference);
        return preference;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mDataSaverPreferenceController =
                (SecDataSaverSummaryPreferenceController)
                        use(SecDataSaverSummaryPreferenceController.class);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        boolean z;
        super.onCreate(bundle);
        Log.i("DataUsageSummary", "onCreate start");
        boolean hasMobileData = DataUsageUtils.hasMobileData(getContext());
        boolean hasActiveSim = DataUsageUtils.hasActiveSim(getContext());
        int defaultSubscriptionId = DataUsageUtils.getDefaultSubscriptionId(getContext());
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                defaultSubscriptionId, "Default Sub Id : ", "DataUsageSummary");
        if (defaultSubscriptionId == -1) {
            hasMobileData = false;
        }
        this.mSummaryPreference = (DataUsageSummaryPreference) findPreference("status_header");
        this.mIsSecureFolderMode = SemPersonaManager.isSecureFolderId(UserHandle.myUserId());
        if (ConnectionsUtils.isSupportPco()) {
            this.mDataSaverPreferencePco =
                    (DataSaverPreference) findPreference("restrict_background");
        }
        if (this.mDataSaverPreferenceController.isAvailable()) {
            this.mDataSaverPreference = (DataSaverPreference) findPreference("restrict_background");
        }
        DataSaverPreference dataSaverPreference = this.mDataSaverPreference;
        if (dataSaverPreference != null && this.mIsSecureFolderMode) {
            dataSaverPreference.setSummary((CharSequence) null);
            if (Rune.SUPPORT_SMARTMANAGER_CN) {
                this.mDataSaverPreference.setTitle(R.string.data_saver_title_chn);
                this.mDataSaverPreference.setFragment(DataSaverSummaryCHN.class.getName());
            }
        }
        SecPreferenceCategory secPreferenceCategory =
                (SecPreferenceCategory) findPreference("mobile_category");
        SecPreferenceCategory secPreferenceCategory2 =
                (SecPreferenceCategory) findPreference("sim1_category");
        SecPreferenceCategory secPreferenceCategory3 =
                (SecPreferenceCategory) findPreference("sim2_category");
        SecPreferenceCategory secPreferenceCategory4 =
                (SecPreferenceCategory) findPreference("roaming_category");
        secPreferenceCategory.setVisible(false);
        secPreferenceCategory2.setVisible(false);
        secPreferenceCategory3.setVisible(false);
        secPreferenceCategory4.setVisible(false);
        Log.i(
                "DataUsageSummary",
                "hasMobileData : " + hasMobileData + ", hasSimCard : " + hasActiveSim);
        if (hasMobileData && hasActiveSim) {
            if (DataUsageUtils.isDataRoaming(defaultSubscriptionId)) {
                secPreferenceCategory4.setVisible(true);
            }
            List<SubscriptionInfo> completeActiveSubscriptionInfoList =
                    ((SubscriptionManager)
                                    getContext().getSystemService("telephony_subscription_service"))
                            .getCompleteActiveSubscriptionInfoList();
            if (completeActiveSubscriptionInfoList != null) {
                completeActiveSubscriptionInfoList.sort(
                        new Comparator() { // from class:
                                           // com.android.settings.datausage.DataUsageSummary.2
                            public final Collator mCollator = Collator.getInstance();

                            @Override // java.util.Comparator
                            public final int compare(Object obj, Object obj2) {
                                return this.mCollator.compare(
                                        Integer.toString(
                                                ((SubscriptionInfo) obj).getSimSlotIndex()),
                                        Integer.toString(
                                                ((SubscriptionInfo) obj2).getSimSlotIndex()));
                            }
                        });
            }
            if (Rune.isEnabledHidingByOpportunisticEsim(getContext())) {
                Log.i("DataUsageSummary", "Opportunistic Esim Active");
                secPreferenceCategory.setVisible(true);
                if (completeActiveSubscriptionInfoList.get(0).isOpportunistic()) {
                    if (completeActiveSubscriptionInfoList.get(0).getSimSlotIndex() == 0) {
                        secPreferenceCategory2.setVisible(true);
                    } else {
                        secPreferenceCategory3.setVisible(true);
                    }
                } else if (completeActiveSubscriptionInfoList.get(1).getSimSlotIndex() == 0) {
                    secPreferenceCategory2.setVisible(true);
                } else {
                    secPreferenceCategory3.setVisible(true);
                }
                z = true;
            } else {
                z = false;
                for (int i = 0;
                        completeActiveSubscriptionInfoList != null
                                && i < completeActiveSubscriptionInfoList.size();
                        i++) {
                    int simSlotIndex = completeActiveSubscriptionInfoList.get(i).getSimSlotIndex();
                    if (TelephonyManager.getSimStateForSlotIndex(simSlotIndex) != 0) {
                        secPreferenceCategory.setVisible(true);
                        if (completeActiveSubscriptionInfoList.size() > 1) {
                            if (simSlotIndex == 0
                                    && TelephonyManager.getSimStateForSlotIndex(1) != 0) {
                                secPreferenceCategory2.setVisible(true);
                                secPreferenceCategory2.setTitle(
                                        DataUsageUtils.getSimName(getContext(), simSlotIndex));
                                Log.i("DataUsageSummary", "Add Sim Slot 0 Mobile Section");
                            } else if (simSlotIndex == 1
                                    && TelephonyManager.getSimStateForSlotIndex(0) != 0) {
                                secPreferenceCategory3.setVisible(true);
                                secPreferenceCategory3.setTitle(
                                        DataUsageUtils.getSimName(getContext(), simSlotIndex));
                                Log.i("DataUsageSummary", "Add Sim Slot 1 Mobile Section");
                            }
                        }
                        z = true;
                    }
                }
            }
        } else {
            z = false;
        }
        if (SemPersonaManager.isSecureFolderId(UserHandle.semGetMyUserId())) {
            removePreference("status_header");
        }
        if (z
                && Rune.SUPPORT_SMARTMANAGER_CN
                && Utils.isIntentAvailable(
                        getActivity(), "com.samsung.android.uds.SHOW_UDS_ACTIVITY")) {
            inflatePreferences(R.xml.sec_data_usage_uds);
            this.mUDSPreference = (SecPreference) findPreference("ultra_data_saver");
            int i2 = Settings.System.getInt(getActivity().getContentResolver(), "udsState", 0);
            SecPreference secPreference = this.mUDSPreference;
            if (secPreference != null) {
                SecPreferenceUtils.applySummaryColor(secPreference, true);
                this.mUDSPreference.setSummary(
                        getString(i2 == 1 ? R.string.switch_on_text : R.string.switch_off_text));
                this.mUDSPreference.setOnPreferenceClickListener(
                        new Preference
                                .OnPreferenceClickListener() { // from class:
                                                               // com.android.settings.datausage.DataUsageSummary.7
                            @Override // androidx.preference.Preference.OnPreferenceClickListener
                            public final boolean onPreferenceClick(Preference preference) {
                                Intent intent = new Intent();
                                intent.setAction("com.samsung.android.uds.SHOW_UDS_ACTIVITY");
                                DataUsageSummary.this.getActivity().startActivity(intent);
                                return false;
                            }
                        });
            }
        }
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(getContext());
        }
        EnterpriseDeviceManager enterpriseDeviceManager = this.mEDM;
        if (enterpriseDeviceManager != null) {
            this.mRestrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy();
        }
        getActivity()
                .registerReceiver(
                        this.mSimHotSwapReceiver,
                        new IntentFilter("com.samsung.intent.action.SIMHOTSWAP"),
                        2);
        Log.i("DataUsageSummary", "onCreate - end");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        getActivity().unregisterReceiver(this.mSimHotSwapReceiver);
        super.onDestroy();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (Rune.SUPPORT_SMARTMANAGER_CN) {
            getActivity().getContentResolver().unregisterContentObserver(this.mUDSStateObserver);
        }
        getContext().getContentResolver().unregisterContentObserver(this.mPcoSettingObserver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updateState$3();
        if (Rune.SUPPORT_SMARTMANAGER_CN) {
            getActivity()
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.System.getUriFor("udsState"), false, this.mUDSStateObserver);
            int i = Settings.System.getInt(getActivity().getContentResolver(), "udsState", 0);
            SecPreference secPreference = this.mUDSPreference;
            if (secPreference != null) {
                secPreference.setSummary(
                        i == 1
                                ? getString(R.string.switch_on_text)
                                : getString(R.string.switch_off_text));
            }
        }
        getContext()
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("background_data_by_pco"),
                        false,
                        this.mPcoSettingObserver);
        Log.i("DataUsageSummary", "onResume - end");
    }

    public final void updateState$3() {
        Log.i("DataUsageSummary", "updateState-start");
        int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        SecPreferenceCategory secPreferenceCategory =
                (SecPreferenceCategory) findPreference("roaming_category");
        if (DataUsageUtils.isDataRoaming(defaultDataSubscriptionId)) {
            secPreferenceCategory.setVisible(true);
        } else {
            secPreferenceCategory.setVisible(false);
        }
        if (this.mDataSaverPreference != null) {
            RestrictionPolicy restrictionPolicy = this.mRestrictionPolicy;
            if (restrictionPolicy == null
                    || (restrictionPolicy.isDataSavingAllowed()
                            && this.mRestrictionPolicy.isBackgroundDataEnabled())) {
                this.mDataSaverPreference.setEnabled(true);
            } else {
                this.mDataSaverPreference.setEnabled(false);
            }
        }
        DataSaverPreference dataSaverPreference = this.mDataSaverPreference;
        if (dataSaverPreference != null && this.mIsSecureFolderMode) {
            dataSaverPreference.setSummary((CharSequence) null);
        }
        DataUsageSummaryPreference dataUsageSummaryPreference = this.mSummaryPreference;
        if (dataUsageSummaryPreference != null) {
            this.mSummaryController.updateState(dataUsageSummaryPreference);
        }
    }
}
