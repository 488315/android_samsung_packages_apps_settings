package com.android.settings.connecteddevice.audiosharing.audiostreams;

import java.util.function.BiFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioStreamsProgressCategoryController$$ExternalSyntheticLambda6
        implements BiFunction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioStreamsProgressCategoryController f$0;

    public /* synthetic */ AudioStreamsProgressCategoryController$$ExternalSyntheticLambda6(
            AudioStreamsProgressCategoryController audioStreamsProgressCategoryController, int i) {
        this.$r8$classId = i;
        this.f$0 = audioStreamsProgressCategoryController;
    }

    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        AudioStreamPreference lambda$handleSourceConnectBadCode$9;
        AudioStreamPreference lambda$handleSourceFailedToConnect$10;
        AudioStreamPreference lambda$handleSourceFromQrCodeIfExists$3;
        int i = this.$r8$classId;
        AudioStreamsProgressCategoryController audioStreamsProgressCategoryController = this.f$0;
        Integer num = (Integer) obj;
        AudioStreamPreference audioStreamPreference = (AudioStreamPreference) obj2;
        switch (i) {
            case 0:
                lambda$handleSourceConnectBadCode$9 =
                        audioStreamsProgressCategoryController.lambda$handleSourceConnectBadCode$9(
                                num, audioStreamPreference);
                return lambda$handleSourceConnectBadCode$9;
            case 1:
                lambda$handleSourceFailedToConnect$10 =
                        audioStreamsProgressCategoryController
                                .lambda$handleSourceFailedToConnect$10(num, audioStreamPreference);
                return lambda$handleSourceFailedToConnect$10;
            default:
                lambda$handleSourceFromQrCodeIfExists$3 =
                        audioStreamsProgressCategoryController
                                .lambda$handleSourceFromQrCodeIfExists$3(
                                        num, audioStreamPreference);
                return lambda$handleSourceFromQrCodeIfExists$3;
        }
    }
}
