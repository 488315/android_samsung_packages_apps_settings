package com.android.settings.accessibility;

import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AccessibilitySetupWizardUtils$$ExternalSyntheticLambda0
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Runnable f$0;

    public /* synthetic */ AccessibilitySetupWizardUtils$$ExternalSyntheticLambda0(
            TextReadingPreferenceFragmentForSetupWizard$$ExternalSyntheticLambda0
                    textReadingPreferenceFragmentForSetupWizard$$ExternalSyntheticLambda0) {
        this.f$0 = textReadingPreferenceFragmentForSetupWizard$$ExternalSyntheticLambda0;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        Runnable runnable = this.f$0;
        switch (i) {
            case 0:
                runnable.run();
                break;
            default:
                runnable.run();
                break;
        }
    }

    public /* synthetic */ AccessibilitySetupWizardUtils$$ExternalSyntheticLambda0(
            Runnable runnable) {
        this.f$0 = runnable;
    }
}
