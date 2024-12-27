package com.android.settings.spa.development;

import android.os.Bundle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.compose.RuntimeUtilsKt;
import com.android.settingslib.spaprivileged.model.app.AppListModel;
import com.android.settingslib.spaprivileged.template.app.AppListPageKt;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UsageStatsPageProvider implements SettingsPageProvider {
    public static final UsageStatsPageProvider INSTANCE = new UsageStatsPageProvider();

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(563759543);
        if ((i & 1) == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            AppListPageKt.AppListPage(
                    StringResources_androidKt.stringResource(
                            composerImpl, R.string.testing_usage_stats),
                    (AppListModel)
                            RuntimeUtilsKt.rememberContext(
                                    UsageStatsPageProvider$Page$1.INSTANCE, composerImpl),
                    false,
                    false,
                    false,
                    null,
                    null,
                    null,
                    null,
                    composerImpl,
                    64,
                    FileType.VTS);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.development.UsageStatsPageProvider$Page$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            UsageStatsPageProvider.this.Page(
                                    bundle,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final String getName() {
        return "UsageStats";
    }
}
