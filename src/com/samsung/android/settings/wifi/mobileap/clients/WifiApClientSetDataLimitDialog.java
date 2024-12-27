package com.samsung.android.settings.wifi.mobileap.clients;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApConnectedDeviceUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApClientSetDataLimitDialog extends AlertDialog {
    public Context mContext;
    public TextView mDataLimitEditErrorTextView;
    public EditText mDataLimitEditText;
    public AnonymousClass1 mDataLimitEditTextWatcher;
    public String mDialogMessage;
    public DialogInterface.OnClickListener mListener;
    public String mMacAddress;
    public Button mOkButton;
    public WifiApDataUsageConfig mWifiApDataUsageConfig;

    public final void buttonValidate$1() {
        if (this.mOkButton != null) {
            if (this.mDataLimitEditText.getText().length() <= 0
                    || this.mDataLimitEditText.getText().toString().trim().length() <= 0) {
                this.mOkButton.setEnabled(false);
                return;
            }
            if ((this.mMacAddress.equals(ApnSettings.MVNO_NONE)
                                    ? WifiApConnectedDeviceUtils.getWifiApTodayTotalDataUsage(
                                            this.mContext)
                                    : WifiApConnectedDeviceUtils
                                            .getWifiApClientMobileDataConsumedDetail(
                                                    this.mContext, this.mMacAddress))
                            .getUsageValueInMB()
                    < getInputDataInDataUsageConfig().getUsageValueInMB()) {
                this.mOkButton.setEnabled(true);
            } else {
                this.mOkButton.setEnabled(false);
            }
        }
    }

    public final WifiApDataUsageConfig getInputDataInDataUsageConfig() {
        return new WifiApDataUsageConfig(
                Long.parseLong(
                                (this.mDataLimitEditText.getText().length() <= 0
                                                || this.mDataLimitEditText
                                                                .getText()
                                                                .toString()
                                                                .trim()
                                                                .length()
                                                        <= 0)
                                        ? null
                                        : this.mDataLimitEditText.getText().toString())
                        * 1000000);
    }

    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog,
              // androidx.activity.ComponentDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        View inflate =
                getLayoutInflater()
                        .inflate(R.layout.sec_wifi_ap_set_data_limit_dialog1, (ViewGroup) null);
        setView$1(inflate);
        ((TextView) inflate.findViewById(R.id.data_limit_message_textview))
                .setText(this.mDialogMessage);
        EditText editText = (EditText) inflate.findViewById(R.id.data_limit_edittext);
        this.mDataLimitEditText = editText;
        editText.addTextChangedListener(this.mDataLimitEditTextWatcher);
        this.mDataLimitEditErrorTextView =
                (TextView) inflate.findViewById(R.id.data_limit_edit_error_textview);
        setButton(-1, getContext().getString(R.string.dlg_ok), this.mListener);
        setButton(-3, getContext().getString(R.string.wifi_cancel), this.mListener);
        WifiApDataUsageConfig wifiApDataUsageConfig = this.mWifiApDataUsageConfig;
        if (wifiApDataUsageConfig != null && wifiApDataUsageConfig.getUsageValueInMB() > 0.0d) {
            this.mDataLimitEditText.setText(
                    String.valueOf((long) this.mWifiApDataUsageConfig.getUsageValueInMB()));
            this.mDataLimitEditText.selectAll();
            setButton(-2, getContext().getString(R.string.data_limit_cancel), this.mListener);
        }
        super.onCreate(bundle);
        this.mOkButton = getButton(-1);
        getButton(-2);
        buttonValidate$1();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSetDataLimitDialog$1, reason: invalid class name */
    public final class AnonymousClass1 implements TextWatcher {
        public AnonymousClass1() {}

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            WifiApClientSetDataLimitDialog.this.buttonValidate$1();
        }

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (charSequence.toString().length() >= 10) {
                WifiApClientSetDataLimitDialog.this.mDataLimitEditErrorTextView.setVisibility(0);
                WifiApClientSetDataLimitDialog wifiApClientSetDataLimitDialog =
                        WifiApClientSetDataLimitDialog.this;
                SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                        wifiApClientSetDataLimitDialog.mContext,
                        R.color.sec_wifi_dialog_error_color,
                        wifiApClientSetDataLimitDialog.mDataLimitEditText);
                return;
            }
            WifiApClientSetDataLimitDialog.this.mDataLimitEditErrorTextView.setVisibility(8);
            WifiApClientSetDataLimitDialog wifiApClientSetDataLimitDialog2 =
                    WifiApClientSetDataLimitDialog.this;
            SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                    wifiApClientSetDataLimitDialog2.mContext,
                    R.color.sec_wifi_ap_edit_text_background_color,
                    wifiApClientSetDataLimitDialog2.mDataLimitEditText);
        }

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
    }
}
