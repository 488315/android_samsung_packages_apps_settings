package com.android.settings.gestures;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DoubleTapScreenPreferenceController extends GesturePreferenceController {
    private static final String PREF_KEY_VIDEO = "gesture_double_tap_screen_video";
    private static final String SECURE_KEY = "doze_pulse_on_double_tap";
    private final int OFF;
    private final int ON;
    private AmbientDisplayConfiguration mAmbientConfig;
    private final int mUserId;

    public DoubleTapScreenPreferenceController(Context context, String str) {
        super(context, str);
        this.ON = 1;
        this.OFF = 0;
        this.mUserId = UserHandle.myUserId();
    }

    private AmbientDisplayConfiguration getAmbientConfig() {
        if (this.mAmbientConfig == null) {
            this.mAmbientConfig = new AmbientDisplayConfiguration(this.mContext);
        }
        return this.mAmbientConfig;
    }

    public static boolean isSuggestionComplete(
            Context context, SharedPreferences sharedPreferences) {
        return isSuggestionComplete(new AmbientDisplayConfiguration(context), sharedPreferences);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !getAmbientConfig().doubleTapSensorAvailable() ? 3 : 0;
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.gestures.GesturePreferenceController
    public String getVideoPrefKey() {
        return PREF_KEY_VIDEO;
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return getAmbientConfig().doubleTapGestureEnabled(this.mUserId);
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isPublicSlice() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return TextUtils.equals(getPreferenceKey(), "gesture_double_tap_screen");
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        return Settings.Secure.putInt(this.mContext.getContentResolver(), SECURE_KEY, z ? 1 : 0);
    }

    public DoubleTapScreenPreferenceController setConfig(
            AmbientDisplayConfiguration ambientDisplayConfiguration) {
        this.mAmbientConfig = ambientDisplayConfiguration;
        return this;
    }

    @Override // com.android.settings.gestures.GesturePreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public static boolean isSuggestionComplete(
            AmbientDisplayConfiguration ambientDisplayConfiguration,
            SharedPreferences sharedPreferences) {
        return !ambientDisplayConfiguration.doubleTapSensorAvailable()
                || sharedPreferences.getBoolean(
                        "pref_double_tap_screen_suggestion_complete", false);
    }
}
