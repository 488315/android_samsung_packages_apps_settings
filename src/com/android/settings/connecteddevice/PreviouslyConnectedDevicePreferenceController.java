package com.android.settings.connecteddevice;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.bluetooth.BluetoothDevicePreference;
import com.android.settings.bluetooth.BluetoothDeviceUpdater;
import com.android.settings.bluetooth.SavedBluetoothDeviceUpdater;
import com.android.settings.connecteddevice.dock.DockUpdater;
import com.android.settings.connecteddevice.dock.DockUpdaterFeatureProviderImpl;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PreviouslyConnectedDevicePreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop, DevicePreferenceCallback {
    private static final int DOCK_DEVICE_INDEX = 9;
    private static final String KEY_SEE_ALL = "previously_connected_devices_see_all";
    private static final int MAX_DEVICE_NUM = 3;
    private final BluetoothAdapter mBluetoothAdapter;
    private BluetoothDeviceUpdater mBluetoothDeviceUpdater;
    private final Map<BluetoothDevice, Preference> mDevicePreferenceMap;
    private final List<Preference> mDevicesList;
    private final List<Preference> mDockDevicesList;
    IntentFilter mIntentFilter;
    private PreferenceGroup mPreferenceGroup;
    BroadcastReceiver mReceiver;
    private DockUpdater mSavedDockUpdater;
    Preference mSeeAllPreference;
    private static final String TAG = "PreviouslyDevicePreController";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);

    public PreviouslyConnectedDevicePreferenceController(Context context, String str) {
        super(context, str);
        this.mDevicesList = new ArrayList();
        this.mDockDevicesList = new ArrayList();
        this.mDevicePreferenceMap = new HashMap();
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.connecteddevice.PreviouslyConnectedDevicePreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        PreviouslyConnectedDevicePreferenceController.this
                                .updatePreferenceVisibility();
                    }
                };
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        ((DockUpdaterFeatureProviderImpl)
                        featureFactoryImpl.dockUpdaterFeatureProvider$delegate.getValue())
                .getClass();
        this.mSavedDockUpdater = new DockUpdaterFeatureProviderImpl.AnonymousClass1();
        this.mIntentFilter = new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED");
        this.mBluetoothAdapter =
                ((BluetoothManager) context.getSystemService(BluetoothManager.class)).getAdapter();
    }

    private void addPreference(int i, Preference preference) {
        if (!(preference instanceof BluetoothDevicePreference)) {
            this.mDockDevicesList.add(preference);
        } else if (i < 0 || this.mDevicesList.size() < i) {
            this.mDevicesList.add(preference);
        } else {
            this.mDevicesList.add(i, preference);
        }
        addPreference();
    }

    private int getDeviceListSize() {
        if (this.mDevicesList.size() >= 3) {
            return 3;
        }
        return this.mDevicesList.size();
    }

    private int getDockDeviceListSize(int i) {
        return this.mDockDevicesList.size() >= i ? i : this.mDockDevicesList.size();
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
            this.mBluetoothDeviceUpdater.mPrefContext = preferenceScreen.getContext();
            this.mSavedDockUpdater.getClass();
            this.mBluetoothDeviceUpdater.forceUpdate();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth")
                        || this.mSavedDockUpdater != null)
                ? 0
                : 2;
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

    public void init(DashboardFragment dashboardFragment) {
        this.mBluetoothDeviceUpdater =
                new SavedBluetoothDeviceUpdater(
                        dashboardFragment.getContext(),
                        this,
                        false,
                        dashboardFragment.getMetricsCategory());
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

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public void onDeviceAdded(Preference preference) {
        if (preference instanceof BluetoothDevicePreference) {
            this.mDevicePreferenceMap.put(
                    ((BluetoothDevicePreference) preference).mCachedDevice.mDevice, preference);
        } else {
            this.mDockDevicesList.add(preference);
        }
        if (DEBUG) {
            Log.d(TAG, "onDeviceAdded() " + ((Object) preference.getTitle()));
        }
        updatePreferenceGroup();
    }

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public void onDeviceRemoved(Preference preference) {
        if (preference instanceof BluetoothDevicePreference) {
            this.mDevicePreferenceMap.remove(
                    ((BluetoothDevicePreference) preference).mCachedDevice.mDevice, preference);
        } else {
            this.mDockDevicesList.remove(preference);
        }
        if (DEBUG) {
            Log.d(TAG, "onDeviceRemoved() " + ((Object) preference.getTitle()));
        }
        updatePreferenceGroup();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mBluetoothDeviceUpdater.registerCallback();
        this.mSavedDockUpdater.getClass();
        this.mContext.registerReceiver(this.mReceiver, this.mIntentFilter, 2);
        this.mBluetoothDeviceUpdater.getClass();
        updatePreferenceGroup();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mBluetoothDeviceUpdater.unregisterCallback();
        this.mSavedDockUpdater.getClass();
        this.mContext.unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setBluetoothDeviceUpdater(BluetoothDeviceUpdater bluetoothDeviceUpdater) {
        this.mBluetoothDeviceUpdater = bluetoothDeviceUpdater;
    }

    public void setPreferenceGroup(PreferenceGroup preferenceGroup) {
        this.mPreferenceGroup = preferenceGroup;
    }

    public void setSavedDockUpdater(DockUpdater dockUpdater) {
        this.mSavedDockUpdater = dockUpdater;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updatePreferenceGroup() {
        this.mPreferenceGroup.removeAll();
        this.mPreferenceGroup.addPreference(this.mSeeAllPreference);
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            Iterator it = this.mBluetoothAdapter.getMostRecentlyConnectedDevices().iterator();
            int i = 0;
            while (it.hasNext()) {
                Preference orDefault =
                        this.mDevicePreferenceMap.getOrDefault((BluetoothDevice) it.next(), null);
                if (orDefault != null) {
                    orDefault.setOrder(i);
                    this.mPreferenceGroup.addPreference(orDefault);
                    i++;
                }
                if (i == 3) {
                    break;
                }
            }
            for (Preference preference : this.mDockDevicesList) {
                if (i == 3) {
                    break;
                }
                preference.setOrder(i);
                this.mPreferenceGroup.addPreference(preference);
                i++;
            }
        }
        updatePreferenceVisibility();
    }

    public void updatePreferenceVisibility() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            this.mSeeAllPreference.setSummary(
                    this.mContext.getString(R.string.connected_device_see_all_summary));
        } else {
            this.mSeeAllPreference.setSummary(ApnSettings.MVNO_NONE);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    private void addPreference() {
        this.mPreferenceGroup.removeAll();
        this.mPreferenceGroup.addPreference(this.mSeeAllPreference);
        int deviceListSize = getDeviceListSize();
        for (int i = 0; i < deviceListSize; i++) {
            if (DEBUG) {
                Log.d(
                        TAG,
                        "addPreference() add device : "
                                + ((Object) this.mDevicesList.get(i).getTitle()));
            }
            this.mDevicesList.get(i).setOrder(i);
            this.mPreferenceGroup.addPreference(this.mDevicesList.get(i));
        }
        if (this.mDockDevicesList.size() > 0) {
            for (int i2 = 0; i2 < getDockDeviceListSize(3 - deviceListSize); i2++) {
                if (DEBUG) {
                    Log.d(
                            TAG,
                            "addPreference() add dock device : "
                                    + ((Object) this.mDockDevicesList.get(i2).getTitle()));
                }
                this.mDockDevicesList.get(i2).setOrder(9);
                this.mPreferenceGroup.addPreference(this.mDockDevicesList.get(i2));
            }
        }
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public /* bridge */ /* synthetic */ void onDeviceClick(Preference preference) {}
}
