package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SubscriptionManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.network.DefaultSubscriptionReceiver;
import com.android.settings.network.MobileNetworkRepository;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.mobile.dataservice.SubscriptionInfoEntity;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DefaultSubscriptionController extends TelephonyBasePreferenceController
        implements LifecycleObserver,
                Preference.OnPreferenceChangeListener,
                MobileNetworkRepository.MobileNetworkCallback,
                DefaultSubscriptionReceiver.DefaultSubscriptionListener {
    private static final String TAG = "DefaultSubController";
    private DefaultSubscriptionReceiver mDataSubscriptionChangedReceiver;
    private boolean mIsRtlMode;
    protected LifecycleOwner mLifecycleOwner;
    protected SubscriptionManager mManager;
    protected MobileNetworkRepository mMobileNetworkRepository;
    protected ListPreference mPreference;
    List<SubscriptionInfoEntity> mSubInfoEntityList;

    public DefaultSubscriptionController(
            Context context, String str, Lifecycle lifecycle, LifecycleOwner lifecycleOwner) {
        this(context, str);
        this.mLifecycleOwner = lifecycleOwner;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CharSequence lambda$refreshSummary$0(Preference preference) {
        return getSummary();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CharSequence lambda$updateEntries$1(
            List list, Preference preference) {
        return ((SubscriptionInfoEntity) list.get(0)).uniqueName;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (ListPreference) preferenceScreen.findPreference(getPreferenceKey());
        updateEntries();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.network.telephony.TelephonyAvailabilityCallback
    public int getAvailabilityStatus(int i) {
        return 2;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    public abstract int getDefaultSubscriptionId();

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @VisibleForTesting
    public List<SubscriptionInfoEntity> getSubscriptionInfoList() {
        return this.mSubInfoEntityList;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public boolean isAskEverytimeSupported() {
        return true;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    public boolean isRtlMode() {
        return this.mIsRtlMode;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public void onActiveSubInfoChanged(List<SubscriptionInfoEntity> list) {
        this.mSubInfoEntityList = list;
        updateEntries();
        refreshSummary(this.mPreference);
    }

    @Override // com.android.settings.network.DefaultSubscriptionReceiver.DefaultSubscriptionListener
    public void onDefaultSmsChanged(int i) {
        updateEntries();
        refreshSummary(this.mPreference);
    }

    @Override // com.android.settings.network.DefaultSubscriptionReceiver.DefaultSubscriptionListener
    public void onDefaultVoiceChanged(int i) {
        updateEntries();
        refreshSummary(this.mPreference);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        this.mMobileNetworkRepository.removeRegister(this);
        DefaultSubscriptionReceiver defaultSubscriptionReceiver =
                this.mDataSubscriptionChangedReceiver;
        defaultSubscriptionReceiver.mContext.unregisterReceiver(defaultSubscriptionReceiver);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        setDefaultSubscription(Integer.parseInt((String) obj));
        refreshSummary(this.mPreference);
        return true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        this.mMobileNetworkRepository.addRegister(this.mLifecycleOwner, this, -1);
        this.mMobileNetworkRepository.updateEntity();
        this.mDataSubscriptionChangedReceiver.registerReceiver();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void refreshSummary(Preference preference) {
        if (preference == null || this.mSubInfoEntityList.isEmpty()) {
            return;
        }
        preference.setSummaryProvider(
                new DefaultSubscriptionController$$ExternalSyntheticLambda0(1, this));
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public abstract void setDefaultSubscription(int i);

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @VisibleForTesting
    public void updateEntries() {
        if (this.mPreference == null) {
            return;
        }
        if (!isAvailable()) {
            this.mPreference.setVisible(false);
            return;
        }
        this.mPreference.setVisible(true);
        this.mPreference.setOnPreferenceChangeListener(this);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        List<SubscriptionInfoEntity> subscriptionInfoList = getSubscriptionInfoList();
        if (subscriptionInfoList.isEmpty()) {
            return;
        }
        if (subscriptionInfoList.size() == 1) {
            this.mPreference.setEnabled(false);
            this.mPreference.setSummaryProvider(
                    new DefaultSubscriptionController$$ExternalSyntheticLambda0(
                            0, subscriptionInfoList));
            return;
        }
        int defaultSubscriptionId = getDefaultSubscriptionId();
        boolean z = false;
        for (SubscriptionInfoEntity subscriptionInfoEntity : subscriptionInfoList) {
            if (!subscriptionInfoEntity.isOpportunistic) {
                arrayList.add(subscriptionInfoEntity.uniqueName);
                String str = subscriptionInfoEntity.subId;
                int parseInt = Integer.parseInt(str);
                arrayList2.add(str);
                if (parseInt == defaultSubscriptionId) {
                    z = true;
                }
            }
        }
        if (isAskEverytimeSupported()) {
            arrayList.add(this.mContext.getString(R.string.calls_and_sms_ask_every_time));
            arrayList2.add(Integer.toString(-1));
        }
        this.mPreference.setEnabled(true);
        this.mPreference.setEntries((CharSequence[]) arrayList.toArray(new CharSequence[0]));
        this.mPreference.mEntryValues = (CharSequence[]) arrayList2.toArray(new CharSequence[0]);
        if (z) {
            this.mPreference.setValue(Integer.toString(defaultSubscriptionId));
        } else {
            this.mPreference.setValue(Integer.toString(-1));
        }
    }

    @Override // com.android.settings.network.telephony.TelephonyBasePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public DefaultSubscriptionController(Context context, String str) {
        super(context, str);
        this.mSubInfoEntityList = new ArrayList();
        this.mManager = (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        this.mIsRtlMode = context.getResources().getConfiguration().getLayoutDirection() == 1;
        this.mMobileNetworkRepository = MobileNetworkRepository.getInstance(context);
        this.mDataSubscriptionChangedReceiver = new DefaultSubscriptionReceiver(context, this);
    }

    public /* bridge */ /* synthetic */ void onAirplaneModeChanged(boolean z) {}

    public /* bridge */ /* synthetic */ void onAllMobileNetworkInfoChanged(List list) {}

    public /* bridge */ /* synthetic */ void onAllUiccInfoChanged(List list) {}

    public /* bridge */ /* synthetic */ void onAvailableSubInfoChanged(List list) {}

    public /* bridge */ /* synthetic */ void onCallStateChanged(int i) {}

    public /* bridge */ /* synthetic */ void onDefaultDataChanged(int i) {}

    public /* bridge */ /* synthetic */ void onDefaultSubInfoChanged(int i) {}

    public /* bridge */ /* synthetic */ void onDataRoamingChanged(int i, boolean z) {}
}
