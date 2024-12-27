package com.android.settings.notification.modes;

import android.app.Flags;
import android.content.Context;
import android.service.notification.ZenModeConfig;
import android.service.notification.ZenPolicy;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settingslib.core.AbstractPreferenceController;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractZenModePreferenceController extends AbstractPreferenceController {
    public final ZenModesBackend mBackend;
    public final String mKey;
    public ZenMode mZenMode;

    public AbstractZenModePreferenceController(
            Context context, String str, ZenModesBackend zenModesBackend) {
        super(context);
        this.mBackend = zenModesBackend;
        this.mKey = str;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return this.mKey;
    }

    public CharSequence getSummary(ZenMode zenMode) {
        return null;
    }

    public boolean isAvailable(ZenMode zenMode) {
        return true;
    }

    public final boolean saveMode(Function function) {
        ZenModesBackend zenModesBackend = this.mBackend;
        if (zenModesBackend == null) {
            throw new IllegalStateException();
        }
        ZenMode zenMode = this.mZenMode;
        if (zenMode == null) {
            Log.wtf(
                    "AbstractZenModePreferenceController",
                    "Cannot save mode, it hasn't been loaded (" + getClass() + ")");
            return false;
        }
        ZenMode zenMode2 = (ZenMode) function.apply(zenMode);
        zenModesBackend.getClass();
        if (zenMode2.mIsManualDnd) {
            try {
                zenModesBackend.mNotificationManager.setNotificationPolicy(
                        new ZenModeConfig().toNotificationPolicy(zenMode2.getPolicy()), true);
                zenModesBackend.mNotificationManager.setManualZenRuleDeviceEffects(
                        zenMode2.mRule.getDeviceEffects());
            } catch (Exception e) {
                Log.w("ZenModeBackend", "Error updating manual mode", e);
            }
        } else {
            zenModesBackend.mNotificationManager.updateAutomaticZenRule(
                    zenMode2.mId, zenMode2.mRule, true);
        }
        return true;
    }

    public final boolean savePolicy(final Function function) {
        return saveMode(
                new Function() { // from class:
                                 // com.android.settings.notification.modes.AbstractZenModePreferenceController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        ZenMode zenMode = (ZenMode) obj;
                        ZenPolicy build =
                                ((ZenPolicy.Builder)
                                                function.apply(
                                                        new ZenPolicy.Builder(zenMode.getPolicy())))
                                        .build();
                        ZenPolicy policy = zenMode.getPolicy();
                        if (!policy.equals(build)) {
                            if (build.getAllowedChannels() != -1
                                    || policy.getAllowedChannels() == -1) {
                                if (build.getAllowedChannels() == -1
                                        || policy.getAllowedChannels() != -1) {
                                    if (zenMode.mRule.getInterruptionFilter() != 2) {
                                        zenMode.mRule.setInterruptionFilter(2);
                                    }
                                    zenMode.mRule.setZenPolicy(build);
                                } else {
                                    zenMode.mRule.setInterruptionFilter(2);
                                    if (zenMode.mRule.getZenPolicy() == null) {
                                        zenMode.mRule.setZenPolicy(build);
                                    }
                                }
                            } else {
                                if (zenMode.mIsManualDnd) {
                                    throw new IllegalArgumentException(
                                            "Manual DND cannot have CHANNEL_POLICY_ALL");
                                }
                                zenMode.mRule.setInterruptionFilter(1);
                                if (zenMode.mRule.getZenPolicy() == null) {
                                    zenMode.mRule.setZenPolicy(policy);
                                }
                            }
                        }
                        return zenMode;
                    }
                });
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        ZenMode zenMode = this.mZenMode;
        if (zenMode != null) {
            updateState(preference, zenMode);
        }
    }

    public abstract void updateState(Preference preference, ZenMode zenMode);

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final CharSequence getSummary() {
        ZenMode zenMode = this.mZenMode;
        if (zenMode != null) {
            return getSummary(zenMode);
        }
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean isAvailable() {
        return this.mZenMode != null
                ? Flags.modesUi() && isAvailable(this.mZenMode)
                : Flags.modesUi();
    }
}
