package com.android.settings.network.telephony.wificalling;

import android.app.Application;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelKt;

import com.android.settings.R;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.network.telephony.SubscriptionRepositoryKt;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.internal.ContextScope;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CrossSimCallingViewModel extends AndroidViewModel {
    public final Application application;
    public final CarrierConfigManager carrierConfigManager;
    public final SettingsMetricsFeatureProvider metricsFeatureProvider;
    public final ContextScope scope;
    public final SubscriptionManager subscriptionManager;
    public final BufferedChannel updateChannel;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\u0018\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010 \n"
                    + "\u0002\u0010\b\n"
                    + "\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u00012\u0018\u0010\u0002\u001a\u0014\u0012\n"
                    + "\u0012\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0004\u0012\u00020\u00060\u0003H\u008a@"
            },
            d2 = {
                "<anonymous>",
                ApnSettings.MVNO_NONE,
                "<name for destructuring parameter 0>",
                "Lkotlin/Pair;",
                ApnSettings.MVNO_NONE,
                ApnSettings.MVNO_NONE,
                ApnSettings.MVNO_NONE
            },
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel$2, reason: invalid class name */
    public static final class AnonymousClass2 extends SuspendLambda implements Function2 {
        /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 =
                    CrossSimCallingViewModel.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((Pair) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Pair pair = (Pair) this.L$0;
                List list = (List) pair.getFirst();
                boolean booleanValue = ((Boolean) pair.getSecond()).booleanValue();
                CrossSimCallingViewModel crossSimCallingViewModel = CrossSimCallingViewModel.this;
                this.label = 1;
                if (CrossSimCallingViewModel.access$updateCrossSimCalling(
                                crossSimCallingViewModel, list, booleanValue, this)
                        == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CrossSimCallingViewModel(Application application) {
        super(application);
        Intrinsics.checkNotNullParameter(application, "application");
        this.application = application;
        this.subscriptionManager = SubscriptionRepositoryKt.requireSubscriptionManager(application);
        Object systemService = application.getSystemService(CarrierConfigManager.class);
        Intrinsics.checkNotNull(systemService);
        this.carrierConfigManager = (CarrierConfigManager) systemService;
        ContextScope plus =
                CoroutineScopeKt.plus(ViewModelKt.getViewModelScope(this), Dispatchers.Default);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.metricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.updateChannel = ChannelKt.Channel$default(0, null, null, 7);
        if (application
                .getResources()
                .getBoolean(R.bool.config_auto_data_switch_enables_cross_sim_calling)) {
            FlowKt.launchIn(
                    FlowKt.onEach(
                            FlowKt.distinctUntilChanged(
                                    FlowKt.transformLatest(
                                            SubscriptionRepositoryKt.subscriptionsChangedFlow(
                                                    application),
                                            new CrossSimCallingViewModel$special$$inlined$flatMapLatest$1(
                                                    this, null))),
                            new AnonymousClass2(null)),
                    plus);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0125 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x0099 -> B:26:0x0057). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$updateCrossSimCalling(
            com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel r9,
            java.util.List r10,
            boolean r11,
            kotlin.coroutines.Continuation r12) {
        /*
            Method dump skipped, instructions count: 295
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel.access$updateCrossSimCalling(com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel,"
                    + " java.util.List, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object crossSimAvailable(int r7, kotlin.coroutines.Continuation r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel$crossSimAvailable$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel$crossSimAvailable$1 r0 = (com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel$crossSimAvailable$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel$crossSimAvailable$1 r0 = new com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel$crossSimAvailable$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L35
            if (r2 != r3) goto L2d
            int r7 = r0.I$0
            java.lang.Object r6 = r0.L$0
            com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel r6 = (com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel) r6
            kotlin.ResultKt.throwOnFailure(r8)
            goto L54
        L2d:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L35:
            kotlin.ResultKt.throwOnFailure(r8)
            com.android.settings.network.telephony.wificalling.WifiCallingRepository r8 = new com.android.settings.network.telephony.wificalling.WifiCallingRepository
            android.app.Application r2 = r6.application
            r8.<init>(r2, r7)
            r0.L$0 = r6
            r0.I$0 = r7
            r0.label = r3
            kotlinx.coroutines.scheduling.DefaultScheduler r2 = kotlinx.coroutines.Dispatchers.Default
            com.android.settings.network.telephony.wificalling.WifiCallingRepository$isWifiCallingSupported$2 r4 = new com.android.settings.network.telephony.wificalling.WifiCallingRepository$isWifiCallingSupported$2
            r5 = 0
            r4.<init>(r8, r5)
            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r2, r4, r0)
            if (r8 != r1) goto L54
            return r1
        L54:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            r0 = 0
            if (r8 == 0) goto L70
            android.telephony.CarrierConfigManager r6 = r6.carrierConfigManager
            java.lang.String r8 = "carrier_cross_sim_ims_available_bool"
            java.util.List r1 = kotlin.collections.CollectionsKt__CollectionsJVMKt.listOf(r8)
            android.os.PersistableBundle r6 = com.android.settings.network.telephony.CarrierConfigManagerExtKt.safeGetConfig(r6, r1, r7)
            boolean r6 = r6.getBoolean(r8, r0)
            if (r6 == 0) goto L70
            goto L71
        L70:
            r3 = r0
        L71:
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r3)
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.telephony.wificalling.CrossSimCallingViewModel.crossSimAvailable(int,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }
}
