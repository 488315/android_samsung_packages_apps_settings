package com.android.settings.spa.app.appinfo;

import android.content.pm.PackageInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.lifecycle.compose.FlowExtKt;

import com.android.settings.R;
import com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt;
import com.android.settingslib.spaprivileged.template.app.AppInfoProvider;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CloneAppInfoSettingsKt {
    /* JADX WARN: Type inference failed for: r2v0, types: [com.android.settings.spa.app.appinfo.CloneAppInfoSettingsKt$CloneAppInfoSettings$1, kotlin.jvm.internal.Lambda] */
    public static final void access$CloneAppInfoSettings(
            final PackageInfoPresenter packageInfoPresenter, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1233597717);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final PackageInfo packageInfo =
                (PackageInfo)
                        FlowExtKt.collectAsStateWithLifecycle(
                                        packageInfoPresenter.flow, composerImpl)
                                .getValue();
        if (packageInfo == null) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.appinfo.CloneAppInfoSettingsKt$CloneAppInfoSettings$packageInfo$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                CloneAppInfoSettingsKt.access$CloneAppInfoSettings(
                                        PackageInfoPresenter.this,
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        RegularScaffoldKt.RegularScaffold(
                StringResources_androidKt.stringResource(
                        composerImpl, R.string.application_info_label),
                null,
                ComposableLambdaKt.rememberComposableLambda(
                        122190022,
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.appinfo.CloneAppInfoSettingsKt$CloneAppInfoSettings$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                Composer composer2 = (Composer) obj;
                                if ((((Number) obj2).intValue() & 11) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                composerImpl3.startReplaceGroup(1465775720);
                                PackageInfo packageInfo2 = packageInfo;
                                Object rememberedValue = composerImpl3.rememberedValue();
                                if (rememberedValue == Composer.Companion.Empty) {
                                    rememberedValue = new AppInfoProvider(packageInfo2);
                                    composerImpl3.updateRememberedValue(rememberedValue);
                                }
                                composerImpl3.end(false);
                                ((AppInfoProvider) rememberedValue)
                                        .AppInfo(false, true, composerImpl3, 560, 1);
                                ClonePageAppButtonsKt.ClonePageAppButtons(
                                        PackageInfoPresenter.this, composerImpl3, 8);
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                384,
                2);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.CloneAppInfoSettingsKt$CloneAppInfoSettings$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            CloneAppInfoSettingsKt.access$CloneAppInfoSettings(
                                    PackageInfoPresenter.this,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
