package com.android.settings.gestures;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PreventRingingParentPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    static final int KEY_CHORD_POWER_VOLUME_UP_MUTE_TOGGLE = 1;
    final String SECURE_KEY;
    private PrimarySwitchPreference mPreference;
    private SettingObserver mSettingObserver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingObserver extends ContentObserver {
        public final Uri mKeyChordVolumePowerUpUri;
        public final Preference mPreference;
        public final Uri mVolumeHushGestureUri;

        public SettingObserver(Preference preference) {
            super(new Handler());
            this.mVolumeHushGestureUri = Settings.Secure.getUriFor("volume_hush_gesture");
            this.mKeyChordVolumePowerUpUri = Settings.Global.getUriFor("key_chord_power_volume_up");
            this.mPreference = preference;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (uri == null
                    || this.mVolumeHushGestureUri.equals(uri)
                    || this.mKeyChordVolumePowerUpUri.equals(uri)) {
                PreventRingingParentPreferenceController.this.updateState(this.mPreference);
            }
        }
    }

    public PreventRingingParentPreferenceController(Context context, String str) {
        super(context, str);
        this.SECURE_KEY = "volume_hush_gesture";
    }

    private boolean isVolumePowerKeyChordSetToHush() {
        return Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        "key_chord_power_volume_up",
                        this.mContext
                                .getResources()
                                .getInteger(
                                        R.integer.config_minMillisBetweenInputUserActivityEvents))
                == 1;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (PrimarySwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mSettingObserver = new SettingObserver(this.mPreference);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!this.mContext.getResources().getBoolean(R.bool.config_wirelessConsentRequired)) {
            return 3;
        }
        if (isVolumePowerKeyChordSetToHush()) {
            return 0;
        }
        return this.mContext
                        .getResources()
                        .getBoolean(R.bool.config_multiuserVisibleBackgroundUsers)
                ? 5
                : 3;
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
        return com.android.settings.R.string.menu_key_sound;
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
        return isVolumePowerKeyChordSetToHush()
                && Settings.Secure.getInt(
                                this.mContext.getContentResolver(), "volume_hush_gesture", 1)
                        != 0;
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
        SettingObserver settingObserver = this.mSettingObserver;
        if (settingObserver != null) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            contentResolver.registerContentObserver(
                    settingObserver.mKeyChordVolumePowerUpUri, false, settingObserver);
            contentResolver.registerContentObserver(
                    settingObserver.mVolumeHushGestureUri, false, settingObserver);
            this.mSettingObserver.onChange(false, null);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        SettingObserver settingObserver = this.mSettingObserver;
        if (settingObserver != null) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            settingObserver.getClass();
            contentResolver.unregisterContentObserver(settingObserver);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        int i =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "volume_hush_gesture", 1);
        int i2 = i != 0 ? i : 1;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (!z) {
            i2 = 0;
        }
        return Settings.Secure.putInt(contentResolver, "volume_hush_gesture", i2);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        CharSequence text;
        super.updateState(preference);
        int i =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "volume_hush_gesture", 1);
        if (isVolumePowerKeyChordSetToHush()) {
            text =
                    i != 1
                            ? i != 2
                                    ? this.mContext.getText(
                                            com.android.settings.R.string.switch_off_text)
                                    : this.mContext.getText(
                                            com.android.settings.R.string
                                                    .prevent_ringing_option_mute_summary)
                            : this.mContext.getText(
                                    com.android.settings.R.string
                                            .prevent_ringing_option_vibrate_summary);
            preference.setEnabled(true);
            this.mPreference.setSwitchEnabled(true);
        } else {
            text =
                    this.mContext.getText(
                            com.android.settings.R.string
                                    .prevent_ringing_option_unavailable_lpp_summary);
            preference.setEnabled(false);
            this.mPreference.setSwitchEnabled(false);
        }
        preference.setSummary(text);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
