package com.samsung.android.settings.wifi.develop.homewifi;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.util.SemLog;

import java.util.HashSet;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class AdviserFragment$$ExternalSyntheticLambda2 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ExtendedBssidInfo extendedBssidInfo = (ExtendedBssidInfo) obj;
        HashSet hashSet = AdviserFragment.mDefaultNames;
        SemLog.d(
                "HomeWifi.AdviserFragment",
                "ssid="
                        + extendedBssidInfo.ssid
                        + " bssid="
                        + extendedBssidInfo.bssid
                        + " freq="
                        + extendedBssidInfo.freq
                        + " rssi="
                        + extendedBssidInfo.rssi
                        + " security="
                        + extendedBssidInfo.security.replace("\n", ApnSettings.MVNO_NONE));
    }
}
