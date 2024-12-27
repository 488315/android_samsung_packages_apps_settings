package com.android.settings.connecteddevice;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.Looper;
import android.util.FeatureFlagUtils;
import android.view.InputDevice;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.bluetooth.BluetoothDeviceUpdater;
import com.android.settings.bluetooth.ConnectedBluetoothDeviceUpdater;
import com.android.settings.bluetooth.Utils;
import com.android.settings.connecteddevice.dock.DockUpdater;
import com.android.settings.connecteddevice.dock.DockUpdaterFeatureProviderImpl;
import com.android.settings.connecteddevice.stylus.StylusDeviceUpdater;
import com.android.settings.connecteddevice.usb.ConnectedUsbDeviceUpdater;
import com.android.settings.connecteddevice.usb.UsbBackend;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.search.SearchIndexableRaw;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConnectedDeviceGroupController extends BasePreferenceController
        implements PreferenceControllerMixin,
                LifecycleObserver,
                OnStart,
                OnStop,
                DevicePreferenceCallback {
    private static final String KEY = "connected_device_list";
    private static final String TAG = "ConnectedDeviceGroupController";
    private BluetoothDeviceUpdater mBluetoothDeviceUpdater;
    private DockUpdater mConnectedDockUpdater;
    private ConnectedUsbDeviceUpdater mConnectedUsbDeviceUpdater;
    private final InputManager mInputManager;
    private final LocalBluetoothManager mLocalBluetoothManager;
    private final PackageManager mPackageManager;
    PreferenceGroup mPreferenceGroup;
    private StylusDeviceUpdater mStylusDeviceUpdater;

    public ConnectedDeviceGroupController(Context context) {
        super(context, KEY);
        this.mPackageManager = context.getPackageManager();
        this.mInputManager = (InputManager) context.getSystemService(InputManager.class);
        this.mLocalBluetoothManager = Utils.getLocalBluetoothManager(context);
    }

    private boolean hasBluetoothFeature() {
        return this.mPackageManager.hasSystemFeature("android.hardware.bluetooth");
    }

    private boolean hasUsbFeature() {
        return this.mPackageManager.hasSystemFeature("android.hardware.usb.accessory")
                || this.mPackageManager.hasSystemFeature("android.hardware.usb.host");
    }

    private boolean hasUsiStylusFeature() {
        if (!FeatureFlagUtils.isEnabled(this.mContext, "settings_show_stylus_preferences")) {
            return false;
        }
        for (int i : this.mInputManager.getInputDeviceIds()) {
            InputDevice inputDevice = this.mInputManager.getInputDevice(i);
            if (inputDevice != null
                    && inputDevice.supportsSource(16386)
                    && !inputDevice.isExternal()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        PreferenceGroup preferenceGroup = (PreferenceGroup) preferenceScreen.findPreference(KEY);
        this.mPreferenceGroup = preferenceGroup;
        preferenceGroup.setVisible(false);
        if (isAvailable()) {
            Context context = preferenceScreen.getContext();
            BluetoothDeviceUpdater bluetoothDeviceUpdater = this.mBluetoothDeviceUpdater;
            if (bluetoothDeviceUpdater != null) {
                bluetoothDeviceUpdater.mPrefContext = context;
                bluetoothDeviceUpdater.forceUpdate();
            }
            ConnectedUsbDeviceUpdater connectedUsbDeviceUpdater = this.mConnectedUsbDeviceUpdater;
            if (connectedUsbDeviceUpdater != null) {
                connectedUsbDeviceUpdater.initUsbPreference(context);
            }
            DockUpdater dockUpdater = this.mConnectedDockUpdater;
            if (dockUpdater != null) {
                dockUpdater.getClass();
                this.mConnectedDockUpdater.getClass();
            }
            StylusDeviceUpdater stylusDeviceUpdater = this.mStylusDeviceUpdater;
            if (stylusDeviceUpdater != null) {
                stylusDeviceUpdater.mContext = context;
                stylusDeviceUpdater.forceUpdate();
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (hasBluetoothFeature()
                        || hasUsbFeature()
                        || hasUsiStylusFeature()
                        || this.mConnectedDockUpdater != null)
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

    public void init(
            BluetoothDeviceUpdater bluetoothDeviceUpdater,
            ConnectedUsbDeviceUpdater connectedUsbDeviceUpdater,
            DockUpdater dockUpdater,
            StylusDeviceUpdater stylusDeviceUpdater) {
        this.mBluetoothDeviceUpdater = bluetoothDeviceUpdater;
        this.mConnectedUsbDeviceUpdater = connectedUsbDeviceUpdater;
        this.mConnectedDockUpdater = dockUpdater;
        this.mStylusDeviceUpdater = stylusDeviceUpdater;
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
        if (this.mPreferenceGroup.getPreferenceCount() == 0) {
            this.mPreferenceGroup.setVisible(true);
        }
        this.mPreferenceGroup.addPreference(preference);
    }

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public void onDeviceRemoved(Preference preference) {
        this.mPreferenceGroup.removePreference(preference);
        if (this.mPreferenceGroup.getPreferenceCount() == 0) {
            this.mPreferenceGroup.setVisible(false);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        BluetoothDeviceUpdater bluetoothDeviceUpdater = this.mBluetoothDeviceUpdater;
        if (bluetoothDeviceUpdater != null) {
            bluetoothDeviceUpdater.registerCallback();
            this.mBluetoothDeviceUpdater.getClass();
        }
        ConnectedUsbDeviceUpdater connectedUsbDeviceUpdater = this.mConnectedUsbDeviceUpdater;
        if (connectedUsbDeviceUpdater != null) {
            connectedUsbDeviceUpdater.registerCallback();
        }
        StylusDeviceUpdater stylusDeviceUpdater = this.mStylusDeviceUpdater;
        if (stylusDeviceUpdater != null) {
            for (int i : stylusDeviceUpdater.mInputManager.getInputDeviceIds()) {
                stylusDeviceUpdater.onInputDeviceAdded(i);
            }
            stylusDeviceUpdater.mInputManager.registerInputDeviceListener(
                    stylusDeviceUpdater, new Handler(Looper.myLooper()));
            stylusDeviceUpdater.forceUpdate();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        BluetoothDeviceUpdater bluetoothDeviceUpdater = this.mBluetoothDeviceUpdater;
        if (bluetoothDeviceUpdater != null) {
            bluetoothDeviceUpdater.unregisterCallback();
        }
        ConnectedUsbDeviceUpdater connectedUsbDeviceUpdater = this.mConnectedUsbDeviceUpdater;
        if (connectedUsbDeviceUpdater != null) {
            connectedUsbDeviceUpdater.unregisterCallback();
        }
        DockUpdater dockUpdater = this.mConnectedDockUpdater;
        if (dockUpdater != null) {
            dockUpdater.getClass();
        }
        StylusDeviceUpdater stylusDeviceUpdater = this.mStylusDeviceUpdater;
        if (stylusDeviceUpdater != null) {
            Iterator it =
                    ((ArrayList) stylusDeviceUpdater.mRegisteredBatteryCallbackIds).iterator();
            while (it.hasNext()) {
                stylusDeviceUpdater.mInputManager.removeInputDeviceBatteryListener(
                        ((Integer) it.next()).intValue(), stylusDeviceUpdater);
            }
            stylusDeviceUpdater.mInputManager.unregisterInputDeviceListener(stylusDeviceUpdater);
        }
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

    public void init(DashboardFragment dashboardFragment) {
        Context context = dashboardFragment.getContext();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl != null) {
            ((DockUpdaterFeatureProviderImpl)
                            featureFactoryImpl.dockUpdaterFeatureProvider$delegate.getValue())
                    .getClass();
            init(
                    hasBluetoothFeature()
                            ? new ConnectedBluetoothDeviceUpdater(
                                    context, this, dashboardFragment.getMetricsCategory())
                            : null,
                    hasUsbFeature()
                            ? new ConnectedUsbDeviceUpdater(
                                    context, dashboardFragment, this, new UsbBackend(context))
                            : null,
                    new DockUpdaterFeatureProviderImpl.AnonymousClass1(),
                    hasUsiStylusFeature()
                            ? new StylusDeviceUpdater(context, dashboardFragment, this)
                            : null);
            return;
        }
        throw new UnsupportedOperationException("No feature factory configured");
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public /* bridge */ /* synthetic */ void onDeviceClick(Preference preference) {}

    @Override // com.android.settings.core.BasePreferenceController
    public void updateDynamicRawDataToIndex(List<SearchIndexableRaw> list) {}
}
