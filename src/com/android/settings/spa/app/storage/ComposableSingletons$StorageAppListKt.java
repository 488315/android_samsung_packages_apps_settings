package com.android.settings.spa.app.storage;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;

import com.android.settingslib.spa.widget.scaffold.MoreOptionsScope;
import com.android.settingslib.spaprivileged.template.app.AppListInput;
import com.android.settingslib.spaprivileged.template.app.AppListKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$StorageAppListKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f51lambda1 =
            new ComposableLambdaImpl(
                    388673050,
                    false,
                    new Function3() { // from class:
                                      // com.android.settings.spa.app.storage.ComposableSingletons$StorageAppListKt$lambda-1$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            AppListInput appListInput = (AppListInput) obj;
                            Composer composer = (Composer) obj2;
                            int intValue = ((Number) obj3).intValue();
                            Intrinsics.checkNotNullParameter(appListInput, "$this$null");
                            if ((intValue & 14) == 0) {
                                intValue |= ((ComposerImpl) composer).changed(appListInput) ? 4 : 2;
                            }
                            if ((intValue & 91) == 18) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            AppListKt.AppList(appListInput, composer, (intValue & 14) | 8);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f52lambda2 =
            new ComposableLambdaImpl(
                    1689434098,
                    false,
                    new Function3() { // from class:
                                      // com.android.settings.spa.app.storage.ComposableSingletons$StorageAppListKt$lambda-2$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            MoreOptionsScope AppListPage = (MoreOptionsScope) obj;
                            Composer composer = (Composer) obj2;
                            int intValue = ((Number) obj3).intValue();
                            Intrinsics.checkNotNullParameter(AppListPage, "$this$AppListPage");
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
}
