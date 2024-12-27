package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.network.MobileNetworkRepository;
import com.android.settings.wifi.WifiPickerTrackerHelper;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.mobile.dataservice.MobileNetworkInfoEntity;
import com.android.settingslib.mobile.dataservice.SubscriptionInfoEntity;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MobileDataPreferenceController extends TelephonyTogglePreferenceController
        implements LifecycleObserver, MobileNetworkRepository.MobileNetworkCallback {
    private static final String DIALOG_TAG = "MobileDataDialog";
    private int mDefaultSubId;
    int mDialogType;
    private FragmentManager mFragmentManager;
    protected LifecycleOwner mLifecycleOwner;
    MobileNetworkInfoEntity mMobileNetworkInfoEntity;
    private List<MobileNetworkInfoEntity> mMobileNetworkInfoEntityList;
    protected MobileNetworkRepository mMobileNetworkRepository;
    boolean mNeedDialog;
    private TwoStatePreference mPreference;
    SubscriptionInfoEntity mSubscriptionInfoEntity;
    private List<SubscriptionInfoEntity> mSubscriptionInfoEntityList;
    private SubscriptionManager mSubscriptionManager;
    private TelephonyManager mTelephonyManager;
    private WifiPickerTrackerHelper mWifiPickerTrackerHelper;

    public MobileDataPreferenceController(
            Context context,
            String str,
            Lifecycle lifecycle,
            LifecycleOwner lifecycleOwner,
            int i) {
        this(context, str);
        this.mSubId = i;
        this.mLifecycleOwner = lifecycleOwner;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    private TelephonyManager getTelephonyManager() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager != null) {
            return telephonyManager;
        }
        TelephonyManager telephonyManager2 =
                (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        int i = this.mSubId;
        if (i != -1) {
            telephonyManager2 = telephonyManager2.createForSubscriptionId(i);
        }
        this.mTelephonyManager = telephonyManager2;
        return telephonyManager2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onActiveSubInfoChanged$0(
            SubscriptionInfoEntity subscriptionInfoEntity) {
        if (subscriptionInfoEntity.getSubId() == this.mSubId) {
            this.mSubscriptionInfoEntity = subscriptionInfoEntity;
            if (subscriptionInfoEntity.getSubId()
                    == SubscriptionManager.getDefaultDataSubscriptionId()) {
                this.mDefaultSubId = subscriptionInfoEntity.getSubId();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAllMobileNetworkInfoChanged$1(
            MobileNetworkInfoEntity mobileNetworkInfoEntity) {
        if (Integer.parseInt(mobileNetworkInfoEntity.subId) == this.mSubId) {
            this.mMobileNetworkInfoEntity = mobileNetworkInfoEntity;
            update();
            refreshSummary(this.mPreference);
        }
    }

    private void showDialog(int i) {
        int i2 = this.mSubId;
        MobileDataDialogFragment mobileDataDialogFragment = new MobileDataDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("dialog_type", i);
        bundle.putInt("subId", i2);
        mobileDataDialogFragment.setArguments(bundle);
        mobileDataDialogFragment.show(this.mFragmentManager, DIALOG_TAG);
    }

    private void update() {
        TwoStatePreference twoStatePreference;
        if (this.mSubscriptionInfoEntity == null
                || (twoStatePreference = this.mPreference) == null) {
            return;
        }
        twoStatePreference.setChecked(getThreadEnabled());
        if (this.mSubscriptionInfoEntity.isOpportunistic) {
            this.mPreference.setEnabled(false);
            this.mPreference.setSummary(R.string.mobile_data_settings_summary_auto_switch);
        } else {
            this.mPreference.setEnabled(true);
            this.mPreference.setSummary(R.string.mobile_data_settings_summary);
        }
        if (this.mSubscriptionInfoEntity.isValidSubscription) {
            this.mPreference.setSelectable(true);
        } else {
            this.mPreference.setSelectable(false);
            this.mPreference.setSummary(R.string.mobile_data_settings_summary_unavailable);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (TwoStatePreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        return 2;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        if (!this.mNeedDialog) {
            return true;
        }
        showDialog(this.mDialogType);
        return true;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public void init(
            FragmentManager fragmentManager,
            int i,
            SubscriptionInfoEntity subscriptionInfoEntity,
            MobileNetworkInfoEntity mobileNetworkInfoEntity) {
        this.mFragmentManager = fragmentManager;
        this.mSubId = i;
        this.mTelephonyManager = null;
        this.mTelephonyManager = getTelephonyManager();
        this.mSubscriptionInfoEntity = subscriptionInfoEntity;
        this.mMobileNetworkInfoEntity = mobileNetworkInfoEntity;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        MobileNetworkInfoEntity mobileNetworkInfoEntity = this.mMobileNetworkInfoEntity;
        if (mobileNetworkInfoEntity == null) {
            return false;
        }
        return mobileNetworkInfoEntity.isMobileDataEnabled;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public boolean isDialogNeeded() {
        boolean z = !getThreadEnabled();
        TelephonyManager telephonyManager = getTelephonyManager();
        this.mTelephonyManager = telephonyManager;
        boolean z2 = telephonyManager.getActiveModemCount() > 1;
        boolean z3 = this.mDefaultSubId != this.mSubId;
        if (!z || !z2 || !z3) {
            return false;
        }
        this.mDialogType = 1;
        return true;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public void onActiveSubInfoChanged(List<SubscriptionInfoEntity> list) {
        this.mSubscriptionInfoEntityList = list;
        list.forEach(new MobileDataPreferenceController$$ExternalSyntheticLambda0(this, 0));
        update();
        refreshSummary(this.mPreference);
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public void onAllMobileNetworkInfoChanged(List<MobileNetworkInfoEntity> list) {
        this.mMobileNetworkInfoEntityList = list;
        list.forEach(new MobileDataPreferenceController$$ExternalSyntheticLambda0(this, 1));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        this.mMobileNetworkRepository.addRegister(this.mLifecycleOwner, this, this.mSubId);
        this.mMobileNetworkRepository.updateEntity();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        this.mMobileNetworkRepository.removeRegister(this);
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        boolean isDialogNeeded = isDialogNeeded();
        this.mNeedDialog = isDialogNeeded;
        if (isDialogNeeded) {
            return false;
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("setMobileDataEnabled: ", DIALOG_TAG, z);
        MobileNetworkUtils.setMobileDataEnabled(this.mSubId, this.mContext, z, false);
        WifiPickerTrackerHelper wifiPickerTrackerHelper = this.mWifiPickerTrackerHelper;
        if (wifiPickerTrackerHelper == null
                || wifiPickerTrackerHelper.isCarrierNetworkProvisionEnabled(this.mSubId)) {
            return true;
        }
        this.mWifiPickerTrackerHelper.setCarrierNetworkEnabled(z);
        return true;
    }

    public void setMobileNetworkInfoEntity(MobileNetworkInfoEntity mobileNetworkInfoEntity) {
        this.mMobileNetworkInfoEntity = mobileNetworkInfoEntity;
    }

    public void setSubscriptionInfoEntity(SubscriptionInfoEntity subscriptionInfoEntity) {
        this.mSubscriptionInfoEntity = subscriptionInfoEntity;
    }

    public void setWifiPickerTrackerHelper(WifiPickerTrackerHelper wifiPickerTrackerHelper) {
        this.mWifiPickerTrackerHelper = wifiPickerTrackerHelper;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mPreference = (TwoStatePreference) preference;
        update();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public MobileDataPreferenceController(Context context, String str) {
        super(context, str);
        this.mSubscriptionInfoEntityList = new ArrayList();
        this.mMobileNetworkInfoEntityList = new ArrayList();
        this.mDefaultSubId = -1;
        this.mSubscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        this.mMobileNetworkRepository = MobileNetworkRepository.getInstance(context);
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAirplaneModeChanged(boolean z) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAllUiccInfoChanged(List list) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAvailableSubInfoChanged(List list) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onCallStateChanged(int i) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onDataRoamingChanged(int i, boolean z) {}
}
