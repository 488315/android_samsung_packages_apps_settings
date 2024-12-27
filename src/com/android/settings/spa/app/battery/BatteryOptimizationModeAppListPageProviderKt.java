package com.android.settings.spa.app.battery;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.framework.compose.RuntimeUtilsKt;
import com.android.settingslib.spaprivileged.model.app.AppListModel;
import com.android.settingslib.spaprivileged.template.app.AppListPageKt;

import com.samsung.android.knox.custom.IKnoxCustomManager;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BatteryOptimizationModeAppListPageProviderKt {
    public static final void BatteryOptimizationModeAppList(
            final Function3 function3, Composer composer, final int i, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1637416840);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
        } else if ((i & 14) == 0) {
            i3 = (composerImpl.changedInstance(function3) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i3 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (i4 != 0) {
                function3 =
                        ComposableSingletons$BatteryOptimizationModeAppListPageProviderKt
                                .f50lambda2;
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            AppListPageKt.AppListPage(
                    StringResources_androidKt.stringResource(
                            composerImpl, R.string.app_battery_usage_title),
                    (AppListModel)
                            RuntimeUtilsKt.rememberContext(
                                    BatteryOptimizationModeAppListPageProviderKt$BatteryOptimizationModeAppList$1
                                            .INSTANCE,
                                    composerImpl),
                    false,
                    false,
                    false,
                    null,
                    null,
                    null,
                    function3,
                    composerImpl,
                    ((i3 << 24) & 234881024) | 64,
                    IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.battery.BatteryOptimizationModeAppListPageProviderKt$BatteryOptimizationModeAppList$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            BatteryOptimizationModeAppListPageProviderKt
                                    .BatteryOptimizationModeAppList(
                                            Function3.this,
                                            (Composer) obj,
                                            RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                            i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
