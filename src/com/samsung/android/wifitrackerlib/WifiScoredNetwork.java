package com.samsung.android.wifitrackerlib;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiScoredNetwork {
    public final String bssid;
    public final int[] levels;
    public final int networkType;

    public WifiScoredNetwork(String str, int i, int[] iArr) {
        this.bssid = str;
        this.networkType = i;
        this.levels = iArr;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("key:");
        sb.append(this.bssid);
        sb.append(", networkType:");
        sb.append(this.networkType);
        sb.append(", speed:[");
        for (int i : this.levels) {
            sb.append(i);
            sb.append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
}
