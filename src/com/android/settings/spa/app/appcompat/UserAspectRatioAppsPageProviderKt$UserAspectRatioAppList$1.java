package com.android.settings.spa.app.appcompat;

import android.content.Context;

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
final /* synthetic */ class UserAspectRatioAppsPageProviderKt$UserAspectRatioAppList$1
        extends AdaptedFunctionReference implements Function1 {
    public static final UserAspectRatioAppsPageProviderKt$UserAspectRatioAppList$1 INSTANCE =
            new UserAspectRatioAppsPageProviderKt$UserAspectRatioAppList$1();

    public UserAspectRatioAppsPageProviderKt$UserAspectRatioAppList$1() {
        super(
                UserAspectRatioAppListModel.class,
                "<init>(Landroid/content/Context;Lkotlinx/coroutines/CoroutineDispatcher;)V");
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Context p0 = (Context) obj;
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new UserAspectRatioAppListModel(p0);
    }
}
