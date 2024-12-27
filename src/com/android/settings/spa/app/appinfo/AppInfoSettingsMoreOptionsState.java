package com.android.settings.spa.app.appinfo;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppInfoSettingsMoreOptionsState {
    public final boolean shouldShowAccessRestrictedSettings;
    public final boolean shownUninstallForAllUsers;
    public final boolean shownUninstallUpdates;

    public AppInfoSettingsMoreOptionsState(boolean z, boolean z2, boolean z3) {
        this.shownUninstallUpdates = z;
        this.shownUninstallForAllUsers = z2;
        this.shouldShowAccessRestrictedSettings = z3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppInfoSettingsMoreOptionsState)) {
            return false;
        }
        AppInfoSettingsMoreOptionsState appInfoSettingsMoreOptionsState =
                (AppInfoSettingsMoreOptionsState) obj;
        return this.shownUninstallUpdates == appInfoSettingsMoreOptionsState.shownUninstallUpdates
                && this.shownUninstallForAllUsers
                        == appInfoSettingsMoreOptionsState.shownUninstallForAllUsers
                && this.shouldShowAccessRestrictedSettings
                        == appInfoSettingsMoreOptionsState.shouldShowAccessRestrictedSettings;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.shouldShowAccessRestrictedSettings)
                + TransitionData$$ExternalSyntheticOutline0.m(
                        Boolean.hashCode(this.shownUninstallUpdates) * 31,
                        31,
                        this.shownUninstallForAllUsers);
    }

    public final String toString() {
        StringBuilder sb =
                new StringBuilder("AppInfoSettingsMoreOptionsState(shownUninstallUpdates=");
        sb.append(this.shownUninstallUpdates);
        sb.append(", shownUninstallForAllUsers=");
        sb.append(this.shownUninstallForAllUsers);
        sb.append(", shouldShowAccessRestrictedSettings=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(
                sb, this.shouldShowAccessRestrictedSettings, ")");
    }
}
