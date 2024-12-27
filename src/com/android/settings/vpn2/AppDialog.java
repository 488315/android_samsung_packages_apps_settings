package com.android.settings.vpn2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.UserHandle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppDialog extends AlertDialog implements DialogInterface.OnClickListener {
    public final String mLabel;
    public final AppDialogFragment mListener;
    public final PackageInfo mPackageInfo;

    public AppDialog(
            FragmentActivity fragmentActivity,
            AppDialogFragment appDialogFragment,
            PackageInfo packageInfo,
            String str) {
        super(fragmentActivity, 0);
        this.mListener = appDialogFragment;
        this.mPackageInfo = packageInfo;
        this.mLabel = str;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -2) {
            AppDialogFragment appDialogFragment = this.mListener;
            if (!appDialogFragment.isUiRestricted$1()) {
                appDialogFragment.mVpnManager.setVpnPackageAuthorization(
                        appDialogFragment.mPackageInfo.packageName,
                        UserHandle.getUserId(appDialogFragment.mPackageInfo.applicationInfo.uid),
                        -1);
                appDialogFragment.onDisconnect();
                AppManagementFragment.AnonymousClass1 anonymousClass1 = appDialogFragment.mListener;
                if (anonymousClass1 != null) {
                    AppManagementFragment appManagementFragment = AppManagementFragment.this;
                    if (appManagementFragment.mPackageName.equals(
                            appManagementFragment.mVpnManager.getAlwaysOnVpnPackageForUser(
                                    appManagementFragment.mUserId))) {
                        appManagementFragment.mVpnManager.setAlwaysOnVpnPackageForUser(
                                appManagementFragment.mUserId, null, false, null);
                    }
                    appManagementFragment.finish();
                }
            }
        }
        dismiss();
    }

    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog,
              // androidx.activity.ComponentDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        setTitle(this.mLabel);
        setMessage(getContext().getString(R.string.vpn_version, this.mPackageInfo.versionName));
        Context context = getContext();
        setButton(-2, context.getString(R.string.vpn_forget), this);
        setButton(-1, context.getString(R.string.vpn_done), this);
        super.onCreate(bundle);
    }
}
