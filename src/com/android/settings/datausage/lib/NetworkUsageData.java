package com.android.settings.datausage.lib;

import android.R;
import android.content.Context;
import android.text.BidiFormatter;
import android.text.format.Formatter;
import android.util.Range;

import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NetworkUsageData {
    public final long endTime;
    public final long startTime;
    public final Range timeRange;
    public final long usage;

    static {
        new NetworkUsageData(0L, 0L, 0L);
    }

    public NetworkUsageData(long j, long j2, long j3) {
        this.startTime = j;
        this.endTime = j2;
        this.usage = j3;
        this.timeRange = new Range(Long.valueOf(j), Long.valueOf(j2));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NetworkUsageData)) {
            return false;
        }
        NetworkUsageData networkUsageData = (NetworkUsageData) obj;
        return this.startTime == networkUsageData.startTime
                && this.endTime == networkUsageData.endTime
                && this.usage == networkUsageData.usage;
    }

    public final DataUsageFormatter.FormattedDataUsage formatUsage(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Formatter.BytesResult formatBytes =
                Formatter.formatBytes(context.getResources(), this.usage, 8);
        String unicodeWrap =
                BidiFormatter.getInstance()
                        .unicodeWrap(
                                context.getString(
                                        R.string.lockscreen_glogin_invalid_input,
                                        formatBytes.value,
                                        formatBytes.units));
        Intrinsics.checkNotNullExpressionValue(unicodeWrap, "unicodeWrap(...)");
        String string =
                context.getString(
                        R.string.lockscreen_glogin_invalid_input,
                        formatBytes.value,
                        formatBytes.unitsContentDescription);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return new DataUsageFormatter.FormattedDataUsage(unicodeWrap, string);
    }

    public final int hashCode() {
        return Long.hashCode(this.usage)
                + Scale$$ExternalSyntheticOutline0.m(
                        Long.hashCode(this.startTime) * 31, 31, this.endTime);
    }

    public final String toString() {
        return "NetworkUsageData(startTime="
                + this.startTime
                + ", endTime="
                + this.endTime
                + ", usage="
                + this.usage
                + ")";
    }
}
