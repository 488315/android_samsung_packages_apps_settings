package com.samsung.android.settings.wifi;

import android.content.DialogInterface;

import com.android.wifitrackerlib.WifiEntry;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class WifiSetupWizardActivityVzw$$ExternalSyntheticLambda2
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiSetupWizardActivityVzw f$0;

    public /* synthetic */ WifiSetupWizardActivityVzw$$ExternalSyntheticLambda2(
            WifiSetupWizardActivityVzw wifiSetupWizardActivityVzw, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiSetupWizardActivityVzw;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        WifiSetupWizardActivityVzw wifiSetupWizardActivityVzw = this.f$0;
        switch (i2) {
            case 0:
                int i3 = WifiSetupWizardActivityVzw.$r8$clinit;
                wifiSetupWizardActivityVzw.forgetCurrentNetwork$1();
                dialogInterface.dismiss();
                break;
            case 1:
                WifiEntry wifiEntry = wifiSetupWizardActivityVzw.mWifiEntry;
                if (wifiEntry != null && wifiEntry.canSignIn()) {
                    wifiSetupWizardActivityVzw.mWifiEntry.signIn();
                }
                dialogInterface.dismiss();
                break;
            case 2:
                int i4 = WifiSetupWizardActivityVzw.$r8$clinit;
                wifiSetupWizardActivityVzw.forgetCurrentNetwork$1();
                dialogInterface.dismiss();
                break;
            case 3:
                int i5 = WifiSetupWizardActivityVzw.$r8$clinit;
                wifiSetupWizardActivityVzw.getClass();
                dialogInterface.dismiss();
                wifiSetupWizardActivityVzw.setFinishAction();
                break;
            case 4:
                int i6 = WifiSetupWizardActivityVzw.$r8$clinit;
                wifiSetupWizardActivityVzw.getClass();
                dialogInterface.dismiss();
                wifiSetupWizardActivityVzw.setFinishAction();
                break;
            default:
                int i7 = WifiSetupWizardActivityVzw.$r8$clinit;
                wifiSetupWizardActivityVzw.getClass();
                dialogInterface.dismiss();
                wifiSetupWizardActivityVzw.setFinishAction();
                break;
        }
    }
}
