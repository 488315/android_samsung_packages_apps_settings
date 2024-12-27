package com.android.settingslib.spaprivileged.model.app;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\"\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\n"
                + "\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00060\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\b2\u0006\u0010"
                + "\t\u001a\u00020\n"
                + "H\u008a@"
        },
        d2 = {
            "<anonymous>",
            "Lcom/android/settingslib/spaprivileged/model/app/AppListData;",
            "T",
            "Lcom/android/settingslib/spaprivileged/model/app/AppRecord;",
            "appEntries",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settingslib/spaprivileged/model/app/AppEntry;",
            "listModel",
            "Lcom/android/settingslib/spaprivileged/model/app/AppListModel;",
            "option",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppListViewModelImpl$appListDataFlow$1 extends SuspendLambda implements Function4 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        int intValue = ((Number) obj3).intValue();
        AppListViewModelImpl$appListDataFlow$1 appListViewModelImpl$appListDataFlow$1 =
                new AppListViewModelImpl$appListDataFlow$1(4, (Continuation) obj4);
        appListViewModelImpl$appListDataFlow$1.L$0 = (List) obj;
        appListViewModelImpl$appListDataFlow$1.L$1 = (AppListModel) obj2;
        appListViewModelImpl$appListDataFlow$1.I$0 = intValue;
        return appListViewModelImpl$appListDataFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        AppListModel appListModel = (AppListModel) this.L$1;
        int i = this.I$0;
        return new AppListData(
                i, CollectionsKt___CollectionsKt.sortedWith(list, appListModel.getComparator(i)));
    }
}
