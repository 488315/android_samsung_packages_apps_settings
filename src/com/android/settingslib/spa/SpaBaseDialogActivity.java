package com.android.settingslib.spa;

import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.activity.compose.ComponentActivityKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;

import com.android.settings.spa.SettingsSpaEnvironment;
import com.android.settingslib.spa.framework.common.LogCategory;
import com.android.settingslib.spa.framework.common.SpaEnvironment;
import com.android.settingslib.spa.framework.common.SpaEnvironmentFactory;
import com.android.settingslib.spa.framework.theme.SettingsThemeKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SpaBaseDialogActivity extends ComponentActivity {
    public abstract void Content(Composer composer, int i);

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SpaEnvironment spaEnvironment = SpaEnvironmentFactory.spaEnvironment;
        if (spaEnvironment == null) {
            throw new UnsupportedOperationException("Spa environment is not set");
        }
        LogCategory logCategory = LogCategory.FRAMEWORK;
        ((SettingsSpaEnvironment) spaEnvironment).logger.getClass();
        ComponentActivityKt.setContent$default(
                this,
                new ComposableLambdaImpl(
                        902662360,
                        true,
                        new Function2() { // from class:
                                          // com.android.settingslib.spa.SpaBaseDialogActivity$onCreate$1
                            {
                                super(2);
                            }

                            /* JADX WARN: Type inference failed for: r3v5, types: [com.android.settingslib.spa.SpaBaseDialogActivity$onCreate$1$1, kotlin.jvm.internal.Lambda] */
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
                                final SpaBaseDialogActivity spaBaseDialogActivity =
                                        SpaBaseDialogActivity.this;
                                SettingsThemeKt.SettingsTheme(
                                        ComposableLambdaKt.rememberComposableLambda(
                                                652429373,
                                                new Function2() { // from class:
                                                                  // com.android.settingslib.spa.SpaBaseDialogActivity$onCreate$1.1
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
                                                        SpaBaseDialogActivity.this.Content(
                                                                composer2, 0);
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
