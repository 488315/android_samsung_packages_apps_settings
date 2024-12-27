package com.android.settings.wifi.repository;

import android.content.Context;
import android.net.TetheringManager;
import android.net.wifi.SoftApCapability;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiAvailableChannel;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.animation.ChangeSize$$ExternalSyntheticOutline0;
import androidx.lifecycle.MutableLiveData;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.overlay.FeatureFactoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiHotspotRepository {
    public static final Map sSpeedMap;
    public MutableLiveData m5gAvailable;
    public MutableLiveData m6gAvailable;
    public ActiveCountryCodeChangedCallback mActiveCountryCodeChangedCallback;
    public final Context mAppContext;
    public Boolean mIs5gBandSupported;
    public Boolean mIs6gBandSupported;
    Boolean mIsConfigShowSpeed;
    public Boolean mIsDualBand;
    boolean mIsRestarting;
    public Boolean mIsSpeedFeatureAvailable;
    public String mLastPassword;
    MutableLiveData mRestarting;
    public MutableLiveData mSecurityType;
    public MutableLiveData mSpeedType;
    StartTetheringCallback mStartTetheringCallback;
    public final TetheringManager mTetheringManager;
    public final WifiManager mWifiManager;
    public final LastPasswordListener mLastPasswordListener = new LastPasswordListener();
    public final SapBand mBand5g = new SapBand(6);
    public final SapBand mBand6g = new SapBand(8);
    SoftApCallback mSoftApCallback = new SoftApCallback();
    int mWifiApState = 11;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LastPasswordListener implements Consumer {
        public LastPasswordListener() {}

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            WifiHotspotRepository.this.mLastPassword = (String) obj;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class SapBand {
        public final int band;
        public boolean hasCapability;
        public boolean hasUsableChannels;
        public boolean isUsableChannelsReady;
        public boolean isUsableChannelsUnsupported;

        public SapBand(int i) {
            this.band = i;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SapBand{band:");
            sb.append(this.band);
            sb.append(",isUsableChannelsReady:");
            sb.append(this.isUsableChannelsReady);
            sb.append(",hasUsableChannels:");
            sb.append(this.hasUsableChannels);
            sb.append(",isUsableChannelsUnsupported:");
            sb.append(this.isUsableChannelsUnsupported);
            sb.append(",hasChannelsCapability:");
            return ChangeSize$$ExternalSyntheticOutline0.m(sb, this.hasCapability, '}');
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class SoftApCallback implements WifiManager.SoftApCallback {
        public SoftApCallback() {}

        public final void onCapabilityChanged(SoftApCapability softApCapability) {
            WifiHotspotRepository wifiHotspotRepository = WifiHotspotRepository.this;
            Map map = WifiHotspotRepository.sSpeedMap;
            wifiHotspotRepository.getClass();
            WifiHotspotRepository.log(
                    "onCapabilityChanged(), softApCapability:" + softApCapability);
            WifiHotspotRepository.this.mBand5g.hasCapability =
                    softApCapability.getSupportedChannelList(2).length > 0;
            WifiHotspotRepository.this.mBand6g.hasCapability =
                    softApCapability.getSupportedChannelList(4).length > 0;
            WifiHotspotRepository.this.updateCapabilityChanged();
        }

        public final void onStateChanged(int i, int i2) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    "onStateChanged(), state:", ", failureReason:", i, i2, "WifiHotspotRepository");
            WifiHotspotRepository wifiHotspotRepository = WifiHotspotRepository.this;
            wifiHotspotRepository.mWifiApState = i;
            if (wifiHotspotRepository.mIsRestarting) {
                if (i == 11) {
                    wifiHotspotRepository
                            .mAppContext
                            .getMainThreadHandler()
                            .postDelayed(
                                    new WifiHotspotRepository$$ExternalSyntheticLambda0(1, this),
                                    100L);
                } else if (i == 13) {
                    wifiHotspotRepository.refresh();
                    WifiHotspotRepository.this.setRestarting(false);
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class StartTetheringCallback implements TetheringManager.StartTetheringCallback {
        public StartTetheringCallback() {}

        public final void onTetheringFailed(int i) {
            WifiHotspotRepository.this.getClass();
            WifiHotspotRepository.log("onTetheringFailed(), error:" + i);
        }

        public final void onTetheringStarted() {
            WifiHotspotRepository.this.getClass();
            WifiHotspotRepository.log("onTetheringStarted()");
        }
    }

    static {
        HashMap hashMap = new HashMap();
        sSpeedMap = hashMap;
        hashMap.put(0, 0);
        hashMap.put(1, 1);
        hashMap.put(2, 2);
        hashMap.put(4, 4);
        hashMap.put(3, 3);
    }

    public WifiHotspotRepository(
            Context context, WifiManager wifiManager, TetheringManager tetheringManager) {
        this.mAppContext = context;
        this.mWifiManager = wifiManager;
        this.mTetheringManager = tetheringManager;
        wifiManager.registerSoftApCallback(context.getMainExecutor(), this.mSoftApCallback);
    }

    public static void log(String str) {
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        featureFactoryImpl.getWifiFeatureProvider().verboseLog("WifiHotspotRepository", str);
    }

    public final String generatePassword() {
        if (!TextUtils.isEmpty(this.mLastPassword)) {
            return this.mLastPassword;
        }
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13);
    }

    public final MutableLiveData getRestarting() {
        if (this.mRestarting == null) {
            MutableLiveData mutableLiveData = new MutableLiveData();
            this.mRestarting = mutableLiveData;
            mutableLiveData.setValue(Boolean.valueOf(this.mIsRestarting));
        }
        return this.mRestarting;
    }

    public final MutableLiveData getSecurityType() {
        if (this.mSecurityType == null) {
            startAutoRefresh();
            this.mSecurityType = new MutableLiveData();
            updateSecurityType();
            log("getSecurityType():" + this.mSecurityType.getValue());
        }
        return this.mSecurityType;
    }

    public final MutableLiveData getSpeedType() {
        if (this.mSpeedType == null) {
            startAutoRefresh();
            this.mSpeedType = new MutableLiveData();
            updateSpeedType();
            log("getSpeedType():" + this.mSpeedType.getValue());
        }
        return this.mSpeedType;
    }

    public final boolean is5GHzBandSupported() {
        if (this.mIs5gBandSupported == null) {
            this.mIs5gBandSupported = Boolean.valueOf(this.mWifiManager.is5GHzBandSupported());
            log("is5GHzBandSupported():" + this.mIs5gBandSupported);
        }
        return this.mIs5gBandSupported.booleanValue();
    }

    public final boolean is5gAvailable() {
        SapBand sapBand = this.mBand5g;
        if (!sapBand.isUsableChannelsReady && is5GHzBandSupported()) {
            isChannelAvailable(sapBand);
        }
        return sapBand.isUsableChannelsUnsupported
                ? sapBand.hasCapability
                : sapBand.hasUsableChannels;
    }

    public final boolean is6GHzBandSupported() {
        if (this.mIs6gBandSupported == null) {
            this.mIs6gBandSupported = Boolean.valueOf(this.mWifiManager.is6GHzBandSupported());
            log("is6GHzBandSupported():" + this.mIs6gBandSupported);
        }
        return this.mIs6gBandSupported.booleanValue();
    }

    public final boolean is6gAvailable() {
        SapBand sapBand = this.mBand6g;
        if (!sapBand.isUsableChannelsReady && is6GHzBandSupported()) {
            isChannelAvailable(sapBand);
        }
        return sapBand.isUsableChannelsUnsupported
                ? sapBand.hasCapability
                : sapBand.hasUsableChannels;
    }

    public boolean isChannelAvailable(SapBand sapBand) {
        try {
            List<WifiAvailableChannel> usableChannels =
                    this.mWifiManager.getUsableChannels(sapBand.band, 2);
            log("isChannelAvailable(), band:" + sapBand.band + ", channels:" + usableChannels);
            sapBand.hasUsableChannels = usableChannels != null && usableChannels.size() > 0;
            sapBand.isUsableChannelsUnsupported = false;
        } catch (IllegalArgumentException unused) {
            Log.e(
                    "WifiHotspotRepository",
                    "Querying usable SAP channels failed, band:" + sapBand.band);
            sapBand.hasUsableChannels = false;
            sapBand.isUsableChannelsUnsupported = true;
        } catch (UnsupportedOperationException unused2) {
            Log.e(
                    "WifiHotspotRepository",
                    "Querying usable SAP channels is unsupported, band:" + sapBand.band);
            sapBand.hasUsableChannels = false;
            sapBand.isUsableChannelsUnsupported = true;
        }
        sapBand.isUsableChannelsReady = true;
        log("isChannelAvailable(), " + sapBand);
        return sapBand.isUsableChannelsUnsupported
                ? sapBand.hasCapability
                : sapBand.hasUsableChannels;
    }

    public final boolean isDualBand() {
        if (this.mIsDualBand == null) {
            this.mIsDualBand = Boolean.valueOf(this.mWifiManager.isBridgedApConcurrencySupported());
            log("isDualBand():" + this.mIsDualBand);
        }
        return this.mIsDualBand.booleanValue();
    }

    public final boolean isSpeedFeatureAvailable() {
        Boolean bool = this.mIsSpeedFeatureAvailable;
        if (bool != null) {
            return bool.booleanValue();
        }
        if (this.mIsConfigShowSpeed == null) {
            this.mIsConfigShowSpeed =
                    Boolean.valueOf(
                            this.mAppContext
                                    .getResources()
                                    .getBoolean(R.bool.config_show_wifi_hotspot_speed));
            log("isConfigShowSpeed():" + this.mIsConfigShowSpeed);
        }
        if (!this.mIsConfigShowSpeed.booleanValue()) {
            this.mIsSpeedFeatureAvailable = Boolean.FALSE;
            log("isSpeedFeatureAvailable():false, isConfigShowSpeed():false");
            return false;
        }
        if (is5GHzBandSupported()) {
            this.mIsSpeedFeatureAvailable = Boolean.TRUE;
            log("isSpeedFeatureAvailable():true");
            return true;
        }
        this.mIsSpeedFeatureAvailable = Boolean.FALSE;
        log("isSpeedFeatureAvailable():false, 5 GHz band is not supported on this device");
        return false;
    }

    public final void refresh() {
        updateSecurityType();
        MutableLiveData mutableLiveData = this.m6gAvailable;
        if (mutableLiveData != null) {
            mutableLiveData.setValue(Boolean.valueOf(is6gAvailable()));
        }
        MutableLiveData mutableLiveData2 = this.m5gAvailable;
        if (mutableLiveData2 != null) {
            mutableLiveData2.setValue(Boolean.valueOf(is5gAvailable()));
        }
        updateSpeedType();
    }

    public void restartTetheringIfNeeded() {
        if (this.mWifiApState != 13) {
            return;
        }
        log("restartTetheringIfNeeded()");
        this.mAppContext
                .getMainThreadHandler()
                .postDelayed(new WifiHotspotRepository$$ExternalSyntheticLambda0(0, this), 100L);
    }

    public final void setRestarting(boolean z) {
        log("setRestarting(), isRestarting:" + z);
        this.mIsRestarting = z;
        MutableLiveData mutableLiveData = this.mRestarting;
        if (mutableLiveData != null) {
            mutableLiveData.setValue(Boolean.valueOf(z));
        }
    }

    public final void setSoftApConfiguration(SoftApConfiguration softApConfiguration) {
        if (this.mIsRestarting) {
            Log.e(
                    "WifiHotspotRepository",
                    "Skip setSoftApConfiguration because hotspot is restarting.");
            return;
        }
        this.mWifiManager.setSoftApConfiguration(softApConfiguration);
        refresh();
        restartTetheringIfNeeded();
    }

    public final void startAutoRefresh() {
        if (this.mActiveCountryCodeChangedCallback != null) {
            return;
        }
        log("startMonitorSoftApConfiguration()");
        this.mActiveCountryCodeChangedCallback = new ActiveCountryCodeChangedCallback();
        this.mWifiManager.registerActiveCountryCodeChangedCallback(
                this.mAppContext.getMainExecutor(), this.mActiveCountryCodeChangedCallback);
    }

    public void updateCapabilityChanged() {
        SapBand sapBand = this.mBand5g;
        if (sapBand.isUsableChannelsUnsupported) {
            MutableLiveData mutableLiveData = this.m5gAvailable;
            if (mutableLiveData != null) {
                mutableLiveData.setValue(Boolean.valueOf(is5gAvailable()));
            }
            log("updateCapabilityChanged(), " + sapBand);
        }
        SapBand sapBand2 = this.mBand6g;
        if (sapBand2.isUsableChannelsUnsupported) {
            MutableLiveData mutableLiveData2 = this.m6gAvailable;
            if (mutableLiveData2 != null) {
                mutableLiveData2.setValue(Boolean.valueOf(is6gAvailable()));
            }
            log("updateCapabilityChanged(), " + sapBand2);
        }
        if (sapBand.isUsableChannelsUnsupported || sapBand2.isUsableChannelsUnsupported) {
            updateSpeedType();
        }
    }

    public final void updateSecurityType() {
        if (this.mSecurityType == null) {
            return;
        }
        SoftApConfiguration softApConfiguration = this.mWifiManager.getSoftApConfiguration();
        int securityType = softApConfiguration != null ? softApConfiguration.getSecurityType() : 0;
        log("updateSecurityType(), securityType:" + securityType);
        this.mSecurityType.setValue(Integer.valueOf(securityType));
    }

    public final void updateSpeedType() {
        if (this.mSpeedType == null) {
            return;
        }
        SoftApConfiguration softApConfiguration = this.mWifiManager.getSoftApConfiguration();
        int i = 0;
        if (softApConfiguration == null) {
            this.mSpeedType.setValue(0);
            return;
        }
        int band = softApConfiguration.getBand();
        log("updateSpeedType(), getBand():" + band);
        if (!is5gAvailable()) {
            band &= -3;
        }
        if (!is6gAvailable()) {
            band &= -5;
        }
        if ((band & 4) != 0) {
            i = 4;
        } else if (isDualBand() && is5gAvailable()) {
            i = 3;
        } else if ((band & 2) != 0) {
            i = 2;
        } else if ((band & 1) != 0) {
            i = 1;
        }
        log("updateSpeedType(), keyBand:" + i);
        this.mSpeedType.setValue((Integer) ((HashMap) sSpeedMap).get(Integer.valueOf(i)));
    }

    public String generatePassword(SoftApConfiguration softApConfiguration) {
        String passphrase = softApConfiguration.getPassphrase();
        return TextUtils.isEmpty(passphrase) ? generatePassword() : passphrase;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ActiveCountryCodeChangedCallback
            implements WifiManager.ActiveCountryCodeChangedCallback {
        public ActiveCountryCodeChangedCallback() {}

        public final void onActiveCountryCodeChanged(String str) {
            WifiHotspotRepository.this.getClass();
            WifiHotspotRepository.log("onActiveCountryCodeChanged(), country:" + str);
            WifiHotspotRepository wifiHotspotRepository = WifiHotspotRepository.this;
            wifiHotspotRepository.mBand5g.isUsableChannelsReady = false;
            wifiHotspotRepository.mBand6g.isUsableChannelsReady = false;
            wifiHotspotRepository.refresh();
        }

        public final void onCountryCodeInactive() {}
    }
}
