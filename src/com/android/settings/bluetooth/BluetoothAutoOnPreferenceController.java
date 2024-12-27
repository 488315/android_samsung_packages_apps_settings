package com.android.settings.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothAutoOnPreferenceController extends TogglePreferenceController
        implements BluetoothCallback, LifecycleObserver, OnStart, OnStop {
    static final String PREF_KEY = "bluetooth_auto_on_settings_toggle";
    private static final String TAG = "BluetoothAutoOnPrefCtlr";
    private boolean mAutoOnValue;
    BluetoothAdapter mBluetoothAdapter;
    private final LocalBluetoothManager mLocalBluetoothManager;
    private TwoStatePreference mPreference;

    public BluetoothAutoOnPreferenceController(Context context, String str) {
        super(context, str);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mLocalBluetoothManager =
                LocalBluetoothManager.getInstance(this.mContext, Utils.mOnInitCallback);
        this.mAutoOnValue = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAutoOnStateChanged$0() {
        TwoStatePreference twoStatePreference = this.mPreference;
        if (twoStatePreference != null) {
            updateState(twoStatePreference);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAutoOnStateChanged$1(int i) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "onAutoOnStateChanged() state: ", TAG);
        updateValue();
        this.mContext
                .getMainExecutor()
                .execute(
                        new BluetoothAutoOnPreferenceController$$ExternalSyntheticLambda2(this, 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setChecked$2(boolean z) {
        try {
            this.mBluetoothAdapter.setAutoOnEnabled(z);
        } catch (Exception e) {
            Log.e(TAG, "Error calling setAutoOnEnabled()", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateValue() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            return;
        }
        try {
            this.mAutoOnValue = bluetoothAdapter.isAutoOnEnabled();
        } catch (Exception e) {
            Log.e(TAG, "Error calling isAutoOnEnabled()", e);
        }
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
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            return 3;
        }
        try {
            boolean isAutoOnSupported = bluetoothAdapter.isAutoOnSupported();
            Log.i(TAG, "getAvailabilityStatus() isSupported: " + isAutoOnSupported);
            if (isAutoOnSupported) {
                ThreadUtils.postOnBackgroundThread(
                        new BluetoothAutoOnPreferenceController$$ExternalSyntheticLambda2(this, 0));
            }
            return isAutoOnSupported ? 0 : 3;
        } catch (Exception | NoSuchMethodError unused) {
            return 3;
        }
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
        return PREF_KEY;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
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
        return this.mAutoOnValue;
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

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onAutoOnStateChanged(final int i) {
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.bluetooth.BluetoothAutoOnPreferenceController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BluetoothAutoOnPreferenceController.this.lambda$onAutoOnStateChanged$1(i);
                    }
                });
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager == null) {
            return;
        }
        localBluetoothManager.mEventManager.registerCallback(this);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager == null) {
            return;
        }
        localBluetoothManager.mEventManager.unregisterCallback(this);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(final boolean z) {
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.bluetooth.BluetoothAutoOnPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BluetoothAutoOnPreferenceController.this.lambda$setChecked$2(z);
                    }
                });
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAudioModeChanged() {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onBluetoothStateChanged(int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceAdded(
            CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceDeleted(
            CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onScanningStateChanged(boolean z) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAclConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onActiveDeviceChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceBondStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onProfileConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {}
}
