package com.android.settings.spa.app.appcompat;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

import kotlinx.coroutines.CoroutineScopeKt;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0016\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lcom/android/settings/spa/app/appcompat/UserAspectRatioAppListItemModel;",
            NetworkAnalyticsConstants.DataPoints.UID,
            ApnSettings.MVNO_NONE,
            "appList",
            "Landroid/content/pm/ApplicationInfo;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class UserAspectRatioAppListModel$transform$1 extends SuspendLambda implements Function3 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserAspectRatioAppListModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserAspectRatioAppListModel$transform$1(
            UserAspectRatioAppListModel userAspectRatioAppListModel, Continuation continuation) {
        super(3, continuation);
        this.this$0 = userAspectRatioAppListModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        UserAspectRatioAppListModel$transform$1 userAspectRatioAppListModel$transform$1 =
                new UserAspectRatioAppListModel$transform$1(this.this$0, (Continuation) obj3);
        userAspectRatioAppListModel$transform$1.I$0 = intValue;
        userAspectRatioAppListModel$transform$1.L$0 = (List) obj2;
        return userAspectRatioAppListModel$transform$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            UserAspectRatioAppListModel$transform$1$invokeSuspend$$inlined$asyncMap$1
                    userAspectRatioAppListModel$transform$1$invokeSuspend$$inlined$asyncMap$1 =
                            new UserAspectRatioAppListModel$transform$1$invokeSuspend$$inlined$asyncMap$1(
                                    (List) this.L$0, null, this.this$0, this.I$0);
            this.label = 1;
            obj =
                    CoroutineScopeKt.coroutineScope(
                            userAspectRatioAppListModel$transform$1$invokeSuspend$$inlined$asyncMap$1,
                            this);
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
