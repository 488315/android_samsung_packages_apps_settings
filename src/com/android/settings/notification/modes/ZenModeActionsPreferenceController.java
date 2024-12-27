package com.android.settings.notification.modes;

import android.os.Bundle;
import android.view.View;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.widget.ActionButtonsPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeActionsPreferenceController extends AbstractZenModePreferenceController {
    public static void $r8$lambda$a4cOGRhnNia5JgFHhNIk_yrZOVQ(
            ZenModeActionsPreferenceController zenModeActionsPreferenceController,
            ZenMode zenMode) {
        zenModeActionsPreferenceController.getClass();
        Bundle bundle = new Bundle();
        bundle.putString("MODE_ID", zenMode.mId);
        SubSettingLauncher subSettingLauncher =
                new SubSettingLauncher(zenModeActionsPreferenceController.mContext);
        String name = ZenModeIconPickerFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 0;
        launchRequest.mArguments = bundle;
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, final ZenMode zenMode) {
        ActionButtonsPreference actionButtonsPreference = (ActionButtonsPreference) preference;
        actionButtonsPreference.setButton1Text(R.string.zen_mode_action_change_name);
        actionButtonsPreference.setButton1Icon(R.drawable.ic_mode_edit);
        actionButtonsPreference.setButton1Enabled(false);
        actionButtonsPreference.setButton2Text(R.string.zen_mode_action_change_icon);
        actionButtonsPreference.setButton2Icon(R.drawable.ic_zen_mode_action_change_icon);
        boolean z = !zenMode.mIsManualDnd;
        ActionButtonsPreference.ButtonInfo buttonInfo = actionButtonsPreference.mButton2Info;
        if (z != buttonInfo.mIsEnabled) {
            buttonInfo.mIsEnabled = z;
            actionButtonsPreference.notifyChanged();
        }
        actionButtonsPreference.setButton2OnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.modes.ZenModeActionsPreferenceController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ZenModeActionsPreferenceController.$r8$lambda$a4cOGRhnNia5JgFHhNIk_yrZOVQ(
                                ZenModeActionsPreferenceController.this, zenMode);
                    }
                });
    }
}
