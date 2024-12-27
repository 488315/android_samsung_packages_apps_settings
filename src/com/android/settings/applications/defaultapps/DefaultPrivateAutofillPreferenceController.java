package com.android.settings.applications.defaultapps;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.provider.Settings;

import com.android.settings.Utils;
import com.android.settingslib.applications.DefaultAppInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DefaultPrivateAutofillPreferenceController
        extends DefaultAutofillPreferenceController {
    public final UserHandle userHandle;

    public DefaultPrivateAutofillPreferenceController(Context context) {
        super(context);
        this.userHandle = Utils.getProfileOfType(this.mUserManager, 4);
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAutofillPreferenceController,
              // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final DefaultAppInfo getDefaultAppInfo() {
        UserHandle userHandle;
        UserHandle userHandle2 = this.userHandle;
        String stringForUser =
                userHandle2 != null
                        ? Settings.Secure.getStringForUser(
                                this.mContext.getContentResolver(),
                                "autofill_service",
                                userHandle2.getIdentifier())
                        : null;
        if (stringForUser == null
                || stringForUser.length() == 0
                || (userHandle = this.userHandle) == null) {
            return null;
        }
        return new DefaultAppInfo(
                this.mContext,
                this.mPackageManager,
                userHandle.getIdentifier(),
                ComponentName.unflattenFromString(stringForUser));
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAutofillPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "default_autofill_private";
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAutofillPreferenceController,
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
        if (userHandle == null) {
            this.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
        } else {
            this.mContext.startActivityAsUser(intent, userHandle);
        }
    }
}
