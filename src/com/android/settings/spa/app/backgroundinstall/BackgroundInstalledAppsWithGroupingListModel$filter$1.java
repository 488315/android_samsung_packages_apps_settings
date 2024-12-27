package com.android.settings.spa.app.backgroundinstall;

import android.content.pm.PackageInfo;
import android.content.pm.ParceledListSlice;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0012\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/spa/app/backgroundinstall/BackgroundInstalledAppListWithGroupingAppRecord;",
            "userId",
            ApnSettings.MVNO_NONE,
            "recordList"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class BackgroundInstalledAppsWithGroupingListModel$filter$1 extends SuspendLambda
        implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ BackgroundInstalledAppsWithGroupingListModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BackgroundInstalledAppsWithGroupingListModel$filter$1(
            BackgroundInstalledAppsWithGroupingListModel
                    backgroundInstalledAppsWithGroupingListModel,
            Continuation continuation) {
        super(3, continuation);
        this.this$0 = backgroundInstalledAppsWithGroupingListModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        BackgroundInstalledAppsWithGroupingListModel$filter$1
                backgroundInstalledAppsWithGroupingListModel$filter$1 =
                        new BackgroundInstalledAppsWithGroupingListModel$filter$1(
                                this.this$0, (Continuation) obj3);
        backgroundInstalledAppsWithGroupingListModel$filter$1.I$0 = intValue;
        backgroundInstalledAppsWithGroupingListModel$filter$1.L$0 = (List) obj2;
        return backgroundInstalledAppsWithGroupingListModel$filter$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        List list = (List) this.L$0;
        ParceledListSlice backgroundInstalledPackages =
                this.this$0.backgroundInstallService.getBackgroundInstalledPackages(131072L, i);
        Intrinsics.checkNotNull(
                backgroundInstalledPackages,
                "null cannot be cast to non-null type"
                    + " android.content.pm.ParceledListSlice<android.content.pm.PackageInfo>");
        List list2 = backgroundInstalledPackages.getList();
        Intrinsics.checkNotNull(list2);
        List list3 = list2;
        ArrayList arrayList =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
        Iterator it = list3.iterator();
        while (it.hasNext()) {
            arrayList.add(((PackageInfo) it.next()).packageName);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : list) {
            if (arrayList.contains(
                    ((BackgroundInstalledAppListWithGroupingAppRecord) obj2).app.packageName)) {
                arrayList2.add(obj2);
            }
        }
        return arrayList2;
    }
}
