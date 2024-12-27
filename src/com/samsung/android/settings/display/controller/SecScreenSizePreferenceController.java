package com.samsung.android.settings.display.controller;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.Signature;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.DisplayDisabledAppearancePreference;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.display.SecScreenZoomPreferenceFragment;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecScreenSizePreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final String TAG = "SecScreenSizePreferenceController";
    private ContentObserver mContentObserver;
    private DisplayDisabledAppearancePreference mPreference;

    public SecScreenSizePreferenceController(Context context, String str) {
        super(context, str);
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.controller.SecScreenSizePreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        SecScreenSizePreferenceController secScreenSizePreferenceController =
                                SecScreenSizePreferenceController.this;
                        secScreenSizePreferenceController.updateState(
                                secScreenSizePreferenceController.mPreference);
                    }
                };
    }

    private ContentResolver getContentResolver() {
        return this.mContext.getContentResolver();
    }

    private Uri getDatabaseUri() {
        return Settings.System.getUriFor("easy_mode_switch");
    }

    private boolean isLegacyDexMode() {
        if (!Rune.isSamsungDexMode(this.mContext)
                && !Utils.isDesktopStandaloneMode(this.mContext)) {
            Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        } else if (!Utils.isNewDexMode(this.mContext)) {
            return true;
        }
        return false;
    }

    private void updateScreenSizeState() {
        boolean isLegacyDexMode = isLegacyDexMode();
        this.mPreference.setEnabledAppearance(!isLegacyDexMode);
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        this.mPreference.setEnabled(!isLegacyDexMode);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (DisplayDisabledAppearancePreference)
                        preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (((UserHandle.myUserId() != 0)
                                || (Settings.System.getInt(
                                                getContentResolver(), "easy_mode_switch", 1)
                                        == 0))
                        && !Utils.isNewDexMode(this.mContext))
                ? 3
                : 0;
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
    public Intent getLaunchIntent() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = SecScreenZoomPreferenceFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        subSettingLauncher.setTitleRes(R.string.sec_screen_zoom_title, null);
        launchRequest.mSourceMetricsCategory = 7448;
        subSettingLauncher.addFlags(268468224);
        return subSettingLauncher.toIntent();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        if ((Settings.System.getInt(getContentResolver(), "easy_mode_switch", 1) == 0
                        && !Utils.isNewDexMode(this.mContext))
                || isLegacyDexMode()) {
            return false;
        }
        Bundle bundle = new Bundle();
        if (preference.getKey().equalsIgnoreCase("sec_screen_size_a11y")) {
            bundle.putBoolean("FROM_ACCESSIBILITY", true);
        }
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = SecScreenZoomPreferenceFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        subSettingLauncher.setTitleRes(R.string.sec_screen_zoom_title, null);
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = 7448;
        subSettingLauncher.launch();
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        getContentResolver()
                .registerContentObserver(getDatabaseUri(), false, this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updatePreference() {
        boolean z = true;
        boolean z2 = Settings.System.getInt(getContentResolver(), "easy_mode_switch", 1) == 0;
        DisplayDisabledAppearancePreference displayDisabledAppearancePreference = this.mPreference;
        if (!isAvailable() || (z2 && !Utils.isNewDexMode(this.mContext))) {
            z = false;
        }
        displayDisabledAppearancePreference.setVisible(z);
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        this.mPreference.setTitle(R.string.sec_screen_zoom_title);
        updateScreenSizeState();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference == null) {
            return;
        }
        updatePreference();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
