package com.android.settings.notification.modes;

import android.service.notification.Condition;
import android.view.View;
import android.widget.Button;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeButtonPreferenceController extends AbstractZenModePreferenceController {
    public Button mZenButton;

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final boolean isAvailable(ZenMode zenMode) {
        return zenMode.mRule.isManualInvocationAllowed() && zenMode.mRule.isEnabled();
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, final ZenMode zenMode) {
        if (this.mZenButton == null) {
            this.mZenButton =
                    (Button)
                            ((LayoutPreference) preference)
                                    .mRootView.findViewById(R.id.activate_mode);
        }
        this.mZenButton.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.modes.ZenModeButtonPreferenceController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ZenModeButtonPreferenceController zenModeButtonPreferenceController =
                                ZenModeButtonPreferenceController.this;
                        ZenMode zenMode2 = zenMode;
                        zenModeButtonPreferenceController.getClass();
                        boolean z = zenMode2.mIsActive;
                        ZenModesBackend zenModesBackend =
                                zenModeButtonPreferenceController.mBackend;
                        String str = zenMode2.mId;
                        boolean z2 = zenMode2.mIsManualDnd;
                        if (z) {
                            zenModesBackend.getClass();
                            if (z2) {
                                zenModesBackend.mNotificationManager.setZenMode(
                                        0, null, "ZenModeBackend", true);
                                return;
                            } else {
                                zenModesBackend.mNotificationManager.setAutomaticZenRuleState(
                                        str,
                                        new Condition(
                                                zenMode2.mRule.getConditionId(),
                                                ApnSettings.MVNO_NONE,
                                                0,
                                                1));
                                return;
                            }
                        }
                        zenModesBackend.getClass();
                        if (z2) {
                            zenModesBackend.mNotificationManager.setZenMode(
                                    1, null, "ZenModeBackend", true);
                        } else {
                            zenModesBackend.mNotificationManager.setAutomaticZenRuleState(
                                    str,
                                    new Condition(
                                            zenMode2.mRule.getConditionId(),
                                            ApnSettings.MVNO_NONE,
                                            1,
                                            1));
                        }
                    }
                });
        if (zenMode.mIsActive) {
            this.mZenButton.setText(R.string.zen_mode_button_turn_off);
        } else {
            this.mZenButton.setText(R.string.zen_mode_button_turn_on);
        }
    }
}
