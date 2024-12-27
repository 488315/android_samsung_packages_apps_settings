package com.android.settings.sim.receivers;

import android.content.Context;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SimSlotChangeHandler {
    public static volatile SimSlotChangeHandler sSlotChangeHandler;
    public Context mContext;
    public SubscriptionManager mSubMgr;
    public TelephonyManager mTelMgr;

    public static boolean isSuwFinished(Context context) {
        try {
            return Settings.Global.getInt(context.getContentResolver(), "device_provisioned") == 1;
        } catch (Settings.SettingNotFoundException e) {
            Log.e("SimSlotChangeHandler", "Cannot get DEVICE_PROVISIONED from the device.", e);
            return false;
        }
    }
}
