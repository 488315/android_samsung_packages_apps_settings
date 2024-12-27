package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.util.ArrayMap;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settings.network.SubscriptionsChangeListener;
import com.android.settings.network.telephony.NetworkProviderWifiCallingGroup.PhoneCallStateTelephonyCallback;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NetworkProviderWifiCallingPreferenceController extends BasePreferenceController
        implements LifecycleObserver {
    private static final String PREFERENCE_CATEGORY_KEY = "provider_model_calling_category";
    private static final String TAG = "NetworkProviderWfcController";
    private NetworkProviderWifiCallingGroup mNetworkProviderWifiCallingGroup;
    private PreferenceCategory mPreferenceCategory;
    private PreferenceScreen mPreferenceScreen;

    public NetworkProviderWifiCallingPreferenceController(Context context, String str) {
        super(context, str);
    }

    public NetworkProviderWifiCallingGroup createWifiCallingControllerForSub(Lifecycle lifecycle) {
        Context context = this.mContext;
        NetworkProviderWifiCallingGroup networkProviderWifiCallingGroup =
                new NetworkProviderWifiCallingGroup(context);
        networkProviderWifiCallingGroup.mTelephonyManagerList = new HashMap();
        networkProviderWifiCallingGroup.mSimCallManagerList = new HashMap();
        networkProviderWifiCallingGroup.mCarrierConfigManager =
                (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
        networkProviderWifiCallingGroup.mSubscriptionManager =
                ((SubscriptionManager) context.getSystemService(SubscriptionManager.class))
                        .createForAllUserProfiles();
        networkProviderWifiCallingGroup.mPreferenceGroupKey = PREFERENCE_CATEGORY_KEY;
        networkProviderWifiCallingGroup.mWifiCallingForSubPreferences = new ArrayMap();
        networkProviderWifiCallingGroup.setSubscriptionInfoList(context);
        if (networkProviderWifiCallingGroup.mTelephonyCallback == null) {
            networkProviderWifiCallingGroup.mTelephonyCallback =
                    networkProviderWifiCallingGroup.new PhoneCallStateTelephonyCallback();
        }
        lifecycle.addObserver(networkProviderWifiCallingGroup);
        networkProviderWifiCallingGroup.mChangeListener =
                new SubscriptionsChangeListener(context, networkProviderWifiCallingGroup);
        return networkProviderWifiCallingGroup;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceScreen = preferenceScreen;
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference(PREFERENCE_CATEGORY_KEY);
        this.mPreferenceCategory = preferenceCategory;
        preferenceCategory.setVisible(isAvailable());
        this.mNetworkProviderWifiCallingGroup.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        NetworkProviderWifiCallingGroup networkProviderWifiCallingGroup =
                this.mNetworkProviderWifiCallingGroup;
        return (networkProviderWifiCallingGroup == null
                        || !networkProviderWifiCallingGroup.isAvailable())
                ? 3
                : 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init(Lifecycle lifecycle) {
        this.mNetworkProviderWifiCallingGroup = createWifiCallingControllerForSub(lifecycle);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
