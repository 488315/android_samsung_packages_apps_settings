package com.android.settings.safetycenter;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.biometrics.fingerprint.FingerprintProfileStatusPreferenceController;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.notification.LockScreenNotificationPreferenceController;
import com.android.settings.security.InstallCertificatePreferenceController;
import com.android.settings.security.ResetCredentialsPreferenceController;
import com.android.settings.security.UserCredentialsPreferenceController;
import com.android.settings.security.trustagent.TrustAgentListPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.encryption.SDCardEncryptStatusPreferenceController;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SafetyCenterUtils {
    public static List getControllersForAdvancedPrivacy(Context context, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        LockScreenNotificationPreferenceController lockScreenNotificationPreferenceController =
                new LockScreenNotificationPreferenceController(
                        context,
                        "privacy_lock_screen_notifications",
                        "privacy_work_profile_notifications_category",
                        "privacy_lock_screen_work_profile_notifications");
        if (lifecycle != null) {
            lifecycle.addObserver(lockScreenNotificationPreferenceController);
        }
        arrayList.add(lockScreenNotificationPreferenceController);
        return arrayList;
    }

    public static List getControllersForAdvancedSecurity(
            Context context, Lifecycle lifecycle, DashboardFragment dashboardFragment) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new TrustAgentListPreferenceController(context, dashboardFragment, lifecycle));
        arrayList.add(new UserCredentialsPreferenceController(context));
        arrayList.add(new ResetCredentialsPreferenceController(context, lifecycle));
        arrayList.add(new InstallCertificatePreferenceController(context));
        arrayList.add(
                new SDCardEncryptStatusPreferenceController(
                        context,
                        SDCardEncryptStatusPreferenceController
                                .PREF_KEY_SDCARD_ENCRYPT_DETAIL_PAGE));
        return arrayList;
    }

    public static void replaceEnterpriseStringsForPrivacyEntries(
            DashboardFragment dashboardFragment) {
        dashboardFragment.replaceEnterpriseStringTitle(
                "privacy_lock_screen_work_profile_notifications",
                "Settings.WORK_PROFILE_LOCKED_NOTIFICATION_TITLE",
                R.string.locked_work_profile_notification_title);
        dashboardFragment.replaceEnterpriseStringTitle(
                "interact_across_profiles_privacy",
                "Settings.CONNECTED_WORK_AND_PERSONAL_APPS_TITLE",
                R.string.interact_across_profiles_title);
        dashboardFragment.replaceEnterpriseStringTitle(
                "privacy_work_profile_notifications_category",
                "Settings.WORK_PROFILE_NOTIFICATIONS_SECTION_HEADER",
                R.string.profile_section_header);
        dashboardFragment.replaceEnterpriseStringTitle(
                "work_policy_info",
                "Settings.WORK_PROFILE_PRIVACY_POLICY_INFO",
                R.string.work_policy_privacy_settings);
        dashboardFragment.replaceEnterpriseStringSummary(
                "work_policy_info",
                "Settings.WORK_PROFILE_PRIVACY_POLICY_INFO_SUMMARY",
                R.string.work_policy_privacy_settings_summary);
    }

    public static void replaceEnterpriseStringsForSecurityEntries(
            DashboardFragment dashboardFragment) {
        dashboardFragment.replaceEnterpriseStringTitle(
                "unlock_set_or_change_profile",
                "Settings.WORK_PROFILE_SET_UNLOCK_LAUNCH_PICKER_TITLE",
                R.string.unlock_set_unlock_launch_picker_title_profile);
        dashboardFragment.replaceEnterpriseStringSummary(
                "unification",
                "Settings.WORK_PROFILE_UNIFY_LOCKS_SUMMARY",
                R.string.lock_settings_profile_unification_summary);
        dashboardFragment.replaceEnterpriseStringTitle(
                FingerprintProfileStatusPreferenceController.KEY_FINGERPRINT_SETTINGS,
                "Settings.FINGERPRINT_FOR_WORK",
                R.string.security_settings_work_fingerprint_preference_title);
        dashboardFragment.replaceEnterpriseStringTitle(
                "manage_device_admin",
                "Settings.MANAGE_DEVICE_ADMIN_APPS",
                R.string.manage_device_admin);
        dashboardFragment.replaceEnterpriseStringTitle(
                "security_category_profile",
                "Settings.WORK_PROFILE_SECURITY_TITLE",
                R.string.lock_settings_profile_title);
        dashboardFragment.replaceEnterpriseStringTitle(
                "enterprise_privacy",
                "Settings.MANAGED_DEVICE_INFO",
                R.string.enterprise_privacy_settings);
    }
}
