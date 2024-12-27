package com.android.wifitrackerlib;

import android.net.wifi.hotspot2.PasspointConfiguration;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SavedNetworkTracker$$ExternalSyntheticLambda8
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Map f$0;

    public /* synthetic */ SavedNetworkTracker$$ExternalSyntheticLambda8(int i, Map map) {
        this.$r8$classId = i;
        this.f$0 = map;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Map map = this.f$0;
        switch (i) {
            case 0:
                PasspointWifiEntry passpointWifiEntry =
                        (PasspointWifiEntry) ((Map.Entry) obj).getValue();
                PasspointConfiguration passpointConfiguration =
                        (PasspointConfiguration) map.remove(passpointWifiEntry.mKey);
                if (passpointConfiguration == null) {
                    return true;
                }
                passpointWifiEntry.updatePasspointConfig(passpointConfiguration);
                return false;
            default:
                StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
                standardWifiEntry.updateConfig((List) map.remove(standardWifiEntry.mKey));
                return !standardWifiEntry.isSaved();
        }
    }
}
