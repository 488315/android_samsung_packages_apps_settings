package com.android.settings.biometrics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;

import com.android.internal.app.UnlaunchableAppActivity;
import com.android.settings.DisplaySettings$$ExternalSyntheticOutline0;
import com.android.settingslib.RestrictedLockUtils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BiometricNavigationUtils {
    public final int mUserId;

    public BiometricNavigationUtils(int i) {
        this.mUserId = i;
    }

    public final Intent getBiometricSettingsIntent(
            Context context,
            String str,
            RestrictedLockUtils.EnforcedAdmin enforcedAdmin,
            Bundle bundle) {
        int i = this.mUserId;
        if (enforcedAdmin == null) {
            Intent createInQuietModeDialogIntent =
                    UserManager.get(context).isQuietModeEnabled(UserHandle.of(i))
                            ? UnlaunchableAppActivity.createInQuietModeDialogIntent(i)
                            : null;
            return createInQuietModeDialogIntent != null
                    ? createInQuietModeDialogIntent
                    : getSettingsPageIntent(bundle, str);
        }
        Intent showAdminSupportDetailsIntent =
                RestrictedLockUtils.getShowAdminSupportDetailsIntent(enforcedAdmin);
        UserHandle userHandle = enforcedAdmin.user;
        if (userHandle != null
                && RestrictedLockUtils.isCurrentUserOrProfile(
                        context, userHandle.getIdentifier())) {
            i = enforcedAdmin.user.getIdentifier();
        }
        showAdminSupportDetailsIntent.putExtra(
                "android.app.extra.RESTRICTION", enforcedAdmin.enforcedRestriction);
        showAdminSupportDetailsIntent.putExtra("android.intent.extra.USER_ID", i);
        return showAdminSupportDetailsIntent;
    }

    public final Intent getSettingsPageIntent(Bundle bundle, String str) {
        Intent m =
                DisplaySettings$$ExternalSyntheticOutline0.m(
                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, str);
        if (!bundle.isEmpty()) {
            m.putExtras(bundle);
        }
        m.putExtra("from_settings_summary", true);
        m.putExtra("android.intent.extra.USER_ID", this.mUserId);
        m.putExtra("page_transition_type", 1);
        return m;
    }
}
