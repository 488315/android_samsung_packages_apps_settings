package com.android.settings.network.telephony.gsm;

import android.content.Context;

import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.spa.preference.ComposePreference;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010"
                + "\t\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "it", ApnSettings.MVNO_NONE},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class AutoSelectPreferenceController$onViewCreated$1 extends SuspendLambda
        implements Function2 {
    Object L$0;
    int label;
    final /* synthetic */ AutoSelectPreferenceController this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\n\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000b\n"
                    + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
            },
            d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.network.telephony.gsm.AutoSelectPreferenceController$onViewCreated$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ AutoSelectPreferenceController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                AutoSelectPreferenceController autoSelectPreferenceController,
                Continuation continuation) {
            super(2, continuation);
            this.this$0 = autoSelectPreferenceController;
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
            Context context;
            int i;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            context = ((AbstractPreferenceController) this.this$0).mContext;
            i = this.this$0.subId;
            return Boolean.valueOf(
                    MobileNetworkUtils.shouldDisplayNetworkSelectOptions(context, i));
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoSelectPreferenceController$onViewCreated$1(
            AutoSelectPreferenceController autoSelectPreferenceController,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = autoSelectPreferenceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AutoSelectPreferenceController$onViewCreated$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AutoSelectPreferenceController$onViewCreated$1)
                        create(Long.valueOf(((Number) obj).longValue()), (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ComposePreference preference;
        ComposePreference composePreference;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            preference = this.this$0.getPreference();
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
            this.L$0 = preference;
            this.label = 1;
            Object withContext = BuildersKt.withContext(defaultScheduler, anonymousClass1, this);
            if (withContext == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = withContext;
            composePreference = preference;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            composePreference = (ComposePreference) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        composePreference.setVisible(((Boolean) obj).booleanValue());
        return Unit.INSTANCE;
    }
}
