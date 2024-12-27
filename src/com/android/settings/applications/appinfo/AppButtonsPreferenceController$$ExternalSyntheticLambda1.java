package com.android.settings.applications.appinfo;

import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AppButtonsPreferenceController$$ExternalSyntheticLambda1
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppButtonsPreferenceController f$0;

    public /* synthetic */ AppButtonsPreferenceController$$ExternalSyntheticLambda1(
            AppButtonsPreferenceController appButtonsPreferenceController, int i) {
        this.$r8$classId = i;
        this.f$0 = appButtonsPreferenceController;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        AppButtonsPreferenceController appButtonsPreferenceController = this.f$0;
        switch (i) {
            case 0:
                appButtonsPreferenceController.lambda$initBottomButtonsLayout$3(view);
                break;
            default:
                appButtonsPreferenceController.lambda$initButtonPreference$2(view);
                break;
        }
    }
}
