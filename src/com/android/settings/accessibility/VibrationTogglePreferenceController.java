package com.android.settings.accessibility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class VibrationTogglePreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    protected final VibrationPreferenceConfig mPreferenceConfig;
    private final VibrationPreferenceConfig.SettingObserver mSettingsContentObserver;

    public VibrationTogglePreferenceController(
            Context context, String str, VibrationPreferenceConfig vibrationPreferenceConfig) {
        super(context, str);
        this.mPreferenceConfig = vibrationPreferenceConfig;
        this.mSettingsContentObserver =
                new VibrationPreferenceConfig.SettingObserver(vibrationPreferenceConfig);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        VibrationPreferenceConfig.SettingObserver settingObserver = this.mSettingsContentObserver;
        settingObserver.mPreferenceController = this;
        settingObserver.mPreference = findPreference;
        findPreference.setEnabled(this.mPreferenceConfig.isPreferenceEnabled());
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
        return R.string.menu_key_accessibility;
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
        return this.mPreferenceConfig.isPreferenceEnabled()
                && this.mPreferenceConfig.readIntensity() != 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mSettingsContentObserver.register(this.mContext);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        VibrationPreferenceConfig.SettingObserver settingObserver = this.mSettingsContentObserver;
        Context context = this.mContext;
        VibrationPreferenceConfig.SettingObserver.AnonymousClass1 anonymousClass1 =
                settingObserver.mRingerModeChangeReceiver;
        if (anonymousClass1 != null) {
            context.unregisterReceiver(anonymousClass1);
        }
        context.getContentResolver().unregisterContentObserver(settingObserver);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (!this.mPreferenceConfig.isPreferenceEnabled()) {
            return false;
        }
        boolean updateIntensity =
                this.mPreferenceConfig.updateIntensity(
                        z ? this.mPreferenceConfig.mDefaultIntensity : 0);
        if (updateIntensity && z) {
            VibrationPreferenceConfig vibrationPreferenceConfig = this.mPreferenceConfig;
            vibrationPreferenceConfig.mVibrator.vibrate(
                    VibrationPreferenceConfig.PREVIEW_VIBRATION_EFFECT,
                    vibrationPreferenceConfig.mPreviewVibrationAttributes);
        }
        return updateIntensity;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference != null) {
            preference.setEnabled(this.mPreferenceConfig.isPreferenceEnabled());
            VibrationPreferenceConfig vibrationPreferenceConfig = this.mPreferenceConfig;
            preference.setSummary(
                    (vibrationPreferenceConfig.isRestrictedByRingerModeSilent()
                                    && vibrationPreferenceConfig.mAudioManager
                                                    .getRingerModeInternal()
                                            == 0)
                            ? vibrationPreferenceConfig.mRingerModeSilentSummary
                            : null);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
