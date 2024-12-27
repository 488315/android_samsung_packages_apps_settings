package com.samsung.android.settings.gts;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class AdvancedSettingsGtsProvider$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        GtsResources.GtsGroup gtsGroup = (GtsResources.GtsGroup) obj;
        int i = AdvancedSettingsGtsProvider.$r8$clinit;
        return "com.samsung.android.settings.gts.category.ADVANCED".equals(gtsGroup.getGroupName())
                || "com.samsung.android.settings.gts.category.ONE_HANDED_OPERATION"
                        .equals(gtsGroup.getGroupName());
    }
}
