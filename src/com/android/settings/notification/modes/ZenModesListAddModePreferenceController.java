package com.android.settings.notification.modes;

import android.app.AutomaticZenRule;
import android.content.Context;
import android.service.notification.SystemZenRules;
import android.service.notification.ZenModeConfig;

import androidx.preference.Preference;

import com.android.settingslib.core.AbstractPreferenceController;

import java.util.Random;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModesListAddModePreferenceController extends AbstractPreferenceController {
    public final ZenModesBackend mBackend;

    public static void $r8$lambda$dHGe1XmF6BmxK2pwZZeWS1e6cvY(
            ZenModesListAddModePreferenceController zenModesListAddModePreferenceController) {
        zenModesListAddModePreferenceController.getClass();
        String str = "New mode #" + new Random().nextInt(1000);
        ZenModesBackend zenModesBackend = zenModesListAddModePreferenceController.mBackend;
        zenModesBackend.getClass();
        ZenModeConfig.ScheduleInfo scheduleInfo = new ZenModeConfig.ScheduleInfo();
        scheduleInfo.days = ZenModeConfig.ALL_DAYS;
        scheduleInfo.startHour = 22;
        scheduleInfo.endHour = 7;
        ZenMode mode =
                zenModesBackend.getMode(
                        zenModesBackend.mNotificationManager.addAutomaticZenRule(
                                new AutomaticZenRule.Builder(
                                                str,
                                                ZenModeConfig.toScheduleConditionId(scheduleInfo))
                                        .setPackage(
                                                ZenModeConfig.getScheduleConditionProvider()
                                                        .getPackageName())
                                        .setType(2)
                                        .setOwner(ZenModeConfig.getScheduleConditionProvider())
                                        .setTriggerDescription(
                                                SystemZenRules.getTriggerDescriptionForScheduleTime(
                                                        zenModesBackend.mContext, scheduleInfo))
                                        .setManualInvocationAllowed(true)
                                        .build()));
        if (mode != null) {
            ZenSubSettingLauncher.forMode(
                            zenModesListAddModePreferenceController.mContext, mode.mId)
                    .launch();
        }
    }

    public ZenModesListAddModePreferenceController(
            Context context, ZenModesBackend zenModesBackend) {
        super(context);
        this.mBackend = zenModesBackend;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "add_mode";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        preference.setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.android.settings.notification.modes.ZenModesListAddModePreferenceController$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference2) {
                        ZenModesListAddModePreferenceController
                                .$r8$lambda$dHGe1XmF6BmxK2pwZZeWS1e6cvY(
                                        ZenModesListAddModePreferenceController.this);
                        return true;
                    }
                });
    }
}
