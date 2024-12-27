package com.android.settingslib.enterprise;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.UserHandle;
import com.android.internal.util.Preconditions;
import com.android.settings.R;
import com.android.settings.Settings;
import com.android.settings.applications.specialaccess.deviceadmin.DeviceAdminAdd;
import com.android.settings.enterprise.ActionDisabledLearnMoreButtonLauncherImpl;
import com.android.settingslib.RestrictedLockUtils;
import com.samsung.android.knox.EnterpriseDeviceManager;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FinancedDeviceActionDisabledByAdminController extends BaseActionDisabledByAdminController {
    @Override // com.android.settingslib.enterprise.BaseActionDisabledByAdminController
    public final String getAdminSupportTitle() {
        return this.mStringProvider.mContext.getString(R.string.disabled_by_policy_title_financed_device);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.settingslib.enterprise.ActionDisabledLearnMoreButtonLauncher$$ExternalSyntheticLambda0] */
    @Override // com.android.settingslib.enterprise.BaseActionDisabledByAdminController
    public final void setupLearnMoreButton(final Context context) {
        Preconditions.checkState(this.mLauncher != null, "must call initialize() first");
        ActionDisabledLearnMoreButtonLauncher actionDisabledLearnMoreButtonLauncher = this.mLauncher;
        int i = this.mEnforcementAdminUserId;
        final RestrictedLockUtils.EnforcedAdmin enforcedAdmin = this.mEnforcedAdmin;
        actionDisabledLearnMoreButtonLauncher.getClass();
        Objects.requireNonNull(context, "context cannot be null");
        if (actionDisabledLearnMoreButtonLauncher.isSameProfileGroup(context, i) || (i == 0 && i == ((DevicePolicyManager) context.getSystemService(DevicePolicyManager.class)).getDeviceOwnerUserId())) {
            final ActionDisabledLearnMoreButtonLauncherImpl actionDisabledLearnMoreButtonLauncherImpl = (ActionDisabledLearnMoreButtonLauncherImpl) actionDisabledLearnMoreButtonLauncher;
            final ?? r1 = new Runnable() { // from class: com.android.settingslib.enterprise.ActionDisabledLearnMoreButtonLauncher$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ComponentName componentName;
                    ActionDisabledLearnMoreButtonLauncher actionDisabledLearnMoreButtonLauncher2 = actionDisabledLearnMoreButtonLauncherImpl;
                    Context context2 = context;
                    RestrictedLockUtils.EnforcedAdmin enforcedAdmin2 = enforcedAdmin;
                    actionDisabledLearnMoreButtonLauncher2.getClass();
                    if (enforcedAdmin2 == null || (componentName = enforcedAdmin2.component) == null) {
                        Objects.requireNonNull(context2, "context cannot be null");
                        Intent intent = new Intent();
                        Activity activity = ((ActionDisabledLearnMoreButtonLauncherImpl) actionDisabledLearnMoreButtonLauncher2).mActivity;
                        activity.startActivity(intent.setClass(activity, Settings.DeviceAdminSettingsActivity.class).addFlags(268435456));
                    } else {
                        UserHandle userHandle = enforcedAdmin2.user;
                        Objects.requireNonNull(context2, "context cannot be null");
                        Objects.requireNonNull(userHandle, "user cannot be null");
                        Intent intent2 = new Intent();
                        Activity activity2 = ((ActionDisabledLearnMoreButtonLauncherImpl) actionDisabledLearnMoreButtonLauncher2).mActivity;
                        activity2.startActivityAsUser(intent2.setClass(activity2, DeviceAdminAdd.class).putExtra(EnterpriseDeviceManager.EXTRA_DEVICE_ADMIN, componentName).putExtra("android.app.extra.CALLED_FROM_SUPPORT_DIALOG", true), userHandle);
                    }
                    ((ActionDisabledLearnMoreButtonLauncherImpl) actionDisabledLearnMoreButtonLauncher2).mActivity.finish();
                }
            };
            actionDisabledLearnMoreButtonLauncherImpl.mBuilder.setNeutralButton(R.string.learn_more, new DialogInterface.OnClickListener() { // from class: com.android.settings.enterprise.ActionDisabledLearnMoreButtonLauncherImpl$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i2) {
                    r1.run();
                }
            });
        }
    }

    @Override // com.android.settingslib.enterprise.BaseActionDisabledByAdminController
    public final CharSequence getAdminSupportContentString(CharSequence charSequence) {
        return charSequence;
    }
}
