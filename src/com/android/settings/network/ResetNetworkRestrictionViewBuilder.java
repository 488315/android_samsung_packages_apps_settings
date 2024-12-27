package com.android.settings.network;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.UserHandle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.enterprise.ActionDisabledByAdminDialogHelper;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ResetNetworkRestrictionViewBuilder extends NetworkResetRestrictionChecker {
    static final String mRestriction = "no_network_reset";
    public final Activity mActivity;

    public ResetNetworkRestrictionViewBuilder(Activity activity) {
        super(activity);
        this.mActivity = activity;
    }

    public final View build() {
        if (!this.mUserManager.isAdminUser() || hasUserBaseRestriction()) {
            return operationNotAllow();
        }
        RestrictedLockUtils.EnforcedAdmin enforceAdminByRestriction =
                getEnforceAdminByRestriction();
        if (enforceAdminByRestriction == null) {
            return null;
        }
        AlertDialog.Builder createRestrictDialogBuilder =
                createRestrictDialogBuilder(enforceAdminByRestriction);
        createRestrictDialogBuilder.P.mOnDismissListener =
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.android.settings.network.ResetNetworkRestrictionViewBuilder$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        ResetNetworkRestrictionViewBuilder.this.mActivity.finish();
                    }
                };
        createRestrictDialogBuilder.show();
        return createEmptyView();
    }

    public View createEmptyView() {
        return new ViewStub(this.mActivity);
    }

    public AlertDialog.Builder createRestrictDialogBuilder(
            RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        return new ActionDisabledByAdminDialogHelper(this.mActivity, null)
                .prepareDialogBuilder(mRestriction, enforcedAdmin);
    }

    public RestrictedLockUtils.EnforcedAdmin getEnforceAdminByRestriction() {
        return RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                this.mActivity, UserHandle.myUserId(), mRestriction);
    }

    public LayoutInflater getLayoutInflater() {
        return this.mActivity.getLayoutInflater();
    }

    public View operationNotAllow() {
        return getLayoutInflater()
                .inflate(R.layout.network_reset_disallowed_screen, (ViewGroup) null);
    }
}
