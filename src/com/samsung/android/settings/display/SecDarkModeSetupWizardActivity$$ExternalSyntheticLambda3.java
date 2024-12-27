package com.samsung.android.settings.display;

import android.content.ContentResolver;
import android.content.pm.Signature;
import android.provider.Settings;
import android.view.View;

import androidx.core.util.Consumer;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecDarkModeSetupWizardActivity$$ExternalSyntheticLambda3
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ SecDarkModeSetupWizardActivity.UiMode f$1;

    public /* synthetic */ SecDarkModeSetupWizardActivity$$ExternalSyntheticLambda3(
            Object obj, SecDarkModeSetupWizardActivity.UiMode uiMode, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = uiMode;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                SecDarkModeSetupWizardActivity secDarkModeSetupWizardActivity =
                        (SecDarkModeSetupWizardActivity) this.f$0;
                SecDarkModeSetupWizardActivity.UiMode uiMode = this.f$1;
                int i = SecDarkModeSetupWizardActivity.$r8$clinit;
                secDarkModeSetupWizardActivity.setResult(-1);
                int i2 = uiMode == SecDarkModeSetupWizardActivity.UiMode.DARK ? 1 : 0;
                Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
                String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
                SALogging.insertSALog(i2, "SUW_74470", String.valueOf(74470), (String) null);
                ContentResolver contentResolver =
                        secDarkModeSetupWizardActivity.getContentResolver();
                if (contentResolver != null) {
                    Settings.Global.putInt(contentResolver, "ui_night_mode_on_suw", i2);
                }
                secDarkModeSetupWizardActivity.finish();
                break;
            default:
                ((Consumer) this.f$0).accept(this.f$1);
                break;
        }
    }
}
