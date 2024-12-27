package com.samsung.android.settings.wifi.intelligent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticOutline0;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.search.Indexable$SearchIndexProvider;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.appconfig.ApplicationRestrictionsManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SmartNetworkSwitchSettings extends SettingsPreferenceFragment {
    public static final Indexable$SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass5();
    public SnsRadioButtonPreference mAggBtn;
    public FragmentActivity mContext;
    public final AnonymousClass1 mDataRoamingObserver;
    public SecPreferenceScreen mExcludedNetworkPref;
    public IntentFilter mFilter;
    public final AnonymousClass1 mMobileDataObserver;
    public SnsRadioButtonPreference mNormalBtn;
    public WifiSmartNetworkSwitchEnabler mSnsEnabler;
    public PreferenceScreen mSnsSettingPref;
    public SettingsMainSwitchBar mSwitchBar;
    public SwitchPreferenceCompat mSwitchForIndividualApps;
    public WifiManager mWifiManager;
    public boolean mEnabled = false;
    public boolean mAggressiveEnabled = false;
    public boolean mIsStartedFromHintCard = false;
    public long mStartedFromHintCardTime = -1;
    public final AnonymousClass3 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.intelligent.SmartNetworkSwitchSettings.3
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                        int intExtra = intent.getIntExtra("wifi_state", 4);
                        if (intExtra == 3 || intExtra == 1) {
                            SmartNetworkSwitchSettings smartNetworkSwitchSettings =
                                    SmartNetworkSwitchSettings.this;
                            Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                                    SmartNetworkSwitchSettings.SEARCH_INDEX_DATA_PROVIDER;
                            smartNetworkSwitchSettings.finishSnsSettings();
                            return;
                        }
                        return;
                    }
                    if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                        SmartNetworkSwitchSettings smartNetworkSwitchSettings2 =
                                SmartNetworkSwitchSettings.this;
                        Indexable$SearchIndexProvider indexable$SearchIndexProvider2 =
                                SmartNetworkSwitchSettings.SEARCH_INDEX_DATA_PROVIDER;
                        smartNetworkSwitchSettings2.finishSnsSettings();
                    } else if (action.equals("android.intent.action.SIM_STATE_CHANGED")
                            || action.equals("android.intent.action.ANY_DATA_STATE")) {
                        SmartNetworkSwitchSettings smartNetworkSwitchSettings3 =
                                SmartNetworkSwitchSettings.this;
                        Indexable$SearchIndexProvider indexable$SearchIndexProvider3 =
                                SmartNetworkSwitchSettings.SEARCH_INDEX_DATA_PROVIDER;
                        smartNetworkSwitchSettings3.finishSnsSettings();
                    }
                }
            };
    public final AnonymousClass4 mOnHierarchyChangeListener = new AnonymousClass4();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.intelligent.SmartNetworkSwitchSettings$5, reason: invalid class name */
    public final class AnonymousClass5 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            Bundle applicationRestrictions;
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            WifiManager wifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
            SemWifiManager semWifiManager =
                    (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
            boolean z = semWifiManager == null || semWifiManager.isIndividualAppSupported();
            int updateSmartNetworkSwitchAvailability =
                    Utils.updateSmartNetworkSwitchAvailability(context);
            boolean z2 = UserHandle.myUserId() == 0;
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            AccessibilitySettings$$ExternalSyntheticOutline0.m(
                    arrayList,
                    "sns_settings",
                    "mode_normal_mode",
                    "mode_aggressive_mode",
                    "empty_general");
            ApplicationRestrictionsManager applicationRestrictionsManager =
                    ApplicationRestrictionsManager.getInstance(context);
            if (applicationRestrictionsManager != null
                    && (applicationRestrictions =
                                    applicationRestrictionsManager.getApplicationRestrictions(
                                            KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, 0))
                            != null) {
                if (applicationRestrictions.containsKey("wifi_switch_for_individual_apps")
                        && applicationRestrictions.getBundle("wifi_switch_for_individual_apps")
                                != null
                        && (applicationRestrictions
                                        .getBundle("wifi_switch_for_individual_apps")
                                        .getBoolean("hide")
                                || applicationRestrictions
                                        .getBundle("wifi_switch_for_individual_apps")
                                        .getBoolean("grayout"))) {
                    arrayList.add("wifi_switch_for_individual_apps");
                }
                if (applicationRestrictions.containsKey("sns_excluded_device")
                        && applicationRestrictions.getBundle("sns_excluded_device") != null
                        && (applicationRestrictions
                                        .getBundle("sns_excluded_device")
                                        .getBoolean("hide")
                                || applicationRestrictions
                                        .getBundle("sns_excluded_device")
                                        .getBoolean("grayout"))) {
                    arrayList.add("sns_excluded_device");
                }
                if (applicationRestrictions.containsKey("sns_excluded_device_vzw")
                        && applicationRestrictions.getBundle("sns_excluded_device_vzw") != null
                        && (applicationRestrictions
                                        .getBundle("sns_excluded_device_vzw")
                                        .getBoolean("hide")
                                || applicationRestrictions
                                        .getBundle("sns_excluded_device_vzw")
                                        .getBoolean("grayout"))) {
                    arrayList.add("sns_excluded_device_vzw");
                }
            }
            if ((wifiManager != null && !wifiManager.isWifiEnabled())
                    || updateSmartNetworkSwitchAvailability == 2
                    || updateSmartNetworkSwitchAvailability == 3
                    || updateSmartNetworkSwitchAvailability == 4
                    || updateSmartNetworkSwitchAvailability == 5
                    || !z2) {
                arrayList.add("sns_excluded_device");
                arrayList.add("sns_excluded_device_vzw");
                arrayList.add("wifi_switch_for_individual_apps");
            } else if (Utils.updateSmartNetworkSwitchAvailability(context) != 1
                    || com.android.settingslib.Utils.isWifiOnly(context)) {
                arrayList.add("wifi_switch_for_individual_apps");
                arrayList.add("sns_excluded_device");
                arrayList.add("sns_excluded_device_vzw");
                if (!z) {
                    arrayList.add("wifi_switch_for_individual_apps");
                }
            } else {
                SemCscFeature.getInstance().getString("SalesCode");
                arrayList.add("sns_excluded_device_vzw");
                if (!z) {
                    arrayList.add("wifi_switch_for_individual_apps");
                }
            }
            Iterator it = arrayList.iterator();
            String str = ApnSettings.MVNO_NONE;
            while (it.hasNext()) {
                str =
                        AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                                str, (String) it.next(), " ");
            }
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "Remove preferences at Smart network switch Searching Result : ",
                    str,
                    "SmartNetworkSwitchSettings");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            ArrayList arrayList = new ArrayList();
            Resources resources = context.getResources();
            new SearchIndexableRaw(context);
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw).key = "wifi_switch_for_individual_apps";
            searchIndexableRaw.title =
                    String.valueOf(R.string.wifi_smart_network_switch_switch_for_individual_apps);
            searchIndexableRaw.screenTitle =
                    resources.getString(R.string.wifi_switch_to_mobile_data);
            searchIndexableRaw.summaryOn =
                    resources.getString(
                            R.string.wifi_smart_network_switch_switch_for_individual_apps_detail);
            arrayList.add(searchIndexableRaw);
            SemCscFeature.getInstance().getString("SalesCode");
            SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
            ((SearchIndexableData) searchIndexableRaw2).key = "sns_excluded_device";
            searchIndexableRaw2.title =
                    String.valueOf(R.string.wifi_smart_network_switch_excluded_networks);
            searchIndexableRaw2.screenTitle =
                    resources.getString(R.string.wifi_switch_to_mobile_data);
            if (Utils.isTablet()) {
                searchIndexableRaw2.summaryOn =
                        resources.getString(
                                R.string.wifi_smart_network_switch_excluded_networks_body_tablet);
            } else {
                searchIndexableRaw2.summaryOn =
                        resources.getString(
                                R.string.wifi_smart_network_switch_excluded_networks_body);
            }
            arrayList.add(searchIndexableRaw2);
            return arrayList;
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.wifi.intelligent.SmartNetworkSwitchSettings$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.wifi.intelligent.SmartNetworkSwitchSettings$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.samsung.android.settings.wifi.intelligent.SmartNetworkSwitchSettings$3] */
    public SmartNetworkSwitchSettings() {
        final int i = 0;
        this.mMobileDataObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.wifi.intelligent.SmartNetworkSwitchSettings.1
                    public final /* synthetic */ SmartNetworkSwitchSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i) {
                            case 0:
                                SmartNetworkSwitchSettings smartNetworkSwitchSettings = this.this$0;
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                                        SmartNetworkSwitchSettings.SEARCH_INDEX_DATA_PROVIDER;
                                smartNetworkSwitchSettings.finishSnsSettings();
                                break;
                            default:
                                SmartNetworkSwitchSettings smartNetworkSwitchSettings2 =
                                        this.this$0;
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider2 =
                                        SmartNetworkSwitchSettings.SEARCH_INDEX_DATA_PROVIDER;
                                smartNetworkSwitchSettings2.finishSnsSettings();
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mDataRoamingObserver =
                new ContentObserver(
                        this,
                        new Handler()) { // from class:
                                         // com.samsung.android.settings.wifi.intelligent.SmartNetworkSwitchSettings.1
                    public final /* synthetic */ SmartNetworkSwitchSettings this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        switch (i2) {
                            case 0:
                                SmartNetworkSwitchSettings smartNetworkSwitchSettings = this.this$0;
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                                        SmartNetworkSwitchSettings.SEARCH_INDEX_DATA_PROVIDER;
                                smartNetworkSwitchSettings.finishSnsSettings();
                                break;
                            default:
                                SmartNetworkSwitchSettings smartNetworkSwitchSettings2 =
                                        this.this$0;
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider2 =
                                        SmartNetworkSwitchSettings.SEARCH_INDEX_DATA_PROVIDER;
                                smartNetworkSwitchSettings2.finishSnsSettings();
                                break;
                        }
                    }
                };
    }

    public final void finishSnsSettings() {
        WifiManager wifiManager = this.mWifiManager;
        if ((wifiManager != null && !wifiManager.isWifiEnabled())
                || Utils.updateSmartNetworkSwitchAvailability(getActivity()) == 2
                || Utils.updateSmartNetworkSwitchAvailability(getActivity()) == 3
                || Utils.updateSmartNetworkSwitchAvailability(getActivity()) == 4
                || Utils.updateSmartNetworkSwitchAvailability(getActivity()) == 5) {
            if (!Utils.isTablet()
                    || getFragmentManager() == null
                    || getFragmentManager().getBackStackEntryCount() <= 0) {
                finish();
            } else {
                getFragmentManager().popBackStack();
            }
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 89;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mContext = getActivity();
        SemCscFeature.getInstance().getString("SalesCode");
        this.mExcludedNetworkPref = (SecPreferenceScreen) findPreference("sns_excluded_device");
        this.mNormalBtn = (SnsRadioButtonPreference) findPreference("mode_normal_mode");
        this.mAggBtn = (SnsRadioButtonPreference) findPreference("mode_aggressive_mode");
        this.mNormalBtn.setVisible(false);
        this.mAggBtn.setVisible(false);
        this.mNormalBtn = null;
        this.mAggBtn = null;
        if (Utils.isTablet()) {
            this.mExcludedNetworkPref.setSummary(
                    R.string.wifi_smart_network_switch_excluded_networks_body_tablet);
        }
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.setChecked(this.mEnabled);
        this.mSwitchBar.show();
        this.mSnsSettingPref = getPreferenceScreen();
        this.mExcludedNetworkPref.setEnabled(true);
        SemWifiManager semWifiManager =
                (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mSwitchForIndividualApps =
                (SwitchPreferenceCompat) findPreference("wifi_switch_for_individual_apps");
        if (semWifiManager == null || !semWifiManager.isIndividualAppSupported()) {
            SwitchPreferenceCompat switchPreferenceCompat = this.mSwitchForIndividualApps;
            if (switchPreferenceCompat != null) {
                switchPreferenceCompat.setVisible(false);
                try {
                    this.mSnsSettingPref.removePreference(this.mSwitchForIndividualApps);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.mSwitchForIndividualApps = null;
        }
        this.mSnsEnabler =
                new WifiSmartNetworkSwitchEnabler(
                        this,
                        this.mSwitchBar,
                        this.mExcludedNetworkPref,
                        this.mNormalBtn,
                        this.mAggBtn,
                        this.mSwitchForIndividualApps);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        setActionBarTitle();
        "vzw".equalsIgnoreCase(SemCscFeature.getInstance().getString("SalesCode"));
        addPreferencesFromResource(R.xml.smart_network_switch_settings);
        this.mWifiManager = (WifiManager) getActivity().getSystemService(ImsProfile.PDN_WIFI);
        this.mEnabled =
                Settings.Global.getInt(
                                getActivity().getContentResolver(),
                                "wifi_watchdog_poor_network_test_enabled",
                                0)
                        == 1;
        this.mAggressiveEnabled =
                Settings.Global.getInt(
                                getActivity().getContentResolver(),
                                "wifi_watchdog_poor_network_aggressive_mode_on",
                                0)
                        == 1;
        Log.d(
                "SmartNetworkSwitchSettings",
                "Smart Network Switch Aggressive Mode Enabled : " + this.mAggressiveEnabled);
        IntentFilter intentFilter = new IntentFilter();
        this.mFilter = intentFilter;
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        this.mFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        this.mFilter.addAction("android.intent.action.ANY_DATA_STATE");
        this.mFilter.addAction("android.intent.action.AIRPLANE_MODE");
        getActivity().getString(R.string.screen_wifi_adaptive_wifi_setting);
        Optional.ofNullable(getIntent())
                .map(new SmartNetworkSwitchSettings$$ExternalSyntheticLambda0())
                .ifPresent(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.wifi.intelligent.SmartNetworkSwitchSettings$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                SmartNetworkSwitchSettings smartNetworkSwitchSettings =
                                        SmartNetworkSwitchSettings.this;
                                Bundle bundle2 = (Bundle) obj;
                                Indexable$SearchIndexProvider indexable$SearchIndexProvider =
                                        SmartNetworkSwitchSettings.SEARCH_INDEX_DATA_PROVIDER;
                                smartNetworkSwitchSettings.getClass();
                                smartNetworkSwitchSettings.mIsStartedFromHintCard =
                                        bundle2.getBoolean("hint_card", false);
                                smartNetworkSwitchSettings.mStartedFromHintCardTime =
                                        bundle2.getLong("hint_card_timestamp", 0L);
                                Log.i(
                                        "SmartNetworkSwitchSettings",
                                        "onCreate() hint_card:"
                                                + smartNetworkSwitchSettings.mIsStartedFromHintCard
                                                + " time: "
                                                + smartNetworkSwitchSettings
                                                        .mStartedFromHintCardTime);
                            }
                        });
        setHeaderView(
                getLayoutInflater().inflate(R.layout.sec_sns_explain_intelligent, (ViewGroup) null),
                true);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mSwitchBar.hide();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            Log.i("SmartNetworkSwitchSettings", "Action bar up button");
            LoggingHelper.insertEventLogging(3118, 3110);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        WifiSmartNetworkSwitchEnabler wifiSmartNetworkSwitchEnabler = this.mSnsEnabler;
        if (wifiSmartNetworkSwitchEnabler != null) {
            wifiSmartNetworkSwitchEnabler.mContext.unregisterReceiver(
                    wifiSmartNetworkSwitchEnabler.mReceiver);
            wifiSmartNetworkSwitchEnabler
                    .mContext
                    .getContentResolver()
                    .unregisterContentObserver(wifiSmartNetworkSwitchEnabler.mMobileDataObserver);
            SettingsMainSwitchBar settingsMainSwitchBar = wifiSmartNetworkSwitchEnabler.mSwitchBar;
            if (settingsMainSwitchBar != null) {
                settingsMainSwitchBar.removeOnSwitchChangeListener(wifiSmartNetworkSwitchEnabler);
            }
            SnsRadioButtonPreference snsRadioButtonPreference =
                    wifiSmartNetworkSwitchEnabler.mNormal;
            if (snsRadioButtonPreference != null) {
                snsRadioButtonPreference.mListener = null;
            }
            SnsRadioButtonPreference snsRadioButtonPreference2 = wifiSmartNetworkSwitchEnabler.mAgg;
            if (snsRadioButtonPreference2 != null) {
                snsRadioButtonPreference2.mListener = null;
            }
        }
        getActivity().unregisterReceiver(this.mReceiver);
        getActivity().getContentResolver().unregisterContentObserver(this.mMobileDataObserver);
        getActivity().getContentResolver().unregisterContentObserver(this.mDataRoamingObserver);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        key.getClass();
        if (key.equals("sns_excluded_device_vzw") || key.equals("sns_excluded_device")) {
            Log.d("SmartNetworkSwitchSettings", "Excluded Network Preference Selected");
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
            String canonicalName = WifiExceptionNetworkSettings.class.getCanonicalName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = canonicalName;
            launchRequest.mArguments = null;
            launchRequest.mSourceMetricsCategory = 89;
            launchRequest.mTitle =
                    getContext().getString(R.string.wifi_smart_network_switch_excluded_networks);
            subSettingLauncher.launch();
            LoggingHelper.insertEventLogging(3118, 3120);
        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        SnsRadioButtonPreference snsRadioButtonPreference;
        super.onResume();
        setActionBarTitle();
        WifiSmartNetworkSwitchEnabler wifiSmartNetworkSwitchEnabler = this.mSnsEnabler;
        if (wifiSmartNetworkSwitchEnabler != null) {
            wifiSmartNetworkSwitchEnabler.mContext.registerReceiver(
                    wifiSmartNetworkSwitchEnabler.mReceiver,
                    wifiSmartNetworkSwitchEnabler.mFilter,
                    "android.permission.CHANGE_NETWORK_STATE",
                    null);
            wifiSmartNetworkSwitchEnabler
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.Global.getUriFor("mobile_data"),
                            false,
                            wifiSmartNetworkSwitchEnabler.mMobileDataObserver);
            Settings.Global.getInt(
                    wifiSmartNetworkSwitchEnabler.mContext.getContentResolver(),
                    "wifi_watchdog_poor_network_aggressive_mode_on",
                    0);
            SettingsMainSwitchBar settingsMainSwitchBar = wifiSmartNetworkSwitchEnabler.mSwitchBar;
            if (settingsMainSwitchBar != null) {
                settingsMainSwitchBar.addOnSwitchChangeListener(wifiSmartNetworkSwitchEnabler);
            }
            SnsRadioButtonPreference snsRadioButtonPreference2 = wifiSmartNetworkSwitchEnabler.mAgg;
            if (snsRadioButtonPreference2 != null
                    && (snsRadioButtonPreference = wifiSmartNetworkSwitchEnabler.mNormal) != null) {
                snsRadioButtonPreference2.mListener = wifiSmartNetworkSwitchEnabler;
                snsRadioButtonPreference.mListener = wifiSmartNetworkSwitchEnabler;
            }
            wifiSmartNetworkSwitchEnabler.updateRadioGroup();
            wifiSmartNetworkSwitchEnabler.updateSmartNetworkSwitchServiceCheck();
            boolean z =
                    Settings.Global.getInt(
                                    wifiSmartNetworkSwitchEnabler.mContext.getContentResolver(),
                                    "wifi_switch_for_individual_apps_enabled",
                                    0)
                            == 1;
            wifiSmartNetworkSwitchEnabler.mIsSwitchForIndividualAppsEnabled = z;
            SwitchPreferenceCompat switchPreferenceCompat =
                    wifiSmartNetworkSwitchEnabler.mSwitchForIndividualApps;
            if (switchPreferenceCompat != null) {
                switchPreferenceCompat.setChecked(z);
            }
        }
        this.mEnabled =
                Settings.Global.getInt(
                                getActivity().getContentResolver(),
                                "wifi_watchdog_poor_network_test_enabled",
                                0)
                        == 1;
        this.mAggressiveEnabled =
                Settings.Global.getInt(
                                getActivity().getContentResolver(),
                                "wifi_watchdog_poor_network_aggressive_mode_on",
                                0)
                        == 1;
        getActivity()
                .registerReceiver(
                        this.mReceiver,
                        this.mFilter,
                        "android.permission.CHANGE_NETWORK_STATE",
                        null);
        getActivity()
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("mobile_data"), false, this.mMobileDataObserver);
        getActivity()
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("data_roaming"),
                        false,
                        this.mDataRoamingObserver);
        finishSnsSettings();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        LoggingHelper.insertFlowLogging(3118);
    }

    public final void setActionBarTitle() {
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(
                    getResources().getString(R.string.wifi_switch_to_mobile_data));
        }
        getActivity().setTitle(getResources().getString(R.string.wifi_switch_to_mobile_data));
        ViewGroup viewGroup =
                (ViewGroup)
                        getActivity()
                                .getWindow()
                                .getDecorView()
                                .findViewById(
                                        getResources()
                                                .getIdentifier(
                                                        "action_bar",
                                                        "id",
                                                        RecentAppOpsAccess
                                                                .ANDROID_SYSTEM_PACKAGE_NAME));
        if (viewGroup != null) {
            viewGroup.setOnHierarchyChangeListener(this.mOnHierarchyChangeListener);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.intelligent.SmartNetworkSwitchSettings$4, reason: invalid class name */
    public final class AnonymousClass4 implements ViewGroup.OnHierarchyChangeListener {
        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public final void onChildViewAdded(View view, View view2) {
            Log.d("SmartNetworkSwitchSettings", "OnHierarchyChangeListener, parent: " + view);
            View childAt = ((ViewGroup) view).getChildAt(1);
            Log.d("SmartNetworkSwitchSettings", "OnHierarchyChangeListener, titleView: " + childAt);
            if (childAt instanceof TextView) {
                Log.d(
                        "SmartNetworkSwitchSettings",
                        "OnHierarchyChangeListener, set the setallcaps to the false.");
                ((TextView) childAt).setAllCaps(true);
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public final void onChildViewRemoved(View view, View view2) {}
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {}
}
