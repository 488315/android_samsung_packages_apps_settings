package com.android.settings.bluetooth;

import android.text.Spanned;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;

import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothLengthDeviceNameFilter extends Utf8ByteLengthFilter {
    public boolean isSmepFilter;
    public EditText mDeviceNameEditText;
    public TextView mDeviceNameErrorText;
    public FragmentActivity mFragmentActivity;
    public Space mRenameBottomMargin;

    public BluetoothLengthDeviceNameFilter() {
        super(IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut);
        this.isSmepFilter = false;
    }

    @Override // com.android.settings.bluetooth.Utf8ByteLengthFilter, android.text.InputFilter
    public final CharSequence filter(
            CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        CharSequence filter = super.filter(charSequence, i, i2, spanned, i3, i4);
        if (this.isSmepFilter) {
            if (filter == null) {
                this.mRenameBottomMargin.setVisibility(0);
                this.mDeviceNameErrorText.setVisibility(8);
                this.mDeviceNameEditText.setBackgroundTintList(
                        this.mFragmentActivity
                                .getResources()
                                .getColorStateList(R.color.sec_dashboard_tab_selected_text_color));
            } else if (this.mDeviceNameErrorText.getVisibility() != 0) {
                this.mDeviceNameEditText.setBackgroundTintList(
                        this.mFragmentActivity
                                .getResources()
                                .getColorStateList(R.color.sec_bluetooth_dialog_error_color));
                this.mDeviceNameErrorText.setText(R.string.wifi_ssid_max_byte_error);
                this.mDeviceNameErrorText.setVisibility(0);
                this.mRenameBottomMargin.setVisibility(8);
            }
        }
        return filter;
    }
}
