package com.android.settingslib.spa.widget.preference;

import androidx.compose.material3.DividerKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$BaseLayoutKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f76lambda1 =
            new ComposableLambdaImpl(
                    -106438690,
                    false,
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.preference.ComposableSingletons$BaseLayoutKt$lambda-1$1
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

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f77lambda2 =
            new ComposableLambdaImpl(
                    18209105,
                    false,
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.preference.ComposableSingletons$BaseLayoutKt$lambda-2$1
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
                            DividerKt.m184HorizontalDivider9IZ8Weo(null, 10, 0L, composer, 48, 5);
                            return Unit.INSTANCE;
                        }
                    });

    static {
        int i = ComposableSingletons$BaseLayoutKt$lambda3$1.$r8$clinit;
    }
}
