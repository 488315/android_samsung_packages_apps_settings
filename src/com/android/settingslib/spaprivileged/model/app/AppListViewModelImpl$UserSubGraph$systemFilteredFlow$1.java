package com.android.settingslib.spaprivileged.model.app;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u001c\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\b\u0003\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\"\b\b\u0000\u0010\u0001*\u00020\u00002\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00022\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006H\u008a@¢\u0006\u0004\b\b\u0010"
                + "\t"
        },
        d2 = {
            "Lcom/android/settingslib/spaprivileged/model/app/AppRecord;",
            "T",
            "Lkotlin/Function1;",
            "Landroid/content/pm/ApplicationInfo;",
            ApnSettings.MVNO_NONE,
            "showAppPredicate",
            ApnSettings.MVNO_NONE,
            "recordList",
            "<anonymous>",
            "(Lkotlin/jvm/functions/Function1;Ljava/util/List;)Ljava/util/List;"
        },
        k = 3,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
final class AppListViewModelImpl$UserSubGraph$systemFilteredFlow$1 extends SuspendLambda
        implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AppListViewModelImpl$UserSubGraph$systemFilteredFlow$1
                appListViewModelImpl$UserSubGraph$systemFilteredFlow$1 =
                        new AppListViewModelImpl$UserSubGraph$systemFilteredFlow$1(
                                3, (Continuation) obj3);
        appListViewModelImpl$UserSubGraph$systemFilteredFlow$1.L$0 = (Function1) obj;
        appListViewModelImpl$UserSubGraph$systemFilteredFlow$1.L$1 = (List) obj2;
        return appListViewModelImpl$UserSubGraph$systemFilteredFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Function1 function1 = (Function1) this.L$0;
        List list = (List) this.L$1;
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : list) {
            if (((Boolean) function1.invoke(((AppRecord) obj2).getApp())).booleanValue()) {
                arrayList.add(obj2);
            }
        }
        return arrayList;
    }
}
