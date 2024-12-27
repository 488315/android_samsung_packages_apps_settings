package com.samsung.android.settings.usefulfeature.multiwindow.showmultiwindowmenuinfullscreen;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.controller.SecAccountPreferenceController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.usefulfeature.multiwindow.MultiwindowSettings;
import com.sec.ims.configuration.DATA;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ShowMultiWindowMenuInFullScreenViewPreferenceController
        extends TogglePreferenceController implements LifecycleObserver, OnResume, OnPause {
    private static final String TAG = "ShowMultiWindowMenuInFullScreenViewPreferenceController";
    private ContentObserver mMultiWindowMenuInFullScreenObserver;
    private SecSwitchPreference mPreference;

    public ShowMultiWindowMenuInFullScreenViewPreferenceController(Context context, String str) {
        super(context, str);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        if (Utils.isTablet()) {
            this.mPreference.setSummary(
                    R.string.sec_show_multi_window_menu_in_full_screen_view_summary);
        } else {
            this.mPreference.setSummary(
                    R.string.sec_show_multi_window_menu_in_full_screen_view_summary_fold);
        }
        super.displayPreference(preferenceScreen);
        this.mMultiWindowMenuInFullScreenObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.usefulfeature.multiwindow.showmultiwindowmenuinfullscreen.ShowMultiWindowMenuInFullScreenViewPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        if (ShowMultiWindowMenuInFullScreenViewPreferenceController.this.mPreference
                                        == null
                                || ShowMultiWindowMenuInFullScreenViewPreferenceController.this
                                                .getThreadEnabled()
                                        == ShowMultiWindowMenuInFullScreenViewPreferenceController
                                                .this
                                                .mPreference.isChecked()) {
                            return;
                        }
                        ShowMultiWindowMenuInFullScreenViewPreferenceController.this.mPreference
                                .setChecked(
                                        ShowMultiWindowMenuInFullScreenViewPreferenceController.this
                                                .getThreadEnabled());
                    }
                };
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (Utils.isTablet()) {
            return 0;
        }
        if (Build.VERSION.SEM_PLATFORM_INT <= 130100) {
            return 3;
        }
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
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
        Bundle bundle = new Bundle();
        bundle.putString(":settings:fragment_args_key", getPreferenceKey());
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = MultiwindowSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 68000;
        launchRequest.mArguments = bundle;
        return SecAccountPreferenceController$$ExternalSyntheticOutline0.m(
                subSettingLauncher, null, R.string.sec_labs_settings_title, 268468224);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_advanced_features;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
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
        return Settings.Global.getInt(
                        this.mContext.getContentResolver(), "multi_window_menu_in_full_screen", 0)
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

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        if (this.mPreference == null || this.mMultiWindowMenuInFullScreenObserver == null) {
            return;
        }
        this.mContext
                .getContentResolver()
                .unregisterContentObserver(this.mMultiWindowMenuInFullScreenObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (this.mPreference == null || this.mMultiWindowMenuInFullScreenObserver == null) {
            return;
        }
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("multi_window_menu_in_full_screen"),
                        true,
                        this.mMultiWindowMenuInFullScreenObserver);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "multi_window_menu_in_full_screen", z ? 1 : 0);
        SALogging.insertSALog(
                String.valueOf(78000), "68102", z ? "1000" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
