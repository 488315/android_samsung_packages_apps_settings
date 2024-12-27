package com.android.settings.network.telephony;

import android.telephony.SubscriptionInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ToggleSubscriptionDialogActivity$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                String str = ToggleSubscriptionDialogActivity.ARG_enable;
                break;
            case 1:
                String str2 = ToggleSubscriptionDialogActivity.ARG_enable;
                break;
            default:
                String str3 = ToggleSubscriptionDialogActivity.ARG_enable;
                break;
        }
        return ((SubscriptionInfo) obj).isEmbedded();
    }
}
