package com.samsung.android.settings.wifi.warning;

import android.R;
import android.content.DialogInterface;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.samsung.android.settings.Rune;
import com.samsung.android.wifitrackerlib.SemWifiUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MobileHotspotWithoutP2pDialog extends WifiWarningControlDialog {
    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final DialogInterface.OnClickListener getClickListener() {
        return new MobileHotspotWithoutP2pDialog$$ExternalSyntheticLambda1(this, 1);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final String getPositiveButtonText() {
        return this.mContext.getString(R.string.ok);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final boolean handleWifiApStateChanged(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "Received MHS state changed, ", "WifiWarningControlDialog");
        if (i != 11 || this.mWifiManager == null) {
            return false;
        }
        this.mActivity.setResult(-1);
        dismissProgressDialog();
        if ("VZW".equals(SemWifiUtils.readSalesCode())) {
            Toast.makeText(this.mContext, com.android.settings.R.string.mobile_ap_disable, 0)
                    .show();
        }
        if (!this.mIsAirplaneMode) {
            Settings.Secure.putInt(
                    this.mContext.getContentResolver(), "wifiap_wifi_tile_clicked", 1);
            this.mWifiManager.setWifiEnabled(true);
        }
        return true;
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setAlertDialog() {
        if (Rune.isJapanModel()) {
            this.mBuilder.setTitle(com.android.settings.R.string.wifi_enable_warning_title_jpn);
            this.mBuilder.setMessage(com.android.settings.R.string.wifi_enable_warning_jpn);
        } else {
            this.mBuilder.setTitle(com.android.settings.R.string.wifi_enable_warning_title);
            this.mBuilder.setMessage(com.android.settings.R.string.wifi_enable_warning);
        }
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setNegativeButton() {
        this.mBuilder.setOnCancelListener(
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.samsung.android.settings.wifi.warning.MobileHotspotWithoutP2pDialog$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        MobileHotspotWithoutP2pDialog mobileHotspotWithoutP2pDialog =
                                MobileHotspotWithoutP2pDialog.this;
                        mobileHotspotWithoutP2pDialog.dismissApDisableDialog();
                        mobileHotspotWithoutP2pDialog.sendBroadcastCancelEnablingWifi();
                        mobileHotspotWithoutP2pDialog.mActivity.setResult(0);
                        mobileHotspotWithoutP2pDialog.mActivity.finish();
                    }
                });
        this.mBuilder.setNegativeButton(
                R.string.cancel,
                new MobileHotspotWithoutP2pDialog$$ExternalSyntheticLambda1(this, 0));
    }
}
