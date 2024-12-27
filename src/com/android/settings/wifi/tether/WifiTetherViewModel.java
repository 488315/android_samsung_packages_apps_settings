package com.android.settings.wifi.tether;

import android.app.Application;
import android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.factory.WifiFeatureProvider;
import com.android.settings.wifi.repository.SharedConnectivityRepository;
import com.android.settings.wifi.repository.WifiHotspotRepository;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiTetherViewModel extends AndroidViewModel {
    public static final Map sSecuritySummaryResMap;
    public static final Map sSpeedSummaryResMap;
    Observer mInstantHotspotStateObserver;
    MutableLiveData mInstantHotspotSummary;
    public MutableLiveData mSecuritySummary;
    public final WifiTetherViewModel$$ExternalSyntheticLambda0 mSecurityTypeObserver;
    public final SharedConnectivityRepository mSharedConnectivityRepository;
    public MutableLiveData mSpeedSummary;
    public final WifiTetherViewModel$$ExternalSyntheticLambda0 mSpeedTypeObserver;
    public final WifiHotspotRepository mWifiHotspotRepository;

    static {
        HashMap hashMap = new HashMap();
        sSecuritySummaryResMap = hashMap;
        hashMap.put(3, Integer.valueOf(R.string.wifi_security_sae));
        hashMap.put(2, Integer.valueOf(R.string.wifi_security_psk_sae));
        hashMap.put(1, Integer.valueOf(R.string.wifi_security_wpa2));
        hashMap.put(0, Integer.valueOf(R.string.wifi_security_none));
        HashMap hashMap2 = new HashMap();
        sSpeedSummaryResMap = hashMap2;
        hashMap2.put(1, Integer.valueOf(R.string.wifi_hotspot_speed_summary_2g));
        hashMap2.put(2, Integer.valueOf(R.string.wifi_hotspot_speed_summary_5g));
        hashMap2.put(4, Integer.valueOf(R.string.wifi_hotspot_speed_summary_6g));
        hashMap2.put(3, Integer.valueOf(R.string.wifi_hotspot_speed_summary_2g_and_5g));
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.settings.wifi.tether.WifiTetherViewModel$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.settings.wifi.tether.WifiTetherViewModel$$ExternalSyntheticLambda0] */
    public WifiTetherViewModel(Application application) {
        super(application);
        final int i = 0;
        this.mSecurityTypeObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.wifi.tether.WifiTetherViewModel$$ExternalSyntheticLambda0
                    public final /* synthetic */ WifiTetherViewModel f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        switch (i) {
                            case 0:
                                Integer num = (Integer) obj;
                                WifiTetherViewModel wifiTetherViewModel = this.f$0;
                                wifiTetherViewModel.getClass();
                                num.getClass();
                                HashMap hashMap =
                                        (HashMap) WifiTetherViewModel.sSecuritySummaryResMap;
                                wifiTetherViewModel.mSecuritySummary.setValue(
                                        Integer.valueOf(
                                                hashMap.containsKey(num)
                                                        ? ((Integer) hashMap.get(num)).intValue()
                                                        : R.string.summary_placeholder));
                                break;
                            case 1:
                                Integer num2 = (Integer) obj;
                                WifiTetherViewModel wifiTetherViewModel2 = this.f$0;
                                wifiTetherViewModel2.getClass();
                                HashMap hashMap2 =
                                        (HashMap) WifiTetherViewModel.sSpeedSummaryResMap;
                                wifiTetherViewModel2.mSpeedSummary.setValue(
                                        Integer.valueOf(
                                                hashMap2.containsKey(num2)
                                                        ? ((Integer) hashMap2.get(num2)).intValue()
                                                        : R.string.summary_placeholder));
                                break;
                            default:
                                this.f$0.onInstantHotspotStateChanged(
                                        (SharedConnectivitySettingsState) obj);
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mSpeedTypeObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.wifi.tether.WifiTetherViewModel$$ExternalSyntheticLambda0
                    public final /* synthetic */ WifiTetherViewModel f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        switch (i2) {
                            case 0:
                                Integer num = (Integer) obj;
                                WifiTetherViewModel wifiTetherViewModel = this.f$0;
                                wifiTetherViewModel.getClass();
                                num.getClass();
                                HashMap hashMap =
                                        (HashMap) WifiTetherViewModel.sSecuritySummaryResMap;
                                wifiTetherViewModel.mSecuritySummary.setValue(
                                        Integer.valueOf(
                                                hashMap.containsKey(num)
                                                        ? ((Integer) hashMap.get(num)).intValue()
                                                        : R.string.summary_placeholder));
                                break;
                            case 1:
                                Integer num2 = (Integer) obj;
                                WifiTetherViewModel wifiTetherViewModel2 = this.f$0;
                                wifiTetherViewModel2.getClass();
                                HashMap hashMap2 =
                                        (HashMap) WifiTetherViewModel.sSpeedSummaryResMap;
                                wifiTetherViewModel2.mSpeedSummary.setValue(
                                        Integer.valueOf(
                                                hashMap2.containsKey(num2)
                                                        ? ((Integer) hashMap2.get(num2)).intValue()
                                                        : R.string.summary_placeholder));
                                break;
                            default:
                                this.f$0.onInstantHotspotStateChanged(
                                        (SharedConnectivitySettingsState) obj);
                                break;
                        }
                    }
                };
        this.mInstantHotspotSummary = new MutableLiveData();
        final int i3 = 2;
        this.mInstantHotspotStateObserver =
                new Observer(
                        this) { // from class:
                                // com.android.settings.wifi.tether.WifiTetherViewModel$$ExternalSyntheticLambda0
                    public final /* synthetic */ WifiTetherViewModel f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        switch (i3) {
                            case 0:
                                Integer num = (Integer) obj;
                                WifiTetherViewModel wifiTetherViewModel = this.f$0;
                                wifiTetherViewModel.getClass();
                                num.getClass();
                                HashMap hashMap =
                                        (HashMap) WifiTetherViewModel.sSecuritySummaryResMap;
                                wifiTetherViewModel.mSecuritySummary.setValue(
                                        Integer.valueOf(
                                                hashMap.containsKey(num)
                                                        ? ((Integer) hashMap.get(num)).intValue()
                                                        : R.string.summary_placeholder));
                                break;
                            case 1:
                                Integer num2 = (Integer) obj;
                                WifiTetherViewModel wifiTetherViewModel2 = this.f$0;
                                wifiTetherViewModel2.getClass();
                                HashMap hashMap2 =
                                        (HashMap) WifiTetherViewModel.sSpeedSummaryResMap;
                                wifiTetherViewModel2.mSpeedSummary.setValue(
                                        Integer.valueOf(
                                                hashMap2.containsKey(num2)
                                                        ? ((Integer) hashMap2.get(num2)).intValue()
                                                        : R.string.summary_placeholder));
                                break;
                            default:
                                this.f$0.onInstantHotspotStateChanged(
                                        (SharedConnectivitySettingsState) obj);
                                break;
                        }
                    }
                };
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        WifiFeatureProvider wifiFeatureProvider = featureFactoryImpl.getWifiFeatureProvider();
        this.mWifiHotspotRepository = wifiFeatureProvider.getWifiHotspotRepository();
        if (wifiFeatureProvider.mSharedConnectivityRepository == null) {
            wifiFeatureProvider.mSharedConnectivityRepository =
                    new SharedConnectivityRepository(
                            wifiFeatureProvider.mAppContext,
                            SharedConnectivityRepository.isDeviceConfigEnabled());
            wifiFeatureProvider.verboseLog(
                    "WifiFeatureProvider",
                    "getSharedConnectivityRepository():"
                            + wifiFeatureProvider.mSharedConnectivityRepository);
        }
        SharedConnectivityRepository sharedConnectivityRepository =
                wifiFeatureProvider.mSharedConnectivityRepository;
        this.mSharedConnectivityRepository = sharedConnectivityRepository;
        if (sharedConnectivityRepository.mManager != null) {
            sharedConnectivityRepository
                    .getSettingsState()
                    .observeForever(this.mInstantHotspotStateObserver);
        }
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        MutableLiveData mutableLiveData = this.mSecuritySummary;
        WifiHotspotRepository wifiHotspotRepository = this.mWifiHotspotRepository;
        if (mutableLiveData != null) {
            wifiHotspotRepository.getSecurityType().removeObserver(this.mSecurityTypeObserver);
        }
        if (this.mSpeedSummary != null) {
            wifiHotspotRepository.getSpeedType().removeObserver(this.mSpeedTypeObserver);
        }
        SharedConnectivityRepository sharedConnectivityRepository =
                this.mSharedConnectivityRepository;
        if (sharedConnectivityRepository.mManager != null) {
            sharedConnectivityRepository
                    .getSettingsState()
                    .removeObserver(this.mInstantHotspotStateObserver);
        }
    }

    public void onInstantHotspotStateChanged(
            SharedConnectivitySettingsState sharedConnectivitySettingsState) {
        String str = "onInstantHotspotStateChanged(), state:" + sharedConnectivitySettingsState;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getWifiFeatureProvider().verboseLog("WifiTetherViewModel", str);
        if (sharedConnectivitySettingsState == null) {
            this.mInstantHotspotSummary.setValue(null);
            return;
        }
        this.mInstantHotspotSummary.setValue(
                getApplication()
                        .getString(
                                sharedConnectivitySettingsState.isInstantTetherEnabled()
                                        ? R.string.wifi_hotspot_instant_summary_on
                                        : R.string.wifi_hotspot_instant_summary_off));
    }
}
