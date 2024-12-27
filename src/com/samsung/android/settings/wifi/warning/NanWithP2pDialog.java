package com.samsung.android.settings.wifi.warning;

import android.content.DialogInterface;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class NanWithP2pDialog extends WifiWarningControlDialog {
    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final DialogInterface.OnClickListener getClickListener() {
        return new NanWithP2pDialog$$ExternalSyntheticLambda0(this, 0);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final String getPositiveButtonText() {
        return this.mContext.getString(R.string.wifi_ap_button_stop);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void sendNotifySelectedBroadcast(boolean z) {
        sendBroadcastAsUser("WifiDirect", z);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setAlertDialog() {
        this.mBuilder.setTitle(R.string.wifi_aware_enable_p2p_warning_title);
        this.mBuilder.setMessage(R.string.wifi_aware_enable_p2p_warning_body);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setNegativeButton() {
        this.mBuilder.setOnCancelListener(
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.samsung.android.settings.wifi.warning.NanWithP2pDialog$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        NanWithP2pDialog nanWithP2pDialog = NanWithP2pDialog.this;
                        nanWithP2pDialog.dismissApDisableDialog();
                        nanWithP2pDialog.sendEnableNanBroadcast(false);
                        nanWithP2pDialog.sendNotifySelectedBroadcast(false);
                        nanWithP2pDialog.mActivity.setResult(0);
                        nanWithP2pDialog.mActivity.finish();
                    }
                });
        this.mBuilder.setNegativeButton(
                R.string.cancel, new NanWithP2pDialog$$ExternalSyntheticLambda0(this, 1));
    }
}
