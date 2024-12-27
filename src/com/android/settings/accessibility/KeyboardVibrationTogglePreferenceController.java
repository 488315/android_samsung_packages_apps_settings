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
import android.os.vibrator.Flags;
import android.provider.Settings;
import android.util.Log;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class KeyboardVibrationTogglePreferenceController extends TogglePreferenceController
        implements DefaultLifecycleObserver {
    private static final Uri MAIN_VIBRATION_SWITCH_URI = Settings.System.getUriFor("vibrate_on");
    private static final String TAG = "KeyboardVibrateControl";
    private final ContentObserver mContentObserver;
    private MetricsFeatureProvider mMetricsFeatureProvider;
    private TwoStatePreference mPreference;
    private final Vibrator mVibrator;

    public KeyboardVibrationTogglePreferenceController(Context context, String str) {
        super(context, str);
        this.mVibrator = (Vibrator) context.getSystemService(Vibrator.class);
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                true)) { // from class:
                                         // com.android.settings.accessibility.KeyboardVibrationTogglePreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        if (uri.equals(
                                KeyboardVibrationTogglePreferenceController
                                        .MAIN_VIBRATION_SWITCH_URI)) {
                            KeyboardVibrationTogglePreferenceController
                                    keyboardVibrationTogglePreferenceController =
                                            KeyboardVibrationTogglePreferenceController.this;
                            keyboardVibrationTogglePreferenceController.updateState(
                                    keyboardVibrationTogglePreferenceController.mPreference);
                        } else {
                            Log.w(
                                    KeyboardVibrationTogglePreferenceController.TAG,
                                    "Unexpected uri change:" + uri);
                        }
                    }
                };
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    private boolean isKeyboardVibrationSwitchEnabled() {
        return Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        "keyboard_vibration_enabled",
                        this.mVibrator.isDefaultKeyboardVibrationEnabled() ? 1 : 0)
                == 1;
    }

    private boolean isPreferenceEnabled() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        VibrationEffect vibrationEffect = VibrationPreferenceConfig.PREVIEW_VIBRATION_EFFECT;
        return Settings.System.getInt(contentResolver, "vibrate_on", 1) == 1;
    }

    private boolean updateKeyboardVibrationSetting(boolean z) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean putInt =
                Settings.System.putInt(contentResolver, "keyboard_vibration_enabled", z ? 1 : 0);
        contentResolver.notifyChange(
                Settings.System.getUriFor("keyboard_vibration_enabled"),
                (ContentObserver) null,
                NetworkAnalyticsConstants.DataPoints.FLAG_UID);
        if (!putInt) {
            Log.w(TAG, "Update settings database error!");
        }
        return putInt;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = (TwoStatePreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (Flags.keyboardCategoryEnabled()
                        && this.mContext
                                .getResources()
                                .getBoolean(R.bool.config_keyboard_vibration_supported)
                        && this.mContext
                                        .getResources()
                                        .getFloat(android.R.dimen.config_qsTileStrokeWidthInactive)
                                > 0.0f)
                ? 0
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
        return isPreferenceEnabled() && isKeyboardVibrationSwitchEnabled();
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

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        super.onCreate(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
        super.onDestroy(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        super.onPause(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        super.onResume(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        this.mContext
                .getContentResolver()
                .registerContentObserver(MAIN_VIBRATION_SWITCH_URI, false, this.mContentObserver);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        boolean updateKeyboardVibrationSetting = updateKeyboardVibrationSetting(z);
        this.mMetricsFeatureProvider.action(this.mContext, 1900, z);
        if (updateKeyboardVibrationSetting && z) {
            VibrationEffect vibrationEffect = VibrationPreferenceConfig.PREVIEW_VIBRATION_EFFECT;
            this.mVibrator.vibrate(
                    VibrationPreferenceConfig.PREVIEW_VIBRATION_EFFECT,
                    new VibrationAttributes.Builder(
                                    new VibrationAttributes.Builder()
                                            .setUsage(18)
                                            .setFlags(5)
                                            .build())
                            .setCategory(1)
                            .build());
        }
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference != null) {
            super.updateState(preference);
            preference.setEnabled(isPreferenceEnabled());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
