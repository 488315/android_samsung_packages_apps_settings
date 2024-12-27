package com.android.settingslib.net;

import android.content.Context;
import android.net.NetworkTemplate;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkCycleDataForUidLoader extends NetworkCycleDataLoader {
    public final List mData;
    public final boolean mRetrieveDetail;
    public final List mUids;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settingslib.net.NetworkCycleDataForUidLoader$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final Context mContext;
        public NetworkTemplate mNetworkTemplate;
        public final List mUids = new ArrayList();
        public boolean mRetrieveDetail = true;

        public AnonymousClass1(Context context) {
            this.mContext = context;
        }
    }

    public NetworkCycleDataForUidLoader(AnonymousClass1 anonymousClass1) {
        super(anonymousClass1);
        this.mUids = anonymousClass1.mUids;
        this.mRetrieveDetail = anonymousClass1.mRetrieveDetail;
        this.mData = new ArrayList();
    }

    public List<Integer> getUids() {
        return this.mUids;
    }

    @Override // com.android.settingslib.net.NetworkCycleDataLoader
    public final void recordUsage(long j, long j2) {
        try {
            Iterator it = ((ArrayList) this.mUids).iterator();
            long j3 = 0;
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                long totalUsage =
                        NetworkCycleDataLoader.getTotalUsage(
                                this.mNetworkStatsManager.queryDetailsForUidTagState(
                                        this.mNetworkTemplate, j, j2, intValue, 0, -1));
                if (totalUsage > 0) {
                    j3 += totalUsage;
                    if (this.mRetrieveDetail) {
                        NetworkCycleDataLoader.getTotalUsage(
                                this.mNetworkStatsManager.queryDetailsForUidTagState(
                                        this.mNetworkTemplate, j, j2, intValue, 0, 2));
                    }
                }
            }
            if (j3 > 0) {
                NetworkCycleDataForUid networkCycleDataForUid = new NetworkCycleDataForUid();
                networkCycleDataForUid.mStartTime = j;
                networkCycleDataForUid.mTotalUsage = j3;
                ((ArrayList) this.mData).add(networkCycleDataForUid);
            }
        } catch (Exception e) {
            Log.e("NetworkDataForUidLoader", "Exception querying network detail.", e);
        }
    }
}
