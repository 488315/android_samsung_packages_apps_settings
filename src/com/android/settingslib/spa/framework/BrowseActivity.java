package com.android.settingslib.spa.framework;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.activity.compose.ComponentActivityKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.core.view.WindowInsetsControllerCompat;

import com.android.settings.spa.SettingsSpaEnvironment;
import com.android.settingslib.spa.framework.common.LogCategory;
import com.android.settingslib.spa.framework.common.SettingsPageProviderRepository;
import com.android.settingslib.spa.framework.common.SpaEnvironment;
import com.android.settingslib.spa.framework.common.SpaEnvironmentFactory;
import com.android.settingslib.spa.framework.theme.SettingsThemeKt;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BrowseActivity extends ComponentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public void onCreate(Bundle bundle) {
        setTheme(2132084377);
        super.onCreate(bundle);
        int i = EdgeToEdge.DefaultLightScrim;
        SystemBarStyle auto$default = SystemBarStyle.Companion.auto$default(0, 0);
        SystemBarStyle auto$default2 =
                SystemBarStyle.Companion.auto$default(
                        EdgeToEdge.DefaultLightScrim, EdgeToEdge.DefaultDarkScrim);
        View decorView = getWindow().getDecorView();
        Intrinsics.checkNotNullExpressionValue(decorView, "window.decorView");
        Resources resources = decorView.getResources();
        Intrinsics.checkNotNullExpressionValue(resources, "view.resources");
        boolean booleanValue =
                ((Boolean) auto$default.detectDarkMode.invoke(resources)).booleanValue();
        Resources resources2 = decorView.getResources();
        Intrinsics.checkNotNullExpressionValue(resources2, "view.resources");
        boolean booleanValue2 =
                ((Boolean) auto$default2.detectDarkMode.invoke(resources2)).booleanValue();
        Window window = getWindow();
        Intrinsics.checkNotNullExpressionValue(window, "window");
        window.setDecorFitsSystemWindows(false);
        window.setStatusBarColor(0);
        window.setNavigationBarColor(0);
        window.setStatusBarContrastEnforced(false);
        window.setNavigationBarContrastEnforced(true);
        WindowInsetsControllerCompat windowInsetsControllerCompat =
                new WindowInsetsControllerCompat(window, decorView);
        windowInsetsControllerCompat.setAppearanceLightStatusBars(!booleanValue);
        windowInsetsControllerCompat.setAppearanceLightNavigationBars(!booleanValue2);
        SpaEnvironment spaEnvironment = SpaEnvironmentFactory.spaEnvironment;
        if (spaEnvironment == null) {
            throw new UnsupportedOperationException("Spa environment is not set");
        }
        LogCategory logCategory = LogCategory.FRAMEWORK;
        ((SettingsSpaEnvironment) spaEnvironment).logger.getClass();
        ComponentActivityKt.setContent$default(
                this,
                new ComposableLambdaImpl(
                        -629781350,
                        true,
                        new Function2() { // from class:
                            // com.android.settingslib.spa.framework.BrowseActivity$onCreate$1
                            {
                                super(2);
                            }

                            /* JADX WARN: Type inference failed for: r3v5, types: [com.android.settingslib.spa.framework.BrowseActivity$onCreate$1$1, kotlin.jvm.internal.Lambda] */
                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                Composer composer = (Composer) obj;
                                if ((((Number) obj2).intValue() & 11) == 2) {
                                    ComposerImpl composerImpl = (ComposerImpl) composer;
                                    if (composerImpl.getSkipping()) {
                                        composerImpl.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey = ComposerKt.invocation;
                                final BrowseActivity browseActivity = BrowseActivity.this;
                                SettingsThemeKt.SettingsTheme(
                                        ComposableLambdaKt.rememberComposableLambda(
                                                322274943,
                                                new Function2() { // from class:
                                                    // com.android.settingslib.spa.framework.BrowseActivity$onCreate$1.1
                                                    {
                                                        super(2);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function2
                                                    public final Object invoke(
                                                            Object obj3, Object obj4) {
                                                        Composer composer2 = (Composer) obj3;
                                                        if ((((Number) obj4).intValue() & 11)
                                                                == 2) {
                                                            ComposerImpl composerImpl2 =
                                                                    (ComposerImpl) composer2;
                                                            if (composerImpl2.getSkipping()) {
                                                                composerImpl2.skipToGroupEnd();
                                                                return Unit.INSTANCE;
                                                            }
                                                        }
                                                        OpaqueKey opaqueKey2 =
                                                                ComposerKt.invocation;
                                                        BrowseActivity browseActivity2 =
                                                                BrowseActivity.this;
                                                        int i2 = BrowseActivity.$r8$clinit;
                                                        browseActivity2.getClass();
                                                        SpaEnvironment spaEnvironment2 =
                                                                SpaEnvironmentFactory
                                                                        .spaEnvironment;
                                                        if (spaEnvironment2 == null) {
                                                            throw new UnsupportedOperationException(
                                                                    "Spa environment is not set");
                                                        }
                                                        SettingsPageProviderRepository
                                                                settingsPageProviderRepository =
                                                                        (SettingsPageProviderRepository)
                                                                                ((SettingsSpaEnvironment)
                                                                                                spaEnvironment2)
                                                                                                .pageProviderRepository
                                                                                                .getValue();
                                                        BrowseActivity browseActivity3 =
                                                                BrowseActivity.this;
                                                        ComposerImpl composerImpl3 =
                                                                (ComposerImpl) composer2;
                                                        composerImpl3.startReplaceGroup(1397364195);
                                                        boolean changed =
                                                                composerImpl3.changed(
                                                                        browseActivity3);
                                                        Object rememberedValue =
                                                                composerImpl3.rememberedValue();
                                                        if (changed
                                                                || rememberedValue
                                                                        == Composer.Companion
                                                                                .Empty) {
                                                            BrowseActivity$onCreate$1$1$1$1
                                                                    browseActivity$onCreate$1$1$1$1 =
                                                                            new BrowseActivity$onCreate$1$1$1$1(
                                                                                    1,
                                                                                    browseActivity3,
                                                                                    BrowseActivity
                                                                                            .class,
                                                                                    "isPageEnabled",
                                                                                    "isPageEnabled(Lcom/android/settingslib/spa/framework/common/SettingsPage;)Z",
                                                                                    0);
                                                            composerImpl3.updateRememberedValue(
                                                                    browseActivity$onCreate$1$1$1$1);
                                                            rememberedValue =
                                                                    browseActivity$onCreate$1$1$1$1;
                                                        }
                                                        composerImpl3.end(false);
                                                        BrowseActivityKt.BrowseContent(
                                                                settingsPageProviderRepository,
                                                                (Function1)
                                                                        ((KFunction)
                                                                                rememberedValue),
                                                                BrowseActivity.this.getIntent(),
                                                                composerImpl3,
                                                                FileType.LOC);
                                                        return Unit.INSTANCE;
                                                    }
                                                },
                                                composer),
                                        composer,
                                        6);
                                return Unit.INSTANCE;
                            }
                        }));
    }
}
