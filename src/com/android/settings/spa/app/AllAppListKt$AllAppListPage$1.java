package com.android.settings.spa.app;

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
final /* synthetic */ class AllAppListKt$AllAppListPage$1 extends AdaptedFunctionReference
        implements Function1 {
    public static final AllAppListKt$AllAppListPage$1 INSTANCE =
            new AllAppListKt$AllAppListPage$1();

    public AllAppListKt$AllAppListPage$1() {
        super(
                AllAppListModel.class,
                "<init>(Landroid/content/Context;Lkotlin/jvm/functions/Function3;)V");
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Context p0 = (Context) obj;
        Intrinsics.checkNotNullParameter(p0, "p0");
        return new AllAppListModel(p0);
    }
}
