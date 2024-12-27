package com.android.settings.print;

import android.content.Context;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;

import com.android.settingslib.spaprivileged.template.common.UserGroup;
import com.android.settingslib.spaprivileged.template.common.UserProfilePagerKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$PrintSettingsPageProviderKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f27lambda1 =
            new ComposableLambdaImpl(
                    552657952,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.print.ComposableSingletons$PrintSettingsPageProviderKt$lambda-1$1
                        /* JADX WARN: Type inference failed for: r1v8, types: [com.android.settings.print.ComposableSingletons$PrintSettingsPageProviderKt$lambda-1$1$1, kotlin.jvm.internal.Lambda] */
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
                            ComposerImpl composerImpl2 = (ComposerImpl) composer;
                            Context context =
                                    (Context)
                                            composerImpl2.consume(
                                                    AndroidCompositionLocals_androidKt
                                                            .LocalContext);
                            composerImpl2.startReplaceGroup(823125800);
                            boolean changed = composerImpl2.changed(context);
                            Object rememberedValue = composerImpl2.rememberedValue();
                            if (changed || rememberedValue == Composer.Companion.Empty) {
                                rememberedValue = new PrintRepository(context);
                                composerImpl2.updateRememberedValue(rememberedValue);
                            }
                            final PrintRepository printRepository =
                                    (PrintRepository) rememberedValue;
                            composerImpl2.end(false);
                            UserProfilePagerKt.UserProfilePager(
                                    ComposableLambdaKt.rememberComposableLambda(
                                            1312626124,
                                            new Function3() { // from class:
                                                              // com.android.settings.print.ComposableSingletons$PrintSettingsPageProviderKt$lambda-1$1.1
                                                {
                                                    super(3);
                                                }

                                                @Override // kotlin.jvm.functions.Function3
                                                public final Object invoke(
                                                        Object obj3, Object obj4, Object obj5) {
                                                    UserGroup it = (UserGroup) obj3;
                                                    Composer composer2 = (Composer) obj4;
                                                    int intValue = ((Number) obj5).intValue();
                                                    Intrinsics.checkNotNullParameter(it, "it");
                                                    if ((intValue & 81) == 16) {
                                                        ComposerImpl composerImpl3 =
                                                                (ComposerImpl) composer2;
                                                        if (composerImpl3.getSkipping()) {
                                                            composerImpl3.skipToGroupEnd();
                                                            return Unit.INSTANCE;
                                                        }
                                                    }
                                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                                    PrintSettingsPageProvider.access$PrintServices(
                                                            PrintSettingsPageProvider.INSTANCE,
                                                            PrintRepository.this,
                                                            composer2,
                                                            56);
                                                    return Unit.INSTANCE;
                                                }
                                            },
                                            composerImpl2),
                                    composerImpl2,
                                    6);
                            return Unit.INSTANCE;
                        }
                    });
}
