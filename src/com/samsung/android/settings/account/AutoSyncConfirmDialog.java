package com.samsung.android.settings.account;

import android.R;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;

import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AutoSyncConfirmDialog extends AlertActivity
        implements DialogInterface.OnClickListener {
    public boolean mEnabling;
    public UserHandle mUserHandle;

    public final void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        super.onApplyThemeResource(
                theme,
                (getResources().getConfiguration().uiMode & 48) == 32
                        ? R.style.Theme.DeviceDefault.Dialog.Alert
                        : R.style.Theme.DeviceDefault.Light.Dialog.Alert,
                z);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            UserHandle userHandle = this.mUserHandle;
            if (userHandle != null) {
                ContentResolver.setMasterSyncAutomaticallyAsUser(
                        this.mEnabling, userHandle.getIdentifier());
            } else {
                ContentResolver.setMasterSyncAutomaticallyAsUser(
                        this.mEnabling, UserHandle.getCallingUserId());
            }
            getApplicationContext()
                    .sendBroadcast(
                            new Intent("android.settings.AUTO_SYNC_SETTINGS"),
                            "android.permission.WRITE_SYNC_SETTINGS");
        }
        finish();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mUserHandle = Process.myUserHandle();
        Intent intent = getIntent();
        if (intent.hasExtra("check_state")) {
            this.mEnabling = intent.getBooleanExtra("check_state", true);
        }
        AlertController.AlertParams alertParams = ((AlertActivity) this).mAlertParams;
        if (this.mEnabling) {
            alertParams.mTitle =
                    getResources()
                            .getString(
                                    com.android.settings.R.string
                                            .data_usage_auto_sync_on_dialog_title);
            alertParams.mMessage =
                    getResources()
                            .getString(
                                    com.android.settings.R.string.data_usage_auto_sync_on_dialog);
        } else {
            alertParams.mTitle =
                    getResources()
                            .getString(
                                    com.android.settings.R.string
                                            .data_usage_auto_sync_off_dialog_title);
            alertParams.mMessage =
                    getResources()
                            .getString(
                                    com.android.settings.R.string.data_usage_auto_sync_off_dialog);
        }
        alertParams.mPositiveButtonText =
                getResources().getString(com.android.settings.R.string.common_ok);
        alertParams.mNegativeButtonText =
                getResources().getString(com.android.settings.R.string.cancel);
        alertParams.mPositiveButtonListener = this;
        alertParams.mNegativeButtonListener = this;
        getWindow().setGravity(80);
        setupAlert();
    }
}
