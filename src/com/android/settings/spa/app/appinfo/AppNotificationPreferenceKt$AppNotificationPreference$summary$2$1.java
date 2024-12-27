package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.spa.notification.AppNotificationRepository;
import com.android.settings.spa.notification.IAppNotificationRepository;
import com.android.settingslib.spa.framework.util.MessageFormatsKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000e\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/flow/FlowCollector;",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppNotificationPreferenceKt$AppNotificationPreference$summary$2$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ ApplicationInfo $app;
    final /* synthetic */ IAppNotificationRepository $repository;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppNotificationPreferenceKt$AppNotificationPreference$summary$2$1(
            IAppNotificationRepository iAppNotificationRepository,
            ApplicationInfo applicationInfo,
            Continuation continuation) {
        super(2, continuation);
        this.$repository = iAppNotificationRepository;
        this.$app = applicationInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppNotificationPreferenceKt$AppNotificationPreference$summary$2$1
                appNotificationPreferenceKt$AppNotificationPreference$summary$2$1 =
                        new AppNotificationPreferenceKt$AppNotificationPreference$summary$2$1(
                                this.$repository, this.$app, continuation);
        appNotificationPreferenceKt$AppNotificationPreference$summary$2$1.L$0 = obj;
        return appNotificationPreferenceKt$AppNotificationPreference$summary$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppNotificationPreferenceKt$AppNotificationPreference$summary$2$1)
                        create((FlowCollector) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        int i;
        String string;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            IAppNotificationRepository iAppNotificationRepository = this.$repository;
            ApplicationInfo app = this.$app;
            AppNotificationRepository appNotificationRepository =
                    (AppNotificationRepository) iAppNotificationRepository;
            appNotificationRepository.getClass();
            Intrinsics.checkNotNullParameter(app, "app");
            if (appNotificationRepository.notificationManager.areNotificationsEnabledForPackage(
                    app.packageName, app.uid)) {
                int i3 = 0;
                try {
                    i =
                            appNotificationRepository.notificationManager
                                    .getNumNotificationChannelsForPackage(
                                            app.packageName, app.uid, false);
                } catch (Exception e) {
                    Log.w("AppNotificationsRepo", "Error calling INotificationManager", e);
                    i = 0;
                }
                if (i == 0) {
                    string =
                            appNotificationRepository.calculateFrequencySummary(
                                    appNotificationRepository.getSentCount(app));
                } else {
                    try {
                        i3 =
                                appNotificationRepository.notificationManager
                                        .getBlockedChannelCount(app.packageName, app.uid);
                    } catch (Exception e2) {
                        Log.w("AppNotificationsRepo", "Error calling INotificationManager", e2);
                    }
                    if (i == i3) {
                        string =
                                appNotificationRepository.context.getString(
                                        R.string.notifications_disabled);
                        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    } else {
                        String calculateFrequencySummary =
                                appNotificationRepository.calculateFrequencySummary(
                                        appNotificationRepository.getSentCount(app));
                        if (i3 == 0) {
                            string = calculateFrequencySummary;
                        } else {
                            Context context = appNotificationRepository.context;
                            string =
                                    context.getString(
                                            R.string.notifications_enabled_with_info,
                                            calculateFrequencySummary,
                                            MessageFormatsKt.formatString(
                                                    context,
                                                    R.string.notifications_categories_off,
                                                    new Pair("count", Integer.valueOf(i3))));
                            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                        }
                    }
                }
            } else {
                string =
                        appNotificationRepository.context.getString(
                                R.string.notifications_disabled);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            }
            this.label = 1;
            if (flowCollector.emit(string, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
