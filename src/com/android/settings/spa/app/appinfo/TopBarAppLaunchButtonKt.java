package com.android.settings.spa.app.appinfo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;

import androidx.compose.material3.IconButtonKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;

import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TopBarAppLaunchButtonKt {
    public static final void TopBarAppLaunchButton(
            final PackageInfoPresenter packageInfoPresenter,
            final ApplicationInfo app,
            Composer composer,
            final int i) {
        Intrinsics.checkNotNullParameter(packageInfoPresenter, "packageInfoPresenter");
        Intrinsics.checkNotNullParameter(app, "app");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-817505087);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        final Intent launchIntentForPackage =
                packageInfoPresenter
                        .getUserPackageManager()
                        .getLaunchIntentForPackage(app.packageName);
        if (launchIntentForPackage == null) {
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settings.spa.app.appinfo.TopBarAppLaunchButtonKt$TopBarAppLaunchButton$intent$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                TopBarAppLaunchButtonKt.TopBarAppLaunchButton(
                                        PackageInfoPresenter.this,
                                        app,
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        IconButtonKt.IconButton(
                new Function0() { // from class:
                                  // com.android.settings.spa.app.appinfo.TopBarAppLaunchButtonKt$TopBarAppLaunchButton$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        try {
                            packageInfoPresenter.context.startActivityAsUser(
                                    launchIntentForPackage, ApplicationInfosKt.getUserHandle(app));
                        } catch (ActivityNotFoundException unused) {
                        }
                        return Unit.INSTANCE;
                    }
                },
                null,
                false,
                null,
                null,
                ComposableSingletons$TopBarAppLaunchButtonKt.f46lambda1,
                composerImpl,
                196608,
                30);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settings.spa.app.appinfo.TopBarAppLaunchButtonKt$TopBarAppLaunchButton$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            TopBarAppLaunchButtonKt.TopBarAppLaunchButton(
                                    PackageInfoPresenter.this,
                                    app,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
