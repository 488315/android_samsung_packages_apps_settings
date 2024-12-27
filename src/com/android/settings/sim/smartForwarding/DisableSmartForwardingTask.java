package com.android.settings.sim.smartForwarding;

import android.telephony.CallForwardingInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DisableSmartForwardingTask implements Runnable {
    public final CallForwardingInfo[] callForwardingInfo;
    public final boolean[] callWaitingStatus;
    public final TelephonyManager tm;

    public DisableSmartForwardingTask(
            TelephonyManager telephonyManager,
            boolean[] zArr,
            CallForwardingInfo[] callForwardingInfoArr) {
        this.tm = telephonyManager;
        this.callWaitingStatus = zArr;
        this.callForwardingInfo = callForwardingInfoArr;
    }

    @Override // java.lang.Runnable
    public final void run() {
        for (int i = 0; i < this.tm.getActiveModemCount(); i++) {
            int subscriptionId = SubscriptionManager.getSubscriptionId(i);
            if (this.callWaitingStatus != null && subscriptionId != -1) {
                ActionBarContextView$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Restore call waiting to "),
                        this.callWaitingStatus[i],
                        "SmartForwarding");
                this.tm
                        .createForSubscriptionId(subscriptionId)
                        .setCallWaitingEnabled(this.callWaitingStatus[i], null, null);
            }
            CallForwardingInfo[] callForwardingInfoArr = this.callForwardingInfo;
            if (callForwardingInfoArr != null
                    && callForwardingInfoArr[i] != null
                    && subscriptionId != -1) {
                Log.d(
                        "SmartForwarding",
                        "Restore call forwarding to " + this.callForwardingInfo[i]);
                this.tm
                        .createForSubscriptionId(subscriptionId)
                        .setCallForwarding(this.callForwardingInfo[i], null, null);
            }
        }
    }
}
