package com.android.settings.connecteddevice;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.bluetooth.BluetoothDevicePreference;
import com.android.settings.bluetooth.BluetoothDeviceUpdater;
import com.android.settings.bluetooth.SavedBluetoothDeviceUpdater;
import com.android.settings.connecteddevice.dock.DockUpdater;
import com.android.settings.connecteddevice.dock.DockUpdaterFeatureProviderImpl;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

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
public class SavedDeviceGroupController extends BasePreferenceController
        implements PreferenceControllerMixin,
                LifecycleObserver,
                OnStart,
                OnStop,
                DevicePreferenceCallback {
    private static final String KEY = "saved_device_list";
    private final BluetoothAdapter mBluetoothAdapter;
    private BluetoothDeviceUpdater mBluetoothDeviceUpdater;
    private final Map<BluetoothDevice, Preference> mDevicePreferenceMap;
    private final List<Preference> mDockDevicesList;
    PreferenceGroup mPreferenceGroup;
    private DockUpdater mSavedDockUpdater;

    public SavedDeviceGroupController(Context context) {
        super(context, KEY);
        this.mDevicePreferenceMap = new HashMap();
        this.mDockDevicesList = new ArrayList();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        ((DockUpdaterFeatureProviderImpl)
                        featureFactoryImpl.dockUpdaterFeatureProvider$delegate.getValue())
                .getClass();
        this.mSavedDockUpdater = new DockUpdaterFeatureProviderImpl.AnonymousClass1();
        this.mBluetoothAdapter =
                ((BluetoothManager) context.getSystemService(BluetoothManager.class)).getAdapter();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        PreferenceGroup preferenceGroup = (PreferenceGroup) preferenceScreen.findPreference(KEY);
        this.mPreferenceGroup = preferenceGroup;
        preferenceGroup.setVisible(false);
        if (isAvailable()) {
            Context context = preferenceScreen.getContext();
            BluetoothDeviceUpdater bluetoothDeviceUpdater = this.mBluetoothDeviceUpdater;
            bluetoothDeviceUpdater.mPrefContext = context;
            bluetoothDeviceUpdater.forceUpdate();
            this.mSavedDockUpdater.getClass();
            this.mSavedDockUpdater.getClass();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth")
                        || this.mSavedDockUpdater != null)
                ? 0
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

    public void init(DashboardFragment dashboardFragment) {
        this.mBluetoothDeviceUpdater =
                new SavedBluetoothDeviceUpdater(
                        dashboardFragment.getContext(),
                        this,
                        true,
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
        this.mPreferenceGroup.addPreference(preference);
        if (preference instanceof BluetoothDevicePreference) {
            this.mDevicePreferenceMap.put(
                    ((BluetoothDevicePreference) preference).mCachedDevice.mDevice, preference);
        } else {
            this.mDockDevicesList.add(preference);
        }
        updatePreferenceGroup();
    }

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public void onDeviceRemoved(Preference preference) {
        this.mPreferenceGroup.removePreference(preference);
        if (preference instanceof BluetoothDevicePreference) {
            this.mDevicePreferenceMap.remove(
                    ((BluetoothDevicePreference) preference).mCachedDevice.mDevice, preference);
        } else {
            this.mDockDevicesList.remove(preference);
        }
        updatePreferenceGroup();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mBluetoothDeviceUpdater.registerCallback();
        this.mSavedDockUpdater.getClass();
        this.mBluetoothDeviceUpdater.getClass();
        updatePreferenceGroup();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mBluetoothDeviceUpdater.unregisterCallback();
        this.mSavedDockUpdater.getClass();
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
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        int i = 0;
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            this.mPreferenceGroup.setVisible(false);
            return;
        }
        this.mPreferenceGroup.setVisible(true);
        Iterator it = this.mBluetoothAdapter.getMostRecentlyConnectedDevices().iterator();
        while (it.hasNext()) {
            Preference orDefault =
                    this.mDevicePreferenceMap.getOrDefault((BluetoothDevice) it.next(), null);
            if (orDefault != null) {
                orDefault.setOrder(i);
                i++;
            }
        }
        Iterator<Preference> it2 = this.mDockDevicesList.iterator();
        while (it2.hasNext()) {
            it2.next().setOrder(i);
            i++;
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
