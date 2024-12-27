package com.android.settings.wifi.tether;

import android.app.Application;
import android.util.Log;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.repository.WifiHotspotRepository;
import com.android.settings.wifi.tether.WifiHotspotSpeedViewModel;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiHotspotSpeedViewModel extends AndroidViewModel {
    static final int RES_SPEED_5G_SUMMARY = 2132030790;
    static final int RES_SPEED_6G_SUMMARY = 2132030792;
    static final int RES_SUMMARY_UNAVAILABLE = 2132030800;
    public final WifiHotspotSpeedViewModel$$ExternalSyntheticLambda0 m5gAvailableObserver;
    public final WifiHotspotSpeedViewModel$$ExternalSyntheticLambda0 m6gAvailableObserver;
    public final SpeedInfo mSpeedInfo2g;
    public final SpeedInfo mSpeedInfo2g5g;
    public final SpeedInfo mSpeedInfo5g;
    public final SpeedInfo mSpeedInfo6g;
    public final Map mSpeedInfoMap;
    public MutableLiveData mSpeedInfoMapData;
    public final WifiHotspotSpeedViewModel$$ExternalSyntheticLambda0 mSpeedTypeObserver;
    public final WifiHotspotRepository mWifiHotspotRepository;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SpeedInfo {
        public Boolean mIsChecked = Boolean.FALSE;
        public boolean mIsEnabled = true;
        public boolean mIsVisible;
        public String mSummary;

        public SpeedInfo(boolean z) {
            this.mIsVisible = z;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SpeedInfo{isChecked:");
            sb.append(this.mIsChecked);
            sb.append(",isEnabled:");
            sb.append(this.mIsEnabled);
            sb.append(",isVisible:");
            sb.append(this.mIsVisible);
            sb.append(",mSummary:");
            return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.mSummary, '}');
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [androidx.lifecycle.Observer, com.android.settings.wifi.tether.WifiHotspotSpeedViewModel$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v1, types: [androidx.lifecycle.Observer, com.android.settings.wifi.tether.WifiHotspotSpeedViewModel$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.lifecycle.Observer, com.android.settings.wifi.tether.WifiHotspotSpeedViewModel$$ExternalSyntheticLambda0] */
    public WifiHotspotSpeedViewModel(Application application) {
        super(application);
        this.mSpeedInfoMap = new HashMap();
        this.mSpeedInfo2g = new SpeedInfo(false);
        this.mSpeedInfo5g = new SpeedInfo(false);
        this.mSpeedInfo2g5g = new SpeedInfo(true);
        SpeedInfo speedInfo = new SpeedInfo(true);
        this.mSpeedInfo6g = speedInfo;
        final int i = 0;
        ?? r0 = new Observer(this) { // from class: com.android.settings.wifi.tether.WifiHotspotSpeedViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ WifiHotspotSpeedViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                switch (i) {
                    case 0:
                        Boolean bool = (Boolean) obj;
                        WifiHotspotSpeedViewModel wifiHotspotSpeedViewModel = this.f$0;
                        wifiHotspotSpeedViewModel.getClass();
                        Log.d("WifiHotspotSpeedViewModel", "on6gAvailableChanged(), available:" + bool);
                        boolean booleanValue = bool.booleanValue();
                        WifiHotspotSpeedViewModel.SpeedInfo speedInfo2 = wifiHotspotSpeedViewModel.mSpeedInfo6g;
                        speedInfo2.mIsEnabled = booleanValue;
                        speedInfo2.mSummary = wifiHotspotSpeedViewModel.getApplication().getString(bool.booleanValue() ? WifiHotspotSpeedViewModel.RES_SPEED_6G_SUMMARY : WifiHotspotSpeedViewModel.RES_SUMMARY_UNAVAILABLE);
                        wifiHotspotSpeedViewModel.updateSpeedInfoMapData();
                        break;
                    case 1:
                        Boolean bool2 = (Boolean) obj;
                        WifiHotspotSpeedViewModel wifiHotspotSpeedViewModel2 = this.f$0;
                        wifiHotspotSpeedViewModel2.getClass();
                        Log.d("WifiHotspotSpeedViewModel", "on5gAvailableChanged(), available:" + bool2);
                        boolean booleanValue2 = bool2.booleanValue();
                        WifiHotspotSpeedViewModel.SpeedInfo speedInfo3 = wifiHotspotSpeedViewModel2.mSpeedInfo5g;
                        speedInfo3.mIsEnabled = booleanValue2;
                        speedInfo3.mSummary = wifiHotspotSpeedViewModel2.getApplication().getString(bool2.booleanValue() ? WifiHotspotSpeedViewModel.RES_SPEED_5G_SUMMARY : WifiHotspotSpeedViewModel.RES_SUMMARY_UNAVAILABLE);
                        boolean z = wifiHotspotSpeedViewModel2.mWifiHotspotRepository.isDualBand() && bool2.booleanValue();
                        WifiHotspotSpeedViewModel.log$4("on5gAvailableChanged(), showDualBand:" + z);
                        wifiHotspotSpeedViewModel2.mSpeedInfo2g5g.mIsVisible = z;
                        boolean z2 = z ^ true;
                        wifiHotspotSpeedViewModel2.mSpeedInfo2g.mIsVisible = z2;
                        speedInfo3.mIsVisible = z2;
                        wifiHotspotSpeedViewModel2.updateSpeedInfoMapData();
                        break;
                    default:
                        Integer num = (Integer) obj;
                        WifiHotspotSpeedViewModel wifiHotspotSpeedViewModel3 = this.f$0;
                        wifiHotspotSpeedViewModel3.getClass();
                        WifiHotspotSpeedViewModel.log$4("onSpeedTypeChanged(), speedType:" + num);
                        wifiHotspotSpeedViewModel3.mSpeedInfo2g.mIsChecked = Boolean.valueOf(num.equals(1));
                        wifiHotspotSpeedViewModel3.mSpeedInfo5g.mIsChecked = Boolean.valueOf(num.equals(2));
                        wifiHotspotSpeedViewModel3.mSpeedInfo2g5g.mIsChecked = Boolean.valueOf(num.equals(3));
                        wifiHotspotSpeedViewModel3.mSpeedInfo6g.mIsChecked = Boolean.valueOf(num.equals(4));
                        wifiHotspotSpeedViewModel3.updateSpeedInfoMapData();
                        break;
                }
            }
        };
        this.m6gAvailableObserver = r0;
        final int i2 = 1;
        ?? r1 = new Observer(this) { // from class: com.android.settings.wifi.tether.WifiHotspotSpeedViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ WifiHotspotSpeedViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                switch (i2) {
                    case 0:
                        Boolean bool = (Boolean) obj;
                        WifiHotspotSpeedViewModel wifiHotspotSpeedViewModel = this.f$0;
                        wifiHotspotSpeedViewModel.getClass();
                        Log.d("WifiHotspotSpeedViewModel", "on6gAvailableChanged(), available:" + bool);
                        boolean booleanValue = bool.booleanValue();
                        WifiHotspotSpeedViewModel.SpeedInfo speedInfo2 = wifiHotspotSpeedViewModel.mSpeedInfo6g;
                        speedInfo2.mIsEnabled = booleanValue;
                        speedInfo2.mSummary = wifiHotspotSpeedViewModel.getApplication().getString(bool.booleanValue() ? WifiHotspotSpeedViewModel.RES_SPEED_6G_SUMMARY : WifiHotspotSpeedViewModel.RES_SUMMARY_UNAVAILABLE);
                        wifiHotspotSpeedViewModel.updateSpeedInfoMapData();
                        break;
                    case 1:
                        Boolean bool2 = (Boolean) obj;
                        WifiHotspotSpeedViewModel wifiHotspotSpeedViewModel2 = this.f$0;
                        wifiHotspotSpeedViewModel2.getClass();
                        Log.d("WifiHotspotSpeedViewModel", "on5gAvailableChanged(), available:" + bool2);
                        boolean booleanValue2 = bool2.booleanValue();
                        WifiHotspotSpeedViewModel.SpeedInfo speedInfo3 = wifiHotspotSpeedViewModel2.mSpeedInfo5g;
                        speedInfo3.mIsEnabled = booleanValue2;
                        speedInfo3.mSummary = wifiHotspotSpeedViewModel2.getApplication().getString(bool2.booleanValue() ? WifiHotspotSpeedViewModel.RES_SPEED_5G_SUMMARY : WifiHotspotSpeedViewModel.RES_SUMMARY_UNAVAILABLE);
                        boolean z = wifiHotspotSpeedViewModel2.mWifiHotspotRepository.isDualBand() && bool2.booleanValue();
                        WifiHotspotSpeedViewModel.log$4("on5gAvailableChanged(), showDualBand:" + z);
                        wifiHotspotSpeedViewModel2.mSpeedInfo2g5g.mIsVisible = z;
                        boolean z2 = z ^ true;
                        wifiHotspotSpeedViewModel2.mSpeedInfo2g.mIsVisible = z2;
                        speedInfo3.mIsVisible = z2;
                        wifiHotspotSpeedViewModel2.updateSpeedInfoMapData();
                        break;
                    default:
                        Integer num = (Integer) obj;
                        WifiHotspotSpeedViewModel wifiHotspotSpeedViewModel3 = this.f$0;
                        wifiHotspotSpeedViewModel3.getClass();
                        WifiHotspotSpeedViewModel.log$4("onSpeedTypeChanged(), speedType:" + num);
                        wifiHotspotSpeedViewModel3.mSpeedInfo2g.mIsChecked = Boolean.valueOf(num.equals(1));
                        wifiHotspotSpeedViewModel3.mSpeedInfo5g.mIsChecked = Boolean.valueOf(num.equals(2));
                        wifiHotspotSpeedViewModel3.mSpeedInfo2g5g.mIsChecked = Boolean.valueOf(num.equals(3));
                        wifiHotspotSpeedViewModel3.mSpeedInfo6g.mIsChecked = Boolean.valueOf(num.equals(4));
                        wifiHotspotSpeedViewModel3.updateSpeedInfoMapData();
                        break;
                }
            }
        };
        this.m5gAvailableObserver = r1;
        final int i3 = 2;
        ?? r2 = new Observer(this) { // from class: com.android.settings.wifi.tether.WifiHotspotSpeedViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ WifiHotspotSpeedViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                switch (i3) {
                    case 0:
                        Boolean bool = (Boolean) obj;
                        WifiHotspotSpeedViewModel wifiHotspotSpeedViewModel = this.f$0;
                        wifiHotspotSpeedViewModel.getClass();
                        Log.d("WifiHotspotSpeedViewModel", "on6gAvailableChanged(), available:" + bool);
                        boolean booleanValue = bool.booleanValue();
                        WifiHotspotSpeedViewModel.SpeedInfo speedInfo2 = wifiHotspotSpeedViewModel.mSpeedInfo6g;
                        speedInfo2.mIsEnabled = booleanValue;
                        speedInfo2.mSummary = wifiHotspotSpeedViewModel.getApplication().getString(bool.booleanValue() ? WifiHotspotSpeedViewModel.RES_SPEED_6G_SUMMARY : WifiHotspotSpeedViewModel.RES_SUMMARY_UNAVAILABLE);
                        wifiHotspotSpeedViewModel.updateSpeedInfoMapData();
                        break;
                    case 1:
                        Boolean bool2 = (Boolean) obj;
                        WifiHotspotSpeedViewModel wifiHotspotSpeedViewModel2 = this.f$0;
                        wifiHotspotSpeedViewModel2.getClass();
                        Log.d("WifiHotspotSpeedViewModel", "on5gAvailableChanged(), available:" + bool2);
                        boolean booleanValue2 = bool2.booleanValue();
                        WifiHotspotSpeedViewModel.SpeedInfo speedInfo3 = wifiHotspotSpeedViewModel2.mSpeedInfo5g;
                        speedInfo3.mIsEnabled = booleanValue2;
                        speedInfo3.mSummary = wifiHotspotSpeedViewModel2.getApplication().getString(bool2.booleanValue() ? WifiHotspotSpeedViewModel.RES_SPEED_5G_SUMMARY : WifiHotspotSpeedViewModel.RES_SUMMARY_UNAVAILABLE);
                        boolean z = wifiHotspotSpeedViewModel2.mWifiHotspotRepository.isDualBand() && bool2.booleanValue();
                        WifiHotspotSpeedViewModel.log$4("on5gAvailableChanged(), showDualBand:" + z);
                        wifiHotspotSpeedViewModel2.mSpeedInfo2g5g.mIsVisible = z;
                        boolean z2 = z ^ true;
                        wifiHotspotSpeedViewModel2.mSpeedInfo2g.mIsVisible = z2;
                        speedInfo3.mIsVisible = z2;
                        wifiHotspotSpeedViewModel2.updateSpeedInfoMapData();
                        break;
                    default:
                        Integer num = (Integer) obj;
                        WifiHotspotSpeedViewModel wifiHotspotSpeedViewModel3 = this.f$0;
                        wifiHotspotSpeedViewModel3.getClass();
                        WifiHotspotSpeedViewModel.log$4("onSpeedTypeChanged(), speedType:" + num);
                        wifiHotspotSpeedViewModel3.mSpeedInfo2g.mIsChecked = Boolean.valueOf(num.equals(1));
                        wifiHotspotSpeedViewModel3.mSpeedInfo5g.mIsChecked = Boolean.valueOf(num.equals(2));
                        wifiHotspotSpeedViewModel3.mSpeedInfo2g5g.mIsChecked = Boolean.valueOf(num.equals(3));
                        wifiHotspotSpeedViewModel3.mSpeedInfo6g.mIsChecked = Boolean.valueOf(num.equals(4));
                        wifiHotspotSpeedViewModel3.updateSpeedInfoMapData();
                        break;
                }
            }
        };
        this.mSpeedTypeObserver = r2;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        WifiHotspotRepository wifiHotspotRepository = featureFactoryImpl.getWifiFeatureProvider().getWifiHotspotRepository();
        this.mWifiHotspotRepository = wifiHotspotRepository;
        if (wifiHotspotRepository.m6gAvailable == null) {
            MutableLiveData mutableLiveData = new MutableLiveData();
            wifiHotspotRepository.m6gAvailable = mutableLiveData;
            mutableLiveData.setValue(Boolean.valueOf(wifiHotspotRepository.is6gAvailable()));
        }
        wifiHotspotRepository.m6gAvailable.observeForever(r0);
        if (wifiHotspotRepository.m5gAvailable == null) {
            MutableLiveData mutableLiveData2 = new MutableLiveData();
            wifiHotspotRepository.m5gAvailable = mutableLiveData2;
            mutableLiveData2.setValue(Boolean.valueOf(wifiHotspotRepository.is5gAvailable()));
        }
        wifiHotspotRepository.m5gAvailable.observeForever(r1);
        wifiHotspotRepository.getSpeedType().observeForever(r2);
        wifiHotspotRepository.startAutoRefresh();
        speedInfo.mIsVisible = wifiHotspotRepository.is6GHzBandSupported();
    }

    public static void log$4(String str) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getWifiFeatureProvider().verboseLog("WifiHotspotSpeedViewModel", str);
    }

    public final MutableLiveData getSpeedInfoMapData() {
        if (this.mSpeedInfoMapData == null) {
            MutableLiveData mutableLiveData = new MutableLiveData();
            this.mSpeedInfoMapData = mutableLiveData;
            mutableLiveData.setValue(this.mSpeedInfoMap);
            log$4("getSpeedViewData(), mSpeedInfoMap:" + this.mSpeedInfoMapData.getValue());
        }
        return this.mSpeedInfoMapData;
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        WifiHotspotRepository wifiHotspotRepository = this.mWifiHotspotRepository;
        if (wifiHotspotRepository.m6gAvailable == null) {
            MutableLiveData mutableLiveData = new MutableLiveData();
            wifiHotspotRepository.m6gAvailable = mutableLiveData;
            mutableLiveData.setValue(Boolean.valueOf(wifiHotspotRepository.is6gAvailable()));
        }
        wifiHotspotRepository.m6gAvailable.removeObserver(this.m6gAvailableObserver);
        if (wifiHotspotRepository.m5gAvailable == null) {
            MutableLiveData mutableLiveData2 = new MutableLiveData();
            wifiHotspotRepository.m5gAvailable = mutableLiveData2;
            mutableLiveData2.setValue(Boolean.valueOf(wifiHotspotRepository.is5gAvailable()));
        }
        wifiHotspotRepository.m5gAvailable.removeObserver(this.m5gAvailableObserver);
        wifiHotspotRepository.getSpeedType().removeObserver(this.mSpeedTypeObserver);
    }

    public final void updateSpeedInfoMapData() {
        ((HashMap) this.mSpeedInfoMap).put(1, this.mSpeedInfo2g);
        ((HashMap) this.mSpeedInfoMap).put(2, this.mSpeedInfo5g);
        ((HashMap) this.mSpeedInfoMap).put(3, this.mSpeedInfo2g5g);
        ((HashMap) this.mSpeedInfoMap).put(4, this.mSpeedInfo6g);
        MutableLiveData mutableLiveData = this.mSpeedInfoMapData;
        if (mutableLiveData != null) {
            mutableLiveData.setValue(this.mSpeedInfoMap);
        }
    }
}
