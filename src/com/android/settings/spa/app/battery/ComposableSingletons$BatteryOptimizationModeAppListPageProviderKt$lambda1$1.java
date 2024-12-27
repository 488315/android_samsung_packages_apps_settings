package com.android.settings.spa.app.battery;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;
import com.android.settingslib.spa.widget.preference.PreferenceKt;
import com.android.settingslib.spa.widget.preference.PreferenceModel;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u000b¢\u0006\u0004\b\u0004\u0010\u0005"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "it",
            "Landroid/os/Bundle;",
            "invoke",
            "(Landroid/os/Bundle;Landroidx/compose/runtime/Composer;I)V"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* renamed from: com.android.settings.spa.app.battery.ComposableSingletons$BatteryOptimizationModeAppListPageProviderKt$lambda-1$1, reason: invalid class name */
/* loaded from: classes2.dex */
final class ComposableSingletons$BatteryOptimizationModeAppListPageProviderKt$lambda1$1
        extends Lambda implements Function3 {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new ComposableSingletons$BatteryOptimizationModeAppListPageProviderKt$lambda1$1();
    }

    public ComposableSingletons$BatteryOptimizationModeAppListPageProviderKt$lambda1$1() {
        super(3);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Composer composer = (Composer) obj2;
        ((Number) obj3).intValue();
        OpaqueKey opaqueKey = ComposerKt.invocation;
        PreferenceKt.Preference(
                new PreferenceModel(
                        composer) { // from class:
                                    // com.android.settings.spa.app.battery.ComposableSingletons$BatteryOptimizationModeAppListPageProviderKt$lambda-1$1.1
                    public final Lambda onClick;
                    public final String title;

                    {
                        this.title =
                                StringResources_androidKt.stringResource(
                                        composer, R.string.app_battery_usage_title);
                        BatteryOptimizationModeAppListPageProvider
                                batteryOptimizationModeAppListPageProvider =
                                        BatteryOptimizationModeAppListPageProvider.INSTANCE;
                        this.onClick =
                                (Lambda)
                                        NavControllerWrapperKt.navigator(
                                                composer, "BatteryOptimizationModeAppList");
                    }

                    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final Function0 getOnClick() {
                        return this.onClick;
                    }

                    @Override // com.android.settingslib.spa.widget.preference.PreferenceModel
                    public final String getTitle() {
                        return this.title;
                    }
                },
                false,
                composer,
                0,
                2);
        return Unit.INSTANCE;
    }
}
