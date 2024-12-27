package com.samsung.android.settings.display.controller;

import android.annotation.Nullable;
import android.app.UiModeManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.secutil.Log;
import android.widget.Toast;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SecSingleChoicePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.account.controller.SecAccountPreferenceController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.SecDarkModeSettingsFragment;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecHorizontalRadioPreference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecDarkModePreferenceController extends SecSingleChoicePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final String DARK_MODE_NO = "0";
    private static final String DARK_MODE_YES = "1";
    private static final String KEY = "dark_mode";
    private static final String KEY_UI_NIGHT_MODE_ON_SUW = "ui_night_mode_on_suw";
    private static final int NIGHT_MODE_ALREADY_LOGGING_DONE = 2;
    private static final int NIGHT_MODE_NO = 0;
    private static final int NIGHT_MODE_YES = 1;
    private static final String TAG = "DarkModePrefCtrl";
    private Handler mHandler;
    private boolean mIsPowerturnmode;
    private SecHorizontalRadioPreference mPreference;
    private SettingsObserver mSettingsObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri LOW_POWER_MODE_ENABLED;
        public final Uri NIGHT_THEME_URI;
        public final Uri POWER_SAVING_TURN_ON_DARK_MODE_ENABLED;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.NIGHT_THEME_URI = Settings.System.getUriFor("display_night_theme");
            this.LOW_POWER_MODE_ENABLED = Settings.Global.getUriFor("low_power");
            this.POWER_SAVING_TURN_ON_DARK_MODE_ENABLED =
                    Settings.Global.getUriFor("pms_settings_dark_mode_enabled");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            SecDarkModePreferenceController secDarkModePreferenceController =
                    SecDarkModePreferenceController.this;
            secDarkModePreferenceController.mIsPowerturnmode =
                    Settings.Global.getInt(
                                    ((AbstractPreferenceController) secDarkModePreferenceController)
                                            .mContext.getContentResolver(),
                                    "pms_settings_dark_mode_enabled",
                                    -1)
                            == 1;
            if (SecDarkModePreferenceController.this.mPreference != null) {
                if (Utils.isMediumPowerSavingModeEnabled(
                                ((AbstractPreferenceController)
                                                SecDarkModePreferenceController.this)
                                        .mContext)
                        && SecDarkModePreferenceController.this.mIsPowerturnmode) {
                    SecDarkModePreferenceController.this.mPreference.setEnabled(false);
                    Iterator<String> it =
                            SecDarkModePreferenceController.this.getEntryValues().iterator();
                    while (it.hasNext()) {
                        SecDarkModePreferenceController.this.mPreference.setEntryEnabled(
                                it.next(), false);
                    }
                    SecDarkModePreferenceController.this.mPreference.setValue("1");
                    return;
                }
                SecDarkModePreferenceController.this.mPreference.setEnabled(true);
                Iterator<String> it2 =
                        SecDarkModePreferenceController.this.getEntryValues().iterator();
                while (it2.hasNext()) {
                    SecDarkModePreferenceController.this.mPreference.setEntryEnabled(
                            it2.next(), true);
                }
                SecDarkModePreferenceController.this.mPreference.setValue(
                        SecDarkModePreferenceController.this.getSelectedValue());
            }
        }

        public final void setListening(boolean z) {
            if (!z) {
                ((AbstractPreferenceController) SecDarkModePreferenceController.this)
                        .mContext
                        .getContentResolver()
                        .unregisterContentObserver(this);
                return;
            }
            ((AbstractPreferenceController) SecDarkModePreferenceController.this)
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(this.NIGHT_THEME_URI, false, this);
            ((AbstractPreferenceController) SecDarkModePreferenceController.this)
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(this.LOW_POWER_MODE_ENABLED, false, this);
            ((AbstractPreferenceController) SecDarkModePreferenceController.this)
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(
                            this.POWER_SAVING_TURN_ON_DARK_MODE_ENABLED, false, this);
        }
    }

    public SecDarkModePreferenceController(Context context, String str) {
        super(context, str);
        this.mIsPowerturnmode = false;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mSettingsObserver = new SettingsObserver(this.mHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDarkModeScheduled() {
        return Settings.System.getInt(
                        this.mContext.getContentResolver(), "display_night_theme_scheduled", 0)
                == 1;
    }

    private boolean isPowerSavingAndTurnOnDarkModeEnabled() {
        return Utils.isMediumPowerSavingModeEnabled(this.mContext)
                && Settings.Global.getInt(
                                this.mContext.getContentResolver(),
                                "pms_settings_dark_mode_enabled",
                                -1)
                        == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loggingIfNeed(int i) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (contentResolver != null
                && Settings.Global.getInt(contentResolver, KEY_UI_NIGHT_MODE_ON_SUW, 2) == 1
                && i == 0) {
            LoggingHelper.insertEventLogging(46, 7551);
            Settings.Global.putInt(contentResolver, KEY_UI_NIGHT_MODE_ON_SUW, 2);
        }
    }

    private void registerStateCode() {
        if (Settings.System.getInt(this.mContext.getContentResolver(), "ultra_powersaving_mode", 0)
                == 1) {
            setStatusCode("disabled_by_power_saving_mode");
        }
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        Log.secE(TAG, "displayPreference");
        super.displayPreference(preferenceScreen);
        SecHorizontalRadioPreference secHorizontalRadioPreference =
                (SecHorizontalRadioPreference) preferenceScreen.findPreference(KEY);
        this.mPreference = secHorizontalRadioPreference;
        boolean z = false;
        secHorizontalRadioPreference.mIsDividerEnabled = false;
        secHorizontalRadioPreference.mIsTouchEffectEnabled = false;
        if (!isPowerSavingAndTurnOnDarkModeEnabled()
                && SecDisplayUtils.canChangeNightMode(this.mContext)) {
            z = true;
        }
        Iterator<String> it = getEntryValues().iterator();
        while (it.hasNext()) {
            this.mPreference.setEntryEnabled(it.next(), z);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        registerStateCode();
        return (isPowerSavingAndTurnOnDarkModeEnabled()
                        || !SecDisplayUtils.canChangeNightMode(this.mContext))
                ? 5
                : 0;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getEntries() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(this.mContext.getString(R.string.sec_dark_mode_light));
        arrayList.add(this.mContext.getString(R.string.sec_dark_mode_dark));
        return arrayList;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public ArrayList<String> getEntryValues() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("0");
        arrayList.add("1");
        return arrayList;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    @Nullable
    public ArrayList<Integer> getImageEntries() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(Integer.valueOf(R.drawable.sec_display_help_light_mode));
        arrayList.add(Integer.valueOf(R.drawable.sec_display_help_dark_mode));
        return arrayList;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = SecDarkModeSettingsFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 7449;
        return SecAccountPreferenceController$$ExternalSyntheticOutline0.m(
                subSettingLauncher, null, R.string.sec_dark_mode_settings, 268468224);
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public String getSelectedValue() {
        return Settings.System.getInt(this.mContext.getContentResolver(), "display_night_theme", 0)
                        == 1
                ? "1"
                : "0";
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    @Nullable
    public ArrayList<String> getSubEntries() {
        return null;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
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
            if (isPowerSavingAndTurnOnDarkModeEnabled()
                    || !SecDisplayUtils.canChangeNightMode(this.mContext)) {
                this.mPreference.setEnabled(false);
                Iterator<String> it = getEntryValues().iterator();
                while (it.hasNext()) {
                    this.mPreference.setEntryEnabled(it.next(), false);
                }
            } else {
                this.mPreference.setEnabled(true);
                Iterator<String> it2 = getEntryValues().iterator();
                while (it2.hasNext()) {
                    this.mPreference.setEntryEnabled(it2.next(), true);
                }
            }
        }
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            settingsObserver.setListening(true);
        }
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController
    public boolean setSelectedValue(String str) {
        if (str.equals(getSelectedValue())) {
            return true;
        }
        final UiModeManager uiModeManager =
                (UiModeManager) this.mContext.getSystemService("uimode");
        final boolean equals = "1".equals(str);
        final int i = equals ? 2 : 1;
        LoggingHelper.insertEventLogging(46, 7447, equals ? 1L : 0L);
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.display.controller.SecDarkModePreferenceController.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (SecDarkModePreferenceController.this.isDarkModeScheduled()) {
                            uiModeManager.setNightModeActivated(equals);
                        } else {
                            SecDarkModePreferenceController.this.loggingIfNeed(equals ? 1 : 0);
                            uiModeManager.setNightMode(i);
                        }
                        if (equals && SecDarkModePreferenceController.this.isDarkModeScheduled()) {
                            Toast.makeText(
                                            ((AbstractPreferenceController)
                                                            SecDarkModePreferenceController.this)
                                                    .mContext,
                                            ((AbstractPreferenceController)
                                                            SecDarkModePreferenceController.this)
                                                    .mContext.getString(
                                                            R.string
                                                                    .sec_night_theme_scheduled_toast),
                                            0)
                                    .show();
                        }
                    }
                });
        return true;
    }

    @Override // com.android.settings.core.SecSingleChoicePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
