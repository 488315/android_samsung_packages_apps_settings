package com.samsung.android.settings.wifi.warning;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.android.settings.R;

import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiWarningEmptyDialog extends WifiWarningControlDialog {
    public final String mFriendlyName;

    public WifiWarningEmptyDialog(Context context, Activity activity, String str) {
        super(activity, context);
        this.mFriendlyName = str;
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final DialogInterface.OnClickListener getClickListener() {
        return null;
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setAlertDialog() {
        String format =
                String.format(
                        this.mContext.getString(R.string.wifi_progress_updating),
                        this.mFriendlyName);
        if (this.mWifiApTurningOffDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(this.mActivity, this.mDialogTheme);
            if (Rune.isSamsungDexMode(this.mContext)) {
                progressDialog.getWindow().clearFlags(2);
            }
            this.mWifiApTurningOffDialog = progressDialog;
        }
        if (!this.mWifiApTurningOffDialog.isShowing()) {
            this.mWifiApTurningOffDialog.setMessage(format);
            this.mWifiApTurningOffDialog.show();
        }
        this.mTimeoutHandler.sendEmptyMessageDelayed(0, 2000L);
    }

    @Override // com.samsung.android.settings.wifi.warning.WifiWarningControlDialog
    public final void setNegativeButton() {}
}
