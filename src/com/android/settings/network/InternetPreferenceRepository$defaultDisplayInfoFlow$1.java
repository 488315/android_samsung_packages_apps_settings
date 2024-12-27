package com.android.settings.network;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0012\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\b\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u008a@"
        },
        d2 = {
            "<anonymous>",
            "Lcom/android/settings/network/InternetPreferenceRepository$DisplayInfo;",
            "airplaneModeOn",
            ApnSettings.MVNO_NONE,
            "wifiState",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class InternetPreferenceRepository$defaultDisplayInfoFlow$1 extends SuspendLambda
        implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ InternetPreferenceRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InternetPreferenceRepository$defaultDisplayInfoFlow$1(
            InternetPreferenceRepository internetPreferenceRepository, Continuation continuation) {
        super(3, continuation);
        this.this$0 = internetPreferenceRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int intValue = ((Number) obj2).intValue();
        InternetPreferenceRepository$defaultDisplayInfoFlow$1
                internetPreferenceRepository$defaultDisplayInfoFlow$1 =
                        new InternetPreferenceRepository$defaultDisplayInfoFlow$1(
                                this.this$0, (Continuation) obj3);
        internetPreferenceRepository$defaultDisplayInfoFlow$1.Z$0 = booleanValue;
        internetPreferenceRepository$defaultDisplayInfoFlow$1.I$0 = intValue;
        return internetPreferenceRepository$defaultDisplayInfoFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        int i = this.I$0;
        if (!z || i == 3) {
            String string = this.this$0.context.getString(R.string.networks_available);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            return new InternetPreferenceRepository.DisplayInfo(
                    string, R.drawable.ic_no_internet_available);
        }
        String string2 = this.this$0.context.getString(R.string.condition_airplane_title);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        return new InternetPreferenceRepository.DisplayInfo(
                string2, R.drawable.ic_no_internet_unavailable);
    }
}
