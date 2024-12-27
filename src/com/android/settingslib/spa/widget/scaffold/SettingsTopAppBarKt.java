package com.android.settingslib.spa.widget.scaffold;

import androidx.compose.material3.TopAppBarScrollBehavior;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsTopAppBarKt {
    public static final void SettingsTopAppBar(
            final String title,
            final TopAppBarScrollBehavior scrollBehavior,
            final Function3 actions,
            Composer composer,
            final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(scrollBehavior, "scrollBehavior");
        Intrinsics.checkNotNullParameter(actions, "actions");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-329820538);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(title) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changed(scrollBehavior) ? 32 : 16;
        }
        if ((i & 896) == 0) {
            i2 |= composerImpl.changedInstance(actions) ? 256 : 128;
        }
        if ((i2 & 731) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            CustomizedAppBarKt.CustomizedLargeTopAppBar(
                    title,
                    null,
                    ComposableSingletons$SettingsTopAppBarKt.f99lambda1,
                    actions,
                    scrollBehavior,
                    composerImpl,
                    (i2 & 14) | 384 | ((i2 << 3) & 7168) | ((i2 << 9) & 57344),
                    2);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.SettingsTopAppBarKt$SettingsTopAppBar$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsTopAppBarKt.SettingsTopAppBar(
                                    title,
                                    scrollBehavior,
                                    actions,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
