package com.android.settings.network;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.telephony.SubscriptionManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.widget.SummaryUpdater;
import com.android.settings.wifi.WifiPickerTrackerHelper;
import com.android.settings.wifi.WifiSummaryUpdater;
import com.android.settings.wifi.repository.SharedConnectivityRepository;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.mobile.dataservice.SubscriptionInfoEntity;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.wifi.WifiUtils;
import com.android.wifitrackerlib.HotspotNetworkEntry;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class InternetPreferenceController extends AbstractPreferenceController
        implements LifecycleObserver,
                SummaryUpdater.OnSummaryChangeListener,
                InternetUpdater.InternetChangeListener,
                MobileNetworkRepository.MobileNetworkCallback,
                DefaultSubscriptionReceiver.DefaultSubscriptionListener,
                WifiPickerTracker.WifiPickerTrackerCallback {
    static Map<Integer, Integer> sIconMap;
    public static final Map sSummaryMap;
    public final DefaultSubscriptionReceiver mDataSubscriptionChangedReceiver;
    public int mDefaultDataSubId;
    public int mInternetType;
    public final LifecycleOwner mLifecycleOwner;
    public final MobileNetworkRepository mMobileNetworkRepository;
    public Preference mPreference;
    public List mSubInfoEntityList;
    WifiSummaryUpdater mSummaryHelper;
    WifiPickerTrackerHelper mWifiPickerTrackerHelper;

    static {
        HashMap hashMap = new HashMap();
        sIconMap = hashMap;
        hashMap.put(0, Integer.valueOf(R.drawable.ic_no_internet_unavailable));
        sIconMap.put(1, Integer.valueOf(R.drawable.ic_no_internet_available));
        sIconMap.put(2, Integer.valueOf(R.drawable.ic_wifi_signal_4));
        sIconMap.put(3, Integer.valueOf(R.drawable.ic_network_cell));
        sIconMap.put(4, Integer.valueOf(R.drawable.ic_settings_ethernet));
        HashMap hashMap2 = new HashMap();
        sSummaryMap = hashMap2;
        hashMap2.put(0, Integer.valueOf(R.string.condition_airplane_title));
        hashMap2.put(1, Integer.valueOf(R.string.networks_available));
        hashMap2.put(2, 0);
        hashMap2.put(3, 0);
        hashMap2.put(4, Integer.valueOf(R.string.to_switch_networks_disconnect_ethernet));
    }

    public InternetPreferenceController(
            Context context, Lifecycle lifecycle, LifecycleOwner lifecycleOwner) {
        super(context);
        this.mSubInfoEntityList = new ArrayList();
        this.mDefaultDataSubId = -1;
        boolean isDeviceConfigEnabled = SharedConnectivityRepository.isDeviceConfigEnabled();
        if (lifecycle == null) {
            throw new IllegalArgumentException("Lifecycle must be set");
        }
        this.mSummaryHelper = new WifiSummaryUpdater(this.mContext, this, null);
        this.mInternetType = new InternetUpdater(context, lifecycle, this).mInternetType;
        this.mLifecycleOwner = lifecycleOwner;
        this.mMobileNetworkRepository = MobileNetworkRepository.getInstance(context);
        this.mDataSubscriptionChangedReceiver = new DefaultSubscriptionReceiver(context, this);
        if (isDeviceConfigEnabled) {
            this.mWifiPickerTrackerHelper = new WifiPickerTrackerHelper(lifecycle, context, this);
        }
        lifecycle.addObserver(this);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference("internet_settings");
    }

    public int getDefaultDataSubscriptionId() {
        return this.mDefaultDataSubId;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "internet_settings";
    }

    public List<SubscriptionInfoEntity> getSubscriptionInfoList() {
        return this.mSubInfoEntityList;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mContext.getResources().getBoolean(R.bool.config_show_internet_settings);
    }

    @Override // com.android.settings.network.InternetUpdater.InternetChangeListener,
              // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public final void onAirplaneModeChanged(boolean z) {
        ThreadUtils.postOnMainThread(
                new InternetPreferenceController$$ExternalSyntheticLambda0(this, 1));
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public final void onAvailableSubInfoChanged(List list) {
        this.mSubInfoEntityList = list;
        updateState(this.mPreference);
    }

    @Override // com.android.settings.network.DefaultSubscriptionReceiver.DefaultSubscriptionListener
    public final void onDefaultDataChanged(int i) {
        this.mDefaultDataSubId = i;
        updateState(this.mPreference);
    }

    @Override // com.android.settings.network.InternetUpdater.InternetChangeListener
    public final void onInternetTypeChanged(int i) {
        boolean z = i != this.mInternetType;
        this.mInternetType = i;
        if (z) {
            ThreadUtils.postOnMainThread(
                    new InternetPreferenceController$$ExternalSyntheticLambda0(this, 0));
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        this.mMobileNetworkRepository.removeRegister(this);
        this.mSummaryHelper.register(false);
        DefaultSubscriptionReceiver defaultSubscriptionReceiver =
                this.mDataSubscriptionChangedReceiver;
        defaultSubscriptionReceiver.mContext.unregisterReceiver(defaultSubscriptionReceiver);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
        MobileNetworkRepository mobileNetworkRepository = this.mMobileNetworkRepository;
        mobileNetworkRepository.addRegister(lifecycleOwner, this, -1);
        mobileNetworkRepository.updateEntity();
        this.mSummaryHelper.register(true);
        this.mDataSubscriptionChangedReceiver.registerReceiver();
        this.mDefaultDataSubId = SubscriptionManager.getDefaultDataSubscriptionId();
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onWifiEntriesChanged() {
        if (this.mInternetType == 2) {
            updateState(this.mPreference);
        }
    }

    public void updateCellularSummary() {
        SubscriptionInfoEntity subscriptionInfoEntity = null;
        SubscriptionInfoEntity subscriptionInfoEntity2 = null;
        for (SubscriptionInfoEntity subscriptionInfoEntity3 : getSubscriptionInfoList()) {
            if (subscriptionInfoEntity3.isActiveDataSubscriptionId) {
                subscriptionInfoEntity = subscriptionInfoEntity3;
            }
            if (subscriptionInfoEntity3.getSubId() == getDefaultDataSubscriptionId()) {
                subscriptionInfoEntity2 = subscriptionInfoEntity3;
            }
        }
        if (subscriptionInfoEntity == null || subscriptionInfoEntity2 == null) {
            return;
        }
        if (!subscriptionInfoEntity.isSubscriptionVisible) {
            subscriptionInfoEntity = subscriptionInfoEntity2;
        }
        boolean equals = subscriptionInfoEntity.equals(subscriptionInfoEntity2);
        String str = subscriptionInfoEntity.uniqueName;
        if (!equals) {
            str = this.mContext.getString(R.string.mobile_data_temp_using, str);
        }
        this.mPreference.setSummary(str);
    }

    public boolean updateHotspotNetwork() {
        WifiPickerTrackerHelper wifiPickerTrackerHelper = this.mWifiPickerTrackerHelper;
        if (wifiPickerTrackerHelper == null) {
            return false;
        }
        WifiEntry wifiEntry = wifiPickerTrackerHelper.mWifiPickerTracker.mConnectedWifiEntry;
        if (!(wifiEntry instanceof HotspotNetworkEntry)) {
            return false;
        }
        HotspotNetworkEntry hotspotNetworkEntry = (HotspotNetworkEntry) wifiEntry;
        Drawable drawable =
                this.mContext.getDrawable(
                        WifiUtils.getHotspotIconResource(hotspotNetworkEntry.getDeviceType()));
        if (drawable != null) {
            drawable.setTintList(
                    Utils.getColorAttr(this.mContext, android.R.attr.colorControlNormal));
            this.mPreference.setIcon(drawable);
        }
        this.mPreference.setSummary(hotspotNetworkEntry.getAlternateSummary());
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        Drawable drawable;
        if (this.mPreference == null) {
            return;
        }
        if (this.mInternetType == 2 && updateHotspotNetwork()) {
            return;
        }
        int intValue = sIconMap.get(Integer.valueOf(this.mInternetType)).intValue();
        if (intValue != 0 && (drawable = this.mContext.getDrawable(intValue)) != null) {
            drawable.setTintList(
                    Utils.getColorAttr(this.mContext, android.R.attr.colorControlNormal));
            this.mPreference.setIcon(drawable);
        }
        int i = this.mInternetType;
        if (i == 2) {
            this.mPreference.setSummary(this.mSummaryHelper.getSummary());
            return;
        }
        if (i == 3) {
            updateCellularSummary();
            return;
        }
        int intValue2 = ((Integer) ((HashMap) sSummaryMap).get(Integer.valueOf(i))).intValue();
        if (intValue2 != 0) {
            this.mPreference.setSummary(intValue2);
        }
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onNumSavedNetworksChanged() {}

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onNumSavedSubscriptionsChanged() {}

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onWifiStateChanged() {}
}
