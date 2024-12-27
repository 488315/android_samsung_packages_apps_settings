package com.samsung.android.settings.display.controller;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecShowChargingInfoPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final int DISABLE_CHARGING_INFO = 0;
    private static final int ENABLE_CHARGING_INFO = 1;
    public static final String SETTINGS_KEY_CHARGING_INFO_ALWAYS = "charging_info_always";
    private static final int UNSUPPPORTED_CHARGING_INFO = -1;
    private ContentObserver mContentObserver;
    private SecSwitchPreference mPreference;

    public SecShowChargingInfoPreferenceController(Context context, String str) {
        super(context, str);
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.controller.SecShowChargingInfoPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        SecShowChargingInfoPreferenceController
                                secShowChargingInfoPreferenceController =
                                        SecShowChargingInfoPreferenceController.this;
                        secShowChargingInfoPreferenceController.updateState(
                                secShowChargingInfoPreferenceController.mPreference);
                    }
                };
    }

    private ContentResolver getContentResolver() {
        return this.mContext.getContentResolver();
    }

    private Uri getDatabaseUri() {
        return Settings.System.getUriFor(SETTINGS_KEY_CHARGING_INFO_ALWAYS);
    }

    private void updateShowChargingInfoState(Preference preference) {
        if (preference instanceof SecSwitchPreference) {
            int chargingInfoAlways = SecDisplayUtils.getChargingInfoAlways(this.mContext);
            if (Rune.isSamsungDexMode(this.mContext)
                    && Utils.isDesktopStandaloneMode(this.mContext)) {
                preference.setEnabled(false);
            } else if (chargingInfoAlways != -1) {
                preference.setEnabled(true);
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return SecDisplayUtils.getChargingInfoAlways(this.mContext) != -1 ? 0 : 3;
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
        return R.string.menu_key_display;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return Rune.SUPPORT_BATTERY_CHARGING_ESTIMATE_TIME
                ? Rune.SUPPORT_AOD
                        ? this.mContext.getString(R.string.sec_show_charging_info_summary_support)
                        : this.mContext.getString(
                                R.string.sec_show_charging_info_summary_support_no_aod)
                : this.mContext.getString(R.string.sec_show_charging_info_summary);
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
        return SecDisplayUtils.getChargingInfoAlways(this.mContext) == 1;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        getContentResolver()
                .registerContentObserver(getDatabaseUri(), false, this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        LoggingHelper.insertEventLogging(46, 7471, z ? 1L : 0L);
        SecDisplayUtils.setChargingInfoAlways(this.mContext, z ? 1 : 0);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference == null) {
            return;
        }
        updateShowChargingInfoState(preference);
        refreshSummary(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
