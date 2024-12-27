package com.android.settings.network;

import com.android.internal.net.VpnConfig;
import com.android.internal.net.VpnProfile;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class VpnPreferenceController$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ VpnPreferenceController$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean lambda$getNumberOfNonLegacyVpn$2;
        boolean lambda$getInsecureVpnCount$4;
        switch (this.$r8$classId) {
            case 0:
                lambda$getNumberOfNonLegacyVpn$2 =
                        VpnPreferenceController.lambda$getNumberOfNonLegacyVpn$2((VpnConfig) obj);
                return lambda$getNumberOfNonLegacyVpn$2;
            default:
                lambda$getInsecureVpnCount$4 =
                        VpnPreferenceController.lambda$getInsecureVpnCount$4((VpnProfile) obj);
                return lambda$getInsecureVpnCount$4;
        }
    }
}
