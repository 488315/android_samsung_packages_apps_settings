package com.android.settings.applications.credentials;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DefaultWorkCombinedPreferenceController
        extends DefaultCombinedPreferenceController {
    public final UserHandle mUserHandle;

    public DefaultWorkCombinedPreferenceController(Context context) {
        super(context);
        this.mUserHandle = Utils.getManagedProfile(this.mUserManager);
    }

    @Override // com.android.settings.applications.credentials.DefaultCombinedPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "default_credman_autofill_main_work";
    }

    @Override // com.android.settings.applications.credentials.DefaultCombinedPreferenceController
    public final int getUser() {
        return this.mUserHandle.getIdentifier();
    }

    @Override // com.android.settings.applications.credentials.DefaultCombinedPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        UserHandle userHandle = this.mUserHandle;
        if (userHandle == null) {
            return false;
        }
        if (this.mUserManager.hasUserRestrictionForUser(
                "no_autofill", UserHandle.of(userHandle.getIdentifier()))) {
            return false;
        }
        return super.isAvailable();
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final void startActivity(Intent intent) {
        this.mContext.startActivityAsUser(intent, this.mUserHandle);
    }
}
