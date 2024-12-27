package com.android.settings.deviceinfo.simstatus;

import android.telephony.CellSignalStrength;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyCallback;
import android.util.Log;

import com.android.settings.network.telephony.TelephonyRepositoryKt;
import com.android.settingslib.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.configuration.DATA;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

import java.util.List;

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
public final class SignalStrengthRepository$signalStrengthDisplayFlow$$inlined$flatMapLatest$1
        extends SuspendLambda implements Function3 {
    final /* synthetic */ int $subId$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ SignalStrengthRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SignalStrengthRepository$signalStrengthDisplayFlow$$inlined$flatMapLatest$1(
            Continuation continuation, SignalStrengthRepository signalStrengthRepository, int i) {
        super(3, continuation);
        this.this$0 = signalStrengthRepository;
        this.$subId$inlined = i;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SignalStrengthRepository$signalStrengthDisplayFlow$$inlined$flatMapLatest$1
                signalStrengthRepository$signalStrengthDisplayFlow$$inlined$flatMapLatest$1 =
                        new SignalStrengthRepository$signalStrengthDisplayFlow$$inlined$flatMapLatest$1(
                                (Continuation) obj3, this.this$0, this.$subId$inlined);
        signalStrengthRepository$signalStrengthDisplayFlow$$inlined$flatMapLatest$1.L$0 =
                (FlowCollector) obj;
        signalStrengthRepository$signalStrengthDisplayFlow$$inlined$flatMapLatest$1.L$1 = obj2;
        return signalStrengthRepository$signalStrengthDisplayFlow$$inlined$flatMapLatest$1
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (Utils.isInService((ServiceState) this.L$1)) {
                SignalStrengthRepository signalStrengthRepository = this.this$0;
                final int i2 = this.$subId$inlined;
                final Flow telephonyCallbackFlow =
                        TelephonyRepositoryKt.telephonyCallbackFlow(
                                signalStrengthRepository.context,
                                i2,
                                new Function1() { // from class:
                                                  // com.android.settings.deviceinfo.simstatus.SignalStrengthRepository$signalStrengthFlow$1

                                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                    /* renamed from: com.android.settings.deviceinfo.simstatus.SignalStrengthRepository$signalStrengthFlow$1$1, reason: invalid class name */
                                    public final class AnonymousClass1 extends TelephonyCallback
                                            implements TelephonyCallback.SignalStrengthsListener {
                                        public final /* synthetic */ int $subId;
                                        public final /* synthetic */ ProducerScope
                                                $this_telephonyCallbackFlow;

                                        public AnonymousClass1(ProducerScope producerScope, int i) {
                                            this.$this_telephonyCallbackFlow = producerScope;
                                            this.$subId = i;
                                        }

                                        @Override // android.telephony.TelephonyCallback.SignalStrengthsListener
                                        public final void onSignalStrengthsChanged(
                                                SignalStrength signalStrength) {
                                            Intrinsics.checkNotNullParameter(
                                                    signalStrength, "signalStrength");
                                            ((ProducerCoroutine) this.$this_telephonyCallbackFlow)
                                                    .mo1469trySendJP2dKIU(signalStrength);
                                            List<CellSignalStrength> cellSignalStrengths =
                                                    signalStrength.getCellSignalStrengths();
                                            Intrinsics.checkNotNullExpressionValue(
                                                    cellSignalStrengths,
                                                    "getCellSignalStrengths(...)");
                                            Log.d(
                                                    "SignalStrengthRepo",
                                                    "["
                                                            + this.$subId
                                                            + "] onSignalStrengthsChanged: "
                                                            + cellSignalStrengths);
                                        }
                                    }

                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj2) {
                                        ProducerScope telephonyCallbackFlow2 = (ProducerScope) obj2;
                                        Intrinsics.checkNotNullParameter(
                                                telephonyCallbackFlow2,
                                                "$this$telephonyCallbackFlow");
                                        return new AnonymousClass1(telephonyCallbackFlow2, i2);
                                    }
                                });
                final SignalStrengthRepository signalStrengthRepository2 = this.this$0;
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 =
                        new Flow() { // from class:
                            // com.android.settings.deviceinfo.simstatus.SignalStrengthRepository$signalStrengthDisplayFlow$lambda$1$$inlined$map$1

                            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                            /* renamed from: com.android.settings.deviceinfo.simstatus.SignalStrengthRepository$signalStrengthDisplayFlow$lambda$1$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ SignalStrengthRepository this$0;

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                @Metadata(
                                        k = 3,
                                        mv = {1, 9, 0},
                                        xi = 48)
                                /* renamed from: com.android.settings.deviceinfo.simstatus.SignalStrengthRepository$signalStrengthDisplayFlow$lambda$1$$inlined$map$1$2$1, reason: invalid class name */
                                public final class AnonymousClass1 extends ContinuationImpl {
                                    Object L$0;
                                    int label;
                                    /* synthetic */ Object result;

                                    public AnonymousClass1(Continuation continuation) {
                                        super(continuation);
                                    }

                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                    public final Object invokeSuspend(Object obj) {
                                        this.result = obj;
                                        this.label |= Integer.MIN_VALUE;
                                        return AnonymousClass2.this.emit(null, this);
                                    }
                                }

                                public AnonymousClass2(
                                        FlowCollector flowCollector,
                                        SignalStrengthRepository signalStrengthRepository) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.this$0 = signalStrengthRepository;
                                }

                                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(
                                        java.lang.Object r10, kotlin.coroutines.Continuation r11) {
                                    /*
                                        r9 = this;
                                        boolean r0 = r11 instanceof com.android.settings.deviceinfo.simstatus.SignalStrengthRepository$signalStrengthDisplayFlow$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r11
                                        com.android.settings.deviceinfo.simstatus.SignalStrengthRepository$signalStrengthDisplayFlow$lambda$1$$inlined$map$1$2$1 r0 = (com.android.settings.deviceinfo.simstatus.SignalStrengthRepository$signalStrengthDisplayFlow$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.settings.deviceinfo.simstatus.SignalStrengthRepository$signalStrengthDisplayFlow$lambda$1$$inlined$map$1$2$1 r0 = new com.android.settings.deviceinfo.simstatus.SignalStrengthRepository$signalStrengthDisplayFlow$lambda$1$$inlined$map$1$2$1
                                        r0.<init>(r11)
                                    L18:
                                        java.lang.Object r11 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L30
                                        if (r2 != r3) goto L28
                                        kotlin.ResultKt.throwOnFailure(r11)
                                        goto Lb6
                                    L28:
                                        java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                                        java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
                                        r9.<init>(r10)
                                        throw r9
                                    L30:
                                        kotlin.ResultKt.throwOnFailure(r11)
                                        android.telephony.SignalStrength r10 = (android.telephony.SignalStrength) r10
                                        com.android.settings.deviceinfo.simstatus.SignalStrengthRepository r11 = r9.this$0
                                        android.content.Context r11 = r11.context
                                        java.util.List r2 = r10.getCellSignalStrengths()
                                        java.lang.String r4 = "getCellSignalStrengths(...)"
                                        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r4)
                                        java.lang.Iterable r2 = (java.lang.Iterable) r2
                                        java.util.Iterator r2 = r2.iterator()
                                    L48:
                                        boolean r5 = r2.hasNext()
                                        r6 = 0
                                        r7 = -1
                                        if (r5 == 0) goto L5e
                                        java.lang.Object r5 = r2.next()
                                        r8 = r5
                                        android.telephony.CellSignalStrength r8 = (android.telephony.CellSignalStrength) r8
                                        int r8 = r8.getDbm()
                                        if (r8 == r7) goto L48
                                        goto L5f
                                    L5e:
                                        r5 = r6
                                    L5f:
                                        android.telephony.CellSignalStrength r5 = (android.telephony.CellSignalStrength) r5
                                        r2 = 0
                                        if (r5 == 0) goto L69
                                        int r5 = r5.getDbm()
                                        goto L6a
                                    L69:
                                        r5 = r2
                                    L6a:
                                        java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
                                        java.util.List r10 = r10.getCellSignalStrengths()
                                        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, r4)
                                        java.lang.Iterable r10 = (java.lang.Iterable) r10
                                        java.util.Iterator r10 = r10.iterator()
                                    L7b:
                                        boolean r4 = r10.hasNext()
                                        if (r4 == 0) goto L8f
                                        java.lang.Object r4 = r10.next()
                                        r8 = r4
                                        android.telephony.CellSignalStrength r8 = (android.telephony.CellSignalStrength) r8
                                        int r8 = r8.getAsuLevel()
                                        if (r8 == r7) goto L7b
                                        r6 = r4
                                    L8f:
                                        android.telephony.CellSignalStrength r6 = (android.telephony.CellSignalStrength) r6
                                        if (r6 == 0) goto L97
                                        int r2 = r6.getAsuLevel()
                                    L97:
                                        java.lang.Integer r10 = java.lang.Integer.valueOf(r2)
                                        java.lang.Object[] r10 = new java.lang.Object[]{r5, r10}
                                        r2 = 2132028443(0x7f142c1b, float:1.9695475E38)
                                        java.lang.String r10 = r11.getString(r2, r10)
                                        java.lang.String r11 = "getString(...)"
                                        kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, r11)
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r9 = r9.$this_unsafeFlow
                                        java.lang.Object r9 = r9.emit(r10, r0)
                                        if (r9 != r1) goto Lb6
                                        return r1
                                    Lb6:
                                        kotlin.Unit r9 = kotlin.Unit.INSTANCE
                                        return r9
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.settings.deviceinfo.simstatus.SignalStrengthRepository$signalStrengthDisplayFlow$lambda$1$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                + " kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(
                                    FlowCollector flowCollector2, Continuation continuation) {
                                Object collect =
                                        Flow.this.collect(
                                                new AnonymousClass2(
                                                        flowCollector2, signalStrengthRepository2),
                                                continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                        ? collect
                                        : Unit.INSTANCE;
                            }
                        };
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 =
                        new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(
                                DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector)
                    == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
