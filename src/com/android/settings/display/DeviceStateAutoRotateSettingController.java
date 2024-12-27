package com.android.settings.display;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.SearchIndexableData;
import android.util.Log;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.HashSet;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DeviceStateAutoRotateSettingController extends TogglePreferenceController
        implements LifecycleObserver {
    private final DeviceStateRotationLockSettingsManager mAutoRotateSettingsManager;
    private final int mDeviceState;
    private final String mDeviceStateDescription;
    private final DeviceStateRotationLockSettingsManager.DeviceStateRotationLockSettingsListener
            mDeviceStateRotationLockSettingsListener;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private final int mOrder;
    private TwoStatePreference mPreference;

    public DeviceStateAutoRotateSettingController(
            Context context,
            int i,
            String str,
            int i2,
            MetricsFeatureProvider metricsFeatureProvider) {
        super(context, getPreferenceKeyForDeviceState(i));
        this.mDeviceStateRotationLockSettingsListener =
                new DeviceStateRotationLockSettingsManager
                        .DeviceStateRotationLockSettingsListener() { // from class:
                                                                     // com.android.settings.display.DeviceStateAutoRotateSettingController$$ExternalSyntheticLambda0
                    @Override // com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager.DeviceStateRotationLockSettingsListener
                    public final void onSettingsChanged() {
                        DeviceStateAutoRotateSettingController.this.lambda$new$0();
                    }
                };
        this.mMetricsFeatureProvider = metricsFeatureProvider;
        this.mDeviceState = i;
        this.mDeviceStateDescription = str;
        this.mAutoRotateSettingsManager =
                DeviceStateRotationLockSettingsManager.getInstance(context);
        this.mOrder = i2;
    }

    private static String getPreferenceKeyForDeviceState(int i) {
        return SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "auto_rotate_device_state_");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        updateState(this.mPreference);
    }

    private void logSettingChanged(boolean z) {
        this.mMetricsFeatureProvider.action(this.mContext, 203, !z);
        this.mMetricsFeatureProvider.action(this.mContext, z ? 1790 : 1791, this.mDeviceState);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        SwitchPreferenceCompat switchPreferenceCompat = new SwitchPreferenceCompat(this.mContext);
        this.mPreference = switchPreferenceCompat;
        switchPreferenceCompat.setTitle(this.mDeviceStateDescription);
        this.mPreference.setKey(getPreferenceKey());
        this.mPreference.setOrder(this.mOrder);
        preferenceScreen.addPreference(this.mPreference);
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return DeviceStateAutoRotationHelper.isDeviceStateRotationEnabled(this.mContext) ? 0 : 3;
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
        return getPreferenceKeyForDeviceState(this.mDeviceState);
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

    public void init(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager =
                this.mAutoRotateSettingsManager;
        int deviceStateToPosture =
                deviceStateRotationLockSettingsManager.mPosturesHelper.deviceStateToPosture(
                        this.mDeviceState);
        int i =
                deviceStateRotationLockSettingsManager.mPostureRotationLockSettings.get(
                        deviceStateToPosture, 0);
        if (i == 0) {
            int indexOfKey =
                    deviceStateRotationLockSettingsManager.mPostureRotationLockFallbackSettings
                            .indexOfKey(deviceStateToPosture);
            if (indexOfKey < 0) {
                Log.w(
                        "DSRotLockSettingsMngr",
                        "Setting is ignored, but no fallback was specified.");
                i = 0;
            } else {
                i =
                        deviceStateRotationLockSettingsManager.mPostureRotationLockSettings.get(
                                deviceStateRotationLockSettingsManager
                                        .mPostureRotationLockFallbackSettings.valueAt(indexOfKey),
                                0);
            }
        }
        return true ^ (i == 1);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
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
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager =
                this.mAutoRotateSettingsManager;
        ((HashSet) deviceStateRotationLockSettingsManager.mListeners)
                .add(this.mDeviceStateRotationLockSettingsListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager =
                this.mAutoRotateSettingsManager;
        if (((HashSet) deviceStateRotationLockSettingsManager.mListeners)
                .remove(this.mDeviceStateRotationLockSettingsListener)) {
            return;
        }
        Log.w(
                "DSRotLockSettingsMngr",
                "Attempting to unregister a listener hadn't been registered");
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        boolean z2 = !z;
        logSettingChanged(z);
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager =
                this.mAutoRotateSettingsManager;
        int deviceStateToPosture =
                deviceStateRotationLockSettingsManager.mPosturesHelper.deviceStateToPosture(
                        this.mDeviceState);
        if (deviceStateRotationLockSettingsManager.mPostureRotationLockFallbackSettings.indexOfKey(
                        deviceStateToPosture)
                >= 0) {
            deviceStateToPosture =
                    deviceStateRotationLockSettingsManager.mPostureRotationLockFallbackSettings.get(
                            deviceStateToPosture);
        }
        deviceStateRotationLockSettingsManager.mPostureRotationLockSettings.put(
                deviceStateToPosture, z2 ? 1 : 2);
        deviceStateRotationLockSettingsManager.persistSettings();
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public void updateRawDataToIndex(List<SearchIndexableRaw> list) {
        SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(this.mContext);
        ((SearchIndexableData) searchIndexableRaw).key = getPreferenceKey();
        searchIndexableRaw.title = this.mDeviceStateDescription;
        searchIndexableRaw.screenTitle = this.mContext.getString(R.string.accelerometer_title);
        list.add(searchIndexableRaw);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DeviceStateAutoRotateSettingController(
            android.content.Context r8, int r9, java.lang.String r10, int r11) {
        /*
            r7 = this;
            com.android.settings.overlay.FeatureFactoryImpl r0 = com.android.settings.overlay.FeatureFactoryImpl._factory
            if (r0 == 0) goto L11
            com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider r6 = r0.getMetricsFeatureProvider()
            r1 = r7
            r2 = r8
            r3 = r9
            r4 = r10
            r5 = r11
            r1.<init>(r2, r3, r4, r5, r6)
            return
        L11:
            java.lang.UnsupportedOperationException r7 = new java.lang.UnsupportedOperationException
            java.lang.String r8 = "No feature factory configured"
            r7.<init>(r8)
            throw r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.display.DeviceStateAutoRotateSettingController.<init>(android.content.Context,"
                    + " int, java.lang.String, int):void");
    }
}
