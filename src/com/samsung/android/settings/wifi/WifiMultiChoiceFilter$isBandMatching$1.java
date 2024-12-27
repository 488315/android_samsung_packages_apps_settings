package com.samsung.android.settings.wifi;

import android.net.wifi.ScanResult;
import android.telephony.SubscriptionManager;

import com.samsung.android.wifitrackerlib.SemWifiUtils;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiMultiChoiceFilter$isBandMatching$1 implements Predicate {
    public static final WifiMultiChoiceFilter$isBandMatching$1 INSTANCE =
            new WifiMultiChoiceFilter$isBandMatching$1(0);
    public static final WifiMultiChoiceFilter$isBandMatching$1 INSTANCE$1 =
            new WifiMultiChoiceFilter$isBandMatching$1(1);
    public static final WifiMultiChoiceFilter$isBandMatching$1 INSTANCE$2 =
            new WifiMultiChoiceFilter$isBandMatching$1(2);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WifiMultiChoiceFilter$isBandMatching$1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = ((ScanResult) obj).frequency;
                SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
                if (i >= 2400 && i <= 2500) {
                    break;
                }
                break;
            case 1:
                int i2 = ((ScanResult) obj).frequency;
                SubscriptionManager subscriptionManager2 = SemWifiUtils.mSubscriptionManager;
                if (i2 >= 4900 && i2 <= 5900) {
                    break;
                }
                break;
            default:
                int i3 = ((ScanResult) obj).frequency;
                SubscriptionManager subscriptionManager3 = SemWifiUtils.mSubscriptionManager;
                if (i3 >= 5925 && i3 <= 7125) {
                    break;
                }
                break;
        }
        return true;
    }
}
