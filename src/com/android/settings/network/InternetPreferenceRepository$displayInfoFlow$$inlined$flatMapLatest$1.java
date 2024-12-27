package com.android.settings.network;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\u0010\u0005\u001a\u00020\u0004\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00010\u00022\u0006\u0010\u0003\u001a\u00028\u0000H\u008a@"
        },
        d2 = {
            "T",
            "R",
            "Lkotlinx/coroutines/flow/FlowCollector;",
            "it",
            ApnSettings.MVNO_NONE,
            "<anonymous>"
        },
        k = 3,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class InternetPreferenceRepository$displayInfoFlow$$inlined$flatMapLatest$1
        extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ InternetPreferenceRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InternetPreferenceRepository$displayInfoFlow$$inlined$flatMapLatest$1(
            InternetPreferenceRepository internetPreferenceRepository, Continuation continuation) {
        super(3, continuation);
        this.this$0 = internetPreferenceRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        InternetPreferenceRepository$displayInfoFlow$$inlined$flatMapLatest$1
                internetPreferenceRepository$displayInfoFlow$$inlined$flatMapLatest$1 =
                        new InternetPreferenceRepository$displayInfoFlow$$inlined$flatMapLatest$1(
                                this.this$0, (Continuation) obj3);
        internetPreferenceRepository$displayInfoFlow$$inlined$flatMapLatest$1.L$0 =
                (FlowCollector) obj;
        internetPreferenceRepository$displayInfoFlow$$inlined$flatMapLatest$1.L$1 = obj2;
        return internetPreferenceRepository$displayInfoFlow$$inlined$flatMapLatest$1.invokeSuspend(
                Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00c1 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            r2 = 1
            if (r1 == 0) goto L16
            if (r1 != r2) goto Le
            kotlin.ResultKt.throwOnFailure(r10)
            goto Lc2
        Le:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L16:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            kotlinx.coroutines.flow.FlowCollector r10 = (kotlinx.coroutines.flow.FlowCollector) r10
            java.lang.Object r1 = r9.L$1
            android.net.NetworkCapabilities r1 = (android.net.NetworkCapabilities) r1
            com.android.settings.network.InternetPreferenceRepository r3 = r9.this$0
            r3.getClass()
            r4 = 12
            boolean r4 = r1.hasCapability(r4)
            if (r4 == 0) goto La7
            r4 = 16
            boolean r4 = r1.hasCapability(r4)
            if (r4 == 0) goto La7
            android.net.TransportInfo r4 = r1.getTransportInfo()
            boolean r5 = r4 instanceof android.net.wifi.WifiInfo
            com.android.settings.network.telephony.DataSubscriptionRepository r6 = r3.dataSubscriptionRepository
            if (r5 == 0) goto L5a
            android.net.wifi.WifiInfo r4 = (android.net.wifi.WifiInfo) r4
            boolean r4 = r4.isCarrierMerged()
            if (r4 == 0) goto L5a
            java.lang.String r1 = "InternetPreferenceRepo"
            java.lang.String r3 = "Detect a merged carrier Wi-Fi connected."
            android.util.Log.i(r1, r3)
            kotlinx.coroutines.flow.Flow r1 = r6.dataSummaryFlow()
            com.android.settings.network.InternetPreferenceRepository$wifiDisplayInfoFlow$$inlined$map$1 r3 = new com.android.settings.network.InternetPreferenceRepository$wifiDisplayInfoFlow$$inlined$map$1
            r4 = 1
            r3.<init>()
            goto Lb9
        L5a:
            int[] r1 = r1.getTransportTypes()
            java.lang.String r4 = "getTransportTypes(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r4)
            int r4 = r1.length
            r5 = 0
        L65:
            if (r5 >= r4) goto La7
            r7 = r1[r5]
            if (r7 == 0) goto L9c
            if (r7 == r2) goto L8f
            r8 = 3
            if (r7 == r8) goto L73
            int r5 = r5 + 1
            goto L65
        L73:
            com.android.settings.network.InternetPreferenceRepository$DisplayInfo r1 = new com.android.settings.network.InternetPreferenceRepository$DisplayInfo
            android.content.Context r3 = r3.context
            r4 = 2132029166(0x7f142eee, float:1.9696942E38)
            java.lang.String r3 = r3.getString(r4)
            java.lang.String r4 = "getString(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r4)
            r4 = 2131232223(0x7f0805df, float:1.808055E38)
            r1.<init>(r3, r4)
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r3 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r3.<init>(r1)
            goto Lb9
        L8f:
            com.android.settings.wifi.WifiSummaryRepository r1 = r3.wifiSummaryRepository
            kotlinx.coroutines.flow.Flow r1 = r1.summaryFlow()
            com.android.settings.network.InternetPreferenceRepository$wifiDisplayInfoFlow$$inlined$map$1 r3 = new com.android.settings.network.InternetPreferenceRepository$wifiDisplayInfoFlow$$inlined$map$1
            r4 = 0
            r3.<init>()
            goto Lb9
        L9c:
            kotlinx.coroutines.flow.Flow r1 = r6.dataSummaryFlow()
            com.android.settings.network.InternetPreferenceRepository$wifiDisplayInfoFlow$$inlined$map$1 r3 = new com.android.settings.network.InternetPreferenceRepository$wifiDisplayInfoFlow$$inlined$map$1
            r4 = 1
            r3.<init>()
            goto Lb9
        La7:
            com.android.settings.wifi.repository.WifiRepository r1 = r3.wifiRepository
            kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 r1 = r1.wifiStateFlow()
            com.android.settings.network.InternetPreferenceRepository$defaultDisplayInfoFlow$1 r4 = new com.android.settings.network.InternetPreferenceRepository$defaultDisplayInfoFlow$1
            r5 = 0
            r4.<init>(r3, r5)
            kotlinx.coroutines.flow.Flow r3 = r3.airplaneModeOnFlow
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 r3 = kotlinx.coroutines.flow.FlowKt.combine(r3, r1, r4)
        Lb9:
            r9.label = r2
            java.lang.Object r9 = kotlinx.coroutines.flow.FlowKt.emitAll(r9, r3, r10)
            if (r9 != r0) goto Lc2
            return r0
        Lc2:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.InternetPreferenceRepository$displayInfoFlow$$inlined$flatMapLatest$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
