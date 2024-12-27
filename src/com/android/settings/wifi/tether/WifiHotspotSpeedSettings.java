package com.android.settings.wifi.tether;

import android.net.wifi.SoftApConfiguration;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;

import com.android.settings.R;
import com.android.settings.applications.intentpicker.ProgressDialogFragment$$ExternalSyntheticOutline0;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.factory.WifiFeatureProvider;
import com.android.settings.wifi.repository.WifiHotspotRepository;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.knox.EnterpriseContainerCallback;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiHotspotSpeedSettings extends DashboardFragment
        implements SelectorWithWidgetPreference.OnClickListener {
    public static final Map sSpeedKeyMap;
    public final Map mSpeedPreferenceMap = new HashMap();
    public WifiHotspotSpeedViewModel mWifiHotspotSpeedViewModel;

    static {
        HashMap hashMap = new HashMap();
        sSpeedKeyMap = hashMap;
        WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                1, hashMap, "wifi_hotspot_speed_2g", 2, "wifi_hotspot_speed_5g");
        WifiHotspotSpeedSettings$$ExternalSyntheticOutline0.m(
                3, hashMap, "wifi_hotspot_speed_2g_5g", 4, "wifi_hotspot_speed_6g");
    }

    public static void log$1(String str) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getWifiFeatureProvider().verboseLog("WifiHotspotSpeedSettings", str);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiHotspotSpeedSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_SUCCESSFUL;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.wifi_hotspot_speed;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        for (Map.Entry entry : ((HashMap) sSpeedKeyMap).entrySet()) {
            SelectorWithWidgetPreference selectorWithWidgetPreference =
                    (SelectorWithWidgetPreference) findPreference((CharSequence) entry.getKey());
            if (selectorWithWidgetPreference != null) {
                selectorWithWidgetPreference.mListener = this;
                ((HashMap) this.mSpeedPreferenceMap)
                        .put((Integer) entry.getValue(), selectorWithWidgetPreference);
            }
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        WifiFeatureProvider wifiFeatureProvider = featureFactoryImpl.getWifiFeatureProvider();
        wifiFeatureProvider.getClass();
        ViewModelStore store = getViewModelStore();
        ViewModelProvider.Factory factory = getDefaultViewModelProviderFactory();
        CreationExtras defaultViewModelCreationExtras = getDefaultViewModelCreationExtras();
        Intrinsics.checkNotNullParameter(store, "store");
        Intrinsics.checkNotNullParameter(factory, "factory");
        ViewModelProviderImpl m =
                ProgressDialogFragment$$ExternalSyntheticOutline0.m(
                        defaultViewModelCreationExtras,
                        "defaultCreationExtras",
                        store,
                        factory,
                        defaultViewModelCreationExtras);
        KClass kotlinClass = JvmClassMappingKt.getKotlinClass(WifiHotspotSpeedViewModel.class);
        String qualifiedName = kotlinClass.getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException(
                    "Local and anonymous classes can not be ViewModels".toString());
        }
        WifiHotspotSpeedViewModel wifiHotspotSpeedViewModel =
                (WifiHotspotSpeedViewModel)
                        m.getViewModel$lifecycle_viewmodel_release(
                                kotlinClass,
                                "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                        .concat(qualifiedName));
        wifiFeatureProvider.verboseLog(
                "WifiFeatureProvider",
                "getWifiHotspotSpeedViewModel():" + wifiHotspotSpeedViewModel);
        this.mWifiHotspotSpeedViewModel = wifiHotspotSpeedViewModel;
        onSpeedInfoMapDataChanged((Map) wifiHotspotSpeedViewModel.getSpeedInfoMapData().getValue());
        final int i = 0;
        this.mWifiHotspotSpeedViewModel
                .getSpeedInfoMapData()
                .observe(
                        this,
                        new Observer(
                                this) { // from class:
                                        // com.android.settings.wifi.tether.WifiHotspotSpeedSettings$$ExternalSyntheticLambda1
                            public final /* synthetic */ WifiHotspotSpeedSettings f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                int i2 = i;
                                WifiHotspotSpeedSettings wifiHotspotSpeedSettings = this.f$0;
                                switch (i2) {
                                    case 0:
                                        wifiHotspotSpeedSettings.onSpeedInfoMapDataChanged(
                                                (Map) obj);
                                        break;
                                    default:
                                        wifiHotspotSpeedSettings.onRestartingChanged((Boolean) obj);
                                        break;
                                }
                            }
                        });
        final int i2 = 1;
        this.mWifiHotspotSpeedViewModel
                .mWifiHotspotRepository
                .getRestarting()
                .observe(
                        this,
                        new Observer(
                                this) { // from class:
                                        // com.android.settings.wifi.tether.WifiHotspotSpeedSettings$$ExternalSyntheticLambda1
                            public final /* synthetic */ WifiHotspotSpeedSettings f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                int i22 = i2;
                                WifiHotspotSpeedSettings wifiHotspotSpeedSettings = this.f$0;
                                switch (i22) {
                                    case 0:
                                        wifiHotspotSpeedSettings.onSpeedInfoMapDataChanged(
                                                (Map) obj);
                                        break;
                                    default:
                                        wifiHotspotSpeedSettings.onRestartingChanged((Boolean) obj);
                                        break;
                                }
                            }
                        });
    }

    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference.OnClickListener
    public final void onRadioButtonClicked(
            SelectorWithWidgetPreference selectorWithWidgetPreference) {
        String key = selectorWithWidgetPreference.getKey();
        log$1("onRadioButtonClicked(), key:" + key);
        HashMap hashMap = (HashMap) sSpeedKeyMap;
        if (hashMap.containsKey(key)) {
            WifiHotspotSpeedViewModel wifiHotspotSpeedViewModel = this.mWifiHotspotSpeedViewModel;
            Integer num = (Integer) hashMap.get(key);
            wifiHotspotSpeedViewModel.getClass();
            int intValue = num.intValue();
            WifiHotspotRepository wifiHotspotRepository =
                    wifiHotspotSpeedViewModel.mWifiHotspotRepository;
            wifiHotspotRepository.getClass();
            WifiHotspotRepository.log("setSpeedType():" + intValue);
            if (wifiHotspotRepository.mSpeedType == null) {
                wifiHotspotRepository.getSpeedType();
            }
            if (intValue == ((Integer) wifiHotspotRepository.mSpeedType.getValue()).intValue()) {
                Log.w(
                        "WifiHotspotRepository",
                        "setSpeedType() is no changed! mSpeedType:"
                                + wifiHotspotRepository.mSpeedType.getValue());
                return;
            }
            SoftApConfiguration softApConfiguration =
                    wifiHotspotRepository.mWifiManager.getSoftApConfiguration();
            if (softApConfiguration == null) {
                wifiHotspotRepository.mSpeedType.setValue(0);
                Log.e(
                        "WifiHotspotRepository",
                        "setSpeedType(), WifiManager#getSoftApConfiguration() return null!");
                return;
            }
            SoftApConfiguration.Builder builder =
                    new SoftApConfiguration.Builder(softApConfiguration);
            if (intValue == 4) {
                WifiHotspotRepository.log("setSpeedType(), setBand(BAND_2GHZ_5GHZ_6GHZ)");
                builder.setBand(7);
                if (softApConfiguration.getSecurityType() != 3) {
                    WifiHotspotRepository.log(
                            "setSpeedType(), setPassphrase(SECURITY_TYPE_WPA3_SAE)");
                    builder.setPassphrase(
                            wifiHotspotRepository.generatePassword(softApConfiguration), 3);
                }
            } else {
                if (intValue == 2) {
                    WifiHotspotRepository.log("setSpeedType(), setBand(BAND_2GHZ_5GHZ)");
                    builder.setBand(3);
                } else if (wifiHotspotRepository.mIsDualBand.booleanValue()) {
                    WifiHotspotRepository.log(
                            "setSpeedType(), setBands(BAND_2GHZ + BAND_2GHZ_5GHZ)");
                    builder.setBands(new int[] {1, 3});
                } else {
                    WifiHotspotRepository.log("setSpeedType(), setBand(BAND_2GHZ)");
                    builder.setBand(1);
                }
                if ((softApConfiguration.getBand() & 4) != 0) {
                    builder.setPassphrase(
                            wifiHotspotRepository.generatePassword(softApConfiguration), 2);
                }
            }
            wifiHotspotRepository.setSoftApConfiguration(builder.build());
        }
    }

    public void onRestartingChanged(Boolean bool) {
        log$1("onRestartingChanged(), restarting:" + bool);
        setLoading(bool.booleanValue(), false);
    }

    public final void onSpeedInfoMapDataChanged(Map map) {
        SelectorWithWidgetPreference selectorWithWidgetPreference;
        log$1("onSpeedViewDataChanged(), speedInfoMap:" + map);
        for (Map.Entry entry : ((HashMap) this.mSpeedPreferenceMap).entrySet()) {
            WifiHotspotSpeedViewModel.SpeedInfo speedInfo =
                    (WifiHotspotSpeedViewModel.SpeedInfo) map.get(entry.getKey());
            if (speedInfo != null
                    && (selectorWithWidgetPreference =
                                    (SelectorWithWidgetPreference) entry.getValue())
                            != null) {
                if (speedInfo.mIsVisible) {
                    selectorWithWidgetPreference.setEnabled(speedInfo.mIsEnabled);
                    selectorWithWidgetPreference.setChecked(speedInfo.mIsChecked.booleanValue());
                    String str = speedInfo.mSummary;
                    if (str != null) {
                        selectorWithWidgetPreference.setSummary(str);
                    }
                    selectorWithWidgetPreference.setVisible(true);
                } else {
                    selectorWithWidgetPreference.setVisible(false);
                }
            }
        }
    }
}
