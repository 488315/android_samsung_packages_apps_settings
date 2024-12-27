package com.samsung.android.settings.display.controller;

import android.content.Context;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.display.DisplayDisabledAppearancePreference;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class EasyModePreferenceController extends SecDisplayPreferenceBaseController
        implements PreferenceControllerMixin {
    public DisplayDisabledAppearancePreference mPreference;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        DisplayDisabledAppearancePreference displayDisabledAppearancePreference =
                (DisplayDisabledAppearancePreference) preferenceScreen.findPreference("easy_mode");
        this.mPreference = displayDisabledAppearancePreference;
        if (displayDisabledAppearancePreference != null) {
            displayDisabledAppearancePreference.getClass();
            SecPreferenceUtils.applySummaryColor(displayDisabledAppearancePreference, true);
        }
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "easy_mode";
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final ArrayList getUriListRequiringObservation() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        DisplayDisabledAppearancePreference displayDisabledAppearancePreference;
        return (!"easy_mode".equals(preference.getKey())
                        || (displayDisabledAppearancePreference = this.mPreference) == null
                        || displayDisabledAppearancePreference.mIsEnabled)
                ? false
                : true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return Rune.supportEasyMode(this.mContext);
    }

    public final void updateEasyModeState(boolean z) {
        if (z) {
            DisplayDisabledAppearancePreference displayDisabledAppearancePreference =
                    this.mPreference;
            displayDisabledAppearancePreference.mMsg =
                    this.mContext.getString(R.string.sec_easy_mode_sub_display_desc);
            displayDisabledAppearancePreference.mIsChecked = true;
            this.mPreference.setEnabledAppearance(false);
        } else if (Utils.isGameModeEnabled(this.mContext)) {
            DisplayDisabledAppearancePreference displayDisabledAppearancePreference2 =
                    this.mPreference;
            Context context = this.mContext;
            displayDisabledAppearancePreference2.mMsg =
                    context.getString(
                            R.string.screen_resolution_disabled_toast,
                            context.getString(R.string.boost_mode_game_title));
            this.mPreference.setEnabledAppearance(false);
        } else if (Utils.isDesktopModeEnabled(this.mContext)
                || Utils.isDesktopDualMode(this.mContext)) {
            DisplayDisabledAppearancePreference displayDisabledAppearancePreference3 =
                    this.mPreference;
            Context context2 = this.mContext;
            displayDisabledAppearancePreference3.mMsg =
                    context2.getString(
                            R.string.screen_resolution_disabled_toast,
                            context2.getString(R.string.hdmi_mode_dex));
            this.mPreference.setEnabledAppearance(false);
        } else {
            this.mPreference.setEnabledAppearance(true);
        }
        this.mPreference.setVisible(Rune.supportEasyMode(this.mContext));
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final void updatePreference$11() {
        if (this.mPreference == null) {
            return;
        }
        updateEasyModeState(
                this.mContext.getResources().getConfiguration().semDisplayDeviceType == 5);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        updateEasyModeState(
                this.mContext.getResources().getConfiguration().semDisplayDeviceType == 5);
    }
}
