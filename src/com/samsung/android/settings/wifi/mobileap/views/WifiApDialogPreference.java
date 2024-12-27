package com.samsung.android.settings.wifi.mobileap.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.preference.Preference;

import com.android.settings.R;

import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApDialogPreference extends WifiApPreference {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AlertDialog mAlertDialog;
    public final Context mContext;
    public final int mMessageEditTextImeOptions;
    public final int mMessageEditTextInputType;
    public String mMessageEditTextString;
    public final String mPositiveButtonText;

    public WifiApDialogPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
        this.mMessageEditTextInputType = -1;
        this.mMessageEditTextImeOptions = -1;
        this.mContext = context;
        setOnPreferenceClickListener(this);
        this.mPositiveButtonText = context.getString(R.string.ok);
    }

    @Override // com.samsung.android.settings.wifi.mobileap.views.WifiApPreference,
              // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        final int i = 0;
        AlertDialog alertDialog = this.mAlertDialog;
        final int i2 = 1;
        if (alertDialog != null && alertDialog.isShowing()) {
            Log.d("WifiApDialogPreference", "Dialog is showing, ignore");
            super.onPreferenceClick(preference);
            return true;
        }
        Context context = this.mContext;
        boolean z = WifiApSettingsUtils.DBG;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 0);
        builder.setTitle(getTitle());
        if (!TextUtils.isEmpty(null)) {
            builder.setNeutralButton(
                    (CharSequence) null,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.wifi.mobileap.views.WifiApDialogPreference.1
                        public final /* synthetic */ WifiApDialogPreference this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i3) {
                            switch (i) {
                                case 0:
                                    int i4 = WifiApDialogPreference.$r8$clinit;
                                    Log.i(
                                            "WifiApDialogPreference",
                                            "Dialog Neutral button clicked");
                                    this.this$0.getClass();
                                    break;
                                case 1:
                                    int i5 = WifiApDialogPreference.$r8$clinit;
                                    Log.i(
                                            "WifiApDialogPreference",
                                            "Dialog Negative button clicked");
                                    this.this$0.getClass();
                                    break;
                                default:
                                    int i6 = WifiApDialogPreference.$r8$clinit;
                                    Log.d("WifiApDialogPreference", "Dialog OK button clicked");
                                    this.this$0.getClass();
                                    break;
                            }
                        }
                    });
        }
        if (!TextUtils.isEmpty(null)) {
            builder.setNegativeButton(
                    (CharSequence) null,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.samsung.android.settings.wifi.mobileap.views.WifiApDialogPreference.1
                        public final /* synthetic */ WifiApDialogPreference this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i3) {
                            switch (i2) {
                                case 0:
                                    int i4 = WifiApDialogPreference.$r8$clinit;
                                    Log.i(
                                            "WifiApDialogPreference",
                                            "Dialog Neutral button clicked");
                                    this.this$0.getClass();
                                    break;
                                case 1:
                                    int i5 = WifiApDialogPreference.$r8$clinit;
                                    Log.i(
                                            "WifiApDialogPreference",
                                            "Dialog Negative button clicked");
                                    this.this$0.getClass();
                                    break;
                                default:
                                    int i6 = WifiApDialogPreference.$r8$clinit;
                                    Log.d("WifiApDialogPreference", "Dialog OK button clicked");
                                    this.this$0.getClass();
                                    break;
                            }
                        }
                    });
        }
        final int i3 = 2;
        builder.setPositiveButton(
                this.mPositiveButtonText,
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.mobileap.views.WifiApDialogPreference.1
                    public final /* synthetic */ WifiApDialogPreference this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i32) {
                        switch (i3) {
                            case 0:
                                int i4 = WifiApDialogPreference.$r8$clinit;
                                Log.i("WifiApDialogPreference", "Dialog Neutral button clicked");
                                this.this$0.getClass();
                                break;
                            case 1:
                                int i5 = WifiApDialogPreference.$r8$clinit;
                                Log.i("WifiApDialogPreference", "Dialog Negative button clicked");
                                this.this$0.getClass();
                                break;
                            default:
                                int i6 = WifiApDialogPreference.$r8$clinit;
                                Log.d("WifiApDialogPreference", "Dialog OK button clicked");
                                this.this$0.getClass();
                                break;
                        }
                    }
                });
        builder.setOnDismissListener(
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.samsung.android.settings.wifi.mobileap.views.WifiApDialogPreference.4
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        int i4 = WifiApDialogPreference.$r8$clinit;
                        Log.i("WifiApDialogPreference", "Dialog dismissed");
                        WifiApDialogPreference.this.getClass();
                    }
                });
        builder.setOnCancelListener(
                new DialogInterface
                        .OnCancelListener() { // from class:
                                              // com.samsung.android.settings.wifi.mobileap.views.WifiApDialogPreference.5
                    @Override // android.content.DialogInterface.OnCancelListener
                    public final void onCancel(DialogInterface dialogInterface) {
                        int i4 = WifiApDialogPreference.$r8$clinit;
                        Log.i("WifiApDialogPreference", "Dialog canceled");
                        WifiApDialogPreference.this.getClass();
                    }
                });
        AlertDialog create = builder.create();
        this.mAlertDialog = create;
        View inflate =
                create.getLayoutInflater()
                        .inflate(
                                R.layout.sec_wifi_ap_dialog_preference_dialog_layout,
                                (ViewGroup) null);
        TextView textView = (TextView) inflate.findViewById(R.id.dialog_message_textview);
        EditText editText = (EditText) inflate.findViewById(R.id.dialog_message_edittext);
        TextView textView2 = (TextView) inflate.findViewById(R.id.dialog_message_edittext_unit);
        textView.setText((CharSequence) null);
        if (TextUtils.isEmpty(null) && TextUtils.isEmpty(this.mMessageEditTextString)) {
            editText.setVisibility(8);
        } else {
            editText.setVisibility(0);
            editText.setHint((CharSequence) null);
            editText.setText(this.mMessageEditTextString);
            int i4 = this.mMessageEditTextInputType;
            if (i4 != -1) {
                editText.setInputType(i4);
            }
            int i5 = this.mMessageEditTextImeOptions;
            if (i5 != -1) {
                editText.setImeOptions(i5);
            }
            editText.setFilters(new InputFilter[0]);
            editText.addTextChangedListener(
                    new TextWatcher() { // from class:
                                        // com.samsung.android.settings.wifi.mobileap.views.WifiApDialogPreference.6
                        @Override // android.text.TextWatcher
                        public final void afterTextChanged(Editable editable) {
                            int i6 = WifiApDialogPreference.$r8$clinit;
                            Log.i(
                                    "WifiApDialogPreference",
                                    "afterTextChanged: " + editable.toString());
                            WifiApDialogPreference.this.mMessageEditTextString =
                                    editable.toString();
                        }

                        @Override // android.text.TextWatcher
                        public final void beforeTextChanged(
                                CharSequence charSequence, int i6, int i7, int i8) {}

                        @Override // android.text.TextWatcher
                        public final void onTextChanged(
                                CharSequence charSequence, int i6, int i7, int i8) {}
                    });
        }
        if (TextUtils.isEmpty(null)) {
            textView2.setVisibility(8);
        } else {
            textView2.setVisibility(0);
            textView2.setText((CharSequence) null);
        }
        this.mAlertDialog.setView(inflate);
        this.mAlertDialog.show();
        return true;
    }
}
