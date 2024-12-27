package com.android.settingslib.spa;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.platform.ComposeView;

import com.android.settings.network.WepNetworkDialogActivity;
import com.android.settings.spa.SettingsSpaEnvironment;
import com.android.settingslib.spa.framework.common.LogCategory;
import com.android.settingslib.spa.framework.common.SpaEnvironment;
import com.android.settingslib.spa.framework.common.SpaEnvironmentFactory;
import com.android.settingslib.spa.framework.theme.SettingsThemeKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SpaDialogWindowTypeActivity extends ComponentActivity {
    public AlertDialogWithType dialog;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AlertDialogWithType extends AlertDialog {
        public final /* synthetic */ SpaDialogWindowTypeActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AlertDialogWithType(
                final SpaDialogWindowTypeActivity spaDialogWindowTypeActivity, Context context) {
            super(context, 2132084379);
            Intrinsics.checkNotNullParameter(context, "context");
            this.this$0 = spaDialogWindowTypeActivity;
            ComposeView composeView = new ComposeView(context, null, 0, 6, null);
            composeView.setContent(
                    new ComposableLambdaImpl(
                            959257001,
                            true,
                            new Function2() { // from class:
                                              // com.android.settingslib.spa.SpaDialogWindowTypeActivity$AlertDialogWithType$1$1
                                {
                                    super(2);
                                }

                                /* JADX WARN: Type inference failed for: r3v5, types: [com.android.settingslib.spa.SpaDialogWindowTypeActivity$AlertDialogWithType$1$1$1, kotlin.jvm.internal.Lambda] */
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
                                    final SpaDialogWindowTypeActivity spaDialogWindowTypeActivity2 =
                                            SpaDialogWindowTypeActivity.this;
                                    SettingsThemeKt.SettingsTheme(
                                            ComposableLambdaKt.rememberComposableLambda(
                                                    1428239908,
                                                    new Function2() { // from class:
                                                                      // com.android.settingslib.spa.SpaDialogWindowTypeActivity$AlertDialogWithType$1$1.1
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
                                                            SpaDialogWindowTypeActivity.this
                                                                    .Content(composer2, 8);
                                                            return Unit.INSTANCE;
                                                        }
                                                    },
                                                    composer),
                                            composer,
                                            6);
                                    return Unit.INSTANCE;
                                }
                            }));
            setView$1(composeView);
            setOnDismissListener(
                    new DialogInterface
                            .OnDismissListener() { // from class:
                                                   // com.android.settingslib.spa.SpaDialogWindowTypeActivity.AlertDialogWithType.2
                        @Override // android.content.DialogInterface.OnDismissListener
                        public final void onDismiss(DialogInterface dialogInterface) {
                            SpaDialogWindowTypeActivity.this.finish();
                        }
                    });
        }

        @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog,
                  // androidx.activity.ComponentDialog, android.app.Dialog
        public final void onCreate(Bundle bundle) {
            int intValue =
                    Integer.valueOf(
                                    ((WepNetworkDialogActivity) this.this$0)
                                            .getIntent()
                                            .getIntExtra("dialog_window_type", 1))
                            .intValue();
            Window window = getWindow();
            if (window != null) {
                window.setType(intValue);
            }
            super.onCreate(bundle);
        }
    }

    public abstract void Content(Composer composer, int i);

    @Override // android.app.Activity
    public final void finish() {
        AlertDialogWithType alertDialogWithType = this.dialog;
        if (alertDialogWithType != null) {
            alertDialogWithType.dismiss();
        }
        super.finish();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SpaEnvironment spaEnvironment = SpaEnvironmentFactory.spaEnvironment;
        if (spaEnvironment == null) {
            throw new UnsupportedOperationException("Spa environment is not set");
        }
        LogCategory logCategory = LogCategory.FRAMEWORK;
        ((SettingsSpaEnvironment) spaEnvironment).logger.getClass();
        AlertDialogWithType alertDialogWithType = new AlertDialogWithType(this, this);
        alertDialogWithType.show();
        this.dialog = alertDialogWithType;
    }
}
