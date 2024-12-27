package com.samsung.android.settings.wifi;

import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.samsung.android.settings.logging.SALogging;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */
class WifiSetupWizardActivity$DataOnButtonUpdater$$ExternalSyntheticLambda0
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ WifiSetupWizardActivity$DataOnButtonUpdater$$ExternalSyntheticLambda0(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        WifiSetupWizardActivity.DataOnButtonUpdater dataOnButtonUpdater =
                (WifiSetupWizardActivity.DataOnButtonUpdater) this.f$0;
        switch (i) {
            case 0:
                WifiSetupWizardActivity wifiSetupWizardActivity = dataOnButtonUpdater.this$0;
                Settings.Global.putInt(
                        wifiSetupWizardActivity.getApplicationContext().getContentResolver(),
                        "device_provisioning_mobile_data",
                        1);
                Settings.Global.putInt(
                        wifiSetupWizardActivity.getApplicationContext().getContentResolver(),
                        "mobile_data",
                        1);
                ((TextView) dataOnButtonUpdater.mTextView).setVisibility(4);
                break;
            default:
                dataOnButtonUpdater.setEnabled(false);
                WifiSetupWizardActivity wifiSetupWizardActivity2 = dataOnButtonUpdater.this$0;
                boolean isWifiEnabled = wifiSetupWizardActivity2.mWifiManager.isWifiEnabled();
                wifiSetupWizardActivity2.mWifiManager.setWifiEnabled(!isWifiEnabled);
                SALogging.insertSALog(
                        "WIFI_230", "0102", isWifiEnabled ? DATA.DM_FIELD_INDEX.PCSCF_DOMAIN : "1");
                break;
        }
    }
}
