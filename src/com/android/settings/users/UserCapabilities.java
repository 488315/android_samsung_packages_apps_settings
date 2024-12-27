package com.android.settings.users;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;

import androidx.compose.animation.ChangeSize$$ExternalSyntheticOutline0;

import com.android.settings.Utils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UserCapabilities {
    public boolean mCanAddGuest;
    public boolean mCanAddRestrictedProfile;
    public boolean mCanAddUser;
    public boolean mDisallowAddUser;
    public boolean mDisallowAddUserSetByAdmin;
    public boolean mDisallowSwitchUser;
    public boolean mEnabled;
    public RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    public boolean mIsAdmin;
    public boolean mIsEphemeral;
    public boolean mIsGuest;
    public boolean mIsMain;
    public boolean mUserSwitcherEnabled;

    public static UserCapabilities create(Context context) {
        UserManager userManager = (UserManager) context.getSystemService("user");
        UserCapabilities userCapabilities = new UserCapabilities();
        boolean z = true;
        userCapabilities.mEnabled = true;
        userCapabilities.mCanAddUser = true;
        if (UserManager.supportsMultipleUsers()) {
            StringBuilder sb = Utils.sBuilder;
            if (!ActivityManager.isUserAMonkey()) {
                UserInfo userInfo = userManager.getUserInfo(UserHandle.myUserId());
                userCapabilities.mIsGuest = userInfo.isGuest();
                userCapabilities.mIsAdmin = userInfo.isAdmin();
                userCapabilities.mIsMain = userInfo.isMain();
                userCapabilities.mIsEphemeral = userInfo.isEphemeral();
                DevicePolicyManager devicePolicyManager =
                        (DevicePolicyManager) context.getSystemService("device_policy");
                if (!Rune.supportSoftphone()
                        && (devicePolicyManager.isDeviceManaged()
                                || Utils.isVoiceCapable(context))) {
                    z = false;
                }
                userCapabilities.mCanAddRestrictedProfile = z;
                userCapabilities.updateAddUserCapabilities(context);
                return userCapabilities;
            }
        }
        userCapabilities.mEnabled = false;
        return userCapabilities;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("UserCapabilities{mEnabled=");
        sb.append(this.mEnabled);
        sb.append(", mCanAddUser=");
        sb.append(this.mCanAddUser);
        sb.append(", mCanAddRestrictedProfile=");
        sb.append(this.mCanAddRestrictedProfile);
        sb.append(", mIsAdmin=");
        sb.append(this.mIsAdmin);
        sb.append(", mIsGuest=");
        sb.append(this.mIsGuest);
        sb.append(", mCanAddGuest=");
        sb.append(this.mCanAddGuest);
        sb.append(", mDisallowAddUser=");
        sb.append(this.mDisallowAddUser);
        sb.append(", mEnforcedAdmin=");
        sb.append(this.mEnforcedAdmin);
        sb.append(", mDisallowSwitchUser=");
        sb.append(this.mDisallowSwitchUser);
        sb.append(", mDisallowAddUserSetByAdmin=");
        sb.append(this.mDisallowAddUserSetByAdmin);
        sb.append(", mUserSwitcherEnabled=");
        return ChangeSize$$ExternalSyntheticOutline0.m(sb, this.mUserSwitcherEnabled, '}');
    }

    public final void updateAddUserCapabilities(Context context) {
        UserManager userManager = (UserManager) context.getSystemService("user");
        this.mEnforcedAdmin =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        context, UserHandle.myUserId(), "no_add_user");
        boolean hasBaseUserRestriction =
                RestrictedLockUtilsInternal.hasBaseUserRestriction(
                        context, UserHandle.myUserId(), "no_add_user");
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin = this.mEnforcedAdmin;
        boolean z = false;
        this.mDisallowAddUserSetByAdmin =
                (enforcedAdmin == null || hasBaseUserRestriction) ? false : true;
        this.mDisallowAddUser = enforcedAdmin != null || hasBaseUserRestriction;
        this.mUserSwitcherEnabled = userManager.isUserSwitcherEnabled();
        this.mCanAddUser = true;
        if (!this.mIsAdmin
                || UserManager.getMaxSupportedUsers() < 2
                || !UserManager.supportsMultipleUsers()
                || this.mDisallowAddUser
                || (!userManager.isUserTypeEnabled("android.os.usertype.full.SECONDARY")
                        && !this.mCanAddRestrictedProfile)) {
            this.mCanAddUser = false;
        }
        boolean z2 =
                this.mIsAdmin
                        || Settings.Global.getInt(
                                        context.getContentResolver(), "add_users_when_locked", 0)
                                == 1;
        if (!this.mIsGuest
                && !this.mDisallowAddUser
                && z2
                && userManager.isUserTypeEnabled("android.os.usertype.full.GUEST")) {
            z = true;
        }
        this.mCanAddGuest = z;
        this.mDisallowSwitchUser = userManager.hasUserRestriction("no_user_switch");
    }
}
