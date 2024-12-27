package com.android.settings.wifi.details;

import android.app.Application;
import android.net.wifi.sharedconnectivity.app.HotspotNetwork;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.wifitrackerlib.HotspotNetworkEntry;
import com.android.wifitrackerlib.WifiEntry;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiNetworkDetailsViewModel extends AndroidViewModel {
    MutableLiveData mHotspotNetworkData;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HotspotNetworkData {
        public int mBatteryPercentage;
        public boolean mIsBatteryCharging;
        public int mNetworkType;
        public int mUpstreamConnectionStrength;

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(HotspotNetworkData.class.getSimpleName());
            sb.append(":{networkType:");
            sb.append(this.mNetworkType);
            sb.append(", upstreamConnectionStrength:");
            sb.append(this.mUpstreamConnectionStrength);
            sb.append(", batteryPercentage:");
            sb.append(this.mBatteryPercentage);
            sb.append(", isBatteryCharging:");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                    sb, this.mIsBatteryCharging, " }");
        }
    }

    public WifiNetworkDetailsViewModel(Application application) {
        super(application);
        this.mHotspotNetworkData = new MutableLiveData();
    }

    public final void setWifiEntry(WifiEntry wifiEntry) {
        boolean z;
        int hostNetworkType;
        int batteryPercentage;
        if (!(wifiEntry instanceof HotspotNetworkEntry)) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            featureFactoryImpl
                    .getWifiFeatureProvider()
                    .verboseLog("WifiNetworkDetailsViewModel", "post HotspotNetworkData:null");
            this.mHotspotNetworkData.postValue(null);
            return;
        }
        HotspotNetworkEntry hotspotNetworkEntry = (HotspotNetworkEntry) wifiEntry;
        synchronized (hotspotNetworkEntry) {
            HotspotNetwork hotspotNetwork = hotspotNetworkEntry.mHotspotNetworkData;
            z = false;
            hostNetworkType = hotspotNetwork == null ? 0 : hotspotNetwork.getHostNetworkType();
        }
        int upstreamConnectionStrength = hotspotNetworkEntry.getUpstreamConnectionStrength();
        synchronized (hotspotNetworkEntry) {
            HotspotNetwork hotspotNetwork2 = hotspotNetworkEntry.mHotspotNetworkData;
            batteryPercentage =
                    hotspotNetwork2 == null
                            ? 0
                            : hotspotNetwork2.getNetworkProviderInfo().getBatteryPercentage();
        }
        synchronized (hotspotNetworkEntry) {
            HotspotNetwork hotspotNetwork3 = hotspotNetworkEntry.mHotspotNetworkData;
            if (hotspotNetwork3 != null) {
                z =
                        hotspotNetwork3.getNetworkProviderInfo().isBatteryCharging()
                                ? true
                                : hotspotNetworkEntry
                                        .mHotspotNetworkData
                                        .getNetworkProviderInfo()
                                        .getExtras()
                                        .getBoolean("is_battery_charging", false);
            }
        }
        HotspotNetworkData hotspotNetworkData = new HotspotNetworkData();
        hotspotNetworkData.mNetworkType = hostNetworkType;
        hotspotNetworkData.mUpstreamConnectionStrength = upstreamConnectionStrength;
        hotspotNetworkData.mBatteryPercentage = batteryPercentage;
        hotspotNetworkData.mIsBatteryCharging = z;
        String str = "post HotspotNetworkData:" + hotspotNetworkData;
        FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
        if (featureFactoryImpl2 == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl2.getWifiFeatureProvider().verboseLog("WifiNetworkDetailsViewModel", str);
        this.mHotspotNetworkData.postValue(hotspotNetworkData);
    }
}
