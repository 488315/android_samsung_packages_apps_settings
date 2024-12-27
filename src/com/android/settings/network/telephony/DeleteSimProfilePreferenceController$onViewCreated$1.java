package com.android.settings.network.telephony;

import androidx.preference.Preference;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "it", ApnSettings.MVNO_NONE},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class DeleteSimProfilePreferenceController$onViewCreated$1 extends SuspendLambda
        implements Function2 {
    /* synthetic */ int I$0;
    int label;
    final /* synthetic */ DeleteSimProfilePreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeleteSimProfilePreferenceController$onViewCreated$1(
            DeleteSimProfilePreferenceController deleteSimProfilePreferenceController,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = deleteSimProfilePreferenceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeleteSimProfilePreferenceController$onViewCreated$1
                deleteSimProfilePreferenceController$onViewCreated$1 =
                        new DeleteSimProfilePreferenceController$onViewCreated$1(
                                this.this$0, continuation);
        deleteSimProfilePreferenceController$onViewCreated$1.I$0 = ((Number) obj).intValue();
        return deleteSimProfilePreferenceController$onViewCreated$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        DeleteSimProfilePreferenceController$onViewCreated$1
                deleteSimProfilePreferenceController$onViewCreated$1 =
                        (DeleteSimProfilePreferenceController$onViewCreated$1)
                                create(
                                        Integer.valueOf(((Number) obj).intValue()),
                                        (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        deleteSimProfilePreferenceController$onViewCreated$1.invokeSuspend(unit);
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
        int i = this.I$0;
        preference = this.this$0.preference;
        if (preference != null) {
            preference.setEnabled(i == 0);
            return Unit.INSTANCE;
        }
        Intrinsics.throwUninitializedPropertyAccessException("preference");
        throw null;
    }
}
