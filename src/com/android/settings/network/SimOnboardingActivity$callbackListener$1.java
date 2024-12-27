package com.android.settings.network;

import android.telephony.SubscriptionInfo;
import android.util.Log;

import androidx.compose.runtime.MutableState;

import com.android.settings.wifi.WifiPickerTrackerHelper;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n"
                + "¢\u0006\u0002\b\u0004"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "it",
            "Lcom/android/settings/network/SimOnboardingActivity$Companion$CallbackType;",
            "invoke"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class SimOnboardingActivity$callbackListener$1 extends Lambda implements Function1 {
    final /* synthetic */ SimOnboardingActivity this$0;

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
    /* renamed from: com.android.settings.network.SimOnboardingActivity$callbackListener$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ SimOnboardingActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                SimOnboardingActivity simOnboardingActivity, Continuation continuation) {
            super(2, continuation);
            this.this$0 = simOnboardingActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
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
            Unit unit = Unit.INSTANCE;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SimOnboardingService simOnboardingService = SimOnboardingActivity.onboardingService;
                SimOnboardingActivity simOnboardingActivity = this.this$0;
                this.label = 1;
                simOnboardingService.getClass();
                Object withContext =
                        BuildersKt.withContext(
                                Dispatchers.Default,
                                new SimOnboardingService$startEnableDsds$2(
                                        simOnboardingActivity, simOnboardingService, null),
                                this);
                if (withContext != coroutineSingletons) {
                    withContext = unit;
                }
                if (withContext == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return unit;
        }
    }

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
    /* renamed from: com.android.settings.network.SimOnboardingActivity$callbackListener$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ SimOnboardingActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(
                SimOnboardingActivity simOnboardingActivity, Continuation continuation) {
            super(2, continuation);
            this.this$0 = simOnboardingActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            AnonymousClass2 anonymousClass2 =
                    (AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2);
            Unit unit = Unit.INSTANCE;
            anonymousClass2.invokeSuspend(unit);
            return unit;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            SimOnboardingActivity simOnboardingActivity = this.this$0;
            simOnboardingActivity.getClass();
            Log.d("SimOnboardingActivity", "startSimSwitching:");
            SimOnboardingService simOnboardingService = SimOnboardingActivity.onboardingService;
            SubscriptionInfo subscriptionInfo = simOnboardingService.targetSubInfo;
            boolean z = (subscriptionInfo != null ? subscriptionInfo.getSimSlotIndex() : -1) >= 0;
            Unit unit = Unit.INSTANCE;
            if (z) {
                Log.d("SimOnboardingActivity", "target subInfo is already active");
                ((SimOnboardingActivity$callbackListener$1) simOnboardingActivity.callbackListener)
                        .invoke(SimOnboardingActivity$Companion$CallbackType.CALLBACK_SETUP_NAME);
            } else {
                Object obj2 = null;
                if (subscriptionInfo != null) {
                    Iterator it = simOnboardingService.activeSubInfoList.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Object next = it.next();
                        if (!((ArrayList) simOnboardingService.userSelectedSubInfoList)
                                .contains((SubscriptionInfo) next)) {
                            obj2 = next;
                            break;
                        }
                    }
                    SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) obj2;
                    if (subscriptionInfo.isEmbedded()) {
                        SwitchToEuiccSubscriptionSidecar switchToEuiccSubscriptionSidecar =
                                simOnboardingActivity.switchToEuiccSubscriptionSidecar;
                        Intrinsics.checkNotNull(switchToEuiccSubscriptionSidecar);
                        switchToEuiccSubscriptionSidecar.run(
                                subscriptionInfo.getSubscriptionId(), -1, subscriptionInfo2);
                    } else {
                        SwitchToRemovableSlotSidecar switchToRemovableSlotSidecar =
                                simOnboardingActivity.switchToRemovableSlotSidecar;
                        Intrinsics.checkNotNull(switchToRemovableSlotSidecar);
                        switchToRemovableSlotSidecar.run(subscriptionInfo2);
                    }
                    obj2 = unit;
                }
                if (obj2 == null) {
                    Log.e("SimOnboardingActivity", "no target subInfo in onboardingService");
                    simOnboardingActivity.finish();
                }
            }
            return unit;
        }
    }

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
    /* renamed from: com.android.settings.network.SimOnboardingActivity$callbackListener$1$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(2, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            Unit unit = Unit.INSTANCE;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SimOnboardingService simOnboardingService = SimOnboardingActivity.onboardingService;
                this.label = 1;
                simOnboardingService.getClass();
                Object withContext =
                        BuildersKt.withContext(
                                Dispatchers.Default,
                                new SimOnboardingService$startSetupName$2(
                                        simOnboardingService, null),
                                this);
                if (withContext != coroutineSingletons) {
                    withContext = unit;
                }
                if (withContext == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return unit;
        }
    }

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
    /* renamed from: com.android.settings.network.SimOnboardingActivity$callbackListener$1$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ SimOnboardingActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(
                SimOnboardingActivity simOnboardingActivity, Continuation continuation) {
            super(2, continuation);
            this.this$0 = simOnboardingActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass4(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SimOnboardingService simOnboardingService = SimOnboardingActivity.onboardingService;
                SimOnboardingService simOnboardingService2 =
                        SimOnboardingActivity.onboardingService;
                SimOnboardingActivity simOnboardingActivity = this.this$0;
                WifiPickerTrackerHelper wifiPickerTrackerHelper =
                        simOnboardingActivity.wifiPickerTrackerHelper;
                if (wifiPickerTrackerHelper == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("wifiPickerTrackerHelper");
                    throw null;
                }
                this.label = 1;
                if (simOnboardingService2.startSetupPrimarySim(
                                simOnboardingActivity, wifiPickerTrackerHelper, this)
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
    public SimOnboardingActivity$callbackListener$1(SimOnboardingActivity simOnboardingActivity) {
        super(1);
        this.this$0 = simOnboardingActivity;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        SimOnboardingActivity$Companion$CallbackType it =
                (SimOnboardingActivity$Companion$CallbackType) obj;
        Intrinsics.checkNotNullParameter(it, "it");
        Log.d("SimOnboardingActivity", "Receive the CALLBACK: " + it);
        int ordinal = it.ordinal();
        if (ordinal == 0) {
            this.this$0.setProgressDialog(false);
        } else if (ordinal == 1) {
            MutableState mutableState = this.this$0.showStartingDialog;
            if (mutableState == null) {
                Intrinsics.throwUninitializedPropertyAccessException("showStartingDialog");
                throw null;
            }
            mutableState.setValue(Boolean.FALSE);
            this.this$0.setProgressDialog(true);
            BuildersKt.launch$default(
                    this.this$0.getScope(), null, null, new AnonymousClass2(this.this$0, null), 3);
        } else if (ordinal == 2) {
            BuildersKt.launch$default(
                    this.this$0.getScope(), null, null, new AnonymousClass1(this.this$0, null), 3);
        } else if (ordinal == 3) {
            BuildersKt.launch$default(
                    this.this$0.getScope(), null, null, new AnonymousClass3(2, null), 3);
        } else if (ordinal == 4) {
            BuildersKt.launch$default(
                    this.this$0.getScope(), null, null, new AnonymousClass4(this.this$0, null), 3);
        } else if (ordinal == 5) {
            this.this$0.finish();
        }
        return Unit.INSTANCE;
    }
}
