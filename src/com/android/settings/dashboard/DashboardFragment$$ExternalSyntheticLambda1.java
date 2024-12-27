package com.android.settings.dashboard;

import android.content.ContentResolver;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DashboardFragment$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DashboardFragment f$0;
    public final /* synthetic */ ContentResolver f$1;

    public /* synthetic */ DashboardFragment$$ExternalSyntheticLambda1(
            DashboardFragment dashboardFragment, ContentResolver contentResolver, int i) {
        this.$r8$classId = i;
        this.f$0 = dashboardFragment;
        this.f$1 = contentResolver;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DashboardFragment.$r8$lambda$bl5y84BjkU1V5p_2MS5gk70M2Zs(
                        this.f$0, this.f$1, (DashboardFeatureProviderImpl.AnonymousClass1) obj);
                break;
            case 1:
                this.f$0.registerDynamicDataObserver(
                        this.f$1, (DashboardFeatureProviderImpl.AnonymousClass1) obj);
                break;
            default:
                DashboardFragment.$r8$lambda$LM966RpCTbdrKoeN8Vi8X9x2SYo(
                        this.f$0, this.f$1, (DashboardFeatureProviderImpl.AnonymousClass1) obj);
                break;
        }
    }
}
