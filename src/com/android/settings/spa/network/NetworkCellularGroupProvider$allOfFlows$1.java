package com.android.settings.spa.network;

import android.telephony.SubscriptionInfo;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.AdaptedFunctionReference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final /* synthetic */ class NetworkCellularGroupProvider$allOfFlows$1
        extends AdaptedFunctionReference implements Function5 {
    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        List list = (List) obj;
        int intValue = ((Number) obj2).intValue();
        int intValue2 = ((Number) obj3).intValue();
        int intValue3 = ((Number) obj4).intValue();
        NetworkCellularGroupProvider networkCellularGroupProvider =
                (NetworkCellularGroupProvider) this.receiver;
        networkCellularGroupProvider.defaultVoiceSubId = intValue;
        networkCellularGroupProvider.defaultSmsSubId = intValue2;
        networkCellularGroupProvider.defaultDataSubId = intValue3;
        int i = -1;
        if (intValue3 != -1) {
            ArrayList arrayList = new ArrayList();
            for (Object obj6 : list) {
                SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj6;
                if (subscriptionInfo.getSimSlotIndex() != -1
                        && subscriptionInfo.getSubscriptionId()
                                != networkCellularGroupProvider.defaultDataSubId) {
                    arrayList.add(obj6);
                }
            }
            ArrayList arrayList2 =
                    new ArrayList(
                            CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(Integer.valueOf(((SubscriptionInfo) it.next()).getSubscriptionId()));
            }
            Integer num = (Integer) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList2);
            if (num != null) {
                i = num.intValue();
            }
        }
        networkCellularGroupProvider.nonDds = i;
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "defaultDataSubId: ",
                ", nonDds: ",
                networkCellularGroupProvider.defaultDataSubId,
                i,
                "NetworkCellularGroupProvider");
        return Unit.INSTANCE;
    }
}
