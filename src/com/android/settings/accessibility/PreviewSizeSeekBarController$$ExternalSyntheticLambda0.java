package com.android.settings.accessibility;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class PreviewSizeSeekBarController$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ PreviewSizeSeekBarController$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        PreviewSizeSeekBarController.ProgressInteractionListener progressInteractionListener =
                (PreviewSizeSeekBarController.ProgressInteractionListener) obj;
        switch (this.$r8$classId) {
            case 0:
                progressInteractionListener.onProgressChanged();
                break;
            default:
                progressInteractionListener.onEndTrackingTouch();
                break;
        }
    }
}
