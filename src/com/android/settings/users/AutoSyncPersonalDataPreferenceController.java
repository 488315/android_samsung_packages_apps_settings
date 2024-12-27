package com.android.settings.users;

import android.content.Context;
import android.os.UserHandle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.account.AccountUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AutoSyncPersonalDataPreferenceController
        extends AutoSyncDataPreferenceController {
    public AutoSyncPersonalDataPreferenceController(
            Context context, PreferenceFragmentCompat preferenceFragmentCompat) {
        super(context, preferenceFragmentCompat);
        if (AccountUtils.isKnoxActivated(context)) {
            this.mUserHandle = UserHandle.SYSTEM;
        }
    }

    @Override // com.android.settings.users.AutoSyncDataPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "auto_sync_personal_account_data";
    }

    @Override // com.android.settings.users.AutoSyncDataPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        List<UserHandle> userProfiles = this.mUserManager.getUserProfiles();
        AccountUtils.removeDualAppManagedProfiles(userProfiles);
        return AccountUtils.isKnoxActivated(this.mContext)
                ? (this.mUserManager.isLinkedUser()
                                || userProfiles.size() <= 1
                                || SemPersonaManager.isSecureFolderId(UserHandle.myUserId()))
                        ? false
                        : true
                : (this.mUserManager.isManagedProfile()
                                || this.mUserManager.isLinkedUser()
                                || userProfiles.size() <= 1)
                        ? false
                        : true;
    }

    @Override // com.android.settings.users.AutoSyncDataPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        SecSwitchPreference secSwitchPreference = (SecSwitchPreference) preference;
        if (AccountUtils.isSecureFolderActivated(this.mContext)
                && !AccountUtils.isKnoxActivated(this.mContext)
                && !SemPersonaManager.isKnoxId(UserHandle.myUserId())) {
            secSwitchPreference.setTitle(R.string.auto_sync_account_title);
        } else if (isAvailable()) {
            secSwitchPreference.setTitle(R.string.account_settings_menu_auto_sync_personal);
        }
    }
}
