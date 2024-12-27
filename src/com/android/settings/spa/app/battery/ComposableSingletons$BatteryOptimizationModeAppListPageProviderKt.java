package com.android.settings.spa.app.battery;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;

import com.android.settingslib.spaprivileged.template.app.AppListInput;
import com.android.settingslib.spaprivileged.template.app.AppListKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$BatteryOptimizationModeAppListPageProviderKt {

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f50lambda2;

    static {
        int i =
                ComposableSingletons$BatteryOptimizationModeAppListPageProviderKt$lambda1$1
                        .$r8$clinit;
        f50lambda2 =
                new ComposableLambdaImpl(
                        1316095364,
                        false,
                        new Function3() { // from class:
                                          // com.android.settings.spa.app.battery.ComposableSingletons$BatteryOptimizationModeAppListPageProviderKt$lambda-2$1
                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                AppListInput appListInput = (AppListInput) obj;
                                Composer composer = (Composer) obj2;
                                int intValue = ((Number) obj3).intValue();
                                Intrinsics.checkNotNullParameter(appListInput, "$this$null");
                                if ((intValue & 14) == 0) {
                                    intValue |=
                                            ((ComposerImpl) composer).changed(appListInput) ? 4 : 2;
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
    }
}
