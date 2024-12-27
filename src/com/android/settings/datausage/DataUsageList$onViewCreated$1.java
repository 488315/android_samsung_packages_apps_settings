package com.android.settings.datausage;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final /* synthetic */ class DataUsageList$onViewCreated$1 extends AdaptedFunctionReference
        implements Function2 {
    /* JADX WARN: Removed duplicated region for block: B:17:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002b  */
    @Override // kotlin.jvm.functions.Function2
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invoke(java.lang.Object r4, java.lang.Object r5) {
        /*
            r3 = this;
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            boolean r4 = r4.booleanValue()
            kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
            java.lang.Object r3 = r3.receiver
            com.android.settings.datausage.DataUsageList r3 = (com.android.settings.datausage.DataUsageList) r3
            r5 = 0
            if (r4 == 0) goto L26
            android.content.Context r4 = r3.requireContext()
            java.lang.String r0 = "requireContext(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r4, r0)
            android.telephony.SubscriptionManager r4 = com.android.settings.network.telephony.SubscriptionRepositoryKt.requireSubscriptionManager(r4)
            int r0 = r3.subId
            android.telephony.SubscriptionInfo r4 = r4.getActiveSubscriptionInfo(r0)
            if (r4 == 0) goto L26
            r4 = 1
            goto L27
        L26:
            r4 = r5
        L27:
            com.android.settings.datausage.DataUsageListHeaderController r0 = r3.dataUsageListHeaderController
            if (r0 == 0) goto L40
            boolean r1 = r0.isRoaming
            r2 = 8
            if (r1 == 0) goto L37
            android.view.View r5 = r0.configureButton
            r5.setVisibility(r2)
            goto L40
        L37:
            android.view.View r0 = r0.configureButton
            if (r4 == 0) goto L3c
            goto L3d
        L3c:
            r5 = r2
        L3d:
            r0.setVisibility(r5)
        L40:
            com.android.settings.datausage.ChartDataUsagePreferenceController r3 = r3.chartDataUsagePreferenceController
            if (r3 == 0) goto L47
            r3.setBillingCycleModifiable(r4)
        L47:
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.datausage.DataUsageList$onViewCreated$1.invoke(java.lang.Object,"
                    + " java.lang.Object):java.lang.Object");
    }
}
