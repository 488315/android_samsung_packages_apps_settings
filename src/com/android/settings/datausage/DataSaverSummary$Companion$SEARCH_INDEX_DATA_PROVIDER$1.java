package com.android.settings.datausage;

import android.content.Context;

import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.Rune;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DataSaverSummary$Companion$SEARCH_INDEX_DATA_PROVIDER$1
        extends BaseSearchIndexProvider {
    @Override // com.android.settings.search.BaseSearchIndexProvider
    public final boolean isPageSearchEnabled(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (!DataUsageUtils.hasMobileData(context)
                        || DataUsageUtils.getDefaultSubscriptionId(context) == -1
                        || Rune.SUPPORT_SMARTMANAGER_CN)
                ? false
                : true;
    }
}
