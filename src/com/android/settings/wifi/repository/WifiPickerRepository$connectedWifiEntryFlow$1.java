package com.android.settings.wifi.repository;

import android.os.HandlerThread;
import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import com.android.wifitrackerlib.WifiPickerTracker;
import com.samsung.android.knox.net.apn.ApnSettings;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/channels/ProducerScope;", "Lcom/android/wifitrackerlib/WifiEntry;"}, k = 3, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes2.dex */
final class WifiPickerRepository$connectedWifiEntryFlow$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ WifiPickerRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiPickerRepository$connectedWifiEntryFlow$1(WifiPickerRepository wifiPickerRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = wifiPickerRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiPickerRepository$connectedWifiEntryFlow$1 wifiPickerRepository$connectedWifiEntryFlow$1 = new WifiPickerRepository$connectedWifiEntryFlow$1(this.this$0, continuation);
        wifiPickerRepository$connectedWifiEntryFlow$1.L$0 = obj;
        return wifiPickerRepository$connectedWifiEntryFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiPickerRepository$connectedWifiEntryFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r4v3, types: [T, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final HandlerThread handlerThread = new HandlerThread(ComposerKt$$ExternalSyntheticOutline0.m("WifiPickerRepository{", Integer.toHexString(System.identityHashCode(producerScope)), "}"), 10);
            handlerThread.start();
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            ?? invoke = this.this$0.createWifiPickerTracker.invoke(handlerThread, new WifiPickerTracker.WifiPickerTrackerCallback() { // from class: com.android.settings.wifi.repository.WifiPickerRepository$connectedWifiEntryFlow$1$callback$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
                public final void onWifiEntriesChanged() {
                    WifiPickerTracker wifiPickerTracker = (WifiPickerTracker) ref$ObjectRef.element;
                    ((ProducerCoroutine) producerScope).mo1469trySendJP2dKIU(wifiPickerTracker != null ? wifiPickerTracker.mConnectedWifiEntry : null);
                }

                @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
                public final void onNumSavedNetworksChanged() {
                }

                @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
                public final void onNumSavedSubscriptionsChanged() {
                }

                @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
                public final void onWifiStateChanged() {
                }
            });
            ref$ObjectRef.element = invoke;
            ((WifiPickerTracker) invoke).onStart();
            Function0 function0 = new Function0() { // from class: com.android.settings.wifi.repository.WifiPickerRepository$connectedWifiEntryFlow$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* renamed from: invoke */
                public final Object mo1068invoke() {
                    Ref$ObjectRef.this.element.onStop();
                    Ref$ObjectRef.this.element.unregisterReceiverAndCallback();
                    handlerThread.quit();
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
