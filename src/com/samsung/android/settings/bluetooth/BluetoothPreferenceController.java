package com.samsung.android.settings.bluetooth;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.bluetooth.BluetoothEnabler;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    private static final String KEY_BLUETOOTH_SETTINGS = "bluetooth_settings";
    private static final String TAG = "BluetoothPreferenceController";
    private BluetoothEnabler mBluetoothEnabler;
    private SecSwitchPreferenceScreen mBluetoothEnablerPreference;
    private Context mContext;
    private LocalBluetoothAdapter mLocalAdapter;
    private LocalBluetoothManager mLocalManager;

    public BluetoothPreferenceController(Context context, String str) {
        super(context, str);
        this.mContext = context;
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        this.mLocalManager = localBluetoothManager;
        if (localBluetoothManager != null) {
            this.mLocalAdapter = localBluetoothManager.mLocalAdapter;
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference(getPreferenceKey());
        this.mBluetoothEnablerPreference = secSwitchPreferenceScreen;
        this.mBluetoothEnabler = new BluetoothEnabler(this.mContext, secSwitchPreferenceScreen);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mLocalAdapter == null ? 5 : 0;
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
        LocalBluetoothAdapter localBluetoothAdapter = this.mLocalAdapter;
        if (localBluetoothAdapter != null) {
            return localBluetoothAdapter.mAdapter.isEnabled();
        }
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        BluetoothEnabler bluetoothEnabler = this.mBluetoothEnabler;
        if (bluetoothEnabler != null) {
            bluetoothEnabler.pause();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        BluetoothEnabler bluetoothEnabler = this.mBluetoothEnabler;
        if (bluetoothEnabler != null) {
            bluetoothEnabler.resume(this.mContext);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        LocalBluetoothAdapter localBluetoothAdapter = this.mLocalAdapter;
        if (localBluetoothAdapter != null) {
            return localBluetoothAdapter.setBluetoothEnabled(z);
        }
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
