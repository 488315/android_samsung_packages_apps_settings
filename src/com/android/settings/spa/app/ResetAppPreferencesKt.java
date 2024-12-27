package com.android.settings.spa.app;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.widget.scaffold.MoreOptionsScope;
import com.android.settingslib.spaprivileged.model.enterprise.Restrictions;
import com.android.settingslib.spaprivileged.template.scaffold.RestrictedMenuItemKt;

import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ResetAppPreferencesKt {
    public static final void ResetAppPreferences(
            final MoreOptionsScope moreOptionsScope,
            final Function0 onClick,
            Composer composer,
            final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(moreOptionsScope, "<this>");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-880100305);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(moreOptionsScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changedInstance(onClick) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            RestrictedMenuItemKt.RestrictedMenuItem(
                    moreOptionsScope,
                    StringResources_androidKt.stringResource(
                            composerImpl, R.string.reset_app_preferences),
                    false,
                    new Restrictions(
                            0, CollectionsKt__CollectionsJVMKt.listOf("no_control_apps"), 5),
                    onClick,
                    composerImpl,
                    (i2 & 14) | 4096 | ((i2 << 9) & 57344),
                    2);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.ResetAppPreferencesKt$ResetAppPreferences$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ResetAppPreferencesKt.ResetAppPreferences(
                                    MoreOptionsScope.this,
                                    onClick,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
