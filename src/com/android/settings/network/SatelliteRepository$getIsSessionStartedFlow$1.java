package com.android.settings.network;

import android.telephony.satellite.SatelliteManager;
import android.telephony.satellite.SatelliteModemStateCallback;
import android.util.Log;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.apn.ApnSettings;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/channels/ProducerScope;", ApnSettings.MVNO_NONE}, k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class SatelliteRepository$getIsSessionStartedFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ CoroutineDispatcher $defaultDispatcher;
    final /* synthetic */ SatelliteManager $satelliteManager;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SatelliteRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SatelliteRepository$getIsSessionStartedFlow$1(SatelliteManager satelliteManager, CoroutineDispatcher coroutineDispatcher, SatelliteRepository satelliteRepository, Continuation continuation) {
        super(2, continuation);
        this.$satelliteManager = satelliteManager;
        this.$defaultDispatcher = coroutineDispatcher;
        this.this$0 = satelliteRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SatelliteRepository$getIsSessionStartedFlow$1 satelliteRepository$getIsSessionStartedFlow$1 = new SatelliteRepository$getIsSessionStartedFlow$1(this.$satelliteManager, this.$defaultDispatcher, this.this$0, continuation);
        satelliteRepository$getIsSessionStartedFlow$1.L$0 = obj;
        return satelliteRepository$getIsSessionStartedFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SatelliteRepository$getIsSessionStartedFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [android.telephony.satellite.SatelliteModemStateCallback, com.android.settings.network.SatelliteRepository$getIsSessionStartedFlow$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final SatelliteRepository satelliteRepository = this.this$0;
            final ?? r1 = new SatelliteModemStateCallback() { // from class: com.android.settings.network.SatelliteRepository$getIsSessionStartedFlow$1$callback$1
                public final void onSatelliteModemStateChanged(int i2) {
                    SatelliteRepository.this.getClass();
                    boolean z = (i2 == -1 || i2 == 4 || i2 == 5) ? false : true;
                    Log.i("SatelliteRepository", "Satellite modem state changed: state=" + i2 + ", isSessionStarted=" + z);
                    ((ProducerCoroutine) producerScope).mo1469trySendJP2dKIU(Boolean.valueOf(z));
                }
            };
            int registerForModemStateChanged = this.$satelliteManager.registerForModemStateChanged(ExecutorsKt.asExecutor(this.$defaultDispatcher), (SatelliteModemStateCallback) r1);
            if (registerForModemStateChanged != 0) {
                RecordingInputConnection$$ExternalSyntheticOutline0.m(registerForModemStateChanged, "Failed to register for satellite modem state change: ", "SatelliteRepository");
                ((ProducerCoroutine) producerScope).mo1469trySendJP2dKIU(Boolean.FALSE);
            }
            final SatelliteManager satelliteManager = this.$satelliteManager;
            Function0 function0 = new Function0() { // from class: com.android.settings.network.SatelliteRepository$getIsSessionStartedFlow$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* renamed from: invoke */
                public final Object mo1068invoke() {
                    satelliteManager.unregisterForModemStateChanged(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
