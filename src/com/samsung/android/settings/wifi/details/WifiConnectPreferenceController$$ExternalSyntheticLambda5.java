package com.samsung.android.settings.wifi.details;

import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class WifiConnectPreferenceController$$ExternalSyntheticLambda5
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiConnectPreferenceController f$0;

    public /* synthetic */ WifiConnectPreferenceController$$ExternalSyntheticLambda5(
            WifiConnectPreferenceController wifiConnectPreferenceController, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiConnectPreferenceController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        WifiConnectPreferenceController wifiConnectPreferenceController = this.f$0;
        switch (i) {
            case 0:
                wifiConnectPreferenceController.save(false, false);
                break;
            case 1:
                wifiConnectPreferenceController.finishActivity$2();
                break;
            default:
                boolean z = WifiConnectPreferenceController.DBG;
                wifiConnectPreferenceController.insertSaLoggingForPasswordShare("rejected");
                WifiConnectPreferenceController.Timer timer =
                        wifiConnectPreferenceController.mTimer;
                timer.getClass();
                Log.d("WifiConnectPrefController", "Stop share timer");
                timer.removeMessages(3);
                wifiConnectPreferenceController.setProfileSharingMode(false);
                break;
        }
    }
}
