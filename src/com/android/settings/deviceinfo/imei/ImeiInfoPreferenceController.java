package com.android.settings.deviceinfo.imei;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserManager;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.deviceinfo.simstatus.SlotSimStatus;
import com.android.settings.network.SubscriptionUtil;
import com.android.settingslib.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ImeiInfoPreferenceController extends BasePreferenceController {
    public static final String DEFAULT_KEY = "imei_info";
    private static final String KEY_PREFERENCE_CATEGORY = "device_detail_category";
    private static final String TAG = "ImeiInfoPreferenceController";
    private Fragment mFragment;
    private SlotSimStatus mSlotSimStatus;
    private TelephonyManager mTelephonyManager;

    public ImeiInfoPreferenceController(Context context, String str) {
        super(context, str);
    }

    private SubscriptionInfo getSubscriptionInfo(int i) {
        SlotSimStatus slotSimStatus = this.mSlotSimStatus;
        if (slotSimStatus == null) {
            return null;
        }
        return slotSimStatus.getSubscriptionInfo(i);
    }

    private CharSequence getSummary(int i) {
        return getPhoneType(i) == 2
                ? this.mTelephonyManager.getMeid(i)
                : this.mTelephonyManager.getImei(i);
    }

    private CharSequence getTitle(int i) {
        boolean z = isMultiSim() && isPrimaryImei(i);
        return getPhoneType(i) == 2 ? getTitleForCdmaPhone(i, z) : getTitleForGsmPhone(i, z);
    }

    private CharSequence getTitleForCdmaPhone(int i, boolean z) {
        return isMultiSim()
                ? this.mContext.getString(
                        z ? R.string.meid_multi_sim_primary : R.string.meid_multi_sim,
                        Integer.valueOf(i + 1))
                : this.mContext.getString(R.string.status_meid_number);
    }

    private CharSequence getTitleForGsmPhone(int i, boolean z) {
        return isMultiSim()
                ? this.mContext.getString(
                        z ? R.string.imei_multi_sim_primary : R.string.imei_multi_sim,
                        Integer.valueOf(i + 1))
                : this.mContext.getString(R.string.status_imei);
    }

    private boolean isMultiSim() {
        SlotSimStatus slotSimStatus = this.mSlotSimStatus;
        return slotSimStatus != null && slotSimStatus.size() > 1;
    }

    private int keyToSlotIndex(String str) {
        try {
            return Integer.valueOf(str.replace(DEFAULT_KEY, ApnSettings.MVNO_NONE)).intValue() - 1;
        } catch (Exception unused) {
            AbsAdapter$$ExternalSyntheticOutline0.m("Invalid key : ", str, TAG);
            return -1;
        }
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
        this.mTelephonyManager =
                (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        Preference findPreference = preferenceScreen.findPreference(DEFAULT_KEY);
        if (isAvailable() && findPreference != null && findPreference.isVisible()) {
            PreferenceCategory preferenceCategory =
                    (PreferenceCategory) preferenceScreen.findPreference(KEY_PREFERENCE_CATEGORY);
            int order = findPreference.getOrder();
            preferenceScreen.removePreference(findPreference);
            int i = 0;
            findPreference.setVisible(false);
            while (i < this.mSlotSimStatus.size()) {
                Preference createNewPreference = createNewPreference(preferenceScreen.getContext());
                createNewPreference.setOrder(order + 1 + i);
                StringBuilder sb = new StringBuilder(DEFAULT_KEY);
                i++;
                sb.append(i);
                createNewPreference.setKey(sb.toString());
                createNewPreference.setEnabled(true);
                createNewPreference.setCopyingEnabled(true);
                preferenceCategory.addPreference(createNewPreference);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (SubscriptionUtil.isSimHardwareVisible(this.mContext)
                        && ((UserManager) this.mContext.getSystemService(UserManager.class))
                                .isAdminUser()
                        && !Utils.isWifiOnly(this.mContext))
                ? 0
                : 3;
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

    public int getPhoneType(int i) {
        SubscriptionInfo subscriptionInfo = getSubscriptionInfo(i);
        return this.mTelephonyManager.getCurrentPhoneType(
                subscriptionInfo != null
                        ? subscriptionInfo.getSubscriptionId()
                        : Preference.DEFAULT_ORDER);
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
        int keyToSlotIndex = keyToSlotIndex(preference.getKey());
        if (keyToSlotIndex < 0) {
            return false;
        }
        ImeiInfoDialogFragment.show(
                keyToSlotIndex, this.mFragment, preference.getTitle().toString());
        preference.setSummary(getSummary(keyToSlotIndex));
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

    public boolean isPrimaryImei(int i) {
        String str;
        CharSequence summary = getSummary(i);
        if (summary == null) {
            return false;
        }
        try {
            str = this.mTelephonyManager.getPrimaryImei();
        } catch (Exception e) {
            Log.i(TAG, "PrimaryImei not available. " + e);
            str = null;
        }
        return str != null && str.equals(summary.toString());
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

    public void updatePreference(Preference preference, int i) {
        if (i < 0) {
            preference.setVisible(false);
        } else {
            preference.setTitle(getTitle(i));
            preference.setSummary(getSummary(i));
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        updatePreference(preference, keyToSlotIndex(preference.getKey()));
    }

    @Override // com.android.settings.slices.Sliceable
    public boolean useDynamicSliceSummary() {
        return true;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
