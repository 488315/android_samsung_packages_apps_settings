package com.android.settings.applications.credentials;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DefaultPrivateCombinedPreferenceController
        extends DefaultCombinedPreferenceController {
    public final UserHandle userHandle;

    public DefaultPrivateCombinedPreferenceController(Context context) {
        super(context);
        this.userHandle = Utils.getProfileOfType(this.mUserManager, 4);
    }

    @Override // com.android.settings.applications.credentials.DefaultCombinedPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "default_credman_autofill_private";
    }

    @Override // com.android.settings.applications.credentials.DefaultCombinedPreferenceController
    public final int getUser() {
        UserHandle userHandle = this.userHandle;
        return userHandle != null ? userHandle.getIdentifier() : UserHandle.myUserId();
    }

    @Override // com.android.settings.applications.credentials.DefaultCombinedPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (this.userHandle == null) {
            return false;
        }
        return super.isAvailable();
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final void startActivity(Intent intent) {
        UserHandle userHandle = this.userHandle;
        if (userHandle != null) {
            this.mContext.startActivityAsUser(intent, userHandle);
        }
    }
}