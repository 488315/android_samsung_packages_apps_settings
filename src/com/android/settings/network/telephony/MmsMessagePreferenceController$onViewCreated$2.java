package com.android.settings.network.telephony;

import androidx.preference.PreferenceScreen;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "it"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class MmsMessagePreferenceController$onViewCreated$2 extends SuspendLambda
        implements Function2 {
    int label;
    final /* synthetic */ MmsMessagePreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MmsMessagePreferenceController$onViewCreated$2(
            MmsMessagePreferenceController mmsMessagePreferenceController,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = mmsMessagePreferenceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MmsMessagePreferenceController$onViewCreated$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        MmsMessagePreferenceController$onViewCreated$2
                mmsMessagePreferenceController$onViewCreated$2 =
                        (MmsMessagePreferenceController$onViewCreated$2)
                                create((Unit) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        mmsMessagePreferenceController$onViewCreated$2.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        PreferenceScreen preferenceScreen;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        preferenceScreen = this.this$0.preferenceScreen;
        if (preferenceScreen != null) {
            super /*com.android.settings.core.TogglePreferenceController*/.displayPreference(
                    preferenceScreen);
        }
        return Unit.INSTANCE;
    }
}
