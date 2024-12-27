package com.samsung.android.settings.password;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.SystemProperties;
import android.os.UserManager;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.dar.ddar.DualDarManager;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.knox.UCMUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LockTypePolicy {
    public final Context mContext;
    public final boolean mForAppLockBackupKey;
    public final boolean mForFace;
    public final boolean mForFingerprint;
    public final boolean mForPrivateModeBackupKey;
    public final boolean mFromChinaSUW;
    public final boolean mFromSetupwizard;
    public final Intent mIntent;
    public final boolean mIsDOEnabled;
    public final boolean mIsDualDarDoEnabled;
    public final boolean mIsDualDarDoUser;
    public final boolean mIsKnoxId;
    public final boolean mIsManagedProfile;
    public final boolean mIsMultifactorAuthEnforced;
    public final boolean mIsOnelockOngoing;
    public final boolean mIsOrganizationOwned;
    public final boolean mIsOrganizationOwnedDeviceWithManagedProfile;
    public final boolean mIsPasswordAdminEnabled;
    public final boolean mIsSecureFolderId;
    public final boolean mIsSupportBiometricForUCM;
    public final boolean mIsSupportChangePin;
    public final boolean mIsSystemUser;
    public final boolean mIsUCMKeyguardEnabled;
    public final boolean mSetPrevious;
    public final int mUserId;
    public final UserManager mUserManager;
    public int mUnificationProfileId = -10000;
    public final int[] prime_number = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};

    public LockTypePolicy(int i, Context context, Intent intent) {
        this.mContext = context;
        this.mUserId = i;
        this.mIntent = intent;
        UserManager userManager = (UserManager) context.getSystemService("user");
        this.mUserManager = userManager;
        boolean z = false;
        this.mSetPrevious = intent.getBooleanExtra("recover_password", false);
        this.mFromSetupwizard = intent.getBooleanExtra("fromSetupWizard", false);
        this.mFromChinaSUW = intent.getBooleanExtra("fromChinaSUW", false);
        this.mForFingerprint = intent.getBooleanExtra("for_fingerprint", false);
        this.mForFace = intent.getBooleanExtra("for_face", false);
        this.mForAppLockBackupKey = intent.getStringExtra("forAppLockBackupKey") != null;
        this.mForPrivateModeBackupKey = intent.getBooleanExtra("forPrivateBackupKey", false);
        if (intent.getBooleanExtra("from_sec_non_biometrics", false)) {
            this.mForFingerprint = false;
            this.mForFace = false;
        }
        this.mIsOnelockOngoing = intent.getBooleanExtra("isOneLockOngoing", false);
        this.mIsUCMKeyguardEnabled = UCMUtils.isUCMKeyguardEnabledAsUser(i);
        this.mIsSupportChangePin =
                UCMUtils.isEnforcedCredentialStorageExistAsUser(i)
                        && UCMUtils.isSupportChangePin(i);
        this.mIsSupportBiometricForUCM =
                UCMUtils.isEnforcedCredentialStorageExistAsUser(i)
                        && UCMUtils.isSupportBiometricForUCM(i);
        this.mIsSystemUser = i == 0;
        this.mIsKnoxId = SemPersonaManager.isKnoxId(i);
        this.mIsDOEnabled = SemPersonaManager.isDoEnabled(i);
        this.mIsSecureFolderId = SemPersonaManager.isSecureFolderId(i);
        userManager.getUserInfo(i);
        this.mIsMultifactorAuthEnforced = KnoxUtils.isMultifactorAuthEnforced(context, i);
        this.mIsOrganizationOwnedDeviceWithManagedProfile =
                ((DevicePolicyManager) context.getSystemService("device_policy"))
                        .isOrganizationOwnedDeviceWithManagedProfile();
        boolean isOnDeviceOwnerEnabled = DualDarManager.isOnDeviceOwnerEnabled();
        this.mIsDualDarDoEnabled = isOnDeviceOwnerEnabled;
        this.mIsDualDarDoUser =
                isOnDeviceOwnerEnabled && KnoxUtils.isDualDarDoInnerAuthUser(context, i);
        EnterpriseDeviceManager enterpriseDeviceManager =
                EnterpriseDeviceManager.getInstance(context);
        if (enterpriseDeviceManager != null) {
            this.mIsPasswordAdminEnabled = enterpriseDeviceManager.isMdmAdminPresent();
        }
        String str = SystemProperties.get("ro.organization_owned", (String) null);
        this.mIsOrganizationOwned = str != null && str.equals("true");
        UserInfo userInfo = userManager.getUserInfo(i);
        if (userInfo != null && userInfo.isManagedProfile()) {
            z = true;
        }
        this.mIsManagedProfile = z;
    }

    public final boolean isDevicePasswordAdminEnabled() {
        return this.mIsSystemUser && this.mIsPasswordAdminEnabled;
    }

    public final boolean isEnterpriseUser() {
        return (this.mIsKnoxId && !this.mIsSecureFolderId)
                || this.mIsDOEnabled
                || this.mIsDualDarDoUser;
    }

    public final boolean isWorkDeviceOrProfile() {
        return this.mIsKnoxId
                || this.mIsDOEnabled
                || this.mIsOrganizationOwnedDeviceWithManagedProfile
                || this.mIsDualDarDoUser;
    }
}
