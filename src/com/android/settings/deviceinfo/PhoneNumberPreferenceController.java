package com.android.settings.deviceinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.network.SubscriptionUtil;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PhoneNumberPreferenceController extends BasePreferenceController {
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_PREFERENCE_CATEGORY = "basic_info_category";
    private final List<Preference> mPreferenceList;
    private final SubscriptionManager mSubscriptionManager;
    private final TelephonyManager mTelephonyManager;

    public PhoneNumberPreferenceController(Context context, String str) {
        super(context, str);
        this.mPreferenceList = new ArrayList();
        this.mTelephonyManager =
                (TelephonyManager) this.mContext.getSystemService(TelephonyManager.class);
        this.mSubscriptionManager =
                (SubscriptionManager) this.mContext.getSystemService(SubscriptionManager.class);
    }

    private CharSequence getFirstPhoneNumber() {
        List<SubscriptionInfo> activeSubscriptionInfoList =
                this.mSubscriptionManager.getActiveSubscriptionInfoList();
        return (activeSubscriptionInfoList == null || activeSubscriptionInfoList.isEmpty())
                ? this.mContext.getText(R.string.device_info_default)
                : getFormattedPhoneNumber(activeSubscriptionInfoList.get(0));
    }

    private CharSequence getPhoneNumber(int i) {
        SubscriptionInfo subscriptionInfo = getSubscriptionInfo(i);
        return subscriptionInfo == null
                ? this.mContext.getText(R.string.device_info_default)
                : getFormattedPhoneNumber(subscriptionInfo);
    }

    private CharSequence getPreferenceTitle(int i) {
        return this.mTelephonyManager.getPhoneCount() > 1
                ? this.mContext.getString(R.string.status_number_sim_slot, Integer.valueOf(i + 1))
                : this.mContext.getString(R.string.status_number);
    }

    public Preference createNewPreference(Context context) {
        return new Preference(context);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        if (SubscriptionUtil.isSimHardwareVisible(this.mContext)) {
            Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
            PreferenceCategory preferenceCategory =
                    (PreferenceCategory) preferenceScreen.findPreference(KEY_PREFERENCE_CATEGORY);
            this.mPreferenceList.add(findPreference);
            int order = findPreference.getOrder();
            for (int i = 1; i < this.mTelephonyManager.getPhoneCount(); i++) {
                Preference createNewPreference = createNewPreference(preferenceScreen.getContext());
                createNewPreference.setSelectable(false);
                createNewPreference.setCopyingEnabled(true);
                createNewPreference.setOrder(order + i);
                createNewPreference.setKey(KEY_PHONE_NUMBER + i);
                preferenceCategory.addPreference(createNewPreference);
                this.mPreferenceList.add(createNewPreference);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return SubscriptionUtil.isSimHardwareVisible(this.mContext) ? 0 : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public String getFormattedPhoneNumber(SubscriptionInfo subscriptionInfo) {
        String bidiFormattedPhoneNumber =
                SubscriptionUtil.getBidiFormattedPhoneNumber(this.mContext, subscriptionInfo);
        return TextUtils.isEmpty(bidiFormattedPhoneNumber)
                ? this.mContext.getString(R.string.device_info_default)
                : bidiFormattedPhoneNumber;
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

    public SubscriptionInfo getSubscriptionInfo(int i) {
        List<SubscriptionInfo> activeSubscriptionInfoList =
                this.mSubscriptionManager.getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList == null) {
            return null;
        }
        for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
            if (subscriptionInfo.getSimSlotIndex() == i) {
                return subscriptionInfo;
            }
        }
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        for (int i = 0; i < this.mPreferenceList.size(); i++) {
            Preference preference2 = this.mPreferenceList.get(i);
            preference2.setTitle(getPreferenceTitle(i));
            preference2.setSummary(getPhoneNumber(i));
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public boolean useDynamicSliceSummary() {
        return true;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
