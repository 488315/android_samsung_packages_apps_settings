package com.samsung.android.settings.wifi;

import android.content.DialogInterface;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class WifiSetupWizardDialogBuilder$$ExternalSyntheticLambda0
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        switch (this.$r8$classId) {
            case 0:
                Log.d("WifiSetupWizard", "User confirm FRP/KME warning");
                break;
            default:
                Log.d("WifiSetupWizard", "Return to Wi-Fi setup");
                break;
        }
    }
}
