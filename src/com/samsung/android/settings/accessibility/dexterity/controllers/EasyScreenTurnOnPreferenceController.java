package com.samsung.android.settings.accessibility.dexterity.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;

import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.hardware.context.SemContextManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingUtil;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;

import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EasyScreenTurnOnPreferenceController extends TogglePreferenceController
        implements A11yStatusLoggingProvider {
    public EasyScreenTurnOnPreferenceController(Context context, String str) {
        super(context, str);
    }

    private boolean isApproachSupported(Context context) {
        if (context == null) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        SemContextManager semContextManager =
                (SemContextManager) context.getSystemService("scontext");
        Sensor defaultSensor =
                ((SensorManager) context.getSystemService("sensor")).getDefaultSensor(8);
        return (!packageManager.hasSystemFeature("com.sec.feature.sensorhub")
                        || !semContextManager.isAvailableService(1)
                        || defaultSensor == null
                        || defaultSensor.getName().contains("Palm")
                        || defaultSensor.getName().contains("Ear Hover"))
                ? false
                : true;
    }

    private boolean isEasyScreenTurnOnSupported(Context context) {
        if (!Utils.isTablet()) {
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            if (isApproachSupported(context)
                    && SystemProperties.getInt(
                                    TouchPadGestureSettingsController.FIRST_API_LEVEL, 20)
                            < 33) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (isEasyScreenTurnOnSupported(this.mContext)) {
            return SecAccessibilityUtils.isDesktopDualModeMonitorDisplay(this.mContext) ? 5 : 0;
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

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        return Map.of("A11YS5005", A11yStatusLoggingUtil.getSwitchStatus(getThreadEnabled()));
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
        return Settings.System.getInt(this.mContext.getContentResolver(), "air_motion_wake_up", 0)
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        AccessibilityManager accessibilityManager =
                (AccessibilityManager) this.mContext.getSystemService("accessibility");
        if (z) {
            accessibilityManager.semEnableAirGestureWakeUp();
            return true;
        }
        accessibilityManager.semDisableAirGestureWakeUp();
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
