package com.android.settings.enterprise;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;

import androidx.appcompat.app.AlertDialog;

import com.android.settingslib.RestrictedLockUtils;

import com.samsung.android.knox.EnterpriseDeviceManager;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ActionDisabledByAdminDialog extends Activity
        implements DialogInterface.OnDismissListener {
    public ActionDisabledByAdminDialogHelper mDialogHelper;

    public RestrictedLockUtils.EnforcedAdmin getAdminDetailsFromIntent(Intent intent) {
        Bundle bundle;
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin =
                new RestrictedLockUtils.EnforcedAdmin(null, UserHandle.of(UserHandle.myUserId()));
        if (intent == null) {
            return enforcedAdmin;
        }
        enforcedAdmin.component =
                (ComponentName)
                        intent.getParcelableExtra(EnterpriseDeviceManager.EXTRA_DEVICE_ADMIN);
        int intExtra = intent.getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        if (enforcedAdmin.component == null) {
            bundle =
                    ((DevicePolicyManager) getSystemService(DevicePolicyManager.class))
                            .getEnforcingAdminAndUserDetails(
                                    intExtra, getRestrictionFromIntent(intent));
            if (bundle != null) {
                enforcedAdmin.component =
                        (ComponentName)
                                bundle.getParcelable(EnterpriseDeviceManager.EXTRA_DEVICE_ADMIN);
            }
        } else {
            bundle = null;
        }
        if (intent.hasExtra("android.intent.extra.USER")) {
            enforcedAdmin.user =
                    (UserHandle) intent.getParcelableExtra("android.intent.extra.USER");
        } else {
            if (bundle != null) {
                intExtra = bundle.getInt("android.intent.extra.USER_ID", UserHandle.myUserId());
            }
            if (intExtra == -10000) {
                enforcedAdmin.user = null;
            } else {
                enforcedAdmin.user = UserHandle.of(intExtra);
            }
        }
        return enforcedAdmin;
    }

    public String getRestrictionFromIntent(Intent intent) {
        if (intent == null) {
            return null;
        }
        return intent.getStringExtra("android.app.extra.RESTRICTION");
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        RestrictedLockUtils.EnforcedAdmin adminDetailsFromIntent =
                getAdminDetailsFromIntent(getIntent());
        String restrictionFromIntent = getRestrictionFromIntent(getIntent());
        ActionDisabledByAdminDialogHelper actionDisabledByAdminDialogHelper =
                new ActionDisabledByAdminDialogHelper(this, restrictionFromIntent);
        this.mDialogHelper = actionDisabledByAdminDialogHelper;
        AlertDialog.Builder prepareDialogBuilder =
                actionDisabledByAdminDialogHelper.prepareDialogBuilder(
                        restrictionFromIntent, adminDetailsFromIntent);
        prepareDialogBuilder.P.mOnDismissListener = this;
        prepareDialogBuilder.show();
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        finish();
    }

    @Override // android.app.Activity
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        RestrictedLockUtils.EnforcedAdmin adminDetailsFromIntent =
                getAdminDetailsFromIntent(intent);
        String restrictionFromIntent = getRestrictionFromIntent(intent);
        ActionDisabledByAdminDialogHelper actionDisabledByAdminDialogHelper = this.mDialogHelper;
        if (actionDisabledByAdminDialogHelper.mEnforcedAdmin.equals(adminDetailsFromIntent)
                && Objects.equals(
                        actionDisabledByAdminDialogHelper.mRestriction, restrictionFromIntent)) {
            return;
        }
        actionDisabledByAdminDialogHelper.mEnforcedAdmin = adminDetailsFromIntent;
        actionDisabledByAdminDialogHelper.mRestriction = restrictionFromIntent;
        actionDisabledByAdminDialogHelper.initializeDialogViews(
                actionDisabledByAdminDialogHelper.mDialogView,
                adminDetailsFromIntent,
                ActionDisabledByAdminDialogHelper.getEnforcementAdminUserId(adminDetailsFromIntent),
                actionDisabledByAdminDialogHelper.mRestriction);
    }
}
