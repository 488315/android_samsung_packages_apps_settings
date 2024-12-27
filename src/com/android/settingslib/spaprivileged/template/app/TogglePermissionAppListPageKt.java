package com.android.settingslib.spaprivileged.template.app;

import androidx.navigation.NamedNavArgumentKt;
import androidx.navigation.NavArgument;
import androidx.navigation.NavArgumentBuilder;
import androidx.navigation.NavType;
import androidx.navigation.NavType$Companion$IntType$1;

import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TogglePermissionAppListPageKt {
    public static final List PAGE_PARAMETER =
            CollectionsKt__CollectionsJVMKt.listOf(
                    NamedNavArgumentKt.navArgument(
                            "permission",
                            new Function1() { // from class:
                                              // com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListPageKt$PAGE_PARAMETER$1
                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj) {
                                    NavArgumentBuilder navArgument = (NavArgumentBuilder) obj;
                                    Intrinsics.checkNotNullParameter(
                                            navArgument, "$this$navArgument");
                                    NavType$Companion$IntType$1 navType$Companion$IntType$1 =
                                            NavType.StringType;
                                    NavArgument.Builder builder = navArgument.builder;
                                    builder.getClass();
                                    builder.type = navType$Companion$IntType$1;
                                    return Unit.INSTANCE;
                                }
                            }));

    /* JADX WARN: Removed duplicated region for block: B:26:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void TogglePermissionAppList(
            final com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel
                    r18,
            final java.lang.String r19,
            kotlin.jvm.functions.Function2 r20,
            kotlin.jvm.functions.Function3 r21,
            androidx.compose.runtime.Composer r22,
            final int r23,
            final int r24) {
        /*
            Method dump skipped, instructions count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListPageKt.TogglePermissionAppList(com.android.settingslib.spaprivileged.template.app.TogglePermissionAppListModel,"
                    + " java.lang.String, kotlin.jvm.functions.Function2,"
                    + " kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int,"
                    + " int):void");
    }
}
