package com.android.settings.development;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AdbWirelessDialogController {
    public final TextView mIpAddr;
    public final TextView mSixDigitCode;
    public final View mView;

    /* JADX WARN: Multi-variable type inference failed */
    public AdbWirelessDialogController(
            AdbWirelessDialogUiBase adbWirelessDialogUiBase, View view, int i) {
        this.mView = view;
        Resources resources = adbWirelessDialogUiBase.getContext().getResources();
        this.mSixDigitCode = (TextView) view.findViewById(R.id.pairing_code);
        this.mIpAddr = (TextView) view.findViewById(R.id.ip_addr);
        if (i == 0) {
            ((AlertDialog) adbWirelessDialogUiBase)
                    .setTitle(resources.getString(R.string.adb_pairing_device_dialog_title));
            view.findViewById(R.id.l_pairing_six_digit).setVisibility(0);
            CharSequence string = resources.getString(R.string.cancel);
            AdbWirelessDialog adbWirelessDialog = (AdbWirelessDialog) adbWirelessDialogUiBase;
            adbWirelessDialog.setButton(-2, string, adbWirelessDialog);
            adbWirelessDialogUiBase.setCanceledOnTouchOutside(false);
        } else if (i == 2) {
            String string2 = resources.getString(R.string.adb_pairing_device_dialog_failed_msg);
            ((AppCompatDialog) adbWirelessDialogUiBase)
                    .setTitle(R.string.adb_pairing_device_dialog_failed_title);
            view.findViewById(R.id.l_pairing_failed).setVisibility(0);
            ((TextView) view.findViewById(R.id.pairing_failed_label)).setText(string2);
            AdbWirelessDialog adbWirelessDialog2 = (AdbWirelessDialog) adbWirelessDialogUiBase;
            adbWirelessDialog2.setButton(
                    -1, resources.getString(R.string.okay), adbWirelessDialog2);
        } else if (i == 3) {
            ((AppCompatDialog) adbWirelessDialogUiBase)
                    .setTitle(R.string.adb_pairing_device_dialog_failed_title);
            view.findViewById(R.id.l_qrcode_pairing_failed).setVisibility(0);
            AdbWirelessDialog adbWirelessDialog3 = (AdbWirelessDialog) adbWirelessDialogUiBase;
            adbWirelessDialog3.setButton(
                    -1, resources.getString(R.string.okay), adbWirelessDialog3);
        }
        view.findViewById(R.id.l_adbwirelessdialog).requestFocus();
    }
}
