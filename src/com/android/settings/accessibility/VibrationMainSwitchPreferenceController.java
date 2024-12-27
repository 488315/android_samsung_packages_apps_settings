package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.widget.SettingsMainSwitchPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class VibrationMainSwitchPreferenceController extends SettingsMainSwitchPreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private final ContentObserver mSettingObserver;
    private final Vibrator mVibrator;

    public VibrationMainSwitchPreferenceController(Context context, String str) {
        super(context, str);
        this.mVibrator = (Vibrator) context.getSystemService(Vibrator.class);
        this.mSettingObserver =
                new ContentObserver(
                        new Handler(
                                true)) { // from class:
                                         // com.android.settings.accessibility.VibrationMainSwitchPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        VibrationMainSwitchPreferenceController
                                vibrationMainSwitchPreferenceController =
                                        VibrationMainSwitchPreferenceController.this;
                        vibrationMainSwitchPreferenceController.updateState(
                                ((SettingsMainSwitchPreferenceController)
                                                vibrationMainSwitchPreferenceController)
                                        .mSwitchPreference);
                    }
                };
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_accessibility;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        VibrationEffect vibrationEffect = VibrationPreferenceConfig.PREVIEW_VIBRATION_EFFECT;
        return Settings.System.getInt(contentResolver, "vibrate_on", 1) == 1;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("vibrate_on"), false, this.mSettingObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingObserver);
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        boolean threadEnabled = getThreadEnabled();
        boolean putInt =
                Settings.System.putInt(this.mContext.getContentResolver(), "vibrate_on", z ? 1 : 0);
        if (putInt && !threadEnabled && z) {
            Vibrator vibrator = this.mVibrator;
            VibrationEffect vibrationEffect = VibrationPreferenceConfig.PREVIEW_VIBRATION_EFFECT;
            vibrator.vibrate(
                    VibrationPreferenceConfig.PREVIEW_VIBRATION_EFFECT,
                    new VibrationAttributes.Builder().setUsage(18).setFlags(5).build());
        }
        return putInt;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
