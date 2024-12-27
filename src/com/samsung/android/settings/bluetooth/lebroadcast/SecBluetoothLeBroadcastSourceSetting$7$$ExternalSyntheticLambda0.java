package com.samsung.android.settings.bluetooth.lebroadcast;

import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.util.HashSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecBluetoothLeBroadcastSourceSetting$7$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecBluetoothLeBroadcastSourceSetting.AnonymousClass7 f$0;
    public final /* synthetic */ BluetoothDevice f$1;

    public /* synthetic */ SecBluetoothLeBroadcastSourceSetting$7$$ExternalSyntheticLambda0(
            SecBluetoothLeBroadcastSourceSetting.AnonymousClass7 anonymousClass7,
            BluetoothDevice bluetoothDevice,
            int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass7;
        this.f$1 = bluetoothDevice;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean isEmpty;
        switch (this.$r8$classId) {
            case 0:
                SecBluetoothLeBroadcastSourceSetting.AnonymousClass7 anonymousClass7 = this.f$0;
                BluetoothDevice bluetoothDevice = this.f$1;
                SecBluetoothLeBroadcastSourceSetting secBluetoothLeBroadcastSourceSetting =
                        SecBluetoothLeBroadcastSourceSetting.this;
                boolean z = SecBluetoothLeBroadcastSourceSetting.DEBUG;
                secBluetoothLeBroadcastSourceSetting.getClass();
                Log.d(
                        "SecBluetoothLeBroadcastSourceSetting",
                        "handleOnSourceAddFailed device: " + bluetoothDevice);
                synchronized (secBluetoothLeBroadcastSourceSetting.mSyncedDevicesLock) {
                    ((HashSet) secBluetoothLeBroadcastSourceSetting.mLocalSourceAddedDevices)
                            .remove(bluetoothDevice);
                }
                return;
            case 1:
                SecBluetoothLeBroadcastSourceSetting.AnonymousClass7 anonymousClass72 = this.f$0;
                BluetoothDevice bluetoothDevice2 = this.f$1;
                SecBluetoothLeBroadcastSourceSetting secBluetoothLeBroadcastSourceSetting2 =
                        SecBluetoothLeBroadcastSourceSetting.this;
                if (secBluetoothLeBroadcastSourceSetting2.mBroadcastId == -1) {
                    Log.e(
                            "SecBluetoothLeBroadcastSourceSetting",
                            "handleAddSyncDevice : Invalid Broadcast Source");
                    return;
                }
                Log.d(
                        "SecBluetoothLeBroadcastSourceSetting",
                        "handleAddSyncDevice device: " + bluetoothDevice2);
                synchronized (secBluetoothLeBroadcastSourceSetting2.mSyncedDevicesLock) {
                    ((HashSet) secBluetoothLeBroadcastSourceSetting2.mLocalSourceAddedDevices)
                            .add(bluetoothDevice2);
                }
                if (secBluetoothLeBroadcastSourceSetting2.mIsBroadcasting) {
                    secBluetoothLeBroadcastSourceSetting2.mDeviceAdapter.updateDeviceList(
                            secBluetoothLeBroadcastSourceSetting2.mDeviceController
                                    .getConnectedLeAudioDeviceList());
                    return;
                }
                secBluetoothLeBroadcastSourceSetting2.clearOperationTimeout();
                secBluetoothLeBroadcastSourceSetting2.mIsBroadcasting = true;
                Log.d("SecBluetoothLeBroadcastSourceSetting", "handleAddSyncDevice, Update UI");
                secBluetoothLeBroadcastSourceSetting2.mDeviceAdapter.updateDeviceList(
                        secBluetoothLeBroadcastSourceSetting2.mDeviceController
                                .getConnectedLeAudioDeviceList());
                secBluetoothLeBroadcastSourceSetting2.refreshUI();
                return;
            default:
                SecBluetoothLeBroadcastSourceSetting.AnonymousClass7 anonymousClass73 = this.f$0;
                BluetoothDevice bluetoothDevice3 = this.f$1;
                SecBluetoothLeBroadcastSourceSetting secBluetoothLeBroadcastSourceSetting3 =
                        SecBluetoothLeBroadcastSourceSetting.this;
                boolean z2 = SecBluetoothLeBroadcastSourceSetting.DEBUG;
                secBluetoothLeBroadcastSourceSetting3.getClass();
                Log.d(
                        "SecBluetoothLeBroadcastSourceSetting",
                        "handleRemoveSyncDevice device: " + bluetoothDevice3);
                synchronized (secBluetoothLeBroadcastSourceSetting3.mSyncedDevicesLock) {
                    ((HashSet) secBluetoothLeBroadcastSourceSetting3.mLocalSourceAddedDevices)
                            .remove(bluetoothDevice3);
                    isEmpty =
                            ((HashSet)
                                            secBluetoothLeBroadcastSourceSetting3
                                                    .mLocalSourceAddedDevices)
                                    .isEmpty();
                }
                if (!isEmpty
                        || secBluetoothLeBroadcastSourceSetting3.mMenuStopBroadcast.isEnabled()) {
                    return;
                }
                Log.d(
                        "SecBluetoothLeBroadcastSourceSetting",
                        "handleRemoveSyncDevice, mLocalSourceAddedDevices empty ");
                secBluetoothLeBroadcastSourceSetting3.clearOperationTimeout();
                return;
        }
    }
}
