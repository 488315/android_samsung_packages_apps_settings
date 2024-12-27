package com.samsung.android.settings.bluetooth.controller;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecBluetoothDetailsHeaderController extends BasePreferenceController
        implements LifecycleObserver, OnPause, OnDestroy, OnResume, CachedBluetoothDevice.Callback {
    private static final String TAG = "SecBluetoothDetailsHeaderController";
    private CachedBluetoothDevice mCachedDevice;
    boolean mIsRegisterCallback;
    LayoutPreference mLayoutPreference;

    public SecBluetoothDetailsHeaderController(Context context, String str) {
        super(context, str);
        this.mIsRegisterCallback = false;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mLayoutPreference = layoutPreference;
        layoutPreference.setVisible(isAvailable());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        BluetoothDevice bluetoothDevice;
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        return (cachedBluetoothDevice == null
                        || (bluetoothDevice = cachedBluetoothDevice.mDevice) == null
                        || BluetoothUtils.isSupportSmep(bluetoothDevice))
                ? 2
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

    public void init(CachedBluetoothDevice cachedBluetoothDevice) {
        this.mCachedDevice = cachedBluetoothDevice;
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

    @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public void onDeviceAttributesChanged() {
        if (this.mCachedDevice != null) {
            refresh();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        if (this.mIsRegisterCallback) {
            this.mCachedDevice.unregisterCallback(this);
            this.mIsRegisterCallback = false;
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        if (!isAvailable()) {
            this.mLayoutPreference.setVisible(false);
        } else {
            this.mLayoutPreference.setVisible(true);
            refresh();
        }
    }

    public void refresh() {
        if (this.mLayoutPreference == null || this.mCachedDevice == null) {
            return;
        }
        if (!isAvailable()) {
            this.mLayoutPreference.setVisible(false);
            return;
        }
        if (!this.mIsRegisterCallback) {
            this.mIsRegisterCallback = true;
            this.mCachedDevice.registerCallback(this);
        }
        this.mLayoutPreference.setVisible(true);
        ((TextView) this.mLayoutPreference.mRootView.findViewById(R.id.bluetooth_header_title))
                .setText(this.mCachedDevice.getName());
        ((TextView) this.mLayoutPreference.mRootView.findViewById(R.id.bluetooth_header_summary))
                .setText(this.mCachedDevice.getConnectionSummary(true));
        ImageView imageView =
                (ImageView)
                        this.mLayoutPreference.mRootView.findViewById(R.id.bluetooth_header_icon);
        imageView.setImageDrawable(this.mCachedDevice.getIconDrawable());
        if (this.mCachedDevice.isConnected()) {
            this.mLayoutPreference
                    .mRootView
                    .findViewById(R.id.bluetooth_header_icon_container)
                    .setBackground(
                            this.mContext.getDrawable(R.drawable.ic_bluetooth_battery_header));
            imageView.setColorFilter(
                    this.mContext.getColor(R.color.sec_bluetooth_header_icon_connected_bg_color));
        } else {
            this.mLayoutPreference
                    .mRootView
                    .findViewById(R.id.bluetooth_header_icon_container)
                    .setBackground(
                            this.mContext.getDrawable(
                                    R.drawable.ic_bluetooth_battery_header_disconnect));
            imageView.setColorFilter(
                    this.mContext.getColor(
                            R.color.sec_bluetooth_header_icon_disconnected_bg_color));
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

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {}
}
