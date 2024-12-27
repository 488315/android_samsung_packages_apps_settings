package com.android.settingslib.enterprise;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.android.settings.R;
import com.android.settingslib.RestrictedLockUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BiometricActionDisabledByAdminController
        extends BaseActionDisabledByAdminController {
    @Override // com.android.settingslib.enterprise.BaseActionDisabledByAdminController
    public final CharSequence getAdminSupportContentString(CharSequence charSequence) {
        return this.mStringProvider.mContext.getString(
                R.string.disabled_by_policy_content_biometric_parental_consent);
    }

    @Override // com.android.settingslib.enterprise.BaseActionDisabledByAdminController
    public final String getAdminSupportTitle() {
        return this.mStringProvider.mContext.getString(
                R.string.disabled_by_policy_title_biometric_parental_consent);
    }

    @Override // com.android.settingslib.enterprise.BaseActionDisabledByAdminController
    public final DialogInterface.OnClickListener getPositiveButtonListener(
            final Context context, final RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        return new DialogInterface
                .OnClickListener() { // from class:
                                     // com.android.settingslib.enterprise.BiometricActionDisabledByAdminController$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                RestrictedLockUtils.EnforcedAdmin enforcedAdmin2 = enforcedAdmin;
                Context context2 = context;
                Log.d(
                        "BiometricActionDisabledByAdminController",
                        "Positive button clicked, component: " + enforcedAdmin2.component);
                context2.startActivity(
                        new Intent("android.settings.MANAGE_SUPERVISOR_RESTRICTED_SETTING")
                                .putExtra(
                                        "android.provider.extra.SUPERVISOR_RESTRICTED_SETTING_KEY",
                                        1)
                                .setData(
                                        new Uri.Builder()
                                                .scheme("policy")
                                                .appendPath("biometric")
                                                .build())
                                .setPackage(enforcedAdmin2.component.getPackageName()));
            }
        };
    }

    @Override // com.android.settingslib.enterprise.BaseActionDisabledByAdminController
    public final void setupLearnMoreButton(Context context) {}
}
