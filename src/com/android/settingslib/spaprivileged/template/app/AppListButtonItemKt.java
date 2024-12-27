package com.android.settingslib.spaprivileged.template.app;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.graphics.vector.ImageVector;

import com.android.settingslib.spa.framework.theme.SettingsDimension;
import com.android.settingslib.spa.widget.preference.TwoTargetButtonPreferenceKt;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppListButtonItemKt {
    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.settingslib.spaprivileged.template.app.AppListButtonItemKt$AppListButtonItem$1, kotlin.jvm.internal.Lambda] */
    public static final void AppListButtonItem(
            final AppListItemModel appListItemModel,
            final Function0 onClick,
            final Function0 onButtonClick,
            final ImageVector buttonIcon,
            final String buttonIconDescription,
            Composer composer,
            final int i) {
        int i2;
        ComposerImpl composerImpl;
        Intrinsics.checkNotNullParameter(appListItemModel, "<this>");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        Intrinsics.checkNotNullParameter(onButtonClick, "onButtonClick");
        Intrinsics.checkNotNullParameter(buttonIcon, "buttonIcon");
        Intrinsics.checkNotNullParameter(buttonIconDescription, "buttonIconDescription");
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-212039000);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(appListItemModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl2.changedInstance(onClick) ? 32 : 16;
        }
        if ((i & 896) == 0) {
            i2 |= composerImpl2.changedInstance(onButtonClick) ? 256 : 128;
        }
        if ((i & 7168) == 0) {
            i2 |= composerImpl2.changed(buttonIcon) ? 2048 : 1024;
        }
        if ((i & 57344) == 0) {
            i2 |=
                    composerImpl2.changed(buttonIconDescription)
                            ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT
                            : 8192;
        }
        if ((46811 & i2) == 9362 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            int i3 = i2 << 3;
            composerImpl = composerImpl2;
            TwoTargetButtonPreferenceKt.TwoTargetButtonPreference(
                    appListItemModel.label,
                    appListItemModel.summary,
                    ComposableLambdaKt.rememberComposableLambda(
                            -1893677435,
                            new Function2() { // from class:
                                              // com.android.settingslib.spaprivileged.template.app.AppListButtonItemKt$AppListButtonItem$1
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    Composer composer2 = (Composer) obj;
                                    if ((((Number) obj2).intValue() & 11) == 2) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    AppInfoKt.m1050AppIconziNgDLE(
                                            AppListItemModel.this.record.getApp(),
                                            SettingsDimension.appIconItemSize,
                                            composer2,
                                            8);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl2),
                    onClick,
                    buttonIcon,
                    buttonIconDescription,
                    onButtonClick,
                    composerImpl2,
                    (57344 & i3)
                            | ((i2 << 6) & 7168)
                            | 384
                            | (458752 & i3)
                            | ((i2 << 12) & 3670016),
                    0);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppListButtonItemKt$AppListButtonItem$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppListButtonItemKt.AppListButtonItem(
                                    AppListItemModel.this,
                                    onClick,
                                    onButtonClick,
                                    buttonIcon,
                                    buttonIconDescription,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
