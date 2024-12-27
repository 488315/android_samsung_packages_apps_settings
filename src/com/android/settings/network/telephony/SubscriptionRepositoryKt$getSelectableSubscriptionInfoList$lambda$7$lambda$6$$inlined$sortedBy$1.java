package com.android.settings.network.telephony;

import android.telephony.SubscriptionInfo;

import kotlin.comparisons.ComparisonsKt__ComparisonsKt;

import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final
class SubscriptionRepositoryKt$getSelectableSubscriptionInfoList$lambda$7$lambda$6$$inlined$sortedBy$1
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return ComparisonsKt__ComparisonsKt.compareValues(
                Integer.valueOf(((SubscriptionInfo) obj).getSubscriptionId()),
                Integer.valueOf(((SubscriptionInfo) obj2).getSubscriptionId()));
    }
}
