package com.android.settings.users;

import android.os.UserHandle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AutoSyncPrivateDataPreferenceController
        extends AutoSyncDataPreferenceController {
    @Override // com.android.settings.users.AutoSyncDataPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "auto_sync_private_account_data";
    }

    @Override // com.android.settings.users.AutoSyncDataPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        UserHandle userHandle = this.mUserHandle;
        return userHandle != null
                && this.mUserManager.getUserInfo(userHandle.getIdentifier()).isPrivateProfile();
    }
}
