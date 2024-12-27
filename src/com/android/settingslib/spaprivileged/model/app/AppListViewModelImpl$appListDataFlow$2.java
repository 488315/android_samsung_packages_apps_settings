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
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt___StringsKt;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0014\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u008a@"
        },
        d2 = {
            "<anonymous>",
            "Lcom/android/settingslib/spaprivileged/model/app/AppListData;",
            "T",
            "Lcom/android/settingslib/spaprivileged/model/app/AppRecord;",
            "appListData",
            "searchQuery",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppListViewModelImpl$appListDataFlow$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AppListViewModelImpl$appListDataFlow$2 appListViewModelImpl$appListDataFlow$2 =
                new AppListViewModelImpl$appListDataFlow$2(3, (Continuation) obj3);
        appListViewModelImpl$appListDataFlow$2.L$0 = (AppListData) obj;
        appListViewModelImpl$appListDataFlow$2.L$1 = (String) obj2;
        return appListViewModelImpl$appListDataFlow$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AppListData appListData = (AppListData) this.L$0;
        final String str = (String) this.L$1;
        Function1 function1 =
                new Function1() { // from class:
                                  // com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$appListDataFlow$2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        AppEntry it = (AppEntry) obj2;
                        Intrinsics.checkNotNullParameter(it, "it");
                        return Boolean.valueOf(StringsKt___StringsKt.contains(it.label, str, true));
                    }
                };
        appListData.getClass();
        List list = appListData.appEntries;
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : list) {
            if (((Boolean) function1.invoke(obj2)).booleanValue()) {
                arrayList.add(obj2);
            }
        }
        return new AppListData(appListData.option, arrayList);
    }
}
