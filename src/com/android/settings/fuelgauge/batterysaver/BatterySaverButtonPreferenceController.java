package com.android.settings.fuelgauge.batterysaver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;

import androidx.picker.features.search.InitialSearchUtils$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.fuelgauge.BatterySaverReceiver;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.fuelgauge.BatterySaverUtils;
import com.android.settingslib.widget.MainSwitchPreference;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.IMSParameter;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatterySaverButtonPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop, BatterySaverReceiver.BatterySaverListener {
    private static final long SWITCH_ANIMATION_DURATION = 350;
    private final BatterySaverReceiver mBatterySaverReceiver;
    private Handler mHandler;
    private final PowerManager mPowerManager;
    private MainSwitchPreference mPreference;

    public BatterySaverButtonPreferenceController(Context context, String str) {
        super(context, str);
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        BatterySaverReceiver batterySaverReceiver = new BatterySaverReceiver(context);
        this.mBatterySaverReceiver = batterySaverReceiver;
        batterySaverReceiver.mBatterySaverListener = this;
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onPowerSaveModeChangedInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$onPowerSaveModeChanged$0() {
        boolean threadEnabled = getThreadEnabled();
        MainSwitchPreference mainSwitchPreference = this.mPreference;
        if (mainSwitchPreference == null || mainSwitchPreference.mChecked == threadEnabled) {
            return;
        }
        mainSwitchPreference.setChecked(threadEnabled);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        MainSwitchPreference mainSwitchPreference =
                (MainSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreference = mainSwitchPreference;
        mainSwitchPreference.updateStatus(getThreadEnabled());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
        return R.string.menu_key_battery;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public Uri getSliceUri() {
        return InitialSearchUtils$$ExternalSyntheticOutline0.m(
                "content", "android.settings.slices", IMSParameter.CALL.ACTION, "battery_saver");
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
        return this.mPowerManager.isPowerSaveMode();
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
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.fuelgauge.BatterySaverReceiver.BatterySaverListener
    public void onBatteryChanged(boolean z) {
        MainSwitchPreference mainSwitchPreference = this.mPreference;
        if (mainSwitchPreference != null) {
            mainSwitchPreference.setEnabled(!z);
        }
    }

    @Override // com.android.settings.fuelgauge.BatterySaverReceiver.BatterySaverListener
    public void onPowerSaveModeChanged() {
        this.mHandler.postDelayed(
                new Runnable() { // from class:
                                 // com.android.settings.fuelgauge.batterysaver.BatterySaverButtonPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BatterySaverButtonPreferenceController.this
                                .lambda$onPowerSaveModeChanged$0();
                    }
                },
                SWITCH_ANIMATION_DURATION);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mBatterySaverReceiver.setListening(true);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mBatterySaverReceiver.setListening(false);
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        return BatterySaverUtils.setPowerSaveMode(3, this.mContext, z, false);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
