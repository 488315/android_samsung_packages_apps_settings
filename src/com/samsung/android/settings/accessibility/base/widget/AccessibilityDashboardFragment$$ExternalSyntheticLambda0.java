package com.samsung.android.settings.accessibility.base.widget;

import androidx.core.util.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class AccessibilityDashboardFragment$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ DescriptionPreference f$0;

    public /* synthetic */ AccessibilityDashboardFragment$$ExternalSyntheticLambda0(
            DescriptionPreference descriptionPreference) {
        this.f$0 = descriptionPreference;
    }

    @Override // androidx.core.util.Consumer
    public final void accept(Object obj) {
        this.f$0.notifyChanged();
    }
}
