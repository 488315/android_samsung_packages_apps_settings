package com.samsung.android.settings.display.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.DisplayDisabledAppearancePreference;
import com.samsung.android.settings.display.SecDarkModeSettingsFragment;
import com.samsung.android.settings.display.SecDisplayUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecDarkModeSettingsPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private Handler mHandler;
    private DisplayDisabledAppearancePreference mPreference;
    private SettingsObserver mSettingsObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri LOW_POWER_MODE_ENABLED;
        public final Uri POWER_SAVING_TURN_ON_DARK_MODE_ENABLED;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.LOW_POWER_MODE_ENABLED = Settings.Global.getUriFor("low_power");
            this.POWER_SAVING_TURN_ON_DARK_MODE_ENABLED =
                    Settings.Global.getUriFor("pms_settings_dark_mode_enabled");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (SecDarkModeSettingsPreferenceController.this.mPreference != null) {
                if (SecDarkModeSettingsPreferenceController.this
                        .isPowerSavingAndTurnOnDarkModeEnabled()) {
                    SecDarkModeSettingsPreferenceController.this.mPreference.setEnabled(false);
                } else {
                    SecDarkModeSettingsPreferenceController.this.mPreference.setEnabled(true);
                }
            }
        }

        public final void setListening(boolean z) {
            if (!z) {
                ((AbstractPreferenceController) SecDarkModeSettingsPreferenceController.this)
                        .mContext
                        .getContentResolver()
                        .unregisterContentObserver(this);
            } else {
                ((AbstractPreferenceController) SecDarkModeSettingsPreferenceController.this)
                        .mContext
                        .getContentResolver()
                        .registerContentObserver(this.LOW_POWER_MODE_ENABLED, false, this);
                ((AbstractPreferenceController) SecDarkModeSettingsPreferenceController.this)
                        .mContext
                        .getContentResolver()
                        .registerContentObserver(
                                this.POWER_SAVING_TURN_ON_DARK_MODE_ENABLED, false, this);
            }
        }
    }

    public SecDarkModeSettingsPreferenceController(Context context, String str) {
        super(context, str);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mSettingsObserver = new SettingsObserver(this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPowerSavingAndTurnOnDarkModeEnabled() {
        return Utils.isMediumPowerSavingModeEnabled(this.mContext)
                && Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "pms_settings_dark_mode_enabled",
                                -1)
                        == 1;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return Utils.isMinimalBatteryUseEnabled(this.mContext)
                ? this.mContext.getString(R.string.sec_night_theme_powersave_summary)
                : ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            settingsObserver.setListening(false);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (this.mPreference != null) {
            if (isPowerSavingAndTurnOnDarkModeEnabled()) {
                this.mPreference.setEnabled(false);
            } else {
                this.mPreference.setEnabled(true);
            }
        }
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            settingsObserver.setListening(true);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference instanceof DisplayDisabledAppearancePreference) {
            this.mPreference = (DisplayDisabledAppearancePreference) preference;
            boolean canChangeNightMode = SecDisplayUtils.canChangeNightMode(this.mContext);
            if (Utils.isMinimalBatteryUseEnabled(this.mContext)) {
                this.mPreference.setEnabled(canChangeNightMode);
            } else if (isPowerSavingAndTurnOnDarkModeEnabled()) {
                this.mPreference.setEnabled(false);
            } else {
                this.mPreference.setEnabledAppearance(canChangeNightMode);
            }
            if (canChangeNightMode) {
                preference.setFragment(SecDarkModeSettingsFragment.class.getCanonicalName());
                return;
            }
            DisplayDisabledAppearancePreference displayDisabledAppearancePreference =
                    this.mPreference;
            Context context = this.mContext;
            char c =
                    Utils.isCurrentThemeSupportNightTheme(context) ^ true
                            ? (char) 4
                            : Utils.isMinimalBatteryUseEnabled(context) ? (char) 5 : (char) 3;
            displayDisabledAppearancePreference.mMsg =
                    c != 4
                            ? c != 5
                                    ? ApnSettings.MVNO_NONE
                                    : context.getString(
                                            R.string.screen_resolution_disabled_toast,
                                            context.getString(R.string.power_saving_mode_title))
                            : context.getString(R.string.sec_night_theme_disabled_by_open_theme);
            preference.setFragment(null);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
