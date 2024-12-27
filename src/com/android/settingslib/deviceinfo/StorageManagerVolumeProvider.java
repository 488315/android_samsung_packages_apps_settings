package com.android.settingslib.deviceinfo;

import android.os.storage.StorageManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class StorageManagerVolumeProvider implements StorageVolumeProvider {
    public final StorageManager mStorageManager;

    public StorageManagerVolumeProvider(StorageManager storageManager) {
        this.mStorageManager = storageManager;
    }
}
