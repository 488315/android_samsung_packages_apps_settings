package com.android.settings.network;

import android.content.Context;
import android.telephony.SubscriptionManager;

import com.android.settingslib.SignalIcon$MobileIconGroup;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class MobileIconGroupExtKt {
    public static final String getSummaryForSub(
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup, Context context, int i) {
        Intrinsics.checkNotNullParameter(signalIcon$MobileIconGroup, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        int i2 = signalIcon$MobileIconGroup.dataContentDescription;
        if (i2 == 0) {
            return ApnSettings.MVNO_NONE;
        }
        String string = SubscriptionManager.getResourcesForSubId(context, i).getString(i2);
        Intrinsics.checkNotNull(string);
        return string;
    }
}
