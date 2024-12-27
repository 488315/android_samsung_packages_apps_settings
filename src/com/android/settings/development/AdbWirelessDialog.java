package com.android.settings.development;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AdbWirelessDialog extends AlertDialog
        implements AdbWirelessDialogUiBase, DialogInterface.OnClickListener {
    public AdbWirelessDialogController mController;
    public final WirelessDebuggingFragment.PairingCodeDialogListener mListener;
    public final int mMode;
    public View mView;

    public AdbWirelessDialog(
            FragmentActivity fragmentActivity,
            WirelessDebuggingFragment.PairingCodeDialogListener pairingCodeDialogListener,
            int i) {
        super(fragmentActivity, 0);
        this.mListener = pairingCodeDialogListener;
        this.mMode = i;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        WirelessDebuggingFragment.PairingCodeDialogListener pairingCodeDialogListener =
                this.mListener;
        if (pairingCodeDialogListener == null || i != -2) {
            return;
        }
        pairingCodeDialogListener.getClass();
    }

    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog,
              // androidx.activity.ComponentDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        View inflate = getLayoutInflater().inflate(R.layout.adb_wireless_dialog, (ViewGroup) null);
        this.mView = inflate;
        setView$1(inflate);
        this.mController = new AdbWirelessDialogController(this, this.mView, this.mMode);
        super.onCreate(bundle);
    }

    @Override // androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog,
              // android.app.Dialog
    public final void onStop() {
        super.onStop();
        dismiss();
        WirelessDebuggingFragment.PairingCodeDialogListener pairingCodeDialogListener =
                this.mListener;
        if (pairingCodeDialogListener != null) {
            pairingCodeDialogListener.getClass();
            Log.i("WirelessDebuggingFrag", "onDismiss");
            WirelessDebuggingFragment wirelessDebuggingFragment = WirelessDebuggingFragment.this;
            wirelessDebuggingFragment.mPairingCodeDialog = null;
            try {
                wirelessDebuggingFragment.mAdbManager.disablePairing();
            } catch (RemoteException unused) {
                Log.e("WirelessDebuggingFrag", "Unable to cancel pairing");
            }
        }
    }
}
