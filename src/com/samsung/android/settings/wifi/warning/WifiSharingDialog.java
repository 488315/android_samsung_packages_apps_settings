package com.samsung.android.settings.wifi.warning;

import android.content.DialogInterface;

import com.android.settings.R;

import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiSharingDialog extends WifiWarningControlDialog {
    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final DialogInterface.OnClickListener getClickListener() {
        return new WifiSharingDialog$$ExternalSyntheticLambda0(this, 0);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setAlertDialog() {
        this.mBuilder.setTitle(
                Rune.isJapanModel()
                        ? R.string.wifi_ap_wifi_sharinglite_tethering_on_text
                        : R.string.wifi_ap_wifi_sharinglite_mobile_hotspot_on_text);
        this.mBuilder.setMessage(R.string.wifi_ap_wifi_sharinglite_wifiwarning_text);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setNegativeButton() {
        this.mBuilder.setOnCancelListener(
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.samsung.android.settings.wifi.warning.WifiSharingDialog$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        WifiSharingDialog wifiSharingDialog = WifiSharingDialog.this;
                        boolean z = wifiSharingDialog.mHasSentBroadcast;
                        wifiSharingDialog.mActivity.finish();
                    }
                });
        this.mBuilder.setNegativeButton(
                R.string.settings_label, new WifiSharingDialog$$ExternalSyntheticLambda0(this, 1));
    }
}
