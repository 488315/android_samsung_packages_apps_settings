package com.samsung.android.settings.display.controller;

import android.content.Context;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.display.SubBrightnessSeekBarPreference;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecSubBrightnessPreferenceController extends SecDisplayPreferenceBaseController
        implements PreferenceControllerMixin {
    public Context mContext;
    public SubBrightnessSeekBarPreference mPreference;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SubBrightnessSeekBarPreference)
                        preferenceScreen.findPreference("secfrontbrightness");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "secfrontbrightness";
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final ArrayList getUriListRequiringObservation() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return false;
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final void onPause() {
        super.onPause();
        SubBrightnessSeekBarPreference subBrightnessSeekBarPreference = this.mPreference;
        subBrightnessSeekBarPreference.mContentResolver.unregisterContentObserver(
                subBrightnessSeekBarPreference.mSubBrightnessObserver);
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final void onResume() {
        super.onResume();
        if (RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        this.mContext, UserHandle.myUserId(), "no_config_brightness")
                != null) {
            SubBrightnessSeekBarPreference subBrightnessSeekBarPreference = this.mPreference;
            subBrightnessSeekBarPreference.mIsRestricted = true;
            subBrightnessSeekBarPreference.notifyChanged();
        }
        SubBrightnessSeekBarPreference subBrightnessSeekBarPreference2 = this.mPreference;
        subBrightnessSeekBarPreference2.mContentResolver.registerContentObserver(
                Settings.System.getUriFor("sub_screen_brightness"),
                true,
                subBrightnessSeekBarPreference2.mSubBrightnessObserver);
        this.mPreference.onSubBrightnessChanged();
    }

    @Override // com.samsung.android.settings.display.controller.SecDisplayPreferenceBaseController
    public final void updatePreference$11() {
        if (this.mPreference == null) {
            return;
        }
        this.mPreference.setEnabled(
                !(Rune.isSamsungDexMode(this.mContext) && Utils.isDesktopDualMode(this.mContext)));
    }
}
