package com.android.settings.wifi;

import android.content.Context;

import com.android.settings.wifi.repository.SharedConnectivityRepository;
import com.android.settings.wifi.repository.WifiPickerRepository;
import com.android.settings.wifi.repository.WifiStatusRepository;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiSummaryRepository {
    public final Context context;
    public final WifiPickerRepository wifiPickerRepository;
    public final WifiStatusRepository wifiStatusRepository;

    public WifiSummaryRepository(Context context) {
        WifiStatusRepository wifiStatusRepository = new WifiStatusRepository(context);
        WifiPickerRepository wifiPickerRepository =
                SharedConnectivityRepository.isDeviceConfigEnabled()
                        ? new WifiPickerRepository(context)
                        : null;
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.wifiStatusRepository = wifiStatusRepository;
        this.wifiPickerRepository = wifiPickerRepository;
    }

    public final Flow summaryFlow() {
        WifiStatusRepository wifiStatusRepository = this.wifiStatusRepository;
        WifiPickerRepository wifiPickerRepository = this.wifiPickerRepository;
        if (wifiPickerRepository == null) {
            final Flow wifiStatusTrackerFlow = wifiStatusRepository.wifiStatusTrackerFlow();
            return FlowKt.flowOn(
                    FlowKt.buffer$default(
                            new Flow() { // from class:
                                // com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1

                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                /* renamed from: com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1$2, reason: invalid class name */
                                public final class AnonymousClass2 implements FlowCollector {
                                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                    public final /* synthetic */ WifiSummaryRepository this$0;

                                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                    @Metadata(
                                            k = 3,
                                            mv = {1, 9, 0},
                                            xi = 48)
                                    /* renamed from: com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1$2$1, reason: invalid class name */
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
                                            WifiSummaryRepository wifiSummaryRepository) {
                                        this.$this_unsafeFlow = flowCollector;
                                        this.this$0 = wifiSummaryRepository;
                                    }

                                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                    @Override // kotlinx.coroutines.flow.FlowCollector
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                                    */
                                    public final java.lang.Object emit(
                                            java.lang.Object r7,
                                            kotlin.coroutines.Continuation r8) {
                                        /*
                                            r6 = this;
                                            boolean r0 = r8 instanceof com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                            if (r0 == 0) goto L13
                                            r0 = r8
                                            com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1$2$1 r0 = (com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                            int r1 = r0.label
                                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                            r3 = r1 & r2
                                            if (r3 == 0) goto L13
                                            int r1 = r1 - r2
                                            r0.label = r1
                                            goto L18
                                        L13:
                                            com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1$2$1 r0 = new com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1$2$1
                                            r0.<init>(r8)
                                        L18:
                                            java.lang.Object r8 = r0.result
                                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                            int r2 = r0.label
                                            r3 = 1
                                            if (r2 == 0) goto L2f
                                            if (r2 != r3) goto L27
                                            kotlin.ResultKt.throwOnFailure(r8)
                                            goto L91
                                        L27:
                                            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                            r6.<init>(r7)
                                            throw r6
                                        L2f:
                                            kotlin.ResultKt.throwOnFailure(r8)
                                            com.android.settingslib.wifi.WifiStatusTracker r7 = (com.android.settingslib.wifi.WifiStatusTracker) r7
                                            com.android.settings.wifi.WifiSummaryRepository r8 = r6.this$0
                                            r8.getClass()
                                            boolean r2 = r7.enabled
                                            java.lang.String r4 = "getString(...)"
                                            if (r2 != 0) goto L4c
                                            android.content.Context r7 = r8.context
                                            r8 = 2132028881(0x7f142dd1, float:1.9696364E38)
                                            java.lang.String r7 = r7.getString(r8)
                                            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r7, r4)
                                            goto L86
                                        L4c:
                                            boolean r2 = r7.connected
                                            if (r2 != 0) goto L5d
                                            android.content.Context r7 = r8.context
                                            r8 = 2132020499(0x7f140d13, float:1.9679363E38)
                                            java.lang.String r7 = r7.getString(r8)
                                            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r7, r4)
                                            goto L86
                                        L5d:
                                            java.lang.String r2 = r7.ssid
                                            java.lang.String r2 = android.net.wifi.WifiInfo.sanitizeSsid(r2)
                                            if (r2 != 0) goto L67
                                            java.lang.String r2 = ""
                                        L67:
                                            java.lang.String r5 = r7.statusLabel
                                            if (r5 == 0) goto L85
                                            int r5 = r5.length()
                                            if (r5 != 0) goto L72
                                            goto L85
                                        L72:
                                            android.content.Context r8 = r8.context
                                            java.lang.String r7 = r7.statusLabel
                                            java.lang.Object[] r7 = new java.lang.Object[]{r2, r7}
                                            r2 = 2132024441(0x7f141c79, float:1.9687358E38)
                                            java.lang.String r7 = r8.getString(r2, r7)
                                            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r7, r4)
                                            goto L86
                                        L85:
                                            r7 = r2
                                        L86:
                                            r0.label = r3
                                            kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                                            java.lang.Object r6 = r6.emit(r7, r0)
                                            if (r6 != r1) goto L91
                                            return r1
                                        L91:
                                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                            return r6
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                    + " kotlin.coroutines.Continuation):java.lang.Object");
                                    }
                                }

                                @Override // kotlinx.coroutines.flow.Flow
                                public final Object collect(
                                        FlowCollector flowCollector, Continuation continuation) {
                                    Object collect =
                                            Flow.this.collect(
                                                    new AnonymousClass2(flowCollector, this),
                                                    continuation);
                                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                            ? collect
                                            : Unit.INSTANCE;
                                }
                            },
                            -1),
                    Dispatchers.Default);
        }
        final Flow wifiStatusTrackerFlow2 = wifiStatusRepository.wifiStatusTrackerFlow();
        return FlowKt.combine(
                FlowKt.flowOn(
                        FlowKt.buffer$default(
                                new Flow() { // from class:
                                    // com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1

                                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                    /* renamed from: com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1$2, reason: invalid class name */
                                    public final class AnonymousClass2 implements FlowCollector {
                                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                        public final /* synthetic */ WifiSummaryRepository this$0;

                                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                        @Metadata(
                                                k = 3,
                                                mv = {1, 9, 0},
                                                xi = 48)
                                        /* renamed from: com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1$2$1, reason: invalid class name */
                                        public final class AnonymousClass1
                                                extends ContinuationImpl {
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
                                                WifiSummaryRepository wifiSummaryRepository) {
                                            this.$this_unsafeFlow = flowCollector;
                                            this.this$0 = wifiSummaryRepository;
                                        }

                                        @Override // kotlinx.coroutines.flow.FlowCollector
                                        public final Object emit(
                                                Object obj, Continuation continuation) {
                                            /*
                                                this = this;
                                                boolean r0 = r8 instanceof com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                                if (r0 == 0) goto L13
                                                r0 = r8
                                                com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1$2$1 r0 = (com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                                int r1 = r0.label
                                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                                r3 = r1 & r2
                                                if (r3 == 0) goto L13
                                                int r1 = r1 - r2
                                                r0.label = r1
                                                goto L18
                                            L13:
                                                com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1$2$1 r0 = new com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1$2$1
                                                r0.<init>(r8)
                                            L18:
                                                java.lang.Object r8 = r0.result
                                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                                int r2 = r0.label
                                                r3 = 1
                                                if (r2 == 0) goto L2f
                                                if (r2 != r3) goto L27
                                                kotlin.ResultKt.throwOnFailure(r8)
                                                goto L91
                                            L27:
                                                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                                java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                                r6.<init>(r7)
                                                throw r6
                                            L2f:
                                                kotlin.ResultKt.throwOnFailure(r8)
                                                com.android.settingslib.wifi.WifiStatusTracker r7 = (com.android.settingslib.wifi.WifiStatusTracker) r7
                                                com.android.settings.wifi.WifiSummaryRepository r8 = r6.this$0
                                                r8.getClass()
                                                boolean r2 = r7.enabled
                                                java.lang.String r4 = "getString(...)"
                                                if (r2 != 0) goto L4c
                                                android.content.Context r7 = r8.context
                                                r8 = 2132028881(0x7f142dd1, float:1.9696364E38)
                                                java.lang.String r7 = r7.getString(r8)
                                                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r7, r4)
                                                goto L86
                                            L4c:
                                                boolean r2 = r7.connected
                                                if (r2 != 0) goto L5d
                                                android.content.Context r7 = r8.context
                                                r8 = 2132020499(0x7f140d13, float:1.9679363E38)
                                                java.lang.String r7 = r7.getString(r8)
                                                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r7, r4)
                                                goto L86
                                            L5d:
                                                java.lang.String r2 = r7.ssid
                                                java.lang.String r2 = android.net.wifi.WifiInfo.sanitizeSsid(r2)
                                                if (r2 != 0) goto L67
                                                java.lang.String r2 = ""
                                            L67:
                                                java.lang.String r5 = r7.statusLabel
                                                if (r5 == 0) goto L85
                                                int r5 = r5.length()
                                                if (r5 != 0) goto L72
                                                goto L85
                                            L72:
                                                android.content.Context r8 = r8.context
                                                java.lang.String r7 = r7.statusLabel
                                                java.lang.Object[] r7 = new java.lang.Object[]{r2, r7}
                                                r2 = 2132024441(0x7f141c79, float:1.9687358E38)
                                                java.lang.String r7 = r8.getString(r2, r7)
                                                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r7, r4)
                                                goto L86
                                            L85:
                                                r7 = r2
                                            L86:
                                                r0.label = r3
                                                kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                                                java.lang.Object r6 = r6.emit(r7, r0)
                                                if (r6 != r1) goto L91
                                                return r1
                                            L91:
                                                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                                return r6
                                            */
                                            throw new UnsupportedOperationException(
                                                    "Method not decompiled:"
                                                        + " com.android.settings.wifi.WifiSummaryRepository$wifiStatusSummaryFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                                        }
                                    }

                                    @Override // kotlinx.coroutines.flow.Flow
                                    public final Object collect(
                                            FlowCollector flowCollector,
                                            Continuation continuation) {
                                        Object collect =
                                                Flow.this.collect(
                                                        new AnonymousClass2(flowCollector, this),
                                                        continuation);
                                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                                ? collect
                                                : Unit.INSTANCE;
                                    }
                                },
                                -1),
                        Dispatchers.Default),
                wifiPickerRepository.connectedWifiEntryFlow(),
                new WifiSummaryRepository$summaryFlow$1(3, null));
    }
}
