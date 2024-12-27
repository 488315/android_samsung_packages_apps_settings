package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.network.MobileNetworkRepository;
import com.android.settingslib.RestrictedSwitchPreference;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.mobile.dataservice.MobileNetworkInfoEntity;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RoamingPreferenceController extends TelephonyTogglePreferenceController
        implements LifecycleObserver, MobileNetworkRepository.MobileNetworkCallback {
    private static final String DIALOG_TAG = "MobileDataDialog";
    private static final String TAG = "RoamingController";
    private CarrierConfigManager mCarrierConfigManager;
    FragmentManager mFragmentManager;
    protected LifecycleOwner mLifecycleOwner;
    MobileNetworkInfoEntity mMobileNetworkInfoEntity;
    private List<MobileNetworkInfoEntity> mMobileNetworkInfoEntityList;
    protected MobileNetworkRepository mMobileNetworkRepository;
    private RestrictedSwitchPreference mSwitchPreference;
    private TelephonyManager mTelephonyManager;

    public RoamingPreferenceController(
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

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAllMobileNetworkInfoChanged$0(
            MobileNetworkInfoEntity mobileNetworkInfoEntity) {
        if (Integer.parseInt(mobileNetworkInfoEntity.subId) == this.mSubId) {
            this.mMobileNetworkInfoEntity = mobileNetworkInfoEntity;
            update();
            refreshSummary(this.mSwitchPreference);
        }
    }

    private void showDialog() {
        int i = this.mSubId;
        RoamingDialogFragment roamingDialogFragment = new RoamingDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("sub_id_key", i);
        roamingDialogFragment.setArguments(bundle);
        roamingDialogFragment.show(this.mFragmentManager, DIALOG_TAG);
    }

    private void update() {
        RestrictedSwitchPreference restrictedSwitchPreference = this.mSwitchPreference;
        if (restrictedSwitchPreference == null
                || restrictedSwitchPreference.mHelper.mDisabledByAdmin) {
            return;
        }
        restrictedSwitchPreference.setEnabled(this.mSubId != -1);
        this.mSwitchPreference.setChecked(getThreadEnabled());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mSwitchPreference =
                (RestrictedSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        PersistableBundle configForSubId =
                this.mCarrierConfigManager.getConfigForSubId(this.mSubId);
        return (configForSubId == null || !configForSubId.getBoolean("force_home_network_bool"))
                ? 0
                : 2;
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
            MobileNetworkInfoEntity mobileNetworkInfoEntity) {
        this.mFragmentManager = fragmentManager;
        this.mSubId = i;
        this.mMobileNetworkInfoEntity = mobileNetworkInfoEntity;
        TelephonyManager telephonyManager =
                (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        this.mTelephonyManager = telephonyManager;
        int i2 = this.mSubId;
        if (i2 == -1) {
            return;
        }
        TelephonyManager createForSubscriptionId = telephonyManager.createForSubscriptionId(i2);
        if (createForSubscriptionId != null) {
            this.mTelephonyManager = createForSubscriptionId;
            return;
        }
        Log.w(TAG, "fail to init in sub" + this.mSubId);
        this.mSubId = -1;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        MobileNetworkInfoEntity mobileNetworkInfoEntity = this.mMobileNetworkInfoEntity;
        if (mobileNetworkInfoEntity == null) {
            return false;
        }
        return mobileNetworkInfoEntity.isDataRoamingEnabled;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public boolean isDialogNeeded() {
        MobileNetworkInfoEntity mobileNetworkInfoEntity = this.mMobileNetworkInfoEntity;
        boolean z =
                mobileNetworkInfoEntity == null
                        ? false
                        : mobileNetworkInfoEntity.isDataRoamingEnabled;
        PersistableBundle configForSubId =
                this.mCarrierConfigManager.getConfigForSubId(this.mSubId);
        return !z
                && (configForSubId == null
                        || !configForSubId.getBoolean("disable_charge_indication_bool"));
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public void onAllMobileNetworkInfoChanged(List<MobileNetworkInfoEntity> list) {
        this.mMobileNetworkInfoEntityList = list;
        list.forEach(
                new Consumer() { // from class:
                                 // com.android.settings.network.telephony.RoamingPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        RoamingPreferenceController.this.lambda$onAllMobileNetworkInfoChanged$0(
                                (MobileNetworkInfoEntity) obj);
                    }
                });
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public void onDataRoamingChanged(int i, boolean z) {
        if (i == this.mSubId) {
            update();
            return;
        }
        Log.d(TAG, "onDataRoamingChanged - wrong subId : " + i + " / " + z);
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
        if (isDialogNeeded()) {
            showDialog();
            return false;
        }
        this.mTelephonyManager.setDataRoamingEnabled(z);
        return true;
    }

    public void setMobileNetworkInfoEntity(MobileNetworkInfoEntity mobileNetworkInfoEntity) {
        this.mMobileNetworkInfoEntity = mobileNetworkInfoEntity;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mSwitchPreference = (RestrictedSwitchPreference) preference;
        update();
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyTogglePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        return this.mSubId != -1 ? 0 : 1;
    }

    public RoamingPreferenceController(Context context, String str) {
        super(context, str);
        this.mMobileNetworkInfoEntityList = new ArrayList();
        this.mCarrierConfigManager =
                (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
        this.mMobileNetworkRepository = MobileNetworkRepository.getInstance(context);
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onActiveSubInfoChanged(List list) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAirplaneModeChanged(boolean z) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAllUiccInfoChanged(List list) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAvailableSubInfoChanged(List list) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onCallStateChanged(int i) {}
}
