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
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FastPairDevicePreferenceController extends BasePreferenceController
        implements DefaultLifecycleObserver, DevicePreferenceCallback {
    private static final String KEY_SEE_ALL = "fast_pair_devices_see_all";
    private static final int MAX_DEVICE_NUM = 3;
    private BluetoothAdapter mBluetoothAdapter;
    private FastPairDeviceUpdater mFastPairDeviceUpdater;
    IntentFilter mIntentFilter;
    private PreferenceGroup mPreferenceGroup;
    private final List<Preference> mPreferenceList;
    BroadcastReceiver mReceiver;
    Preference mSeeAllPreference;
    private static final String TAG = "FastPairDevicePrefCtr";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);

    public FastPairDevicePreferenceController(Context context, String str) {
        super(context, str);
        this.mPreferenceList = new ArrayList();
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.connecteddevice.fastpair.FastPairDevicePreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        FastPairDevicePreferenceController.this.updatePreferenceVisibility();
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
        this.mIntentFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        PreferenceGroup preferenceGroup =
                (PreferenceGroup) preferenceScreen.findPreference(getPreferenceKey());
        this.mPreferenceGroup = preferenceGroup;
        this.mSeeAllPreference = preferenceGroup.findPreference(KEY_SEE_ALL);
        updatePreferenceVisibility();
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
        if (preference == null) {
            if (DEBUG) {
                Log.d(TAG, "onDeviceAdd receives null preference. Ignore.");
                return;
            }
            return;
        }
        int binarySearch = Collections.binarySearch(this.mPreferenceList, preference);
        if (binarySearch >= 0) {
            if (DEBUG) {
                Log.d(TAG, "onDeviceAdd receives duplicate preference. Ignore.");
                return;
            }
            return;
        }
        int i = (binarySearch + 1) * (-1);
        this.mPreferenceList.add(i, preference);
        if (i < 3) {
            if (this.mPreferenceList.size() > 3) {
                this.mPreferenceGroup.removePreference(this.mPreferenceList.get(3));
            }
            this.mPreferenceGroup.addPreference(preference);
        }
        updatePreferenceVisibility();
    }

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public void onDeviceRemoved(Preference preference) {
        if (preference == null) {
            if (DEBUG) {
                Log.d(TAG, "onDeviceRemoved receives null preference. Ignore.");
                return;
            }
            return;
        }
        int indexOf = this.mPreferenceList.indexOf(preference);
        this.mPreferenceList.remove(preference);
        if (indexOf < 3) {
            this.mPreferenceGroup.removePreference(preference);
            if (this.mPreferenceList.size() >= 3) {
                this.mPreferenceGroup.addPreference(this.mPreferenceList.get(2));
            }
        }
        updatePreferenceVisibility();
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

    public void updatePreferenceVisibility() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null
                || !bluetoothAdapter.isEnabled()
                || this.mPreferenceList.size() <= 0) {
            this.mPreferenceGroup.setVisible(false);
            this.mSeeAllPreference.setVisible(false);
        } else {
            this.mPreferenceGroup.setVisible(true);
            this.mSeeAllPreference.setVisible(this.mPreferenceList.size() > 3);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public /* bridge */ /* synthetic */ void onDeviceClick(Preference preference) {}
}
