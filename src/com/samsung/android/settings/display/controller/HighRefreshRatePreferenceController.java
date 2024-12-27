package com.samsung.android.settings.display.controller;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.Signature;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.DisplayDisabledAppearancePreference;
import com.samsung.android.settings.display.HighRefreshRateFragment;
import com.samsung.android.settings.display.SecDisplayUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class HighRefreshRatePreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final String KEY_SCREEN_REFRESH_RATE = "sec_high_refresh_rate";
    private static final String TAG = "HighRefreshRatePreferenceController";
    private ContentObserver mContentObserver;
    private boolean mIsLowPowerMotionSmoothness;
    private DisplayDisabledAppearancePreference mPreference;

    public HighRefreshRatePreferenceController(Context context, String str) {
        super(context, str);
        this.mIsLowPowerMotionSmoothness = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustRefreshRateForPowerSavingMode() {
        this.mIsLowPowerMotionSmoothness =
                Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "pms_settings_refresh_rate_enabled",
                                0)
                        != 0;
        if (Utils.isMediumPowerSavingModeEnabled(this.mContext)
                && this.mIsLowPowerMotionSmoothness) {
            int currentRefreshRateMoe = getCurrentRefreshRateMoe();
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    currentRefreshRateMoe,
                    "adjustRefreshRateMode : currentRefreshRateMode = ",
                    TAG);
            if (currentRefreshRateMoe != 0) {
                SecDisplayUtils.putIntRefreshRate(this.mContext, 0, 999);
                Log.d(TAG, "adjustRefreshRateMode : change to 0 from " + currentRefreshRateMoe);
            }
        }
    }

    private ContentResolver getContentResolver() {
        return this.mContext.getContentResolver();
    }

    private int getCurrentRefreshRateMoe() {
        Context context = this.mContext;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return SecDisplayUtils.getIntRefreshRate(context, 999);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (DisplayDisabledAppearancePreference)
                        preferenceScreen.findPreference(KEY_SCREEN_REFRESH_RATE);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return Rune.supportHighRefreshRate(this.mContext, 0) ? 0 : 3;
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
        int currentRefreshRateMoe = getCurrentRefreshRateMoe();
        return currentRefreshRateMoe != 0
                ? currentRefreshRateMoe != 1
                        ? currentRefreshRateMoe != 2
                                ? ApnSettings.MVNO_NONE
                                : this.mContext.getString(
                                        R.string.sec_high_refresh_rate_best_display_title)
                        : this.mContext.getString(R.string.sec_high_refresh_rate_dynamic_title)
                : this.mContext.getString(R.string.sec_high_refresh_rate_standard_title);
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
        getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (this.mContentObserver == null) {
            this.mContentObserver =
                    new ContentObserver(
                            new Handler(
                                    Looper
                                            .getMainLooper())) { // from class:
                                                                 // com.samsung.android.settings.display.controller.HighRefreshRatePreferenceController.1
                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z, Uri uri) {
                            onChange(z);
                            if (uri.equals(Settings.Global.getUriFor("low_power"))
                                    || uri.equals(
                                            Settings.Global.getUriFor(
                                                    "pms_settings_refresh_rate_enabled"))) {
                                HighRefreshRatePreferenceController.this
                                        .adjustRefreshRateForPowerSavingMode();
                            }
                            HighRefreshRatePreferenceController
                                    highRefreshRatePreferenceController =
                                            HighRefreshRatePreferenceController.this;
                            highRefreshRatePreferenceController.refreshSummary(
                                    highRefreshRatePreferenceController.mPreference);
                        }
                    };
        }
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("refresh_rate_mode"),
                        false,
                        this.mContentObserver);
        getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("pms_settings_refresh_rate_enabled"),
                        false,
                        this.mContentObserver);
        getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("low_power"), false, this.mContentObserver);
        adjustRefreshRateForPowerSavingMode();
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
            DisplayDisabledAppearancePreference displayDisabledAppearancePreference =
                    (DisplayDisabledAppearancePreference) preference;
            displayDisabledAppearancePreference.setEnabledAppearance(
                    SecDisplayUtils.canSetHighRefreshRate(this.mContext));
            if (SecDisplayUtils.canSetHighRefreshRate(this.mContext)) {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.setClassName(
                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                        "com.android.settings.Settings$HighRefreshRatesSettingsActivity");
                preference.setIntent(intent);
                preference.setFragment(HighRefreshRateFragment.class.getCanonicalName());
                displayDisabledAppearancePreference.setEnabledAppearance(true);
            } else {
                preference.setIntent(null);
                preference.setFragment(null);
                displayDisabledAppearancePreference.setEnabledAppearance(false);
                displayDisabledAppearancePreference.mMsg =
                        this.mContext.getString(
                                R.string.sec_high_refresh_rate_dual_display_flip_close_toast);
                displayDisabledAppearancePreference.mIsChecked = true;
            }
            refreshSummary(preference);
            displayDisabledAppearancePreference.getClass();
            SecPreferenceUtils.applySummaryColor(displayDisabledAppearancePreference, true);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
