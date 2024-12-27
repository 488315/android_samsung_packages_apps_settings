package com.samsung.android.settings.wifi.warning;

import android.content.DialogInterface;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WepBlockedDialog extends WifiWarningControlDialog {
    public String mSsid;

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final DialogInterface.OnClickListener getClickListener() {
        return new WepBlockedDialog$$ExternalSyntheticLambda1(this, 1);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final String getPositiveButtonText() {
        return this.mContext.getString(R.string.cancel);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setAlertDialog() {
        this.mBuilder.setTitle(
                String.format(
                        this.mContext
                                .getResources()
                                .getString(R.string.wifi_wep_networks_block_dialog_title),
                        this.mSsid));
        if (this.mWifiManager.isWepSupported()) {
            this.mBuilder.setMessage(R.string.wifi_wep_networks_block_dialog_summary);
        } else {
            this.mBuilder.setMessage(R.string.wifi_allow_wep_networks_summary_carrier_not_allow);
        }
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setNegativeButton() {
        this.mBuilder.setOnCancelListener(
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.samsung.android.settings.wifi.warning.WepBlockedDialog$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        WepBlockedDialog wepBlockedDialog = WepBlockedDialog.this;
                        wepBlockedDialog.mActivity.setResult(0);
                        wepBlockedDialog.mActivity.finish();
                    }
                });
        if (this.mWifiManager.isWepSupported()) {
            this.mBuilder.setNegativeButton(
                    R.string.wifi_wep_networks_button_allow_wep,
                    new WepBlockedDialog$$ExternalSyntheticLambda1(this, 0));
        }
    }
}
