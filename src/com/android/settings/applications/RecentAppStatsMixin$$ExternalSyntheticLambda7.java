package com.android.settings.applications;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;

import com.android.settingslib.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class RecentAppStatsMixin$$ExternalSyntheticLambda7
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ RecentAppStatsMixin f$0;

    public /* synthetic */ RecentAppStatsMixin$$ExternalSyntheticLambda7(
            RecentAppStatsMixin recentAppStatsMixin, int i) {
        this.$r8$classId = i;
        this.f$0 = recentAppStatsMixin;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        RecentAppStatsMixin recentAppStatsMixin = this.f$0;
        switch (i) {
            case 0:
                List<RecentAppStatsMixin.UsageStatsWrapper> list = recentAppStatsMixin.mRecentApps;
                throw null;
            default:
                recentAppStatsMixin.getClass();
                recentAppStatsMixin.loadDisplayableRecentApps(4);
                Iterator it = ((ArrayList) recentAppStatsMixin.mAppStatsListeners).iterator();
                while (it.hasNext()) {
                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                    ThreadUtils.postOnMainThread(
                            new RecentAppStatsMixin$$ExternalSyntheticLambda7(
                                    recentAppStatsMixin, 0));
                }
                return;
        }
    }
}
