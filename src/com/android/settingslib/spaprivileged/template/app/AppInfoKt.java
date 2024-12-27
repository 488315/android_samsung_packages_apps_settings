package com.android.settingslib.spaprivileged.template.app;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;

import com.android.settingslib.spa.framework.compose.DrawablePainterKt;
import com.android.settingslib.spa.widget.ui.TextKt;
import com.android.settingslib.spaprivileged.model.app.AppRepositoryImpl;
import com.android.settingslib.spaprivileged.model.app.AppRepositoryKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AppInfoKt {
    /* renamed from: AppIcon-ziNgDLE, reason: not valid java name */
    public static final void m1050AppIconziNgDLE(
            final ApplicationInfo app, final float f, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(app, "app");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-229615873);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AppRepositoryImpl rememberAppRepository =
                AppRepositoryKt.rememberAppRepository(composerImpl);
        ImageKt.Image(
                DrawablePainterKt.rememberDrawablePainter(
                        (Drawable) rememberAppRepository.produceIcon(app, composerImpl).getValue(),
                        composerImpl),
                (String)
                        rememberAppRepository
                                .produceIconContentDescription(app, composerImpl)
                                .getValue(),
                SizeKt.m96size3ABfNKs(Modifier.Companion.$$INSTANCE, f),
                null,
                null,
                0.0f,
                null,
                composerImpl,
                8,
                120);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppInfoKt$AppIcon$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppInfoKt.m1050AppIconziNgDLE(
                                    app,
                                    f,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void AppLabel(
            final ApplicationInfo app,
            final boolean z,
            Composer composer,
            final int i,
            final int i2) {
        Intrinsics.checkNotNullParameter(app, "app");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(68368828);
        if ((i2 & 2) != 0) {
            z = false;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        TextKt.SettingsTitle(
                (String)
                        AppRepositoryKt.rememberAppRepository(composerImpl)
                                .produceLabel(app, z, composerImpl)
                                .getValue(),
                null,
                true,
                composerImpl,
                384,
                2);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spaprivileged.template.app.AppInfoKt$AppLabel$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            AppInfoKt.AppLabel(
                                    app,
                                    z,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1),
                                    i2);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
