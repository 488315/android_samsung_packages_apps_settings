package com.samsung.android.server.uwb.bigdata.db;

import android.database.sqlite.SQLiteDatabase;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class UwbDbReader {
    public SQLiteDatabase mDb;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UwbDbStateData {
        public final long mStateChangedTime;
        public final int mUwbState;

        public UwbDbStateData(int i, long j) {
            this.mUwbState = i;
            this.mStateChangedTime = j;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UwbDbUsageData {
        public final String mPackageName;
        public final long mRangingDuration;
        public final long mUpdatedTime;

        public UwbDbUsageData(int i, int i2, int i3, long j, long j2, String str) {
            this.mPackageName = str;
            this.mRangingDuration = j;
            this.mUpdatedTime = j2;
        }
    }
}
