package com.android.settingslib.spaprivileged.template.app;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;

import com.android.settingslib.spa.widget.scaffold.MoreOptionsScope;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$AppListPageKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f101lambda1 =
            new ComposableLambdaImpl(
                    -2078328954,
                    false,
                    new Function3() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.ComposableSingletons$AppListPageKt$lambda-1$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            Composer composer = (Composer) obj2;
                            int intValue = ((Number) obj3).intValue();
                            Intrinsics.checkNotNullParameter((MoreOptionsScope) obj, "$this$null");
                            if ((intValue & 81) == 16) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f102lambda2 =
            new ComposableLambdaImpl(
                    1733764321,
                    false,
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.ComposableSingletons$AppListPageKt$lambda-2$1
                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            Composer composer = (Composer) obj;
                            if ((((Number) obj2).intValue() & 11) == 2) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-3, reason: not valid java name */
    public static final ComposableLambdaImpl f103lambda3 =
            new ComposableLambdaImpl(
                    1587994128,
                    false,
                    new Function3() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.ComposableSingletons$AppListPageKt$lambda-3$1
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
