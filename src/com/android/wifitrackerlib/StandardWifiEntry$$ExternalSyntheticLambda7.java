package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import android.telephony.SubscriptionManager;

import com.samsung.android.wifitrackerlib.SemWifiUtils;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class StandardWifiEntry$$ExternalSyntheticLambda7
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = ((ScanResult) obj).frequency;
        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
        return !(i >= 5925 && i <= 7125);
    }
}
