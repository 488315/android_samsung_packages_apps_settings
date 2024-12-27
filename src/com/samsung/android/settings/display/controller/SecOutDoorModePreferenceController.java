package com.samsung.android.settings.display.controller;

import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecOutDoorModePreferenceController extends SecDisplayPreferenceBaseController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public SecRestrictedSwitchPreference mPreference;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference =
                (SecRestrictedSwitchPreference) preferenceScreen.findPreference("outdoor_mode");
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "outdoor_mode";
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final ArrayList getUriListRequiringObservation() {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(Settings.System.getUriFor("display_outdoor_mode"));
        return arrayList;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return !Rune.supportAutoBrightness(this.mContext);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Settings.System.putInt(
                this.mContentResolver,
                "display_outdoor_mode",
                ((Boolean) obj).booleanValue() ? 1 : 0);
        return true;
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final void updatePreference$11() {
        if (this.mPreference == null) {
            return;
        }
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        this.mContext, UserHandle.myUserId(), "no_config_brightness");
        if (checkIfRestrictionEnforced != null) {
            this.mPreference.setDisabledByAdmin(checkIfRestrictionEnforced);
        } else {
            this.mPreference.setChecked(
                    Settings.System.getInt(this.mContentResolver, "display_outdoor_mode", 0) != 0);
        }
    }
}
