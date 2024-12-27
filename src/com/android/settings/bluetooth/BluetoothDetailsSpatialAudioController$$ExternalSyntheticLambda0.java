package com.android.settings.bluetooth;

import android.media.AudioDeviceAttributes;

import androidx.preference.TwoStatePreference;

import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BluetoothDetailsSpatialAudioController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BluetoothDetailsSpatialAudioController f$0;
    public final /* synthetic */ TwoStatePreference f$1;

    public /* synthetic */ BluetoothDetailsSpatialAudioController$$ExternalSyntheticLambda0(
            BluetoothDetailsSpatialAudioController bluetoothDetailsSpatialAudioController,
            TwoStatePreference twoStatePreference,
            int i) {
        this.$r8$classId = i;
        this.f$0 = bluetoothDetailsSpatialAudioController;
        this.f$1 = twoStatePreference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BluetoothDetailsSpatialAudioController bluetoothDetailsSpatialAudioController =
                        this.f$0;
                TwoStatePreference twoStatePreference = this.f$1;
                AtomicBoolean atomicBoolean =
                        bluetoothDetailsSpatialAudioController.mHasHeadTracker;
                AudioDeviceAttributes audioDeviceAttributes =
                        bluetoothDetailsSpatialAudioController.mAudioDevice;
                atomicBoolean.set(
                        audioDeviceAttributes != null
                                && bluetoothDetailsSpatialAudioController.mSpatializer
                                        .hasHeadTracker(audioDeviceAttributes));
                ((BluetoothDetailsController) bluetoothDetailsSpatialAudioController)
                        .mContext
                        .getMainExecutor()
                        .execute(
                                new BluetoothDetailsSpatialAudioController$$ExternalSyntheticLambda0(
                                        bluetoothDetailsSpatialAudioController,
                                        twoStatePreference,
                                        1));
                break;
            default:
                this.f$0.refreshSpatialAudioEnabled(this.f$1);
                break;
        }
    }
}
