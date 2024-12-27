package com.android.settingslib.enterprise;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.UserHandle;
import android.os.UserManager;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.enterprise.ActionDisabledLearnMoreButtonLauncherImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ActionDisabledLearnMoreButtonLauncher {
    @VisibleForTesting
    public boolean isSameProfileGroup(Context context, int i) {
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        return userManager.isSameProfileGroup(i, userManager.getProcessUserId());
    }

    @VisibleForTesting
    public void showHelpPage(Context context, String str, UserHandle userHandle) {
        context.startActivityAsUser(
                new Intent("android.intent.action.VIEW", Uri.parse(str)).setFlags(276824064),
                userHandle);
        ((ActionDisabledLearnMoreButtonLauncherImpl) this).mActivity.finish();
    }
}
