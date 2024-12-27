package com.android.settings.privatespace;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PrivateSpaceMaintainer {
    public static PrivateSpaceMaintainer sPrivateSpaceMaintainer;
    public final ActivityManager mActivityManager;
    public final Context mContext;
    public final KeyguardManager mKeyguardManager;
    public ProfileBroadcastReceiver mProfileBroadcastReceiver;
    public UserHandle mUserHandle;
    public final UserManager mUserManager;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ErrorDeletingPrivateSpace {
        public static final /* synthetic */ ErrorDeletingPrivateSpace[] $VALUES;
        public static final ErrorDeletingPrivateSpace DELETE_PS_ERROR_INTERNAL;
        public static final ErrorDeletingPrivateSpace DELETE_PS_ERROR_NONE;
        public static final ErrorDeletingPrivateSpace DELETE_PS_ERROR_NO_PRIVATE_SPACE;

        static {
            ErrorDeletingPrivateSpace errorDeletingPrivateSpace =
                    new ErrorDeletingPrivateSpace("DELETE_PS_ERROR_NONE", 0);
            DELETE_PS_ERROR_NONE = errorDeletingPrivateSpace;
            ErrorDeletingPrivateSpace errorDeletingPrivateSpace2 =
                    new ErrorDeletingPrivateSpace("DELETE_PS_ERROR_NO_PRIVATE_SPACE", 1);
            DELETE_PS_ERROR_NO_PRIVATE_SPACE = errorDeletingPrivateSpace2;
            ErrorDeletingPrivateSpace errorDeletingPrivateSpace3 =
                    new ErrorDeletingPrivateSpace("DELETE_PS_ERROR_INTERNAL", 2);
            DELETE_PS_ERROR_INTERNAL = errorDeletingPrivateSpace3;
            $VALUES =
                    new ErrorDeletingPrivateSpace[] {
                        errorDeletingPrivateSpace,
                        errorDeletingPrivateSpace2,
                        errorDeletingPrivateSpace3
                    };
        }

        public static ErrorDeletingPrivateSpace valueOf(String str) {
            return (ErrorDeletingPrivateSpace) Enum.valueOf(ErrorDeletingPrivateSpace.class, str);
        }

        public static ErrorDeletingPrivateSpace[] values() {
            return (ErrorDeletingPrivateSpace[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ProfileBroadcastReceiver extends BroadcastReceiver {
        public ProfileBroadcastReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            UserHandle userHandle =
                    (UserHandle)
                            intent.getParcelableExtra(
                                    "android.intent.extra.USER", UserHandle.class);
            if (!intent.getAction().equals("android.intent.action.PROFILE_REMOVED")) {
                if (userHandle.equals(PrivateSpaceMaintainer.this.getPrivateProfileHandle())) {
                    Log.i("PrivateSpaceMaintainer", "Removing all Settings tasks.");
                    PrivateSpaceMaintainer.m995$$Nest$mremoveSettingsAllTasks(
                            PrivateSpaceMaintainer.this);
                    return;
                } else {
                    Log.d(
                            "PrivateSpaceMaintainer",
                            "Ignoring intent for non-private profile with user id "
                                    + userHandle.getIdentifier());
                    return;
                }
            }
            PrivateSpaceMaintainer.m995$$Nest$mremoveSettingsAllTasks(PrivateSpaceMaintainer.this);
            PrivateSpaceMaintainer privateSpaceMaintainer = PrivateSpaceMaintainer.this;
            synchronized (privateSpaceMaintainer) {
                if (Flags.allowPrivateProfile()
                        && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
                    ProfileBroadcastReceiver profileBroadcastReceiver =
                            privateSpaceMaintainer.mProfileBroadcastReceiver;
                    if (profileBroadcastReceiver == null) {
                        Log.w(
                                "PrivateSpaceMaintainer",
                                "Requested to unregister when there is no receiver.");
                        return;
                    }
                    Log.d("PrivateSpaceMaintainer", "Unregistering the receiver");
                    PrivateSpaceMaintainer.this.mContext.unregisterReceiver(
                            profileBroadcastReceiver);
                    privateSpaceMaintainer.mProfileBroadcastReceiver = null;
                }
            }
        }
    }

    /* renamed from: -$$Nest$mremoveSettingsAllTasks, reason: not valid java name */
    public static void m995$$Nest$mremoveSettingsAllTasks(
            PrivateSpaceMaintainer privateSpaceMaintainer) {
        for (ActivityManager.AppTask appTask :
                privateSpaceMaintainer.mActivityManager.getAppTasks()) {
            if (!appTask.getTaskInfo().isVisible() && !appTask.getTaskInfo().isFocused) {
                appTask.finishAndRemoveTask();
            }
        }
    }

    public PrivateSpaceMaintainer(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mUserManager = (UserManager) applicationContext.getSystemService(UserManager.class);
        this.mKeyguardManager =
                (KeyguardManager) applicationContext.getSystemService(KeyguardManager.class);
        this.mActivityManager =
                (ActivityManager) applicationContext.getSystemService(ActivityManager.class);
    }

    public static synchronized PrivateSpaceMaintainer getInstance(Context context) {
        PrivateSpaceMaintainer privateSpaceMaintainer;
        synchronized (PrivateSpaceMaintainer.class) {
            try {
                if (sPrivateSpaceMaintainer == null) {
                    sPrivateSpaceMaintainer = new PrivateSpaceMaintainer(context);
                }
                privateSpaceMaintainer = sPrivateSpaceMaintainer;
            } catch (Throwable th) {
                throw th;
            }
        }
        return privateSpaceMaintainer;
    }

    public final synchronized boolean createPrivateSpace() {
        boolean z;
        if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            if (doesPrivateSpaceExist()) {
                return true;
            }
            if (this.mUserHandle == null) {
                try {
                    UserHandle createProfile =
                            this.mUserManager.createProfile(
                                    "Private space",
                                    "android.os.usertype.profile.PRIVATE",
                                    new ArraySet());
                    this.mUserHandle = createProfile;
                    if (createProfile == null) {
                        Log.e("PrivateSpaceMaintainer", "Failed to create private space");
                        return false;
                    }
                    registerBroadcastReceiver();
                    try {
                        z = this.mActivityManager.startProfile(this.mUserHandle);
                    } catch (IllegalArgumentException unused) {
                        Log.e(
                                "PrivateSpaceMaintainer",
                                "Unexpected that "
                                        + this.mUserHandle.getIdentifier()
                                        + " is not a profile");
                        z = false;
                    }
                    if (!z) {
                        Log.e(
                                "PrivateSpaceMaintainer",
                                "profile not started, created profile is deleted");
                        deletePrivateSpace();
                        return false;
                    }
                    Log.i(
                            "PrivateSpaceMaintainer",
                            "Private space created with id: " + this.mUserHandle.getIdentifier());
                    setHidePrivateSpaceEntryPointSetting(0);
                    setPrivateSpaceAutoLockSetting(2);
                    Settings.Secure.putIntForUser(
                            this.mContext.getContentResolver(),
                            "lock_screen_allow_private_notifications",
                            0,
                            this.mUserHandle.getIdentifier());
                    Log.d(
                            "PrivateSpaceMaintainer",
                            "setting USER_SETUP_COMPLETE = 1 for private profile");
                    Settings.Secure.putIntForUser(
                            this.mContext.getContentResolver(),
                            "user_setup_complete",
                            1,
                            this.mUserHandle.getIdentifier());
                    Log.d(
                            "PrivateSpaceMaintainer",
                            "setting SKIP_FIRST_USE_HINTS = 1 for private profile");
                    Settings.Secure.putIntForUser(
                            this.mContext.getContentResolver(),
                            "skip_first_use_hints",
                            1,
                            this.mUserHandle.getIdentifier());
                    disableComponentsToHidePrivateSpaceSettings();
                } catch (Exception e) {
                    Log.e("PrivateSpaceMaintainer", "Error creating private space", e);
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public final synchronized ErrorDeletingPrivateSpace deletePrivateSpace() {
        if (!doesPrivateSpaceExist()) {
            return ErrorDeletingPrivateSpace.DELETE_PS_ERROR_NO_PRIVATE_SPACE;
        }
        try {
            Log.i(
                    "PrivateSpaceMaintainer",
                    "Deleting Private space with id: " + this.mUserHandle.getIdentifier());
        } catch (Exception e) {
            Log.e("PrivateSpaceMaintainer", "Error deleting private space", e);
        }
        if (!this.mUserManager.removeUser(this.mUserHandle)) {
            Log.e("PrivateSpaceMaintainer", "Failed to delete private space");
            return ErrorDeletingPrivateSpace.DELETE_PS_ERROR_INTERNAL;
        }
        Log.i("PrivateSpaceMaintainer", "Private space deleted");
        this.mUserHandle = null;
        return ErrorDeletingPrivateSpace.DELETE_PS_ERROR_NONE;
    }

    public final void disableComponentsToHidePrivateSpaceSettings() {
        UserHandle userHandle = this.mUserHandle;
        if (userHandle == null) {
            Log.e("PrivateSpaceMaintainer", "User handle null while hiding settings icon");
            return;
        }
        Context createContextAsUser = this.mContext.createContextAsUser(userHandle, 0);
        PackageManager packageManager = createContextAsUser.getPackageManager();
        Log.d(
                "PrivateSpaceMaintainer",
                "Hiding settings app launcher icon for " + this.mUserHandle);
        Utils.disableComponentsToHideSettings(createContextAsUser, packageManager);
    }

    public final synchronized boolean doesPrivateSpaceExist() {
        if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            if (this.mUserHandle != null) {
                return true;
            }
            for (UserInfo userInfo : this.mUserManager.getProfiles(this.mContext.getUserId())) {
                if (userInfo.isPrivateProfile()) {
                    this.mUserHandle = userInfo.getUserHandle();
                    registerBroadcastReceiver();
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public final synchronized UserHandle getPrivateProfileHandle() {
        if (!doesPrivateSpaceExist()) {
            return null;
        }
        return this.mUserHandle;
    }

    public synchronized ProfileBroadcastReceiver getProfileBroadcastReceiver() {
        if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            if (!doesPrivateSpaceExist()) {
                Log.e(
                        "PrivateSpaceMaintainer",
                        "Cannot return a broadcast receiver when private space doesn't exist");
                return null;
            }
            if (this.mProfileBroadcastReceiver == null) {
                this.mProfileBroadcastReceiver = new ProfileBroadcastReceiver();
            }
            return this.mProfileBroadcastReceiver;
        }
        return null;
    }

    public synchronized boolean isPrivateProfileRunning() {
        UserHandle userHandle;
        if (!doesPrivateSpaceExist() || (userHandle = this.mUserHandle) == null) {
            return false;
        }
        return this.mUserManager.isUserRunning(userHandle);
    }

    public final synchronized boolean isPrivateSpaceLocked() {
        if (!doesPrivateSpaceExist()) {
            return true;
        }
        return this.mUserManager.isQuietModeEnabled(this.mUserHandle);
    }

    public final synchronized void registerBroadcastReceiver() {
        if (Flags.allowPrivateProfile() && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            ProfileBroadcastReceiver profileBroadcastReceiver = getProfileBroadcastReceiver();
            if (profileBroadcastReceiver == null) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PROFILE_UNAVAILABLE");
            intentFilter.addAction("android.intent.action.PROFILE_REMOVED");
            PrivateSpaceMaintainer.this.mContext.registerReceiver(
                    profileBroadcastReceiver, intentFilter, 4);
        }
    }

    public synchronized void resetBroadcastReceiver() {
        this.mProfileBroadcastReceiver = null;
    }

    public final void setHidePrivateSpaceEntryPointSetting(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "Setting HIDE_PRIVATE_SPACE_ENTRY_POINT = ", "PrivateSpaceMaintainer");
        Settings.Secure.putInt(
                this.mContext.getContentResolver(), "hide_privatespace_entry_point", i);
    }

    public final void setPrivateSpaceAutoLockSetting(int i) {
        if (Flags.allowPrivateProfile()
                && android.multiuser.Flags.supportAutolockForPrivateSpace()
                && android.multiuser.Flags.enablePrivateSpaceFeatures()) {
            Settings.Secure.putInt(
                    this.mContext.getContentResolver(), "private_space_auto_lock", i);
        }
    }
}
