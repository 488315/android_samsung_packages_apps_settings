package com.android.settings.spa.app.appinfo;

import android.content.Context;

import com.android.settings.spa.notification.AppNotificationRepository;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final /* synthetic */ class AppNotificationPreferenceKt$AppNotificationPreference$1
        extends AdaptedFunctionReference implements Function1 {
    public static final AppNotificationPreferenceKt$AppNotificationPreference$1 INSTANCE =
            new AppNotificationPreferenceKt$AppNotificationPreference$1();

    public AppNotificationPreferenceKt$AppNotificationPreference$1() {
        super(
                AppNotificationRepository.class,
                "<init>(Landroid/content/Context;Lcom/android/settingslib/spaprivileged/model/app/IPackageManagers;Landroid/app/usage/IUsageStatsManager;Landroid/app/INotificationManager;Landroid/os/IUserManager;)V");
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Context p0 = (Context) obj;
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new AppNotificationRepository(p0);
    }
}
