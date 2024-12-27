package com.android.settings.accessibility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.DeviceConfig;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class VibrationRampingRingerTogglePreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private final AudioManager mAudioManager;
    private final DeviceConfigProvider mDeviceConfigProvider;
    private Preference mPreference;
    private final VibrationPreferenceConfig.SettingObserver mRingSettingObserver;
    private final VibrationPreferenceConfig mRingVibrationPreferenceConfig;
    private final ContentObserver mSettingObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DeviceConfigProvider {}

    public VibrationRampingRingerTogglePreferenceController(Context context, String str) {
        this(context, str, new DeviceConfigProvider());
    }

    private boolean isRingVibrationEnabled() {
        return this.mRingVibrationPreferenceConfig.isPreferenceEnabled()
                && this.mRingVibrationPreferenceConfig.readIntensity() != 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = findPreference;
        VibrationPreferenceConfig.SettingObserver settingObserver = this.mRingSettingObserver;
        settingObserver.mPreferenceController = this;
        settingObserver.mPreference = findPreference;
        findPreference.setEnabled(isRingVibrationEnabled());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        this.mDeviceConfigProvider.getClass();
        return (!Utils.isVoiceCapable(this.mContext)
                        || DeviceConfig.getBoolean("telephony", "ramping_ringer_enabled", false))
                ? 3
                : 0;
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
        return isRingVibrationEnabled() && this.mAudioManager.isRampingRingerEnabled();
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
        this.mRingSettingObserver.register(this.mContext);
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("apply_ramping_ringer"),
                        false,
                        this.mSettingObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        VibrationPreferenceConfig.SettingObserver settingObserver = this.mRingSettingObserver;
        Context context = this.mContext;
        VibrationPreferenceConfig.SettingObserver.AnonymousClass1 anonymousClass1 =
                settingObserver.mRingerModeChangeReceiver;
        if (anonymousClass1 != null) {
            context.unregisterReceiver(anonymousClass1);
        }
        context.getContentResolver().unregisterContentObserver(settingObserver);
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingObserver);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (!isRingVibrationEnabled()) {
            return true;
        }
        this.mAudioManager.setRampingRingerEnabled(z);
        if (!z) {
            return true;
        }
        VibrationPreferenceConfig vibrationPreferenceConfig = this.mRingVibrationPreferenceConfig;
        vibrationPreferenceConfig.mVibrator.vibrate(
                VibrationPreferenceConfig.PREVIEW_VIBRATION_EFFECT,
                vibrationPreferenceConfig.mPreviewVibrationAttributes);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference != null) {
            preference.setEnabled(isRingVibrationEnabled());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public VibrationRampingRingerTogglePreferenceController(
            Context context, String str, DeviceConfigProvider deviceConfigProvider) {
        super(context, str);
        this.mDeviceConfigProvider = deviceConfigProvider;
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
        RingVibrationPreferenceConfig ringVibrationPreferenceConfig =
                new RingVibrationPreferenceConfig(context);
        this.mRingVibrationPreferenceConfig = ringVibrationPreferenceConfig;
        this.mRingSettingObserver =
                new VibrationPreferenceConfig.SettingObserver(ringVibrationPreferenceConfig);
        this.mSettingObserver =
                new ContentObserver(
                        new Handler(
                                true)) { // from class:
                                         // com.android.settings.accessibility.VibrationRampingRingerTogglePreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        VibrationRampingRingerTogglePreferenceController
                                vibrationRampingRingerTogglePreferenceController =
                                        VibrationRampingRingerTogglePreferenceController.this;
                        vibrationRampingRingerTogglePreferenceController.updateState(
                                vibrationRampingRingerTogglePreferenceController.mPreference);
                    }
                };
    }
}
