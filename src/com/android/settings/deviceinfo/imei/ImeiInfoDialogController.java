package com.android.settings.deviceinfo.imei;

import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ImeiInfoDialogController {
    static final int ID_CDMA_SETTINGS = 2131362526;
    static final int ID_GSM_SETTINGS = 2131363565;
    static final int ID_IMEI_SV_VALUE = 2131363784;
    static final int ID_IMEI_VALUE = 2131363786;
    static final int ID_MEID_NUMBER_VALUE = 2131364152;
    static final int ID_MIN_NUMBER_VALUE = 2131364191;
    static final int ID_PRL_VERSION_VALUE = 2131364774;
    public final int mSlotId;
    public final SubscriptionInfo mSubscriptionInfo;
    public final TelephonyManager mTelephonyManager;

    public ImeiInfoDialogController(ImeiInfoDialogFragment imeiInfoDialogFragment, int i) {
        this.mSlotId = i;
        Context context = imeiInfoDialogFragment.getContext();
        SubscriptionInfo activeSubscriptionInfoForSimSlotIndex =
                ((SubscriptionManager) context.getSystemService(SubscriptionManager.class))
                        .getActiveSubscriptionInfoForSimSlotIndex(i);
        this.mSubscriptionInfo = activeSubscriptionInfoForSimSlotIndex;
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        if (activeSubscriptionInfoForSimSlotIndex != null) {
            this.mTelephonyManager =
                    ((TelephonyManager) context.getSystemService(TelephonyManager.class))
                            .createForSubscriptionId(
                                    activeSubscriptionInfoForSimSlotIndex.getSubscriptionId());
        } else if (isValidSlotIndex(i, telephonyManager)) {
            this.mTelephonyManager = telephonyManager;
        } else {
            this.mTelephonyManager = null;
        }
    }

    private boolean isValidSlotIndex(int i, TelephonyManager telephonyManager) {
        return i >= 0 && i < telephonyManager.getPhoneCount();
    }

    public String getCdmaPrlVersion() {
        return this.mSubscriptionInfo != null
                ? this.mTelephonyManager.getCdmaPrlVersion()
                : ApnSettings.MVNO_NONE;
    }

    public String getMeid() {
        return this.mTelephonyManager.getMeid(this.mSlotId);
    }

    public boolean isCdmaLteEnabled() {
        return this.mTelephonyManager.isLteCdmaEvdoGsmWcdmaEnabled();
    }
}
