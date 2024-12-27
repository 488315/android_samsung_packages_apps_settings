package com.android.settings.wifi.details2;

import android.os.SimpleClock;
import android.os.SystemClock;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiPrivacyPageProviderKt$getWifiEntry$elapsedRealtimeClock$1
        extends SimpleClock {
    public final long millis() {
        return SystemClock.elapsedRealtime();
    }
}
