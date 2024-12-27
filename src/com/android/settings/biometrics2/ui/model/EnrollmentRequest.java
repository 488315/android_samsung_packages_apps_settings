package com.android.settings.biometrics2.ui.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.settings.SetupWizardUtils;

import com.google.android.setupcompat.util.WizardManagerHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnrollmentRequest {
    public final Bundle _suwExtras;
    public final boolean isAfterSuwOrSuwSuggestedAction;
    public final boolean isSkipFindSensor;
    public final boolean isSkipIntro;
    public final boolean isSuw;
    public final int theme;

    public EnrollmentRequest(Context context, Intent intent, boolean z) {
        boolean z2 = true;
        boolean z3 = z && intent.getBooleanExtra("isSetupFlow", false);
        this.isSuw = z3;
        if (!z
                || (!intent.getBooleanExtra("deferredSetup", false)
                        && !intent.getBooleanExtra("portalSetup", false)
                        && !intent.getBooleanExtra("isSuwSuggestedActionFlow", false))) {
            z2 = false;
        }
        this.isAfterSuwOrSuwSuggestedAction = z2;
        Intent intent2 = new Intent();
        if (z3) {
            WizardManagerHelper.copyWizardManagerExtras(intent, intent2);
        }
        Bundle extras = intent2.getExtras();
        this._suwExtras = extras == null ? new Bundle() : extras;
        this.isSkipIntro = intent.getBooleanExtra("skip_intro", false);
        this.isSkipFindSensor = intent.getBooleanExtra("skip_find_sensor", false);
        this.theme = SetupWizardUtils.getTheme(context, intent);
    }

    public final String toString() {
        return "EnrollmentRequest:{isSuw:"
                + this.isSuw
                + ", isAfterSuwOrSuwSuggestedAction:"
                + this.isAfterSuwOrSuwSuggestedAction
                + "}";
    }
}
