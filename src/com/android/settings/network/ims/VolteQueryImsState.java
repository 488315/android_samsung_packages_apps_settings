package com.android.settings.network.ims;

import android.content.Context;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.telephony.ims.ImsException;
import android.telephony.ims.ImsMmTelManager;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VolteQueryImsState extends ImsQueryController {
    public final Context mContext;
    public final int mSubId;

    public VolteQueryImsState(Context context, int i) {
        super(1, 0, 1);
        this.mContext = context;
        this.mSubId = i;
    }

    public final boolean isAllowUserControl() {
        int i = this.mSubId;
        if (SubscriptionManager.isValidSubscriptionId(i)) {
            return !isTtyEnabled(this.mContext) || isTtyOnVolteEnabled(i);
        }
        return false;
    }

    public boolean isEnabledByUser(int i) {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            return false;
        }
        try {
            return ImsMmTelManager.createForSubscriptionId(i).isAdvancedCallingSettingEnabled();
        } catch (IllegalArgumentException e) {
            Log.w("QueryEnhanced4gLteModeUserSetting", "fail to get VoLte settings. subId=" + i, e);
            return false;
        }
    }

    public boolean isTtyEnabled(Context context) {
        return ((TelecomManager) context.getSystemService(TelecomManager.class)).getCurrentTtyMode()
                != 0;
    }

    public final boolean isVoLteProvisioned() {
        int i = this.mSubId;
        if (!SubscriptionManager.isValidSubscriptionId(i) || !isProvisionedOnDevice(i)) {
            return false;
        }
        try {
            return isEnabledByPlatform(i);
        } catch (ImsException | IllegalArgumentException | InterruptedException e) {
            Log.w("VolteQueryImsState", "fail to get VoLte supporting status. subId=" + i, e);
            return false;
        }
    }

    public final boolean isEnabledByUser() {
        int i = this.mSubId;
        if (SubscriptionManager.isValidSubscriptionId(i)) {
            return isEnabledByUser(i);
        }
        return false;
    }
}
