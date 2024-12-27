package com.android.settings.deviceinfo.storage;

import android.content.Context;
import android.content.SharedPreferences;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class StorageCacheHelper {
    public final SharedPreferences mSharedPreferences;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class StorageCache {
        public long allAppsExceptGamesSize;
        public long audioSize;
        public long documentsSize;
        public long gamesSize;
        public long imagesSize;
        public long otherSize;
        public long systemSize;
        public long temporaryFilesSize;
        public long totalSize;
        public long totalUsedSize;
        public long trashSize;
        public long videosSize;
    }

    public StorageCacheHelper(Context context, int i) {
        this.mSharedPreferences = context.getSharedPreferences("StorageCache" + i, 0);
    }

    public final boolean hasCachedSizeInfo() {
        return this.mSharedPreferences.getAll().size() > 0;
    }

    public final StorageCache retrieveCachedSize() {
        StorageCache storageCache = new StorageCache();
        storageCache.totalSize = this.mSharedPreferences.getLong("total_size_key", 0L);
        storageCache.totalUsedSize = this.mSharedPreferences.getLong("total_used_size_key", 0L);
        storageCache.imagesSize = this.mSharedPreferences.getLong("images_size_key", 0L);
        storageCache.videosSize = this.mSharedPreferences.getLong("videos_size_key", 0L);
        storageCache.audioSize = this.mSharedPreferences.getLong("audio_size_key", 0L);
        storageCache.allAppsExceptGamesSize = this.mSharedPreferences.getLong("apps_size_key", 0L);
        storageCache.gamesSize = this.mSharedPreferences.getLong("games_size_key", 0L);
        storageCache.documentsSize = this.mSharedPreferences.getLong("documents_size_key", 0L);
        storageCache.otherSize = this.mSharedPreferences.getLong("other_size_key", 0L);
        storageCache.trashSize = this.mSharedPreferences.getLong("trash_size_key", 0L);
        storageCache.systemSize = this.mSharedPreferences.getLong("system_size_key", 0L);
        storageCache.temporaryFilesSize =
                this.mSharedPreferences.getLong("temporary_files_size_key", 0L);
        return storageCache;
    }
}
