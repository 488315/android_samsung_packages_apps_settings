package com.samsung.android.settings.wifi.warning;

import android.content.DialogInterface;
import android.net.wifi.WifiInfo;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WepDisconnectDialog extends WifiWarningControlDialog {
    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final DialogInterface.OnClickListener getClickListener() {
        return new WepDisconnectDialog$$ExternalSyntheticLambda0(this, 0);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final String getPositiveButtonText() {
        return this.mContext.getString(R.string.wifi_wep_networks_button_disconnect);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setAlertDialog() {
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        if (connectionInfo != null) {
            this.mBuilder.setTitle(
                    String.format(
                            this.mContext
                                    .getResources()
                                    .getString(R.string.wifi_wep_networks_disconnect_dialog_title),
                            connectionInfo.getSSID()));
            this.mBuilder.setMessage(R.string.wifi_wep_networks_disconnect_dialog_summary);
        }
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setNegativeButton() {
        this.mBuilder.setOnCancelListener(
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.samsung.android.settings.wifi.warning.WepDisconnectDialog$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        WepDisconnectDialog wepDisconnectDialog = WepDisconnectDialog.this;
                        wepDisconnectDialog.mActivity.setResult(0);
                        wepDisconnectDialog.mActivity.finish();
                    }
                });
        this.mBuilder.setNegativeButton(
                R.string.cancel, new WepDisconnectDialog$$ExternalSyntheticLambda0(this, 1));
    }
}
