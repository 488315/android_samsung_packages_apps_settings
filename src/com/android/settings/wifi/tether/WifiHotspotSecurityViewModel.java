package com.android.settings.wifi.tether;

import android.app.Application;
import androidx.compose.animation.ChangeSize$$ExternalSyntheticOutline0;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.repository.WifiHotspotRepository;
import com.android.settings.wifi.tether.WifiHotspotSecurityViewModel;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiHotspotSecurityViewModel extends AndroidViewModel {
    public final WifiHotspotSecurityViewModel$$ExternalSyntheticLambda0 mSecurityTypeObserver;
    public final WifiHotspotSecurityViewModel$$ExternalSyntheticLambda0 mSpeedTypeObserver;
    public MutableLiveData mViewInfoListData;
    public final Map mViewItemMap;
    public final WifiHotspotRepository mWifiHotspotRepository;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ViewItem {
        public boolean mIsChecked;
        public boolean mIsEnabled = true;
        public final String mKey;

        public ViewItem(String str) {
            this.mKey = str;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ViewItem:{Key:");
            sb.append(this.mKey);
            sb.append(",IsChecked:");
            sb.append(this.mIsChecked);
            sb.append(",IsEnabled:");
            return ChangeSize$$ExternalSyntheticOutline0.m(sb, this.mIsEnabled, '}');
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.lifecycle.Observer, com.android.settings.wifi.tether.WifiHotspotSecurityViewModel$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v1, types: [androidx.lifecycle.Observer, com.android.settings.wifi.tether.WifiHotspotSecurityViewModel$$ExternalSyntheticLambda0] */
    public WifiHotspotSecurityViewModel(Application application) {
        super(application);
        HashMap hashMap = new HashMap();
        this.mViewItemMap = hashMap;
        final int i = 0;
        ?? r0 = new Observer(this) { // from class: com.android.settings.wifi.tether.WifiHotspotSecurityViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ WifiHotspotSecurityViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                int i2 = i;
                Integer num = (Integer) obj;
                WifiHotspotSecurityViewModel wifiHotspotSecurityViewModel = this.f$0;
                wifiHotspotSecurityViewModel.getClass();
                switch (i2) {
                    case 0:
                        WifiHotspotSecurityViewModel.log$3("onSecurityTypeChanged(), securityType:" + num.intValue());
                        for (Map.Entry entry : ((HashMap) wifiHotspotSecurityViewModel.mViewItemMap).entrySet()) {
                            ((WifiHotspotSecurityViewModel.ViewItem) entry.getValue()).mIsChecked = ((Integer) entry.getKey()).equals(num);
                        }
                        wifiHotspotSecurityViewModel.updateViewItemListData();
                        break;
                    default:
                        WifiHotspotSecurityViewModel.log$3("onSpeedTypeChanged(), speedType:" + num);
                        boolean z = num.intValue() == 4;
                        for (Map.Entry entry2 : ((HashMap) wifiHotspotSecurityViewModel.mViewItemMap).entrySet()) {
                            if (((Integer) entry2.getKey()).intValue() != 3) {
                                ((WifiHotspotSecurityViewModel.ViewItem) entry2.getValue()).mIsEnabled = !z;
                            }
                        }
                        wifiHotspotSecurityViewModel.updateViewItemListData();
                        break;
                }
            }
        };
        this.mSecurityTypeObserver = r0;
        final int i2 = 1;
        ?? r1 = new Observer(this) { // from class: com.android.settings.wifi.tether.WifiHotspotSecurityViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ WifiHotspotSecurityViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                int i22 = i2;
                Integer num = (Integer) obj;
                WifiHotspotSecurityViewModel wifiHotspotSecurityViewModel = this.f$0;
                wifiHotspotSecurityViewModel.getClass();
                switch (i22) {
                    case 0:
                        WifiHotspotSecurityViewModel.log$3("onSecurityTypeChanged(), securityType:" + num.intValue());
                        for (Map.Entry entry : ((HashMap) wifiHotspotSecurityViewModel.mViewItemMap).entrySet()) {
                            ((WifiHotspotSecurityViewModel.ViewItem) entry.getValue()).mIsChecked = ((Integer) entry.getKey()).equals(num);
                        }
                        wifiHotspotSecurityViewModel.updateViewItemListData();
                        break;
                    default:
                        WifiHotspotSecurityViewModel.log$3("onSpeedTypeChanged(), speedType:" + num);
                        boolean z = num.intValue() == 4;
                        for (Map.Entry entry2 : ((HashMap) wifiHotspotSecurityViewModel.mViewItemMap).entrySet()) {
                            if (((Integer) entry2.getKey()).intValue() != 3) {
                                ((WifiHotspotSecurityViewModel.ViewItem) entry2.getValue()).mIsEnabled = !z;
                            }
                        }
                        wifiHotspotSecurityViewModel.updateViewItemListData();
                        break;
                }
            }
        };
        this.mSpeedTypeObserver = r1;
        hashMap.put(3, new ViewItem("wifi_hotspot_security_wpa3"));
        hashMap.put(2, new ViewItem("wifi_hotspot_security_wpa2_wpa3"));
        hashMap.put(1, new ViewItem("wifi_hotspot_security_wpa2"));
        hashMap.put(0, new ViewItem("wifi_hotspot_security_none"));
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        WifiHotspotRepository wifiHotspotRepository = featureFactoryImpl.getWifiFeatureProvider().getWifiHotspotRepository();
        this.mWifiHotspotRepository = wifiHotspotRepository;
        wifiHotspotRepository.getSecurityType().observeForever(r0);
        wifiHotspotRepository.getSpeedType().observeForever(r1);
    }

    public static void log$3(String str) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getWifiFeatureProvider().verboseLog("WifiHotspotSecurityViewModel", str);
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        WifiHotspotRepository wifiHotspotRepository = this.mWifiHotspotRepository;
        wifiHotspotRepository.getSecurityType().removeObserver(this.mSecurityTypeObserver);
        wifiHotspotRepository.getSpeedType().removeObserver(this.mSpeedTypeObserver);
    }

    public final void updateViewItemListData() {
        MutableLiveData mutableLiveData = this.mViewInfoListData;
        if (mutableLiveData == null) {
            return;
        }
        mutableLiveData.setValue(((HashMap) this.mViewItemMap).values().stream().toList());
    }
}
