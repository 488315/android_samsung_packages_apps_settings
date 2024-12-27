package com.android.settingslib.spaprivileged.template.app;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$TogglePermissionAppListPageKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f104lambda1 =
            new ComposableLambdaImpl(
                    293721060,
                    false,
                    new Function3() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.ComposableSingletons$TogglePermissionAppListPageKt$lambda-1$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            AppListInput appListInput = (AppListInput) obj;
                            ((Number) obj3).intValue();
                            Intrinsics.checkNotNullParameter(appListInput, "$this$null");
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            AppListKt.AppList(appListInput, (Composer) obj2, 8);
                            return Unit.INSTANCE;
                        }
                    });
}
