package com.android.settings.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CarrierConfigChangedReceiver extends BroadcastReceiver {
    public final CountDownLatch mLatch;

    public CarrierConfigChangedReceiver(CountDownLatch countDownLatch) {
        this.mLatch = countDownLatch;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (!isInitialStickyBroadcast()
                && "android.telephony.action.CARRIER_CONFIG_CHANGED".equals(intent.getAction())
                && intent.hasExtra("android.telephony.extra.SUBSCRIPTION_INDEX")) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1),
                    "subId from config changed: ",
                    "CarrierConfigChangedReceiver");
            this.mLatch.countDown();
        }
    }
}
