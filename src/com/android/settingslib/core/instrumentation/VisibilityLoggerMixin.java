package com.android.settingslib.core.instrumentation;

import android.os.SystemClock;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.android.settingslib.core.lifecycle.LifecycleObserver;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VisibilityLoggerMixin implements LifecycleObserver {
    public long mCreationTimestamp;
    public final int mMetricsCategory;
    public final MetricsFeatureProvider mMetricsFeature;
    public int mSourceMetricsCategory = 0;
    public long mVisibleTimestamp;

    public VisibilityLoggerMixin(int i, MetricsFeatureProvider metricsFeatureProvider) {
        this.mMetricsCategory = i;
        this.mMetricsFeature = metricsFeatureProvider;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        int i;
        this.mCreationTimestamp = 0L;
        MetricsFeatureProvider metricsFeatureProvider = this.mMetricsFeature;
        if (metricsFeatureProvider == null || (i = this.mMetricsCategory) == 0) {
            return;
        }
        int elapsedRealtime = (int) (SystemClock.elapsedRealtime() - this.mVisibleTimestamp);
        Iterator it = ((ArrayList) metricsFeatureProvider.mLoggerWriters).iterator();
        while (it.hasNext()) {
            ((LogWriter) it.next()).hidden(i, elapsedRealtime);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        int i;
        MetricsFeatureProvider metricsFeatureProvider = this.mMetricsFeature;
        if (metricsFeatureProvider == null || (i = this.mMetricsCategory) == 0) {
            return;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mVisibleTimestamp = elapsedRealtime;
        long j = this.mCreationTimestamp;
        if (j != 0) {
            metricsFeatureProvider.visible(
                    null, this.mSourceMetricsCategory, i, (int) (elapsedRealtime - j));
        } else {
            metricsFeatureProvider.visible(null, this.mSourceMetricsCategory, i, 0);
        }
    }
}
