package com.android.settings.sim.smartForwarding;

import android.content.Context;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SmartForwardingUtils {
    public static String getPhoneNumber(Context context, int i) {
        SubscriptionInfo activeSubscriptionInfo =
                ((SubscriptionManager) context.getSystemService(SubscriptionManager.class))
                        .getActiveSubscriptionInfo(SubscriptionManager.getSubscriptionId(i));
        return activeSubscriptionInfo != null
                ? activeSubscriptionInfo.getNumber()
                : ApnSettings.MVNO_NONE;
    }
}
