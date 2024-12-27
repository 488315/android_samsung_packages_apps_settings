package com.samsung.android.settings.analyzestorage.presenter.account;

import kotlin.jvm.internal.Intrinsics;

import java.util.Arrays;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class QuotaInfo {
    public long[] additionalUsageInfo;
    public long totalSize;
    public long usedSize;

    public final boolean equals(Object obj) {
        if (!(obj instanceof QuotaInfo)) {
            return super.equals(obj);
        }
        QuotaInfo quotaInfo = (QuotaInfo) obj;
        if (quotaInfo.totalSize == this.totalSize && quotaInfo.usedSize == this.usedSize) {
            long[] jArr = quotaInfo.additionalUsageInfo;
            int length = jArr.length;
            long[] jArr2 = this.additionalUsageInfo;
            if (length == jArr2.length && Arrays.equals(jArr2, jArr)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Arrays.hashCode(this.additionalUsageInfo)
                + (Objects.hash(Long.valueOf(this.totalSize), Long.valueOf(this.usedSize)) * 31);
    }

    public final String toString() {
        String arrays = Arrays.toString(this.additionalUsageInfo);
        Intrinsics.checkNotNullExpressionValue(arrays, "toString(...)");
        return "QuotaInfo{mTotalSize="
                + this.totalSize
                + ", mUsedSize="
                + this.usedSize
                + ", mAdditionalUsageInfo="
                + arrays
                + "}";
    }
}
