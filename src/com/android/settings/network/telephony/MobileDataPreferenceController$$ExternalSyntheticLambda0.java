package com.android.settings.network.telephony;

import com.android.settingslib.mobile.dataservice.MobileNetworkInfoEntity;
import com.android.settingslib.mobile.dataservice.SubscriptionInfoEntity;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class MobileDataPreferenceController$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MobileDataPreferenceController f$0;

    public /* synthetic */ MobileDataPreferenceController$$ExternalSyntheticLambda0(
            MobileDataPreferenceController mobileDataPreferenceController, int i) {
        this.$r8$classId = i;
        this.f$0 = mobileDataPreferenceController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        MobileDataPreferenceController mobileDataPreferenceController = this.f$0;
        switch (i) {
            case 0:
                mobileDataPreferenceController.lambda$onActiveSubInfoChanged$0(
                        (SubscriptionInfoEntity) obj);
                break;
            default:
                mobileDataPreferenceController.lambda$onAllMobileNetworkInfoChanged$1(
                        (MobileNetworkInfoEntity) obj);
                break;
        }
    }
}
