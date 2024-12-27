package com.samsung.android.settings.lockscreen.controller;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.MediaRouter;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.fuelgauge.BatterySaverReceiver;
import com.android.settings.network.apn.ApnPreference$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.lockscreen.LockScreenSettings;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreferenceScreen;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AODPreferenceController extends TogglePreferenceController
        implements BatterySaverReceiver.BatterySaverListener {
    private static final String ACTION_AOD_SETTINGS_MAIN =
            "com.samsung.android.app.aodservice.settings.AODSettingsMain";
    private static final String KEY_ALWAYS_ON_SCREEN = "always_on_screen";
    private static final String TAG =
            "com.samsung.android.settings.lockscreen.controller.AODPreferenceController";
    private static final String VALUE_DISABLED_IN_PSM = "aod_err_disabled_in_psm";
    private static final String VALUE_SMART_VIEW_CONNECTED = "aod_err_smart_view_connected";
    private SecRestrictedSwitchPreferenceScreen mAlwaysOnScreen;
    private final ContentObserver mAlwaysOnScreenObserver;
    private final BatterySaverReceiver mBatteryStateChangeReceiver;
    private LockScreenSettings mHost;
    private PowerManager mPowerManager;
    private UserManager mUserManager;

    public AODPreferenceController(Context context) {
        this(context, null);
    }

    private void updatePreference() {
        if (this.mAlwaysOnScreen != null) {
            if (getAvailabilityStatus() == 5) {
                this.mAlwaysOnScreen.setEnabled(false);
            } else {
                this.mAlwaysOnScreen.setEnabled(true);
            }
        }
        updateSummary();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSummary() {
        SecRestrictedSwitchPreferenceScreen secRestrictedSwitchPreferenceScreen =
                this.mAlwaysOnScreen;
        if (secRestrictedSwitchPreferenceScreen != null) {
            boolean threadEnabled = getThreadEnabled();
            secRestrictedSwitchPreferenceScreen.getClass();
            SecPreferenceUtils.applySummaryColor(
                    secRestrictedSwitchPreferenceScreen, threadEnabled);
            refreshSummary(this.mAlwaysOnScreen);
        }
    }

    private String useCalendar(int i, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(DateFormat.is24HourFormat(this.mContext) ? 11 : 10, i);
        calendar.set(12, i2);
        return DateFormat.getTimeFormat(this.mContext).format(new Date(calendar.getTimeInMillis()));
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mAlwaysOnScreen =
                (SecRestrictedSwitchPreferenceScreen)
                        preferenceScreen.findPreference(getPreferenceKey());
        updateSummary();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!LockUtils.isSupportAodService()) {
            return 4;
        }
        boolean isPowerSaveMode = this.mPowerManager.isPowerSaveMode();
        boolean z = Rune.isSamsungDexMode(this.mContext) && !Utils.isDesktopDualMode(this.mContext);
        boolean isAODBlockonSmartView = LockUtils.isAODBlockonSmartView(this.mContext);
        if (isPowerSaveMode && LockUtils.isAODDisabledInPSM(this.mContext)) {
            setStatusCode(VALUE_DISABLED_IN_PSM);
        } else if (isAODBlockonSmartView) {
            setStatusCode(VALUE_SMART_VIEW_CONNECTED);
        }
        if ((isPowerSaveMode && LockUtils.isAODDisabledInPSM(this.mContext))
                || isAODBlockonSmartView) {
            Utils$$ExternalSyntheticOutline0.m653m(
                    "Disable : PowerSaving = ",
                    isPowerSaveMode,
                    ", isDisableinSmartViewMode = ",
                    isAODBlockonSmartView,
                    TAG);
            return 5;
        }
        if (z) {
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m("isDisableinDexMode = ", TAG, z);
            return 5;
        }
        if (!this.mUserManager.hasUserRestriction("no_ambient_display")) {
            return 0;
        }
        Log.d(TAG, "isDiabledByKnox");
        return 3;
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
    public Intent getLaunchIntent() {
        Intent action = new Intent().setAction(ACTION_AOD_SETTINGS_MAIN);
        action.putExtra(
                "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_HIGHLIGHT_MENU_KEY",
                this.mContext.getString(R.string.menu_key_lockscreen));
        action.addFlags(268468224);
        return action;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_ALWAYS_ON_SCREEN;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getSliceType() {
        return super.getSliceType();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        if (this.mPowerManager.isPowerSaveMode() && LockUtils.isAODDisabledInPSM(this.mContext)) {
            return this.mContext.getString(R.string.aod_disable_by_powersaving_mode);
        }
        if (LockUtils.isAODBlockonSmartView(this.mContext)) {
            return this.mContext.getString(R.string.aod_disable_by_smart_view);
        }
        MediaRouter.RouteInfo selectedRoute =
                ((MediaRouter) this.mContext.getSystemService("media_router")).getSelectedRoute(4);
        return (selectedRoute == null
                        || selectedRoute.getPlaybackType() != 1
                        || selectedRoute.getPresentationDisplay() == null)
                ? ApnSettings.MVNO_NONE
                : this.mContext.getString(R.string.aod_disable_by_screen_mirroring);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        try {
            Intent action = new Intent().setAction(ACTION_AOD_SETTINGS_MAIN);
            action.putExtra("from_settings", true);
            this.mContext.startActivity(action);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "ActivityNotFoundException in AlwaysOnDisplay");
            e.printStackTrace();
        }
        LoggingHelper.insertEventLogging(4400, 2081);
        return true;
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
        return Settings.System.getIntForUser(
                        this.mContext.getContentResolver(), "aod_mode", 1, UserHandle.myUserId())
                != 0;
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

    @Override // com.android.settings.fuelgauge.BatterySaverReceiver.BatterySaverListener
    public void onPowerSaveModeChanged() {
        updateState(this.mAlwaysOnScreen);
    }

    public void registerContentObserver() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("aod_mode"), true, this.mAlwaysOnScreenObserver);
        this.mBatteryStateChangeReceiver.setListening(true);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (!KEY_ALWAYS_ON_SCREEN.equals(getPreferenceKey())) {
            return false;
        }
        Settings.System.putIntForUser(
                this.mContext.getContentResolver(), "aod_mode", z ? 1 : 0, UserHandle.myUserId());
        if (z) {
            Context context = this.mContext;
            String str = LockUtils.CONFIG_LOCK_SCREEN_CLOCK_DISPLAY_POLICY;
            int i =
                    Settings.System.getInt(
                            context.getContentResolver(),
                            "aod_mode_start_time",
                            VolteConstants.ErrorCode.BAD_EXTENSION);
            int i2 = Settings.System.getInt(context.getContentResolver(), "aod_mode_end_time", 0);
            if (i != i2) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int i3 = (calendar.get(11) * 60) + calendar.get(12);
                if (i >= i2 ? !(i3 >= i || i3 <= i2) : !(i3 >= i && i3 <= i2)) {
                    Context context2 = this.mContext;
                    ApnPreference$$ExternalSyntheticOutline0.m(
                            context2, R.string.aod_not_support_time_toast, context2, 0);
                }
            }
        }
        updateSummary();
        LoggingHelper.insertEventLogging(4400, 2082);
        return true;
    }

    public void unregisterContentObserver() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mAlwaysOnScreenObserver);
        this.mBatteryStateChangeReceiver.setListening(false);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        this.mAlwaysOnScreen.setVisible(isAvailable());
        updatePreference();
        super.updateState(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public AODPreferenceController(Context context, LockScreenSettings lockScreenSettings) {
        super(context, KEY_ALWAYS_ON_SCREEN);
        this.mAlwaysOnScreenObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.lockscreen.controller.AODPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        if (AODPreferenceController.this.mHost != null) {
                            LockScreenSettings lockScreenSettings2 =
                                    AODPreferenceController.this.mHost;
                            if (!lockScreenSettings2.mLockPatternUtils.isLockScreenDisabled(
                                            lockScreenSettings2.mUserId)
                                    || LockUtils.isAlwaysOnDisplayEnabled(
                                            lockScreenSettings2.mContext)) {
                                lockScreenSettings2.mEditorCategory.addPreference(
                                        lockScreenSettings2.mEditorHijriCalendar);
                                lockScreenSettings2.mEditorCategory.addPreference(
                                        lockScreenSettings2.mEditorLunarCalendar);
                                lockScreenSettings2.mEditorCategory.mOrderingAsAdded = true;
                            } else {
                                lockScreenSettings2.mEditorCategory.removePreference(
                                        lockScreenSettings2.mEditorHijriCalendar);
                                lockScreenSettings2.mEditorCategory.removePreference(
                                        lockScreenSettings2.mEditorLunarCalendar);
                            }
                        }
                        if (AODPreferenceController.this.mAlwaysOnScreen != null) {
                            AODPreferenceController.this.mAlwaysOnScreen.setChecked(
                                    AODPreferenceController.this.getThreadEnabled());
                            AODPreferenceController.this.updateSummary();
                        }
                    }
                };
        this.mHost = lockScreenSettings;
        this.mPowerManager = (PowerManager) this.mContext.getSystemService("power");
        BatterySaverReceiver batterySaverReceiver = new BatterySaverReceiver(context);
        this.mBatteryStateChangeReceiver = batterySaverReceiver;
        batterySaverReceiver.mBatterySaverListener = this;
        this.mUserManager = (UserManager) this.mContext.getSystemService("user");
    }

    @Override // com.android.settings.fuelgauge.BatterySaverReceiver.BatterySaverListener
    public void onBatteryChanged(boolean z) {}
}
