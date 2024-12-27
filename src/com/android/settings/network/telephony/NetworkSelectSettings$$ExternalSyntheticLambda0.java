package com.android.settings.network.telephony;

import com.android.settings.network.telephony.scan.NetworkScanRepository;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NetworkSelectSettings$$ExternalSyntheticLambda0
        implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NetworkSelectSettings f$0;

    public /* synthetic */ NetworkSelectSettings$$ExternalSyntheticLambda0(
            NetworkSelectSettings networkSelectSettings, int i) {
        this.$r8$classId = i;
        this.f$0 = networkSelectSettings;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Unit lambda$onViewCreated$1;
        Unit lambda$launchNetworkScan$2;
        int i = this.$r8$classId;
        NetworkSelectSettings networkSelectSettings = this.f$0;
        switch (i) {
            case 0:
                lambda$onViewCreated$1 =
                        networkSelectSettings.lambda$onViewCreated$1(
                                (NetworkSelectRepository.NetworkRegistrationAndForbiddenInfo) obj);
                return lambda$onViewCreated$1;
            default:
                lambda$launchNetworkScan$2 =
                        networkSelectSettings.lambda$launchNetworkScan$2(
                                (NetworkScanRepository.NetworkScanResult) obj);
                return lambda$launchNetworkScan$2;
        }
    }
}
