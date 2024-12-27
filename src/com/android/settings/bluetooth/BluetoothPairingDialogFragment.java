package com.android.settings.bluetooth;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import com.samsung.android.settings.logging.SALogging;

import java.util.UnknownFormatConversionException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothPairingDialogFragment extends InstrumentedDialogFragment
        implements TextWatcher, DialogInterface.OnClickListener, DialogInterface.OnKeyListener {
    public AlertDialog.Builder mBuilder;
    public AlertDialog mDialog;
    public BluetoothPairingController mPairingController;
    public BluetoothPairingDialog mPairingDialogActivity;
    public EditText mPairingView;
    public TextView mPairingViewErrorText;
    public Space mPairingViewMargin;
    public String mTempUserInput = null;

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null) {
            Button button = alertDialog.getButton(-1);
            if (button != null) {
                boolean z = false;
                boolean z2 = this.mPairingController.mType == 7;
                if ((editable.length() >= 16 && z2) || (editable.length() > 0 && !z2)) {
                    z = true;
                }
                button.setEnabled(z);
            }
            this.mPairingController.mUserInput = editable.toString();
        }
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null || charSequence.toString().length() >= 17) {
            return;
        }
        this.mTempUserInput = charSequence.toString();
    }

    public final AlertDialog createConfirmationDialog() {
        StringBuilder sb =
                new StringBuilder("createConfirmationDialog: isBondedInitiatedLocally = ");
        sb.append(this.mPairingController.mIsBondedInitiatedLocally);
        sb.append(", settingIsForegroundActivity = ");
        sb.append(this.mPairingController.mIsSettingsForeground);
        sb.append(", getBlockState = ");
        Preference$$ExternalSyntheticOutline0.m(
                sb, this.mPairingController.mBlockState, "BTPairingDialogFragment");
        this.mBuilder.P.mTitle = getString(R.string.bluetooth_pairing_request);
        this.mBuilder.setView(createView$1());
        this.mBuilder.setPositiveButton(getString(R.string.sec_bluetooth_pairingdialog_pair), this);
        BluetoothPairingController bluetoothPairingController = this.mPairingController;
        if (bluetoothPairingController.mIsBondedInitiatedLocally
                || bluetoothPairingController.mIsSettingsForeground
                || bluetoothPairingController.mBlockState != 1) {
            this.mBuilder.setNegativeButton(getString(R.string.common_cancel), this);
        } else {
            this.mBuilder.setNegativeButton(
                    getString(R.string.sec_bluetooth_pairing_dialog_block), this);
            AlertDialog.Builder builder = this.mBuilder;
            String string = getString(R.string.common_cancel);
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mNeutralButtonText = string;
            alertParams.mNeutralButtonListener = this;
        }
        return this.mBuilder.create();
    }

    public final View createView$1() {
        char c;
        String str = null;
        View inflate =
                getActivity()
                        .getLayoutInflater()
                        .inflate(R.layout.bluetooth_pin_confirm, (ViewGroup) null);
        String str2 = "\u200e" + Html.escapeHtml(this.mPairingController.mDeviceName) + "\u200e";
        TextView textView = (TextView) inflate.findViewById(R.id.message);
        TextView textView2 = (TextView) inflate.findViewById(R.id.pincode);
        switch (this.mPairingController.mType) {
            case 0:
            case 1:
            case 7:
                c = 0;
                break;
            case 2:
                c = 1;
                break;
            case 3:
            case 6:
                c = 3;
                break;
            case 4:
            case 5:
                c = 2;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 1) {
            try {
                String string =
                        getString(
                                R.string.bluetooth_confirm_passkey_msg,
                                Html.fromHtml(str2),
                                Html.fromHtml(str2));
                textView2.setText(this.mPairingController.getPairingContent());
                BluetoothPairingController bluetoothPairingController = this.mPairingController;
                if (bluetoothPairingController.mIsBondedInitiatedLocally
                        || bluetoothPairingController.mIsSettingsForeground
                        || bluetoothPairingController.mBlockState != 1) {
                    str = string;
                } else {
                    str = (string + "\n\n") + getString(R.string.bluetooth_pairing_block_msg);
                }
            } catch (UnknownFormatConversionException unused) {
                str =
                        this.getString(
                                R.string.bluetooth_confirm_passkey_msg,
                                this.mPairingController.getPairingContent(),
                                "Unknown Device");
            }
        } else if (c == 2) {
            str =
                    getString(
                            R.string.bluetooth_display_passkey_pin_msg,
                            Html.fromHtml(str2),
                            Html.fromHtml(str2));
            textView2.setText(this.mPairingController.getPairingContent());
        } else if (c == 3) {
            str = getString(R.string.bluetooth_consent_pairing_msg, Html.fromHtml(str2));
            textView2.setVisibility(8);
            BluetoothPairingController bluetoothPairingController2 = this.mPairingController;
            if (!bluetoothPairingController2.mIsBondedInitiatedLocally
                    && !bluetoothPairingController2.mIsSettingsForeground
                    && bluetoothPairingController2.mBlockState == 1) {
                StringBuilder m =
                        EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                                AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "\n\n"));
                m.append(getString(R.string.bluetooth_pairing_block_msg));
                str = m.toString();
            }
        }
        if (str != null) {
            textView.setText(str);
        } else {
            textView.setVisibility(8);
        }
        inflate.setFocusable(false);
        return inflate;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 613;
    }

    public CharSequence getPairingViewText() {
        EditText editText = this.mPairingView;
        if (editText != null) {
            return editText.getText();
        }
        return null;
    }

    public final void hideSoftInput() {
        if (this.mPairingView == null) {
            return;
        }
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.mPairingDialogActivity.getSystemService("input_method");
        if (inputMethodManager.isActive(this.mPairingView)) {
            inputMethodManager.hideSoftInputFromWindow(this.mPairingView.getWindowToken(), 0);
        }
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        Log.d("BTPairingDialogFragment", "onCancel ::");
        hideSoftInput();
        BluetoothPairingController bluetoothPairingController = this.mPairingController;
        if (!bluetoothPairingController.mIsBondedInitiatedLocally
                && !bluetoothPairingController.mIsSettingsForeground) {
            bluetoothPairingController.updateBlockingDevice(false);
        }
        this.mPairingController.onCancel();
        this.mPairingDialogActivity.dismiss();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        StringBuilder m =
                ListPopupWindow$$ExternalSyntheticOutline0.m(
                        i, "onClick :: which = ", ", isBondedInitiatedLocally = ");
        m.append(this.mPairingController.mIsBondedInitiatedLocally);
        m.append(", settingIsForegroundActivity = ");
        m.append(this.mPairingController.mIsSettingsForeground);
        m.append(", getBlockState = ");
        Preference$$ExternalSyntheticOutline0.m(
                m, this.mPairingController.mBlockState, "BTPairingDialogFragment");
        char c = 0;
        String str = null;
        if (i == -1) {
            BluetoothPairingController bluetoothPairingController = this.mPairingController;
            switch (bluetoothPairingController.mType) {
                case 0:
                case 1:
                case 7:
                    bluetoothPairingController.onPair(bluetoothPairingController.mUserInput);
                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                default:
                    bluetoothPairingController.onPair(null);
                    break;
            }
            bluetoothPairingController.mIsAcceptPair = true;
        } else if (i == -3) {
            BluetoothPairingController bluetoothPairingController2 = this.mPairingController;
            if (!bluetoothPairingController2.mIsBondedInitiatedLocally
                    && !bluetoothPairingController2.mIsSettingsForeground
                    && bluetoothPairingController2.mBlockState == 1) {
                bluetoothPairingController2.updateBlockingDevice(false);
            }
            this.mPairingController.onCancel();
        } else if (i == -2) {
            BluetoothPairingController bluetoothPairingController3 = this.mPairingController;
            if (!bluetoothPairingController3.mIsBondedInitiatedLocally
                    && !bluetoothPairingController3.mIsSettingsForeground) {
                if (bluetoothPairingController3.mBlockState == 1) {
                    bluetoothPairingController3.updateBlockingDevice(true);
                } else {
                    bluetoothPairingController3.updateBlockingDevice(false);
                }
            }
            this.mPairingController.onCancel();
        }
        switch (this.mPairingController.mType) {
            case 0:
            case 1:
            case 7:
                break;
            case 2:
                c = 1;
                break;
            case 3:
            case 6:
                c = 3;
                break;
            case 4:
            case 5:
                c = 2;
                break;
            default:
                c = 65535;
                break;
        }
        if (c != 65535) {
            String string = getString(R.string.screen_request_dialog);
            String string2 =
                    c != 0
                            ? c != 1
                                    ? c != 2
                                            ? c != 3
                                                    ? null
                                                    : getString(
                                                            R.string
                                                                    .event_request_dialog_pairing_consent)
                                            : getString(
                                                    R.string.event_request_dialog_pairing_display)
                                    : getString(R.string.event_request_dialog_pairing_passkey)
                            : getString(R.string.event_request_dialog_pairing_pin);
            if (i == -2) {
                str = getString(R.string.detail_cancel);
            } else if (i == -1) {
                str = getString(R.string.detail_ok);
            }
            if (c == 2) {
                SALogging.insertSALog(string, string2);
            } else {
                SALogging.insertSALog(string, string2, str);
            }
        }
        hideSoftInput();
        this.mPairingDialogActivity.dismiss();
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0169  */
    @Override // androidx.fragment.app.DialogFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.app.Dialog onCreateDialog(android.os.Bundle r13) {
        /*
            Method dump skipped, instructions count: 536
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.bluetooth.BluetoothPairingDialogFragment.onCreateDialog(android.os.Bundle):android.app.Dialog");
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        StringBuilder m =
                ListPopupWindow$$ExternalSyntheticOutline0.m(
                        i, "onKey :: keyCode = ", ", event = ");
        m.append(keyEvent.describeContents());
        Log.d("BTPairingDialogFragment", m.toString());
        if (i == 3) {
            BluetoothPairingDialog bluetoothPairingDialog = this.mPairingDialogActivity;
            bluetoothPairingDialog.getClass();
            try {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                intent.addFlags(329252864);
                bluetoothPairingDialog.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
            BluetoothPairingController bluetoothPairingController =
                    bluetoothPairingDialog.mBluetoothPairingController;
            if (bluetoothPairingController != null) {
                if (!bluetoothPairingController.mIsBondedInitiatedLocally
                        && !bluetoothPairingController.mIsSettingsForeground) {
                    bluetoothPairingController.updateBlockingDevice(false);
                }
                bluetoothPairingDialog.mBluetoothPairingController.onCancel();
            }
            bluetoothPairingDialog.dismiss();
        }
        return false;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        EditText editText = this.mPairingView;
        if (editText != null) {
            editText.requestFocus();
            InputMethodManager inputMethodManager =
                    (InputMethodManager)
                            this.mPairingDialogActivity.getSystemService("input_method");
            this.mDialog.getWindow().setSoftInputMode(21);
            inputMethodManager.showSoftInput(this.mPairingView, 1);
        }
        Button button = this.mDialog.getButton(-1);
        if (button != null) {
            button.requestFocus();
        }
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window"))
                    .requestSystemKeyEvent(3, this.mPairingDialogActivity.getComponentName(), true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        String charSequence2 = charSequence.toString();
        if (charSequence2 == null || charSequence2.length() < 17) {
            this.mPairingView.setBackgroundTintList(
                    getResources().getColorStateList(R.color.dashboard_tab_selected_color));
            this.mPairingViewErrorText.setVisibility(8);
            this.mPairingViewMargin.setVisibility(0);
            return;
        }
        String str = this.mTempUserInput;
        if (str != null && str.length() < 17) {
            this.mPairingView.setText(this.mTempUserInput, (TextView.BufferType) null);
            this.mPairingView.setSelection(this.mTempUserInput.length());
        }
        this.mPairingView.setBackgroundTintList(
                getResources().getColorStateList(R.color.sec_bluetooth_dialog_error_color));
        this.mPairingViewErrorText.setText(R.string.wifi_ssid_max_byte_error);
        this.mPairingViewErrorText.setVisibility(0);
        this.mPairingViewMargin.setVisibility(8);
    }
}
