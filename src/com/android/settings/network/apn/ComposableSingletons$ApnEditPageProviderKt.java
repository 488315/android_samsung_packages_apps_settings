package com.android.settings.network.apn;

import androidx.compose.foundation.layout.RowScope;
import androidx.compose.material3.TextKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$ApnEditPageProviderKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f25lambda1 =
            new ComposableLambdaImpl(
                    -228715520,
                    false,
                    new Function3() { // from class:
                                      // com.android.settings.network.apn.ComposableSingletons$ApnEditPageProviderKt$lambda-1$1
                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            RowScope Button = (RowScope) obj;
                            Composer composer = (Composer) obj2;
                            int intValue = ((Number) obj3).intValue();
                            Intrinsics.checkNotNullParameter(Button, "$this$Button");
                            if ((intValue & 81) == 16) {
                                ComposerImpl composerImpl = (ComposerImpl) composer;
                                if (composerImpl.getSkipping()) {
                                    composerImpl.skipToGroupEnd();
                                    return Unit.INSTANCE;
                                }
                            }
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            TextKt.m210Text4IGK_g(
                                    StringResources_androidKt.stringResource(
                                            composer, R.string.save),
                                    null,
                                    0L,
                                    0L,
                                    null,
                                    null,
                                    null,
                                    0L,
                                    null,
                                    null,
                                    0L,
                                    0,
                                    false,
                                    0,
                                    0,
                                    null,
                                    null,
                                    composer,
                                    0,
                                    0,
                                    131070);
                            return Unit.INSTANCE;
                        }
                    });

    /* renamed from: lambda-2, reason: not valid java name */
    public static final ComposableLambdaImpl f26lambda2 =
            new ComposableLambdaImpl(
                    1685893806,
                    false,
                    new Function2() { // from class:
                                      // com.android.settings.network.apn.ComposableSingletons$ApnEditPageProviderKt$lambda-2$1
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
                            TextKt.m210Text4IGK_g(
                                    StringResources_androidKt.stringResource(
                                            composer, R.string.menu_delete),
                                    null,
                                    0L,
                                    0L,
                                    null,
                                    null,
                                    null,
                                    0L,
                                    null,
                                    null,
                                    0L,
                                    0,
                                    false,
                                    0,
                                    0,
                                    null,
                                    null,
                                    composer,
                                    0,
                                    0,
                                    131070);
                            return Unit.INSTANCE;
                        }
                    });
}
