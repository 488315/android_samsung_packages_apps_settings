package com.android.settings.enterprise;

import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.net.VpnManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;

import com.android.settingslib.utils.WorkPolicyUtils;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnterprisePrivacyFeatureProviderImpl
        implements EnterprisePrivacyFeatureProvider {
    public static final int MY_USER_ID = UserHandle.myUserId();
    public final Context mContext;
    public final DevicePolicyManager mDpm;
    public final PackageManager mPm;
    public final Resources mResources;
    public final UserManager mUm;
    public final VpnManager mVm;
    public final WorkPolicyUtils mWorkPolicyUtils;

    public EnterprisePrivacyFeatureProviderImpl(
            Context context,
            DevicePolicyManager devicePolicyManager,
            PackageManager packageManager,
            UserManager userManager,
            VpnManager vpnManager,
            Resources resources) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mDpm = devicePolicyManager;
        this.mPm = packageManager;
        this.mUm = userManager;
        this.mVm = vpnManager;
        this.mResources = resources;
        this.mWorkPolicyUtils = new WorkPolicyUtils(applicationContext);
    }

    public final String getImeLabelIfOwnerSet() {
        if (!this.mDpm.isCurrentInputMethodSetByOwner()) {
            return null;
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i = MY_USER_ID;
        String stringForUser =
                Settings.Secure.getStringForUser(contentResolver, "default_input_method", i);
        if (stringForUser == null) {
            return null;
        }
        try {
            return this.mPm
                    .getApplicationInfoAsUser(stringForUser, 0, i)
                    .loadLabel(this.mPm)
                    .toString();
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    public final int getManagedProfileUserId() {
        UserInfo userInfo;
        Iterator it = this.mUm.getProfiles(MY_USER_ID).iterator();
        while (true) {
            if (!it.hasNext()) {
                userInfo = null;
                break;
            }
            userInfo = (UserInfo) it.next();
            if (userInfo.isManagedProfile()) {
                break;
            }
        }
        if (userInfo != null) {
            return userInfo.id;
        }
        return -10000;
    }

    public final boolean hasDeviceOwner() {
        return (!this.mPm.hasSystemFeature("android.software.device_admin")
                        ? null
                        : this.mDpm.getDeviceOwnerComponentOnAnyUser())
                != null;
    }

    public final boolean isInCompMode() {
        return hasDeviceOwner() && getManagedProfileUserId() != -10000;
    }
}
