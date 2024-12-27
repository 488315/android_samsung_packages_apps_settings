package com.android.settings.display;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorPrivacyManager;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;

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

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SmartAutoRotatePreferenceController extends TogglePreferenceController
        implements LifecycleObserver {
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private final PowerManager mPowerManager;
    private Preference mPreference;
    private final SensorPrivacyManager.OnSensorPrivacyChangedListener mPrivacyChangedListener;
    private final SensorPrivacyManager mPrivacyManager;
    private final BroadcastReceiver mReceiver;
    private RotationPolicy.RotationPolicyListener mRotationPolicyListener;

    public SmartAutoRotatePreferenceController(Context context, String str) {
        super(context, str);
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.display.SmartAutoRotatePreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        SmartAutoRotatePreferenceController smartAutoRotatePreferenceController =
                                SmartAutoRotatePreferenceController.this;
                        smartAutoRotatePreferenceController.refreshSummary(
                                smartAutoRotatePreferenceController.mPreference);
                    }
                };
        this.mPrivacyChangedListener =
                new SensorPrivacyManager
                        .OnSensorPrivacyChangedListener() { // from class:
                                                            // com.android.settings.display.SmartAutoRotatePreferenceController.2
                    public final void onSensorPrivacyChanged(int i, boolean z) {
                        SmartAutoRotatePreferenceController smartAutoRotatePreferenceController =
                                SmartAutoRotatePreferenceController.this;
                        smartAutoRotatePreferenceController.refreshSummary(
                                smartAutoRotatePreferenceController.mPreference);
                    }
                };
        this.mPrivacyManager = SensorPrivacyManager.getInstance(context);
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
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
        return (!RotationPolicy.isRotationLockToggleVisible(this.mContext)
                        || DeviceStateAutoRotationHelper.isDeviceStateRotationEnabled(
                                this.mContext))
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
        return R.string.menu_key_display;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mContext.getString(
                !RotationPolicy.isRotationLocked(this.mContext)
                        ? (Settings.Secure.getIntForUser(
                                                        this.mContext.getContentResolver(),
                                                        "camera_autorotate",
                                                        0,
                                                        -2)
                                                == 1
                                        && SmartAutoRotateController
                                                .isRotationResolverServiceAvailable(this.mContext)
                                        && SmartAutoRotateController.hasSufficientPermission(
                                                this.mContext)
                                        && !isCameraLocked()
                                        && !isPowerSaveMode())
                                ? R.string.auto_rotate_option_face_based
                                : R.string.auto_rotate_option_on
                        : R.string.auto_rotate_option_off);
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
        return !RotationPolicy.isRotationLocked(this.mContext);
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isPublicSlice() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return TextUtils.equals(getPreferenceKey(), "auto_rotate");
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
                                                        // com.android.settings.display.SmartAutoRotatePreferenceController.3
                        public final void onChange() {
                            if (SmartAutoRotatePreferenceController.this.mPreference != null) {
                                SmartAutoRotatePreferenceController
                                        smartAutoRotatePreferenceController =
                                                SmartAutoRotatePreferenceController.this;
                                smartAutoRotatePreferenceController.updateState(
                                        smartAutoRotatePreferenceController.mPreference);
                            }
                        }
                    };
        }
        RotationPolicy.registerRotationPolicyListener(this.mContext, this.mRotationPolicyListener);
        this.mPrivacyManager.addSensorPrivacyListener(2, this.mPrivacyChangedListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        this.mContext.unregisterReceiver(this.mReceiver);
        RotationPolicy.RotationPolicyListener rotationPolicyListener = this.mRotationPolicyListener;
        if (rotationPolicyListener != null) {
            RotationPolicy.unregisterRotationPolicyListener(this.mContext, rotationPolicyListener);
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
        boolean z2 = !z;
        this.mMetricsFeatureProvider.action(this.mContext, 203, z2);
        RotationPolicy.setRotationLock(
                this.mContext, z2, "SmartAutoRotatePreferenceController#setChecked");
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        refreshSummary(this.mPreference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
