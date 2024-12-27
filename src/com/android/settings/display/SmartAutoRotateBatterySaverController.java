package com.android.settings.display;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.view.View;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.widget.BannerMessagePreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SmartAutoRotateBatterySaverController extends BasePreferenceController
        implements LifecycleObserver {
    private final PowerManager mPowerManager;
    private Preference mPreference;
    private final BroadcastReceiver mReceiver;

    public SmartAutoRotateBatterySaverController(Context context, String str) {
        super(context, str);
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.display.SmartAutoRotateBatterySaverController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if (SmartAutoRotateBatterySaverController.this.mPreference == null) {
                            return;
                        }
                        SmartAutoRotateBatterySaverController.this.mPreference.setVisible(
                                SmartAutoRotateBatterySaverController.this.isAvailable());
                        SmartAutoRotateBatterySaverController
                                smartAutoRotateBatterySaverController =
                                        SmartAutoRotateBatterySaverController.this;
                        smartAutoRotateBatterySaverController.updateState(
                                smartAutoRotateBatterySaverController.mPreference);
                    }
                };
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$displayPreference$0(View view) {
        this.mPowerManager.setPowerSaveModeEnabled(false);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = findPreference;
        BannerMessagePreference bannerMessagePreference = (BannerMessagePreference) findPreference;
        bannerMessagePreference.setPositiveButtonText$1(R.string.ambient_camera_battery_saver_off);
        bannerMessagePreference.setPositiveButtonOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.display.SmartAutoRotateBatterySaverController$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        SmartAutoRotateBatterySaverController.this.lambda$displayPreference$0(view);
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (SmartAutoRotateController.isRotationResolverServiceAvailable(this.mContext)
                        && isPowerSaveMode())
                ? 1
                : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public boolean isPowerSaveMode() {
        return this.mPowerManager.isPowerSaveMode();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        this.mContext.registerReceiver(
                this.mReceiver, new IntentFilter("android.os.action.POWER_SAVE_MODE_CHANGED"));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
