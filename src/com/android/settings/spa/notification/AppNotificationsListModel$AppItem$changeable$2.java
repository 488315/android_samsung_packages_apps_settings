package com.android.settings.spa.notification;

import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.ProduceStateScope;
import androidx.compose.runtime.ProduceStateScopeImpl;

import com.android.settingslib.spaprivileged.template.app.AppListItemModel;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Landroidx/compose/runtime/ProduceStateScope;",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppNotificationsListModel$AppItem$changeable$2 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ AppListItemModel $this_AppItem;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AppNotificationsListModel this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\n\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
            },
            d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.spa.notification.AppNotificationsListModel$AppItem$changeable$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ProduceStateScope $$this$produceState;
        final /* synthetic */ AppListItemModel $this_AppItem;
        int label;
        final /* synthetic */ AppNotificationsListModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                ProduceStateScope produceStateScope,
                AppNotificationsListModel appNotificationsListModel,
                AppListItemModel appListItemModel,
                Continuation continuation) {
            super(2, continuation);
            this.$$this$produceState = produceStateScope;
            this.this$0 = appNotificationsListModel;
            this.$this_AppItem = appListItemModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(
                    this.$$this$produceState, this.this$0, this.$this_AppItem, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 =
                    (AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2);
            Unit unit = Unit.INSTANCE;
            anonymousClass1.invokeSuspend(unit);
            return unit;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ProduceStateScope produceStateScope = this.$$this$produceState;
            AppNotificationRepository appNotificationRepository = this.this$0.repository;
            ApplicationInfo app = ((AppNotificationsRecord) this.$this_AppItem.record).app;
            appNotificationRepository.getClass();
            Intrinsics.checkNotNullParameter(app, "app");
            boolean z = false;
            if (!appNotificationRepository.notificationManager.isImportanceLocked(
                            app.packageName, app.uid)
                    && (app.targetSdkVersion < 33
                            || appNotificationRepository.packageManagers.hasRequestPermission(
                                    app, "android.permission.POST_NOTIFICATIONS"))) {
                z = true;
            }
            ((ProduceStateScopeImpl) produceStateScope).setValue(Boolean.valueOf(z));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppNotificationsListModel$AppItem$changeable$2(
            AppNotificationsListModel appNotificationsListModel,
            AppListItemModel appListItemModel,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = appNotificationsListModel;
        this.$this_AppItem = appListItemModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppNotificationsListModel$AppItem$changeable$2
                appNotificationsListModel$AppItem$changeable$2 =
                        new AppNotificationsListModel$AppItem$changeable$2(
                                this.this$0, this.$this_AppItem, continuation);
        appNotificationsListModel$AppItem$changeable$2.L$0 = obj;
        return appNotificationsListModel$AppItem$changeable$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppNotificationsListModel$AppItem$changeable$2)
                        create((ProduceStateScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProduceStateScope produceStateScope = (ProduceStateScope) this.L$0;
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(produceStateScope, this.this$0, this.$this_AppItem, null);
            this.label = 1;
            if (BuildersKt.withContext(defaultScheduler, anonymousClass1, this)
                    == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
