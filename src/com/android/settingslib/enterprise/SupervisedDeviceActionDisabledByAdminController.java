package com.android.settingslib.enterprise;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.enterprise.DeviceAdminStringProviderImpl;
import com.android.settingslib.RestrictedLockUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SupervisedDeviceActionDisabledByAdminController
        extends BaseActionDisabledByAdminController {
    public final String mRestriction;

    public SupervisedDeviceActionDisabledByAdminController(
            DeviceAdminStringProviderImpl deviceAdminStringProviderImpl, String str) {
        super(deviceAdminStringProviderImpl);
        this.mRestriction = str;
    }

    @Override // com.android.settingslib.enterprise.BaseActionDisabledByAdminController
    public final CharSequence getAdminSupportContentString(CharSequence charSequence) {
        return this.mStringProvider.mContext.getString(
                R.string.disabled_by_policy_parental_consent);
    }

    @Override // com.android.settingslib.enterprise.BaseActionDisabledByAdminController
    public final String getAdminSupportTitle() {
        return this.mStringProvider.mContext.getString(
                R.string.disabled_by_policy_title_biometric_parental_consent);
    }

    @Override // com.android.settingslib.enterprise.BaseActionDisabledByAdminController
    public final DialogInterface.OnClickListener getPositiveButtonListener(
            final Context context, RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        ComponentName componentName = enforcedAdmin.component;
        if (componentName == null || TextUtils.isEmpty(componentName.getPackageName())) {
            return null;
        }
        final Intent intent =
                new Intent("android.settings.MANAGE_SUPERVISOR_RESTRICTED_SETTING")
                        .setData(
                                new Uri.Builder()
                                        .scheme("policy")
                                        .appendPath("user_restrictions")
                                        .appendPath(this.mRestriction)
                                        .build())
                        .setPackage(enforcedAdmin.component.getPackageName());
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return null;
        }
        return new DialogInterface
                .OnClickListener() { // from class:
                                     // com.android.settingslib.enterprise.SupervisedDeviceActionDisabledByAdminController$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                context.startActivity(intent);
            }
        };
    }

    @Override // com.android.settingslib.enterprise.BaseActionDisabledByAdminController
    public final void setupLearnMoreButton(Context context) {}
}
