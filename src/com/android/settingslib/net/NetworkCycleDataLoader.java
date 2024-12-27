package com.android.settingslib.net;

import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.net.NetworkPolicy;
import android.net.NetworkPolicyManager;
import android.net.NetworkTemplate;
import android.util.Pair;
import android.util.Range;

import androidx.loader.content.AsyncTaskLoader;

import com.android.settingslib.NetworkPolicyEditor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class NetworkCycleDataLoader extends AsyncTaskLoader {
    public final NetworkStatsManager mNetworkStatsManager;
    public final NetworkTemplate mNetworkTemplate;
    public final NetworkPolicy mPolicy;

    public NetworkCycleDataLoader(NetworkCycleDataForUidLoader.AnonymousClass1 anonymousClass1) {
        super(anonymousClass1.mContext);
        NetworkTemplate networkTemplate = anonymousClass1.mNetworkTemplate;
        this.mNetworkTemplate = networkTemplate;
        this.mNetworkStatsManager =
                (NetworkStatsManager) anonymousClass1.mContext.getSystemService("netstats");
        NetworkPolicyEditor networkPolicyEditor =
                new NetworkPolicyEditor(NetworkPolicyManager.from(anonymousClass1.mContext));
        networkPolicyEditor.read();
        this.mPolicy = networkPolicyEditor.getPolicy(networkTemplate);
    }

    public static long getTotalUsage(NetworkStats networkStats) {
        long j = 0;
        if (networkStats != null) {
            NetworkStats.Bucket bucket = new NetworkStats.Bucket();
            while (networkStats.hasNextBucket() && networkStats.getNextBucket(bucket)) {
                j += bucket.getTxBytes() + bucket.getRxBytes();
            }
            networkStats.close();
        }
        return j;
    }

    public ArrayList<Long> getCycles() {
        return null;
    }

    public NetworkStats.Bucket getNextBucket(NetworkStats networkStats) {
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        networkStats.getNextBucket(bucket);
        return bucket;
    }

    public Range getTimeRangeOf(NetworkStats networkStats) {
        long j = Long.MAX_VALUE;
        long j2 = Long.MIN_VALUE;
        while (hasNextBucket(networkStats)) {
            NetworkStats.Bucket nextBucket = getNextBucket(networkStats);
            j = Math.min(j, nextBucket.getStartTimeStamp());
            j2 = Math.max(j2, nextBucket.getEndTimeStamp());
        }
        return new Range(Long.valueOf(j), Long.valueOf(j2));
    }

    public boolean hasNextBucket(NetworkStats networkStats) {
        return networkStats.hasNextBucket();
    }

    public void loadDataForSpecificCycles() {
        throw null;
    }

    public void loadFourWeeksData() {
        NetworkTemplate networkTemplate = this.mNetworkTemplate;
        if (networkTemplate == null) {
            return;
        }
        try {
            Range timeRangeOf =
                    getTimeRangeOf(
                            this.mNetworkStatsManager.queryDetailsForDevice(
                                    networkTemplate, Long.MIN_VALUE, Long.MAX_VALUE));
            long longValue = ((Long) timeRangeOf.getUpper()).longValue();
            while (longValue > ((Long) timeRangeOf.getLower()).longValue()) {
                long j = longValue - 2419200000L;
                recordUsage(j, longValue);
                longValue = j;
            }
        } catch (IllegalArgumentException unused) {
        }
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final Object loadInBackground() {
        if (this.mPolicy == null) {
            loadFourWeeksData();
        } else {
            loadPolicyData();
        }
        return ((NetworkCycleDataForUidLoader) this).mData;
    }

    public void loadPolicyData() {
        Iterator cycleIterator = NetworkPolicyManager.cycleIterator(this.mPolicy);
        while (cycleIterator.hasNext()) {
            Pair pair = (Pair) cycleIterator.next();
            recordUsage(
                    ((ZonedDateTime) pair.first).toInstant().toEpochMilli(),
                    ((ZonedDateTime) pair.second).toInstant().toEpochMilli());
        }
    }

    @Override // androidx.loader.content.Loader
    public final void onReset() {
        onCancelLoad();
    }

    @Override // androidx.loader.content.Loader
    public final void onStartLoading() {
        onForceLoad();
    }

    @Override // androidx.loader.content.Loader
    public final void onStopLoading() {
        onCancelLoad();
    }

    public abstract void recordUsage(long j, long j2);
}
