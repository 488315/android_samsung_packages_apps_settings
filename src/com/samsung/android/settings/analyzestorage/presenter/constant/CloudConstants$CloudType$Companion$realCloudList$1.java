package com.samsung.android.settings.analyzestorage.presenter.constant;

import kotlin.jvm.internal.Intrinsics;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CloudConstants$CloudType$Companion$realCloudList$1 implements Predicate {
    public static final CloudConstants$CloudType$Companion$realCloudList$1 INSTANCE =
            new CloudConstants$CloudType$Companion$realCloudList$1();

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        CloudConstants$CloudType cloudType = (CloudConstants$CloudType) obj;
        Intrinsics.checkNotNullParameter(cloudType, "cloudType");
        CloudConstants$CloudType.Companion.getClass();
        return cloudType != CloudConstants$CloudType.NONE;
    }
}
