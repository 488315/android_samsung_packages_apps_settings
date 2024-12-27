package com.android.settings.wifi.addappnetworks;

import android.net.wifi.hotspot2.PasspointConfiguration;
import android.text.TextUtils;

import com.android.wifitrackerlib.WifiEntry;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AddAppNetworksFragment$$ExternalSyntheticLambda3
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AddAppNetworksFragment$$ExternalSyntheticLambda3(int i, Object obj) {
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
                        ((AddAppNetworksFragment.UiConfigurationItem) obj2)
                                .mWifiNetworkSuggestion.getSsid(),
                        ((WifiEntry) obj).getSsid());
            default:
                return ((PasspointConfiguration) obj).equals((PasspointConfiguration) obj2);
        }
    }
}
