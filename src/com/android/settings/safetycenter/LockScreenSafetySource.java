package com.android.settings.safetycenter;

import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.os.UserManager;
import android.safetycenter.SafetyEvent;
import android.safetycenter.SafetySourceData;
import android.safetycenter.SafetySourceIssue;
import android.safetycenter.SafetySourceStatus;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.security.ScreenLockPreferenceDetailsUtils;
import com.android.settings.security.screenlock.ScreenLockSettings;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class LockScreenSafetySource {
    public static void setSafetySourceData(
            Context context,
            ScreenLockPreferenceDetailsUtils screenLockPreferenceDetailsUtils,
            SafetyEvent safetyEvent) {
        SafetySourceStatus.IconAction iconAction;
        SafetyCenterManagerWrapper.get().getClass();
        if (SafetyCenterManagerWrapper.isEnabled(context)) {
            UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
            if (userManager == null || !userManager.isProfile()) {
                if (!screenLockPreferenceDetailsUtils
                        .mContext
                        .getResources()
                        .getBoolean(R.bool.config_show_unlock_set_or_change)) {
                    SafetyCenterManagerWrapper.get().getClass();
                    SafetyCenterManagerWrapper.setSafetySourceData(
                            context, "AndroidLockScreen", null, safetyEvent);
                    return;
                }
                int myUserId = UserHandle.myUserId();
                RestrictedLockUtils.EnforcedAdmin checkIfPasswordQualityIsSet =
                        RestrictedLockUtilsInternal.checkIfPasswordQualityIsSet(context, myUserId);
                Intent quietModeDialogIntent =
                        screenLockPreferenceDetailsUtils.getQuietModeDialogIntent();
                if (quietModeDialogIntent == null) {
                    SubSettingLauncher subSettingLauncher =
                            new SubSettingLauncher(screenLockPreferenceDetailsUtils.mContext);
                    String name = ChooseLockGeneric.ChooseLockGenericFragment.class.getName();
                    SubSettingLauncher.LaunchRequest launchRequest =
                            subSettingLauncher.mLaunchRequest;
                    launchRequest.mDestinationName = name;
                    launchRequest.mSourceMetricsCategory = 1917;
                    launchRequest.mTransitionType = 1;
                    quietModeDialogIntent = subSettingLauncher.toIntent();
                }
                PendingIntent activity =
                        PendingIntent.getActivity(context, 1, quietModeDialogIntent, 67108864);
                LockPatternUtils lockPatternUtils =
                        screenLockPreferenceDetailsUtils.mLockPatternUtils;
                int i = screenLockPreferenceDetailsUtils.mUserId;
                if (lockPatternUtils.isSecure(i)) {
                    SubSettingLauncher subSettingLauncher2 =
                            new SubSettingLauncher(screenLockPreferenceDetailsUtils.mContext);
                    String name2 = ScreenLockSettings.class.getName();
                    SubSettingLauncher.LaunchRequest launchRequest2 =
                            subSettingLauncher2.mLaunchRequest;
                    launchRequest2.mDestinationName = name2;
                    launchRequest2.mSourceMetricsCategory = 1917;
                    iconAction =
                            new SafetySourceStatus.IconAction(
                                    100,
                                    PendingIntent.getActivity(
                                            context, 2, subSettingLauncher2.toIntent(), 67108864));
                } else {
                    iconAction = null;
                }
                boolean z =
                        !(checkIfPasswordQualityIsSet != null
                                && ((DevicePolicyManager)
                                                        screenLockPreferenceDetailsUtils.mContext
                                                                .getSystemService("device_policy"))
                                                .getPasswordQuality(
                                                        checkIfPasswordQualityIsSet.component,
                                                        myUserId)
                                        == 524288);
                boolean isSecure = screenLockPreferenceDetailsUtils.mLockPatternUtils.isSecure(i);
                SafetySourceStatus.Builder enabled =
                        new SafetySourceStatus.Builder(
                                        context.getString(
                                                R.string.sec_unlock_set_unlock_launch_picker_title),
                                        z
                                                ? screenLockPreferenceDetailsUtils.getSummary(
                                                        UserHandle.myUserId())
                                                : context.getString(
                                                        R.string.disabled_by_policy_title),
                                        z ? isSecure ? 200 : 300 : 100)
                                .setPendingIntent(z ? activity : null)
                                .setEnabled(z);
                if (!z) {
                    iconAction = null;
                }
                SafetySourceData.Builder status =
                        new SafetySourceData.Builder()
                                .setStatus(enabled.setIconAction(iconAction).build());
                if (z && !isSecure) {
                    status.addIssue(
                            new SafetySourceIssue.Builder(
                                            "NoScreenLockIssue",
                                            context.getString(R.string.no_screen_lock_issue_title),
                                            context.getString(
                                                    R.string.no_screen_lock_issue_summary),
                                            300,
                                            "NoScreenLockIssueType")
                                    .setIssueCategory(100)
                                    .addAction(
                                            new SafetySourceIssue.Action.Builder(
                                                            "SetScreenLockAction",
                                                            context.getString(
                                                                    R.string
                                                                            .no_screen_lock_issue_action_label),
                                                            activity)
                                                    .build())
                                    .setIssueActionability(0)
                                    .setCustomNotification(
                                            new SafetySourceIssue.Notification.Builder(
                                                            context.getString(
                                                                    R.string
                                                                            .no_screen_lock_issue_notification_title),
                                                            context.getString(
                                                                    R.string
                                                                            .no_screen_lock_issue_notification_text))
                                                    .build())
                                    .setNotificationBehavior(200)
                                    .build());
                }
                SafetySourceData build = status.build();
                SafetyCenterManagerWrapper.get().getClass();
                SafetyCenterManagerWrapper.setSafetySourceData(
                        context, "AndroidLockScreen", build, safetyEvent);
            }
        }
    }
}
