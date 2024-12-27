package com.android.settings.connecteddevice.audiosharing.audiostreams;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioStreamsProgressCategoryController$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioStreamsProgressCategoryController f$0;
    public final /* synthetic */ AudioStreamPreference f$1;

    public /* synthetic */ AudioStreamsProgressCategoryController$$ExternalSyntheticLambda1(
            AudioStreamsProgressCategoryController audioStreamsProgressCategoryController,
            AudioStreamPreference audioStreamPreference,
            int i) {
        this.$r8$classId = i;
        this.f$0 = audioStreamsProgressCategoryController;
        this.f$1 = audioStreamPreference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$handleSourceRemoved$7(this.f$1);
                break;
            case 1:
                this.f$0.lambda$moveToState$16(this.f$1);
                break;
            default:
                this.f$0.lambda$handleSourceLost$5(this.f$1);
                break;
        }
    }
}
