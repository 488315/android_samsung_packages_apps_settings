package com.android.settings.datausage.lib;

import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.net.NetworkTemplate;
import android.util.Log;
import android.util.Range;

import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;

import kotlin.ExceptionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkStatsRepository {
    public static final Range AllTimeRange = new Range(Long.MIN_VALUE, Long.MAX_VALUE);
    public static final Companion Companion = null;
    public final NetworkStatsManager networkStatsManager;
    public final NetworkTemplate template;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class Bucket {
            public final long bytes;
            public final long endTimeStamp;
            public final long startTimeStamp;
            public final int state;
            public final int uid;

            public Bucket(int i, long j, int i2, long j2, long j3) {
                this.uid = i;
                this.bytes = j;
                this.state = i2;
                this.startTimeStamp = j2;
                this.endTimeStamp = j3;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Bucket)) {
                    return false;
                }
                Bucket bucket = (Bucket) obj;
                return this.uid == bucket.uid
                        && this.bytes == bucket.bytes
                        && this.state == bucket.state
                        && this.startTimeStamp == bucket.startTimeStamp
                        && this.endTimeStamp == bucket.endTimeStamp;
            }

            public final int hashCode() {
                return Long.hashCode(this.endTimeStamp)
                        + Scale$$ExternalSyntheticOutline0.m(
                                KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                                        this.state,
                                        Scale$$ExternalSyntheticOutline0.m(
                                                Integer.hashCode(this.uid) * 31, 31, this.bytes),
                                        31),
                                31,
                                this.startTimeStamp);
            }

            public final String toString() {
                return "Bucket(uid="
                        + this.uid
                        + ", bytes="
                        + this.bytes
                        + ", state="
                        + this.state
                        + ", startTimeStamp="
                        + this.startTimeStamp
                        + ", endTimeStamp="
                        + this.endTimeStamp
                        + ")";
            }
        }

        public static final List access$convertToBuckets(NetworkStats networkStats) {
            Companion companion = NetworkStatsRepository.Companion;
            try {
                ArrayList arrayList = new ArrayList();
                NetworkStats.Bucket bucket = new NetworkStats.Bucket();
                while (networkStats.getNextBucket(bucket)) {
                    Companion companion2 = NetworkStatsRepository.Companion;
                    if (bucket.getTxBytes() + bucket.getRxBytes() > 0) {
                        arrayList.add(
                                new Bucket(
                                        bucket.getUid(),
                                        bucket.getRxBytes() + bucket.getTxBytes(),
                                        bucket.getState(),
                                        bucket.getStartTimeStamp(),
                                        bucket.getEndTimeStamp()));
                    }
                }
                networkStats.close();
                return arrayList;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    try {
                        networkStats.close();
                    } catch (Throwable th3) {
                        ExceptionsKt.addSuppressed(th, th3);
                    }
                    throw th2;
                }
            }
        }

        public static NetworkUsageData aggregate(List list) {
            long j;
            long j2;
            Intrinsics.checkNotNullParameter(list, "<this>");
            if (list.isEmpty()) {
                return null;
            }
            List list2 = list;
            Iterator it = list2.iterator();
            if (!it.hasNext()) {
                throw new NoSuchElementException();
            }
            long j3 = ((Bucket) it.next()).startTimeStamp;
            loop0:
            while (true) {
                j = j3;
                while (it.hasNext()) {
                    j3 = ((Bucket) it.next()).startTimeStamp;
                    if (j > j3) {
                        break;
                    }
                }
            }
            Iterator it2 = list2.iterator();
            if (!it2.hasNext()) {
                throw new NoSuchElementException();
            }
            long j4 = ((Bucket) it2.next()).endTimeStamp;
            loop2:
            while (true) {
                j2 = j4;
                while (it2.hasNext()) {
                    j4 = ((Bucket) it2.next()).endTimeStamp;
                    if (j2 < j4) {
                        break;
                    }
                }
            }
            Iterator it3 = list2.iterator();
            long j5 = 0;
            while (it3.hasNext()) {
                j5 += ((Bucket) it3.next()).bytes;
            }
            return new NetworkUsageData(j, j2, j5);
        }

        public static List filterTime(List list, long j, long j2) {
            Intrinsics.checkNotNullParameter(list, "<this>");
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                Bucket bucket = (Bucket) obj;
                if (bucket.startTimeStamp >= j && bucket.endTimeStamp <= j2) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }
    }

    public NetworkStatsRepository(Context context, NetworkTemplate template) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(template, "template");
        this.template = template;
        Object systemService = context.getSystemService((Class<Object>) NetworkStatsManager.class);
        Intrinsics.checkNotNull(systemService);
        this.networkStatsManager = (NetworkStatsManager) systemService;
    }

    public final List queryBuckets(long j, long j2) {
        try {
            NetworkStats querySummary = this.networkStatsManager.querySummary(this.template, j, j2);
            Intrinsics.checkNotNullExpressionValue(querySummary, "querySummary(...)");
            return Companion.access$convertToBuckets(querySummary);
        } catch (Exception e) {
            Log.e("NetworkStatsRepository", "Exception querySummary", e);
            return EmptyList.INSTANCE;
        }
    }

    public final List queryDetailsForDevice() {
        try {
            NetworkStats queryDetailsForDevice =
                    this.networkStatsManager.queryDetailsForDevice(
                            this.template, Long.MIN_VALUE, Long.MAX_VALUE);
            Intrinsics.checkNotNullExpressionValue(
                    queryDetailsForDevice, "queryDetailsForDevice(...)");
            return Companion.access$convertToBuckets(queryDetailsForDevice);
        } catch (Exception e) {
            Log.e("NetworkStatsRepository", "Exception queryDetailsForDevice", e);
            return EmptyList.INSTANCE;
        }
    }

    public final List queryDetailsForUidTagState(Range range, int i, int i2) {
        try {
            NetworkStatsManager networkStatsManager = this.networkStatsManager;
            NetworkTemplate networkTemplate = this.template;
            Object lower = range.getLower();
            Intrinsics.checkNotNullExpressionValue(lower, "getLower(...)");
            long longValue = ((Number) lower).longValue();
            Object upper = range.getUpper();
            Intrinsics.checkNotNullExpressionValue(upper, "getUpper(...)");
            NetworkStats queryDetailsForUidTagState =
                    networkStatsManager.queryDetailsForUidTagState(
                            networkTemplate, longValue, ((Number) upper).longValue(), i, 0, i2);
            Intrinsics.checkNotNullExpressionValue(
                    queryDetailsForUidTagState, "queryDetailsForUidTagState(...)");
            return Companion.access$convertToBuckets(queryDetailsForUidTagState);
        } catch (Exception e) {
            Log.e("NetworkStatsRepository", "Exception queryDetailsForUidTagState", e);
            return EmptyList.INSTANCE;
        }
    }
}
