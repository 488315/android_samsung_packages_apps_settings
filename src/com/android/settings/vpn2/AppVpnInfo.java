package com.android.settings.vpn2;

import com.android.internal.util.Preconditions;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppVpnInfo implements Comparable {
    public final String packageName;
    public final int userId;

    public AppVpnInfo(int i, String str) {
        this.userId = i;
        this.packageName = (String) Preconditions.checkNotNull(str);
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        AppVpnInfo appVpnInfo = (AppVpnInfo) obj;
        int compareTo = this.packageName.compareTo(appVpnInfo.packageName);
        return compareTo == 0 ? appVpnInfo.userId - this.userId : compareTo;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof AppVpnInfo)) {
            return false;
        }
        AppVpnInfo appVpnInfo = (AppVpnInfo) obj;
        return this.userId == appVpnInfo.userId
                && Objects.equals(this.packageName, appVpnInfo.packageName);
    }

    public final int hashCode() {
        return Objects.hash(this.packageName, Integer.valueOf(this.userId));
    }
}
