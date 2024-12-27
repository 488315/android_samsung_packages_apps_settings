package com.samsung.android.settings.security;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.secutil.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.settings.R;

import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.security.SecIccLockManager.ChangeIccLockPin;
import com.samsung.android.settings.security.SecIccLockManager.SetIccLockEnabled;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecSimPinDialog extends AlertDialog implements DialogInterface.OnClickListener {
    public static SecSimPinDialog sSimPinDialog;
    public EditText mEditText;
    public TextView mErrorTextView;
    public SecIccLockManager mIccLockManager;
    public TextView mMessageView;

    public static void releaseDialog() {
        Log.d("SecIccLockSettings.SecSimPinDialog", "releaseDialog() : " + sSimPinDialog);
        SecSimPinDialog secSimPinDialog = sSimPinDialog;
        if (secSimPinDialog != null && secSimPinDialog.isShowing()) {
            sSimPinDialog.dismiss();
        }
        sSimPinDialog = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x00f3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0182  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void show(
            android.content.Context r9,
            com.samsung.android.settings.security.SecIccLockManager r10) {
        /*
            Method dump skipped, instructions count: 461
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.security.SecSimPinDialog.show(android.content.Context,"
                    + " com.samsung.android.settings.security.SecIccLockManager):void");
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void cancel() {
        Log.d("SecIccLockSettings.SecSimPinDialog", "cancel()");
        this.mIccLockManager.resetIccLockData();
        super.cancel();
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        Log.d("SecIccLockSettings.SecSimPinDialog", "dismiss()");
        super.dismiss();
        sSimPinDialog = null;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        Log.i("SecIccLockSettings.SecSimPinDialog", "onPinEntered, positiveResult = " + i);
        LoggingHelper.insertEventLogging(56, i == -1 ? 4515 : 4505);
        if (i == -2) {
            this.mIccLockManager.resetIccLockData();
            return;
        }
        this.mIccLockManager.mPin = this.mEditText.getText().toString();
        String str = this.mIccLockManager.mPin;
        if (str == null || str.length() < 4 || str.length() > 8) {
            this.mIccLockManager.mErrorMessage = getContext().getString(R.string.sim_bad_pin_4to8);
            show(getContext(), this.mIccLockManager);
            Log.i("SecIccLockSettings.SecSimPinDialog", "incorrect input");
            return;
        }
        SecIccLockManager secIccLockManager = this.mIccLockManager;
        int i2 = secIccLockManager.mIccLockMode;
        if (i2 == 1) {
            secIccLockManager.updateMenu();
            secIccLockManager
                    .new SetIccLockEnabled(secIccLockManager.mIccToState, secIccLockManager.mPin)
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
        } else {
            if (i2 != 4) {
                return;
            }
            if (!TextUtils.equals(secIccLockManager.mNewPin, secIccLockManager.mPin)) {
                this.mIccLockManager.setIccLockMode(4);
                this.mIccLockManager.mPin = null;
                show(getContext(), this.mIccLockManager);
            } else {
                SecIccLockManager secIccLockManager2 = this.mIccLockManager;
                secIccLockManager2.mErrorMessage = null;
                secIccLockManager2.updateMenu();
                secIccLockManager2
                        .new ChangeIccLockPin(
                                secIccLockManager2.mOldPin, secIccLockManager2.mNewPin)
                        .execute(new Void[0]);
                releaseDialog();
            }
        }
    }

    @Override // android.app.AlertDialog
    public final void setMessage(CharSequence charSequence) {
        this.mMessageView.setText(charSequence);
    }
}
