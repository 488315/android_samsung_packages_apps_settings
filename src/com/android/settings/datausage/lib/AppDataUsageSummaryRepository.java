package com.android.settings.datausage.lib;

import android.content.Context;
import android.net.NetworkTemplate;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppDataUsageSummaryRepository {
    public final NetworkStatsRepository networkStatsRepository;

    public AppDataUsageSummaryRepository(Context context, NetworkTemplate template) {
        NetworkStatsRepository networkStatsRepository =
                new NetworkStatsRepository(context, template);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(template, "template");
        this.networkStatsRepository = networkStatsRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object querySummary(int r10, kotlin.coroutines.Continuation r11) {
        /*
            Method dump skipped, instructions count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.datausage.lib.AppDataUsageSummaryRepository.querySummary(int,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }
}
