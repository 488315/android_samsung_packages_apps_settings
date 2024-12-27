package com.samsung.android.settings.analyzestorage.external.database.datasource;

import com.samsung.android.settings.analyzestorage.data.model.UnusedAppInfo;

import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class UnusedAppDataSource$$ExternalSyntheticLambda2
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return Objects.nonNull((UnusedAppInfo) obj);
    }
}