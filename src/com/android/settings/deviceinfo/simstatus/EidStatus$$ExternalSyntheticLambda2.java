package com.android.settings.deviceinfo.simstatus;

import android.telephony.SubscriptionInfo;
import android.telephony.UiccCardInfo;

import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class EidStatus$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Objects.nonNull((SubscriptionInfo) obj);
            case 1:
                return ((SubscriptionInfo) obj).isEmbedded();
            case 2:
                return ((UiccCardInfo) obj).isEuicc();
            default:
                return Objects.nonNull((String) obj);
        }
    }
}
