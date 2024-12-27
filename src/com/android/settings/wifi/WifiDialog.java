package com.android.settings.wifi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.wifi.dpp.WifiDppUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.wifi.AccessPoint;

import com.google.android.setupcompat.util.WizardManagerHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiDialog extends AlertDialog
        implements WifiConfigUiBase, DialogInterface.OnClickListener {
    public final AccessPoint mAccessPoint;
    public WifiConfigController mController;
    public final boolean mHideSubmitButton;
    public final WifiDialogListener mListener;
    public final int mMode;
    public View mView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface WifiDialogListener {}

    public WifiDialog(
            Context context,
            WifiDialogListener wifiDialogListener,
            AccessPoint accessPoint,
            int i,
            boolean z) {
        super(context, i);
        this.mMode = 1;
        this.mListener = wifiDialogListener;
        this.mAccessPoint = accessPoint;
        this.mHideSubmitButton = z;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        WifiDialogListener wifiDialogListener = this.mListener;
        if (wifiDialogListener != null) {
            if (i != -3) {
                if (i != -1) {
                    return;
                }
                ((WifiDialogActivity) wifiDialogListener).onSubmit(this);
                return;
            }
            if (WifiUtils.isNetworkLockedDown(getContext(), this.mAccessPoint.mConfig)) {
                RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                        getContext(), RestrictedLockUtilsInternal.getDeviceOwner(getContext()));
                return;
            }
            WifiDialogActivity wifiDialogActivity = (WifiDialogActivity) this.mListener;
            if (wifiDialogActivity.hasWifiManager()) {
                AccessPoint accessPoint = this.mController.mAccessPoint;
                if (accessPoint != null) {
                    if (accessPoint.isSaved()) {
                        wifiDialogActivity.mWifiManager.forget(accessPoint.mConfig.networkId, null);
                    } else {
                        NetworkInfo networkInfo = accessPoint.mNetworkInfo;
                        if (networkInfo == null
                                || networkInfo.getState() == NetworkInfo.State.DISCONNECTED) {
                            Log.e(
                                    "WifiDialogActivity",
                                    "Failed to forget invalid network " + accessPoint.mConfig);
                        } else {
                            wifiDialogActivity.mWifiManager.disableEphemeralNetwork(
                                    AccessPoint.convertToQuotedString(accessPoint.ssid));
                        }
                    }
                }
                Intent intent = new Intent();
                if (accessPoint != null) {
                    Bundle bundle = new Bundle();
                    accessPoint.saveWifiState(bundle);
                    intent.putExtra("access_point_state", bundle);
                }
                wifiDialogActivity.setResult(2);
                wifiDialogActivity.finish();
            }
        }
    }

    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog,
              // androidx.activity.ComponentDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        Button button;
        View inflate = getLayoutInflater().inflate(R.layout.wifi_dialog, (ViewGroup) null);
        this.mView = inflate;
        setView$1(inflate);
        this.mController =
                new WifiConfigController(this, this.mView, this.mAccessPoint, this.mMode);
        super.onCreate(bundle);
        if (this.mHideSubmitButton) {
            Button button2 = ((WifiDialog) this.mController.mConfigUi).getButton(-1);
            if (button2 != null) {
                button2.setVisibility(8);
            }
        } else {
            this.mController.enableSubmitIfAppropriate();
        }
        if (this.mAccessPoint != null
                || (button = ((WifiDialog) this.mController.mConfigUi).getButton(-3)) == null) {
            return;
        }
        button.setVisibility(8);
    }

    @Override // android.app.Dialog
    public final void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        WifiConfigController wifiConfigController = this.mController;
        ((TextView) wifiConfigController.mView.findViewById(R.id.password))
                .setInputType(
                        (((CheckBox) wifiConfigController.mView.findViewById(R.id.show_password))
                                                .isChecked()
                                        ? 144
                                        : 128)
                                | 1);
    }

    @Override // androidx.activity.ComponentDialog, android.app.Dialog
    public final void onStart() {
        ImageButton imageButton = (ImageButton) findViewById(R.id.ssid_scanner_button);
        if (this.mHideSubmitButton) {
            imageButton.setVisibility(8);
        } else {
            imageButton.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.wifi.WifiDialog$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            WifiDialog wifiDialog = WifiDialog.this;
                            if (wifiDialog.mListener == null) {
                                return;
                            }
                            String charSequence =
                                    ((TextView) wifiDialog.findViewById(R.id.ssid))
                                            .getText()
                                            .toString();
                            WifiDialogActivity wifiDialogActivity =
                                    (WifiDialogActivity) wifiDialog.mListener;
                            wifiDialogActivity.getClass();
                            Intent enrolleeQrCodeScannerIntent =
                                    WifiDppUtils.getEnrolleeQrCodeScannerIntent(
                                            wifiDialog.getContext(), charSequence);
                            WizardManagerHelper.copyWizardManagerExtras(
                                    wifiDialogActivity.mIntent, enrolleeQrCodeScannerIntent);
                            wifiDialogActivity.startActivityForResult(
                                    enrolleeQrCodeScannerIntent, 0);
                        }
                    });
        }
    }
}
