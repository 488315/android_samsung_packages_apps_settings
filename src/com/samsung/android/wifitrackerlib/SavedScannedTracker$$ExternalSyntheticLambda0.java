package com.samsung.android.wifitrackerlib;

import android.net.wifi.hotspot2.PasspointConfiguration;

import com.android.wifitrackerlib.PasspointWifiEntry;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class SavedScannedTracker$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(
                ((PasspointConfiguration) obj).getUniqueId());
    }
}
