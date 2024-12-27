package com.android.settings.accessibility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.SliderPreferenceController;
import com.android.settings.widget.SeekBarPreference;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class VibrationIntensityPreferenceController extends SliderPreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private final int mMaxIntensity;
    protected final VibrationPreferenceConfig mPreferenceConfig;
    private final VibrationPreferenceConfig.SettingObserver mSettingsContentObserver;

    public VibrationIntensityPreferenceController(
            Context context, String str, VibrationPreferenceConfig vibrationPreferenceConfig) {
        this(
                context,
                str,
                vibrationPreferenceConfig,
                context.getResources()
                        .getInteger(R.integer.config_vibration_supported_intensity_levels));
    }

    private int calculateVibrationIntensity(int i) {
        int max = getMax();
        if (i < max) {
            return i;
        }
        if (max == 1) {
            return this.mPreferenceConfig.mDefaultIntensity;
        }
        return 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence lambda$displayPreference$0(Preference preference) {
        VibrationPreferenceConfig vibrationPreferenceConfig = this.mPreferenceConfig;
        if (vibrationPreferenceConfig.isRestrictedByRingerModeSilent()
                && vibrationPreferenceConfig.mAudioManager.getRingerModeInternal() == 0) {
            return vibrationPreferenceConfig.mRingerModeSilentSummary;
        }
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SeekBarPreference seekBarPreference =
                (SeekBarPreference) preferenceScreen.findPreference(getPreferenceKey());
        VibrationPreferenceConfig.SettingObserver settingObserver = this.mSettingsContentObserver;
        settingObserver.mPreferenceController = this;
        settingObserver.mPreference = seekBarPreference;
        seekBarPreference.setEnabled(this.mPreferenceConfig.isPreferenceEnabled());
        seekBarPreference.setSummaryProvider(
                new Preference
                        .SummaryProvider() { // from class:
                                             // com.android.settings.accessibility.VibrationIntensityPreferenceController$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.SummaryProvider
                    public final CharSequence provideSummary(Preference preference) {
                        CharSequence lambda$displayPreference$0;
                        lambda$displayPreference$0 =
                                VibrationIntensityPreferenceController.this
                                        .lambda$displayPreference$0(preference);
                        return lambda$displayPreference$0;
                    }
                });
        seekBarPreference.setMin(getMin());
        seekBarPreference.setMax(getMax());
        seekBarPreference.mContinuousUpdates = true;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMax() {
        return this.mMaxIntensity;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getMin() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public int getSliderPosition() {
        return !this.mPreferenceConfig.isPreferenceEnabled()
                ? getMin()
                : Math.min(this.mPreferenceConfig.readIntensity(), getMax());
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
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

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.SliderPreferenceController
    public boolean setSliderPosition(int i) {
        if (!this.mPreferenceConfig.isPreferenceEnabled()) {
            return false;
        }
        boolean updateIntensity =
                this.mPreferenceConfig.updateIntensity(calculateVibrationIntensity(i));
        if (updateIntensity && i != 0) {
            VibrationPreferenceConfig vibrationPreferenceConfig = this.mPreferenceConfig;
            vibrationPreferenceConfig.mVibrator.vibrate(
                    VibrationPreferenceConfig.PREVIEW_VIBRATION_EFFECT,
                    vibrationPreferenceConfig.mPreviewVibrationAttributes);
        }
        return updateIntensity;
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference != null) {
            preference.setEnabled(this.mPreferenceConfig.isPreferenceEnabled());
        }
    }

    @Override // com.android.settings.core.SliderPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public VibrationIntensityPreferenceController(
            Context context,
            String str,
            VibrationPreferenceConfig vibrationPreferenceConfig,
            int i) {
        super(context, str);
        this.mPreferenceConfig = vibrationPreferenceConfig;
        this.mSettingsContentObserver =
                new VibrationPreferenceConfig.SettingObserver(vibrationPreferenceConfig);
        this.mMaxIntensity = Math.min(3, i);
    }
}
