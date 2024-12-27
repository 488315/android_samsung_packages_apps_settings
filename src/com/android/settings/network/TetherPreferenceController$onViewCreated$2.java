package com.android.settings.network;

import androidx.preference.Preference;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\"\n"
                + "\u0002\u0010\b\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "it",
            ApnSettings.MVNO_NONE,
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class TetherPreferenceController$onViewCreated$2 extends SuspendLambda
        implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TetherPreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TetherPreferenceController$onViewCreated$2(
            TetherPreferenceController tetherPreferenceController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = tetherPreferenceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TetherPreferenceController$onViewCreated$2 tetherPreferenceController$onViewCreated$2 =
                new TetherPreferenceController$onViewCreated$2(this.this$0, continuation);
        tetherPreferenceController$onViewCreated$2.L$0 = obj;
        return tetherPreferenceController$onViewCreated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        TetherPreferenceController$onViewCreated$2 tetherPreferenceController$onViewCreated$2 =
                (TetherPreferenceController$onViewCreated$2) create((Set) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        tetherPreferenceController$onViewCreated$2.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Preference preference;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Set<Integer> set = (Set) this.L$0;
        preference = this.this$0.preference;
        if (preference != null) {
            preference.setSummary(this.this$0.getSummaryResId(set));
        }
        return Unit.INSTANCE;
    }
}
