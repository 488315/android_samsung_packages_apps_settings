package com.android.settings.gestures;

import android.content.DialogInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SystemNavigationGestureSettings$$ExternalSyntheticLambda1
        implements DialogInterface.OnDismissListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SystemNavigationGestureSettings f$0;

    public /* synthetic */ SystemNavigationGestureSettings$$ExternalSyntheticLambda1(
            SystemNavigationGestureSettings systemNavigationGestureSettings, int i) {
        this.$r8$classId = i;
        this.f$0 = systemNavigationGestureSettings;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        int i = this.$r8$classId;
        SystemNavigationGestureSettings systemNavigationGestureSettings = this.f$0;
        switch (i) {
            case 0:
                systemNavigationGestureSettings.mA11yTutorialDialogShown = false;
                break;
            default:
                systemNavigationGestureSettings.mA11yTutorialDialogShown = false;
                break;
        }
    }
}
