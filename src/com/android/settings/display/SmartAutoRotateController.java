package com.android.settings.display;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.SensorPrivacyManager;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.view.RotationPolicy;
import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.HashSet;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SmartAutoRotateController extends TogglePreferenceController
        implements LifecycleObserver {
    private final DeviceStateRotationLockSettingsManager mDeviceStateAutoRotateSettingsManager;
    private final DeviceStateRotationLockSettingsManager.DeviceStateRotationLockSettingsListener
            mDeviceStateRotationLockSettingsListener;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private final PowerManager mPowerManager;
    protected Preference mPreference;
    private final SensorPrivacyManager.OnSensorPrivacyChangedListener mPrivacyChangedListener;
    private final SensorPrivacyManager mPrivacyManager;
    private final BroadcastReceiver mReceiver;
    private RotationPolicy.RotationPolicyListener mRotationPolicyListener;

    public SmartAutoRotateController(Context context, String str) {
        super(context, str);
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.display.SmartAutoRotateController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        SmartAutoRotateController smartAutoRotateController =
                                SmartAutoRotateController.this;
                        smartAutoRotateController.updateState(
                                smartAutoRotateController.mPreference);
                    }
                };
        this.mPrivacyChangedListener =
                new SensorPrivacyManager
                        .OnSensorPrivacyChangedListener() { // from class:
                                                            // com.android.settings.display.SmartAutoRotateController.2
                    public final void onSensorPrivacyChanged(int i, boolean z) {
                        SmartAutoRotateController smartAutoRotateController =
                                SmartAutoRotateController.this;
                        smartAutoRotateController.updateState(
                                smartAutoRotateController.mPreference);
                    }
                };
        this.mDeviceStateRotationLockSettingsListener =
                new DeviceStateRotationLockSettingsManager
                        .DeviceStateRotationLockSettingsListener() { // from class:
                                                                     // com.android.settings.display.SmartAutoRotateController$$ExternalSyntheticLambda0
                    @Override // com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager.DeviceStateRotationLockSettingsListener
                    public final void onSettingsChanged() {
                        SmartAutoRotateController.this.lambda$new$0();
                    }
                };
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mPrivacyManager = SensorPrivacyManager.getInstance(context);
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mDeviceStateAutoRotateSettingsManager =
                DeviceStateRotationLockSettingsManager.getInstance(context);
    }

    public static boolean hasSufficientPermission(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String rotationResolverPackageName = packageManager.getRotationResolverPackageName();
        return rotationResolverPackageName != null
                && packageManager.checkPermission(
                                "android.permission.CAMERA", rotationResolverPackageName)
                        == 0;
    }

    public static boolean isRotationResolverServiceAvailable(Context context) {
        ResolveInfo resolveService;
        if (!context.getResources()
                .getBoolean(R.bool.config_auto_rotate_face_detection_available)) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        String rotationResolverPackageName = packageManager.getRotationResolverPackageName();
        return (TextUtils.isEmpty(rotationResolverPackageName)
                        || (resolveService =
                                        packageManager.resolveService(
                                                new Intent(
                                                                "android.service.rotationresolver.RotationResolverService")
                                                        .setPackage(rotationResolverPackageName),
                                                1048576))
                                == null
                        || resolveService.serviceInfo == null)
                ? false
                : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        updateState(this.mPreference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (isRotationResolverServiceAvailable(this.mContext)) {
            return (isRotationLocked()
                            || !hasSufficientPermission(this.mContext)
                            || isCameraLocked()
                            || isPowerSaveMode())
                    ? 5
                    : 0;
        }
        return 3;
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @VisibleForTesting
    public boolean isCameraLocked() {
        return this.mPrivacyManager.isSensorPrivacyEnabled(2);
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return (isRotationLocked()
                        || !hasSufficientPermission(this.mContext)
                        || isCameraLocked()
                        || isPowerSaveMode()
                        || Settings.Secure.getInt(
                                        this.mContext.getContentResolver(), "camera_autorotate", 0)
                                != 1)
                ? false
                : true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @VisibleForTesting
    public boolean isPowerSaveMode() {
        return this.mPowerManager.isPowerSaveMode();
    }

    public boolean isRotationLocked() {
        if (!DeviceStateAutoRotationHelper.isDeviceStateRotationEnabled(this.mContext)) {
            return RotationPolicy.isRotationLocked(this.mContext);
        }
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager =
                this.mDeviceStateAutoRotateSettingsManager;
        for (int i = 0;
                i < deviceStateRotationLockSettingsManager.mPostureRotationLockSettings.size();
                i++) {
            if (deviceStateRotationLockSettingsManager.mPostureRotationLockSettings.valueAt(i)
                    == 2) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        this.mContext.registerReceiver(
                this.mReceiver, new IntentFilter("android.os.action.POWER_SAVE_MODE_CHANGED"));
        if (this.mRotationPolicyListener == null) {
            this.mRotationPolicyListener =
                    new RotationPolicy
                            .RotationPolicyListener() { // from class:
                                                        // com.android.settings.display.SmartAutoRotateController.3
                        public final void onChange() {
                            SmartAutoRotateController smartAutoRotateController =
                                    SmartAutoRotateController.this;
                            smartAutoRotateController.updateState(
                                    smartAutoRotateController.mPreference);
                        }
                    };
        }
        RotationPolicy.registerRotationPolicyListener(this.mContext, this.mRotationPolicyListener);
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager =
                this.mDeviceStateAutoRotateSettingsManager;
        ((HashSet) deviceStateRotationLockSettingsManager.mListeners)
                .add(this.mDeviceStateRotationLockSettingsListener);
        this.mPrivacyManager.addSensorPrivacyListener(2, this.mPrivacyChangedListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        this.mContext.unregisterReceiver(this.mReceiver);
        RotationPolicy.RotationPolicyListener rotationPolicyListener = this.mRotationPolicyListener;
        if (rotationPolicyListener != null) {
            RotationPolicy.unregisterRotationPolicyListener(this.mContext, rotationPolicyListener);
            this.mRotationPolicyListener = null;
        }
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager =
                this.mDeviceStateAutoRotateSettingsManager;
        if (!((HashSet) deviceStateRotationLockSettingsManager.mListeners)
                .remove(this.mDeviceStateRotationLockSettingsListener)) {
            Log.w(
                    "DSRotLockSettingsMngr",
                    "Attempting to unregister a listener hadn't been registered");
        }
        this.mPrivacyManager.removeSensorPrivacyListener(2, this.mPrivacyChangedListener);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        this.mMetricsFeatureProvider.action(this.mContext, 1751, z);
        Settings.Secure.putInt(this.mContext.getContentResolver(), "camera_autorotate", z ? 1 : 0);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference != null) {
            preference.setEnabled(getAvailabilityStatus() == 0);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
