package com.samsung.android.settings.security;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.security.SecIccLockManager.ChangeSimPersoPin;
import com.samsung.android.settings.security.SecIccLockManager.SetSimPersoEnabled;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecSimPersoDialog extends AlertDialog
        implements DialogInterface.OnClickListener {
    public static SecSimPersoDialog sSimPersoDialog;
    public EditText mEditText;
    public SecIccLockManager mIccLockManager;
    public TextView mMessageView;
    public ProgressBar mProgressButton;
    public boolean positiveButtonClicked;

    public static boolean isDialogOpen() {
        SecSimPersoDialog secSimPersoDialog = sSimPersoDialog;
        return secSimPersoDialog != null && secSimPersoDialog.isShowing();
    }

    public static void releaseDialog() {
        Log.i("SecIccLockSettings.SecSimPersoDialog", "releaseDialog() : " + sSimPersoDialog);
        if (isDialogOpen()) {
            sSimPersoDialog.dismiss();
        }
        sSimPersoDialog = null;
    }

    public static void show(Context context, SecIccLockManager secIccLockManager) {
        String str;
        if (isDialogOpen()) {
            sSimPersoDialog.dismiss();
        }
        final SecSimPersoDialog secSimPersoDialog = new SecSimPersoDialog(context);
        secSimPersoDialog.positiveButtonClicked = false;
        secSimPersoDialog.mIccLockManager = secIccLockManager;
        View inflate =
                LayoutInflater.from(context)
                        .inflate(R.layout.sec_lock_phone_to_current_sim_layout, (ViewGroup) null);
        secSimPersoDialog.mMessageView = (TextView) inflate.findViewById(android.R.id.message);
        TextView textView = (TextView) inflate.findViewById(R.id.error_textview_sim);
        textView.setVisibility(8);
        EditText editText = (EditText) inflate.findViewById(android.R.id.edit);
        secSimPersoDialog.mEditText = editText;
        editText.setOnFocusChangeListener(
                new View
                        .OnFocusChangeListener() { // from class:
                                                   // com.samsung.android.settings.security.SecSimPersoDialog$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnFocusChangeListener
                    public final void onFocusChange(View view, boolean z) {
                        SecSimPersoDialog secSimPersoDialog2 = SecSimPersoDialog.this;
                        if (z) {
                            secSimPersoDialog2.getWindow().setSoftInputMode(5);
                        } else {
                            secSimPersoDialog2.getClass();
                        }
                    }
                });
        secSimPersoDialog.setView(inflate);
        secSimPersoDialog.setButton(
                -1, secSimPersoDialog.getContext().getString(R.string.dlg_ok), secSimPersoDialog);
        secSimPersoDialog.setButton(
                -2,
                secSimPersoDialog.getContext().getString(R.string.dlg_cancel),
                secSimPersoDialog);
        sSimPersoDialog = secSimPersoDialog;
        boolean z = secIccLockManager.mIccToState;
        Log.i("SecIccLockSettings.SecSimPersoDialog", "show()");
        Log.i("SecIccLockSettings.SecSimPersoDialog", "setDialogValues Called");
        Resources resources = secSimPersoDialog.getContext().getResources();
        secSimPersoDialog.getWindow().setSoftInputMode(4);
        int i = secIccLockManager.mIccLockMode;
        if (i == 1) {
            String string =
                    (!z
                                    || secSimPersoDialog
                                            .getContext()
                                            .getSharedPreferences("sim_perso_prefs", 0)
                                            .getBoolean("sim_perso_used", false))
                            ? z
                                    ? resources.getString(
                                            R.string.change_phone_to_current_sim_input_password)
                                    : resources.getString(
                                            R.string.unlock_phone_to_current_sim_input_password)
                            : resources.getString(
                                    R.string.lock_phone_to_current_sim_dialog_message);
            secSimPersoDialog.setTitle(
                    z
                            ? resources.getString(R.string.lock_phone_to_current_sim)
                            : resources.getString(R.string.unlock_phone_to_current_sim));
            str = string;
        } else if (i == 2) {
            str = resources.getString(R.string.change_phone_to_current_sim_input_password);
            secSimPersoDialog.setTitle(
                    resources.getString(R.string.lock_phone_to_current_sim_change_password));
        } else if (i == 3) {
            str =
                    resources.getString(
                            R.string
                                    .lock_phone_to_current_sim_change_password_dialog_new_password_message);
            secSimPersoDialog.setTitle(
                    resources.getString(R.string.lock_phone_to_current_sim_change_password));
        } else if (i != 4) {
            str = ApnSettings.MVNO_NONE;
        } else {
            str =
                    resources.getString(
                            R.string
                                    .lock_phone_to_current_sim_change_password_dialog_confirm_password_message);
            secSimPersoDialog.setTitle(
                    resources.getString(R.string.lock_phone_to_current_sim_change_password));
            if (TextUtils.equals(secIccLockManager.mNewPin, secIccLockManager.mPin)) {
                textView.setVisibility(8);
            } else {
                textView.setVisibility(0);
            }
        }
        String str2 = secIccLockManager.mErrorMessage;
        if (!TextUtils.isEmpty(str2)) {
            textView.setVisibility(0);
            textView.setText(str2);
        }
        secSimPersoDialog.setMessage(str);
        editText.setSelection(editText.length());
        secSimPersoDialog.show();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void cancel() {
        Log.d("SecIccLockSettings.SecSimPersoDialog", "cancel()");
        this.mIccLockManager.resetIccLockData();
        super.cancel();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        if (this.positiveButtonClicked) {
            this.positiveButtonClicked = false;
        } else {
            Log.i("SecIccLockSettings.SecSimPersoDialog", "dismiss()");
            super.dismiss();
        }
    }

    public final void getProgressDialog(Button button) {
        if (this.mProgressButton == null) {
            ProgressBar progressBar =
                    new ProgressBar(getContext(), null, android.R.attr.progressBarStyle);
            this.mProgressButton = progressBar;
            progressBar.setIndeterminate(true);
            button.setVisibility(8);
            ((LinearLayout) button.getParent()).addView(this.mProgressButton);
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) this.mProgressButton.getLayoutParams();
            layoutParams.width = button.getWidth();
            layoutParams.height = button.getHeight();
            this.mProgressButton.setLayoutParams(layoutParams);
            this.positiveButtonClicked = true;
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "onPinEntered, positiveResult = ", "SecIccLockSettings.SecSimPersoDialog");
        LoggingHelper.insertEventLogging(56, i == -1 ? 4515 : 4505);
        if (i == -2) {
            this.mIccLockManager.resetIccLockData();
            return;
        }
        this.mIccLockManager.mPin = this.mEditText.getText().toString();
        String str = this.mIccLockManager.mPin;
        if (str == null || str.length() < 4 || str.length() > 8) {
            this.mIccLockManager.mErrorMessage =
                    getContext().getString(R.string.lock_phone_to_current_sim_error_text_4digits);
            show(getContext(), this.mIccLockManager);
            Log.i("SecIccLockSettings.SecSimPersoDialog", "incorrect input");
            return;
        }
        SecIccLockManager secIccLockManager = this.mIccLockManager;
        int i2 = secIccLockManager.mIccLockMode;
        if (i2 == 1) {
            SecSimPersoDialog secSimPersoDialog = sSimPersoDialog;
            if (secSimPersoDialog != null) {
                getProgressDialog(secSimPersoDialog.getButton(-1));
                if (i == -1) {
                    sSimPersoDialog.getButton(-2).setEnabled(false);
                }
            }
            SecIccLockManager secIccLockManager2 = this.mIccLockManager;
            secIccLockManager2.getClass();
            secIccLockManager2
                    .new SetSimPersoEnabled(
                            secIccLockManager2.mSubscriptionId,
                            secIccLockManager2.mIccToState,
                            secIccLockManager2.mPin)
                    .execute(new Void[0]);
            return;
        }
        if (i2 == 2) {
            secIccLockManager.mOldPin = secIccLockManager.mPin;
            secIccLockManager.setIccLockMode(3);
            this.mIccLockManager.mErrorMessage = null;
            show(getContext(), this.mIccLockManager);
            return;
        }
        if (i2 == 3) {
            secIccLockManager.mNewPin = secIccLockManager.mPin;
            secIccLockManager.setIccLockMode(4);
            show(getContext(), this.mIccLockManager);
            return;
        }
        if (i2 != 4) {
            return;
        }
        if (!TextUtils.equals(secIccLockManager.mNewPin, secIccLockManager.mPin)) {
            this.mIccLockManager.setIccLockMode(4);
            this.mIccLockManager.mPin = null;
            show(getContext(), this.mIccLockManager);
            return;
        }
        SecSimPersoDialog secSimPersoDialog2 = sSimPersoDialog;
        if (secSimPersoDialog2 != null) {
            getProgressDialog(secSimPersoDialog2.getButton(-1));
            if (i == -1) {
                sSimPersoDialog.getButton(-2).setEnabled(false);
            }
        }
        SecIccLockManager secIccLockManager3 = this.mIccLockManager;
        secIccLockManager3.mErrorMessage = null;
        secIccLockManager3.updateMenu();
        secIccLockManager3
                .new ChangeSimPersoPin(
                        secIccLockManager3.mSubscriptionId,
                        secIccLockManager3.mOldPin,
                        secIccLockManager3.mNewPin)
                .execute(new Void[0]);
    }

    @Override // android.app.AlertDialog
    public final void setMessage(CharSequence charSequence) {
        this.mMessageView.setText(charSequence);
    }
}
