package com.samsung.android.settings.analyzestorage.data.model;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class UnusedAppInfo extends CommonAppInfo {
    private boolean mIsUnusedApps;
    private String mLabel;
    private long mLastLaunchTime;
    private boolean mIsGroupHeader = false;
    private int mGroupType = -1;
    private long mPackageAppByte = 0;

    @Override // com.samsung.android.settings.analyzestorage.domain.entity.AppInfo
    public final String getLabel() {
        return this.mLabel;
    }

    public final long getLastLaunchTime() {
        return this.mLastLaunchTime;
    }

    public final long getPackageAppByte() {
        return this.mPackageAppByte;
    }

    public final boolean isUnusedApps() {
        return this.mIsUnusedApps;
    }

    public final void setLabel(String str) {
        this.mLabel = str;
    }

    public final void setLastLaunchTime(long j) {
        this.mLastLaunchTime = j;
    }

    public final void setPackageAppByte(long j) {
        this.mPackageAppByte = j;
    }

    public final void setUnusedApps(boolean z) {
        this.mIsUnusedApps = z;
    }
}
