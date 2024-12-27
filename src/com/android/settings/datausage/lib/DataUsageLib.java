package com.android.settings.datausage.lib;

import android.content.Context;
import android.net.NetworkTemplate;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DataUsageLib {
    public static final NetworkTemplate getMobileTemplate(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService((Class<Object>) TelephonyManager.class);
        Intrinsics.checkNotNull(systemService);
        TelephonyManager telephonyManager = (TelephonyManager) systemService;
        int subscriptionId = telephonyManager.getSubscriptionId();
        Object systemService2 = context.getSystemService((Class<Object>) SubscriptionManager.class);
        Intrinsics.checkNotNull(systemService2);
        List<SubscriptionInfo> availableSubscriptionInfoList =
                ((SubscriptionManager) systemService2).getAvailableSubscriptionInfoList();
        if (availableSubscriptionInfoList == null) {
            Log.i("DataUsageLib", "Subscription is not inited: " + i);
            return getMobileTemplateForSubId(telephonyManager, subscriptionId);
        }
        for (SubscriptionInfo subscriptionInfo : availableSubscriptionInfoList) {
            if (subscriptionInfo != null && subscriptionInfo.getSubscriptionId() == i) {
                NetworkTemplate mobileTemplateForSubId =
                        getMobileTemplateForSubId(telephonyManager, i);
                String[] mergedImsisFromGroup =
                        telephonyManager.createForSubscriptionId(i).getMergedImsisFromGroup();
                Intrinsics.checkNotNullExpressionValue(
                        mergedImsisFromGroup, "getMergedImsisFromGroup(...)");
                if (mergedImsisFromGroup.length == 0) {
                    Log.i("DataUsageLib", "mergedSubscriberIds is empty.");
                    return mobileTemplateForSubId;
                }
                Set subscriberIds = mobileTemplateForSubId.getSubscriberIds();
                Intrinsics.checkNotNullExpressionValue(subscriberIds, "getSubscriberIds(...)");
                String str = (String) CollectionsKt___CollectionsKt.firstOrNull(subscriberIds);
                if (str == null) {
                    return mobileTemplateForSubId;
                }
                Set set = ArraysKt___ArraysKt.toSet(mergedImsisFromGroup);
                if (set.size() != mergedImsisFromGroup.length) {
                    String arrays = Arrays.toString(mergedImsisFromGroup);
                    Intrinsics.checkNotNullExpressionValue(arrays, "toString(...)");
                    Log.wtf("DataUsageLib", "Duplicated merged list detected: ".concat(arrays));
                }
                if (!set.contains(str)) {
                    return mobileTemplateForSubId;
                }
                NetworkTemplate build =
                        new NetworkTemplate.Builder(mobileTemplateForSubId.getMatchRule())
                                .setSubscriberIds(set)
                                .setMeteredness(mobileTemplateForSubId.getMeteredness())
                                .build();
                Intrinsics.checkNotNull(build);
                return build;
            }
        }
        Log.i("DataUsageLib", "Subscription is not active: " + i);
        return getMobileTemplateForSubId(telephonyManager, subscriptionId);
    }

    public static final NetworkTemplate getMobileTemplateForSubId(
            TelephonyManager telephonyManager, int i) {
        String subscriberId = telephonyManager.getSubscriberId(i);
        NetworkTemplate build =
                (subscriberId == null
                                ? new NetworkTemplate.Builder(1)
                                : new NetworkTemplate.Builder(10)
                                        .setSubscriberIds(SetsKt.setOf(subscriberId)))
                        .setMeteredness(1)
                        .build();
        Intrinsics.checkNotNullExpressionValue(build, "build(...)");
        return build;
    }
}
