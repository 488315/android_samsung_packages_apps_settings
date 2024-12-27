package com.samsung.android.settings.knox;

import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.net.NetworkTemplate;
import android.os.AsyncTask;
import android.util.Log;

import com.android.settings.applications.manageapplications.CloneBackend$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecManagedProfileNetworkStatsSummaryLoader extends AsyncTask {
    public final NetworkStatsLoaderResponse listener;
    public final long mEnd;
    public final NetworkStatsManager mNetworkStatsManager;
    public final NetworkTemplate mNetworkTemplate;
    public final long mStart;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public final Context mContext;
        public long mEnd;
        public NetworkTemplate mNetworkTemplate;
        public long mStart;

        public Builder(Context context) {
            this.mContext = context;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface NetworkStatsLoaderResponse {
        void setNetworkStatsData(NetworkStats networkStats);
    }

    public SecManagedProfileNetworkStatsSummaryLoader(
            Builder builder, NetworkStatsLoaderResponse networkStatsLoaderResponse) {
        this.listener = networkStatsLoaderResponse;
        this.mStart = builder.mStart;
        this.mEnd = builder.mEnd;
        this.mNetworkTemplate = builder.mNetworkTemplate;
        this.mNetworkStatsManager =
                (NetworkStatsManager) builder.mContext.getSystemService("netstats");
    }

    @Override // android.os.AsyncTask
    public final Object doInBackground(Object[] objArr) {
        Log.d("SecManagedProfileNetworkStatsSummaryLoader ", "Do In Background...");
        try {
            return this.mNetworkStatsManager.querySummary(
                    this.mNetworkTemplate, this.mStart, this.mEnd);
        } catch (Exception e) {
            CloneBackend$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Exception :"),
                    "SecManagedProfileNetworkStatsSummaryLoader ");
            return null;
        }
    }

    @Override // android.os.AsyncTask
    public final void onPostExecute(Object obj) {
        NetworkStats networkStats = (NetworkStats) obj;
        Log.d("SecManagedProfileNetworkStatsSummaryLoader ", "Do PostExecute...");
        super.onPostExecute(networkStats);
        this.listener.setNetworkStatsData(networkStats);
    }
}
