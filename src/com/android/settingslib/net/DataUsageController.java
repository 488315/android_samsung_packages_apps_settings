package com.android.settingslib.net;

import android.R;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.net.NetworkPolicy;
import android.net.NetworkPolicyManager;
import android.net.NetworkTemplate;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.Range;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;

import java.time.ZonedDateTime;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DataUsageController {
    public static final StringBuilder PERIOD_BUILDER;
    public static final Formatter PERIOD_FORMATTER;
    public final Context mContext;
    public final NetworkStatsManager mNetworkStatsManager;
    public final NetworkPolicyManager mPolicyManager;
    public final int mSubscriptionId = -1;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DataUsageInfo {
        public long cycleEnd;
        public long cycleStart;
        public long limitLevel;
        public String period;
        public long startDate;
        public long usageLevel;
        public long warningLevel;
    }

    static {
        Log.isLoggable("DataUsageController", 3);
        StringBuilder sb = new StringBuilder(50);
        PERIOD_BUILDER = sb;
        PERIOD_FORMATTER = new Formatter(sb, Locale.getDefault());
    }

    public DataUsageController(Context context) {
        this.mContext = context;
        this.mPolicyManager = NetworkPolicyManager.from(context);
        this.mNetworkStatsManager =
                (NetworkStatsManager) context.getSystemService(NetworkStatsManager.class);
    }

    public final DataUsageInfo getDataUsageInfo(NetworkTemplate networkTemplate) {
        NetworkPolicy networkPolicy;
        long j;
        long j2;
        long j3;
        long j4;
        String formatter;
        NetworkPolicy[] networkPolicies;
        NetworkPolicyManager networkPolicyManager = this.mPolicyManager;
        if (networkPolicyManager != null
                && networkTemplate != null
                && (networkPolicies = networkPolicyManager.getNetworkPolicies()) != null) {
            for (NetworkPolicy networkPolicy2 : networkPolicies) {
                if (networkPolicy2 != null && networkTemplate.equals(networkPolicy2.template)) {
                    networkPolicy = networkPolicy2;
                    break;
                }
            }
        }
        networkPolicy = null;
        long currentTimeMillis = System.currentTimeMillis();
        Iterator cycleIterator = networkPolicy != null ? networkPolicy.cycleIterator() : null;
        if (cycleIterator == null || !cycleIterator.hasNext()) {
            j = currentTimeMillis;
            j2 = currentTimeMillis - 2419200000L;
        } else {
            Range range = (Range) cycleIterator.next();
            j2 = ((ZonedDateTime) range.getLower()).toInstant().toEpochMilli();
            j = ((ZonedDateTime) range.getUpper()).toInstant().toEpochMilli();
        }
        long usageLevel = getUsageLevel(networkTemplate, j2, j);
        if (usageLevel < 0) {
            Log.w("DataUsageController", "Failed to get data usage, no entry data");
            return null;
        }
        DataUsageInfo dataUsageInfo = new DataUsageInfo();
        dataUsageInfo.startDate = j2;
        dataUsageInfo.usageLevel = usageLevel;
        StringBuilder sb = PERIOD_BUILDER;
        synchronized (sb) {
            sb.setLength(0);
            j3 = j;
            j4 = j2;
            formatter =
                    DateUtils.formatDateRange(this.mContext, PERIOD_FORMATTER, j2, j3, 65552, null)
                            .toString();
        }
        dataUsageInfo.period = formatter;
        dataUsageInfo.cycleStart = j4;
        dataUsageInfo.cycleEnd = j3;
        if (networkPolicy != null) {
            long j5 = networkPolicy.limitBytes;
            if (j5 <= 0) {
                j5 = 0;
            }
            dataUsageInfo.limitLevel = j5;
            long j6 = networkPolicy.warningBytes;
            dataUsageInfo.warningLevel = j6 > 0 ? j6 : 0L;
        } else {
            dataUsageInfo.warningLevel =
                    this.mContext
                                    .getResources()
                                    .getInteger(R.integer.lock_pattern_line_fade_out_delay)
                            * 1048576;
        }
        return dataUsageInfo;
    }

    @VisibleForTesting
    public TelephonyManager getTelephonyManager() {
        int i = this.mSubscriptionId;
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            i = SubscriptionManager.getDefaultDataSubscriptionId();
        }
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            int[] activeSubscriptionIdList =
                    SubscriptionManager.from(this.mContext).getActiveSubscriptionIdList();
            if (!ArrayUtils.isEmpty(activeSubscriptionIdList)) {
                i = activeSubscriptionIdList[0];
            }
        }
        return ((TelephonyManager) this.mContext.getSystemService(TelephonyManager.class))
                .createForSubscriptionId(i);
    }

    public final long getUsageLevel(NetworkTemplate networkTemplate, long j, long j2) {
        try {
            NetworkStats.Bucket querySummaryForDevice =
                    this.mNetworkStatsManager.querySummaryForDevice(networkTemplate, j, j2);
            if (querySummaryForDevice != null) {
                return querySummaryForDevice.getRxBytes() + querySummaryForDevice.getTxBytes();
            }
            Log.w("DataUsageController", "Failed to get data usage, no entry data");
            return -1L;
        } catch (RuntimeException unused) {
            Log.w("DataUsageController", "Failed to get data usage, remote call failed");
            return -1L;
        }
    }
}
