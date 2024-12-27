package com.samsung.android.settings.wifi.warning;

import android.content.DialogInterface;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MobileHotspotWithP2pDialog extends WifiWarningControlDialog {
    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final DialogInterface.OnClickListener getClickListener() {
        return new MobileHotspotWithP2pDialog$$ExternalSyntheticLambda1(this, 1);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final String getPositiveButtonText() {
        return this.mContext.getString(R.string.wifi_button_turn_off);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void sendNotifySelectedBroadcast(boolean z) {
        sendBroadcastAsUser("WifiDirect", z);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setAlertDialog() {
        this.mBuilder.setTitle(R.string.wifi_p2p_enable_warning_title);
        this.mBuilder.setMessage(R.string.wifi_p2p_enable_warning_body);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setNegativeButton() {
        this.mBuilder.setOnCancelListener(
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.samsung.android.settings.wifi.warning.MobileHotspotWithP2pDialog$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        MobileHotspotWithP2pDialog mobileHotspotWithP2pDialog =
                                MobileHotspotWithP2pDialog.this;
                        mobileHotspotWithP2pDialog.dismissApDisableDialog();
                        mobileHotspotWithP2pDialog.sendNotifySelectedBroadcast(false);
                        mobileHotspotWithP2pDialog.mActivity.setResult(0);
                        mobileHotspotWithP2pDialog.mActivity.finish();
                    }
                });
        this.mBuilder.setNegativeButton(
                R.string.cancel, new MobileHotspotWithP2pDialog$$ExternalSyntheticLambda1(this, 0));
    }
}
