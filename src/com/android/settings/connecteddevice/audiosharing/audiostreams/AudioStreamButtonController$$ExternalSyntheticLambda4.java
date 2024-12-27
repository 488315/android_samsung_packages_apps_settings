package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioStreamButtonController$$ExternalSyntheticLambda4
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ AudioStreamButtonController f$0;
    public final /* synthetic */ View.OnClickListener f$1;

    public /* synthetic */ AudioStreamButtonController$$ExternalSyntheticLambda4(
            AudioStreamButtonController audioStreamButtonController,
            AudioStreamButtonController$$ExternalSyntheticLambda3
                    audioStreamButtonController$$ExternalSyntheticLambda3) {
        this.f$0 = audioStreamButtonController;
        this.f$1 = audioStreamButtonController$$ExternalSyntheticLambda3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$updateButton$4(
                        (AudioStreamButtonController$$ExternalSyntheticLambda3) this.f$1);
                break;
            default:
                this.f$0.lambda$updateButton$8(
                        (AudioStreamButtonController$$ExternalSyntheticLambda3) this.f$1);
                break;
        }
    }

    public /* synthetic */ AudioStreamButtonController$$ExternalSyntheticLambda4(
            AudioStreamButtonController audioStreamButtonController,
            AudioStreamButtonController$$ExternalSyntheticLambda3
                    audioStreamButtonController$$ExternalSyntheticLambda3,
            byte b) {
        this.f$0 = audioStreamButtonController;
        this.f$1 = audioStreamButtonController$$ExternalSyntheticLambda3;
    }
}
