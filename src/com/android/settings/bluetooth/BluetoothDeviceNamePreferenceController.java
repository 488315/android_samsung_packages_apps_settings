package com.android.settings.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.BidiFormatter;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothDeviceNamePreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final String TAG = "BluetoothNamePrefCtrl";
    protected BluetoothAdapter mBluetoothAdapter;
    Preference mPreference;
    final BroadcastReceiver mReceiver;

    public BluetoothDeviceNamePreferenceController(Context context, String str) {
        super(context, str);
        this.mReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.bluetooth.BluetoothDeviceNamePreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        BluetoothAdapter bluetoothAdapter;
                        String action = intent.getAction();
                        if (!TextUtils.equals(
                                action, "android.bluetooth.adapter.action.LOCAL_NAME_CHANGED")) {
                            if (TextUtils.equals(
                                    action, "android.bluetooth.adapter.action.STATE_CHANGED")) {
                                BluetoothDeviceNamePreferenceController
                                        bluetoothDeviceNamePreferenceController =
                                                BluetoothDeviceNamePreferenceController.this;
                                bluetoothDeviceNamePreferenceController.updatePreferenceState(
                                        bluetoothDeviceNamePreferenceController.mPreference);
                                return;
                            }
                            return;
                        }
                        BluetoothDeviceNamePreferenceController
                                bluetoothDeviceNamePreferenceController2 =
                                        BluetoothDeviceNamePreferenceController.this;
                        if (bluetoothDeviceNamePreferenceController2.mPreference == null
                                || (bluetoothAdapter =
                                                bluetoothDeviceNamePreferenceController2
                                                        .mBluetoothAdapter)
                                        == null
                                || !bluetoothAdapter.isEnabled()) {
                            return;
                        }
                        BluetoothDeviceNamePreferenceController
                                bluetoothDeviceNamePreferenceController3 =
                                        BluetoothDeviceNamePreferenceController.this;
                        bluetoothDeviceNamePreferenceController3.updatePreferenceState(
                                bluetoothDeviceNamePreferenceController3.mPreference);
                    }
                };
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothAdapter = defaultAdapter;
        if (defaultAdapter == null) {
            Log.e(TAG, "Bluetooth is not supported on this device");
        }
    }

    public Preference createBluetoothDeviceNamePreference(
            PreferenceScreen preferenceScreen, int i) {
        Preference preference = new Preference(preferenceScreen.getContext());
        this.mPreference = preference;
        preference.setOrder(i);
        this.mPreference.setKey(getPreferenceKey());
        preferenceScreen.addPreference(this.mPreference);
        return this.mPreference;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mBluetoothAdapter != null ? 0 : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public String getDeviceName() {
        return this.mBluetoothAdapter.getName();
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        String deviceName = getDeviceName();
        return TextUtils.isEmpty(deviceName)
                ? super.getSummary()
                : TextUtils.expandTemplate(
                                this.mContext.getText(R.string.bluetooth_device_name_summary),
                                BidiFormatter.getInstance().unicodeWrap(deviceName))
                        .toString();
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mContext.registerReceiver(
                this.mReceiver,
                ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                        "android.bluetooth.adapter.action.LOCAL_NAME_CHANGED",
                        "android.bluetooth.adapter.action.STATE_CHANGED"));
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
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

    public void updatePreferenceState(Preference preference) {
        preference.setSelectable(false);
        preference.setSummary(getSummary());
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        updatePreferenceState(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
