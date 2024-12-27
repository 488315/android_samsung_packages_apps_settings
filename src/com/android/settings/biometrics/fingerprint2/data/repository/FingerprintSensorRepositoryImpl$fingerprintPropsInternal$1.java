package com.android.settings.biometrics.fingerprint2.data.repository;

import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback;
import com.samsung.android.knox.net.apn.ApnSettings;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/channels/ProducerScope;", "Landroid/hardware/fingerprint/FingerprintSensorPropertiesInternal;"}, k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes2.dex */
final class FingerprintSensorRepositoryImpl$fingerprintPropsInternal$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ CoroutineDispatcher $backgroundDispatcher;
    final /* synthetic */ FingerprintManager $fingerprintManager;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    /* renamed from: com.android.settings.biometrics.fingerprint2.data.repository.FingerprintSensorRepositoryImpl$fingerprintPropsInternal$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FingerprintSensorRepositoryImpl$fingerprintPropsInternal$1$callback$1 $callback;
        final /* synthetic */ FingerprintManager $fingerprintManager;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(FingerprintManager fingerprintManager, FingerprintSensorRepositoryImpl$fingerprintPropsInternal$1$callback$1 fingerprintSensorRepositoryImpl$fingerprintPropsInternal$1$callback$1, Continuation continuation) {
            super(2, continuation);
            this.$fingerprintManager = fingerprintManager;
            this.$callback = fingerprintSensorRepositoryImpl$fingerprintPropsInternal$1$callback$1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$fingerprintManager, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            FingerprintManager fingerprintManager = this.$fingerprintManager;
            if (fingerprintManager == null) {
                return null;
            }
            fingerprintManager.addAuthenticatorsRegisteredCallback(this.$callback);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintSensorRepositoryImpl$fingerprintPropsInternal$1(CoroutineDispatcher coroutineDispatcher, FingerprintManager fingerprintManager, Continuation continuation) {
        super(2, continuation);
        this.$backgroundDispatcher = coroutineDispatcher;
        this.$fingerprintManager = fingerprintManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FingerprintSensorRepositoryImpl$fingerprintPropsInternal$1 fingerprintSensorRepositoryImpl$fingerprintPropsInternal$1 = new FingerprintSensorRepositoryImpl$fingerprintPropsInternal$1(this.$backgroundDispatcher, this.$fingerprintManager, continuation);
        fingerprintSensorRepositoryImpl$fingerprintPropsInternal$1.L$0 = obj;
        return fingerprintSensorRepositoryImpl$fingerprintPropsInternal$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintSensorRepositoryImpl$fingerprintPropsInternal$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.settings.biometrics.fingerprint2.data.repository.FingerprintSensorRepositoryImpl$fingerprintPropsInternal$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        final ProducerScope producerScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            producerScope = (ProducerScope) this.L$0;
            ?? r9 = new IFingerprintAuthenticatorsRegisteredCallback.Stub() { // from class: com.android.settings.biometrics.fingerprint2.data.repository.FingerprintSensorRepositoryImpl$fingerprintPropsInternal$1$callback$1
                public final void onAllAuthenticatorsRegistered(List sensors) {
                    Intrinsics.checkNotNullParameter(sensors, "sensors");
                    if (sensors.isEmpty()) {
                        ((ProducerCoroutine) ProducerScope.this).mo1469trySendJP2dKIU(FingerprintSensorRepositoryImpl.DEFAULT_PROPS);
                    } else {
                        ((ProducerCoroutine) ProducerScope.this).mo1469trySendJP2dKIU(sensors.get(0));
                    }
                }
            };
            CoroutineDispatcher coroutineDispatcher = this.$backgroundDispatcher;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$fingerprintManager, r9, null);
            this.L$0 = producerScope;
            this.label = 1;
            if (BuildersKt.withContext(coroutineDispatcher, anonymousClass1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            producerScope = (ProducerScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        AnonymousClass2 anonymousClass2 = new Function0() { // from class: com.android.settings.biometrics.fingerprint2.data.repository.FingerprintSensorRepositoryImpl$fingerprintPropsInternal$1.2
            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo1068invoke() {
                return Unit.INSTANCE;
            }
        };
        this.L$0 = null;
        this.label = 2;
        if (ProduceKt.awaitClose(producerScope, anonymousClass2, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
