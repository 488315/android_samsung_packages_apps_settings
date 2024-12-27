package com.android.settings.applications;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settingslib.applications.StorageStatsSource;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStorageSizesController {
    public final Preference mAppSize;
    public final Preference mCacheSize;
    public boolean mCachedCleared;
    public boolean mDataCleared;
    public final Preference mDataSize;
    public StorageStatsSource.AppStorageStats mLastResult;
    public boolean mLastResultFailed;
    public final Preference mTotalSize;
    public long mLastCodeSize = -1;
    public long mLastDataSize = -1;
    public long mLastCacheSize = -1;
    public long mLastTotalSize = -1;
    public final int mComputing = R.string.computing_size;
    public final int mError = R.string.invalid_size_value;

    public AppStorageSizesController(
            Preference preference,
            Preference preference2,
            Preference preference3,
            Preference preference4) {
        this.mTotalSize = preference;
        this.mAppSize = preference2;
        this.mDataSize = preference3;
        this.mCacheSize = preference4;
    }
}
