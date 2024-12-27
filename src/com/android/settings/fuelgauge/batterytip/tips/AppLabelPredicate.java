package com.android.settings.fuelgauge.batterytip.tips;

import android.content.Context;

import com.android.settings.Utils;
import com.android.settings.fuelgauge.batterytip.AppInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppLabelPredicate implements Predicate {
    public static AppLabelPredicate sInstance;
    public Context mContext;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return Utils.getApplicationLabel(this.mContext, ((AppInfo) obj).packageName) == null;
    }
}
