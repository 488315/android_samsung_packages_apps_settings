package com.android.settingslib.spaprivileged.model.app;

import android.content.pm.ApplicationInfo;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0018\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "T",
            "Lcom/android/settingslib/spaprivileged/model/app/AppRecord;",
            "recordList",
            ApnSettings.MVNO_NONE,
            "listModel",
            "Lcom/android/settingslib/spaprivileged/model/app/AppListModel;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppListViewModelImpl$scheduleOnFirstLoaded$1 extends SuspendLambda
        implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ AppListViewModelImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppListViewModelImpl$scheduleOnFirstLoaded$1(
            AppListViewModelImpl appListViewModelImpl, Continuation continuation) {
        super(3, continuation);
        this.this$0 = appListViewModelImpl;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AppListViewModelImpl$scheduleOnFirstLoaded$1 appListViewModelImpl$scheduleOnFirstLoaded$1 =
                new AppListViewModelImpl$scheduleOnFirstLoaded$1(this.this$0, (Continuation) obj3);
        appListViewModelImpl$scheduleOnFirstLoaded$1.L$0 = (List) obj;
        appListViewModelImpl$scheduleOnFirstLoaded$1.L$1 = (AppListModel) obj2;
        return appListViewModelImpl$scheduleOnFirstLoaded$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        List list;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            List list2 = (List) this.L$0;
            AppListModel appListModel = (AppListModel) this.L$1;
            this.L$0 = list2;
            this.label = 1;
            Object onFirstLoaded = appListModel.onFirstLoaded(list2, this);
            if (onFirstLoaded == coroutineSingletons) {
                return coroutineSingletons;
            }
            list = list2;
            obj = onFirstLoaded;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list = (List) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        if (((Boolean) obj).booleanValue()) {
            AppListViewModelImpl appListViewModelImpl = this.this$0;
            appListViewModelImpl.getClass();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ApplicationInfo app = ((AppRecord) it.next()).getApp();
                Object computeIfAbsent =
                        appListViewModelImpl.labelMap.computeIfAbsent(
                                app.packageName,
                                new AppListViewModelImpl$getLabel$1(appListViewModelImpl, app));
                Intrinsics.checkNotNullExpressionValue(computeIfAbsent, "computeIfAbsent(...)");
            }
        }
        return Unit.INSTANCE;
    }
}
