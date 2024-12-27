package com.samsung.android.settings.bluetooth;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.bluetooth.BluetoothLengthDeviceNameFilter;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.bluetooth.BluetoothUtils$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.knox.EnterpriseContainerCallback;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecRemoteDeviceNameDialogFragment extends InstrumentedDialogFragment
        implements TextWatcher, TextView.OnEditorActionListener {
    public int budsNameLimit = 0;
    public AlertDialog mAlertDialog;
    public CachedBluetoothDevice mDevice;
    public EditText mDeviceNameEditText;
    public boolean mDeviceNameEdited;
    public TextView mDeviceNameErrorText;
    public boolean mNameEditedButtonEnabled;
    public Button mOkButton;
    public Space mRenameBottomMargin;

    @Override // android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        Log.d("SecBluetoothNameDialogFragment", "afterTextChanged :: ");
        if (this.mAlertDialog != null) {
            int length = editable.length();
            String editable2 = editable.toString();
            boolean z = false;
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                if (editable2.charAt(i2) == ' ') {
                    i++;
                }
            }
            if (this.budsNameLimit > 0) {
                if (editable.toString().length() <= this.budsNameLimit) {
                    if (editable.length() > 0 && length != i) {
                        CachedBluetoothDevice cachedBluetoothDevice = this.mDevice;
                        if (!editable2.equals(
                                cachedBluetoothDevice != null
                                        ? cachedBluetoothDevice.getName()
                                        : null)) {
                            z = true;
                        }
                    }
                    this.mNameEditedButtonEnabled = z;
                    Button button = this.mOkButton;
                    if (button != null) {
                        button.setEnabled(z);
                        this.mOkButton.setFocusable(this.mNameEditedButtonEnabled);
                        return;
                    }
                    return;
                }
                return;
            }
            if (editable.toString().length() <= 62) {
                if (editable.length() > 0 && length != i) {
                    CachedBluetoothDevice cachedBluetoothDevice2 = this.mDevice;
                    if (!editable2.equals(
                            cachedBluetoothDevice2 != null
                                    ? cachedBluetoothDevice2.getName()
                                    : null)) {
                        z = true;
                    }
                }
                this.mNameEditedButtonEnabled = z;
                Button button2 = this.mOkButton;
                if (button2 != null) {
                    button2.setEnabled(z);
                    this.mOkButton.setFocusable(this.mNameEditedButtonEnabled);
                }
            }
        }
    }

    @Override // android.text.TextWatcher
    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        Log.e("SecBluetoothNameDialogFragment", "beforeTextChanged ::");
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_FAILED;
    }

    @Override // com.android.settings.core.instrumentation.InstrumentedDialogFragment,
              // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        CachedBluetoothDevice findDevice;
        super.onAttach(context);
        String string = getArguments().getString("cached_device");
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        if (localBluetoothManager == null) {
            findDevice = null;
        } else {
            findDevice =
                    localBluetoothManager.mCachedDeviceManager.findDevice(
                            localBluetoothManager.mLocalAdapter.mAdapter.getRemoteDevice(string));
        }
        this.mDevice = findDevice;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        BluetoothDevice bluetoothDevice;
        byte[] m;
        byte b;
        CachedBluetoothDevice cachedBluetoothDevice = this.mDevice;
        String name = cachedBluetoothDevice != null ? cachedBluetoothDevice.getName() : null;
        if (bundle != null) {
            name = bundle.getString("device_name", name);
            this.mDeviceNameEdited = bundle.getBoolean("device_name_edited", false);
            this.mNameEditedButtonEnabled = bundle.getBoolean("remote_device_rename_edited", false);
        }
        CachedBluetoothDevice cachedBluetoothDevice2 = this.mDevice;
        if (cachedBluetoothDevice2 != null
                && (bluetoothDevice = cachedBluetoothDevice2.mDevice) != null
                && (m =
                                BluetoothUtils$$ExternalSyntheticOutline0.m(
                                        SmepTag.FEATURE_CHANGE_DEVICE_NAME, bluetoothDevice))
                        != null
                && m.length >= 4
                && (b = m[3]) > 0) {
            this.budsNameLimit = b;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.bluetooth_device_advanced_rename_device);
        View inflate =
                ((LayoutInflater) getActivity().getSystemService("layout_inflater"))
                        .inflate(R.layout.sec_bluetooth_device_rename_popup, (ViewGroup) null);
        this.mDeviceNameEditText = (EditText) inflate.findViewById(R.id.rename_edit_text);
        this.mDeviceNameErrorText =
                (TextView) inflate.findViewById(R.id.bluetooth_rename_error_text);
        this.mRenameBottomMargin =
                (Space) inflate.findViewById(R.id.rename_edit_text_bottom_margin);
        this.mDeviceNameEditText.setText(name);
        if (this.budsNameLimit > 0) {
            EditText editText = this.mDeviceNameEditText;
            FragmentActivity activity = getActivity();
            TextView textView = this.mDeviceNameErrorText;
            EditText editText2 = this.mDeviceNameEditText;
            Space space = this.mRenameBottomMargin;
            BluetoothLengthDeviceNameFilter bluetoothLengthDeviceNameFilter =
                    new BluetoothLengthDeviceNameFilter(this.budsNameLimit);
            bluetoothLengthDeviceNameFilter.mDeviceNameEditText = editText2;
            bluetoothLengthDeviceNameFilter.mDeviceNameErrorText = textView;
            bluetoothLengthDeviceNameFilter.mFragmentActivity = activity;
            bluetoothLengthDeviceNameFilter.mRenameBottomMargin = space;
            bluetoothLengthDeviceNameFilter.isSmepFilter = true;
            editText.setFilters(
                    new InputFilter[] {
                        bluetoothLengthDeviceNameFilter,
                        new Utils.EmojiInputFilter(this.mDeviceNameEditText.getContext())
                    });
        } else {
            this.mDeviceNameEditText.setFilters(
                    new InputFilter[] {
                        new InputFilter
                                .LengthFilter() { // from class:
                                                  // com.samsung.android.settings.bluetooth.SecBluetoothNameDialogFragment$DeviceRenameLengthFilter
                            {
                                super(62);
                            }

                            @Override // android.text.InputFilter.LengthFilter,
                                      // android.text.InputFilter
                            public final CharSequence filter(
                                    CharSequence charSequence,
                                    int i,
                                    int i2,
                                    Spanned spanned,
                                    int i3,
                                    int i4) {
                                CharSequence filter =
                                        super.filter(charSequence, i, i2, spanned, i3, i4);
                                if (filter == null) {
                                    SecRemoteDeviceNameDialogFragment.this.mRenameBottomMargin
                                            .setVisibility(0);
                                    SecRemoteDeviceNameDialogFragment.this.mDeviceNameErrorText
                                            .setVisibility(8);
                                    SecRemoteDeviceNameDialogFragment
                                            secRemoteDeviceNameDialogFragment =
                                                    SecRemoteDeviceNameDialogFragment.this;
                                    secRemoteDeviceNameDialogFragment.mDeviceNameEditText
                                            .setBackgroundTintList(
                                                    secRemoteDeviceNameDialogFragment
                                                            .getResources()
                                                            .getColorStateList(
                                                                    R.color
                                                                            .sec_dashboard_tab_selected_text_color));
                                } else if (SecRemoteDeviceNameDialogFragment.this
                                                .mDeviceNameErrorText.getVisibility()
                                        != 0) {
                                    SecRemoteDeviceNameDialogFragment
                                            secRemoteDeviceNameDialogFragment2 =
                                                    SecRemoteDeviceNameDialogFragment.this;
                                    secRemoteDeviceNameDialogFragment2.mDeviceNameEditText
                                            .setBackgroundTintList(
                                                    secRemoteDeviceNameDialogFragment2
                                                            .getActivity()
                                                            .getResources()
                                                            .getColorStateList(
                                                                    R.color
                                                                            .sec_bluetooth_dialog_error_color));
                                    SecRemoteDeviceNameDialogFragment.this.mDeviceNameErrorText
                                            .setText(R.string.wifi_ssid_max_byte_error);
                                    SecRemoteDeviceNameDialogFragment.this.mDeviceNameErrorText
                                            .setVisibility(0);
                                    SecRemoteDeviceNameDialogFragment.this.mRenameBottomMargin
                                            .setVisibility(8);
                                }
                                return filter;
                            }
                        },
                        new Utils.EmojiInputFilter(this.mDeviceNameEditText.getContext())
                    });
        }
        if (!TextUtils.isEmpty(name)) {
            this.mDeviceNameEditText.setSelection(Math.min(name.length(), 62));
        }
        this.mDeviceNameEditText.addTextChangedListener(this);
        EditText editText3 = this.mDeviceNameEditText;
        StringBuilder sb = com.android.settings.Utils.sBuilder;
        SeslColorPicker$16$$ExternalSyntheticOutline0.m(editText3);
        this.mDeviceNameEditText.setOnEditorActionListener(this);
        builder.setView(inflate);
        builder.setPositiveButton(
                R.string.bluetooth_rename_button,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.bluetooth.SecBluetoothNameDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        SecRemoteDeviceNameDialogFragment secRemoteDeviceNameDialogFragment =
                                SecRemoteDeviceNameDialogFragment.this;
                        String editable =
                                secRemoteDeviceNameDialogFragment
                                        .mDeviceNameEditText
                                        .getText()
                                        .toString();
                        CachedBluetoothDevice cachedBluetoothDevice3 =
                                secRemoteDeviceNameDialogFragment.mDevice;
                        if (cachedBluetoothDevice3 != null) {
                            cachedBluetoothDevice3.setName(editable);
                        }
                    }
                });
        builder.setNegativeButton(R.string.common_cancel, (DialogInterface.OnClickListener) null);
        this.mAlertDialog = builder.create();
        this.mDeviceNameEditText.selectAll();
        this.mDeviceNameEditText.setImeOptions(6);
        this.mDeviceNameEditText.setBackgroundTintList(
                getResources().getColorStateList(R.color.sec_dashboard_tab_selected_text_color));
        return this.mAlertDialog;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mAlertDialog = null;
        this.mDeviceNameEditText = null;
        this.mOkButton = null;
        this.mNameEditedButtonEnabled = false;
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 6 || !this.mAlertDialog.getButton(-1).isEnabled()) {
            return false;
        }
        String charSequence = textView.getText().toString();
        CachedBluetoothDevice cachedBluetoothDevice = this.mDevice;
        if (cachedBluetoothDevice != null) {
            cachedBluetoothDevice.setName(charSequence);
        }
        this.mAlertDialog.dismiss();
        return false;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (this.mOkButton == null) {
            Button button = this.mAlertDialog.getButton(-1);
            this.mOkButton = button;
            button.setEnabled(this.mNameEditedButtonEnabled);
        }
        EditText editText = this.mDeviceNameEditText;
        if (editText != null) {
            editText.requestFocus();
            this.mDeviceNameEditText.postDelayed(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.bluetooth.SecBluetoothNameDialogFragment$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SecRemoteDeviceNameDialogFragment secRemoteDeviceNameDialogFragment =
                                    SecRemoteDeviceNameDialogFragment.this;
                            if (secRemoteDeviceNameDialogFragment.mAlertDialog == null) {
                                Log.d("SecBluetoothNameDialogFragment", "mAlertDialog is null");
                            } else {
                                ((InputMethodManager)
                                                secRemoteDeviceNameDialogFragment
                                                        .getContext()
                                                        .getSystemService("input_method"))
                                        .showSoftInput(
                                                SecRemoteDeviceNameDialogFragment.this
                                                        .mDeviceNameEditText,
                                                1);
                            }
                        }
                    },
                    400L);
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putString("device_name", this.mDeviceNameEditText.getText().toString());
        bundle.putBoolean("device_name_edited", this.mDeviceNameEdited);
        bundle.putBoolean("remote_device_rename_edited", this.mNameEditedButtonEnabled);
    }

    @Override // android.text.TextWatcher
    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        Log.e("SecBluetoothNameDialogFragment", "onTextChanged ::");
        if (charSequence.toString() == null) {
            Button button = this.mOkButton;
            if (button != null) {
                button.setEnabled(false);
                this.mOkButton.setFocusable(false);
            }
            this.mNameEditedButtonEnabled = false;
        }
    }
}
