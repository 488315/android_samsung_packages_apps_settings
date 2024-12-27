package com.android.settings.applications.specialaccess;

import android.os.UserHandle;

import com.android.settingslib.spaprivileged.model.app.AppListRepository;
import com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Landroid/content/pm/ApplicationInfo;",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class DataSaverController$Companion$getAllowCount$2$1$appsDeferred$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ AppListRepository $appListRepository;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataSaverController$Companion$getAllowCount$2$1$appsDeferred$1(
            AppListRepository appListRepository, Continuation continuation) {
        super(2, continuation);
        this.$appListRepository = appListRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DataSaverController$Companion$getAllowCount$2$1$appsDeferred$1(
                this.$appListRepository, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataSaverController$Companion$getAllowCount$2$1$appsDeferred$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AppListRepository appListRepository = this.$appListRepository;
            int myUserId = UserHandle.myUserId();
            this.label = 1;
            obj =
                    ((AppListRepositoryImpl) appListRepository)
                            .loadAndFilterApps(myUserId, false, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }
}
