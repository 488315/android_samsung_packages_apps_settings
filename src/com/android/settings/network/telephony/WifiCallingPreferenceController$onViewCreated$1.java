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
                + "\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "isReady", ApnSettings.MVNO_NONE},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class WifiCallingPreferenceController$onViewCreated$1 extends SuspendLambda
        implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ WifiCallingPreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiCallingPreferenceController$onViewCreated$1(
            WifiCallingPreferenceController wifiCallingPreferenceController,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = wifiCallingPreferenceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiCallingPreferenceController$onViewCreated$1
                wifiCallingPreferenceController$onViewCreated$1 =
                        new WifiCallingPreferenceController$onViewCreated$1(
                                this.this$0, continuation);
        wifiCallingPreferenceController$onViewCreated$1.Z$0 = ((Boolean) obj).booleanValue();
        return wifiCallingPreferenceController$onViewCreated$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((WifiCallingPreferenceController$onViewCreated$1) create(bool, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Preference preference;
        CallingPreferenceCategoryController callingPreferenceCategoryController;
        Object update;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            boolean z = this.Z$0;
            preference = this.this$0.preference;
            if (preference == null) {
                Intrinsics.throwUninitializedPropertyAccessException("preference");
                throw null;
            }
            preference.setVisible(z);
            callingPreferenceCategoryController = this.this$0.callingPreferenceCategoryController;
            if (callingPreferenceCategoryController == null) {
                Intrinsics.throwUninitializedPropertyAccessException(
                        "callingPreferenceCategoryController");
                throw null;
            }
            String preferenceKey = this.this$0.getPreferenceKey();
            Intrinsics.checkNotNullExpressionValue(preferenceKey, "getPreferenceKey(...)");
            callingPreferenceCategoryController.updateChildVisible(preferenceKey, z);
            if (z) {
                WifiCallingPreferenceController wifiCallingPreferenceController = this.this$0;
                this.label = 1;
                update = wifiCallingPreferenceController.update(this);
                if (update == coroutineSingletons) {
                    return coroutineSingletons;
                }
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
