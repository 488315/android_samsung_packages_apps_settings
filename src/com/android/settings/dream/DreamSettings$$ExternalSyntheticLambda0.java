package com.android.settings.dream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DreamSettings$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ DreamSettings f$0;

    public /* synthetic */ DreamSettings$$ExternalSyntheticLambda0(DreamSettings dreamSettings) {
        this.f$0 = dreamSettings;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DreamSettings dreamSettings = this.f$0;
        dreamSettings.mRecyclerView.setPadding(
                0, 0, 0, dreamSettings.mPreviewButton.getMeasuredHeight());
    }
}
