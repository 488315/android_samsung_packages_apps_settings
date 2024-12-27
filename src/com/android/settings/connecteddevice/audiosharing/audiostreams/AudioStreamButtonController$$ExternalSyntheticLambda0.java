package com.android.settings.connecteddevice.audiosharing.audiostreams;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioStreamButtonController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioStreamButtonController f$0;

    public /* synthetic */ AudioStreamButtonController$$ExternalSyntheticLambda0(
            AudioStreamButtonController audioStreamButtonController, int i) {
        this.$r8$classId = i;
        this.f$0 = audioStreamButtonController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AudioStreamButtonController audioStreamButtonController = this.f$0;
        switch (i) {
            case 0:
                audioStreamButtonController.lambda$updateButton$2();
                break;
            case 1:
                audioStreamButtonController.lambda$updateButton$6();
                break;
            case 2:
                audioStreamButtonController.lambda$updateButton$1();
                break;
            default:
                audioStreamButtonController.lambda$updateButton$5();
                break;
        }
    }
}
