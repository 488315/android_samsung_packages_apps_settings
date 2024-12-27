package com.android.settings.connecteddevice.audiosharing.audiostreams;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioStreamsProgressCategoryController$$ExternalSyntheticLambda3
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AudioStreamsProgressCategoryController$$ExternalSyntheticLambda3(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((AudioStreamsProgressCategoryController) obj).stopScanning();
                break;
            case 1:
                ((AudioStreamsProgressCategoryController) obj).lambda$init$13();
                break;
            case 2:
                ((AudioStreamsProgressCategoryController) obj).lambda$init$14();
                break;
            case 3:
                ((AudioStreamsProgressCategoryController) obj).init();
                break;
            case 4:
                ((AudioStreamsProgressCategoryController) obj).lambda$startScanning$15();
                break;
            default:
                AudioStreamsProgressCategoryController.this.init();
                break;
        }
    }
}
