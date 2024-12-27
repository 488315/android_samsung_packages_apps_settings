package com.android.settingslib;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.os.UserManager;

import com.samsung.android.knox.EnterpriseDeviceManager;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RestrictedLockUtils {
    public static EnforcedAdmin getProfileOrDeviceOwner(
            Context context, String str, UserHandle userHandle) {
        DevicePolicyManager devicePolicyManager;
        ComponentName deviceOwnerComponentOnAnyUser;
        if (userHandle == null
                || (devicePolicyManager =
                                (DevicePolicyManager) context.getSystemService("device_policy"))
                        == null) {
            return null;
        }
        try {
            ComponentName profileOwner =
                    ((DevicePolicyManager)
                                    context.createPackageContextAsUser(
                                                    context.getPackageName(), 0, userHandle)
                                            .getSystemService(DevicePolicyManager.class))
                            .getProfileOwner();
            if (profileOwner != null) {
                return new EnforcedAdmin(profileOwner, str, userHandle);
            }
            if (!Objects.equals(devicePolicyManager.getDeviceOwnerUser(), userHandle)
                    || (deviceOwnerComponentOnAnyUser =
                                    devicePolicyManager.getDeviceOwnerComponentOnAnyUser())
                            == null) {
                return null;
            }
            return new EnforcedAdmin(deviceOwnerComponentOnAnyUser, str, userHandle);
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Intent getShowAdminSupportDetailsIntent(EnforcedAdmin enforcedAdmin) {
        Intent intent = new Intent("android.settings.SHOW_ADMIN_SUPPORT_DETAILS");
        if (enforcedAdmin != null) {
            ComponentName componentName = enforcedAdmin.component;
            if (componentName != null) {
                intent.putExtra(EnterpriseDeviceManager.EXTRA_DEVICE_ADMIN, componentName);
            }
            intent.putExtra("android.intent.extra.USER", enforcedAdmin.user);
        }
        return intent;
    }

    public static boolean isCurrentUserOrProfile(Context context, int i) {
        return ((UserManager) context.getSystemService(UserManager.class))
                .getUserProfiles()
                .contains(UserHandle.of(i));
    }

    public static void sendShowAdminSupportDetailsIntent(
            Context context, EnforcedAdmin enforcedAdmin) {
        Intent showAdminSupportDetailsIntent = getShowAdminSupportDetailsIntent(enforcedAdmin);
        int myUserId = UserHandle.myUserId();
        if (enforcedAdmin != null) {
            UserHandle userHandle = enforcedAdmin.user;
            if (userHandle != null && isCurrentUserOrProfile(context, userHandle.getIdentifier())) {
                myUserId = enforcedAdmin.user.getIdentifier();
            }
            showAdminSupportDetailsIntent.putExtra(
                    "android.app.extra.RESTRICTION", enforcedAdmin.enforcedRestriction);
        }
        context.startActivityAsUser(showAdminSupportDetailsIntent, UserHandle.of(myUserId));
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EnforcedAdmin {
        public static final EnforcedAdmin MULTIPLE_ENFORCED_ADMIN = new EnforcedAdmin();
        public ComponentName component;
        public String enforcedRestriction;
        public UserHandle user;

        public EnforcedAdmin(ComponentName componentName, UserHandle userHandle) {
            this.enforcedRestriction = null;
            this.component = componentName;
            this.user = userHandle;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || EnforcedAdmin.class != obj.getClass()) {
                return false;
            }
            EnforcedAdmin enforcedAdmin = (EnforcedAdmin) obj;
            return Objects.equals(this.user, enforcedAdmin.user)
                    && Objects.equals(this.component, enforcedAdmin.component)
                    && Objects.equals(this.enforcedRestriction, enforcedAdmin.enforcedRestriction);
        }

        public final int hashCode() {
            return Objects.hash(this.component, this.enforcedRestriction, this.user);
        }

        public final String toString() {
            return "EnforcedAdmin{component="
                    + this.component
                    + ", enforcedRestriction='"
                    + this.enforcedRestriction
                    + ", user="
                    + this.user
                    + '}';
        }

        public EnforcedAdmin(ComponentName componentName, String str, UserHandle userHandle) {
            this.component = componentName;
            this.enforcedRestriction = str;
            this.user = userHandle;
        }

        public EnforcedAdmin() {
            this.component = null;
            this.enforcedRestriction = null;
            this.user = null;
        }
    }
}
