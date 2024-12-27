package com.android.settings.network.telephony;

import android.content.Context;
import android.os.ParcelUuid;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.network.SubscriptionUtil;

import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SubscriptionRepositoryKt {
    public static final List getSelectableSubscriptionInfoList(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        SubscriptionManager requireSubscriptionManager = requireSubscriptionManager(context);
        List availableSubscriptionInfoList =
                requireSubscriptionManager.getAvailableSubscriptionInfoList();
        if (availableSubscriptionInfoList == null) {
            return EmptyList.INSTANCE;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : availableSubscriptionInfoList) {
            if (SubscriptionUtil.isSubscriptionVisible(
                    requireSubscriptionManager, context, (SubscriptionInfo) obj)) {
                arrayList.add(obj);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            ParcelUuid groupUuid = ((SubscriptionInfo) next).getGroupUuid();
            Object obj2 = linkedHashMap.get(groupUuid);
            if (obj2 == null) {
                obj2 = new ArrayList();
                linkedHashMap.put(groupUuid, obj2);
            }
            ((List) obj2).add(next);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            ParcelUuid parcelUuid = (ParcelUuid) entry.getKey();
            List list = (List) entry.getValue();
            if (parcelUuid != null) {
                List list2 = list;
                ArrayList arrayList3 = new ArrayList();
                for (Object obj3 : list2) {
                    if (((SubscriptionInfo) obj3).getSimSlotIndex() != -1) {
                        arrayList3.add(obj3);
                    }
                }
                boolean isEmpty = arrayList3.isEmpty();
                Collection collection = arrayList3;
                if (isEmpty) {
                    collection =
                            CollectionsKt___CollectionsKt.sortedWith(
                                    list2,
                                    new SubscriptionRepositoryKt$getSelectableSubscriptionInfoList$lambda$7$lambda$6$$inlined$sortedBy$1());
                }
                list = CollectionsKt___CollectionsKt.take(collection, 1);
            }
            CollectionsKt___CollectionsKt.addAll(list, arrayList2);
        }
        List sortedWith =
                CollectionsKt___CollectionsKt.sortedWith(
                        arrayList2,
                        ComparisonsKt__ComparisonsKt.compareBy(
                                new Function1() { // from class:
                                                  // com.android.settings.network.telephony.SubscriptionRepositoryKt$getSelectableSubscriptionInfoList$3
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj4;
                                        Intrinsics.checkNotNull(subscriptionInfo);
                                        return Integer.valueOf(
                                                subscriptionInfo.getSimSlotIndex() != -1
                                                        ? subscriptionInfo.getSimSlotIndex()
                                                        : Preference.DEFAULT_ORDER);
                                    }
                                },
                                new Function1() { // from class:
                                                  // com.android.settings.network.telephony.SubscriptionRepositoryKt$getSelectableSubscriptionInfoList$4
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj4) {
                                        return Integer.valueOf(
                                                ((SubscriptionInfo) obj4).getSubscriptionId());
                                    }
                                }));
        Log.d("SubscriptionRepository", "getSelectableSubscriptionInfoList: " + sortedWith);
        return sortedWith;
    }

    public static final SubscriptionManager requireSubscriptionManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        SubscriptionManager subscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        Intrinsics.checkNotNull(subscriptionManager);
        return subscriptionManager;
    }

    public static final Flow subscriptionsChangedFlow(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        return FlowKt.flowOn(
                FlowKt.onEach(
                        FlowKt.buffer$default(
                                FlowKt.callbackFlow(
                                        new SubscriptionRepositoryKt$subscriptionsChangedFlow$1(
                                                context, null)),
                                -1),
                        new SubscriptionRepositoryKt$subscriptionsChangedFlow$2(2, null)),
                Dispatchers.Default);
    }
}
