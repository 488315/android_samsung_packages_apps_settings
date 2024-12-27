package com.android.settingslib;

import android.R;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.app.ecm.EnhancedConfirmationManager;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.internal.widget.LockPatternUtils;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.restriction.RestrictionPolicy;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RestrictedLockUtilsInternal extends RestrictedLockUtils {
    public static final boolean DEBUG = Log.isLoggable("RestrictedLockUtils", 3);
    static Proxy sProxy = new Proxy();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface LockSettingCheck {
        boolean isEnforcing(
                DevicePolicyManager devicePolicyManager, ComponentName componentName, int i);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class Proxy {}

    public static RestrictedLockUtils.EnforcedAdmin checkForLockSetting(
            Context context, int i, LockSettingCheck lockSettingCheck) {
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin = null;
        if (devicePolicyManager == null) {
            return null;
        }
        LockPatternUtils lockPatternUtils = new LockPatternUtils(context);
        for (UserInfo userInfo : UserManager.get(context).getProfiles(i)) {
            List<ComponentName> activeAdminsAsUser =
                    devicePolicyManager.getActiveAdminsAsUser(userInfo.id);
            if (activeAdminsAsUser != null) {
                UserHandle userHandleOf = getUserHandleOf(userInfo.id);
                Proxy proxy = sProxy;
                int i2 = userInfo.id;
                proxy.getClass();
                boolean isSeparateProfileChallengeEnabled =
                        lockPatternUtils.isSeparateProfileChallengeEnabled(i2);
                for (ComponentName componentName : activeAdminsAsUser) {
                    RestrictedLockUtils.EnforcedAdmin enforcedAdmin2 =
                            RestrictedLockUtils.EnforcedAdmin.MULTIPLE_ENFORCED_ADMIN;
                    if (!isSeparateProfileChallengeEnabled
                            && lockSettingCheck.isEnforcing(
                                    devicePolicyManager, componentName, userInfo.id)) {
                        if (enforcedAdmin != null) {
                            return enforcedAdmin2;
                        }
                        enforcedAdmin =
                                new RestrictedLockUtils.EnforcedAdmin(componentName, userHandleOf);
                    } else if (userInfo.isManagedProfile()) {
                        sProxy.getClass();
                        if (!lockSettingCheck.isEnforcing(
                                devicePolicyManager.getParentProfileInstance(userInfo),
                                componentName,
                                userInfo.id)) {
                            continue;
                        } else {
                            if (enforcedAdmin != null) {
                                return enforcedAdmin2;
                            }
                            enforcedAdmin =
                                    new RestrictedLockUtils.EnforcedAdmin(
                                            componentName, userHandleOf);
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return enforcedAdmin;
    }

    public static RestrictedLockUtils.EnforcedAdmin checkIfAccessibilityServiceDisallowed(
            Context context, int i, String str) {
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        if (devicePolicyManager == null) {
            return null;
        }
        RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner =
                RestrictedLockUtils.getProfileOrDeviceOwner(context, null, getUserHandleOf(i));
        boolean isAccessibilityServicePermittedByAdmin =
                profileOrDeviceOwner != null
                        ? devicePolicyManager.isAccessibilityServicePermittedByAdmin(
                                profileOrDeviceOwner.component, str, i)
                        : true;
        int managedProfileId = getManagedProfileId(context, i);
        RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner2 =
                RestrictedLockUtils.getProfileOrDeviceOwner(
                        context, null, getUserHandleOf(managedProfileId));
        boolean isAccessibilityServicePermittedByAdmin2 =
                profileOrDeviceOwner2 != null
                        ? devicePolicyManager.isAccessibilityServicePermittedByAdmin(
                                profileOrDeviceOwner2.component, str, managedProfileId)
                        : true;
        if (!isAccessibilityServicePermittedByAdmin && !isAccessibilityServicePermittedByAdmin2) {
            return RestrictedLockUtils.EnforcedAdmin.MULTIPLE_ENFORCED_ADMIN;
        }
        if (!isAccessibilityServicePermittedByAdmin) {
            return profileOrDeviceOwner;
        }
        if (isAccessibilityServicePermittedByAdmin2) {
            return null;
        }
        return profileOrDeviceOwner2;
    }

    public static RestrictedLockUtils.EnforcedAdmin checkIfAccountManagementDisabled(
            Context context, int i, String str) {
        if (str == null) {
            return null;
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        if (context.getPackageManager().hasSystemFeature("android.software.device_admin")
                && devicePolicyManager != null) {
            for (String str2 : devicePolicyManager.getAccountTypesWithManagementDisabledAsUser(i)) {
                if (str.equals(str2)) {
                    return RestrictedLockUtils.getProfileOrDeviceOwner(
                            context, null, getUserHandleOf(i));
                }
            }
        }
        return null;
    }

    public static RestrictedLockUtils.EnforcedAdmin checkIfInputMethodDisallowed(
            Context context, int i, String str) {
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin;
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        if (devicePolicyManager == null) {
            return null;
        }
        RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner =
                RestrictedLockUtils.getProfileOrDeviceOwner(context, null, getUserHandleOf(i));
        boolean z = true;
        boolean isInputMethodPermittedByAdmin =
                profileOrDeviceOwner != null
                        ? devicePolicyManager.isInputMethodPermittedByAdmin(
                                profileOrDeviceOwner.component, str, i)
                        : true;
        int managedProfileId = getManagedProfileId(context, i);
        if (managedProfileId != -10000) {
            enforcedAdmin =
                    RestrictedLockUtils.getProfileOrDeviceOwner(
                            context, null, getUserHandleOf(managedProfileId));
            if (enforcedAdmin != null
                    && devicePolicyManager.isOrganizationOwnedDeviceWithManagedProfile()) {
                Proxy proxy = sProxy;
                UserInfo userInfo = UserManager.get(context).getUserInfo(managedProfileId);
                proxy.getClass();
                z =
                        devicePolicyManager
                                .getParentProfileInstance(userInfo)
                                .isInputMethodPermittedByAdmin(
                                        enforcedAdmin.component, str, managedProfileId);
            }
        } else {
            enforcedAdmin = null;
        }
        if (!isInputMethodPermittedByAdmin && !z) {
            return RestrictedLockUtils.EnforcedAdmin.MULTIPLE_ENFORCED_ADMIN;
        }
        if (!isInputMethodPermittedByAdmin) {
            return profileOrDeviceOwner;
        }
        if (z) {
            return null;
        }
        return enforcedAdmin;
    }

    public static RestrictedLockUtils.EnforcedAdmin checkIfKeyguardFeaturesDisabled(
            Context context, final int i, final int i2) {
        LockSettingCheck lockSettingCheck =
                new LockSettingCheck() { // from class:
                                         // com.android.settingslib.RestrictedLockUtilsInternal$$ExternalSyntheticLambda0
                    @Override // com.android.settingslib.RestrictedLockUtilsInternal.LockSettingCheck
                    public final boolean isEnforcing(
                            DevicePolicyManager devicePolicyManager,
                            ComponentName componentName,
                            int i3) {
                        int keyguardDisabledFeatures =
                                devicePolicyManager.getKeyguardDisabledFeatures(componentName, i3);
                        if (i3 != i2) {
                            keyguardDisabledFeatures &= 951;
                        }
                        return (i & keyguardDisabledFeatures) != 0;
                    }
                };
        if (!UserManager.get(context).getUserInfo(i2).isManagedProfile()) {
            return checkForLockSetting(context, i2, lockSettingCheck);
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        List<ComponentName> activeAdminsAsUser = devicePolicyManager.getActiveAdminsAsUser(i2);
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin = null;
        if (activeAdminsAsUser == null) {
            return null;
        }
        UserHandle userHandleOf = getUserHandleOf(i2);
        for (ComponentName componentName : activeAdminsAsUser) {
            if (lockSettingCheck.isEnforcing(devicePolicyManager, componentName, i2)) {
                if (enforcedAdmin != null) {
                    return RestrictedLockUtils.EnforcedAdmin.MULTIPLE_ENFORCED_ADMIN;
                }
                enforcedAdmin = new RestrictedLockUtils.EnforcedAdmin(componentName, userHandleOf);
            }
        }
        return enforcedAdmin;
    }

    public static RestrictedLockUtils.EnforcedAdmin checkIfMaximumTimeToLockIsSet(Context context) {
        return checkForLockSetting(
                context,
                UserHandle.myUserId(),
                new RestrictedLockUtilsInternal$$ExternalSyntheticLambda1(1));
    }

    public static RestrictedLockUtils.EnforcedAdmin checkIfMeteredDataUsageUserControlDisabled(
            Context context, int i, String str) {
        RoleManager roleManager = (RoleManager) context.getSystemService(RoleManager.class);
        UserHandle userHandleOf = getUserHandleOf(i);
        if (roleManager
                        .getRoleHoldersAsUser(
                                "android.app.role.FINANCED_DEVICE_KIOSK", userHandleOf)
                        .contains(str)
                || roleManager
                        .getRoleHoldersAsUser(
                                "android.app.role.SYSTEM_FINANCED_DEVICE_CONTROLLER", userHandleOf)
                        .contains(str)) {
            return new RestrictedLockUtils.EnforcedAdmin();
        }
        RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner =
                RestrictedLockUtils.getProfileOrDeviceOwner(context, null, userHandleOf);
        if (profileOrDeviceOwner != null
                && ((DevicePolicyManager) context.getSystemService("device_policy"))
                        .isMeteredDataDisabledPackageForUser(
                                profileOrDeviceOwner.component, str, i)) {
            return profileOrDeviceOwner;
        }
        return null;
    }

    public static RestrictedLockUtils.EnforcedAdmin checkIfMteIsDisabled(Context context) {
        if (((DevicePolicyManager) context.getSystemService(DevicePolicyManager.class))
                        .getMtePolicy()
                == 0) {
            return null;
        }
        RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner =
                RestrictedLockUtils.getProfileOrDeviceOwner(context, null, UserHandle.of(0));
        return profileOrDeviceOwner != null
                ? profileOrDeviceOwner
                : RestrictedLockUtils.getProfileOrDeviceOwner(
                        context, null, UserHandle.of(getManagedProfileId(context, 0)));
    }

    public static RestrictedLockUtils.EnforcedAdmin checkIfPasswordQualityIsSet(
            Context context, int i) {
        RestrictedLockUtilsInternal$$ExternalSyntheticLambda1
                restrictedLockUtilsInternal$$ExternalSyntheticLambda1 =
                        new RestrictedLockUtilsInternal$$ExternalSyntheticLambda1(0);
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin = null;
        if (devicePolicyManager == null) {
            return null;
        }
        LockPatternUtils lockPatternUtils = new LockPatternUtils(context);
        int aggregatedPasswordComplexityForUser =
                devicePolicyManager.getAggregatedPasswordComplexityForUser(i);
        if (aggregatedPasswordComplexityForUser > 0) {
            UserHandle deviceOwnerUser = devicePolicyManager.getDeviceOwnerUser();
            if (deviceOwnerUser != null) {
                return new RestrictedLockUtils.EnforcedAdmin(
                        devicePolicyManager.getDeviceOwnerComponentOnAnyUser(), deviceOwnerUser);
            }
            for (UserInfo userInfo : UserManager.get(context).getProfiles(i)) {
                ComponentName profileOwnerAsUser =
                        devicePolicyManager.getProfileOwnerAsUser(userInfo.id);
                if (profileOwnerAsUser != null) {
                    return new RestrictedLockUtils.EnforcedAdmin(
                            profileOwnerAsUser, getUserHandleOf(userInfo.id));
                }
            }
            throw new IllegalStateException(
                    String.format(
                            "Could not find admin enforcing complexity %d for user %d",
                            Integer.valueOf(aggregatedPasswordComplexityForUser),
                            Integer.valueOf(i)));
        }
        sProxy.getClass();
        if (!lockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
            return checkForLockSetting(
                    context, i, restrictedLockUtilsInternal$$ExternalSyntheticLambda1);
        }
        List<ComponentName> activeAdminsAsUser = devicePolicyManager.getActiveAdminsAsUser(i);
        if (activeAdminsAsUser == null) {
            return null;
        }
        UserHandle userHandleOf = getUserHandleOf(i);
        for (ComponentName componentName : activeAdminsAsUser) {
            if (devicePolicyManager.getPasswordQuality(componentName, i) > 0) {
                if (enforcedAdmin != null) {
                    return RestrictedLockUtils.EnforcedAdmin.MULTIPLE_ENFORCED_ADMIN;
                }
                enforcedAdmin = new RestrictedLockUtils.EnforcedAdmin(componentName, userHandleOf);
            }
        }
        return enforcedAdmin;
    }

    public static Intent checkIfRequiresEnhancedConfirmation(
            Context context, String str, String str2) {
        if (Flags.enhancedConfirmationModeApisEnabled()
                && android.security.Flags.extendEcmToAllSettings()) {
            EnhancedConfirmationManager enhancedConfirmationManager =
                    (EnhancedConfirmationManager)
                            context.getSystemService("ecm_enhanced_confirmation");
            try {
                if (enhancedConfirmationManager.isRestricted(str2, str)) {
                    return enhancedConfirmationManager.createRestrictedSettingDialogIntent(
                            str2, str);
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("RestrictedLockUtils", "package not found: " + str2, e);
            }
        }
        return null;
    }

    public static RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced(
            Context context, int i, String str) {
        RestrictionPolicy restrictionPolicy;
        if (((DevicePolicyManager) context.getSystemService("device_policy")) == null) {
            return null;
        }
        UserManager userManager = UserManager.get(context);
        UserHandle of = UserHandle.of(i);
        List userRestrictionSources = userManager.getUserRestrictionSources(str, of);
        if (userRestrictionSources.isEmpty()) {
            return null;
        }
        int size = userRestrictionSources.size();
        if (size <= 1) {
            UserManager.EnforcingUser enforcingUser =
                    (UserManager.EnforcingUser) userRestrictionSources.get(0);
            if (enforcingUser.getUserRestrictionSource() == 1) {
                EnterpriseDeviceManager enterpriseDeviceManager =
                        EnterpriseDeviceManager.getInstance(context);
                if (enterpriseDeviceManager == null
                        || (restrictionPolicy = enterpriseDeviceManager.getRestrictionPolicy())
                                == null
                        || !restrictionPolicy.checkIfRestrictionWasSetByKC(str)) {
                    return null;
                }
                return new RestrictedLockUtils.EnforcedAdmin(
                        RestrictionPolicy.KC_COMPONENT_NAME, str, new UserHandle(0));
            }
            RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner =
                    RestrictedLockUtils.getProfileOrDeviceOwner(
                            context, str, enforcingUser.getUserHandle());
            if (profileOrDeviceOwner != null) {
                return profileOrDeviceOwner;
            }
            RestrictedLockUtils.EnforcedAdmin enforcedAdmin =
                    new RestrictedLockUtils.EnforcedAdmin();
            enforcedAdmin.enforcedRestriction = str;
            return enforcedAdmin;
        }
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin2 = new RestrictedLockUtils.EnforcedAdmin();
        enforcedAdmin2.enforcedRestriction = str;
        enforcedAdmin2.user = of;
        if (DEBUG) {
            Log.d(
                    "RestrictedLockUtils",
                    "Multiple ("
                            + size
                            + ") enforcing users for restriction '"
                            + str
                            + "' on user "
                            + of
                            + "; returning default admin ("
                            + enforcedAdmin2
                            + ")");
        }
        return enforcedAdmin2;
    }

    public static RestrictedLockUtils.EnforcedAdmin checkIfUninstallBlocked(
            Context context, int i, String str) {
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                checkIfRestrictionEnforced(context, i, "no_control_apps");
        if (checkIfRestrictionEnforced != null) {
            return checkIfRestrictionEnforced;
        }
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced2 =
                checkIfRestrictionEnforced(context, i, "no_uninstall_apps");
        if (checkIfRestrictionEnforced2 != null) {
            return checkIfRestrictionEnforced2;
        }
        try {
            if (AppGlobals.getPackageManager().getBlockUninstallForUser(str, i)) {
                return RestrictedLockUtils.getProfileOrDeviceOwner(
                        context, null, getUserHandleOf(i));
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    public static RestrictedLockUtils.EnforcedAdmin checkIfUsbDataSignalingIsDisabled(
            Context context, int i) {
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        if (devicePolicyManager == null || devicePolicyManager.isUsbDataSignalingEnabled()) {
            return null;
        }
        RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner =
                RestrictedLockUtils.getProfileOrDeviceOwner(context, null, getUserHandleOf(i));
        int managedProfileId = getManagedProfileId(context, i);
        return (profileOrDeviceOwner != null || managedProfileId == -10000)
                ? profileOrDeviceOwner
                : RestrictedLockUtils.getProfileOrDeviceOwner(
                        context, null, getUserHandleOf(managedProfileId));
    }

    public static RestrictedLockUtils.EnforcedAdmin getDeviceOwner(Context context) {
        ComponentName deviceOwnerComponentOnAnyUser;
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        if (devicePolicyManager == null
                || (deviceOwnerComponentOnAnyUser =
                                devicePolicyManager.getDeviceOwnerComponentOnAnyUser())
                        == null) {
            return null;
        }
        return new RestrictedLockUtils.EnforcedAdmin(
                deviceOwnerComponentOnAnyUser, null, devicePolicyManager.getDeviceOwnerUser());
    }

    public static int getManagedProfileId(Context context, int i) {
        for (UserInfo userInfo : ((UserManager) context.getSystemService("user")).getProfiles(i)) {
            if (userInfo.id != i && userInfo.isManagedProfile()) {
                return userInfo.id;
            }
        }
        return -10000;
    }

    public static Drawable getRestrictedPadlock(Context context) {
        Drawable drawable = context.getDrawable(R.drawable.ic_info);
        int dimensionPixelSize =
                context.getResources().getDimensionPixelSize(R.dimen.config_restrictedIconSize);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(new int[] {R.attr.colorAccent});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        drawable.setTint(color);
        drawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
        return drawable;
    }

    public static UserHandle getUserHandleOf(int i) {
        if (i == -10000) {
            return null;
        }
        return UserHandle.of(i);
    }

    public static boolean hasBaseUserRestriction(Context context, int i, String str) {
        return ((UserManager) context.getSystemService("user"))
                .hasBaseUserRestriction(str, UserHandle.of(i));
    }

    public static boolean isAdminInCurrentUserOrProfile(
            Context context, ComponentName componentName) {
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager) context.getSystemService("device_policy");
        Iterator it = UserManager.get(context).getProfiles(UserHandle.myUserId()).iterator();
        while (it.hasNext()) {
            if (devicePolicyManager.isAdminActiveAsUser(componentName, ((UserInfo) it.next()).id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEnhancedConfirmationRestricted(Context context, String str) {
        if (Flags.enhancedConfirmationModeApisEnabled()
                && android.security.Flags.extendEcmToAllSettings()) {
            try {
                return ((EnhancedConfirmationManager)
                                context.getSystemService(EnhancedConfirmationManager.class))
                        .isRestricted("android:bind_accessibility_service", str);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(
                        "RestrictedLockUtils",
                        "Exception when retrieving package:android:bind_accessibility_service",
                        e);
                return false;
            }
        }
        try {
            if (!str.equals("android:bind_accessibility_service")) {
                return false;
            }
            int noteOpNoThrow =
                    ((AppOpsManager) context.getSystemService(AppOpsManager.class))
                            .noteOpNoThrow(
                                    119,
                                    context.getPackageManager()
                                            .getPackageUid("android:bind_accessibility_service", 0),
                                    "android:bind_accessibility_service");
            return (!context.getResources()
                                    .getBoolean(R.bool.config_focusScrollContainersInTouchMode)
                            || noteOpNoThrow == 0
                            || noteOpNoThrow == 3)
                    ? false
                    : true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static void removeExistingRestrictedSpans(
            SpannableStringBuilder spannableStringBuilder) {
        int length = spannableStringBuilder.length();
        for (RestrictedLockImageSpan restrictedLockImageSpan :
                (RestrictedLockImageSpan[])
                        spannableStringBuilder.getSpans(
                                length - 1, length, RestrictedLockImageSpan.class)) {
            int spanStart = spannableStringBuilder.getSpanStart(restrictedLockImageSpan);
            int spanEnd = spannableStringBuilder.getSpanEnd(restrictedLockImageSpan);
            spannableStringBuilder.removeSpan(restrictedLockImageSpan);
            spannableStringBuilder.delete(spanStart, spanEnd);
        }
        for (ForegroundColorSpan foregroundColorSpan :
                (ForegroundColorSpan[])
                        spannableStringBuilder.getSpans(0, length, ForegroundColorSpan.class)) {
            spannableStringBuilder.removeSpan(foregroundColorSpan);
        }
    }

    public static void setMenuItemAsDisabledByAdmin(
            final Context context,
            MenuItem menuItem,
            final RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        SpannableStringBuilder spannableStringBuilder =
                new SpannableStringBuilder(menuItem.getTitle());
        removeExistingRestrictedSpans(spannableStringBuilder);
        if (enforcedAdmin != null) {
            spannableStringBuilder.setSpan(
                    new ForegroundColorSpan(
                            Utils.getColorAttrDefaultColor(context, R.attr.textColorHint)),
                    0,
                    spannableStringBuilder.length(),
                    33);
            spannableStringBuilder.append(" ", new RestrictedLockImageSpan(context), 33);
            menuItem.setOnMenuItemClickListener(
                    new MenuItem
                            .OnMenuItemClickListener() { // from class:
                                                         // com.android.settingslib.RestrictedLockUtilsInternal.1
                        @Override // android.view.MenuItem.OnMenuItemClickListener
                        public final boolean onMenuItemClick(MenuItem menuItem2) {
                            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                    context, enforcedAdmin);
                            return true;
                        }
                    });
        } else {
            menuItem.setOnMenuItemClickListener(null);
        }
        menuItem.setTitle(spannableStringBuilder);
    }

    public static void setTextViewAsDisabledByAdmin(Context context, TextView textView, boolean z) {
        SpannableStringBuilder spannableStringBuilder =
                new SpannableStringBuilder(textView.getText());
        removeExistingRestrictedSpans(spannableStringBuilder);
        if (z) {
            spannableStringBuilder.setSpan(
                    new ForegroundColorSpan(
                            context.getColor(
                                    com.android.settings.R.color.sec_body_text_disabled_color)),
                    0,
                    spannableStringBuilder.length(),
                    33);
            textView.setCompoundDrawables(null, null, getRestrictedPadlock(context), null);
            textView.setCompoundDrawablePadding(
                    context.getResources()
                            .getDimensionPixelSize(
                                    com.android.settings.R.dimen.restricted_icon_padding));
        } else {
            textView.setCompoundDrawables(null, null, null, null);
        }
        textView.setText(spannableStringBuilder);
    }
}
