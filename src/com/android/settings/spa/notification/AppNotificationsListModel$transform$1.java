package com.android.settings.spa.notification;

import android.content.pm.ApplicationInfo;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u001e\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010$\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0001H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/spa/notification/AppNotificationsRecord;",
            "usageEvents",
            ApnSettings.MVNO_NONE,
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/spa/notification/NotificationSentState;",
            "appList",
            "Landroid/content/pm/ApplicationInfo;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppNotificationsListModel$transform$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ AppNotificationsListModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppNotificationsListModel$transform$1(
            AppNotificationsListModel appNotificationsListModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = appNotificationsListModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AppNotificationsListModel$transform$1 appNotificationsListModel$transform$1 =
                new AppNotificationsListModel$transform$1(this.this$0, (Continuation) obj3);
        appNotificationsListModel$transform$1.L$0 = (Map) obj;
        appNotificationsListModel$transform$1.L$1 = (List) obj2;
        return appNotificationsListModel$transform$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Map map = (Map) this.L$0;
        List<ApplicationInfo> list = (List) this.L$1;
        AppNotificationsListModel appNotificationsListModel = this.this$0;
        ArrayList arrayList =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (ApplicationInfo applicationInfo : list) {
            arrayList.add(
                    new AppNotificationsRecord(
                            applicationInfo,
                            (NotificationSentState) map.get(applicationInfo.packageName),
                            new AppNotificationController(
                                    appNotificationsListModel.repository, applicationInfo)));
        }
        return arrayList;
    }
}
