package com.samsung.android.settings.wifi.intelligent;

import android.net.wifi.WifiConfiguration;
import android.telephony.SubscriptionManager;

import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class TurnOnWifiAutomaticallySettings$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TurnOnWifiAutomaticallySettings f$0;

    public /* synthetic */ TurnOnWifiAutomaticallySettings$$ExternalSyntheticLambda0(
            TurnOnWifiAutomaticallySettings turnOnWifiAutomaticallySettings, int i) {
        this.$r8$classId = i;
        this.f$0 = turnOnWifiAutomaticallySettings;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        TurnOnWifiAutomaticallySettings turnOnWifiAutomaticallySettings = this.f$0;
        TurnOnWifiAutomaticallySettings.CombinedWifiConfiguration combinedWifiConfiguration =
                (TurnOnWifiAutomaticallySettings.CombinedWifiConfiguration) obj;
        switch (i) {
            case 0:
                int i2 = TurnOnWifiAutomaticallySettings.$r8$clinit;
                turnOnWifiAutomaticallySettings.getClass();
                if (!combinedWifiConfiguration.mWifiConfig.isPasspoint()) {
                    WifiConfiguration wifiConfiguration = combinedWifiConfiguration.mWifiConfig;
                    if (wifiConfiguration.allowAutojoin && wifiConfiguration.carrierId == -1) {
                        SemWifiManager semWifiManager =
                                turnOnWifiAutomaticallySettings.mSemWifiManager;
                        String key = wifiConfiguration.getKey();
                        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
                        if (semWifiManager.hasConfiguredNetworkLocations(key)) {
                            int i3 = combinedWifiConfiguration.mSemWifiConfig.networkScore;
                            if (i3 >= 9 || i3 == -1) {
                                break;
                            }
                        }
                    }
                }
                break;
            default:
                int i4 = TurnOnWifiAutomaticallySettings.$r8$clinit;
                turnOnWifiAutomaticallySettings.getClass();
                if (!combinedWifiConfiguration.mWifiConfig.isPasspoint()) {
                    WifiConfiguration wifiConfiguration2 = combinedWifiConfiguration.mWifiConfig;
                    if (wifiConfiguration2.allowAutojoin && wifiConfiguration2.carrierId == -1) {
                        SemWifiManager semWifiManager2 =
                                turnOnWifiAutomaticallySettings.mSemWifiManager;
                        String key2 = wifiConfiguration2.getKey();
                        SubscriptionManager subscriptionManager2 =
                                SemWifiUtils.mSubscriptionManager;
                        if (semWifiManager2.hasConfiguredNetworkLocations(key2)) {
                            SemWifiConfiguration semWifiConfiguration =
                                    combinedWifiConfiguration.mSemWifiConfig;
                            if (semWifiConfiguration.networkScore <= 0
                                    || semWifiConfiguration.isNoInternetAccessExpected()) {
                                WifiConfiguration wifiConfiguration3 =
                                        combinedWifiConfiguration.mWifiConfig;
                                int i5 = turnOnWifiAutomaticallySettings.mCurrentConnectedNetworkId;
                                if (i5 == -1
                                        || i5 != wifiConfiguration3.networkId
                                        || combinedWifiConfiguration.mSemWifiConfig.networkScore
                                                == -1) {}
                            }
                            if (combinedWifiConfiguration.mSemWifiConfig.networkScore < 9) {
                                break;
                            }
                        }
                    }
                }
                break;
        }
        return true;
    }
}
