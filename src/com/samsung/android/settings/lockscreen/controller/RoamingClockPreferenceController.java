package com.samsung.android.settings.lockscreen.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.datetime.timezone.TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreferenceScreen;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class RoamingClockPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final String DB_ROAMING_CLOCK = "dualclock_menu_settings";
    private final ContentObserver mContentObserver;
    private final LockPatternUtils mLockPatternUtils;
    private SecRestrictedSwitchPreferenceScreen mPreference;

    public RoamingClockPreferenceController(Context context, String str) {
        super(context, str);
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.lockscreen.controller.RoamingClockPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        if (RoamingClockPreferenceController.this.mPreference != null) {
                            RoamingClockPreferenceController.this.mPreference.setVisible(
                                    RoamingClockPreferenceController.this.isAvailable());
                            RoamingClockPreferenceController roamingClockPreferenceController =
                                    RoamingClockPreferenceController.this;
                            roamingClockPreferenceController.updateState(
                                    roamingClockPreferenceController.mPreference);
                        }
                    }
                };
        this.mLockPatternUtils = new LockPatternUtils(context);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecRestrictedSwitchPreferenceScreen)
                        preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        boolean z =
                Settings.System.getInt(this.mContext.getContentResolver(), "easy_mode_switch", 1)
                        == 0;
        Context context = this.mContext;
        String str = LockUtils.CONFIG_LOCK_SCREEN_CLOCK_DISPLAY_POLICY;
        boolean equals =
                "oversea"
                        .equals(
                                TelephonyManager.semGetTelephonyProperty(
                                        SubscriptionManager.getPhoneId(
                                                SubscriptionManager.getDefaultSubscriptionId()),
                                        "ril.currentplmn",
                                        ApnSettings.MVNO_NONE));
        boolean isLockScreenDisabled =
                this.mLockPatternUtils.isLockScreenDisabled(UserHandle.myUserId());
        boolean isAlwaysOnDisplayEnabled = LockUtils.isAlwaysOnDisplayEnabled(this.mContext);
        if (Utils.isWifiOnly(this.mContext)) {
            return 3;
        }
        if (z) {
            return 4;
        }
        if (!isLockScreenDisabled || isAlwaysOnDisplayEnabled) {
            return (equals || !Rune.isDomesticSKTModel()) ? 0 : 5;
        }
        return 4;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_lockscreen;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        if (getAvailabilityStatus() == 5) {
            StringBuilder sb = new StringBuilder();
            TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                    this.mContext, R.string.dualclock_lockscreen_settings_summary, sb, " ");
            return SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                    .m(this.mContext, R.string.not_in_roaming_area, sb);
        }
        if (getThreadEnabled()) {
            SecRestrictedSwitchPreferenceScreen secRestrictedSwitchPreferenceScreen =
                    this.mPreference;
            secRestrictedSwitchPreferenceScreen.getClass();
            SecPreferenceUtils.applySummaryColor(secRestrictedSwitchPreferenceScreen, true);
            return this.mContext.getString(R.string.switch_on_text);
        }
        SecRestrictedSwitchPreferenceScreen secRestrictedSwitchPreferenceScreen2 = this.mPreference;
        secRestrictedSwitchPreferenceScreen2.getClass();
        SecPreferenceUtils.applySummaryColor(secRestrictedSwitchPreferenceScreen2, false);
        return this.mContext.getString(R.string.dualclock_lockscreen_settings_summary);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            LoggingHelper.insertEventLogging(4400, "LSE4407");
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.System.getInt(this.mContext.getContentResolver(), DB_ROAMING_CLOCK, 0) != 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("aod_mode"), true, this.mContentObserver);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.System.putInt(this.mContext.getContentResolver(), DB_ROAMING_CLOCK, z ? 1 : 0);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
