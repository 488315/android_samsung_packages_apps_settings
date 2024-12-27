package com.android.settings.wifi.tether;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.SoftApConfiguration;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticOutline0;
import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settings.wifi.WifiUtils;
import com.android.settings.wifi.repository.SharedConnectivityRepository;
import com.android.settingslib.TetherUtil;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;

import com.samsung.android.knox.EnterpriseContainerCallback;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiTetherSettings extends RestrictedDashboardFragment
        implements WifiTetherBasePreferenceController.OnTetherConfigUpdateListener {
    static final String KEY_INSTANT_HOTSPOT = "wifi_hotspot_instant";
    static final String KEY_WIFI_HOTSPOT_SECURITY = "wifi_hotspot_security";
    static final String KEY_WIFI_HOTSPOT_SPEED = "wifi_hotspot_speed";
    static final String KEY_WIFI_TETHER_AUTO_OFF = "wifi_tether_auto_turn_off";
    static final String KEY_WIFI_TETHER_MAXIMIZE_COMPATIBILITY =
            "wifi_tether_maximize_compatibility";
    static final String KEY_WIFI_TETHER_NETWORK_NAME = "wifi_tether_network_name";
    static final String KEY_WIFI_TETHER_NETWORK_PASSWORD = "wifi_tether_network_password";
    static final String KEY_WIFI_TETHER_SECURITY = "wifi_tether_security";
    Preference mInstantHotspot;
    SettingsMainSwitchBar mMainSwitchBar;
    WifiTetherMaximizeCompatibilityPreferenceController mMaxCompatibilityPrefController;
    WifiTetherPasswordPreferenceController mPasswordPreferenceController;
    WifiTetherSSIDPreferenceController mSSIDPreferenceController;
    WifiTetherSecurityPreferenceController mSecurityPreferenceController;
    public WifiTetherSwitchBarController mSwitchBarController;
    TetherChangeReceiver mTetherChangeReceiver;
    boolean mUnavailable;
    Preference mWifiHotspotSecurity;
    Preference mWifiHotspotSpeed;
    public final WifiRestriction mWifiRestriction;
    WifiTetherAutoOffPreferenceController mWifiTetherAutoOffPreferenceController;
    WifiTetherViewModel mWifiTetherViewModel;
    public static final IntentFilter TETHER_STATE_CHANGE_FILTER =
            new IntentFilter(
                    WifiApMobileDataSharedTodayPreferenceController.ACTION_WIFI_AP_STATE_CHANGED);
    public static final SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new SearchIndexProvider();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class TetherChangeReceiver extends BroadcastReceiver {
        public TetherChangeReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "updating display config due to receiving broadcast action ",
                    intent.getAction(),
                    "WifiTetherSettings");
            WifiTetherSettings wifiTetherSettings = WifiTetherSettings.this;
            IntentFilter intentFilter = WifiTetherSettings.TETHER_STATE_CHANGE_FILTER;
            wifiTetherSettings.updateDisplayWithNewConfig();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class WifiRestriction {}

    public WifiTetherSettings() {
        super("no_config_tethering");
        this.mWifiRestriction = new WifiRestriction();
    }

    public static List buildPreferenceControllers(
            Context context,
            WifiTetherSettings$$ExternalSyntheticLambda2
                    wifiTetherSettings$$ExternalSyntheticLambda2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new WifiTetherSSIDPreferenceController(
                        context, wifiTetherSettings$$ExternalSyntheticLambda2));
        arrayList.add(
                new WifiTetherSecurityPreferenceController(
                        context, wifiTetherSettings$$ExternalSyntheticLambda2));
        arrayList.add(
                new WifiTetherPasswordPreferenceController(
                        context, wifiTetherSettings$$ExternalSyntheticLambda2, null));
        arrayList.add(new WifiTetherAutoOffPreferenceController(context, KEY_WIFI_TETHER_AUTO_OFF));
        WifiTetherMaximizeCompatibilityPreferenceController
                wifiTetherMaximizeCompatibilityPreferenceController =
                        new WifiTetherMaximizeCompatibilityPreferenceController(
                                context, wifiTetherSettings$$ExternalSyntheticLambda2);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        wifiTetherMaximizeCompatibilityPreferenceController.mShouldHidePreference =
                featureFactoryImpl
                        .getWifiFeatureProvider()
                        .getWifiHotspotRepository()
                        .isSpeedFeatureAvailable();
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("mShouldHidePreference:"),
                wifiTetherMaximizeCompatibilityPreferenceController.mShouldHidePreference,
                "WifiTetherMaximizeCompatibilityPref");
        if (!wifiTetherMaximizeCompatibilityPreferenceController.mShouldHidePreference) {
            wifiTetherMaximizeCompatibilityPreferenceController.mIsChecked =
                    wifiTetherMaximizeCompatibilityPreferenceController
                            .isMaximizeCompatibilityEnabled();
        }
        arrayList.add(wifiTetherMaximizeCompatibilityPreferenceController);
        return arrayList;
    }

    public SoftApConfiguration buildNewConfig() {
        String str;
        SoftApConfiguration softApConfiguration =
                this.mWifiTetherViewModel.mWifiHotspotRepository.mWifiManager
                        .getSoftApConfiguration();
        SoftApConfiguration.Builder builder = new SoftApConfiguration.Builder(softApConfiguration);
        builder.setSsid(this.mSSIDPreferenceController.mSSID);
        int securityType =
                this.mWifiTetherViewModel.mWifiHotspotRepository.isSpeedFeatureAvailable()
                        ? softApConfiguration.getSecurityType()
                        : this.mSecurityPreferenceController.mSecurityValue;
        if (securityType == 0) {
            str = null;
        } else {
            WifiTetherPasswordPreferenceController wifiTetherPasswordPreferenceController =
                    this.mPasswordPreferenceController;
            if (securityType == 0) {
                wifiTetherPasswordPreferenceController.getClass();
                str = ApnSettings.MVNO_NONE;
            } else {
                if (!WifiUtils.isHotspotPasswordValid(
                        securityType, wifiTetherPasswordPreferenceController.mPassword)) {
                    wifiTetherPasswordPreferenceController.mPassword =
                            wifiTetherPasswordPreferenceController.mWifiHotspotRepository
                                    .generatePassword();
                    wifiTetherPasswordPreferenceController.updatePasswordDisplay(
                            (EditTextPreference)
                                    wifiTetherPasswordPreferenceController.mPreference);
                }
                str = wifiTetherPasswordPreferenceController.mPassword;
            }
        }
        builder.setPassphrase(str, securityType);
        if (!this.mWifiTetherViewModel.mWifiHotspotRepository.isSpeedFeatureAvailable()) {
            WifiTetherMaximizeCompatibilityPreferenceController
                    wifiTetherMaximizeCompatibilityPreferenceController =
                            this.mMaxCompatibilityPrefController;
            boolean z = wifiTetherMaximizeCompatibilityPreferenceController.mIsChecked;
            if (wifiTetherMaximizeCompatibilityPreferenceController.mWifiManager
                    .isBridgedApConcurrencySupported()) {
                builder.setBands(new int[] {1, 3});
                Log.d(
                        "WifiTetherMaximizeCompatibilityPref",
                        "setBridgedModeOpportunisticShutdownEnabled:" + z);
                builder.setBridgedModeOpportunisticShutdownEnabled(z ^ true);
            } else {
                int i = z ? 1 : 3;
                Log.d("WifiTetherMaximizeCompatibilityPref", "setBand:" + i);
                builder.setBand(i);
            }
        }
        builder.setAutoShutdownEnabled(this.mWifiTetherAutoOffPreferenceController.isEnabled());
        return builder.build();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers(
                context, new WifiTetherSettings$$ExternalSyntheticLambda2(this));
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiTetherSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_SUCCESSFUL;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.wifi_tether_settings;
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (this.mUnavailable) {
            return;
        }
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        SettingsMainSwitchBar settingsMainSwitchBar = settingsActivity.mMainSwitch;
        this.mMainSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.setTitle(getString(R.string.use_wifi_hotsopt_main_switch_title));
        this.mSwitchBarController =
                new WifiTetherSwitchBarController(settingsActivity, this.mMainSwitchBar);
        getSettingsLifecycle().addObserver(this.mSwitchBarController);
        this.mMainSwitchBar.show();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mTetherChangeReceiver = new TetherChangeReceiver();
        this.mSSIDPreferenceController =
                (WifiTetherSSIDPreferenceController) use(WifiTetherSSIDPreferenceController.class);
        this.mSecurityPreferenceController =
                (WifiTetherSecurityPreferenceController)
                        use(WifiTetherSecurityPreferenceController.class);
        this.mPasswordPreferenceController =
                (WifiTetherPasswordPreferenceController)
                        use(WifiTetherPasswordPreferenceController.class);
        this.mMaxCompatibilityPrefController =
                (WifiTetherMaximizeCompatibilityPreferenceController)
                        use(WifiTetherMaximizeCompatibilityPreferenceController.class);
        this.mWifiTetherAutoOffPreferenceController =
                (WifiTetherAutoOffPreferenceController)
                        use(WifiTetherAutoOffPreferenceController.class);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0044 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0045  */
    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r6) {
        /*
            r5 = this;
            super.onCreate(r6)
            android.content.Context r6 = r5.getContext()
            boolean r6 = com.android.settings.wifi.WifiUtils.canShowWifiHotspot(r6)
            if (r6 != 0) goto L18
            java.lang.String r6 = "WifiTetherSettings"
            java.lang.String r0 = "can not launch Wi-Fi hotspot settings because the config is not set to show."
            android.util.Log.e(r6, r0)
            r5.finish()
            return
        L18:
            r6 = 1
            r5.mOnlyAvailableForAdmins = r6
            boolean r0 = r5.isUiRestricted()
            r1 = 0
            if (r0 != 0) goto L3f
            com.android.settings.wifi.tether.WifiTetherSettings$WifiRestriction r0 = r5.mWifiRestriction
            android.content.Context r2 = r5.getContext()
            r0.getClass()
            if (r2 != 0) goto L2e
            goto L36
        L2e:
            java.lang.String r0 = "no_wifi_tethering"
            boolean r0 = com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils.hasUserRestrictionFromT(r2, r0)
            if (r0 != 0) goto L38
        L36:
            r0 = r1
            goto L40
        L38:
            java.lang.String r0 = "WifiEntResUtils"
            java.lang.String r2 = "Wi-Fi Tethering isn't available due to user restriction."
            android.util.Log.w(r0, r2)
        L3f:
            r0 = r6
        L40:
            r5.mUnavailable = r0
            if (r0 == 0) goto L45
            return
        L45:
            com.android.settings.overlay.FeatureFactoryImpl r0 = com.android.settings.overlay.FeatureFactoryImpl._factory
            if (r0 == 0) goto Lba
            com.android.settings.wifi.factory.WifiFeatureProvider r0 = r0.getWifiFeatureProvider()
            r0.getClass()
            androidx.lifecycle.ViewModelStore r0 = r5.getViewModelStore()
            androidx.lifecycle.ViewModelProvider$Factory r2 = r5.getDefaultViewModelProviderFactory()
            androidx.lifecycle.viewmodel.CreationExtras r3 = r5.getDefaultViewModelCreationExtras()
            java.lang.String r4 = "store"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r4)
            java.lang.String r4 = "factory"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r4)
            java.lang.String r4 = "defaultCreationExtras"
            androidx.lifecycle.viewmodel.ViewModelProviderImpl r0 = com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticOutline0.m(r3, r4, r0, r2, r3)
            java.lang.Class<com.android.settings.wifi.tether.WifiTetherViewModel> r2 = com.android.settings.wifi.tether.WifiTetherViewModel.class
            kotlin.reflect.KClass r2 = kotlin.jvm.JvmClassMappingKt.getKotlinClass(r2)
            java.lang.String r3 = r2.getQualifiedName()
            if (r3 == 0) goto Lae
            java.lang.String r4 = "androidx.lifecycle.ViewModelProvider.DefaultKey:"
            java.lang.String r3 = r4.concat(r3)
            androidx.lifecycle.ViewModel r0 = r0.getViewModel$lifecycle_viewmodel_release(r2, r3)
            com.android.settings.wifi.tether.WifiTetherViewModel r0 = (com.android.settings.wifi.tether.WifiTetherViewModel) r0
            r5.mWifiTetherViewModel = r0
            com.android.settings.wifi.repository.WifiHotspotRepository r0 = r0.mWifiHotspotRepository
            boolean r0 = r0.isSpeedFeatureAvailable()
            r5.setupSpeedFeature(r0)
            com.android.settings.wifi.tether.WifiTetherViewModel r0 = r5.mWifiTetherViewModel
            com.android.settings.wifi.repository.SharedConnectivityRepository r0 = r0.mSharedConnectivityRepository
            android.net.wifi.sharedconnectivity.app.SharedConnectivityManager r0 = r0.mManager
            if (r0 == 0) goto L98
            goto L99
        L98:
            r6 = r1
        L99:
            r5.setupInstantHotspot(r6)
            com.android.settings.wifi.tether.WifiTetherViewModel r6 = r5.mWifiTetherViewModel
            com.android.settings.wifi.repository.WifiHotspotRepository r6 = r6.mWifiHotspotRepository
            androidx.lifecycle.MutableLiveData r6 = r6.getRestarting()
            com.android.settings.wifi.tether.WifiTetherSettings$$ExternalSyntheticLambda3 r0 = new com.android.settings.wifi.tether.WifiTetherSettings$$ExternalSyntheticLambda3
            r1 = 3
            r0.<init>(r5, r1)
            r6.observe(r5, r0)
            return
        Lae:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "Local and anonymous classes can not be ViewModels"
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        Lba:
            java.lang.UnsupportedOperationException r5 = new java.lang.UnsupportedOperationException
            java.lang.String r6 = "No feature factory configured"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.tether.WifiTetherSettings.onCreate(android.os.Bundle):void");
    }

    public void onInstantHotspotChanged(String str) {
        if (str == null) {
            this.mInstantHotspot.setVisible(false);
        } else {
            this.mInstantHotspot.setVisible(true);
            this.mInstantHotspot.setSummary(str);
        }
    }

    public void onRestartingChanged(Boolean bool) {
        this.mMainSwitchBar.setVisibility(bool.booleanValue() ? 4 : 0);
        setLoading(bool.booleanValue(), false);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        WifiRestriction wifiRestriction = this.mWifiRestriction;
        Context context = getContext();
        wifiRestriction.getClass();
        if (context != null
                && WifiEnterpriseRestrictionUtils.hasUserRestrictionFromT(
                        context, "no_wifi_tethering")) {
            Log.w("WifiEntResUtils", "Wi-Fi Tethering isn't available due to user restriction.");
            this.mEmptyTextView.setText(R.string.not_allowed_by_ent);
            getPreferenceScreen().removeAll();
        } else if (this.mUnavailable) {
            if (!isUiRestrictedByOnlyAdmin()) {
                this.mEmptyTextView.setText(R.string.tethering_settings_not_available);
            }
            getPreferenceScreen().removeAll();
        } else {
            Context context2 = getContext();
            if (context2 != null) {
                context2.registerReceiver(
                        this.mTetherChangeReceiver, TETHER_STATE_CHANGE_FILTER, 2);
                updateDisplayWithNewConfig();
            }
            this.mWifiTetherViewModel.mWifiHotspotRepository.refresh();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        Context context;
        super.onStop();
        if (this.mUnavailable || (context = getContext()) == null) {
            return;
        }
        context.unregisterReceiver(this.mTetherChangeReceiver);
    }

    @Override // com.android.settings.wifi.tether.WifiTetherBasePreferenceController.OnTetherConfigUpdateListener
    public final void onTetherConfigUpdated(
            AbstractPreferenceController abstractPreferenceController) {
        SoftApConfiguration buildNewConfig = buildNewConfig();
        WifiTetherPasswordPreferenceController wifiTetherPasswordPreferenceController =
                this.mPasswordPreferenceController;
        int securityType = buildNewConfig.getSecurityType();
        wifiTetherPasswordPreferenceController.mSecurityType = securityType;
        wifiTetherPasswordPreferenceController.mPreference.setVisible(securityType != 0);
        this.mWifiTetherViewModel.mWifiHotspotRepository.setSoftApConfiguration(buildNewConfig);
    }

    public void setupInstantHotspot(boolean z) {
        if (z) {
            Preference findPreference = findPreference(KEY_INSTANT_HOTSPOT);
            this.mInstantHotspot = findPreference;
            if (findPreference == null) {
                Log.e(
                        "WifiTetherSettings",
                        "Failed to find Instant Hotspot preference:wifi_hotspot_instant");
            } else {
                this.mWifiTetherViewModel.mInstantHotspotSummary.observe(
                        this, new WifiTetherSettings$$ExternalSyntheticLambda3(this, 2));
                this.mInstantHotspot.setOnPreferenceClickListener(
                        new WifiTetherSettings$$ExternalSyntheticLambda2(this));
            }
        }
    }

    public void setupSpeedFeature(boolean z) {
        this.mWifiHotspotSecurity = findPreference(KEY_WIFI_HOTSPOT_SECURITY);
        Preference findPreference = findPreference(KEY_WIFI_HOTSPOT_SPEED);
        this.mWifiHotspotSpeed = findPreference;
        Preference preference = this.mWifiHotspotSecurity;
        if (preference == null || findPreference == null) {
            return;
        }
        preference.setVisible(z);
        this.mWifiHotspotSpeed.setVisible(z);
        if (z) {
            WifiTetherViewModel wifiTetherViewModel = this.mWifiTetherViewModel;
            if (wifiTetherViewModel.mSecuritySummary == null) {
                wifiTetherViewModel.mSecuritySummary = new MutableLiveData();
                wifiTetherViewModel
                        .mWifiHotspotRepository
                        .getSecurityType()
                        .observeForever(wifiTetherViewModel.mSecurityTypeObserver);
            }
            wifiTetherViewModel.mSecuritySummary.observe(
                    this, new WifiTetherSettings$$ExternalSyntheticLambda3(this, 0));
            WifiTetherViewModel wifiTetherViewModel2 = this.mWifiTetherViewModel;
            if (wifiTetherViewModel2.mSpeedSummary == null) {
                wifiTetherViewModel2.mSpeedSummary = new MutableLiveData();
                wifiTetherViewModel2
                        .mWifiHotspotRepository
                        .getSpeedType()
                        .observeForever(wifiTetherViewModel2.mSpeedTypeObserver);
            }
            wifiTetherViewModel2.mSpeedSummary.observe(
                    this, new WifiTetherSettings$$ExternalSyntheticLambda3(this, 1));
        }
    }

    public final void updateDisplayWithNewConfig() {
        ((WifiTetherSSIDPreferenceController) use(WifiTetherSSIDPreferenceController.class))
                .updateDisplay$1();
        ((WifiTetherSecurityPreferenceController) use(WifiTetherSecurityPreferenceController.class))
                .updateDisplay$1();
        ((WifiTetherPasswordPreferenceController) use(WifiTetherPasswordPreferenceController.class))
                .updateDisplay$1();
        ((WifiTetherMaximizeCompatibilityPreferenceController)
                        use(WifiTetherMaximizeCompatibilityPreferenceController.class))
                .updateDisplay$1();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class SearchIndexProvider extends BaseSearchIndexProvider {
        public final boolean mIsInstantHotspotEnabled;
        public final WifiRestriction mWifiRestriction;

        public SearchIndexProvider() {
            super(R.xml.wifi_tether_settings);
            this.mWifiRestriction = new WifiRestriction();
            this.mIsInstantHotspotEnabled = SharedConnectivityRepository.isDeviceConfigEnabled();
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return WifiTetherSettings.buildPreferenceControllers(context, null);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            this.mWifiRestriction.getClass();
            if (context == null ? true : TetherUtil.isTetherAvailable(context)) {
                if (context == null
                        || !WifiEnterpriseRestrictionUtils.hasUserRestrictionFromT(
                                context, "no_wifi_tethering")) {
                    if (!this.mIsInstantHotspotEnabled) {
                        ((ArrayList) nonIndexableKeys).add(WifiTetherSettings.KEY_INSTANT_HOTSPOT);
                    }
                    ((ArrayList) nonIndexableKeys).add("wifi_tether_settings_screen");
                    return nonIndexableKeys;
                }
                Log.w(
                        "WifiEntResUtils",
                        "Wi-Fi Tethering isn't available due to user restriction.");
            }
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            AccessibilitySettings$$ExternalSyntheticOutline0.m(
                    arrayList,
                    WifiTetherSettings.KEY_WIFI_TETHER_NETWORK_NAME,
                    WifiTetherSettings.KEY_WIFI_TETHER_SECURITY,
                    WifiTetherSettings.KEY_WIFI_TETHER_NETWORK_PASSWORD,
                    WifiTetherSettings.KEY_WIFI_TETHER_AUTO_OFF);
            arrayList.add(WifiTetherSettings.KEY_WIFI_TETHER_MAXIMIZE_COMPATIBILITY);
            arrayList.add(WifiTetherSettings.KEY_INSTANT_HOTSPOT);
            ((ArrayList) nonIndexableKeys).add("wifi_tether_settings_screen");
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            UserManager userManager;
            if (context != null
                    && (userManager = (UserManager) context.getSystemService(UserManager.class))
                            != null) {
                userManager.isAdminUser();
            }
            return false;
        }

        public SearchIndexProvider(int i, WifiRestriction wifiRestriction, boolean z) {
            super(i);
            this.mWifiRestriction = wifiRestriction;
            this.mIsInstantHotspotEnabled = z;
        }
    }
}
