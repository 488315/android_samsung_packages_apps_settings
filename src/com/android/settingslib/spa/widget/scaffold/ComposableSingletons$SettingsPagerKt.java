package com.android.settingslib.spa.widget.scaffold;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$SettingsPagerKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f95lambda1 =
            new ComposableLambdaImpl(
                    -1508386783,
                    false,
                    new Function3() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ComposableSingletons$SettingsPagerKt$lambda-1$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            List it = (List) obj;
                            ((Number) obj3).intValue();
                            Intrinsics.checkNotNullParameter(it, "it");
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f96lambda2 =
            new ComposableLambdaImpl(
                    364697504,
                    false,
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ComposableSingletons$SettingsPagerKt$lambda-2$1
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
}
