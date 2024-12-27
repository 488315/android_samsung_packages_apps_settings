package com.samsung.android.settings.display.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.SensorManager;
import android.hardware.SensorPrivacyManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreference;
import com.sec.ims.im.ImIntent;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecAutoBrightnessPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final int SENSOR_TYPE_CAMERA_LIGHT = 65604;
    private static final String TAG = "SecAutoBrightnessPreferenceController";
    private SensorPrivacyManager.OnSensorPrivacyChangedListener mCameraAccessListener;
    private ContentObserver mCameraModelContentObserver;
    private ContentObserver mContentObserver;
    private final boolean mIsCameraLightSensorSupported;
    private final boolean mIsCameraSensorModel;
    private SecRestrictedSwitchPreference mPreference;
    private final SensorPrivacyManager mPrivacyManager;

    public SecAutoBrightnessPreferenceController(Context context, String str) {
        super(context, str);
        this.mIsCameraSensorModel = Rune.supportCameraSensor(this.mContext);
        this.mPrivacyManager = SensorPrivacyManager.getInstance(context);
        this.mIsCameraLightSensorSupported = isCameraLightSensorSupported(context);
    }

    private boolean isCameraLightSensorSupported(Context context) {
        return ((SensorManager) context.getSystemService("sensor"))
                        .getDefaultSensor(SENSOR_TYPE_CAMERA_LIGHT)
                != null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecRestrictedSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        this.mContext, UserHandle.myUserId(), "no_config_brightness");
        if (checkIfRestrictionEnforced != null) {
            SecRestrictedSwitchPreference secRestrictedSwitchPreference = this.mPreference;
            if (secRestrictedSwitchPreference != null) {
                secRestrictedSwitchPreference.setDisabledByAdmin(checkIfRestrictionEnforced);
            }
            return 5;
        }
        if (this.mIsCameraLightSensorSupported && this.mPrivacyManager.isSensorPrivacyEnabled(2)) {
            return 5;
        }
        if (Rune.isSamsungDexMode(this.mContext) && Utils.isDesktopDualMode(this.mContext)) {
            return 5;
        }
        return Rune.supportAutoBrightness(this.mContext) ? 0 : 3;
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
        return R.string.menu_key_display;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        if (this.mIsCameraLightSensorSupported && this.mPrivacyManager.isSensorPrivacyEnabled(2)) {
            return this.mContext.getString(
                    R.string.auto_brightness_description_for_camera_access_disabled);
        }
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return ApnSettings.MVNO_NONE;
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
                        this.mContext.getContentResolver(), "screen_brightness_mode", 0)
                != 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        if (this.mContentObserver == null) {
            final int i = 0;
            this.mContentObserver =
                    new ContentObserver(
                            this,
                            new Handler(
                                    Looper
                                            .getMainLooper())) { // from class:
                                                                 // com.samsung.android.settings.display.controller.SecAutoBrightnessPreferenceController.1
                        public final /* synthetic */ SecAutoBrightnessPreferenceController this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z, Uri uri) {
                            switch (i) {
                                case 0:
                                    super.onChange(z, uri);
                                    AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                            "onChange = ",
                                            SecAutoBrightnessPreferenceController.TAG,
                                            z);
                                    SecAutoBrightnessPreferenceController
                                            secAutoBrightnessPreferenceController = this.this$0;
                                    secAutoBrightnessPreferenceController.updateState(
                                            secAutoBrightnessPreferenceController.mPreference);
                                    break;
                                default:
                                    super.onChange(z, uri);
                                    AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                            "CameraSensorModel's onChange = ",
                                            SecAutoBrightnessPreferenceController.TAG,
                                            z);
                                    SecAutoBrightnessPreferenceController
                                            secAutoBrightnessPreferenceController2 = this.this$0;
                                    secAutoBrightnessPreferenceController2.updateState(
                                            secAutoBrightnessPreferenceController2.mPreference);
                                    break;
                            }
                        }
                    };
        }
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("screen_brightness_mode"),
                        false,
                        this.mContentObserver);
        if (this.mIsCameraSensorModel && this.mCameraModelContentObserver == null) {
            final int i2 = 1;
            this.mCameraModelContentObserver =
                    new ContentObserver(
                            this,
                            new Handler(
                                    Looper
                                            .getMainLooper())) { // from class:
                                                                 // com.samsung.android.settings.display.controller.SecAutoBrightnessPreferenceController.1
                        public final /* synthetic */ SecAutoBrightnessPreferenceController this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.database.ContentObserver
                        public final void onChange(boolean z, Uri uri) {
                            switch (i2) {
                                case 0:
                                    super.onChange(z, uri);
                                    AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                            "onChange = ",
                                            SecAutoBrightnessPreferenceController.TAG,
                                            z);
                                    SecAutoBrightnessPreferenceController
                                            secAutoBrightnessPreferenceController = this.this$0;
                                    secAutoBrightnessPreferenceController.updateState(
                                            secAutoBrightnessPreferenceController.mPreference);
                                    break;
                                default:
                                    super.onChange(z, uri);
                                    AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                            "CameraSensorModel's onChange = ",
                                            SecAutoBrightnessPreferenceController.TAG,
                                            z);
                                    SecAutoBrightnessPreferenceController
                                            secAutoBrightnessPreferenceController2 = this.this$0;
                                    secAutoBrightnessPreferenceController2.updateState(
                                            secAutoBrightnessPreferenceController2.mPreference);
                                    break;
                            }
                        }
                    };
        }
        if (this.mIsCameraLightSensorSupported) {
            if (this.mCameraAccessListener == null) {
                this.mCameraAccessListener =
                        new SensorPrivacyManager
                                .OnSensorPrivacyChangedListener() { // from class:
                                                                    // com.samsung.android.settings.display.controller.SecAutoBrightnessPreferenceController.3
                            public final void onSensorPrivacyChanged(int i3, boolean z) {
                                AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                                        "CameraAccess onSensorPrivacyChanged = ",
                                        SecAutoBrightnessPreferenceController.TAG,
                                        z);
                                if (SecAutoBrightnessPreferenceController.this.mPreference
                                        != null) {
                                    SecAutoBrightnessPreferenceController.this.mPreference
                                            .setEnabled(!z);
                                }
                                SecAutoBrightnessPreferenceController
                                        secAutoBrightnessPreferenceController =
                                                SecAutoBrightnessPreferenceController.this;
                                secAutoBrightnessPreferenceController.updateState(
                                        secAutoBrightnessPreferenceController.mPreference);
                            }
                        };
            }
            this.mPrivacyManager.addSensorPrivacyListener(2, this.mCameraAccessListener);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        SensorPrivacyManager.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener;
        if (this.mContentObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
        }
        if (this.mIsCameraSensorModel && this.mCameraModelContentObserver != null) {
            this.mContext
                    .getContentResolver()
                    .unregisterContentObserver(this.mCameraModelContentObserver);
        }
        if (!this.mIsCameraLightSensorSupported
                || (onSensorPrivacyChangedListener = this.mCameraAccessListener) == null) {
            return;
        }
        this.mPrivacyManager.removeSensorPrivacyListener(2, onSensorPrivacyChangedListener);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        Settings.System.putInt(
                this.mContext.getContentResolver(), "screen_brightness_mode", z ? 1 : 0);
        LoggingHelper.insertEventLogging(46, 7400, z ? 1L : 0L);
        if (this.mIsCameraSensorModel) {
            Settings.System.putInt(
                    this.mContext.getContentResolver(), "adaptive_brightness_toggled", 1);
            if (z) {
                Intent intent = new Intent();
                intent.setAction("samsung.intent.action.START_AUTO_BRIGHTNESS_ROOT");
                intent.setComponent(
                        new ComponentName(
                                "com.samsung.adaptivebrightnessgo",
                                "com.samsung.adaptivebrightnessgo.RootService"));
                intent.putExtra(ImIntent.Extras.EXTRA_FROM, "settings");
                try {
                    this.mContext.startService(intent);
                } catch (Exception e) {
                    Log.e(TAG, "Error while calling adaptive brightness = ", e);
                }
            }
            LoggingHelper.insertEventLogging(7401, z ? 1000 : 0);
        }
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference instanceof SecRestrictedSwitchPreference) {
            refreshSummary(preference);
            if (this.mIsCameraLightSensorSupported
                    && this.mPrivacyManager.isSensorPrivacyEnabled(2)) {
                SecRestrictedSwitchPreference secRestrictedSwitchPreference =
                        (SecRestrictedSwitchPreference) preference;
                secRestrictedSwitchPreference.getClass();
                SecPreferenceUtils.applySummaryColor(secRestrictedSwitchPreference, false);
            } else {
                SecRestrictedSwitchPreference secRestrictedSwitchPreference2 =
                        (SecRestrictedSwitchPreference) preference;
                secRestrictedSwitchPreference2.getClass();
                SecPreferenceUtils.applySummaryColor(secRestrictedSwitchPreference2, true);
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
