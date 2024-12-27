package com.android.settings.bluetooth;

import android.media.AudioDeviceAttributes;

import androidx.preference.TwoStatePreference;

import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BluetoothDetailsSpatialAudioController$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BluetoothDetailsSpatialAudioController f$0;

    public /* synthetic */ BluetoothDetailsSpatialAudioController$$ExternalSyntheticLambda1(
            BluetoothDetailsSpatialAudioController bluetoothDetailsSpatialAudioController, int i) {
        this.$r8$classId = i;
        this.f$0 = bluetoothDetailsSpatialAudioController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BluetoothDetailsSpatialAudioController bluetoothDetailsSpatialAudioController = this.f$0;
        switch (i) {
            case 0:
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
                                new BluetoothDetailsSpatialAudioController$$ExternalSyntheticLambda1(
                                        bluetoothDetailsSpatialAudioController, 1));
                break;
            default:
                TwoStatePreference twoStatePreference =
                        (TwoStatePreference)
                                bluetoothDetailsSpatialAudioController.mProfilesContainer
                                        .findPreference("spatial_audio");
                if (twoStatePreference != null
                        || bluetoothDetailsSpatialAudioController.mAudioDevice == null) {
                    AudioDeviceAttributes audioDeviceAttributes2 =
                            bluetoothDetailsSpatialAudioController.mAudioDevice;
                    if (audioDeviceAttributes2 == null
                            || !bluetoothDetailsSpatialAudioController.mSpatializer
                                    .isAvailableForDevice(audioDeviceAttributes2)) {
                        if (twoStatePreference != null) {
                            bluetoothDetailsSpatialAudioController.mProfilesContainer
                                    .removePreference(twoStatePreference);
                        }
                        TwoStatePreference twoStatePreference2 =
                                (TwoStatePreference)
                                        bluetoothDetailsSpatialAudioController.mProfilesContainer
                                                .findPreference("head_tracking");
                        if (twoStatePreference2 != null) {
                            bluetoothDetailsSpatialAudioController.mProfilesContainer
                                    .removePreference(twoStatePreference2);
                        }
                        bluetoothDetailsSpatialAudioController.mAudioDevice = null;
                        break;
                    }
                } else {
                    twoStatePreference =
                            bluetoothDetailsSpatialAudioController.createSpatialAudioPreference(
                                    bluetoothDetailsSpatialAudioController.mProfilesContainer
                                            .getContext());
                    bluetoothDetailsSpatialAudioController.mProfilesContainer.addPreference(
                            twoStatePreference);
                }
                bluetoothDetailsSpatialAudioController.refreshSpatialAudioEnabled(
                        twoStatePreference);
                break;
        }
    }
}
