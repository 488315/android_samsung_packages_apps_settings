package com.android.settings.spa.app.battery;

import android.os.Bundle;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;

import com.android.settingslib.spa.framework.common.SettingsPageProvider;
import com.android.settingslib.spa.framework.common.SettingsPageProviderKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BatteryOptimizationModeAppListPageProvider implements SettingsPageProvider {
    public static final BatteryOptimizationModeAppListPageProvider INSTANCE;

    static {
        BatteryOptimizationModeAppListPageProvider batteryOptimizationModeAppListPageProvider =
                new BatteryOptimizationModeAppListPageProvider();
        INSTANCE = batteryOptimizationModeAppListPageProvider;
        SettingsPageProviderKt.createSettingsPage(batteryOptimizationModeAppListPageProvider, null);
    }

    @Override // com.android.settingslib.spa.framework.common.SettingsPageProvider
    public final void Page(final Bundle bundle, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1049210962);
        if ((i & 1) == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            BatteryOptimizationModeAppListPageProviderKt.BatteryOptimizationModeAppList(
                    null, composerImpl, 0, 1);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.battery.BatteryOptimizationModeAppListPageProvider$Page$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            BatteryOptimizationModeAppListPageProvider.this.Page(
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
        return "BatteryOptimizationModeAppList";
    }
}
