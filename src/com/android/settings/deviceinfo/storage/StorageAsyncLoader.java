package com.android.settings.deviceinfo.storage;

import android.app.usage.ExternalStorageStats;
import android.app.usage.StorageStats;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;

import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settingslib.applications.StorageStatsSource;
import com.android.settingslib.utils.AsyncLoaderCompat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class StorageAsyncLoader extends AsyncLoaderCompat {
    public final PackageManager mPackageManager;
    public ArraySet mSeenPackages;
    public final StorageStatsSource mStatsManager;
    public final UserManager mUserManager;
    public final String mUuid;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class StorageResult {
        public long allAppsExceptGamesSize;
        public long audioSize;
        public long documentsSize;
        public long duplicateCodeSize;
        public StorageStatsSource.ExternalStorageStats externalStats;
        public long gamesSize;
        public long imagesSize;
        public long otherSize;
        public long systemSize;
        public long trashSize;
        public long videosSize;
    }

    public StorageAsyncLoader(
            Context context,
            UserManager userManager,
            String str,
            StorageStatsSource storageStatsSource,
            PackageManager packageManager) {
        super(context);
        this.mUserManager = userManager;
        this.mUuid = str;
        this.mStatsManager = storageStatsSource;
        this.mPackageManager = packageManager;
    }

    public final long getFilesSize(int i, Uri uri, Bundle bundle) {
        try {
            Context context = this.mContext;
            Cursor query =
                    context.createPackageContextAsUser(
                                    context.getApplicationContext().getPackageName(),
                                    0,
                                    UserHandle.of(i))
                            .getContentResolver()
                            .query(uri, new String[] {"sum(_size)"}, bundle, null);
            if (query == null) {
                if (query != null) {
                    query.close();
                }
                return 0L;
            }
            try {
                long j = query.moveToFirst() ? query.getLong(0) : 0L;
                query.close();
                return j;
            } catch (Throwable th) {
                try {
                    query.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (PackageManager.NameNotFoundException unused) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Not able to get Context for user ID ", "StorageAsyncLoader");
            return 0L;
        }
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final Object loadInBackground() {
        StorageStatsSource storageStatsSource;
        String str;
        long j;
        UserHandle userHandle;
        int i;
        this.mSeenPackages = new ArraySet();
        SparseArray sparseArray = new SparseArray();
        List<UserInfo> users = this.mUserManager.getUsers();
        Collections.sort(users, new StorageAsyncLoader$$ExternalSyntheticLambda0());
        for (UserInfo userInfo : users) {
            int i2 = userInfo.id;
            Log.d("StorageAsyncLoader", "Loading apps");
            int i3 = 0;
            List installedApplicationsAsUser =
                    this.mPackageManager.getInstalledApplicationsAsUser(0, i2);
            StorageResult storageResult = new StorageResult();
            UserHandle of = UserHandle.of(i2);
            int size = installedApplicationsAsUser.size();
            while (true) {
                storageStatsSource = this.mStatsManager;
                str = this.mUuid;
                if (i3 >= size) {
                    break;
                }
                ApplicationInfo applicationInfo =
                        (ApplicationInfo) installedApplicationsAsUser.get(i3);
                try {
                    StorageStats queryStatsForPackage =
                            storageStatsSource.mStorageStatsManager.queryStatsForPackage(
                                    str, applicationInfo.packageName, of);
                    long dataBytes = queryStatsForPackage.getDataBytes();
                    long cacheQuotaBytes =
                            storageStatsSource.mStorageStatsManager.getCacheQuotaBytes(
                                    str, applicationInfo.uid);
                    long cacheBytes = queryStatsForPackage.getCacheBytes();
                    long appBytes = queryStatsForPackage.getAppBytes() + dataBytes;
                    if (cacheQuotaBytes < cacheBytes) {
                        appBytes = (appBytes - cacheBytes) + cacheQuotaBytes;
                    }
                    if (this.mSeenPackages.contains(applicationInfo.packageName)) {
                        userHandle = of;
                        i = size;
                        storageResult.duplicateCodeSize =
                                queryStatsForPackage.getAppBytes()
                                        + storageResult.duplicateCodeSize;
                    } else {
                        userHandle = of;
                        i = size;
                        this.mSeenPackages.add(applicationInfo.packageName);
                    }
                    int i4 = applicationInfo.category;
                    if (i4 == 0) {
                        storageResult.gamesSize += appBytes;
                    } else if (i4 == 1 || i4 == 2 || i4 == 3) {
                        storageResult.allAppsExceptGamesSize += appBytes;
                    } else if ((applicationInfo.flags & 33554432) != 0) {
                        storageResult.gamesSize += appBytes;
                    } else {
                        storageResult.allAppsExceptGamesSize += appBytes;
                    }
                } catch (PackageManager.NameNotFoundException | IOException e) {
                    userHandle = of;
                    i = size;
                    Log.w("StorageAsyncLoader", "App unexpectedly not found", e);
                }
                i3++;
                of = userHandle;
                size = i;
            }
            Log.d("StorageAsyncLoader", "Loading external stats");
            try {
                ExternalStorageStats queryExternalStatsForUser =
                        storageStatsSource.mStorageStatsManager.queryExternalStatsForUser(
                                str, UserHandle.of(i2));
                StorageStatsSource.ExternalStorageStats externalStorageStats =
                        new StorageStatsSource.ExternalStorageStats();
                externalStorageStats.totalBytes = queryExternalStatsForUser.getTotalBytes();
                queryExternalStatsForUser.getAudioBytes();
                queryExternalStatsForUser.getVideoBytes();
                queryExternalStatsForUser.getImageBytes();
                queryExternalStatsForUser.getAppBytes();
                storageResult.externalStats = externalStorageStats;
            } catch (IOException e2) {
                Log.w("StorageAsyncLoader", e2);
            }
            Log.d("StorageAsyncLoader", "Obtaining result completed");
            Bundle bundle = new Bundle();
            bundle.putString("android:query-arg-sql-selection", "volume_name= 'external_primary'");
            storageResult.imagesSize =
                    getFilesSize(userInfo.id, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, bundle);
            storageResult.videosSize =
                    getFilesSize(userInfo.id, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, bundle);
            storageResult.audioSize =
                    getFilesSize(userInfo.id, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, bundle);
            try {
                j =
                        storageStatsSource.mStorageStatsManager.getTotalBytes(
                                        StorageManager.UUID_DEFAULT)
                                - Environment.getDataDirectory().getTotalSpace();
            } catch (IOException e3) {
                Log.e("StorageAsyncLoader", "Exception in calculating System category size", e3);
                j = 0;
            }
            storageResult.systemSize = j;
            storageResult.documentsSize =
                    getFilesSize(
                            userInfo.id,
                            MediaStore.Files.getContentUri("external_primary"),
                            AbsAdapter$1$$ExternalSyntheticOutline0.m(
                                    "android:query-arg-sql-selection", "media_type=6"));
            storageResult.otherSize =
                    getFilesSize(
                            userInfo.id,
                            MediaStore.Files.getContentUri("external_primary"),
                            AbsAdapter$1$$ExternalSyntheticOutline0.m(
                                    "android:query-arg-sql-selection",
                                    "media_type!=1 AND media_type!=3 AND media_type!=2 AND"
                                        + " media_type!=6 AND mime_type IS NOT NULL"));
            Bundle bundle2 = new Bundle();
            bundle2.putInt("android:query-arg-match-trashed", 3);
            storageResult.trashSize =
                    getFilesSize(
                            userInfo.id,
                            MediaStore.Files.getContentUri("external_primary"),
                            bundle2);
            sparseArray.put(userInfo.id, storageResult);
        }
        return sparseArray;
    }

    @Override // com.android.settingslib.utils.AsyncLoaderCompat
    public final /* bridge */ /* synthetic */ void onDiscardResult(Object obj) {}
}
