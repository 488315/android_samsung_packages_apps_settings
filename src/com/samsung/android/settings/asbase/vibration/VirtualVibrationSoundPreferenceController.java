package com.samsung.android.settings.asbase.vibration;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class VirtualVibrationSoundPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final int DEFAULT_VALUE = 0;
    private static final String KEY_VIBRATION_SOUND_ENABLED = "vibration_sound_enabled";
    private static final int NOTIFICATION_VIBRATION_SOUND_ENABLED = 1;
    private SecSwitchPreference mPreference;
    private SettingObserver mSettingObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingObserver extends ContentObserver {
        public final Uri VIBRATION_SOUND_ENABLED_URI;
        public final Preference mPreference;

        public SettingObserver(Preference preference) {
            super(new Handler());
            this.VIBRATION_SOUND_ENABLED_URI =
                    Settings.System.getUriFor(
                            VirtualVibrationSoundPreferenceController.KEY_VIBRATION_SOUND_ENABLED);
            this.mPreference = preference;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (this.VIBRATION_SOUND_ENABLED_URI.equals(uri)) {
                VirtualVibrationSoundPreferenceController.this.updateState(this.mPreference);
            }
        }
    }

    public VirtualVibrationSoundPreferenceController(Context context, String str) {
        super(context, KEY_VIBRATION_SOUND_ENABLED);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = secSwitchPreference;
        if (secSwitchPreference != null) {
            this.mSettingObserver = new SettingObserver(this.mPreference);
            this.mPreference.setPersistent();
            this.mPreference.setSummary(
                    this.mContext
                            .getResources()
                            .getString(R.string.sec_vibration_sound_for_incoming_calls_summary));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_VIBRATION_SOUND_ENABLED;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_sound;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlValue getValue() {
        boolean z =
                Settings.System.getInt(
                                this.mContext.getContentResolver(), KEY_VIBRATION_SOUND_ENABLED, 0)
                        == 1;
        ControlValue.Builder builder =
                new ControlValue.Builder(getPreferenceKey(), getControlType());
        builder.setValue(Boolean.valueOf(z));
        builder.mAvailabilityStatus = getAvailabilityStatusForControl();
        builder.mStatusCode = getStatusCode();
        return builder.build();
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
        return Settings.System.getInt(
                        this.mContext.getContentResolver(), KEY_VIBRATION_SOUND_ENABLED, 0)
                != 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return TextUtils.equals(getPreferenceKey(), KEY_VIBRATION_SOUND_ENABLED);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        SettingObserver settingObserver = this.mSettingObserver;
        if (settingObserver != null) {
            VirtualVibrationSoundPreferenceController.this
                    .mContext
                    .getContentResolver()
                    .unregisterContentObserver(settingObserver);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        SettingObserver settingObserver = this.mSettingObserver;
        if (settingObserver != null) {
            VirtualVibrationSoundPreferenceController.this
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(
                            settingObserver.VIBRATION_SOUND_ENABLED_URI, false, settingObserver);
        }
        SecSwitchPreference secSwitchPreference = this.mPreference;
        if (secSwitchPreference != null) {
            secSwitchPreference.setChecked(
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    KEY_VIBRATION_SOUND_ENABLED,
                                    0)
                            == 1);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        LoggingHelper.insertEventLogging(FileType.SDOCX, 7250, z);
        if (z) {
            new Handler(Looper.getMainLooper())
                    .postDelayed(
                            new Runnable() { // from class:
                                             // com.samsung.android.settings.asbase.vibration.VirtualVibrationSoundPreferenceController.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    if (Utils.isTablet()) {
                                        Toast.makeText(
                                                        ((AbstractPreferenceController)
                                                                        VirtualVibrationSoundPreferenceController
                                                                                .this)
                                                                .mContext,
                                                        ((AbstractPreferenceController)
                                                                        VirtualVibrationSoundPreferenceController
                                                                                .this)
                                                                .mContext
                                                                .getResources()
                                                                .getString(
                                                                        R.string
                                                                                .sec_vibration_sound_for_incoming_calls_device_description_support_watch_summary_tablet),
                                                        0)
                                                .show();
                                    } else {
                                        Toast.makeText(
                                                        ((AbstractPreferenceController)
                                                                        VirtualVibrationSoundPreferenceController
                                                                                .this)
                                                                .mContext,
                                                        ((AbstractPreferenceController)
                                                                        VirtualVibrationSoundPreferenceController
                                                                                .this)
                                                                .mContext
                                                                .getResources()
                                                                .getString(
                                                                        R.string
                                                                                .sec_vibration_sound_for_incoming_calls_device_description_support_watch_summary),
                                                        0)
                                                .show();
                                    }
                                }
                            },
                            0L);
        }
        return Settings.System.putInt(
                this.mContext.getContentResolver(), KEY_VIBRATION_SOUND_ENABLED, z ? 1 : 0);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public ControlResult setValue(ControlValue controlValue) {
        ControlResult.Builder builder = new ControlResult.Builder(getPreferenceKey());
        Settings.System.putInt(
                this.mContext.getContentResolver(),
                KEY_VIBRATION_SOUND_ENABLED,
                Boolean.parseBoolean(controlValue.getValue()) ? 1 : 0);
        builder.mResultCode = ControlResult.ResultCode.SUCCESS;
        return builder.build();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
