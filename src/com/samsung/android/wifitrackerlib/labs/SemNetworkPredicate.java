package com.samsung.android.wifitrackerlib.labs;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.telephony.SubscriptionManager;

import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SemNetworkPredicate {
    public static final AllNetworkPredicate ALL_PREDICATE = new AllNetworkPredicate(0);
    public static final AllNetworkPredicate OPEN_NETWORK_PREDICATE = new AllNetworkPredicate(2);
    public static final AllNetworkPredicate WEP_NETWORK_PREDICATE = new AllNetworkPredicate(3);
    public static final AllNetworkPredicate LONG_UNUSED_NETWORK_PREDICATE =
            new AllNetworkPredicate(1);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AllNetworkPredicate {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AllNetworkPredicate(int i) {
            this.$r8$classId = i;
        }

        public final boolean matches(Context context, WifiConfiguration wifiConfiguration) {
            String str = null;
            switch (this.$r8$classId) {
                case 0:
                    break;
                case 1:
                    SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
                    SemWifiManager semWifiManager =
                            (SemWifiManager)
                                    context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
                    long currentTimeMillis = System.currentTimeMillis();
                    Map networkLastUpdatedTimeMap = semWifiManager.getNetworkLastUpdatedTimeMap();
                    if (wifiConfiguration == null
                            || (networkLastUpdatedTimeMap.containsKey(wifiConfiguration.getKey())
                                    && currentTimeMillis
                                                    - ((Long)
                                                                    networkLastUpdatedTimeMap.get(
                                                                            wifiConfiguration
                                                                                    .getKey()))
                                                            .longValue()
                                            > 15552000000L)) {
                        break;
                    }
                    break;
                case 2:
                    if (wifiConfiguration != null) {
                        SubscriptionManager subscriptionManager2 =
                                SemWifiUtils.mSubscriptionManager;
                        if (wifiConfiguration.getAuthType() == 0) {
                            if (wifiConfiguration.getAuthType() == 0) {
                                int i = wifiConfiguration.wepTxKeyIndex;
                                if (i >= 0) {
                                    String[] strArr = wifiConfiguration.wepKeys;
                                    if (i < strArr.length) {
                                        str = strArr[i];
                                    }
                                }
                                if (str != null) {}
                            }
                            break;
                        }
                    }
                    break;
                default:
                    if (wifiConfiguration != null) {
                        SubscriptionManager subscriptionManager3 =
                                SemWifiUtils.mSubscriptionManager;
                        if (wifiConfiguration.getAuthType() == 0
                                && wifiConfiguration.getAuthType() == 0) {
                            int i2 = wifiConfiguration.wepTxKeyIndex;
                            if (i2 >= 0) {
                                String[] strArr2 = wifiConfiguration.wepKeys;
                                if (i2 < strArr2.length) {
                                    str = strArr2[i2];
                                }
                            }
                            if (str != null) {
                                break;
                            }
                        }
                    }
                    break;
            }
            return true;
        }
    }
}
