package com.samsung.android.settings.lockscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DeletePreviousActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Button mCancelButton;
    public Button mDeleteButton;
    public TextView mDescriptionTextView;
    public ViewGroup mRootView;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ViewGroup viewGroup =
                (ViewGroup)
                        LayoutInflater.from(this)
                                .inflate(
                                        R.layout.sec_secure_lock_settings_delete_previous_pin,
                                        (ViewGroup) null);
        this.mRootView = viewGroup;
        setContentView(viewGroup);
        getWindow().setGravity(80);
        setFinishOnTouchOutside(false);
        final LockPatternUtils lockPatternUtils = new LockPatternUtils(this);
        ((Toolbar) this.mRootView.findViewById(R.id.action_bar)).setVisibility(8);
        this.mDescriptionTextView = (TextView) findViewById(R.id.description);
        int credentialTypeForUser = lockPatternUtils.getCredentialTypeForUser(-9899);
        if (credentialTypeForUser == 1) {
            this.mDescriptionTextView.setText(
                    getResources()
                            .getString(
                                    R.string
                                            .sec_secured_lock_settings_delete_previous_pattern_dialog_description));
        } else if (credentialTypeForUser == 3) {
            this.mDescriptionTextView.setText(
                    getResources()
                            .getString(
                                    R.string
                                            .sec_secured_lock_settings_delete_previous_pin_dialog_description));
        } else if (credentialTypeForUser == 4) {
            this.mDescriptionTextView.setText(
                    getResources()
                            .getString(
                                    R.string
                                            .sec_secured_lock_settings_delete_previous_password_dialog_description));
        }
        this.mDeleteButton = (Button) findViewById(R.id.delete_button);
        this.mCancelButton = (Button) findViewById(R.id.cancel_button);
        this.mDeleteButton.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.lockscreen.DeletePreviousActivity$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        DeletePreviousActivity deletePreviousActivity = DeletePreviousActivity.this;
                        LockPatternUtils lockPatternUtils2 = lockPatternUtils;
                        int i = DeletePreviousActivity.$r8$clinit;
                        deletePreviousActivity.getClass();
                        lockPatternUtils2.expirePreviousData();
                        deletePreviousActivity.setResult(-1);
                        deletePreviousActivity.finish();
                    }
                });
        this.mCancelButton.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.lockscreen.DeletePreviousActivity$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        DeletePreviousActivity deletePreviousActivity = DeletePreviousActivity.this;
                        int i = DeletePreviousActivity.$r8$clinit;
                        deletePreviousActivity.setResult(0);
                        deletePreviousActivity.finish();
                    }
                });
    }
}
