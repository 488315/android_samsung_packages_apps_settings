package com.android.settings.connecteddevice.fastpair;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.connecteddevice.DevicePreferenceCallback;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FastPairDeviceGroupController extends BasePreferenceController
        implements PreferenceControllerMixin, DefaultLifecycleObserver, DevicePreferenceCallback {
    private static final String KEY = "fast_pair_device_list";
    private final BluetoothAdapter mBluetoothAdapter;
    private final FastPairDeviceUpdater mFastPairDeviceUpdater;
    IntentFilter mIntentFilter;
    PreferenceGroup mPreferenceGroup;
    BroadcastReceiver mReceiver;
    private static final String TAG = "FastPairDeviceGroupCtr";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);

    public FastPairDeviceGroupController(Context context) {
        super(context, KEY);
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.connecteddevice.fastpair.FastPairDeviceGroupController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        FastPairDeviceGroupController.this.updatePreferenceVisibility();
                    }
                };
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        ((FastPairFeatureProviderImpl)
                        featureFactoryImpl.fastPairFeatureProvider$delegate.getValue())
                .getClass();
        this.mFastPairDeviceUpdater = new FastPairFeatureProviderImpl.AnonymousClass1();
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mIntentFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePreferenceVisibility() {
        PreferenceGroup preferenceGroup = this.mPreferenceGroup;
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        preferenceGroup.setVisible(
                bluetoothAdapter != null
                        && bluetoothAdapter.isEnabled()
                        && this.mPreferenceGroup.getPreferenceCount() > 0);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        PreferenceGroup preferenceGroup = (PreferenceGroup) preferenceScreen.findPreference(KEY);
        this.mPreferenceGroup = preferenceGroup;
        preferenceGroup.setVisible(false);
        if (isAvailable()) {
            this.mFastPairDeviceUpdater.getClass();
            this.mFastPairDeviceUpdater.getClass();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth")
                        || this.mFastPairDeviceUpdater == null)
                ? 3
                : 0;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY;
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

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        super.onCreate(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
        super.onDestroy(lifecycleOwner);
    }

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public void onDeviceAdded(Preference preference) {
        if (preference != null) {
            this.mPreferenceGroup.addPreference(preference);
            updatePreferenceVisibility();
        } else if (DEBUG) {
            Log.d(TAG, "onDeviceAdded receives null preference. Ignore.");
        }
    }

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public void onDeviceRemoved(Preference preference) {
        if (preference != null) {
            this.mPreferenceGroup.removePreference(preference);
            updatePreferenceVisibility();
        } else if (DEBUG) {
            Log.d(TAG, "onDeviceRemoved receives null preference. Ignore.");
        }
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
        FastPairDeviceUpdater fastPairDeviceUpdater = this.mFastPairDeviceUpdater;
        if (fastPairDeviceUpdater != null) {
            fastPairDeviceUpdater.getClass();
            this.mFastPairDeviceUpdater.getClass();
        } else if (DEBUG) {
            Log.d(TAG, "Callback register: Fast Pair device updater is null. Ignore.");
        }
        this.mContext.registerReceiver(this.mReceiver, this.mIntentFilter, 2);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        FastPairDeviceUpdater fastPairDeviceUpdater = this.mFastPairDeviceUpdater;
        if (fastPairDeviceUpdater != null) {
            fastPairDeviceUpdater.getClass();
            this.mFastPairDeviceUpdater.getClass();
        } else if (DEBUG) {
            Log.d(TAG, "Callback unregister: Fast Pair device updater is null. Ignore.");
        }
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setPreferenceGroup(PreferenceGroup preferenceGroup) {
        this.mPreferenceGroup = preferenceGroup;
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

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public /* bridge */ /* synthetic */ void onDeviceClick(Preference preference) {}
}
