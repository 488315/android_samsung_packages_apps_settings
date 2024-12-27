package com.android.settings.bluetooth;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BluetoothNameDialogFragment extends InstrumentedDialogFragment
        implements TextWatcher, TextView.OnEditorActionListener {
    AlertDialog mAlertDialog;
    public boolean mDeviceNameEdited;
    public boolean mDeviceNameUpdated;
    public EditText mDeviceNameView;
    public Button mOkButton;

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        if (this.mDeviceNameUpdated) {
            this.mDeviceNameUpdated = false;
            this.mOkButton.setEnabled(false);
            return;
        }
        this.mDeviceNameEdited = true;
        Button button = this.mOkButton;
        if (button != null) {
            button.setEnabled(editable.toString().trim().length() != 0);
        }
    }

    public abstract String getDeviceName();

    public abstract int getDialogTitle();

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        String deviceName = getDeviceName();
        if (bundle != null) {
            deviceName = bundle.getString("device_name", deviceName);
            this.mDeviceNameEdited = bundle.getBoolean("device_name_edited", false);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getDialogTitle());
        View inflate =
                ((LayoutInflater) getActivity().getSystemService("layout_inflater"))
                        .inflate(R.layout.dialog_edittext, (ViewGroup) null);
        EditText editText = (EditText) inflate.findViewById(R.id.edittext);
        this.mDeviceNameView = editText;
        editText.setFilters(new InputFilter[] {new BluetoothLengthDeviceNameFilter()});
        this.mDeviceNameView.setText(deviceName);
        if (!TextUtils.isEmpty(deviceName)) {
            this.mDeviceNameView.setSelection(deviceName.length());
        }
        this.mDeviceNameView.addTextChangedListener(this);
        EditText editText2 = this.mDeviceNameView;
        StringBuilder sb = com.android.settings.Utils.sBuilder;
        SeslColorPicker$16$$ExternalSyntheticOutline0.m(editText2);
        this.mDeviceNameView.setOnEditorActionListener(this);
        builder.setView(inflate);
        builder.setPositiveButton(
                R.string.bluetooth_rename_button,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.bluetooth.BluetoothNameDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        BluetoothNameDialogFragment bluetoothNameDialogFragment =
                                BluetoothNameDialogFragment.this;
                        bluetoothNameDialogFragment.setDeviceName(
                                bluetoothNameDialogFragment.mDeviceNameView.getText().toString());
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        this.mAlertDialog = create;
        create.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.android.settings.bluetooth.BluetoothNameDialogFragment$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        InputMethodManager inputMethodManager;
                        BluetoothNameDialogFragment bluetoothNameDialogFragment =
                                BluetoothNameDialogFragment.this;
                        EditText editText3 = bluetoothNameDialogFragment.mDeviceNameView;
                        if (editText3 == null
                                || !editText3.requestFocus()
                                || (inputMethodManager =
                                                (InputMethodManager)
                                                        bluetoothNameDialogFragment
                                                                .getContext()
                                                                .getSystemService("input_method"))
                                        == null) {
                            return;
                        }
                        inputMethodManager.showSoftInput(
                                bluetoothNameDialogFragment.mDeviceNameView, 1);
                    }
                });
        return this.mAlertDialog;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mAlertDialog = null;
        this.mDeviceNameView = null;
        this.mOkButton = null;
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 6 && i != 0) {
            return false;
        }
        setDeviceName(textView.getText().toString());
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            return true;
        }
        this.mAlertDialog.dismiss();
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.mOkButton == null) {
            Button button = this.mAlertDialog.getButton(-1);
            this.mOkButton = button;
            button.setEnabled(this.mDeviceNameEdited);
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putString("device_name", this.mDeviceNameView.getText().toString());
        bundle.putBoolean("device_name_edited", this.mDeviceNameEdited);
    }

    public abstract void setDeviceName(String str);

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
}
