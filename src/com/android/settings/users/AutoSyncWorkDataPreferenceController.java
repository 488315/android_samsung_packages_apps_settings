package com.android.settings.users;

import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.account.AccountUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AutoSyncWorkDataPreferenceController extends AutoSyncDataPreferenceController {
    public AutoSyncWorkDataPreferenceController(
            Context context, PreferenceFragmentCompat preferenceFragmentCompat) {
        super(context, preferenceFragmentCompat);
        UserManager userManager = this.mUserManager;
        StringBuilder sb = Utils.sBuilder;
        this.mUserHandle = Utils.getManagedProfileWithDisabled(userManager, UserHandle.myUserId());
        if (SemPersonaManager.isKnoxId(UserHandle.myUserId())) {
            this.mUserHandle = UserHandle.of(UserHandle.myUserId());
        }
    }

    @Override // com.android.settings.users.AutoSyncDataPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "auto_sync_work_account_data";
    }

    @Override // com.android.settings.users.AutoSyncDataPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        UserManager userManager = this.mUserManager;
        StringBuilder sb = Utils.sBuilder;
        this.mUserHandle = Utils.getManagedProfileWithDisabled(userManager, UserHandle.myUserId());
        if (SemPersonaManager.isKnoxId(UserHandle.myUserId())) {
            this.mUserHandle = UserHandle.of(UserHandle.myUserId());
        }
        Utils.getManagedProfile(this.mUserManager);
        List<UserHandle> userProfiles = this.mUserManager.getUserProfiles();
        AccountUtils.removeDualAppManagedProfiles(userProfiles);
        if (AccountUtils.isKnoxActivated(this.mContext)) {
            int i = 0;
            while (i < userProfiles.size()) {
                if (SemPersonaManager.isSecureFolderId(userProfiles.get(i).getIdentifier())
                        || SemPersonaManager.isAppSeparationUserId(
                                userProfiles.get(i).getIdentifier())) {
                    userProfiles.remove(i);
                    i--;
                }
                i++;
            }
        }
        return SemPersonaManager.isSepLiteDevice(this.mContext)
                ? (((ArrayList) Utils.getManagedProfiles(this.mUserManager)).size() == 0
                                || this.mUserManager.isLinkedUser()
                                || userProfiles.size() <= 1)
                        ? false
                        : true
                : (!AccountUtils.isSecureFolderActivated(this.mContext)
                                || AccountUtils.isKnoxActivated(this.mContext)
                                || SemPersonaManager.isKnoxId(UserHandle.myUserId()))
                        && this.mUserHandle != null
                        && !this.mUserManager.isLinkedUser()
                        && userProfiles.size() > 1;
    }

    @Override // com.android.settings.users.AutoSyncDataPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        SecSwitchPreference secSwitchPreference = (SecSwitchPreference) preference;
        if (SemPersonaManager.isSecureFolderId(UserHandle.myUserId())) {
            secSwitchPreference.setTitle(R.string.account_settings_menu_auto_sync_securefolder);
        } else if (isAvailable()) {
            secSwitchPreference.setTitle(R.string.account_settings_menu_auto_sync_work);
        }
    }
}
