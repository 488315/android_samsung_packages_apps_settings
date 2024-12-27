package com.android.settings.deviceinfo.storage;

import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.os.storage.VolumeInfo;

import com.android.settingslib.deviceinfo.PrivateStorageInfo;
import com.android.settingslib.deviceinfo.StorageManagerVolumeProvider;
import com.android.settingslib.deviceinfo.StorageVolumeProvider;
import com.android.settingslib.utils.AsyncLoaderCompat;

import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class VolumeSizesLoader extends AsyncLoaderCompat {
    public final StorageStatsManager mStats;
    public final VolumeInfo mVolume;
    public final StorageVolumeProvider mVolumeProvider;

    public VolumeSizesLoader(
            Context context,
            StorageManagerVolumeProvider storageManagerVolumeProvider,
            StorageStatsManager storageStatsManager,
            VolumeInfo volumeInfo) {
        super(context);
        this.mVolumeProvider = storageManagerVolumeProvider;
        this.mStats = storageStatsManager;
        this.mVolume = volumeInfo;
    }

    public static PrivateStorageInfo getVolumeSize(
            StorageVolumeProvider storageVolumeProvider,
            StorageStatsManager storageStatsManager,
            VolumeInfo volumeInfo)
            throws IOException {
        ((StorageManagerVolumeProvider) storageVolumeProvider).getClass();
        return new PrivateStorageInfo(
                storageStatsManager.getFreeBytes(volumeInfo.getFsUuid()),
                storageStatsManager.getTotalBytes(volumeInfo.getFsUuid()));
    }

    @Override // androidx.loader.content.AsyncTaskLoader
    public final Object loadInBackground() {
        try {
            return getVolumeSize(this.mVolumeProvider, this.mStats, this.mVolume);
        } catch (IOException unused) {
            return null;
        }
    }

    @Override // com.android.settingslib.utils.AsyncLoaderCompat
    public final /* bridge */ /* synthetic */ void onDiscardResult(Object obj) {}
}
