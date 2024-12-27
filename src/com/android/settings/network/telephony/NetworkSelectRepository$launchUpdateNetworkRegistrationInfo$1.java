package com.android.settings.network.telephony;

import android.telephony.ServiceState;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.scheduling.DefaultScheduler;

import java.util.List;

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
final class NetworkSelectRepository$launchUpdateNetworkRegistrationInfo$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ Function1 $action;
    final /* synthetic */ LifecycleOwner $lifecycleOwner;
    int label;
    final /* synthetic */ NetworkSelectRepository this$0;

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
    /* renamed from: com.android.settings.network.telephony.NetworkSelectRepository$launchUpdateNetworkRegistrationInfo$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function1 $action;
        int label;
        final /* synthetic */ NetworkSelectRepository this$0;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        @Metadata(
                d1 = {
                    "\u0000\n\n"
                        + "\u0000\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"
                },
                d2 = {
                    "<anonymous>",
                    "Lcom/android/settings/network/telephony/NetworkSelectRepository$NetworkRegistrationAndForbiddenInfo;",
                    "Lkotlinx/coroutines/CoroutineScope;"
                },
                k = 3,
                mv = {1, 9, 0},
                xi = 48)
        /* renamed from: com.android.settings.network.telephony.NetworkSelectRepository$launchUpdateNetworkRegistrationInfo$1$1$1, reason: invalid class name and collision with other inner class name */
        final class C00381 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ NetworkSelectRepository this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00381(
                    NetworkSelectRepository networkSelectRepository, Continuation continuation) {
                super(2, continuation);
                this.this$0 = networkSelectRepository;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00381(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C00381) create((CoroutineScope) obj, (Continuation) obj2))
                        .invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                ServiceState serviceState;
                List list;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                NetworkSelectRepository networkSelectRepository = this.this$0;
                NetworkSelectRepository.NetworkRegistrationAndForbiddenInfo
                        networkRegistrationAndForbiddenInfo = null;
                if (networkSelectRepository.telephonyManager.getDataState() == 2
                        && (serviceState =
                                        networkSelectRepository.telephonyManager.getServiceState())
                                != null) {
                    List networkRegistrationInfoListForTransportType =
                            serviceState.getNetworkRegistrationInfoListForTransportType(1);
                    Intrinsics.checkNotNullExpressionValue(
                            networkRegistrationInfoListForTransportType,
                            "getNetworkRegistrationIn…ListForTransportType(...)");
                    if (!networkRegistrationInfoListForTransportType.isEmpty()) {
                        String[] forbiddenPlmns =
                                networkSelectRepository.telephonyManager.getForbiddenPlmns();
                        if (forbiddenPlmns == null
                                || (list = ArraysKt___ArraysKt.toList(forbiddenPlmns)) == null) {
                            list = EmptyList.INSTANCE;
                        }
                        networkRegistrationAndForbiddenInfo =
                                new NetworkSelectRepository.NetworkRegistrationAndForbiddenInfo(
                                        networkRegistrationInfoListForTransportType, list);
                    }
                }
                return networkRegistrationAndForbiddenInfo;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                Function1 function1,
                NetworkSelectRepository networkSelectRepository,
                Continuation continuation) {
            super(2, continuation);
            this.$action = function1;
            this.this$0 = networkSelectRepository;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$action, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DefaultScheduler defaultScheduler = Dispatchers.Default;
                C00381 c00381 = new C00381(this.this$0, null);
                this.label = 1;
                obj = BuildersKt.withContext(defaultScheduler, c00381, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            NetworkSelectRepository.NetworkRegistrationAndForbiddenInfo
                    networkRegistrationAndForbiddenInfo =
                            (NetworkSelectRepository.NetworkRegistrationAndForbiddenInfo) obj;
            if (networkRegistrationAndForbiddenInfo != null) {
                this.$action.invoke(networkRegistrationAndForbiddenInfo);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkSelectRepository$launchUpdateNetworkRegistrationInfo$1(
            LifecycleOwner lifecycleOwner,
            Function1 function1,
            NetworkSelectRepository networkSelectRepository,
            Continuation continuation) {
        super(2, continuation);
        this.$lifecycleOwner = lifecycleOwner;
        this.$action = function1;
        this.this$0 = networkSelectRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NetworkSelectRepository$launchUpdateNetworkRegistrationInfo$1(
                this.$lifecycleOwner, this.$action, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NetworkSelectRepository$launchUpdateNetworkRegistrationInfo$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = this.$lifecycleOwner;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$action, this.this$0, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, anonymousClass1, this)
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
