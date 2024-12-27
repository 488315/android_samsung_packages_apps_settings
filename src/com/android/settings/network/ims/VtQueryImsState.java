package com.android.settings.network.ims;

import android.content.Context;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.telephony.ims.ImsMmTelManager;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VtQueryImsState extends ImsQueryController {
    public Context mContext;
    public int mSubId;

    public boolean isEnabledByUser(int i) {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            return false;
        }
        try {
            return ImsMmTelManager.createForSubscriptionId(i).isVtSettingEnabled();
        } catch (IllegalArgumentException e) {
            Log.w("QueryVtUserSetting", "fail to get VT settings. subId=" + i, e);
            return false;
        }
    }

    public boolean isTtyEnabled(Context context) {
        return ((TelecomManager) context.getSystemService(TelecomManager.class)).getCurrentTtyMode()
                != 0;
    }
}
