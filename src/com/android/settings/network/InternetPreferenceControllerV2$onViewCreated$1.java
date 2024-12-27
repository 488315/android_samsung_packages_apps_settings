package com.android.settings.network;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.preference.Preference;

import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;

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
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "displayInfo",
            "Lcom/android/settings/network/InternetPreferenceRepository$DisplayInfo;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class InternetPreferenceControllerV2$onViewCreated$1 extends SuspendLambda
        implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ InternetPreferenceControllerV2 this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InternetPreferenceControllerV2$onViewCreated$1(
            InternetPreferenceControllerV2 internetPreferenceControllerV2,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = internetPreferenceControllerV2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        InternetPreferenceControllerV2$onViewCreated$1
                internetPreferenceControllerV2$onViewCreated$1 =
                        new InternetPreferenceControllerV2$onViewCreated$1(
                                this.this$0, continuation);
        internetPreferenceControllerV2$onViewCreated$1.L$0 = obj;
        return internetPreferenceControllerV2$onViewCreated$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        InternetPreferenceControllerV2$onViewCreated$1
                internetPreferenceControllerV2$onViewCreated$1 =
                        (InternetPreferenceControllerV2$onViewCreated$1)
                                create(
                                        (InternetPreferenceRepository.DisplayInfo) obj,
                                        (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        internetPreferenceControllerV2$onViewCreated$1.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Preference preference;
        Context context;
        Context context2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        InternetPreferenceRepository.DisplayInfo displayInfo =
                (InternetPreferenceRepository.DisplayInfo) this.L$0;
        preference = this.this$0.preference;
        if (preference != null) {
            InternetPreferenceControllerV2 internetPreferenceControllerV2 = this.this$0;
            preference.setSummary(displayInfo.summary);
            context = ((AbstractPreferenceController) internetPreferenceControllerV2).mContext;
            Drawable drawable = context.getDrawable(displayInfo.iconResId);
            if (drawable != null) {
                context2 = ((AbstractPreferenceController) internetPreferenceControllerV2).mContext;
                drawable.setTintList(Utils.getColorAttr(context2, R.attr.colorControlNormal));
            } else {
                drawable = null;
            }
            preference.setIcon(drawable);
        }
        return Unit.INSTANCE;
    }
}
