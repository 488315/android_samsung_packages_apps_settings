package com.samsung.android.settings.wifi.develop.homewifi;

import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class SsidListAdapter$$ExternalSyntheticLambda2 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        ExtendedBssidInfo extendedBssidInfo = (ExtendedBssidInfo) obj;
        ExtendedBssidInfo extendedBssidInfo2 = (ExtendedBssidInfo) obj2;
        return extendedBssidInfo.ssid.equals(extendedBssidInfo2.ssid)
                ? extendedBssidInfo2.rssi - extendedBssidInfo.rssi
                : extendedBssidInfo.ssid.compareTo(extendedBssidInfo2.ssid);
    }
}
