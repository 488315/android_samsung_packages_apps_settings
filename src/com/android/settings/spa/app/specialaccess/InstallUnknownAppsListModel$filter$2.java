package com.android.settings.spa.app.specialaccess;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0016\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\"\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/spa/app/specialaccess/InstallUnknownAppsRecord;",
            "potentialPackageNames",
            ApnSettings.MVNO_NONE,
            ApnSettings.MVNO_NONE,
            "recordList"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class InstallUnknownAppsListModel$filter$2 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        InstallUnknownAppsListModel$filter$2 installUnknownAppsListModel$filter$2 =
                new InstallUnknownAppsListModel$filter$2(3, (Continuation) obj3);
        installUnknownAppsListModel$filter$2.L$0 = (Set) obj;
        installUnknownAppsListModel$filter$2.L$1 = (List) obj2;
        return installUnknownAppsListModel$filter$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Set set = (Set) this.L$0;
        List list = (List) this.L$1;
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : list) {
            InstallUnknownAppsListModel.Companion companion = InstallUnknownAppsListModel.Companion;
            if (InstallUnknownAppsListModel.Companion.access$isChangeable(
                    (InstallUnknownAppsRecord) obj2, set)) {
                arrayList.add(obj2);
            }
        }
        return arrayList;
    }
}
