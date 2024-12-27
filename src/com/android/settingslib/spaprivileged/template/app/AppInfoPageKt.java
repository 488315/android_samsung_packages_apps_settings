package com.android.settingslib.spaprivileged.template.app;

import android.content.pm.PackageInfo;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;

import com.android.settingslib.spa.widget.scaffold.RegularScaffoldKt;
import com.android.settingslib.spa.widget.ui.FooterKt;
import com.android.settingslib.spaprivileged.model.app.IPackageManagers;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppInfoPageKt {
    /* JADX WARN: Type inference failed for: r1v15, types: [com.android.settingslib.spaprivileged.template.app.AppInfoPageKt$AppInfoPage$1, kotlin.jvm.internal.Lambda] */
    public static final void AppInfoPage(
            final String title,
            final String packageName,
            final int i,
            final Function2 footerContent,
            final IPackageManagers packageManagers,
            final Function3 content,
            Composer composer,
            final int i2) {
        int i3;
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(footerContent, "footerContent");
        Intrinsics.checkNotNullParameter(packageManagers, "packageManagers");
        Intrinsics.checkNotNullParameter(content, "content");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-595126189);
        if ((i2 & 14) == 0) {
            i3 = (composerImpl.changed(title) ? 4 : 2) | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 112) == 0) {
            i3 |= composerImpl.changed(packageName) ? 32 : 16;
        }
        if ((i2 & 896) == 0) {
            i3 |= composerImpl.changed(i) ? 256 : 128;
        }
        if ((i2 & 7168) == 0) {
            i3 |= composerImpl.changedInstance(footerContent) ? 2048 : 1024;
        }
        if ((57344 & i2) == 0) {
            i3 |=
                    composerImpl.changed(packageManagers)
                            ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT
                            : 8192;
        }
        if ((458752 & i2) == 0) {
            i3 |= composerImpl.changedInstance(content) ? 131072 : 65536;
        }
        if ((374491 & i3) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            composerImpl.startReplaceGroup(1060557486);
            boolean z = ((i3 & 112) == 32) | ((i3 & 896) == 256);
            Object rememberedValue = composerImpl.rememberedValue();
            if (z || rememberedValue == Composer.Companion.Empty) {
                rememberedValue = packageManagers.getPackageInfoAsUser(i, packageName);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final PackageInfo packageInfo = (PackageInfo) rememberedValue;
            composerImpl.end(false);
            if (packageInfo == null) {
                RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
                if (endRestartGroup != null) {
                    endRestartGroup.block =
                            new Function2() { // from class:
                                              // com.android.settingslib.spaprivileged.template.app.AppInfoPageKt$AppInfoPage$packageInfo$2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    ((Number) obj2).intValue();
                                    AppInfoPageKt.AppInfoPage(
                                            title,
                                            packageName,
                                            i,
                                            footerContent,
                                            packageManagers,
                                            content,
                                            (Composer) obj,
                                            RecomposeScopeImplKt.updateChangedFlags(i2 | 1));
                                    return Unit.INSTANCE;
                                }
                            };
                    return;
                }
                return;
            }
            RegularScaffoldKt.RegularScaffold(
                    title,
                    null,
                    ComposableLambdaKt.rememberComposableLambda(
                            1267553518,
                            new Function2() { // from class:
                                              // com.android.settingslib.spaprivileged.template.app.AppInfoPageKt$AppInfoPage$1
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
                                    composerImpl3.startReplaceGroup(1206105966);
                                    boolean changed = composerImpl3.changed(packageInfo);
                                    PackageInfo packageInfo2 = packageInfo;
                                    Object rememberedValue2 = composerImpl3.rememberedValue();
                                    if (changed || rememberedValue2 == Composer.Companion.Empty) {
                                        rememberedValue2 = new AppInfoProvider(packageInfo2);
                                        composerImpl3.updateRememberedValue(rememberedValue2);
                                    }
                                    composerImpl3.end(false);
                                    ((AppInfoProvider) rememberedValue2)
                                            .AppInfo(true, false, composerImpl3, 518, 2);
                                    content.invoke(packageInfo, composerImpl3, 8);
                                    FooterKt.Footer(footerContent, composerImpl3, 0);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    composerImpl,
                    (i3 & 14) | 384,
                    2);
        }
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppInfoPageKt$AppInfoPage$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppInfoPageKt.AppInfoPage(
                                    title,
                                    packageName,
                                    i,
                                    footerContent,
                                    packageManagers,
                                    content,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i2 | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
