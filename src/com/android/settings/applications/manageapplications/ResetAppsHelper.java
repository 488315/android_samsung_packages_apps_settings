package com.android.settings.applications.manageapplications;

import android.app.AppOpsManager;
import android.app.INotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.net.NetworkPolicyManager;
import android.os.AsyncTask;
import android.os.ServiceManager;
import android.os.UserManager;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;

import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ResetAppsHelper
        implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    public final AppOpsManager mAom;
    public final Context mContext;
    public final IPackageManager mIPm =
            IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
    public final INotificationManager mNm =
            INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
    public final NetworkPolicyManager mNpm;
    public final PackageManager mPm;
    public AlertDialog mResetDialog;
    public final UserManager mUm;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.manageapplications.ResetAppsHelper$1, reason: invalid class name */
    public final class AnonymousClass1 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            LoggingHelper.insertEventLogging(65, 3833);
        }
    }

    public ResetAppsHelper(Context context) {
        this.mContext = context;
        this.mPm = context.getPackageManager();
        this.mNpm = NetworkPolicyManager.from(context);
        this.mAom = (AppOpsManager) context.getSystemService("appops");
        this.mUm = (UserManager) context.getSystemService("user");
    }

    public final void buildResetDialog() {
        if (this.mResetDialog == null) {
            String str =
                    this.mContext.getResources().getString(R.string.sec_reset_app_preferences_desc1)
                            + "\n\n• "
                            + this.mContext
                                    .getResources()
                                    .getString(R.string.sec_reset_app_preferences_desc2)
                            + "\n• "
                            + this.mContext
                                    .getResources()
                                    .getString(R.string.sec_reset_app_preferences_desc3)
                            + "\n• "
                            + this.mContext
                                    .getResources()
                                    .getString(R.string.sec_reset_app_preferences_desc4)
                            + "\n• "
                            + this.mContext
                                    .getResources()
                                    .getString(R.string.sec_reset_app_preferences_desc5)
                            + "\n• "
                            + this.mContext
                                    .getResources()
                                    .getString(R.string.sec_reset_app_preferences_desc6)
                            + "\n\n"
                            + this.mContext
                                    .getResources()
                                    .getString(R.string.sec_reset_app_preferences_desc7);
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mMessage = str;
            builder.setPositiveButton(R.string.reset_app_preferences_button, this);
            builder.setNegativeButton(R.string.cancel, new AnonymousClass1());
            alertParams.mOnDismissListener = this;
            this.mResetDialog = builder.show();
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (this.mResetDialog == dialogInterface) {
            AsyncTask.execute(new ResetAppsHelper$$ExternalSyntheticLambda0(this));
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        if (this.mResetDialog == dialogInterface) {
            this.mResetDialog = null;
        }
    }
}
