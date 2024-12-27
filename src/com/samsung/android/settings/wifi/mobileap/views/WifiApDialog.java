package com.samsung.android.settings.wifi.mobileap.views;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.samsung.android.settings.bluetooth.lebroadcast.SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApDialog extends AlertDialog {
    public Context mContext;
    public DialogInterface.OnClickListener mDialogButtonClickListener;
    public EditText mEditText1;
    public TextView mEditText1ErrorTextView;
    public TextView mMessage1TextView;
    public String mNegativeButtonText;
    public Button mPositiveButton;
    public String mPositiveButtonText;
    public String mTitle;

    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog,
              // androidx.activity.ComponentDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        View inflate = getLayoutInflater().inflate(R.layout.sec_wifi_ap_dialog, (ViewGroup) null);
        setView$1(inflate);
        this.mMessage1TextView = (TextView) inflate.findViewById(R.id.message1_textview);
        this.mEditText1 = (EditText) inflate.findViewById(R.id.edittext1);
        this.mEditText1ErrorTextView =
                (TextView) inflate.findViewById(R.id.edittext1_error_textview);
        if (!TextUtils.isEmpty(this.mTitle)) {
            setTitle(this.mTitle);
        }
        if (!TextUtils.isEmpty(this.mPositiveButtonText)) {
            setButton(-1, this.mPositiveButtonText, this.mDialogButtonClickListener);
        }
        if (!TextUtils.isEmpty(this.mNegativeButtonText)) {
            setButton(-2, this.mNegativeButtonText, this.mDialogButtonClickListener);
        }
        if (!TextUtils.isEmpty(null)) {
            setButton(-3, null, this.mDialogButtonClickListener);
        }
        super.onCreate(bundle);
        this.mPositiveButton = getButton(-1);
        getButton(-2);
        getButton(-3);
    }

    public final void setEditText(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mEditText1.setText(str);
        }
        this.mEditText1.setVisibility(0);
    }

    public final void setEditTextErrorText(String str) {
        if (TextUtils.isEmpty(str)) {
            this.mEditText1ErrorTextView.setVisibility(8);
            SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                    this.mContext, R.color.sec_wifi_ap_edit_text_background_color, this.mEditText1);
        } else {
            this.mEditText1ErrorTextView.setText(str);
            this.mEditText1ErrorTextView.setVisibility(0);
            SecBluetoothLeBroadcastSourceInfoController$$ExternalSyntheticOutline0.m(
                    this.mContext, R.color.sec_wifi_dialog_error_color, this.mEditText1);
            SeslColorPicker$16$$ExternalSyntheticOutline0.m(this.mEditText1);
        }
    }

    @Override // androidx.appcompat.app.AlertDialog
    public final void setMessage(CharSequence charSequence) {
        this.mMessage1TextView.setText(charSequence);
    }
}
