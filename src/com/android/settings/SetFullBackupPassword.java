package com.android.settings;

import android.app.Activity;
import android.app.backup.IBackupManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class SetFullBackupPassword extends Activity {
    public IBackupManager mBackupManager;
    public final AnonymousClass1 mButtonListener =
            new View.OnClickListener() { // from class: com.android.settings.SetFullBackupPassword.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SetFullBackupPassword setFullBackupPassword = SetFullBackupPassword.this;
                    if (view != setFullBackupPassword.mSet) {
                        if (view == setFullBackupPassword.mCancel) {
                            setFullBackupPassword.finish();
                            return;
                        } else {
                            Log.w("SetFullBackupPassword", "Click on unknown view");
                            return;
                        }
                    }
                    String charSequence = setFullBackupPassword.mCurrentPw.getText().toString();
                    String charSequence2 = SetFullBackupPassword.this.mNewPw.getText().toString();
                    if (!charSequence2.equals(
                            SetFullBackupPassword.this.mConfirmNewPw.getText().toString())) {
                        Log.i("SetFullBackupPassword", "password mismatch");
                        Toast.makeText(
                                        SetFullBackupPassword.this,
                                        R.string.local_backup_password_toast_confirmation_mismatch,
                                        1)
                                .show();
                        return;
                    }
                    SetFullBackupPassword setFullBackupPassword2 = SetFullBackupPassword.this;
                    setFullBackupPassword2.getClass();
                    boolean z = false;
                    if (!TextUtils.isEmpty(charSequence2)) {
                        try {
                            z =
                                    setFullBackupPassword2.mBackupManager.setBackupPassword(
                                            charSequence, charSequence2);
                        } catch (RemoteException unused) {
                            Log.e(
                                    "SetFullBackupPassword",
                                    "Unable to communicate with backup manager");
                        }
                    }
                    if (!z) {
                        Log.i("SetFullBackupPassword", "failure; password mismatch?");
                        Toast.makeText(
                                        SetFullBackupPassword.this,
                                        R.string.local_backup_password_toast_validation_failure,
                                        1)
                                .show();
                    } else {
                        Log.i("SetFullBackupPassword", "password set successfully");
                        Toast.makeText(
                                        SetFullBackupPassword.this,
                                        R.string.local_backup_password_toast_success,
                                        1)
                                .show();
                        SetFullBackupPassword.this.finish();
                    }
                }
            };
    public Button mCancel;
    public TextView mConfirmNewPw;
    public TextView mCurrentPw;
    public TextView mNewPw;
    public Button mSet;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(8192);
        this.mBackupManager = IBackupManager.Stub.asInterface(ServiceManager.getService("backup"));
        setContentView(R.layout.set_backup_pw);
        this.mCurrentPw = (TextView) findViewById(R.id.current_backup_pw);
        this.mNewPw = (TextView) findViewById(R.id.new_backup_pw);
        this.mConfirmNewPw = (TextView) findViewById(R.id.confirm_new_backup_pw);
        this.mCancel = (Button) findViewById(R.id.backup_pw_cancel_button);
        this.mSet = (Button) findViewById(R.id.backup_pw_set_button);
        this.mCancel.setOnClickListener(this.mButtonListener);
        this.mSet.setOnClickListener(this.mButtonListener);
    }
}
