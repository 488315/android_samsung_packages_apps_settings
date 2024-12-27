package com.android.settings.connecteddevice.audiosharing.audiostreams;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioStreamsCategoryController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioStreamsCategoryController f$0;

    public /* synthetic */ AudioStreamsCategoryController$$ExternalSyntheticLambda0(
            AudioStreamsCategoryController audioStreamsCategoryController, int i) {
        this.$r8$classId = i;
        this.f$0 = audioStreamsCategoryController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AudioStreamsCategoryController audioStreamsCategoryController = this.f$0;
        switch (i) {
            case 0:
                audioStreamsCategoryController.lambda$updateVisibility$2();
                break;
            default:
                audioStreamsCategoryController.lambda$updateVisibility$0();
                break;
        }
    }
}
