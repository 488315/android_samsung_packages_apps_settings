package com.samsung.android.settings.gts;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SoundsAndVibrationSettingsGtsProvider$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        GtsResources.GtsGroup gtsGroup = (GtsResources.GtsGroup) obj;
        int i = SoundsAndVibrationSettingsGtsProvider.$r8$clinit;
        return "com.samsung.android.settings.gts.category.SOUNDS".equals(gtsGroup.getGroupName())
                || "com.samsung.android.settings.gts.category.VIBRATION_INTENSITY"
                        .equals(gtsGroup.getGroupName())
                || "com.samsung.android.settings.gts.category.VIBRATION_SYSTEM"
                        .equals(gtsGroup.getGroupName())
                || "com.samsung.android.settings.gts.category.VIBRATION"
                        .equals(gtsGroup.getGroupName())
                || "com.samsung.android.settings.gts.category.SOUNDS_SYSTEM_SOUNDS"
                        .equals(gtsGroup.getGroupName())
                || "com.samsung.android.settings.gts.category.SOUNDS_VOLUME"
                        .equals(gtsGroup.getGroupName());
    }
}
