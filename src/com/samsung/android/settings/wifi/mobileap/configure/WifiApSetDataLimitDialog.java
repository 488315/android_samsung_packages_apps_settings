package com.samsung.android.settings.wifi.mobileap.configure;

import android.app.AlertDialog;
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

import com.android.settings.R;

import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApSetDataLimitDialog extends AlertDialog {
    public final AnonymousClass1 dataWatcher;
    public Button mButtonOk;
    public final Context mContext;
    public final String mDataLimitValue;
    public EditText mEtDataLimit;
    public final DialogInterface.OnClickListener mListener;
    public TextView mTvDataLength;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.settings.wifi.mobileap.configure.WifiApSetDataLimitDialog$1] */
    public WifiApSetDataLimitDialog(
            Context context,
            String str,
            WifiApConfigureSetDataLimitPreferenceController.AnonymousClass3 anonymousClass3) {
        super(context);
        this.dataWatcher =
                new TextWatcher() { // from class:
                                    // com.samsung.android.settings.wifi.mobileap.configure.WifiApSetDataLimitDialog.1
                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {
                        WifiApSetDataLimitDialog.this.buttonValidate();
                    }

                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {
                        if (charSequence.toString().length() >= 10) {
                            WifiApSetDataLimitDialog.this.mTvDataLength.setVisibility(0);
                            WifiApSetDataLimitDialog wifiApSetDataLimitDialog =
                                    WifiApSetDataLimitDialog.this;
                            SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0
                                    .m(
                                            wifiApSetDataLimitDialog.mContext,
                                            R.color.sec_wifi_dialog_error_color,
                                            wifiApSetDataLimitDialog.mEtDataLimit);
                            return;
                        }
                        WifiApSetDataLimitDialog.this.mTvDataLength.setVisibility(8);
                        WifiApSetDataLimitDialog wifiApSetDataLimitDialog2 =
                                WifiApSetDataLimitDialog.this;
                        SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                                wifiApSetDataLimitDialog2.mContext,
                                R.color.sec_wifi_ap_edit_text_background_color,
                                wifiApSetDataLimitDialog2.mEtDataLimit);
                    }

                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {}
                };
        this.mContext = context;
        this.mDataLimitValue = str;
        this.mListener = anonymousClass3;
    }

    public final void buttonValidate() {
        if (this.mButtonOk != null) {
            if (this.mEtDataLimit.getText().length() <= 0
                    || this.mEtDataLimit.getText().toString().trim().length() <= 0) {
                this.mButtonOk.setEnabled(false);
            } else {
                this.mButtonOk.setEnabled(true);
            }
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        View inflate =
                getLayoutInflater()
                        .inflate(R.layout.sec_wifi_ap_set_data_limit_dialog, (ViewGroup) null);
        setView(inflate);
        setTitle(R.string.data_usage_disable_mobile_limit);
        ((TextView) inflate.findViewById(R.id.mobile_data_limit_message))
                .setText(WifiApUtils.getStringID(R.string.wifi_ap_set_mobile_data_limit));
        EditText editText = (EditText) inflate.findViewById(R.id.mobile_data_limt_edit);
        this.mEtDataLimit = editText;
        editText.addTextChangedListener(this.dataWatcher);
        this.mTvDataLength = (TextView) inflate.findViewById(R.id.mobile_data_max_length);
        setButton(-1, getContext().getString(R.string.dlg_ok), this.mListener);
        setButton(-3, getContext().getString(R.string.wifi_cancel), this.mListener);
        String str = this.mDataLimitValue;
        if (str != null) {
            this.mEtDataLimit.setText(str);
            this.mEtDataLimit.setEnabled(true);
            this.mEtDataLimit.selectAll();
            setButton(-2, getContext().getString(R.string.data_limit_cancel), this.mListener);
        }
        super.onCreate(bundle);
        this.mButtonOk = getButton(-1);
        buttonValidate();
    }
}
