package com.samsung.android.settings.analyzestorage.presenter.execution;

import com.samsung.android.settings.analyzestorage.data.model.CommonAppInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.AppInfo;

import java.util.function.ToLongFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class ExecuteClearAppCache$1$$ExternalSyntheticLambda3
        implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return ((CommonAppInfo) ((AppInfo) obj)).getSize();
    }
}
