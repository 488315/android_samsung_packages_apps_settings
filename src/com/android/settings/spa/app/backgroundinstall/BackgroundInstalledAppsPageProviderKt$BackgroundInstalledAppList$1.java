package com.android.settings.spa.app.backgroundinstall;

import android.content.Context;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final /* synthetic */ class BackgroundInstalledAppsPageProviderKt$BackgroundInstalledAppList$1
        extends FunctionReferenceImpl implements Function1 {
    public static final BackgroundInstalledAppsPageProviderKt$BackgroundInstalledAppList$1
            INSTANCE = new BackgroundInstalledAppsPageProviderKt$BackgroundInstalledAppList$1();

    public BackgroundInstalledAppsPageProviderKt$BackgroundInstalledAppList$1() {
        super(
                1,
                BackgroundInstalledAppsWithGroupingListModel.class,
                "<init>",
                "<init>(Landroid/content/Context;)V",
                0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Context p0 = (Context) obj;
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new BackgroundInstalledAppsWithGroupingListModel(p0);
    }
}