package com.android.wifitrackerlib;

import android.net.wifi.WifiConfiguration;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SavedNetworkTracker$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ SavedNetworkTracker$$ExternalSyntheticLambda1(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        String str = this.f$0;
        WifiConfiguration wifiConfiguration = (WifiConfiguration) obj;
        switch (i) {
        }
        return SavedNetworkTracker.isCertificateUsedByConfiguration(wifiConfiguration, str);
    }
}
