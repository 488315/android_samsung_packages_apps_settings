package com.android.settings.network.telephony.gsm;

import android.content.Context;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class AutoSelectPreferenceController$getDisallowedSummary$2 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ ServiceState $serviceState;
    int label;
    final /* synthetic */ AutoSelectPreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoSelectPreferenceController$getDisallowedSummary$2(
            AutoSelectPreferenceController autoSelectPreferenceController,
            ServiceState serviceState,
            Continuation continuation) {
        super(2, continuation);
        this.$serviceState = serviceState;
        this.this$0 = autoSelectPreferenceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AutoSelectPreferenceController$getDisallowedSummary$2(
                this.this$0, this.$serviceState, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AutoSelectPreferenceController$getDisallowedSummary$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean onlyAutoSelectInHome;
        Context context;
        TelephonyManager telephonyManager;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (!this.$serviceState.getRoaming()) {
            onlyAutoSelectInHome = this.this$0.onlyAutoSelectInHome();
            if (onlyAutoSelectInHome) {
                context = ((AbstractPreferenceController) this.this$0).mContext;
                telephonyManager = this.this$0.telephonyManager;
                if (telephonyManager != null) {
                    return context.getString(
                            R.string.manual_mode_disallowed_summary,
                            telephonyManager.getSimOperatorName());
                }
                Intrinsics.throwUninitializedPropertyAccessException("telephonyManager");
                throw null;
            }
        }
        return ApnSettings.MVNO_NONE;
    }
}
