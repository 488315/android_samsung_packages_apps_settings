package com.android.settings.biometrics.fingerprint2.data.repository;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\u0010\u0004\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00010\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"
        },
        d2 = {
            "T",
            "R",
            "Lkotlinx/coroutines/flow/FlowCollector;",
            ApnSettings.MVNO_NONE,
            "<anonymous>",
            "(Lkotlinx/coroutines/flow/FlowCollector;)V"
        },
        k = 3,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintSensorRepositoryImpl$special$$inlined$transform$1
        extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_transform;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.biometrics.fingerprint2.data.repository.FingerprintSensorRepositoryImpl$special$$inlined$transform$1$1, reason: invalid class name */
    public final class AnonymousClass1 implements FlowCollector {
        public final /* synthetic */ FlowCollector $$this$flow;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        @Metadata(
                k = 3,
                mv = {1, 9, 0},
                xi = 48)
        /* renamed from: com.android.settings.biometrics.fingerprint2.data.repository.FingerprintSensorRepositoryImpl$special$$inlined$transform$1$1$1, reason: invalid class name and collision with other inner class name */
        public final class C00211 extends ContinuationImpl {
            int label;
            /* synthetic */ Object result;

            public C00211(Continuation continuation) {
                super(continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return AnonymousClass1.this.emit(null, this);
            }
        }

        public AnonymousClass1(FlowCollector flowCollector) {
            this.$$this$flow = flowCollector;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
        @Override // kotlinx.coroutines.flow.FlowCollector
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object emit(
                java.lang.Object r12, kotlin.coroutines.Continuation r13) {
            /*
                r11 = this;
                boolean r0 = r13 instanceof com.android.settings.biometrics.fingerprint2.data.repository.FingerprintSensorRepositoryImpl$special$$inlined$transform$1.AnonymousClass1.C00211
                if (r0 == 0) goto L13
                r0 = r13
                com.android.settings.biometrics.fingerprint2.data.repository.FingerprintSensorRepositoryImpl$special$$inlined$transform$1$1$1 r0 = (com.android.settings.biometrics.fingerprint2.data.repository.FingerprintSensorRepositoryImpl$special$$inlined$transform$1.AnonymousClass1.C00211) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.settings.biometrics.fingerprint2.data.repository.FingerprintSensorRepositoryImpl$special$$inlined$transform$1$1$1 r0 = new com.android.settings.biometrics.fingerprint2.data.repository.FingerprintSensorRepositoryImpl$special$$inlined$transform$1$1$1
                r0.<init>(r13)
            L18:
                java.lang.Object r13 = r0.result
                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L30
                if (r2 != r3) goto L28
                kotlin.ResultKt.throwOnFailure(r13)
                goto Lb1
            L28:
                java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                r11.<init>(r12)
                throw r11
            L30:
                kotlin.ResultKt.throwOnFailure(r13)
                android.hardware.fingerprint.FingerprintSensorPropertiesInternal r12 = (android.hardware.fingerprint.FingerprintSensorPropertiesInternal) r12
                java.lang.String r13 = "<this>"
                kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r12, r13)
                int r13 = r12.sensorStrength
                r2 = 2
                if (r13 == 0) goto L56
                if (r13 == r3) goto L53
                if (r13 != r2) goto L47
                com.android.systemui.biometrics.shared.model.SensorStrength r13 = com.android.systemui.biometrics.shared.model.SensorStrength.STRONG
            L45:
                r6 = r13
                goto L59
            L47:
                java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
                java.lang.String r12 = "Invalid SensorStrength value: "
                java.lang.String r12 = androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0.m(r13, r12)
                r11.<init>(r12)
                throw r11
            L53:
                com.android.systemui.biometrics.shared.model.SensorStrength r13 = com.android.systemui.biometrics.shared.model.SensorStrength.WEAK
                goto L45
            L56:
                com.android.systemui.biometrics.shared.model.SensorStrength r13 = com.android.systemui.biometrics.shared.model.SensorStrength.CONVENIENCE
                goto L45
            L59:
                int r13 = r12.sensorType
                if (r13 == 0) goto L86
                if (r13 == r3) goto L83
                if (r13 == r2) goto L80
                r2 = 3
                if (r13 == r2) goto L7d
                r2 = 4
                if (r13 == r2) goto L7a
                r2 = 5
                if (r13 != r2) goto L6e
                com.android.systemui.biometrics.shared.model.FingerprintSensorType r13 = com.android.systemui.biometrics.shared.model.FingerprintSensorType.HOME_BUTTON
            L6c:
                r8 = r13
                goto L89
            L6e:
                java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
                java.lang.String r12 = "Invalid SensorType value: "
                java.lang.String r12 = androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0.m(r13, r12)
                r11.<init>(r12)
                throw r11
            L7a:
                com.android.systemui.biometrics.shared.model.FingerprintSensorType r13 = com.android.systemui.biometrics.shared.model.FingerprintSensorType.POWER_BUTTON
                goto L6c
            L7d:
                com.android.systemui.biometrics.shared.model.FingerprintSensorType r13 = com.android.systemui.biometrics.shared.model.FingerprintSensorType.UDFPS_OPTICAL
                goto L6c
            L80:
                com.android.systemui.biometrics.shared.model.FingerprintSensorType r13 = com.android.systemui.biometrics.shared.model.FingerprintSensorType.UDFPS_ULTRASONIC
                goto L6c
            L83:
                com.android.systemui.biometrics.shared.model.FingerprintSensorType r13 = com.android.systemui.biometrics.shared.model.FingerprintSensorType.REAR
                goto L6c
            L86:
                com.android.systemui.biometrics.shared.model.FingerprintSensorType r13 = com.android.systemui.biometrics.shared.model.FingerprintSensorType.UNKNOWN
                goto L6c
            L89:
                android.hardware.biometrics.SensorLocationInternal r13 = r12.getLocation()
                android.graphics.Rect r9 = r13.getRect()
                java.lang.String r13 = "getRect(...)"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, r13)
                android.hardware.biometrics.SensorLocationInternal r13 = r12.getLocation()
                int r10 = r13.sensorRadius
                com.android.systemui.biometrics.shared.model.FingerprintSensor r13 = new com.android.systemui.biometrics.shared.model.FingerprintSensor
                int r5 = r12.sensorId
                int r7 = r12.maxEnrollmentsPerUser
                r4 = r13
                r4.<init>(r5, r6, r7, r8, r9, r10)
                r0.label = r3
                kotlinx.coroutines.flow.FlowCollector r11 = r11.$$this$flow
                java.lang.Object r11 = r11.emit(r13, r0)
                if (r11 != r1) goto Lb1
                return r1
            Lb1:
                kotlin.Unit r11 = kotlin.Unit.INSTANCE
                return r11
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.biometrics.fingerprint2.data.repository.FingerprintSensorRepositoryImpl$special$$inlined$transform$1.AnonymousClass1.emit(java.lang.Object,"
                        + " kotlin.coroutines.Continuation):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintSensorRepositoryImpl$special$$inlined$transform$1(
            Flow flow, Continuation continuation) {
        super(2, continuation);
        this.$this_transform = flow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FingerprintSensorRepositoryImpl$special$$inlined$transform$1
                fingerprintSensorRepositoryImpl$special$$inlined$transform$1 =
                        new FingerprintSensorRepositoryImpl$special$$inlined$transform$1(
                                this.$this_transform, continuation);
        fingerprintSensorRepositoryImpl$special$$inlined$transform$1.L$0 = obj;
        return fingerprintSensorRepositoryImpl$special$$inlined$transform$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintSensorRepositoryImpl$special$$inlined$transform$1)
                        create((FlowCollector) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flow = this.$this_transform;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(flowCollector);
            this.label = 1;
            if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
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
