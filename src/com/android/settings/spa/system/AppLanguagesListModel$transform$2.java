package com.android.settings.spa.system;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.UserHandle;

import com.android.settings.applications.AppLocaleUtil;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000$\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\u0010!\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u000124\u0010\u0003\u001a0\u0012\u0004\u0012\u00020\u0005\u0012&\u0012$\u0012\f\u0012\n"
                + " \b*\u0004\u0018\u00010\u00070\u0007 \b*\u0010\u0012\f\u0012\n"
                + " \b*\u0004\u0018\u00010\u00070\u00070\u00010\u00060\u00042\f\u0010"
                + "\t\u001a\b\u0012\u0004\u0012\u00020\n"
                + "0\u0001H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/spa/system/AppLanguagesRecord;",
            "<name for destructuring parameter 0>",
            "Lkotlin/Pair;",
            ApnSettings.MVNO_NONE,
            ApnSettings.MVNO_NONE,
            "Landroid/content/pm/ResolveInfo;",
            "kotlin.jvm.PlatformType",
            "appList",
            "Landroid/content/pm/ApplicationInfo;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppLanguagesListModel$transform$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ AppLanguagesListModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppLanguagesListModel$transform$2(
            AppLanguagesListModel appLanguagesListModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = appLanguagesListModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AppLanguagesListModel$transform$2 appLanguagesListModel$transform$2 =
                new AppLanguagesListModel$transform$2(this.this$0, (Continuation) obj3);
        appLanguagesListModel$transform$2.L$0 = (Pair) obj;
        appLanguagesListModel$transform$2.L$1 = (List) obj2;
        return appLanguagesListModel$transform$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Pair pair = (Pair) this.L$0;
        List list = (List) this.L$1;
        int intValue = ((Number) pair.getFirst()).intValue();
        List list2 = (List) pair.getSecond();
        Context context = this.this$0.context;
        UserHandle of = UserHandle.of(intValue);
        Intrinsics.checkNotNullExpressionValue(of, "of(...)");
        Context asUser = ContextsKt.asUser(context, of);
        List<ApplicationInfo> list3 = list;
        ArrayList arrayList =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
        for (ApplicationInfo applicationInfo : list3) {
            arrayList.add(
                    new AppLanguagesRecord(
                            applicationInfo,
                            AppLocaleUtil.canDisplayLocaleUi(asUser, applicationInfo, list2)));
        }
        return arrayList;
    }
}
