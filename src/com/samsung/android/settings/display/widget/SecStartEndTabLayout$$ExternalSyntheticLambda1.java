package com.samsung.android.settings.display.widget;

import java.util.function.IntConsumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecStartEndTabLayout$$ExternalSyntheticLambda1
        implements IntConsumer {
    public final /* synthetic */ SecStartEndTabLayout f$0;

    public /* synthetic */ SecStartEndTabLayout$$ExternalSyntheticLambda1(
            SecStartEndTabLayout secStartEndTabLayout) {
        this.f$0 = secStartEndTabLayout;
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        SecStartEndTabLayout secStartEndTabLayout = this.f$0;
        secStartEndTabLayout.updateTime(i, secStartEndTabLayout.mTimes[i]);
    }
}
