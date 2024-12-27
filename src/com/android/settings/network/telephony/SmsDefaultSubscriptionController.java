package com.android.settings.network.telephony;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SubscriptionManager;

import androidx.lifecycle.LifecycleOwner;

import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.mobile.dataservice.SubscriptionInfoEntity;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SmsDefaultSubscriptionController extends DefaultSubscriptionController {
    private final boolean mIsAskEverytimeSupported;

    public SmsDefaultSubscriptionController(
            Context context, String str, Lifecycle lifecycle, LifecycleOwner lifecycleOwner) {
        super(context, str, lifecycle, lifecycleOwner);
        this.mIsAskEverytimeSupported =
                this.mContext.getResources().getBoolean(R.bool.config_supportMicNearUltrasound);
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController
    public int getDefaultSubscriptionId() {
        int defaultSmsSubscriptionId = SubscriptionManager.getDefaultSmsSubscriptionId();
        for (SubscriptionInfoEntity subscriptionInfoEntity : this.mSubInfoEntityList) {
            int subId = subscriptionInfoEntity.getSubId();
            if (subscriptionInfoEntity.isActiveSubscriptionId
                    && subId == defaultSmsSubscriptionId) {
                return subId;
            }
        }
        return -1;
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return MobileNetworkUtils.getPreferredStatus(
                isRtlMode(), this.mContext, false, this.mSubInfoEntityList);
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController
    public boolean isAskEverytimeSupported() {
        return this.mIsAskEverytimeSupported;
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController
    public void setDefaultSubscription(int i) {
        this.mManager.setDefaultSmsSubId(i);
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public SmsDefaultSubscriptionController(Context context, String str) {
        super(context, str);
        this.mIsAskEverytimeSupported =
                this.mContext.getResources().getBoolean(R.bool.config_supportMicNearUltrasound);
    }

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAirplaneModeChanged(boolean z) {}

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAllMobileNetworkInfoChanged(List list) {}

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAllUiccInfoChanged(List list) {}

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAvailableSubInfoChanged(List list) {}

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onCallStateChanged(int i) {}

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.DefaultSubscriptionReceiver.DefaultSubscriptionListener
    public /* bridge */ /* synthetic */ void onDefaultDataChanged(int i) {}

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.DefaultSubscriptionReceiver.DefaultSubscriptionListener
    public /* bridge */ /* synthetic */ void onDefaultSubInfoChanged(int i) {}

    @Override // com.android.settings.network.telephony.DefaultSubscriptionController,
              // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onDataRoamingChanged(int i, boolean z) {}
}
