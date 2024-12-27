package com.android.settings.deviceinfo.simstatus;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserManager;
import android.provider.SearchIndexableData;
import android.telephony.SubscriptionInfo;
import android.text.TextUtils;
import android.util.Log;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.network.SubscriptionUtil;
import com.android.settingslib.Utils;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SimStatusPreferenceController extends BasePreferenceController {
    private static final String KEY_PREFERENCE_CATEGORY = "device_detail_category";
    private Fragment mFragment;
    private Observer mLifecycleOwnerObserver;
    private Observer mSimChangeObserver;
    private SlotSimStatus mSlotSimStatus;

    public SimStatusPreferenceController(Context context, String str) {
        super(context, str);
    }

    private CharSequence getCarrierName(int i) {
        SubscriptionInfo subscriptionInfo = getSubscriptionInfo(i);
        return subscriptionInfo != null
                ? subscriptionInfo.getCarrierName()
                : this.mContext.getText(R.string.device_info_not_available);
    }

    private String getPreferenceTitle(int i) {
        return this.mSlotSimStatus.size() > 1
                ? this.mContext.getString(
                        R.string.sim_status_title_sim_slot, Integer.valueOf(i + 1))
                : this.mContext.getString(R.string.sim_status_title);
    }

    private SubscriptionInfo getSubscriptionInfo(int i) {
        SlotSimStatus slotSimStatus = this.mSlotSimStatus;
        if (slotSimStatus == null) {
            return null;
        }
        return slotSimStatus.getSubscriptionInfo(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateState$0(Preference preference, int i, Object obj) {
        updateStateBySlot(preference, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateState$1(
            final Preference preference, LiveData liveData, LifecycleOwner lifecycleOwner) {
        if (lifecycleOwner != null) {
            final int simSlotIndex = getSimSlotIndex();
            Observer observer =
                    new Observer() { // from class:
                                     // com.android.settings.deviceinfo.simstatus.SimStatusPreferenceController$$ExternalSyntheticLambda1
                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj) {
                            SimStatusPreferenceController.this.lambda$updateState$0(
                                    preference, simSlotIndex, obj);
                        }
                    };
            this.mSimChangeObserver = observer;
            this.mSlotSimStatus.observe(lifecycleOwner, observer);
            return;
        }
        Observer observer2 = this.mSimChangeObserver;
        if (observer2 != null) {
            this.mSlotSimStatus.removeObserver(observer2);
            this.mSimChangeObserver = null;
        }
        liveData.removeObserver(this.mLifecycleOwnerObserver);
    }

    public Preference createNewPreference(Context context) {
        return new Preference(context);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (!SubscriptionUtil.isSimHardwareVisible(this.mContext) || this.mSlotSimStatus == null) {
            return;
        }
        Preference findPreference = preferenceScreen.findPreference("sim_status");
        if (isAvailable() && findPreference != null && findPreference.isVisible()) {
            PreferenceCategory preferenceCategory =
                    (PreferenceCategory) preferenceScreen.findPreference(KEY_PREFERENCE_CATEGORY);
            this.mSlotSimStatus.mBasePreferenceOrdering = findPreference.getOrder();
            preferenceScreen.removePreference(findPreference);
            findPreference.setVisible(false);
            for (int i = 0; i < this.mSlotSimStatus.size(); i++) {
                Preference createNewPreference = createNewPreference(preferenceScreen.getContext());
                createNewPreference.setOrder(this.mSlotSimStatus.mBasePreferenceOrdering + 1 + i);
                this.mSlotSimStatus.getClass();
                createNewPreference.setKey(i == -1 ? "sim_status" : "sim_status" + (i + 1));
                preferenceCategory.addPreference(createNewPreference);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (getSimSlotIndex() == -1) {
            return 3;
        }
        return (SubscriptionUtil.isSimHardwareVisible(this.mContext)
                        && ((UserManager) this.mContext.getSystemService(UserManager.class))
                                .isAdminUser()
                        && !Utils.isWifiOnly(this.mContext))
                ? 0
                : 2;
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

    public int getSimSlotIndex() {
        int i;
        SlotSimStatus slotSimStatus = this.mSlotSimStatus;
        if (slotSimStatus == null) {
            return -1;
        }
        String preferenceKey = getPreferenceKey();
        slotSimStatus.getClass();
        try {
            i = Integer.parseInt(preferenceKey.substring(10));
        } catch (Exception e) {
            StringBuilder m =
                    ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                            "Preference key invalid: ", preferenceKey, ". Error Msg: ");
            m.append(e.getMessage());
            Log.w("SlotSimStatus", m.toString());
            i = 0;
        }
        return i - 1;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        int simSlotIndex;
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())
                || (simSlotIndex = getSimSlotIndex()) == -1) {
            return false;
        }
        Fragment fragment = this.mFragment;
        String preferenceTitle = getPreferenceTitle(simSlotIndex);
        int[] iArr = SimStatusDialogFragment.sViewIdsInDigitFormat;
        FragmentManager childFragmentManager = fragment.getChildFragmentManager();
        if (childFragmentManager.findFragmentByTag("SimStatusDialog") != null) {
            return true;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("arg_key_sim_slot", simSlotIndex);
        bundle.putString("arg_key_dialog_title", preferenceTitle);
        SimStatusDialogFragment simStatusDialogFragment = new SimStatusDialogFragment();
        simStatusDialogFragment.setArguments(bundle);
        simStatusDialogFragment.show(childFragmentManager, "SimStatusDialog");
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void init(Fragment fragment, SlotSimStatus slotSimStatus) {
        this.mFragment = fragment;
        this.mSlotSimStatus = slotSimStatus;
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

    @Override // com.android.settings.core.BasePreferenceController
    public void updateDynamicRawDataToIndex(List<SearchIndexableRaw> list) {
        int simSlotIndex = getSimSlotIndex();
        SubscriptionInfo subscriptionInfo = getSubscriptionInfo(simSlotIndex);
        if (subscriptionInfo == null) {
            return;
        }
        int i =
                subscriptionInfo.isEmbedded()
                        ? R.string.keywords_sim_status_esim
                        : R.string.keywords_sim_status;
        SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(this.mContext);
        ((SearchIndexableData) searchIndexableRaw).key = getPreferenceKey();
        searchIndexableRaw.title = getPreferenceTitle(simSlotIndex);
        searchIndexableRaw.screenTitle = this.mContext.getString(R.string.about_settings);
        searchIndexableRaw.keywords = this.mContext.getString(i).toString();
        list.add(searchIndexableRaw);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(final Preference preference) {
        Fragment fragment = this.mFragment;
        if (fragment == null) {
            return;
        }
        if (this.mLifecycleOwnerObserver != null) {
            if (this.mSimChangeObserver != null) {
                updateStateBySlot(preference, getSimSlotIndex());
            }
        } else {
            final LiveData viewLifecycleOwnerLiveData = fragment.getViewLifecycleOwnerLiveData();
            Observer observer =
                    new Observer() { // from class:
                                     // com.android.settings.deviceinfo.simstatus.SimStatusPreferenceController$$ExternalSyntheticLambda0
                        @Override // androidx.lifecycle.Observer
                        public final void onChanged(Object obj) {
                            SimStatusPreferenceController.this.lambda$updateState$1(
                                    preference, viewLifecycleOwnerLiveData, (LifecycleOwner) obj);
                        }
                    };
            this.mLifecycleOwnerObserver = observer;
            viewLifecycleOwnerLiveData.observeForever(observer);
        }
    }

    public void updateStateBySlot(Preference preference, int i) {
        SubscriptionInfo subscriptionInfo = getSubscriptionInfo(i);
        preference.setEnabled(subscriptionInfo != null);
        preference.setCopyingEnabled(subscriptionInfo != null);
        preference.setTitle(getPreferenceTitle(i));
        preference.setSummary(getCarrierName(i));
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
