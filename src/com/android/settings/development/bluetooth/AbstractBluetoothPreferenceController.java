package com.android.settings.development.bluetooth;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.development.BluetoothServiceConnectionListener;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AbstractBluetoothPreferenceController
        extends DeveloperOptionsPreferenceController
        implements BluetoothServiceConnectionListener,
                LifecycleObserver,
                OnDestroy,
                PreferenceControllerMixin {
    public volatile BluetoothA2dp mBluetoothA2dp;
    BluetoothAdapter mBluetoothAdapter;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Callback {}

    public AbstractBluetoothPreferenceController(Context context, Lifecycle lifecycle) {
        super(context);
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
        this.mBluetoothAdapter =
                ((BluetoothManager) context.getSystemService(BluetoothManager.class)).getAdapter();
    }

    public final BluetoothDevice getA2dpActiveDevice() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null) {
            return null;
        }
        List activeDevices = bluetoothAdapter.getActiveDevices(2);
        if (activeDevices.size() > 0) {
            return (BluetoothDevice) activeDevices.get(0);
        }
        return null;
    }

    @Override // com.android.settings.development.BluetoothServiceConnectionListener
    public final void onBluetoothCodecUpdated() {
        updateState(this.mPreference);
    }

    public void onBluetoothServiceConnected(BluetoothA2dp bluetoothA2dp) {
        this.mBluetoothA2dp = bluetoothA2dp;
        updateState(this.mPreference);
    }

    @Override // com.android.settings.development.BluetoothServiceConnectionListener
    public final void onBluetoothServiceDisconnected() {
        this.mBluetoothA2dp = null;
        updateState(this.mPreference);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public final void onDestroy() {
        this.mBluetoothA2dp = null;
    }
}
