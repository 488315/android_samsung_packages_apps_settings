package com.android.settings.network;

import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccCardInfo;
import android.util.Log;

import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SimOnboardingService {
    public List activeSubInfoList;
    public List availableSubInfoList;
    public Function1 callback;
    public final Map renameMutableMap;
    public List slotInfoList;
    public SubscriptionManager subscriptionManager;
    public final StateFlowImpl targetPrimarySimAutoDataSwitch;
    public int targetPrimarySimCalls;
    public int targetPrimarySimMobileData;
    public int targetPrimarySimTexts;
    public int targetSubId = -1;
    public SubscriptionInfo targetSubInfo;
    public TelephonyManager telephonyManager;
    public List uiccCardInfoList;
    public final List userSelectedSubInfoList;

    public SimOnboardingService() {
        EmptyList emptyList = EmptyList.INSTANCE;
        this.availableSubInfoList = emptyList;
        this.activeSubInfoList = emptyList;
        this.slotInfoList = emptyList;
        this.uiccCardInfoList = emptyList;
        this.targetPrimarySimCalls = -1;
        this.targetPrimarySimTexts = -1;
        this.targetPrimarySimMobileData = -1;
        this.targetPrimarySimAutoDataSwitch = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.callback =
                new Function1() { // from class:
                                  // com.android.settings.network.SimOnboardingService$callback$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        SimOnboardingActivity$Companion$CallbackType it =
                                (SimOnboardingActivity$Companion$CallbackType) obj;
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Unit.INSTANCE;
                    }
                };
        this.renameMutableMap = new LinkedHashMap();
        this.userSelectedSubInfoList = new ArrayList();
    }

    public final void clear() {
        this.targetSubId = -1;
        this.targetSubInfo = null;
        EmptyList emptyList = EmptyList.INSTANCE;
        this.availableSubInfoList = emptyList;
        this.activeSubInfoList = emptyList;
        this.slotInfoList = emptyList;
        this.uiccCardInfoList = emptyList;
        this.targetPrimarySimCalls = -1;
        this.targetPrimarySimTexts = -1;
        this.targetPrimarySimMobileData = -1;
        ((LinkedHashMap) this.renameMutableMap).clear();
        ((ArrayList) this.userSelectedSubInfoList).clear();
    }

    public final List getSelectableSubscriptionInfoList() {
        SubscriptionInfo subscriptionInfo;
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.activeSubInfoList);
        if (!CollectionsKt___CollectionsKt.contains(arrayList, this.targetSubInfo)
                && (subscriptionInfo = this.targetSubInfo) != null) {
            arrayList.add(subscriptionInfo);
        }
        return CollectionsKt___CollectionsKt.toList(arrayList);
    }

    public final String getSubscriptionInfoDisplayName(SubscriptionInfo subInfo) {
        Intrinsics.checkNotNullParameter(subInfo, "subInfo");
        String str =
                (String)
                        ((LinkedHashMap) this.renameMutableMap)
                                .get(Integer.valueOf(subInfo.getSubscriptionId()));
        return str == null ? subInfo.getDisplayName().toString() : str;
    }

    public final boolean isMultipleEnabledProfilesSupported() {
        if (this.uiccCardInfoList.isEmpty()) {
            Log.w("SimOnboardingService", "UICC cards info list is empty.");
            return false;
        }
        List list = this.uiccCardInfoList;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (((UiccCardInfo) it.next()).isMultipleEnabledProfilesSupported()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object startSetupPrimarySim(
            android.content.Context r6,
            com.android.settings.wifi.WifiPickerTrackerHelper r7,
            kotlin.coroutines.Continuation r8) {
        /*
            r5 = this;
            boolean r0 = r8 instanceof com.android.settings.network.SimOnboardingService$startSetupPrimarySim$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.settings.network.SimOnboardingService$startSetupPrimarySim$1 r0 = (com.android.settings.network.SimOnboardingService$startSetupPrimarySim$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.network.SimOnboardingService$startSetupPrimarySim$1 r0 = new com.android.settings.network.SimOnboardingService$startSetupPrimarySim$1
            r0.<init>(r5, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r5 = r0.L$0
            com.android.settings.network.SimOnboardingService r5 = (com.android.settings.network.SimOnboardingService) r5
            kotlin.ResultKt.throwOnFailure(r8)
            goto L49
        L2b:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L33:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.scheduling.DefaultScheduler r8 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.network.SimOnboardingService$startSetupPrimarySim$2 r2 = new com.android.settings.network.SimOnboardingService$startSetupPrimarySim$2
            r4 = 0
            r2.<init>(r5, r6, r7, r4)
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r6 = kotlinx.coroutines.BuildersKt.withContext(r8, r2, r0)
            if (r6 != r1) goto L49
            return r1
        L49:
            kotlin.jvm.functions.Function1 r5 = r5.callback
            com.android.settings.network.SimOnboardingActivity$Companion$CallbackType r6 = com.android.settings.network.SimOnboardingActivity$Companion$CallbackType.CALLBACK_FINISH
            r5.invoke(r6)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.SimOnboardingService.startSetupPrimarySim(android.content.Context,"
                    + " com.android.settings.wifi.WifiPickerTrackerHelper,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }
}
