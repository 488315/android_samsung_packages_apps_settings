package com.android.settings.applications;

import android.app.usage.StorageStats;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.util.Log;

import com.android.internal.util.Preconditions;
import com.android.settingslib.applications.StorageStatsSource;
import com.android.settingslib.utils.AsyncLoaderCompat;

import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FetchPackageStorageAsyncLoader extends AsyncLoaderCompat {
    public final ApplicationInfo mInfo;
    public final StorageStatsSource mSource;
    public final UserHandle mUser;

    public FetchPackageStorageAsyncLoader(
            Context context,
            StorageStatsSource storageStatsSource,
            ApplicationInfo applicationInfo,
            UserHandle userHandle) {
        super(context);
        this.mSource = (StorageStatsSource) Preconditions.checkNotNull(storageStatsSource);
        this.mInfo = applicationInfo;
        this.mUser = userHandle;
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final Object loadInBackground() {
        try {
            StorageStatsSource storageStatsSource = this.mSource;
            ApplicationInfo applicationInfo = this.mInfo;
            StorageStats queryStatsForPackage =
                    storageStatsSource.mStorageStatsManager.queryStatsForPackage(
                            applicationInfo.volumeUuid, applicationInfo.packageName, this.mUser);
            StorageStatsSource.AppStorageStatsImpl appStorageStatsImpl =
                    new StorageStatsSource.AppStorageStatsImpl();
            appStorageStatsImpl.mStats = queryStatsForPackage;
            return appStorageStatsImpl;
        } catch (PackageManager.NameNotFoundException | IOException e) {
            Log.w(
                    "FetchPackageStorage",
                    "Package may have been removed during query, failing gracefully",
                    e);
            return null;
        }
    }

    @Override // com.android.settingslib.utils.AsyncLoaderCompat
    public final /* bridge */ /* synthetic */ void onDiscardResult(Object obj) {}
}
