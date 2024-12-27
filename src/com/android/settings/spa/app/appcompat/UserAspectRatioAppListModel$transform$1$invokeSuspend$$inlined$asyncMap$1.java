package com.android.settings.spa.app.appcompat;

import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.android.settings.applications.appcompat.UserAspectRatioManager;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.AwaitKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010 \n"
                + "\u0002\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"
        },
        d2 = {
            "R",
            "T",
            "Lkotlinx/coroutines/CoroutineScope;",
            ApnSettings.MVNO_NONE,
            "<anonymous>",
            "(Lkotlinx/coroutines/CoroutineScope;)Ljava/util/List;"
        },
        k = 3,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class UserAspectRatioAppListModel$transform$1$invokeSuspend$$inlined$asyncMap$1
        extends SuspendLambda implements Function2 {
    final /* synthetic */ Iterable $this_asyncMap;
    final /* synthetic */ int $uid$inlined;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ UserAspectRatioAppListModel this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\n\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\u0010\u0003\u001a\u00028\u0000\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\u00020\u0002H\u008a@"
            },
            d2 = {"R", "T", "Lkotlinx/coroutines/CoroutineScope;", "<anonymous>"},
            k = 3,
            mv = {1, 9, 0})
    /* renamed from: com.android.settings.spa.app.appcompat.UserAspectRatioAppListModel$transform$1$invokeSuspend$$inlined$asyncMap$1$1, reason: invalid class name */
    public final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Object $item;
        final /* synthetic */ int $uid$inlined;
        int label;
        final /* synthetic */ UserAspectRatioAppListModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                Object obj,
                Continuation continuation,
                UserAspectRatioAppListModel userAspectRatioAppListModel,
                int i) {
            super(2, continuation);
            this.$item = obj;
            this.this$0 = userAspectRatioAppListModel;
            this.$uid$inlined = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$item, continuation, this.this$0, this.$uid$inlined);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            PackageInfo packageInfo;
            boolean z = true;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ApplicationInfo applicationInfo = (ApplicationInfo) this.$item;
            if (!applicationInfo.isSystemApp()) {
                UserAspectRatioAppListModel userAspectRatioAppListModel = this.this$0;
                PackageManager.PackageInfoFlags packageInfoFlags =
                        UserAspectRatioAppListModel.GET_ACTIVITIES_FLAGS;
                userAspectRatioAppListModel.getClass();
                try {
                    packageInfo =
                            userAspectRatioAppListModel.packageManager.getPackageInfoAsUser(
                                    applicationInfo.packageName,
                                    UserAspectRatioAppListModel.GET_ACTIVITIES_FLAGS,
                                    ApplicationInfosKt.getUserId(applicationInfo));
                } catch (Exception e) {
                    Log.e("AspectRatioAppsListModel", "Exception while getPackageInfoAsUser", e);
                    packageInfo = null;
                }
                if (packageInfo != null) {
                    PackageManager.PackageInfoFlags packageInfoFlags2 =
                            UserAspectRatioAppListModel.GET_ACTIVITIES_FLAGS;
                    ActivityInfo[] activityInfoArr = packageInfo.activities;
                    if (activityInfoArr != null) {
                        for (ActivityInfo activityInfo : activityInfoArr) {
                            if (activityInfo.isFixedOrientation()
                                    || activityInfo.hasFixedAspectRatio()) {
                                break;
                            }
                        }
                    }
                }
            }
            z = false;
            UserAspectRatioManager userAspectRatioManager = this.this$0.userAspectRatioManager;
            String str = applicationInfo.packageName;
            int userMinAspectRatio =
                    userAspectRatioManager.mIPm.getUserMinAspectRatio(str, this.$uid$inlined);
            return new UserAspectRatioAppListItemModel(
                    applicationInfo,
                    userAspectRatioManager.hasAspectRatioOption(userMinAspectRatio, str)
                            ? userMinAspectRatio
                            : 0,
                    z,
                    this.this$0.userAspectRatioManager.canDisplayAspectRatioUi(applicationInfo));
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UserAspectRatioAppListModel$transform$1$invokeSuspend$$inlined$asyncMap$1(
            Iterable iterable,
            Continuation continuation,
            UserAspectRatioAppListModel userAspectRatioAppListModel,
            int i) {
        super(2, continuation);
        this.$this_asyncMap = iterable;
        this.this$0 = userAspectRatioAppListModel;
        this.$uid$inlined = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        UserAspectRatioAppListModel$transform$1$invokeSuspend$$inlined$asyncMap$1
                userAspectRatioAppListModel$transform$1$invokeSuspend$$inlined$asyncMap$1 =
                        new UserAspectRatioAppListModel$transform$1$invokeSuspend$$inlined$asyncMap$1(
                                this.$this_asyncMap, continuation, this.this$0, this.$uid$inlined);
        userAspectRatioAppListModel$transform$1$invokeSuspend$$inlined$asyncMap$1.L$0 = obj;
        return userAspectRatioAppListModel$transform$1$invokeSuspend$$inlined$asyncMap$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UserAspectRatioAppListModel$transform$1$invokeSuspend$$inlined$asyncMap$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            Iterable iterable = this.$this_asyncMap;
            ArrayList arrayList =
                    new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
            Iterator it = iterable.iterator();
            while (it.hasNext()) {
                arrayList.add(
                        BuildersKt.async$default(
                                coroutineScope,
                                new AnonymousClass1(
                                        it.next(), null, this.this$0, this.$uid$inlined)));
            }
            this.label = 1;
            obj = AwaitKt.awaitAll(arrayList, this);
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
