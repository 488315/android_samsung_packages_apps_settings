package com.android.settingslib.spaprivileged.template.app;

import com.android.settingslib.spa.widget.ui.SpinnerOption;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

import java.util.Collection;
import java.util.Iterator;
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
final class AppListKt$SpinnerOptions$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableStateFlow $optionFlow;
    final /* synthetic */ List<SpinnerOption> $options;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppListKt$SpinnerOptions$1(
            List list, MutableStateFlow mutableStateFlow, Continuation continuation) {
        super(2, continuation);
        this.$options = list;
        this.$optionFlow = mutableStateFlow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppListKt$SpinnerOptions$1(this.$options, this.$optionFlow, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        AppListKt$SpinnerOptions$1 appListKt$SpinnerOptions$1 =
                (AppListKt$SpinnerOptions$1) create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        appListKt$SpinnerOptions$1.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List<SpinnerOption> list = this.$options;
        if (list != null) {
            List<SpinnerOption> list2 = list;
            MutableStateFlow mutableStateFlow = this.$optionFlow;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    int i = ((SpinnerOption) it.next()).id;
                    Integer num = (Integer) ((StateFlowImpl) mutableStateFlow).getValue();
                    if (num != null && i == num.intValue()) {
                        break;
                    }
                }
            }
            MutableStateFlow mutableStateFlow2 = this.$optionFlow;
            SpinnerOption spinnerOption =
                    (SpinnerOption) CollectionsKt___CollectionsKt.firstOrNull((List) this.$options);
            ((StateFlowImpl) mutableStateFlow2)
                    .updateState(null, new Integer(spinnerOption != null ? spinnerOption.id : -1));
        }
        return Unit.INSTANCE;
    }
}
