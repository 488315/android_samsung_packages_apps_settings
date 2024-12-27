package com.android.settings.spa.network;

import android.content.Context;
import android.telephony.SubscriptionManager;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.network.telephony.SubscriptionRepositoryKt;
import com.android.settings.wifi.WifiPickerTrackerHelper;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class NetworkCellularGroupProviderKt$setMobileData$2 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ boolean $enabled;
    final /* synthetic */ int $subId;
    final /* synthetic */ SubscriptionManager $subscriptionManager;
    final /* synthetic */ WifiPickerTrackerHelper $wifiPickerTrackerHelper;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkCellularGroupProviderKt$setMobileData$2(
            int i,
            boolean z,
            SubscriptionManager subscriptionManager,
            Context context,
            WifiPickerTrackerHelper wifiPickerTrackerHelper,
            Continuation continuation) {
        super(2, continuation);
        this.$subId = i;
        this.$enabled = z;
        this.$subscriptionManager = subscriptionManager;
        this.$context = context;
        this.$wifiPickerTrackerHelper = wifiPickerTrackerHelper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NetworkCellularGroupProviderKt$setMobileData$2(
                this.$subId,
                this.$enabled,
                this.$subscriptionManager,
                this.$context,
                this.$wifiPickerTrackerHelper,
                continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        NetworkCellularGroupProviderKt$setMobileData$2
                networkCellularGroupProviderKt$setMobileData$2 =
                        (NetworkCellularGroupProviderKt$setMobileData$2)
                                create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        networkCellularGroupProviderKt$setMobileData$2.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Log.d(
                "NetworkCellularGroupProvider",
                "setMobileData[" + this.$subId + "]: " + this.$enabled);
        int i = this.$subId;
        SubscriptionManager subscriptionManager = this.$subscriptionManager;
        int[] activeSubscriptionIdList =
                subscriptionManager != null
                        ? subscriptionManager.getActiveSubscriptionIdList()
                        : null;
        if (activeSubscriptionIdList != null && activeSubscriptionIdList.length == 1) {
            i = activeSubscriptionIdList[0];
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    i,
                    "There is only one sim in the device, correct dds as ",
                    "NetworkCellularGroupProvider");
        }
        if (this.$enabled) {
            MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                    i, "setDefaultData: [", "]", "NetworkCellularGroupProvider");
            SubscriptionManager subscriptionManager2 = this.$subscriptionManager;
            if (subscriptionManager2 != null) {
                subscriptionManager2.setDefaultDataSubId(i);
            }
        }
        Context context = this.$context;
        Flow subscriptionsChangedFlow =
                (2 & 2) != 0 ? SubscriptionRepositoryKt.subscriptionsChangedFlow(context) : null;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(subscriptionsChangedFlow, "subscriptionsChangedFlow");
        boolean z = this.$enabled;
        WifiPickerTrackerHelper wifiPickerTrackerHelper = this.$wifiPickerTrackerHelper;
        if (SubscriptionManager.isValidSubscriptionId(i)) {
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                    "setMobileData: ", "TelephonyRepository", z);
            MobileNetworkUtils.setMobileDataEnabled(i, context, z, true);
            if (wifiPickerTrackerHelper != null
                    && !wifiPickerTrackerHelper.isCarrierNetworkProvisionEnabled(i)) {
                wifiPickerTrackerHelper.setCarrierNetworkEnabled(z);
            }
        }
        return Unit.INSTANCE;
    }
}
