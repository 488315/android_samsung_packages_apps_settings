package com.samsung.android.settings.wifi.warning;

import android.content.DialogInterface;
import android.util.Log;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MobileHotspotWithNanDialog extends WifiWarningControlDialog {
    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final DialogInterface.OnClickListener getClickListener() {
        return new MobileHotspotWithNanDialog$$ExternalSyntheticLambda1(this, 1);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final String getPositiveButtonText() {
        return this.mContext.getString(R.string.wifi_button_turn_off);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final boolean handleWifiApStateChanged(int i) {
        Log.d("WifiWarningControlDialog", "Received MHS state changed, " + i);
        sendEnableNanBroadcast(true);
        this.mActivity.setResult(-1);
        dismissProgressDialog();
        return true;
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void sendNotifySelectedBroadcast(boolean z) {
        sendBroadcastAsUser("WifiAware", z);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setAlertDialog() {
        this.mBuilder.setTitle(R.string.wifi_hotspot_enable_aware_warning_title);
        this.mBuilder.setMessage(R.string.wifi_hotspot_enable_aware_warning_body);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setNegativeButton() {
        this.mBuilder.setOnCancelListener(
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.samsung.android.settings.wifi.warning.MobileHotspotWithNanDialog$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        MobileHotspotWithNanDialog mobileHotspotWithNanDialog =
                                MobileHotspotWithNanDialog.this;
                        mobileHotspotWithNanDialog.dismissApDisableDialog();
                        mobileHotspotWithNanDialog.sendEnableNanBroadcast(false);
                        mobileHotspotWithNanDialog.sendNotifySelectedBroadcast(false);
                        mobileHotspotWithNanDialog.mActivity.setResult(0);
                        mobileHotspotWithNanDialog.mActivity.finish();
                    }
                });
        this.mBuilder.setNegativeButton(
                R.string.cancel, new MobileHotspotWithNanDialog$$ExternalSyntheticLambda1(this, 0));
    }
}
