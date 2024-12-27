package com.android.wifitrackerlib;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.text.TextUtils;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class PasspointNetworkDetailsTracker$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PasspointNetworkDetailsTracker$$ExternalSyntheticLambda0(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return TextUtils.equals(
                        (String) obj2,
                        PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(
                                ((PasspointConfiguration) obj).getUniqueId()));
            case 1:
                WifiConfiguration wifiConfiguration = (WifiConfiguration) obj;
                return wifiConfiguration.isPasspoint()
                        && TextUtils.equals(
                                (String) obj2,
                                PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(
                                        wifiConfiguration.getKey()));
            default:
                PasspointNetworkDetailsTracker passpointNetworkDetailsTracker =
                        (PasspointNetworkDetailsTracker) obj2;
                passpointNetworkDetailsTracker.getClass();
                return TextUtils.equals(
                        PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(
                                ((PasspointConfiguration) obj).getUniqueId()),
                        passpointNetworkDetailsTracker.mChosenEntry.mKey);
        }
    }
}
