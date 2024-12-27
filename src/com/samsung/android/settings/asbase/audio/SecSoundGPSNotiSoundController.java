package com.samsung.android.settings.asbase.audio;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSoundGPSNotiSoundController extends SecSoundTogglePreferenceController {
    private static final String KEY = "gps_notification_sounds";

    public SecSoundGPSNotiSoundController(
            Context context,
            SettingsPreferenceFragment settingsPreferenceFragment,
            Lifecycle lifecycle) {
        super(context, KEY);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return "DCM".equals(Utils.getSalesCode()) ? 0 : 2;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController
    public Uri getDatabaseUri() {
        return Settings.System.getUriFor("gps_noti_sound_enabled");
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.System.getInt(getContentResolver(), "gps_noti_sound_enabled", 1) == 1;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        return Settings.System.putInt(getContentResolver(), "gps_noti_sound_enabled", z ? 1 : 0);
    }

    @Override // com.samsung.android.settings.asbase.audio.SecSoundTogglePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
