package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.app.AlertDialog;
import android.bluetooth.BluetoothLeBroadcastReceiveState;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioStreamsProgressCategoryController$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioStreamsProgressCategoryController f$0;

    public /* synthetic */ AudioStreamsProgressCategoryController$$ExternalSyntheticLambda2(
            AudioStreamsProgressCategoryController audioStreamsProgressCategoryController, int i) {
        this.$r8$classId = i;
        this.f$0 = audioStreamsProgressCategoryController;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        AudioStreamsProgressCategoryController audioStreamsProgressCategoryController = this.f$0;
        switch (i) {
            case 0:
                audioStreamsProgressCategoryController.handleSourceConnected(
                        (BluetoothLeBroadcastReceiveState) obj);
                break;
            default:
                audioStreamsProgressCategoryController.lambda$getNoLeDeviceDialog$17(
                        (AlertDialog) obj);
                break;
        }
    }
}
